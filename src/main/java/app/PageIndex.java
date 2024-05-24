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

public class PageIndex implements Handler {

    // URL of this page relative to http://localhost:7001/
    public static final String URL = "/";
    @Override
    public void handle(Context context) throws Exception {
        // Create a simple HTML webpage in a String
        String html = "<!DOCTYPE html>";
        html += "<html lang='en'>";

        // Add some Header information
        html += "<head>" + "<meta charset='UTF-8'>";
        html += "<title>Homepage</title>";

        // Add some CSS (external file)
        html = html + "<link rel='stylesheet' type='text/css' href='PageIndex.css' />";
        html = html + "</head>";

        // Add the body
        html = html + "<body>";

        // Add header content block
        html += """
            <header>
                <div class='container'>
                    <div class='Weblogo'>
                        <img src='Weblogo.png' alt='Logo'>
                    </div>
                    <nav>
                        <ul class='nav-links'>
                            <li><a href='#home'>Home</a></li>
                            <li><a href='#about'>About Us</a></li>
                            <li class='dropdown'>
                                <a href='#data'>Data & Resources <span class='arrow'>▼</span></a>
                                <div class='dropdown-content'>
                                    <a href='#'>Food Loss and Waste Analysis by Country</a>
                                    <a href='#'>Food Loss and Waste Analysis by Group</a>
                                    <a href='#'>Similarity Data Analysis by Country</a>
                                    <a href='#'>Similarity Data Analysis by Group</a>
                                </div>
                            </li>
                            <li><a href='#news'>News</a></li>
                            <li><a href='#involved'>Get Involved</a></li>
                            <li><a href='#faqs'>FAQs</a></li>
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
                                //Add languages
                            </select>
                        </div>
                    </div>
                </div>
            </header>
        """;
    // Main section
        html += """
            <main>
                <section class='hero'>
                    <div class='container'>
                        <h1>Less Waste, Better World</h1>
                        <div class='highlight'>
                            <p>Join us in exploring the crucial data behind food loss and waste.</p>
                            <p>Let's work together towards a more sustainable future.</p>
                            <button>Get Started</button>
                        </div>
                    </div>
                </section>

                <section class='overview'>
                    <div class='overview-header'>
                        <h2>Global Food Loss Overview: 1966-2022</h2>
                        <h3>Analysing Loss Percentages of Various Commodities Over the Years</h3>
                    </div>
                    <div class='content-wrapper'>
                        <div class='text-box'>
                            <p>The highest single-year food loss recorded was <b>65%</b>. This significant loss occurred for:</p>
                            <ul>
                                <li>Cowpeas, dry in 1974</li>
                                <li>Strawberries in 2001</li>
                                <li>Cauliflowers and Broccoli in 2013</li>
                            </ul>
                            <p>Food loss affects us all. Understanding the data is the first step towards reducing waste.</p>
                            <button class='learn-more'>Learn More</button>
                        </div>
                        <div class='chart-box'>
                            <img src='chart.png' alt='Loss Percentage Chart'>
                        </div>
                    </div>
                </section>
            </main>
        """;
    // Footer section
        html += """
            <footer>
                <div class='footer-container'>
                    <div class='footer-links'>
                        <a href='#about'>About</a>
                        <a href='#contact'>Contact Us</a>
                        <a href='#faqs'>FAQs</a>
                        <a href='#privacy'>Privacy Policy</a>
                        <a href='#terms'>Terms of Use</a>
                    </div>
                    <div class='footer-social'>
                        <a href='#'><img src='twitter-icon.png' alt='Twitter'></a>
                        <a href='#'><img src='facebook-icon.png' alt='Facebook'></a>
                        <a href='#'><img src='linkedin-icon.png' alt='LinkedIn'></a>
                        <a href='#'><img src='instagram-icon.png' alt='Instagram'></a>
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

    // @Override
    // public void handle(Context context) throws Exception {
    //     // Create a simple HTML webpage in a String
    //     String html = "<html>";

    //     // Add some Header information
    //     html = html + "<head>" + 
    //            "<title>Homepage</title>";

    //     // Add some CSS (external file)
    //     html = html + "<link rel='stylesheet' type='text/css' href='common.css' />";
    //     html = html + "</head>";

    //     // Add the body
    //     html = html + "<body>";

    //     // Add the topnav
    //     // This uses a Java v15+ Text Block
    //     html = html + """
    //         <div class='topnav'>
    //             <a href='/'>Homepage</a>
    //             <a href='mission.html'>Our Mission</a>
    //             <a href='page2A.html'>Sub Task 2.A</a>
    //             <a href='page2B.html'>Sub Task 2.B</a>
    //             <a href='page3A.html'>Sub Task 3.A</a>
    //             <a href='page3B.html'>Sub Task 3.B</a>
    //         </div>
    //     """;

    //     // Add header content block
    //     html = html + """
    //         <div class='header'>
    //             <h1>
    //                 <img src='logo.png' class='top-image' alt='RMIT logo' height='75'>
    //                 Homepage
    //             </h1>
    //         </div>
    //     """;

    //     // Add Div for page Content
    //     html = html + "<div class='content'>";

    //     // Add HTML for the page content
    //     html = html + """
    //         <p>Homepage content</p>
    //         """;

    //     // Get the ArrayList of Strings of all countries
    //     ArrayList<String> countryNames = getAllCountries();

    //     // Add HTML for the country list
    //     html = html + "<h1>All Countries in the food loss database</h1>" + "<ul>";

    //     // Finally we can print out all of the countries
    //     for (String name : countryNames) {
    //         html = html + "<li>" + name + "</li>";
    //     }

    //     // Finish the List HTML
    //     html = html + "</ul>";

    //     // Close Content div
    //     html = html + "</div>";

    //     // Footer
    //     html = html + """
    //         <div class='footer'>
    //             <p>COSC2803 - Studio Project Starter Code (Apr24)</p>
    //         </div>
    //     """;

    //     // Finish the HTML webpage
    //     html = html + "</body>" + "</html>";


    //     // DO NOT MODIFY THIS
    //     // Makes Javalin render the webpage
    //     context.html(html);
    // }


    /**
     * Get the names of the countries in the database.
     */
    // public ArrayList<String> getAllCountries() {
    //     // Create the ArrayList of String objects to return
    //     ArrayList<String> countries = new ArrayList<String>();

    //     // Setup the variable for the JDBC connection
    //     Connection connection = null;

    //     try {
    //         // Connect to JDBC data base
    //         connection = DriverManager.getConnection(JDBCConnection.DATABASE);

    //         // Prepare a new SQL Query & Set a timeout
    //         Statement statement = connection.createStatement();
    //         statement.setQueryTimeout(30);

    //         // The Query
    //         String query = "SELECT * FROM country";
            
    //         // Get Result
    //         ResultSet results = statement.executeQuery(query);

    //         // Process all of the results
    //         while (results.next()) {
    //             String countryName  = results.getString("countryName");

    //             // Add the country object to the array
    //             countries.add(countryName);
    //         }

    //         // Close the statement because we are done with it
    //         statement.close();
    //     } catch (SQLException e) {
    //         // If there is an error, lets just print the error
    //         System.err.println(e.getMessage());
    //         //e.printStackTrace();
    //     } finally {
    //         // Safety code to cleanup
    //         try {
    //             if (connection != null) {
    //                 connection.close();
    //             }
    //         } catch (SQLException e) {
    //             // connection close failed.
    //             System.err.println(e.getMessage());
    //             //e.printStackTrace();
    //         }
    //     }

    //     // Finally we return all of the countries
    //     return countries;
    // }
}
