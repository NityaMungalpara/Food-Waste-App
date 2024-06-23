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
        
        //breadcrumb
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

        //Add main content
        html += "<div class='form'>";
        html += "<form action='/page2A.html' method='post'>";
        html += "  <div class='form-group'>";
        html += "    <label>Select the Country(s):</label><br>";
        html += """
                        <input type='checkbox' name='country_drop' value='Africa'>Africa<br>
                        <input type='checkbox' name='country_drop' value='Algeria'>Algeria<br>
                        <input type='checkbox' name='country_drop' value='Angola'>Angola<br>
                        <input type='checkbox' name='country_drop' value='Argentina'>Argentina<br>
                        <input type='checkbox' name='country_drop' value='Argmenia'>Armenia<br>
                        <input type='checkbox' name='country_drop' value='Australia'>Australia<br>
                        <input type='checkbox' name='country_drop' value='Australia and New Zealend'>Australia and New Zealand<br>
                        <input type='checkbox' name='country_drop' value='Azerbaijan'>Azerbaijan<br>
                        <input type='checkbox' name='country_drop' value='Bahrain'>Bahrain<br>
                        <input type='checkbox' name='country_drop' value='Bangladesh'>Bangladesh<br>
                        <input type='checkbox' name='country_drop' value='Belize'>Belize<br>
                        <input type='checkbox' name='country_drop' value='Benin'>Benin<br>
                        <input type='checkbox' name='country_drop' value='Bolivia (Plurinational State of)'>Bolivia (Plurinational State of)<br>
                        <input type='checkbox' name='country_drop' value='Botswana'>Botswana<br>
                        <input type='checkbox' name='country_drop' value='Brazil'>Brazil<br>
                        <input type='checkbox' name='country_drop' value='Burkina Faso'>Burkina Faso<br>
                        <input type='checkbox' name='country_drop' value='Burundi'>Burundi<br>
                        <input type='checkbox' name='country_drop' value='Cote d'Ivoire'>Cote d'Ivoire<br>
                        <input type='checkbox' name='country_drop' value='Cambodia'>Cambodia<br>
                        <input type='checkbox' name='country_drop' value='Cameroon'>Cameroon<br>
                        <input type='checkbox' name='country_drop' value='Canada'>Canada<br>
                        <input type='checkbox' name='country_drop' value='Central Asia'>Central Asia<br>
                        <input type='checkbox' name='country_drop' value='Chad'>Chad<br>
                        <input type='checkbox' name='country_drop' value='Chile'>Chile<br>
                        <input type='checkbox' name='country_drop' value='China'>China<br>
                        <input type='checkbox' name='country_drop' value='Colombia'>Colombia<br>
                        <input type='checkbox' name='country_drop' value='Costa Rica'>Costa Rica<br>
                        <input type='checkbox' name='country_drop' value='Cuba'>Cuba<br>
                        <input type='checkbox' name='country_drop' value='Democratic People's Republic of Korea'>Democratic People's Republic of Korea<br>
                        <input type='checkbox' name='country_drop' value='Democratic Republic of the Congo'>Democratic Republic of the Congo<br>
                        <input type='checkbox' name='country_drop' value='Denmark'>Denmark<br>
                        <input type='checkbox' name='country_drop' value='Dominican Republic'>Dominican Republic<br>
                        <input type='checkbox' name='country_drop' value='Ecuador'>Ecuador<br>
                        <input type='checkbox' name='country_drop' value='Egypt'>Egypt<br>
                        <input type='checkbox' name='country_drop' value='El Salvador'>El Salvador<br>
                        <input type='checkbox' name='country_drop' value='Eritrea'>Eritrea<br>
                        <input type='checkbox' name='country_drop' value='Eswatini'>Eswatini<br>
                        <input type='checkbox' name='country_drop' value='Ethiopia'>Ethiopia<br>
                        <input type='checkbox' name='country_drop' value='Europe'>Europe<br>
                        <input type='checkbox' name='country_drop' value='Fiji'>Fiji<br>
                        <input type='checkbox' name='country_drop' value='Finland'>Finland<br>
                        <input type='checkbox' name='country_drop' value='France'>France<br>
                        <input type='checkbox' name='country_drop' value='Gabon'>Gabon<br>
                        <input type='checkbox' name='country_drop' value='Gambia'>Gambia<br>
                        <input type='checkbox' name='country_drop' value='Germany'>Germany<br>
                        <input type='checkbox' name='country_drop' value='Ghana'>Ghana<br>
                        <input type='checkbox' name='country_drop' value='Guatemala'>Guatemala<br>
                        <input type='checkbox' name='country_drop' value='Guinea'>Guinea<br>
                        <input type='checkbox' name='country_drop' value='Guinea-Bissau'>Guinea-Bissau<br>
                        <input type='checkbox' name='country_drop' value='Guyana'>Guyana<br>
                        <input type='checkbox' name='country_drop' value='Haiti'>Haiti<br>
                        <input type='checkbox' name='country_drop' value='Honduras'>Honduras<br>
                        <input type='checkbox' name='country_drop' value='India'>India<br>
                        <input type='checkbox' name='country_drop' value='Indonesia'>Indonesia<br>
                        <input type='checkbox' name='country_drop' value='Iran (Islamic Republic of)'>Iran (Islamic Republic of)<br>
                        <input type='checkbox' name='country_drop' value='Italy'>Italy<br>
                        <input type='checkbox' name='country_drop' value='Jordan'>Jordan<br>
                        <input type='checkbox' name='country_drop' value='Kazakhstan'>Kazakhstan<br>
                        <input type='checkbox' name='country_drop' value='Kenya'>Kenya<br>
                        <input type='checkbox' name='country_drop' value='Lao People's Democratic Republic'>Lao People's Democratic Republic<br>
                        <input type='checkbox' name='country_drop' value='Latin America and the Caribbean'>Latin America and the Caribbean<br>
                        <input type='checkbox' name='country_drop' value='Lebanon'>Lebanon<br>
                        <input type='checkbox' name='country_drop' value='Lesotho'>Lesotho<br>
                        <input type='checkbox' name='country_drop' value='Liberia'>Liberia<br>
                        <input type='checkbox' name='country_drop' value='Libya'>Libya<br>
                        <input type='checkbox' name='country_drop' value='Madagascar'>Madagascar<br>
                        <input type='checkbox' name='country_drop' value='Malawi'>Malawi<br>
                        <input type='checkbox' name='country_drop' value='Malaysia'>Malaysia<br>
                        <input type='checkbox' name='country_drop' value='Mali'>Mali<br>
                        <input type='checkbox' name='country_drop' value='Mauritania'>Mauritania<br>
                        <input type='checkbox' name='country_drop' value='Mexico'>Mexico<br>
                        <input type='checkbox' name='country_drop' value='Morocco'>Morocco<br>
                        <input type='checkbox' name='country_drop' value='Mozambique'>Mozambique<br>
                        <input type='checkbox' name='country_drop' value='Myanmar'>Myanmar<br>
                        <input type='checkbox' name='country_drop' value='Namibia'>Namibia<br>
                        <input type='checkbox' name='country_drop' value='Nepal'>Nepal<br>
                        <input type='checkbox' name='country_drop' value='New Zealand'>New Zealand<br>
                        <input type='checkbox' name='country_drop' value='Nicaragua'>Nicaragua<br>
                        <input type='checkbox' name='country_drop' value='Niger'>Niger<br>
                        <input type='checkbox' name='country_drop' value='Nigeria'>Nigeria<br>
                        <input type='checkbox' name='country_drop' value='Northern Africa'>Northern Africa<br>
                        <input type='checkbox' name='country_drop' value='Northern America'>Northern America<br>
                        <input type='checkbox' name='country_drop' value='Norway'>Norway<br>
                        <input type='checkbox' name='country_drop' value='Oman'>Oman<br>
                        <input type='checkbox' name='country_drop' value='Pakistan'>Pakistan<br>
                        <input type='checkbox' name='country_drop' value='Palestine'>Palestine<br>
                        <input type='checkbox' name='country_drop' value='Panama'>Panama<br>
                        <input type='checkbox' name='country_drop' value='Paraguay'>Paraguay<br>
                        <input type='checkbox' name='country_drop' value='Peru'>Peru<br>
                        <input type='checkbox' name='country_drop' value='Philippines'>Philippines<br>
                        <input type='checkbox' name='country_drop' value='Republic of Korea'>Republic of Korea<br>
                        <input type='checkbox' name='country_drop' value='Rwanda'>Rwanda<br>
                        <input type='checkbox' name='country_drop' value='Saint Kitts and Nevis'>Saint Kitts and Nevis<br>
                        <input type='checkbox' name='country_drop' value='Saint Lucia'>Saint Lucia<br>
                        <input type='checkbox' name='country_drop' value='Saudi Arabia'>Saudi Arabia<br>
                        <input type='checkbox' name='country_drop' value='Senegal'>Senegal<br>
                        <input type='checkbox' name='country_drop' value='Serbia'>Serbia<br>
                        <input type='checkbox' name='country_drop' value='Sierra Leone'>Sierra Leone<br>
                        <input type='checkbox' name='country_drop' value='Somalia'>Somalia<br>
                        <input type='checkbox' name='country_drop' value='South Africa'>South Africa<br>
                        <input type='checkbox' name='country_drop' value='South Sudan'>South Sudan<br>
                        <input type='checkbox' name='country_drop' value='South-Eastern Asia'>South-Eastern Asia<br>
                        <input type='checkbox' name='country_drop' value='Southern Asia'>Southern Asia<br>
                        <input type='checkbox' name='country_drop' value='Sri Lanka'>Sri Lanka<br>
                        <input type='checkbox' name='country_drop' value='Sub-Saharan Africa'>Sub-Saharan Africa<br>
                        <input type='checkbox' name='country_drop' value='Sudan'>Sudan<br>
                        <input type='checkbox' name='country_drop' value='Sweden'>Sweden<br>
                        <input type='checkbox' name='country_drop' value='Switzerland'>Switzerland<br>
                        <input type='checkbox' name='country_drop' value='Syrian Arab Republic'>Syrian Arab Republic<br>
                        <input type='checkbox' name='country_drop' value='Thailand'>Thailand<br>
                        <input type='checkbox' name='country_drop' value='Timor-Leste'>Timor-Leste<br>
                        <input type='checkbox' name='country_drop' value='Togo'>Togo<br>
                        <input type='checkbox' name='country_drop' value='Trinidad and Tobago'>Trinidad and Tobago<br>
                        <input type='checkbox' name='country_drop' value='Tunisia'>Tunisia<br>
                        <input type='checkbox' name='country_drop' value='Turkey'>Turkey<br>
                        <input type='checkbox' name='country_drop' value='Uganda'>Uganda<br>
                        <input type='checkbox' name='country_drop' value='Ukraine'>Ukraine<br>
                        <input type='checkbox' name='country_drop' value='United Kingdom of Great Britain and Northern Ireland'>United Kingdom of Great Britain and Northern Ireland<br>
                        <input type='checkbox' name='country_drop' value='United Republic of Tanzania'>United Republic of Tanzania<br>
                        <input type='checkbox' name='country_drop' value='United States of America'>United States of America<br>
                        <input type='checkbox' name='country_drop' value='Uruguay'>Uruguay<br>
                        <input type='checkbox' name='country_drop' value='Venezuela (Bolivarian Republic of)'>Venezuela (Bolivarian Republic of)<br>
                        <input type='checkbox' name='country_drop' value='Viet Nam'>Viet Nam<br>
                        <input type='checkbox' name='country_drop' value='Western Africa'>Western Africa<br>
                        <input type='checkbox' name='country_drop' value='Western Asia'>Western Asia<br>
                        <input type='checkbox' name='country_drop' value='Zambia'>Zambia<br>
                        <input type='checkbox' name='country_drop' value='Zimbabwe'>Zimbabwe<br>
                """;    
        html += "  </div>";
        html += "  <div class='form-group'>";
        html += "    <label>Select the Start Year:</label>";
        html += "    <select name='start_year_drop'>";
        html += """
                        <option>1966</option>";
                        <option>1967</option>";
                        <option>1968</option>";
                        <option>1969</option>";
                        <option>1970</option>";
                        <option>1971</option>";
                        <option>1972</option>";
                        <option>1973</option>";
                        <option>1974</option>";
                        <option>1975</option>";
                        <option>1976</option>";
                        <option>1977</option>";
                        <option>1978</option>";
                        <option>1979</option>";
                        <option>1980</option>";
                        <option>1981</option>";
                        <option>1982</option>";
                        <option>1983</option>";
                        <option>1984</option>";
                        <option>1985</option>";
                        <option>1986</option>";
                        <option>1987</option>";
                        <option>1988</option>";
                        <option>1989</option>";
                        <option>1990</option>";
                        <option>1991</option>";
                        <option>1992</option>";
                        <option>1993</option>";
                        <option>1994</option>";
                        <option>1995</option>";
                        <option>1996</option>";
                        <option>1997</option>";
                        <option>1998</option>";
                        <option>1999</option>";
                        <option>2000</option>";
                        <option>2001</option>";
                        <option>2002</option>";
                        <option>2003</option>";
                        <option>2004</option>";
                        <option>2005</option>";
                        <option>2006</option>";
                        <option>2007</option>";
                        <option>2008</option>";
                        <option>2009</option>";
                        <option>2010</option>";
                        <option>2011</option>";
                        <option>2012</option>";
                        <option>2013</option>";
                        <option>2014</option>";
                        <option>2015</option>";
                        <option>2016</option>";
                        <option>2017</option>";
                        <option>2018</option>";
                        <option>2019</option>";
                        <option>2020</option>";
                        <option>2021</option>";
                        <option>2022</option>";
                        <option>2023</option>";
                        <option>2024</option>";
                """;  
        html += "    </select>";
        html += "  </div>";
        html += "  <div class='form-group'>";
        html += "    <label>Select the End Year:</label>";
        html += "    <select name='end_year_drop'>";
        html += """
                        <option>1966</option>";
                        <option>1967</option>";
                        <option>1968</option>";
                        <option>1969</option>";
                        <option>1970</option>";
                        <option>1971</option>";
                        <option>1972</option>";
                        <option>1973</option>";
                        <option>1974</option>";
                        <option>1975</option>";
                        <option>1976</option>";
                        <option>1977</option>";
                        <option>1978</option>";
                        <option>1979</option>";
                        <option>1980</option>";
                        <option>1981</option>";
                        <option>1982</option>";
                        <option>1983</option>";
                        <option>1984</option>";
                        <option>1985</option>";
                        <option>1986</option>";
                        <option>1987</option>";
                        <option>1988</option>";
                        <option>1989</option>";
                        <option>1990</option>";
                        <option>1991</option>";
                        <option>1992</option>";
                        <option>1993</option>";
                        <option>1994</option>";
                        <option>1995</option>";
                        <option>1996</option>";
                        <option>1997</option>";
                        <option>1998</option>";
                        <option>1999</option>";
                        <option>2000</option>";
                        <option>2001</option>";
                        <option>2002</option>";
                        <option>2003</option>";
                        <option>2004</option>";
                        <option>2005</option>";
                        <option>2006</option>";
                        <option>2007</option>";
                        <option>2008</option>";
                        <option>2009</option>";
                        <option>2010</option>";
                        <option>2011</option>";
                        <option>2012</option>";
                        <option>2013</option>";
                        <option>2014</option>";
                        <option>2015</option>";
                        <option>2016</option>";
                        <option>2017</option>";
                        <option>2018</option>";
                        <option>2019</option>";
                        <option>2020</option>";
                        <option>2021</option>";
                        <option>2022</option>";
                        <option>2023</option>";
                        <option>2024</option>";
                """;
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
            html += "<label>NOTE : Filter functionality works for only 1 country selection</label><br>";
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
                List<String> displayFields = new ArrayList<>(groupByList);
                html += filterByFields(country_drop, start_year_drop, end_year_drop, groupByList, displayFields);
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

    public String filterByFields(String[] countries, String startYear, String endYear, List<String> groupByList, List<String> displayFields) {
        String html = "";
    
        List<Map<String, String>> sumLossPercentagesByFields = getSumLossPercentagesByFields(countries, startYear, endYear, groupByList);
    
        html += "<h2>Filtered Data by " + String.join(", ", groupByList) + "</h2>";
        html += "<table class='content-table' border='1'>";
        html += "<tr>";
    
        // Add table headers based on displayFields
        for (String field : displayFields) {
            html += "<th>" + field + "</th>";
        }
        html += "<th>Average (Loss Percentage)</th></tr>";
    
        for (Map<String, String> row : sumLossPercentagesByFields) {
            html += "<tr>";
    
            // Add table data based on displayFields
            for (String field : displayFields) {
                String value = row.getOrDefault(field, "Null");
                html += "<td>" + (value != null && !value.isEmpty() ? value : "Null") + "</td>";
            }
            String averageLossPercentage = row.getOrDefault("AverageLossPercentage", "Null");
            html += "<td>" + (averageLossPercentage != null && !averageLossPercentage.isEmpty() ? averageLossPercentage : "Null") + "</td>";
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
            String groupByFields = String.join(", ", groupByList);
            String query = "SELECT " + groupByFields + ", AVG(loss_percentage) AS AverageLossPercentage " +
                           "FROM food_loss " +
                           "WHERE country IN (" + countriesTogether + ") " +
                           "AND year >= '" + startYear + "' " +
                           "AND year <= '" + endYear + "' " +
                           "GROUP BY " + groupByFields;
    
            ResultSet results = statement.executeQuery(query);
    
            while (results.next()) {
                Map<String, String> row = new HashMap<>();
                for (String field : groupByList) {
                    String value = results.getString(field);
                    row.put(field, value != null ? value : "Null");
                }
                String averageLossPercentage = results.getString("AverageLossPercentage");
                row.put("AverageLossPercentage", averageLossPercentage != null ? averageLossPercentage : "Null");
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
