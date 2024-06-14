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
        html = html + "<link rel='stylesheet' type='text/css' href='sub2A.css' />";
        html = html + "</head>";

        // Add the body
        html = html + "<body>";

        html += """
            <header>
                <div class='container'>
                    <div class='Weblogo'>
                        <img src='Weblogo.png' alt='Logo'>
                    </div>
                    <nav>
                        <ul class='nav-links'>
                            <li><a href='/'>Home</a></li>                            
                            <li><a href='mission.html'>Our Mission</a></li>
                            <li class='dropdown'>
                                <a href='#data'>Data & Resources <span class='arrow'>&#x25BC</span></a>
                                <div class='dropdown-content'>
                                    <a href='page2A.html'>Food Loss and Waste Analysis by Country</a>
                                    <a href='page2B.html'>Food Loss and Waste Analysis by Group</a>
                                    <a href='page3A.html'>Similarity Data Analysis by Country</a>
                                    <a href='page3B.html'>Similarity Data Analysis by Group</a>
                                </div>
                            </li>
                            <li><a href='Reference.html'>Reference</a></li> 
                        </ul>
                    </nav>
                    <div class='nav-right'>
                        <div class='search-container'>
                            <input type='text' placeholder='Search'>
                            <button>Search</button>
                        </div>
                        <button class='help-center'>Help Center</button>
                        <div class='language-selector'>
                            <select>
                                <option value='en'>English</option>
                                <option value='cn'>Chinese</option>
                            </select>
                        </div>
                    </div>
                </div>
            </header>
        """;

        // Add the topnav
        // This uses a Java v15+ Text Block
        html += "<main>";
        html += "  <nav class='breadcrumb'>";
        html += "    <ul>";
        html += "      <li><a href='page2A.html'>Food Loss and Waste Analysis by Country</a></li>";
        html += "      <li>></li>";
        html += "      <li><a href='page2B.html'><b>Food Loss and Waste Analysis by Group</b></a></li>";
        html += "      <li>></li>";
        html += "      <li><a href='page3A.html'>Similarity Data Analysis by Country</a></li>";
        html += "      <li>></li>";
        html += "      <li><a href='page3B.html'>Similarity Data Analysis by Group</a></li>";
        html += "    </ul>";
        html += "  </nav>";

        // Add header content block
    
    html = html + "<h1> Food Loss Percent by Country</h1>";

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
    html = html + "<option> 2001 </option>";
    html = html + "<option> 2002 </option>";
    html = html + "<option> 2003 </option>";
    html = html + "      </select>";
    html = html + "   </div>";

    html = html + "   <div class='form-group'>";
    html = html + "      <label for='end_year_drop'>Select the End Year (Dropdown))</label>";
    html = html + "      <select id='end_year_drop' name='end_year_drop'>";
    html = html + "<option> 2001 </option>";
    html = html + "<option> 2002 </option>";
    html = html + "<option> 2003 </option>";
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
        html = html + outputCountries1(country_drop,start_year_drop);
    }

    if (country_drop == null) {
        // If NULL, nothing to show, therefore we make some "no results" HTML
        html = html + "<h2><i>No Results to show for dropbox</i></h2>";
    } else {
        // If NOT NULL, then lookup the movie by type!
        html = html + outputCountries2(country_drop,end_year_drop);
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
    
    
    public String outputCountries1(String name,String startYear) {
        String html = "";
        html = html + "<h2>" + name + " Data</h2>";

        // Look up movies from JDBC
        ArrayList<Country> countries = getAllCountries1(name,startYear);
        
        // Add HTML for the movies list
        html = html + "<table class = 'content-table' border = '10'>";
        html = html + "<tr class = 'heading'><th>Country</th><th>Year</th><th>Loss Percentage</th></tr>";

        for (Country country : countries) {
            html = html + "<tr>";
            html = html + "<td>" + country.name + "</td>";
            html = html + "<td>" + country.year + "</td>";
            html = html + "<td>" + country.loss_percentage + "</td>";
            html = html + "</tr>";
        }

        html = html + "</table>"; 
        //country.countryName + "," + country.year + "," + 
        
        return html;
    }

    public String outputCountries2(String name,String endYear) {
        String html = "";
        html = html + "<h2>" + name + " Data</h2>";

        // Look up movies from JDBC
        ArrayList<Country> countries = getAllCountries2(name,endYear);
        
        // Add HTML for the movies list
        html = html + "<table class = 'content-table' border = '10'>";
        html = html + "<tr class = 'heading'><th>Country</th><th>Year</th><th>Loss Percentage</th></tr>";

        for (Country country : countries) {
            html = html + "<tr>";
            html = html + "<td>" + country.name + "</td>";
            html = html + "<td>" + country.year + "</td>";
            html = html + "<td>" + country.loss_percentage + "</td>";
            html = html + "</tr>";
        }

        html = html + "</table>"; 
        
        return html;
    }
    
    public ArrayList<Country> getAllCountries1(String name,String startYear) {
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
            String query = "SELECT country,year,loss_percentage FROM food_loss WHERE country ='" + name + "' AND year ='" + startYear + "'";
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {

                // Create a Country Object
                Country country = new Country();
                // Lookup the columns we need

                //String title = results.getString("m49code");
                //String name  = results.getString("countryName");
                country.name = results.getString("country");
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

    public ArrayList<Country> getAllCountries2(String name,String endYear) {
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
            String query = "SELECT country,year,loss_percentage FROM food_loss WHERE country ='" + name + "' AND year ='" + endYear + "'";
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {

                // Create a Country Object
                Country country = new Country();
                // Lookup the columns we need

                //String title = results.getString("m49code");
                //String name  = results.getString("countryName");
                country.name = results.getString("country");
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

    