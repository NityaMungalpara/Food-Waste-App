package app;

import java.util.ArrayList;
import java.util.List;

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

public class PageST2B implements Handler {

    // URL of this page relative to http://localhost:7001/
    public static final String URL = "/page2B.html";

    @Override
    public void handle(Context context) throws Exception {
        // Create a simple HTML webpage in a String
        String html = "<html>";
        html += "<html lang='en'>";

        // Add some Head information
        html = html + "<head>" + "<meta charset='UTF-8'>";
        html += "<title>Subtask 2.2</title>";     

        // Add some CSS (external file)
        html = html + "<link rel='stylesheet' type='text/css' href='sub2B.css' />";
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
                            <li><a href='/'>Home</a></li>
                            <li><a href='#about'>About Us</a></li>
                            <li><a href='mission.html'>Our Mission</a></li>
                            <li class='dropdown'>
                                <a href='#data'>Data & Resources <span class='arrow'>▼</span></a>
                                <div class='dropdown-content'>
                                    <a href='page2A.html'>Food Loss and Waste Analysis by Country</a>
                                    <a href='page2B.html'>Food Loss and Waste Analysis by Group</a>
                                    <a href='page3A.html'>Similarity Data Analysis by Country</a>
                                    <a href='page3B.html'>Similarity Data Analysis by Group</a>
                                </div>
                            </li>
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
                            </select>
                        </div>
                    </div>
                </div>
            </header>
        """;

        // Main section
        html += """
            <main>
                <nav class='breadcrumb'>
                    <ul>
                        <li><a href='page2A.html'>Food Loss and Waste Analysis by Country</a></li>
                        <li>></li>
                        <li><a href='page2B.html'><b>Food Loss and Waste Analysis by Group</b></a></li>
                        <li>></li>
                        <li><a href='page3A.html'>Similarity Data Analysis by Country</a></li>
                        <li>></li>
                        <li><a href='page3B.html'>Similarity Data Analysis by Group</a></li>
                    </ul>
                </nav>
                <section class='hero'>
                    <div class='subheading'>
                        <h1>Food Loss and Waste Analysis by Group</h1>
                    </div>
                </section>

                <section class="filter-section">
                <form method="post" action="/page2B.html">
                    <div class="form-group">
                        <label for="group">Food Group:</label>
                        <div class="dropdown-group">
                            <details>
                                <summary>Select Below</summary>
                                <div class="dropdown-groupMenu" id="group">
        """;
        // Get food groups from the database
        JDBCConnection jdbc = new JDBCConnection();
        List<String> foodGroups = jdbc.getAllFoodGroups();
        
        // Add checkboxes for food groups
        for (String group : foodGroups) {
            html += "<label><input type='checkbox' name='group' value='" + group + "'> " + group + "</label>";
        }
        html += """
                                
                                </div>
                            </details>
                        </div>
                    </div>
        """;
        // Get years from the database
        ArrayList<Integer> years = jdbc.getAllYears();
    
        // Year selection dropdowns
        html += """
            <div class="form-group">
                <label for="start_year">Yearly Comparison: From</label>
                <select name="start_year" id="start_year">
                    <option value="" disabled selected>Select Start Year</option>
        """;

        for (Integer year : years) {
            html += "<option value='" + year + "'>" + year + "</option>";
        }

        html += """
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="end_year">To</label>
                        <select name="end_year" id="end_year">
                            <option value="" disabled selected>Select End Year</option>
        """;

        for (Integer year : years) {
            html += "<option value='" + year + "'>" + year + "</option>";
        }

        html += """
                    </select>
                </div>
        """;
        html += """
                    <div class="form-group">
                        <label for="fields">Filter:</label>
                        <div class="multiselect">
                            <label><input type="checkbox" name="fields" value="activity"> Activity</label>
                            <label><input type="checkbox" name="fields" value="food_supply_stage"> Food Supply Stage</label>
                            <label><input type="checkbox" name="fields" value="cause_of_loss"> Cause of Loss</label>
                        </div>
                    </div>
    
                    <div class="form-group">
                        <label for="sort_order">Sort By:</label>
                        <select name="sort_order" id="sort_order">
                            <option value="asc">Ascending</option>
                            <option value="desc">Descending</option>
                        </select>
                    </div>
    
                    <div class="form-buttons">
                        <button type="submit">Search</button>
                        <button type="reset">Clear All</button>
                    </div>
                </form>
            </section>
    
            <section class="result-section">
                <h2>Results</h2>
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
                        <a href='https://x.com'><img src='twitter-icon.png' alt='Twitter'></a>
                        <a href='https://www.facebook.com/'><img src='facebook-icon.png' alt='Facebook'></a>
                        <a href='https://www.linkedin.com/'><img src='linkedin-icon.png' alt='LinkedIn'></a>
                        <a href='https://www.instagram.com/'><img src='instagram-icon.png' alt='Instagram'></a>
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

}
