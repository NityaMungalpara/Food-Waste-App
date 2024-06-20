//Original Code
package app;

import java.util.ArrayList;
import java.util.List;

import app.PageMission.Student;
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
                </div>
            </header>
        """;
        // Add the topnav
        // This uses a Java v15+ Text Block
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

        // Add header content block
    
    html = html + "<h1> Food Loss Percent by Country</h1>";

        // Add Div for page Content
    html = html + """
        <section class='dropdowns'>
            <form action='/page2A.html' method='post'>
                <div class='form-group'>
                    <label for='country_drop'>Select the Country(s):</label>
                    <div>
                        <input type='checkbox' name='country_drop' value='Africa'>Africa<br>
                        <input type='checkbox' name='country_drop' value='Algeria'>Algeria<br>
                        <input type='checkbox' name='country_drop' value='Angola'>Angola
                        <input type='checkbox' name='country_drop' value='Argentina'>Argentina
                        <input type='checkbox' name='country_drop' value='Argmenia'>Armenia
                        <input type='checkbox' name='country_drop' value='Australia'>Australia
                        <input type='checkbox' name='country_drop' value='Australia and New Zealend'>Australia and New Zealand
                        <input type='checkbox' name='country_drop' value='Azerbaijan'>Azerbaijan
                        <input type='checkbox' name='country_drop' value='Bahrain'>Bahrain
                        <input type='checkbox' name='country_drop' value='Bangladesh'>Bangladesh
                        <input type='checkbox' name='country_drop' value='Benin'>Benin
                        <input type='checkbox' name='country_drop' value='Bolivia (Plurinational State of)'>Bolivia (Plurinational State of)
                        <input type='checkbox' name='country_drop' value='Botswana'>Botswana
                        <input type='checkbox' name='country_drop' value='Brazil'>Brazil
                        <input type='checkbox' name='country_drop' value='Burkina Faso'>Burkina Faso
                        <input type='checkbox' name='country_drop' value='Burundi'>Burundi
                        <input type='checkbox' name='country_drop' value='Cote d'Ivoire'>Cote d'Ivoire
                    </div>
                </div>
                <div class='form-group'>
                    <label for='start_year_drop'>Select the Start Year:</label>
                    <select id='start_year_drop' name='start_year_drop'>
                        <option>1966</option>
                        <option>1967</option>
                        <option>1968</option>
                        <option>1969</option>
                        <option>1970</option>
                        <option>1971</option>
                        <option>1972</option>
                        <option>1973</option>
                        <option>1974</option>
                        <option>1975</option>
                        <option>1976</option>
                        <option>1977</option>
                        <option>1978</option>
                        <option>1979</option>
                        <option>1980</option>
                        <option>1981</option>
                        <option>1982</option>
                        <option>1983</option>
                        <option>1984</option>
                        <option>1985</option>
                        <option>1986</option>
                        <option>1987</option>
                        <option>1988</option>
                        <option>1989</option>
                        <option>1990</option>
                        <option>1991</option>
                        <option>1992</option>
                        <option>1993</option>
                        <option>1994</option>
                        <option>1995</option>
                        <option>1996</option>
                        <option>1997</option>
                        <option>1998</option>
                        <option>1999</option>
                        <option>2000</option>
                        <option>2001</option>
                        <option>2002</option>
                        <option>2003</option>
                        <option>2004</option>
                        <option>2005</option>
                        <option>2006</option>
                        <option>2007</option>
                        <option>2008</option>
                        <option>2009</option>
                        <option>2010</option>
                        <option>2011</option>
                        <option>2012</option>
                        <option>2013</option>
                        <option>2014</option>
                        <option>2015</option>
                        <option>2016</option>
                        <option>2017</option>
                        <option>2018</option>
                        <option>2019</option>
                        <option>2020</option>
                        <option>2021</option>
                        <option>2022</option>
                        <option>2023</option>
                        <option>2024</option>
                    </select>
                </div>
                <div class='form-group'>
                    <label for='end_year_drop'>Select the End Year:</label>
                    <select id='end_year_drop' name='end_year_drop'>
                        <option>1966</option>
                        <option>1967</option>
                        <option>1968</option>
                        <option>1969</option>
                        <option>1970</option>
                        <option>1971</option>
                        <option>1972</option>
                        <option>1973</option>
                        <option>1974</option>
                        <option>1975</option>
                        <option>1976</option>
                        <option>1977</option>
                        <option>1978</option>
                        <option>1979</option>
                        <option>1980</option>
                        <option>1981</option>
                        <option>1982</option>
                        <option>1983</option>
                        <option>1984</option>
                        <option>1985</option>
                        <option>1986</option>
                        <option>1987</option>
                        <option>1988</option>
                        <option>1989</option>
                        <option>1990</option>
                        <option>1991</option>
                        <option>1992</option>
                        <option>1993</option>
                        <option>1994</option>
                        <option>1995</option>
                        <option>1996</option>
                        <option>1997</option>
                        <option>1998</option>
                        <option>1999</option>
                        <option>2000</option>
                        <option>2001</option>
                        <option>2002</option>
                        <option>2003</option>
                        <option>2004</option>
                        <option>2005</option>
                        <option>2006</option>
                        <option>2007</option>
                        <option>2008</option>
                        <option>2009</option>
                        <option>2010</option>
                        <option>2011</option>
                        <option>2012</option>
                        <option>2013</option>
                        <option>2014</option>
                        <option>2015</option>
                        <option>2016</option>
                        <option>2017</option>
                        <option>2018</option>
                        <option>2019</option>
                        <option>2020</option>
                        <option>2021</option>
                        <option>2022</option>
                        <option>2023</option>
                        <option>2024</option>
                    </select>
                </div>
                <div class='form-group'>
                    <label for='filter_drop'>Select the Filter(s):</label>
                    <div>
                        <input type='checkbox' name='filter_drop' value='Activity'>Activity<br>
                        <input type='checkbox' name='filter_drop' value='Food Supply Stage'>Food Supply Stage<br>
                        <input type='checkbox' name='filter_drop' value='Cause of Loss'>Cause of Loss<br>
                    </div>
                </div>
                <div class='form-group'>
                    <label for='order_drop'>Select the Order:</label>
                    <select id='order_drop' name='order_drop'>
                        <option>Ascending</option>
                        <option>Descending</option>
                    </select>
                </div>
            <section>
                <button type='submit' class='btn btn-primary'>Get Data</button>
            </form>
        """;
            

    List<String> country_drop_list = context.formParams("country_drop");
    String start_year_drop = context.formParam("start_year_drop");
    String end_year_drop = context.formParam("end_year_drop");
    // String movietype_drop = context.queryParam("movietype_drop");
    if (country_drop_list == null || start_year_drop == null || end_year_drop == null) {
        // If NULL, nothing to show, therefore we make some "no results" HTML
        html = html + "<h2><i>No Results to show for dropbox</i></h2>";
    } else {
        // If NOT NULL, Convert to Array
        String[] country_drop_array = country_drop_list.toArray(new String[0]);
        html = html + outputCountries(country_drop_array,start_year_drop,end_year_drop);
    }

    // Close Content div
    html = html + "</div>";


        // Add HTML for the page content
        // Close Content div
        //html = html + "</div>";
        // Footer
        html = html + """
            <footer>
                <div class='footer-content'>
                    <div class="footer-logo"><a href='/'><img src = "Weblogo.png" width = 60></a></div>
                </div>
            </footer> """;

        html = html + """
            <footer>
                    <div class="social-media">
                    <a href="https://www.twitter.com"><img src = "twitter-icon.png" alt='Twitter' width = 30></a>
                    <a href="https://www.facebook.com"><img src = "facebook-icon.png" alt='Facebook' width = 30></a>
                    <a href='https://www.linkedin.com/'><img src='linkedin-icon.png' alt='LinkedIn' width = 30></a>
                    <a href="https://www.instagram.com"><img src = "instagram-icon.png" alt='Instagram' width = 30></a>
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
    
    
    public String outputCountries(String[] countries,String startYear,String endYear) {
        String html = "";
        html = html + "<h2>Data from " + startYear + " to " + endYear + "</h2>";

        // Look up movies from JDBC
        List<String> lossPercentages = getLossPercentages(countries,startYear,endYear);
        //ArrayList<Country> countries = getAllCountries(name,startYear);

        // Add HTML for the sum of loss percentages by year and group
        
        // Add HTML for the movies list
        html = html + "<table class = 'content-table' border = '1'>";
        html = html + "<tr class = 'heading'><th>Country</th><th>Start Year</th><th>Start Year Loss Percentage</th><th>End Year</th><th>End Year Loss Percentage</th><th>Loss Percent Difference</th><th>Activity</th><th>Food Supply Stage</th><th>Cause Of Loss</th></tr>";

        for (int i = 0; i < lossPercentages.size(); i += 6) {
            String country = lossPercentages.get(i);
            String loss_start_year = lossPercentages.get(i + 1);
            String loss_end_year = lossPercentages.get(i + 2);
            String activity = lossPercentages.get(i+3);
            String food_supply_stage = lossPercentages.get(i+4);
            String cause_of_loss = lossPercentages.get(i+5);
            double loss_difference = Double.parseDouble(loss_start_year) - Double.parseDouble(loss_end_year);


            html = html + "<tr>";
            html = html + "<td>" + country + "</td>";
            html = html + "<td>" + startYear + "</td>";
            html = html + "<td>" + loss_start_year + "</td>";
            html = html + "<td>" + endYear + "</td>";
            html = html + "<td>" + loss_end_year + "</td>";
            html = html + "<td>" + loss_difference + "</td>";
            html = html + "<td>" + activity + "</td>";
            html = html + "<td>" + food_supply_stage + "</td>";
            html = html + "<td>" + cause_of_loss + "</td>";
            html = html + "</tr>";
        } 

        html = html + "</table>"; 
        
        return html;
    }
    
    public List<String> getLossPercentages(String[] countries, String startYear, String endYear) {
        List<String> countryData = new ArrayList<>();
    
        Connection connection = null;
    
        try {
            connection = DriverManager.getConnection(JDBCConnection.DATABASE);
    
            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
    
            // Construct the SQL query with proper spacing and aliases
            String countriesTogether = "'" + String.join("','", countries) + "'";
            String query = "SELECT t1.country, t1.loss1 AS loss_start_year, t2.loss2 AS loss_end_year,t1.activity,t1.food_supply_stage,t1.cause_of_loss " +
                           "FROM " +
                           "(SELECT SUM(loss_percentage) AS loss1, country,activity,food_supply_stage,cause_of_loss " +
                           " FROM food_loss " +
                           " WHERE country IN (" + countriesTogether + ") " +
                           " AND year = '" + startYear + "' " +
                           " GROUP BY country,year) t1 " +
                           "JOIN " +
                           "(SELECT SUM(loss_percentage) AS loss2, country " +
                           " FROM food_loss " +
                           " WHERE country IN (" + countriesTogether + ") " +
                           " AND year = '" + endYear + "' " +
                           " GROUP BY country,year) t2 ON t1.country = t2.country";
                           //+ " ORDER BY country";
            //            
            // Execute the query
            ResultSet results = statement.executeQuery(query);

            while (results.next()) {
                String country = results.getString("country");
                String loss_start_year = results.getString("loss_start_year");
                String loss_end_year = results.getString("loss_end_year");
                String activity = results.getString("activity"); // Retrieve activity
                String food_supply_stage = results.getString("food_supply_stage"); // Retrieve food_supply_stage
                String cause_of_loss = results.getString("cause_of_loss"); // Retrieve cause_of_loss
            
                // Add country, loss percentages, and additional fields to the list
                countryData.add(country);
                countryData.add(loss_start_year);
                countryData.add(loss_end_year);
                countryData.add(activity);
                countryData.add(food_supply_stage);
                countryData.add(cause_of_loss);
            }
    
            // Close the statement and connection
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

}
