package app;

import io.javalin.http.Context;
import io.javalin.http.Handler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PageST2A implements Handler {

    public static final String URL = "/page2A.html";

    @Override
    public void handle(Context context) throws Exception {
        String html = "<html>";
        html += "<head>" + 
                "<title>Food Loss and Waste Analysis by Country</title>" +
                "<link rel='stylesheet' type='text/css' href='sub2A.css' />" +
                "</head>";

        html += "<body>";
        html += "<header>";
        html += "  <div class='container'>";
        html += "    <div class='Weblogo'>";
        html += "      <a href='/'><img src='Weblogo.png' alt='Logo'></a>";
        html += "    </div>";
        html += "    <nav>";
        html += "      <ul class='nav-links'>";
        html += "        <li><a href='/'>Home</a></li>";
        html += "        <li><a href='mission.html'>Our Mission</a></li>";
        html += "        <li class='dropdown'>";
        html += "          <a href='#data'>Data & Resources <span class='arrow'>&#x25BC</span></a>";
        html += "          <div class='dropdown-content'>";
        html += "            <a href='page2A.html'>Food Loss and Waste Analysis by Country</a>";
        html += "            <a href='page2B.html'>Food Loss and Waste Analysis by Group</a>";
        html += "            <a href='page3A.html'>Similarity Data Analysis by Country</a>";
        html += "            <a href='page3B.html'>Similarity Data Analysis by Group</a>";
        html += "          </div>";
        html += "        </li>";
        html += "        <li><a href='Reference.html'>Reference</a></li>";
        html += "      </ul>";
        html += "    </nav>";
        html += "  </div>";
        html += "</header>";

        html += "<main>";
        html += "  <nav class='breadcrumb'>";
        html += "    <ul>";
        html += "      <li><a href='page2A.html'><b>Food Loss and Waste Analysis by Country</b></a></li>";
        html += "      <li>></li>";
        html += "      <li><a href='page2B.html'>Food Loss and Waste Analysis by Group</a></li>";
        html += "      <li>></li>";
        html += "      <li><a href='page3A.html'>Similarity Data Analysis by Country</a></li>";
        html += "      <li>></li>";
        html += "      <li><a href='page3B.html'>Similarity Data Analysis by Group</a></li>";
        html += "    </ul>";
        html += "  </nav>";

        html += "<h1>Food Loss Percent by Country</h1>";

        html += "<div class='form'>";
        html += "<form action='/page2A.html' method='post'>";
        html += "  <div class='form-group'>";
        html += "    <label>Select the Country(s):</label><br>";
        html += "    <input type='checkbox' name='country_drop' value='Burundi'>Burundi<br>";
        html += "    <input type='checkbox' name='country_drop' value='Cameroon'>Cameroon<br>";
        html += "  </div>";
        html += "  <div class='form-group'>";
        html += "    <label>Select the Start Year:</label>";
        html += "    <select name='start_year_drop'>";
        html += "      <option>2000</option>";
        html += "      <option>2001</option>";
        html += "      <option>2002</option>";
        html += "      <option>2003</option>";
        html += "    </select>";
        html += "  </div>";
        html += "  <div class='form-group'>";
        html += "    <label>Select the End Year:</label>";
        html += "    <select name='end_year_drop'>";
        html += "      <option>2000</option>";
        html += "      <option>2001</option>";
        html += "      <option>2002</option>";
        html += "      <option>2003</option>";
        html += "    </select>";
        html += "  </div>";
        html += "  <button type='submit' class='btn btn-primary'>Get Data</button>";
        html += "</form>";
        html += "</div>";

        List<String> country_drop_list = context.formParams("country_drop");
        String start_year_drop = context.formParam("start_year_drop");
        String end_year_drop = context.formParam("end_year_drop");
        String groupBy = context.formParam("group_by");

        if (country_drop_list == null || start_year_drop == null || end_year_drop == null) {
            html += "<h2><i>No Results to show</i></h2>";
        } else {
            String[] country_drop = country_drop_list.toArray(new String[0]);
            html += outputCountries(country_drop, start_year_drop, end_year_drop);

            html += "<form action='/page2A.html' method='post'>";
            html += "<div class='form-group'>";
            html += "  <label>Filter By:</label>";
            html += "  <select name='group_by'>";
            html += "    <option value='activity'>Activity</option>";
            html += "    <option value='food_supply_stage'>Food Supply Stage</option>";
            html += "    <option value='cause_of_loss'>Cause of Loss</option>";
            html += "  </select>";
            html += "</div>";
            html += "<input type='hidden' name='country_drop' value='" + String.join(",", country_drop) + "'>";
            html += "<input type='hidden' name='start_year_drop' value='" + start_year_drop + "'>";
            html += "<input type='hidden' name='end_year_drop' value='" + end_year_drop + "'>";
            html += "<button type='submit' class='btn btn-primary'>Apply Filter</button>";
            html += "</form>";

            if (groupBy != null && !groupBy.isEmpty()) {
                List<String> groupByList = new ArrayList<>();
                groupByList.add(groupBy);
                html += filterByFields(country_drop, start_year_drop, end_year_drop, groupByList);
            }
        }

        html += "</main>";

        html += "<footer>";
        html += "  <div class='footer-container'>";
        html += "    COSC2803 - Studio Project Starter Code (Apr24)";
        html += "  </div>";
        html += "</footer>";

        html += "</body></html>";

        context.html(html);
    }

    public String outputCountries(String[] countries, String startYear, String endYear) {
        String html = "<h2>Data from " + startYear + " to " + endYear + "</h2>";

        List<String> lossPercentages = getLossPercentages(countries, startYear, endYear);

        html += "<table class='content-table' border='1'>";
        html += "<tr class='heading'><th>Country</th><th>Start Year</th><th>Start Year Loss Percentage</th><th>End Year</th><th>End Year Loss Percentage</th><th>Loss Percent Difference</th></tr>";

        for (int i = 0; i < lossPercentages.size(); i += 3) {
            String country = lossPercentages.get(i);
            String loss_start_year = lossPercentages.get(i + 1);
            String loss_end_year = lossPercentages.get(i + 2);
            double loss_difference = Double.parseDouble(loss_start_year) - Double.parseDouble(loss_end_year);

            html += "<tr>";
            html += "<td>" + country + "</td>";
            html += "<td>" + startYear + "</td>";
            html += "<td>" + loss_start_year + "</td>";
            html += "<td>" + endYear + "</td>";
            html += "<td>" + loss_end_year + "</td>";
            html += "<td>" + loss_difference + "</td>";
            html += "</tr>";
        }

        html += "</table>";

        return html;
    }

    public String filterByFields(String[] countries, String startYear, String endYear, List<String> groupByList) {
        String html = "";

        List<Map<String, String>> sumLossPercentagesByFields = getSumLossPercentagesByFields(countries, startYear, endYear, groupByList);

        html += "<h2>Filtered Data by " + groupByList.get(0) + "</h2>";
        html += "<table border='1'>";
        html += "<tr>";
        html += "<th>" + groupByList.get(0) + "</th>";
        html += "<th>Sum (Loss Percentage)</th></tr>";

        for (Map<String, String> row : sumLossPercentagesByFields) {
            html += "<tr>";
            html += "<td>" + row.get(groupByList.get(0)) + "</td>";
            html += "<td>" + row.get("SumLossPercentage") + "</td>";
            html += "</tr>";
        }

        html += "</table>";

        return html;
    }

    public List<String> getLossPercentages(String[] countries, String startYear, String endYear) {
        List<String> countryData = new ArrayList<>();

        Connection connection = null;

        try {
            connection = DriverManager.getConnection(JDBCConnection.DATABASE);
            Statement statement = connection.createStatement();

            String countriesTogether = "'" + String.join("','", countries) + "'";
            String query = "SELECT t1.country, t1.loss1 AS loss_start_year, t2.loss2 AS loss_end_year " +
                           "FROM " +
                           "(SELECT SUM(loss_percentage) AS loss1, country " +
                           " FROM food_loss " +
                           " WHERE country IN (" + countriesTogether + ") " +
                           " AND year = '" + startYear + "' " +
                           " GROUP BY country, year) t1 " +
                           "JOIN " +
                           "(SELECT SUM(loss_percentage) AS loss2, country " +
                           " FROM food_loss " +
                           " WHERE country IN (" + countriesTogether + ") " +
                           " AND year = '" + endYear + "' " +
                           " GROUP BY country, year) t2 ON t1.country = t2.country";

            ResultSet results = statement.executeQuery(query);

            while (results.next()) {
                String country = results.getString("country");
                String loss_start_year = results.getString("loss_start_year");
                String loss_end_year = results.getString("loss_end_year");

                countryData.add(country);
                countryData.add(loss_start_year);
                countryData.add(loss_end_year);
            }

            results.close();
            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }

        return countryData;
    }

    public List<Map<String, String>> getSumLossPercentagesByFields(String[] countries, String startYear, String endYear, List<String> groupByList) {
        List<Map<String, String>> countryData = new ArrayList<>();

        try {
            Connection connection = DriverManager.getConnection(JDBCConnection.DATABASE);
            Statement statement = connection.createStatement();

            String countriesTogether = "'" + String.join("','", countries) + "'";
            String groupByField = groupByList.get(0);
            String query = "SELECT " + groupByField + ", SUM(loss_percentage) AS SumLossPercentage " +
                           "FROM food_loss " +
                           "WHERE country IN (" + countriesTogether + ") " +
                           "AND year >= '" + startYear + "' " +
                           "AND year <= '" + endYear + "' " +
                           "GROUP BY " + groupByField;

            ResultSet results = statement.executeQuery(query);

            while (results.next()) {
                Map<String, String> row = new HashMap<>();
                row.put(groupByField, results.getString(groupByField));
                row.put("SumLossPercentage", results.getString("SumLossPercentage"));
                countryData.add(row);
            }

            results.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return countryData;
    }
}
