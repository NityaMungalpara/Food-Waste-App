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
        html = html + "<link rel='stylesheet' type='text/css' href='sub3A.css' />";
        html = html + "</head>";

        // Add the body
        html = html + "<body>";

        // Add the topnav
        // This uses a Java v15+ Text Block
        // html = html + """
        //     <div class='topnav'>
        //         <a href='/'>Homepage</a>
        //         <a href='mission.html'>Our Mission</a>
        //         <a href='page2A.html'>Food Loss Percent by Country</a>
        //         <a href='page2B.html'>Sub Task 2.B</a>
        //         <a href='page3A.html'>Similarity Data Analysis by Country</a>
        //         <a href='page3B.html'>Sub Task 3.B</a>
        //     </div>
        // """;

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

        html += "<main>";
        html += "  <nav class='breadcrumb'>";
        html += "    <ul>";
        html += "      <li><a href='page2A.html'>Food Loss and Waste Analysis by Country</a></li>";
        html += "      <li>></li>";
        html += "      <li><a href='page2B.html'>Food Loss and Waste Analysis by Group</a></li>";
        html += "      <li>></li>";
        html += "      <li><a href='page3A.html'><b>Similarity Data Analysis by Country</b></a></li>";
        html += "      <li>></li>";
        html += "      <li><a href='page3B.html'>Similarity Data Analysis by Group</a></li>";
        html += "    </ul>";
        html += "  </nav>";

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
                <div class='footer-content'>
                    <div class="footer-logo"><a href='/'><img src = "Weblogo.png" width = 60></a></div>
                </div>
            </footer> """;
                        
    ArrayList<Student> credits = getAllStudents();
        
        html = html + "<footer>"
                        + "<table class = 'credits'>";
                
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
    
    //Student Class
    public class Student{
        public String studentID;
        public String studentName;

        public Student(){

        }

    }
}
