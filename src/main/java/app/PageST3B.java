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

public class PageST3B implements Handler {

    // URL of this page relative to http://localhost:7001/
    public static final String URL = "/page3B.html";

    @Override
    public void handle(Context context) throws Exception {
        // database
        JDBCConnection jdbc = new JDBCConnection();
        List<String> foodNames = jdbc.getAllFoodName();
        ArrayList<Integer> similarNums = jdbc.getGroupCount();
        
        String html = "<html>";
        html += "<html lang='en'>";
        html += "<head>";
        html +=     "<meta charset='UTF-8'>";
        html +=     "<title>Reference Page</title>";     
        html +=     "<link rel='stylesheet' type='text/css' href='sub3B.css' />";
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

        // // Main section
        html += "<main>";
        html += "  <nav class='breadcrumb'>";
        html += "    <ul>";
        html += "      <li><a href='page2A.html'>Food Loss and Waste Analysis by Country</a></li>";
        html += "      <li>></li>";
        html += "      <li><a href='page2B.html'>Food Loss and Waste Analysis by Group</a></li>";
        html += "      <li>></li>";
        html += "      <li><a href='page3A.html'>Similarity Data Analysis by Country</a></li>";
        html += "      <li>></li>";
        html += "      <li><a href='page3B.html'><b>Similarity Data Analysis by Group</b></a></li>";
        html += "    </ul>";
        html += "  </nav>";
        html += "  <section class='hero'>";
        html += "    <div class='subheading'>";
        html += "      <h1>Similarity Data Analysis by Group</h1>";
        html += "    </div>";
        html += "  </section>";

        // Form section for selecting commodity
        html += "  <section class='filter-section'>";
        html += "    <form method='post' action='" + URL + "'>";
        html += "      <div class='form-group'>";
        html += "        <label for='group'> Food Commodity:</label>";
        html += "        <div class='dropdown-group'>";
        html += "          <details>";
        html += "            <summary>Select Below</summary>";
        html += "            <div class='dropdown-groupMenu' id='group'>";
        for (String name : foodNames) {
            html += "              <label><input type='radio' name='commodity' value='" + name + "'>" + name + "</label>";
        }
        html += "            </div>";
        html += "          </details>";
        html += "        </div>";
        html += "      </div>";

        // similarity criterion filter
        html += "      <div class='form-group'>";
        html += "        <label for='fields'>Similarity Criterion:</label>";
        html += "              <select name='fields'>";
        html += "              <option value='' disabled selected>Select Below</option>";
        html += "                <option value='ratio'>Ratio of Food Loss to Waste</option>";
        html += "                <option value='highest_loss'>Highest Percentage of Food Loss/Waste</option>";
        html += "                <option value='lowest_loss'>Lowest Percentage of Food Loss/Waste</option>";
        html += "              </select>";
        html += "      </div>";

        //similar groups number
        html += "      <div class='form-group'>";
        html += "        <label for='group'>Number of Similar Groups</label>";
        html += "              <select name='commodity'>";
        for (Integer number : similarNums) {
            html += "                <option value='" + number + "'>" + number + "</option>";
        }
        html += "              </select>";
        html += "      </div>";

        // Submit and Reset buttons
        html += "      <div class='form-buttons'>";
        html += "        <button type='submit'>Find Similar Groups</button>";
        html += "        <button type='reset'>Clear All</button>";
        html += "      </div>";
        html += "    </form>";
        html += "  </section>";
        
        // Results section
        html += "  <section class='result-section'>";
        html += "<h2>Results</h2>";


        html += "</main>";
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
