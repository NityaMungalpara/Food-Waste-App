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
                <a href='page2A.html'>Food Loss Percent by Country</a>
                <a href='page2B.html'>Sub Task 2.B</a>
                <a href='page3A.html'>Similarity Data Analysis by Country</a>
                <a href='page3B.html'>Sub Task 3.B</a>
            </div>
        """;

        // Add header content block
        html = html + """
            <header>
            <img src = "Weblogo.png" width = 100px>
                
            </header>
        """;
        // <div>
        //             <nav class = 'box1'>
        //                     <button>Food Loss vs Food Waste</button>
        //                     <button>News</button>
        //                     <button>About us</button>
        //                     <button>Help Centre</button>
        //                     <button>FAQ's</button> 
        //                     <br>
        //                     <br>
        //                     <button>Research and Development</button>
        //                     <input type = "text" placeholder = "Search..."> 
        //                     <button>Search</button>   
        //             </nav>   
        //             <br>
        //             <div>
        //                 <button>Food Supply Chain</button>
        //                 <button>Impact on Environment</button>
        //                 <button>How can we improve?</button>
        //                 <button>Testimonials</button>
        //                 <button>Crowd Funding</button>
        //             </div>

        // Add Div for page Content
        html = html + "<div class='content'>";
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
            </main>
                """;
         // <section class="join-us">
            //     <h2>Want to Join Us?</h2>
            //     <button>Join Us</button>
            // </section>       

        // Add HTML for the page content
        html = html + "<form action='/mission.html' method='post'>";
        
        html = html + "   <div class='content'>";
        html = html + "      <label for='name_drop'>Select the Persona:</label>";
        html = html + "      <select id='name_drop' name='name_drop'>";
        html = html + "         <option>Ethan Campbell</option>";
        html = html + "         <option>Harry Kesotie</option>";
        html = html + "         <option>Georgia Kane</option>";
        html = html + "      </select>";
        html = html + "   </div>";
        
        html = html + "   <button type='submit' class='btn btn-primary'>Get Data</button>";
        html = html + "</form>";

        String name_drop = context.formParam("name_drop");
        if (name_drop == null) {
            // If NULL, nothing to show, therefore we make some "no results" HTML
            html = html + "<h2><i>No Results to show for dropbox</i></h2>";
        } else {
            // If NOT NULL, then lookup the movie by type!
            html = html + outputPersonas(name_drop);
        }

        // This example uses JDBC to lookup the countries
        //JDBCConnection jdbc = new JDBCConnection();

        // Next we will ask this *class* for the Countries
        //ArrayList<Country> countries = jdbc.getAllCountries();

        // Add HTML for the countries list
        //html = html + "<h1>All Countries in the foodloss database (using JDBC Connection)</h1>" + "<ul>";

        // Finally we can print out all of the Countries
        // for (Country country : countries) {
        //     html = html + "<li>" + country.getM49Code()
        //                 + " - " + country.getName() + "</li>";
        // }

        // Finish the List HTML
        // html = html + "</ul>";
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
                    <p>Student 1: Nitya Mungalpara, Student ID: s4057052</p>
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

    public String outputPersonas(String name) {
        String html = "";
        html = html + "<h2>" + name + " Information</h2>";

        // Look up movies from JDBC
        ArrayList<Persona> personas = getAllPersonas(name);
        
        // Add HTML for the movies list
        html = html + "<ul>";
        for (Persona persona : personas) {
            html = html + "<table class = 'content-table'>";
            html = html + "<tr class = 'heading'>" + "<th>ID</th>" + "<th>Image</th>" + "<th>Name</th>" + "<th>Description</th>" + "<th>Needs</th>" + "<th>Goals</th>" + "</tr>";
            html = html + "<div class = 'contents'>";
            
            html = html + "<td>" + persona.id + "</td>";
            html = html + "<td>" + "<img src = '" + persona.image + ".png" + "'>" + "</td>";
            html = html + "<td>" + persona.name + "</td>";
            html = html + "<td>" + persona.description + "</td>";
            html = html + "<td>" + persona.needs + "</td>";
            html = html + "<td>" + persona.skills + "</td>";
            html = html + "</div>";
            html = html + "</table>";
        }
        html = html + "</ul>";
        
        return html;
    }
    //html = html + "<li>" + persona.id +  "," + persona.name + "," + persona.description + "," + persona.needs + "," + persona.skills + "," + "</li>";


    public ArrayList<Persona> getAllPersonas(String name) {
        // Create the ArrayList of Country objects to return
        ArrayList<Persona> personaInfo = new ArrayList<Persona>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(JDBCConnection.DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT id,imageFile,name,description,needs,skills FROM Persona WHERE name ='" + name + "'";
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {

                // Create a Country Object
                Persona persona = new Persona();
                // Lookup the columns we need

                //String title = results.getString("m49code");
                //String name  = results.getString("countryName");
                //country.countryName = results.getString("country");
                persona.id = results.getString("id");
                persona.image = results.getString("imageFile");
                persona.name = results.getString("name");
                persona.description = results.getString("description");
                persona.needs = results.getString("needs");
                persona.skills = results.getString("skills");

                // Add the Country object to the array
                personaInfo.add(persona);
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
        return personaInfo;
    }

}
