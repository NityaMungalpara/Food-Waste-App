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

public class PageST3A implements Handler {

    // URL of this page relative to http://localhost:7001/
    public static final String URL = "/page3A.html";

    @Override
    public void handle(Context context) throws Exception {
        // Create a simple HTML webpage in a String
        String html = "<html>";

        // Add some Head information
        html = html + "<head>" + 
               "<title>Subtask 3.1</title>";

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

        // Add HTML for the page content
        html = html + """
            <h1>Similarity Data Analysis by Country</h1>
            """;

        // Close Content div
        html = html + "</div>";

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

}
