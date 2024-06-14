package app;

import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Class for Managing the JDBC Connection to a SQLLite Database.
 * Allows SQL queries to be used with the SQLLite Databse in Java.
 *
 * @author Timothy Wiley, 2023. email: timothy.wiley@rmit.edu.au
 * @author Santha Sumanasekara, 2021. email: santha.sumanasekara@rmit.edu.au
 * @author Halil Ali, 2024. email: halil.ali@rmit.edu.au
 */

public class JDBCConnection {

    // Name of database file (contained in database folder)
    public static final String DATABASE = "jdbc:sqlite:database/foodloss.db";

    /**
     * This creates a JDBC Object so we can keep talking to the database
     */
    public JDBCConnection() {
        System.out.println("Created JDBC Connection Object");
    }

    /**
     * Get all of the Countries in the database.
     * @return
     *    Returns an ArrayList of Country objects
     */

    /*—————————————————————————————————————————————————————————————————————————————————————— */
    //indexpage result
    public List<PageIndexResult> getMaxYearLoss(String startYear, String endYear) {
        List<PageIndexResult> PageIndexResultList = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(DATABASE)) {
            Statement statement = connection.createStatement();
            
            String query = "WITH user_input AS (" +
               "SELECT " + startYear + " AS start_year, " + endYear + " AS end_year)," +
               "ranked_losses AS (" +
               "SELECT year, commodity, loss_percentage, " +
               "ROW_NUMBER() OVER (PARTITION BY year ORDER BY loss_percentage DESC) AS row_num " +
               "FROM food_loss " +
               "WHERE year BETWEEN (SELECT start_year FROM user_input) AND (SELECT end_year FROM user_input))," +
               "max_losses AS (" +
               "SELECT year, commodity, loss_percentage " +
               "FROM ranked_losses " +
               "WHERE row_num = 1) " +
               "SELECT year, commodity, AVG(loss_percentage) AS avg_max_loss_percentage " +
               "FROM max_losses " +
               "GROUP BY year, commodity " +
               "ORDER BY year;";
    
            System.out.println(query);
            ResultSet results = statement.executeQuery(query);
            while (results.next()) {
                PageIndexResult pageIndexResult = new PageIndexResult();
                pageIndexResult.setYear(results.getString("year"));
                pageIndexResult.setName(results.getString("commodity"));
                pageIndexResult.setAvgMaxLossPercentage(results.getString("avg_max_loss_percentage"));
                PageIndexResultList.add(pageIndexResult);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return PageIndexResultList;
    }
    
    /*—————————————————————————————————————————————————————————————————————————————————————— */
    //get all years
    public ArrayList<Integer> getAllYears() {
        ArrayList<Integer> years = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(DATABASE)) {
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery("SELECT DISTINCT year FROM Date");
            while (results.next()) {
                years.add(results.getInt("year"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return years;
    }
    /*—————————————————————————————————————————————————————————————————————————————————————— */
    //get all food groups
    public List<String> getAllFoodGroups() {
        List<String> foodGroups = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(DATABASE)) {
            Statement statement = connection.createStatement();
            String query = "SELECT DISTINCT group_name FROM groups";
            ResultSet results = statement.executeQuery(query);
            while (results.next()) {
                String groupName = results.getString("group_name");
                foodGroups.add(" " + groupName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return foodGroups;
    }
}
