package app;

import java.util.ArrayList;

import io.javalin.http.Context;
import io.javalin.http.Handler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Example Index HTML class using Javalin
 * <p>
 * Generate a static HTML page using Javalin
 * by writing the raw HTML into a Java String object
 *
 * @author Timothy Wiley, 2023. email: timothy.wiley@rmit.edu.au
 * @author Santha Sumanasekara, 2021. email: santha.sumanasekara@rmit.edu.au
 * @author Halil Ali, 2024. email: halil.ali@rmit.edu.au
 */

public class PageST2A implements Handler {

    // URL of this page relative to http://localhost:7001/
    public static final String URL = "/page2A.html";

    @Override
    public void handle(Context context) throws Exception {
        // Create a simple HTML webpage in a String
        String html = "<html>";

        // Add some Head information
        html = html + "<head>" + 
               "<title>Subtask 2.1</title>";

        // Add some CSS (external file)
        html = html + "<link rel='stylesheet' type='text/css' href='common.css' />";
        html = html + "</head>";

        // Add the body
        html = html + "<body>";

        // Add the topnav
        // This uses a Java v15+ Text Block
        html = html + """
            <div class='topnav'>
                <a href='/'>Homepage</a>
                <a href='mission.html'>Our Mission</a>
                <a href='page2A.html'>Sub Task 2.A</a>
                <a href='page2B.html'>Sub Task 2.B</a>
                <a href='page3A.html'>Sub Task 3.A</a>
                <a href='page3B.html'>Sub Task 3.B</a>
            </div>
        """;

        // Add header content block
        html = html + """
        <header>
        <img src = "Weblogo.png" width = 100px>
            <div>
                <nav class = 'box1'>
                        <button>Food Loss vs Food Waste</button>
                        <button>News</button>
                        <button>About us</button>
                        <button>Help Centre</button>
                        <button>FAQ's</button> 
                        <br>
                        <br>
                        <button>Research and Development</button>
                        <input type = "text" placeholder = "Search..."> 
                        <button>Search</button>   
                </nav>   
                <br>
                <div>
                    <button>Food Supply Chain</button>
                    <button>Impact on Environment</button>
                    <button>How can we improve?</button>
                    <button>Testimonials</button>
                    <button>Crowd Funding</button>
                </div>
        </header>
    """;

        // Add Div for page Content
    html = html + "<div class='content'>";
    html = html + "<form action='#' method='post'>";

    html = html + "   <div class='form-group'>";
    html = html + "      <label for='country_drop'>Select the Country (Dropdown):</label>";
    html = html + "      <select id='country_drop' name='country_drop'>";
    html = html + "<option> Myanmar </option>";
    html = html + "<option> Burundi </option>";
    html = html + "<option> Western Africa </option>";
    html = html + "      </select>";
    html = html + "   </div>";

    html = html + "   <div class='form-group'>";
    html = html + "      <label for='start_year_drop'>Select the Start Year (Dropdown))</label>";
    html = html + "      <select id='start_year_drop' name='start_year_drop'>";
    html = html + "<option> 1966 </option>";
    html = html + "<option> 1967 </option>";
    html = html + "<option> 1968 </option>";
    html = html + "      </select>";
    html = html + "   </div>";

    html = html + "   <div class='form-group'>";
    html = html + "      <label for='end_year_drop'>Select the End Year (Dropdown))</label>";
    html = html + "      <select id='end_year_drop' name='end_year_drop'>";
    html = html + "<option> 1966 </option>";
    html = html + "<option> 1967 </option>";
    html = html + "<option> 1968 </option>";
    html = html + "      </select>";
    html = html + "   </div>";

    html = html + "   <button type='submit' class='btn btn-primary'>Get Results!</button>";

    String country_drop = context.formParam("country_drop");
    String start_year_drop = context.formParam("start_year_drop");
    String end_year_drop = context.formParam("end_year_drop");
    // String movietype_drop = context.queryParam("movietype_drop");
    if (country_drop == null) {
        // If NULL, nothing to show, therefore we make some "no results" HTML
        html = html + "<h2><i>No Results to show for dropbox</i></h2>";
    } else {
        // If NOT NULL, then lookup the movie by type!
        html = html + outputCountries(country_drop,start_year_drop,end_year_drop);
    }
    // Close Content div
    html = html + "</div>";


        // Add HTML for the page content
        // Close Content div
        //html = html + "</div>";
        // Footer
    html = html + """
        <footer>
            <div class='footer'>
                <div class="footer-content">
                <div class="footer-logo"><img src = "Weblogo.png" width = 100></div>
            <div class="credits">
                <p>Created by :</p>
                <p>Student 1: Name1 and Student Number1</p>
                <p>Student 2: Name2 and Student Number2</p>
            </div>
            <div class="social-media">
                <a href="https://www.facebook.com"><img src = "facebook-icon.png"></a>
                <a href="https://www.twitter.com"><img src = "twitter-icon.png"></a>
                <a href="https://www.instagram.com"><img src = "instagram-icon.png"></a>
            </div>
            </div>
        </footer>
    """;

    // Finish the HTML webpage
    html = html + "</body>" + "</html>";
        

    // DO NOT MODIFY THIS
    // Makes Javalin render the webpage
    context.html(html);
}
    
    
    public String outputCountries(String name,String year,String loss_percentage) {
        String html = "";
        html = html + "<h2>" + name + " Data</h2>";

        // Look up movies from JDBC
        ArrayList<Country> countries = getAllCountries(name,year,loss_percentage);
        
        // Add HTML for the movies list
        html = html + "<ul>";
        for (Country country : countries) {
            html = html + "<li>" + country.loss_percentage +  "," + country.year + "," + "</li>";
        }
        html = html + "</ul>";
        //country.countryName + "," + country.year + "," + 
        
        return html;
    }
    
    public ArrayList<Country> getAllCountries(String name,String year,String loss_percentage) {
        // Create the ArrayList of Country objects to return
        ArrayList<Country> countryData = new ArrayList<Country>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(JDBCConnection.DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT year,loss_percentage FROM FoodLoss where country = country_drop";
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {

                // Create a Country Object
                Country country = new Country();
                // Lookup the columns we need

                //String title = results.getString("m49code");
                //String name  = results.getString("countryName");
                //country.countryName = results.getString("country");
                country.year = results.getString("year");
                country.loss_percentage = results.getString("loss_percentage");

                // Add the Country object to the array
                countryData.add(country);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return all of the countries
        return countryData;
    }
}

    