package app;

import io.javalin.http.Context;
import io.javalin.http.Handler;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PageST3A implements Handler {

    // URL of this page relative to http://localhost:7001/
    public static final String URL = "/page3A.html";

    @Override
    public void handle(Context context) throws Exception {
        // Create a simple HTML webpage in a String
        String html = generateHeader();

        // Process form submission or display form
        if (context.formParamMap().isEmpty()) {
            html += generateForm();
        } else {
            String selectedCountry = context.formParam("country_drop");
            String selectedYear = context.formParam("year_drop");
            String similarityType = context.formParam("similarity_type");

            if (selectedCountry == null || selectedYear == null || similarityType == null) {
                html += "<h2><i>No Results to show</i></h2>";
            } else {
                html += "<h2>Selected Country: " + selectedCountry + ", Year: " + selectedYear + "</h2>";

                List<Map<String, String>> similarCountries = findSimilarCountries(selectedCountry, selectedYear, similarityType);

                if (similarCountries.isEmpty()) {
                    html += "<h3>No similar countries found based on selected criteria.</h3>";
                } else {
                    html += "<h3>Most Similar Countries:</h3>";
                    html += "<table border='1'>";
                    html += "<tr><th>Country</th><th>Similarity Score</th><th>Details</th></tr>";

                    for (Map<String, String> country : similarCountries) {
                        html += "<tr>";
                        html += "<td>" + country.get("country") + "</td>";
                        html += "<td>" + country.get("similarity_score") + "</td>";
                        html += "<td>" + country.get("details") + "</td>";
                        html += "</tr>";
                    }

                    html += "</table>";
                }
            }
        }

        html += generateFooter();

        // Render the HTML using Javalin
        context.html(html);
    }

    private String generateHeader() {
        return "<html><head>" +
                "<title>Similarity Data Analysis by Country</title>" +
                "<link rel='stylesheet' type='text/css' href='sub3A.css' />" +
                "</head><body>" +
                "<header>" +
                "<div class='container'>" +
                "<div class='Weblogo'>" +
                "<a href='/'><img src='Weblogo.png' alt='Logo'></a>" +
                "</div>" +
                "<nav>" +
                "<ul class='nav-links'>" +
                "<li><a href='/'>Home</a></li>" +
                "<li><a href='mission.html'>Our Mission</a></li>" +
                "<li class='dropdown'>" +
                "<a href='#data'>Data & Resources <span class='arrow'>&#x25BC</span></a>" +
                "<div class='dropdown-content'>" +
                "<a href='page2A.html'>Food Loss and Waste Analysis by Country</a>" +
                "<a href='page2B.html'>Food Loss and Waste Analysis by Group</a>" +
                "<a href='page3A.html'>Similarity Data Analysis by Country</a>" +
                "<a href='page3B.html'>Similarity Data Analysis by Group</a>" +
                "</div>" +
                "</li>" +
                "<li><a href='Reference.html'>Reference</a></li>" +
                "</ul>" +
                "</nav>" +
                "</div>" +
                "</header>" +
                "<main>" +
                "<nav class='breadcrumb'>" +
                "<ul>" +
                "<li><a href='page2A.html'>Food Loss and Waste Analysis by Country</a></li>" +
                "<li>></li>" +
                "<li><a href='page2B.html'>Food Loss and Waste Analysis by Group</a></li>" +
                "<li>></li>" +
                "<li><a href='page3A.html'><b>Similarity Data Analysis by Country</b></a></li>" +
                "<li>></li>" +
                "<li><a href='page3B.html'>Similarity Data Analysis by Group</a></li>" +
                "</ul>" +
                "</nav>" +
                "<h1>Similarity Data Analysis by Country</h1>";
    }

    private String generateForm() {
        return "<div class='form'>" +
                "<form action='/page3A.html' method='post'>" +
                "<div class='form-group'>" +
                "<label>Select the Country:</label><br>" +
                "<select name='country_drop'>" +
                "<option value='Australia'>Australia</option>" +
                "<option value='Brazil'>Brazil</option>" +
                "<option value='Canada'>Canada</option>" +
                "</select>" +
                "</div>" +
                "<div class='form-group'>" +
                "<label>Select the Year:</label>" +
                "<select name='year_drop'>" +
                "<option value='2019'>2019</option>" +
                "<option value='2020'>2020</option>" +
                "<option value='2021'>2021</option>" +
                "</select>" +
                "</div>" +
                "<div class='form-group'>" +
                "<label>Select Similarity Type:</label>" +
                "<select name='similarity_type'>" +
                "<option value='food_products_absolute'>Food Products (Absolute)</option>" +
                "<option value='food_products_overlap'>Food Products (Overlap)</option>" +
                "<option value='loss_percentage'>Loss Percentage</option>" +
                "<option value='food_and_loss'>Food Products and Loss Percentage</option>" +
                "</select>" +
                "</div>" +
                "<button type='submit' class='btn btn-primary'>Find Similar Countries</button>" +
                "</form>" +
                "</div>";
    }

    private List<Map<String, String>> findSimilarCountries(String selectedCountry, String selectedYear, String similarityType) {
        List<Map<String, String>> similarCountries = new ArrayList<>();

        try {
            Connection connection = DriverManager.getConnection(JDBCConnection.DATABASE);
            Statement statement = connection.createStatement();

            String query = "";
            switch (similarityType) {
                case "food_products_absolute":
                    query = "SELECT country, similarity_score, details FROM similarity_analysis WHERE selected_country = '" + selectedCountry + "' AND year = '" + selectedYear + "' ORDER BY similarity_score DESC LIMIT 5";
                    break;
                case "food_products_overlap":
                    query = "SELECT country, similarity_score, details FROM similarity_analysis WHERE selected_country = '" + selectedCountry + "' AND year = '" + selectedYear + "' ORDER BY similarity_score DESC LIMIT 5";
                    break;
                case "loss_percentage":
                    query = "SELECT country, similarity_score, details FROM similarity_analysis WHERE selected_country = '" + selectedCountry + "' AND year = '" + selectedYear + "' ORDER BY similarity_score DESC LIMIT 5";
                    break;
                case "food_and_loss":
                    query = "SELECT country, similarity_score, details FROM similarity_analysis WHERE selected_country = '" + selectedCountry + "' AND year = '" + selectedYear + "' ORDER BY similarity_score DESC LIMIT 5";
                    break;
                default:
                    break;
            }

            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String country = resultSet.getString("country");
                String similarityScore = resultSet.getString("similarity_score");
                String details = resultSet.getString("details");

                Map<String, String> countryMap = new HashMap<>();
                countryMap.put("country", country);
                countryMap.put("similarity_score", similarityScore);
                countryMap.put("details", details);

                similarCountries.add(countryMap);
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return similarCountries;
    }

    public List<String> getSimilarCountries(String selectedCountry, String selectedYear, List<String> selectedFoodProducts) {
        List<String> similarCountries = new ArrayList<>();
    
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(JDBCConnection.DATABASE);
            Statement statement = connection.createStatement();
    
            // Build the SQL query based on selected criteria
            String foodProductsQuery = "'" + String.join("','", selectedFoodProducts) + "'";
            String query = "SELECT DISTINCT country " +
                           "FROM food_loss " +
                           "WHERE country <> '" + selectedCountry + "' " +
                           "AND year = '" + selectedYear + "' " +
                           "AND food_product IN (" + foodProductsQuery + ") " +
                           "ORDER BY some_similarity_score DESC " +
                           "LIMIT 5";  // Adjust limit as per your requirement
    
            ResultSet results = statement.executeQuery(query);
    
            while (results.next()) {
                String country = results.getString("country");
                similarCountries.add(country);
            }
    
            results.close();
            statement.close();
        } catch (SQLException e) {
            System.err.println("Error executing SQL query: " + e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println("Error closing connection: " + e.getMessage());
            }
        }
    
        return similarCountries;
    }
    

    private String generateFooter() {
        return "</main><footer>" +
                "<div class='footer-container'>" +
                "COSC2803 - Studio Project Starter Code (Apr24)" +
                "</div>" +
                "</footer>" +
                "</body></html>";
    }
}
