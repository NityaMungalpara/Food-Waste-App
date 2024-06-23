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
        // Add header content block
        html += "<header>";
        html += "  <div class='container'>";
        html += "        <div class='Weblogo'>";
        html += "            <a href='/'><img src='Weblogo.png' alt='Logo'></a>";
        html += "        </div>";
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
        html += "        <li><a href='Reference.html'>Reference</a></li>";
        html += "      </ul>";
        html += "    </nav>";
        html += "  </div>";
        html += "</header>";

        // Add Div for page Content
        html = html + "<div class='content'>";
        html = html + """
            <main>
            <section class = 'purpose'>
                

                <h1>WHY THE WEBSITE WAS MADE?</h1>
                <p>
                The website mainly focuses to acknowledge people regarding<br> the global issue of food loss and wastage,
                which occurs<br> throughout the world. The website would also be helpful to people, by providing them<br>
                the actual quantitative data of food waste occuring in the world.
                </p> 
                
                <h1>OUR PURPOSE</h1>
                    <p>
                    The purpose of the website is to help the users find relevant and required information.<br>The users would be able to find the loss percentages
                    for the countries and commodities,<br> based on the selected start and end years. Also the loss percentage would be<br>displayed in a seperate column.The 
                    website would provide the information in tables, so<br> that the users can access the data conveniently.Also the users would be provided with<br>
                    some filters like activity, food supply stage and cause of loss, so that the accuracy<br> of the information is maintained.They would also be
                    offered option to get number of similar<br>countries/ food groups based on selected country/food group and a year.  
                    </p>

                <h1>FEATURES OF THE WEBSITE</h1>
                
                 <li><b>Food Loss and Waste Analysis by Country :</b> Explore the page to get information about loss percentages for a country or even multiple countries in selected years!<br></li><br>
                 <li><b>Food Loss and Waste Analysis by Group :</b> Go through the page if you want to get the loss percentages of particular commodities in particular years!<br></li><br> 
                 <li><b>Filter Feature :</b> For these pages, you also have an option to filter out data based on activity, food supply stage and cause of food loss.<br></li><br>
                 <li><b>Similarity Data Analysis by Country :</b> You can select a country, a year and number of similar countries that you want.<br>A table would provide the countries from most similarity index to the least.</li><br>
                 <li><b>Similarity Data Analysis by Group :</b> This page would help you to find ouy similar commodities based on the selected commodity.</li><br>
                 <li><b>Ordering Feature :</b> The pages would also provide the data in asscending and descending order!</li>
                 <li><b>Reference Page :</b> This page would provide the links to the sites from which the images have been used!</li>
                
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
        html = html + "<h2 class = 'efforts'>EFFORTS BY:</h2>";
                
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

                            
        html += "<footer>";
        html += "    <div class='footer-container'>";
        html += "            COSC2803 - Studio Project Starter Code (Apr24)";
        html += "    </div>";
        html += "</footer>";
        
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
