package app;

import io.javalin.http.Context;
import io.javalin.http.Handler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PageST3A implements Handler {

    // URL of this page relative to http://localhost:7001/
    public static final String URL = "/page3A.html";

    @Override
    public void handle(Context context) throws Exception {
        // Create a simple HTML webpage in a String
        String html = "<html>";

        // Add some Head information
        html += "<head>" +
                "<title>Similarity Data Analysis by Country</title>" +
                "<link rel='stylesheet' type='text/css' href='sub3A.css' />" +
                "</head>";

        // Add the body
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

        //Breadcrumb
        html += "<main>";
        html += "  <nav class='breadcrumb'>";
        html += "    <ul>";
        html += "      <li><a href='page2A.html'>Food Loss and Waste Analysis by Country</a></li>";
        html += "      <li>></li>";
        html += "      <li><a href='page2B.html'>Food Loss and Waste Analysis by Group</a></li>";
        html += "      <li>></li>";
        html += "      <li><a href='page3A.html'><b>Similarity Data Analysis by Country</b></a></li>";
        html += "      <li>></li>";
        html += "      <li><a href='page3B.html'>Similarity Data Analysis by Group</a></li>";
        html += "    </ul>";
        html += "  </nav>";

        html += "<h1>Similarity Data Analysis by Country</h1>";

        // Add Div for page Content
        html += "<div class='content'>";

        // Fetch years, countries, and commodities from the database
        List<String> years = getYears();
        List<String> countries = getCountries();
        List<String> commodities = getCommodities();

        // Add HTML for the form
        html += "<form action='/page3A.html' method='post'>" +
                "<div class='form-group'>" +
                "<label for='year'>Select the Year:</label>" +
                "<select id='year' name='year'>";
        
        for (String year : years) {
            html += "<option value='" + year + "'>" + year + "</option>";
        }

        html += "</select></div>" +
                "<div class='form-group'>" +
                "<label for='location'>Select the Country or Region:</label>" +
                "<select id='location' name='location'>";
        
        for (String country : countries) {
            html += "<option value='" + country + "'>" + country + "</option>";
        }

        html += "</select></div>" +
                "<div class='form-group'>" +
                "<label for='similarity_type'>Select the Similarity Type:</label>" +
                "<select id='similarity_type' name='similarity_type'>" +
                "<option value='common_foods'>Common food products</option>" +
                "<option value='loss_percentage'>Overall percentage of food loss/waste</option>" +
                "<option value='both'>Both common food products and loss/waste percentage</option>" +
                "</select></div>" +
                "<div class='form-group'>" +
                "<label for='similarity_measure'>Select the Similarity Measure:</label>" +
                "<select id='similarity_measure' name='similarity_measure'>" +
                "<option value='absolute_values'>Absolute values</option>" +
                "<option value='level_of_overlap'>Level of overlap</option>" +
                "</select></div>" +
                "<div class='form-group'>" +
                "<label for='num_results'>Number of Similar Results:</label>" +
                "<input type='number' id='num_results' name='num_results' min='1' max='10' value='5'>" +
                "</div>" +
                "<button type='submit' class='btn btn-primary'>Find Similar Groups</button>" +
                "</form>";

        String year = context.formParam("year");
        String location = context.formParam("location");
        String similarityType = context.formParam("similarity_type");
        String similarityMeasure = context.formParam("similarity_measure");
        String numResults = context.formParam("num_results");

        if (year != null && location != null && similarityType != null && similarityMeasure != null && numResults != null) {
            int numResultsInt = Integer.parseInt(numResults);
            html += outputSimilarCountries(year, location, similarityType, similarityMeasure, numResultsInt);
        }

        // Close Content div
        html += "</div>";

        // Footer
        html += """
            <div class='footer-container'>
                <p>COSC2803 - Studio Project Starter Code (Apr24)</p>
            </div>
        """;

        // Finish the HTML webpage
        html += "</body></html>";

        // Makes Javalin render the webpage
        context.html(html);
    }

    private List<String> getYears() {
        List<String> years = new ArrayList<>();

        String query = "SELECT DISTINCT year FROM food_loss ORDER BY year";

        try (Connection connection = DriverManager.getConnection(JDBCConnection.DATABASE);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                years.add(resultSet.getString("year"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return years;
    }

    private List<String> getCountries() {
        List<String> countries = new ArrayList<>();

        String query = "SELECT DISTINCT country FROM food_loss";

        try (Connection connection = DriverManager.getConnection(JDBCConnection.DATABASE);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                countries.add(resultSet.getString("country"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return countries;
    }

    private List<String> getCommodities() {
        List<String> commodities = new ArrayList<>();

        String query = "SELECT DISTINCT commodity FROM food_loss";

        try (Connection connection = DriverManager.getConnection(JDBCConnection.DATABASE);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                commodities.add(resultSet.getString("commodity"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return commodities;
    }

    private String outputSimilarCountries(String year, String location, String similarityType, String similarityMeasure, int numResults) {
        String html = "<h2>Similar Countries/Regions for Year: " + year + " and Location: " + location + "</h2>";

        List<SimilarityResult> similarGroups = getSimilarGroups(year, location, similarityType, similarityMeasure, numResults);
        if (similarGroups.isEmpty()) {
            html += "<p>No similar countries/regions found.</p>";
        } else {
            html += "<table class='content-table' border='1'>";
            html += "<tr><th>Country/Region</th><th>Year</th><th>Similarity Measure</th><th>Similarity Score</th></tr>";

            for (SimilarityResult result : similarGroups) {
                html += "<tr>";
                html += "<td>" + result.region + "</td>";
                html += "<td>" + result.year + "</td>";
                html += "<td>" + result.similarityMeasure + "</td>";
                html += "<td>" + result.similarityScore + "</td>";
                html += "</tr>";
            }

            html += "</table>";
        }

        return html;
    }

    private List<SimilarityResult> getSimilarGroups(String year, String location, String similarityType, String similarityMeasure, int numResults) {
        List<SimilarityResult> results = new ArrayList<>();

        String query = getSimilarityQuery(year, location, similarityType, similarityMeasure, numResults);

        try (Connection connection = DriverManager.getConnection(JDBCConnection.DATABASE);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                SimilarityResult result = new SimilarityResult();
                result.region = resultSet.getString("region");
                result.year = resultSet.getString("year");
                result.similarityMeasure = resultSet.getDouble("similarityMeasure");
                result.similarityScore = resultSet.getDouble("similarityScore");
                results.add(result);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return results;
    }

    private String getSimilarityQuery(String year, String location, String similarityType, String similarityMeasure, int numResults) {
        String query = "";
        String baseQuery = "SELECT country, year, commodity, loss_percentage FROM food_loss";

        switch (similarityType) {
            case "common_foods":
                query = "WITH SelectedData AS ( " +
                        "SELECT country, year, commodity, loss_percentage " +
                        "FROM food_loss " +
                        "WHERE year = '" + year + "' AND country = '" + location + "' " +
                        ") " +
                        "SELECT fd.country AS region, fd.year, COUNT(fd.commodity) AS similarityMeasure, " +
                        "ABS(COUNT(fd.commodity) - (SELECT COUNT(commodity) FROM SelectedData)) AS similarityScore " +
                        "FROM food_loss fd " +
                        "INNER JOIN SelectedData sd ON fd.commodity = sd.commodity " +
                        "WHERE fd.year = '" + year + "' AND fd.country != '" + location + "' " +
                        "GROUP BY fd.country, fd.year " +
                        "ORDER BY similarityScore ASC " +
                        "LIMIT " + numResults;
                break;
            case "loss_percentage":
                query = "WITH SelectedData AS ( " +
                        "SELECT country, year, commodity, AVG(loss_percentage) AS avg_loss " +
                        "FROM food_loss " +
                        "WHERE year = '" + year + "' AND country = '" + location + "' " +
                        "GROUP BY country, year, commodity " +
                        ") " +
                        "SELECT fd.country AS region, fd.year, AVG(fd.loss_percentage) AS similarityMeasure, " +
                        "ABS(AVG(fd.loss_percentage) - (SELECT avg_loss FROM SelectedData)) AS similarityScore " +
                        "FROM food_loss fd " +
                        "INNER JOIN SelectedData sd ON fd.commodity = sd.commodity " +
                        "WHERE fd.year = '" + year + "' AND fd.country != '" + location + "' " +
                        "GROUP BY fd.country, fd.year " +
                        "ORDER BY similarityScore ASC " +
                        "LIMIT " + numResults;
                break;
            case "both":
                query = "WITH SelectedData AS ( " +
                        "SELECT country, year, commodity, loss_percentage " +
                        "FROM food_loss " +
                        "WHERE year = '" + year + "' AND country = '" + location + "' " +
                        "), CommonFoods AS ( " +
                        "SELECT fd.country, fd.year, COUNT(fd.commodity) AS commonFoodCount " +
                        "FROM food_loss fd " +
                        "INNER JOIN SelectedData sd ON fd.commodity = sd.commodity " +
                        "WHERE fd.year = '" + year + "' AND fd.country != '" + location + "' " +
                        "GROUP BY fd.country, fd.year " +
                        "), LossPercentage AS ( " +
                        "SELECT fd.country, fd.year, AVG(fd.loss_percentage) AS avgLossPercentage " +
                        "FROM food_loss fd " +
                        "INNER JOIN SelectedData sd ON fd.commodity = sd.commodity " +
                        "WHERE fd.year = '" + year + "' AND fd.country != '" + location + "' " +
                        "GROUP BY fd.country, fd.year " +
                        ") " +
                        "SELECT cf.country AS region, cf.year, (cf.commonFoodCount + lp.avgLossPercentage) / 2 AS similarityMeasure, " +
                        "ABS((cf.commonFoodCount + lp.avgLossPercentage) / 2 - " +
                        "(SELECT (COUNT(sd.commodity) + AVG(sd.loss_percentage)) / 2 FROM SelectedData sd)) AS similarityScore " +
                        "FROM CommonFoods cf " +
                        "INNER JOIN LossPercentage lp ON cf.country = lp.country AND cf.year = lp.year " +
                        "ORDER BY similarityScore ASC " +
                        "LIMIT " + numResults;
                break;
        }

        return query;
    }

    

    private class SimilarityResult {
        String region;
        String year;
        double similarityMeasure;
        double similarityScore;
    }
}
