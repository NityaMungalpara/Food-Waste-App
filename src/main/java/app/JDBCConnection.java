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

    /*—————————————————————————————————————————————————————————————————————————————————————— */
    //get result for sub2B

    public List<PageST2BBean> getAvgByGroupAndYear(List<String> groupList, List<String> activityList, String startYear, String endYear, String orderType) {
        StringBuffer groupWhereBuffer = new StringBuffer();
        // StringBuffer activityWhereBuffer = new StringBuffer();
        String groupWhereStr = "";
        // String activityWhereStr = "";
        String yearWhere = "";

        if (groupList.size() > 0) {
            for (String foodGroup : groupList) {
                groupWhereBuffer.append("'").append(foodGroup.trim()).append("',");
            }
            String tempWhere = groupWhereBuffer.toString();
            groupWhereStr = tempWhere.substring(0, tempWhere.lastIndexOf(","));
            groupWhereStr = " and g.group_name in (" + groupWhereStr + ")";
        }

        // if (activityList.size() > 0) {
        //     for (String activity : activityList) {
        //         activityWhereBuffer.append("'").append(activity.trim()).append("',");
        //     }
        //     String tempWhere = activityWhereBuffer.toString();
        //     activityWhereStr = tempWhere.substring(0, tempWhere.lastIndexOf(","));
        //     activityWhereStr = " and activity in (" + activityWhereStr + ")";
        // }

        if (!("".equals(startYear) || startYear == null) && !("".equals(endYear) || endYear == null)) {
            yearWhere = " and (year between " + startYear + " and " + endYear + ")";
        }

        if (orderType == null) {
            orderType = "asc";
        }

        List<PageST2BBean> pageST2BBeanList = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(DATABASE)) {
            Statement statement = connection.createStatement();
            String query = "SELECT g.group_name, gf.activity, gf.food_supply_stage, gf.cause_of_loss, " +
                    "ROUND(AVG(CASE WHEN year = " + startYear + " THEN loss_percentage ELSE NULL END), 2) AS start_year_avg, " +
                    "ROUND(AVG(CASE WHEN year = " + endYear + " THEN loss_percentage ELSE NULL END), 2) AS end_year_avg, " +
                    "ROUND((CASE WHEN AVG(CASE WHEN year = " + endYear + " THEN loss_percentage ELSE NULL END) IS NULL THEN 0 " +
                    "ELSE AVG(CASE WHEN year = " + endYear + " THEN loss_percentage ELSE NULL END) END) - " +
                    "(CASE WHEN AVG(CASE WHEN year = " + startYear + " THEN loss_percentage ELSE NULL END) IS NULL THEN 0 " +
                    "ELSE AVG(CASE WHEN year = " + startYear + " THEN loss_percentage ELSE NULL END) END), 2) AS avg_loss_diff " +
                    "FROM food_loss f " +
                    "INNER JOIN groups g ON SUBSTR(f.cpc_code, 1, 3) = g.group_code " +
                    "INNER JOIN group_filter gf ON g.group_name = gf.groupName " +
                    "WHERE 1 = 1 " + yearWhere + groupWhereStr + " " +
                    "GROUP BY g.group_name " +
                    "ORDER BY avg_loss_diff " + orderType;

            System.out.println(query);
            ResultSet results = statement.executeQuery(query);
            while (results.next()) {
                PageST2BBean pageST2BBean = new PageST2BBean();
                pageST2BBean.setGroupName(results.getString("group_name"));
                pageST2BBean.setStartYearAvg(results.getString("start_year_avg"));
                pageST2BBean.setEndYearAvg(results.getString("end_year_avg"));
                pageST2BBean.setLossDifference(results.getString("avg_loss_diff"));

                pageST2BBean.setActivity(results.getString("activity"));
                pageST2BBean.setFoodSupplyStage(results.getString("food_supply_stage"));
                pageST2BBean.setCauseOfLoss(results.getString("cause_of_loss"));
                pageST2BBeanList.add(pageST2BBean);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            PageST2BBean errorBean = new PageST2BBean();
            errorBean.setErrorMessage("An error occurred while querying the database.");
            pageST2BBeanList.add(errorBean);
        }
        return pageST2BBeanList;
    }

    /*—————————————————————————————————————————————————————————————————————————————————————— */
    //get all food commodity
    public List<String> getAllFoodName() {
        List<String> foodNames = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(DATABASE)) {
            Statement statement = connection.createStatement();
            String query = "SELECT DISTINCT commodity FROM food_loss";
            ResultSet results = statement.executeQuery(query);
            while (results.next()) {
                String foodName = results.getString("commodity");
                foodNames.add(" " + foodName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return foodNames;
    }
    /*—————————————————————————————————————————————————————————————————————————————————————— */
    //get total groups count
    public ArrayList<Integer> getGroupCount() {
        ArrayList<Integer> similarNums = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(DATABASE)) {
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery("SELECT COUNT(group_code) AS total_count FROM groups");
            while (results.next()) {
                int totalCount = results.getInt("total_count");
                for (int i = 1; i <= totalCount; i++) {
                    similarNums.add(i);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return similarNums;
    }
    
}
