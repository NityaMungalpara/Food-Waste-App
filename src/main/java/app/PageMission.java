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
        html += "<head>" + "<meta charset='UTF-8'>";
        html += "<title>Homepage</title>";
        html = html + "<link rel='stylesheet' type='text/css' href='sub1B.css' />";
        html = html + "</head>";
        html = html + "<body>";

        // Add the body
        html = html + "<body>";

        // Add the topnav
        // This uses a Java v15+ Text Block
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
                                <a href='#data'>Data & Resources <span class='arrow'>▼</span></a>
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
                    </div>
                </div>
            </header>
        """;
    //     <div class='search-container'>
    //     <input type='text' placeholder='Search'>
    //     <button>Search</button>
    // </div>
    // <button class='help-center'>Help Center</button>
    // <div class='language-selector'>
    //     <select>
    //         <option value='en'>English</option>
    //         <option value='cn'>Chinese</option>
    //     </select>
    // </div>
        // Add header content block

        // Add Div for page Content
        html = html + "<div class='content'>";
        html = html + """
            <main>
            <section class = 'purpose'>
                <h1>Our Purpose</h1>
                    The website mainly focuses to acknowledge people regarding<br> the global issue of food loss and wastage,
                    which occurs<br> throughout the world. The website would provide the 
                    information of the food<br> loss percents occurring in different countries based on a given start year<br> and end year
                    .Besides that, based on a given country,<br> it would find other countries that have common food types being wasted<br> 
                    and similar overall food loss percent. The website <br>would also be helpful to people, by informing them about<br>
                    the devastating impact of food disposal on environment as well as<br> available resources. It would encourage
                    people by providing the required information<br> using tables, so that they can easily access the data. The website
                    would<br> provide information in an engaging manner so that people do not feel bored.
                <h1>Features of the Website</h1>
                
                 <li>Explore the Food Loss Analysis by Country webpage.<br></li><br>
                 <li>You can get the amount of loss percent for a country corresponding to a year.<br></li><br> 
                 <li>You can also filter out data based on activity, food supply stage and cause of food loss.<br></li><br>
                 <li>You can also get other countries having similar food loss percent<br> and in which the wasted products are common through the <br>Similarity Analysis by Country Page.</li>
                
        </section>
            </main>
                """;      

        html = html + "</div>";

        // Footer

        ArrayList<Persona> personas = getAllPersonas();
        html = html + "<h2 class = 'users'>OUR USERS</h2>";
        html = html + "<table border = '1' class = 'table'>";
                
        html = html + "<tr>" + 
                    "<th>ID</th>" + 
                    "<th>Image</th>" + 
                    "<th>Name</th>" + 
                    "<th>Description</th>" + 
                    "<th>Needs</th>" + 
                    "<th>Goals</th>" + 
                    "</tr>";

        for (Persona persona : personas) {
            //html = html + "<table class = 'content-table'>";
            //html = html + "<div class = 'contents'>";
            html = html + "<tr>";
            html = html + "<td>" + persona.id + "</td>";
            html = html + "<td>" + "<img src = '" + persona.image + ".png" + "'>" + "</td>";
            html = html + "<td>" + persona.name + "</td>";
            html = html + "<td>" + persona.description + "</td>";
            html = html + "<td>" + persona.needs + "</td>";
            html = html + "<td>" + persona.skills + "</td>";
            html = html + "</tr>";
            
            //html = html + "</div>";
            
        }

        html = html + "</table>";

        ArrayList<Student> credits = getAllStudents();
        
        html = html + "<footer>"
                        + "<table border = 3 class = 'credits'>";
        html = html + "<h2 class = 'efforts'>Efforts By:</h2>";
                
        html = html + "<tr>" + 
                    "<th>ID</th>" + 
                    "<th>Name</th>" +
                    "</tr>";

        for (Student s : credits) {
            html = html + "<tr>";
            html = html + "<td>" + s.studentID + "</td>";
            html = html + "<td>" + s.studentName + "</td>";
            html = html + "</tr>";    
        }
        html = html + "</table>"
                            + "</footer>";

        html = html + """
            <footer>
                <div class='footer'>
                    <div class="footer-content">
                    <div class="footer-logo"><a href='/'><img src = "Weblogo.png" width = 60></a></div>
                <div class="credits">
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


        // Look up movies from JDBC


    public ArrayList<Persona> getAllPersonas() {
        // Create the ArrayList of Country objects to return
        ArrayList<Persona> personaInfo = new ArrayList<>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(JDBCConnection.DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT * FROM Persona";
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {

                // Create a Country Object
                Persona persona = new Persona();
                // Lookup the columns we need

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

    public ArrayList<Student> getAllStudents() {
        // Create the ArrayList of Country objects to return
        ArrayList<Student> students = new ArrayList<>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(JDBCConnection.DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT * FROM Student";
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {

                // Create a Country Object
                Student student = new Student();
                // Lookup the columns we need

                student.studentID = results.getString("StudentID");
                student.studentName = results.getString("Name");
                

                // Add the Country object to the array
                students.add(student);
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
        return students;
    }    
//Persona class
    public class Persona{
        public String id;
        public String image;
        public String name;
        public String description;
        public String needs;
        public String skills;
    
        public Persona(){
    
        }
    }

//Student Class
    public class Student{
        public String studentID;
        public String studentName;

        public Student(){

        }
    }

}
