package app;

import io.javalin.http.Context;
import io.javalin.http.Handler;

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

public class PageReference implements Handler {

    // URL of this page relative to http://localhost:7001/
    public static final String URL = "/Reference.html";
    @Override
    public void handle(Context context) throws Exception {
        String html = "<html>";
        html += "<html lang='en'>";
        html += "<head>";
        html +=     "<meta charset='UTF-8'>";
        html +=     "<title>Reference Page</title>";     
        html +=     "<link rel='stylesheet' type='text/css' href='reference.css' />";
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
        html += "  <section class='hero'>";
        html += "    <div class='subheading'>";
        html += "      <h1>References</h1>";
        html += "    </div>";
        html += "  </section>";

        // Add the images and descriptions
        html += "<div class='image_container'>";
        html += "    <img src='FoodWaste.jpg' alt='Background for the landing page' width='150'>";
        html += "    <p>This image was used as the background for the landing page.<br>";
        html += "    <br>\n";
        html += "    National Resource Consortium. (2024). <i>How Does Recycling Food Waste Benefit My Business?</i> [Image]. Available at: <a href='https://uk-nrc.com/blog/how-recycling-food-waste-benefits-business/' target='_blank'>https://uk-nrc.com/blog/how-recycling-food-waste-benefits-business/</a> (Accessed: 25 May. 2024).</p>";
        html += "</div>";
        html += "<div class='image_container'>";
        html += "    <img src='Weblogo.png' alt='Website logo' width='150'>";
        html += "    <p>This image was used as the website logo.<br>";
        html += "    <br>\n";
        html += "    Depositphotos. (n.d.). <i>ZN logo</i> [Image]. Available at: <a href='https://depositphotos.com/vectors/zn-logo.html' target='_blank'>https://depositphotos.com/vectors/zn-logo.html</a> (Accessed: 25 May. 2024).</p>";
        html += "</div>";
        
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
