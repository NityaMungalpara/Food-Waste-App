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
        // database
        JDBCConnection jdbc = new JDBCConnection();
        List<String> foodGroups = jdbc.getAllFoodGroups();
        ArrayList<Integer> years = jdbc.getAllYears();

        // Create a simple HTML webpage in a String
        String html = "<html>";
        html += "<html lang='en'>";
        html += "<head>";
        html +=     "<meta charset='UTF-8'>";
        html +=     "<title>Subtask 2.2</title>";     
        html +=     "<link rel='stylesheet' type='text/css' href='sub2B.css' />";
        html += "</head>";

        // Add the body
        html = html + "<body>";

        // Header
        html += "<header>";
        html += "  <div class='container'>";
        html += "    <div class='Weblogo'><img src='Weblogo.png' alt='Logo'></div>";
        html += "    <nav>";
        html += "      <ul class='nav-links'>";
        html += "        <li><a href='/'>Home</a></li>";
        html += "        <li><a href='#about'>About Us</a></li>";
        html += "        <li><a href='mission.html'>Our Mission</a></li>";
        html += "        <li class='dropdown'>";
        html += "          <a href='#data'>Data & Resources <span class='arrow'>▼</span></a>";
        html += "          <div class='dropdown-content'>";
        html += "            <a href='page2A.html'>Food Loss and Waste Analysis by Country</a>";
        html += "            <a href='page2B.html'>Food Loss and Waste Analysis by Group</a>";
        html += "            <a href='page3A.html'>Similarity Data Analysis by Country</a>";
        html += "            <a href='page3B.html'>Similarity Data Analysis by Group</a>";
        html += "          </div>";
        html += "        </li>";
        html += "        <li><a href='/Reference.html'>Reference</a></li>";
        html += "      </ul>";
        html += "    </nav>";
        html += "    <div class='nav-right'>";
        html += "      <div class='search-container'><input type='text' placeholder='Search'><button>Search</button></div>";
        html += "      <button class='help-center'>Help Center</button>";
        html += "      <div class='language-selector'>";
        html += "        <select>";
        html += "          <option value='en'>English</option>";
        html += "          <option value='cn'>Chinese</option>";
        html += "        </select>";
        html += "      </div>";
        html += "    </div>";
        html += "  </div>";
        html += "</header>";

        // Main section
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
        html += "  <section class='hero'>";
        html += "    <div class='subheading'>";
        html += "      <h1>Food Loss and Waste Analysis by Group</h1>";
        html += "    </div>";
        html += "  </section>";

        // Form section for selecting food groups
        html += "  <section class='filter-section'>";
        html += "    <form method='post' action='" + URL + "'>";
        html += "      <div class='form-group'>";
        html += "        <label for='group'>Food Group:</label>";
        html += "        <div class='dropdown-group'>";
        html += "          <details>";
        html += "            <summary>Select Below</summary>";
        html += "            <div class='dropdown-groupMenu' id='group'>";
        for (String group : foodGroups) {
            html += "              <label><input type='checkbox' name='group' value='" + group + "'>" + group + "</label>";
        }
        html += "            </div>";
        html += "          </details>";
        html += "        </div>";
        html += "      </div>";
                                
        // Year dropdowns
        html += "      <div class='form-group'>";
        html += "        <label for='start_year'>Yearly Comparison: From</label>";
        html += "        <select name='start_year' id='start_year'>";
        html += "          <option value='' disabled selected>Select Start Year</option>";
        for (Integer year : years) {
            html += "          <option value='" + year + "'>" + year + "</option>";
        }
        html += "        </select>";
        html += "      </div>";
        html += "      <div class='form-group'>";
        html += "        <label for='end_year'>To</label>";
        html += "        <select name='end_year' id='end_year'>";
        html += "          <option value='' disabled selected>Select End Year</option>";
        for (Integer year : years) {
            html += "          <option value='" + year + "'>" + year + "</option>";
        }
        html += "        </select>";
        html += "      </div>";

        // Additional filters
        html += "      <div class='form-group'>";
        html += "        <label for='fields'>Filter:</label>";
        html += "        <div class='multiselect'>";
        html += "          <label><input type='checkbox' name='fields' value='activity'> Activity</label>";
        html += "          <label><input type='checkbox' name='fields' value='food_supply_stage'> Food Supply Stage</label>";
        html += "          <label><input type='checkbox' name='fields' value='cause_of_loss'> Cause of Loss</label>";
        html += "        </div>";
        html += "      </div>";

        // Sort order
        html += "      <div class='form-group'>";
        html += "        <label for='sort_order'>Sort By:</label>";
        html += "        <select name='sort_order' id='sort_order'>";
        html += "          <option value='asc'>Ascending</option>";
        html += "          <option value='desc'>Descending</option>";
        html += "        </select>";
        html += "      </div>";

        // Submit and Reset buttons
        html += "      <div class='form-buttons'>";
        html += "        <button type='submit'>Search</button>";
        html += "        <button type='reset'>Clear All</button>";
        html += "      </div>";
        html += "    </form>";
        html += "  </section>";

        // Results section
        html += "  <section class='result-section'>";
        html += "    <h2>Results</h2>";
        // Results
        html += "  </section>";

        // Footer
        html += "<footer>";
        html += "  <div class='footer-container'>";
        html += "    <div class='footer-links'>";
        html += "      COSC2803 - Studio Project Starter Code (Apr24)";
        html += "      <a href='#about'>About</a>";
        html += "      <a href='#contact'>Contact Us</a>";
        html += "      <a href='#faqs'>FAQs</a>";
        html += "      <a href='#privacy'>Privacy Policy</a>";
        html += "      <a href='#terms'>Terms of Use</a>";
        html += "    </div>";
        html += "    <div class='footer-social'>";
        html += "      <a href='https://x.com'><img src='twitter-icon.png' alt='Twitter'></a>";
        html += "      <a href='https://www.facebook.com/'><img src='facebook-icon.png' alt='Facebook'></a>";
        html += "      <a href='https://www.linkedin.com/'><img src='linkedin-icon.png' alt='LinkedIn'></a>";
        html += "      <a href='https://www.instagram.com/'><img src='instagram-icon.png' alt='Instagram'></a>";
        html += "    </div>";
        html += "  </div>";
        html += "</footer>";

        // Finish the HTML webpage
        html = html + "</body>" + "</html>";
        

        // DO NOT MODIFY THIS
        // Makes Javalin render the webpage
        context.html(html);
    }

}
