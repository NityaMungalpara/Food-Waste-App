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

public class PageMission implements Handler {

    // URL of this page relative to http://localhost:7001/
    public static final String URL = "/mission.html";

    @Override
    public void handle(Context context) throws Exception {
        // Create a simple HTML webpage in a String
        String html = "<html lang='en'>";

        // Add some Head information
        html = html + "<head>" + 
               "<title>Our Mission</title>";

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

        // This example uses JDBC to lookup the countries
        JDBCConnection jdbc = new JDBCConnection();

        // Next we will ask this *class* for the Countries
        //ArrayList<Country> countries = jdbc.getAllCountries();

        // Add HTML for the countries list
        //html = html + "<h1>All Countries in the foodloss database (using JDBC Connection)</h1>" + "<ul>";

        html = html + """
            <main>
            <section class="purpose">
                <h1>Our Purpose</h1>
                <p>
                    The website mainly focuses to acknowledge people regarding the global issue of food loss and wastage,
                    which occurs<br> at households as well as giant manufacturing industries. The website would provide the 
                    information of the food loss <br> occurring at each level in the food supply chain, contributing to a massive
                    food waste for the entire system. Besides that,<br> it would also give news of current studies and technological 
                    innovations on food loss and waste. The website would also be<br>helpful to people, by informing them about 
                    the devastating impact of food disposal on environment as well as<br> available resources. It would encourage
                    people by publishing success stories of stakeholders on this mission and provide<br> effective strategies,
                    highlighting responsible food management, to combat this social challenge.
                </p>
            
            <section class="join-us">
                <h2>Want to Join Us?</h2>
                <button>Join Us</button>
            </section>
        </main>
                """;
        // Finally we can print out all of the Countries
        // for (Country country : countries) {
        //     html = html + "<li>" + country.getM49Code()
        //                 + " - " + country.getName() + "</li>";
        // }

        // Finish the List HTML
        // html = html + "</ul>";

        html = html + """
            <section class='personas'>
            <table>

                <th>
                <td>Image:</td>
                <td><img src = "Persona1.png" alt = "Persona1"></td>
                </th>
                <br>
                <th>
                    <td>Name:</td>
                    <td>Ethan Campbell</td>
                </th>
                <br>
                <th>
                    <td>Description:</td>
                    <td>
                    Ethan is a first-year undergraduate student majoring in Environmental Science. As a new student,<br>
                        he is excited about learning new knowledge from college. He is very interested in the environmental<br>
                        pollution caused by food loss and waste, so he wants to have some basic information to gain useful<br>
                        knowledge on the matter.
                    </td>
                </th>
                <br>
                <th>
                    <td>Needs And Goals:</td>
                    <td>
                    Ethan is looking for a platform that has useful information and resources related to food loss and waste.<br>
                    He hoped that the headline would give him a quick idea of what the page was about so that he could decide<br> 
                    whether to explore further. He wants to get the information from some graphs or charts rather than searching<br>
                    for specific information. After exploring the website, he would like to have some basic knowledge about food<br>
                    loss and waste and share these resources with his peers to gain knowledge.
                    </td>
                </th>
                <br>
                <th>
                    <td>Skills And Experiences:</td>
                    <td>
                    He has basic computer skills and owns a laptop and mobile phone. He is quite active on social media and<br>
                    has experience on various social platforms. He enjoys learning new things and sharing them with others.
                    </td>
                </th>
            </table>

            <table>

                <th>
                <td>Image:</td>
                <td><img src = "Persona2.png" alt = "Persona2"></td>
                </th>
                <br>
                <th>
                    <td>Name:</td>
                    <td>Harry Kesotie</td>
                </th>
                <br>
                <th>
                    <td>Description:</td>
                    <td>
                    Harry, 29 years old, is a local individual from Australia. He is a food organisation manager and knows<br>
                    that the problem of food waste cannot be ignored. He is also part of a charity group. He plans to distribute<br> 
                    surplus food at household level to those in need.
                    </td>
                </th>
                <br>
                <th>
                    <td>Needs And Goals:</td>
                    <td>
                    He wants an analysis of food waste occurring at the household level in the supply chain. He aims to<br> 
                    reduce food waste at the household level. If the food is not edible, he wants to track the shopping patterns<br> 
                    of consumers that lead to food waste.
                    </td>
                </th>
                <br>
                <th>
                    <td>Skills And Experiences:</td>
                    <td>
                    He has been part of the food organization for 5 years and a charity group for 3 years. He has successfully<br> 
                    made a positive impact on consumers through his speeches. Harry is also well-known for the food donations made by<br> 
                    the charity group.
                    </td>
                </th>
            </table>

            <table>

                <th>
                <td>Image:</td>
                <td><img src = "Persona3.png" alt = "Persona3"></td>
                </th>
                <br>
                <th>
                    <td>Name:</td>
                    <td>Georgia Kane</td>
                </th>
                <br>
                <th>
                    <td>Description:</td>
                    <td>
                    Georgia is originally from Brazil and she is one of many policy makers. She becomes very conscious<br> 
                    when the environment degradation occurs due to food loss and is currently researching how significantly food<br> 
                    dumping leads to environmental degradation, focusing on which greenhouse gases are released and their specific<br>
                    impacts on nature.
                    </td>
                </th>
                <br>
                <th>
                    <td>Needs And Goals:</td>
                    <td>
                    Georgia requires proper statistical data regarding food loss and waste and needs knowledge of the gases<br>
                    released from spoiled and stale food. She also wants to identify other regions that are having similar food<br> 
                    waste changes. She needs accurate information listed up in tables to come up with effective solutions depending<br> 
                    on the intensity of the situation. She also wants to make laws that can handle this situation.
                    </td>
                </th>
                <br>
                <th>
                    <td>Skills And Experiences:</td>
                    <td>
                    Georgia has previously introduced many policies related to food waste that has an impact on environment.<br> 
                    Besides being a successful policy maker, she also considers herself as being responsible for the change in<br> 
                    environment that is taking place. She is famous for her renowned speeches across Brazil, that leave a deep impact<br> 
                    on the audience and encourage them to change in a positive way.
                    </td>
                </th>
            </table>

            
            <section>
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
