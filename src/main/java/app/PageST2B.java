package app;

import java.util.ArrayList;
import java.util.List;

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

public class PageST2B implements Handler {

    // URL of this page relative to http://localhost:7001/
    public static final String URL = "/page2B.html";

    @Override
    public void handle(Context context) throws Exception {
        // database
        JDBCConnection jdbc = new JDBCConnection();
        List<String> foodGroups = jdbc.getAllFoodGroups();
        ArrayList<Integer> years = jdbc.getAllYears();

        List<String> groupList = context.formParams("group");
        List<String> activityList = context.formParams("fields");
        // String activityList = context.formParam("fields");

        String startYear = context.formParam("start_year");
        String endYear = context.formParam("end_year");
        String sortType = context.formParam("sort_order");

        List<PageST2BBean> pageST2BBeanList = new ArrayList<>();
        String errorMessage = null;
        if (endYear != null && startYear != null && Integer.parseInt(endYear) <= Integer.parseInt(startYear)) {
            errorMessage = "End year must be greater than start year!";
        }

        if (errorMessage == null) {
            pageST2BBeanList = jdbc.getAvgByGroupAndYear(groupList, activityList, startYear, endYear, sortType);
        }
        // List<PageST2BBean> pageST2BBeanList = jdbc.getAvgByGroupAndYear(groupList,activityList,startYear,endYear,sortType);


        // Create a simple HTML webpage in a String
        String html = "<html>";
        html += "<html lang='en'>";
        html += "<head>";
        html +=     "<meta charset='UTF-8'>";
        html +=     "<title>Subtask 2.2</title>";
        html +=     "<link rel='stylesheet' type='text/css' href='sub2B.css' />";
        html += "</head>";

        // Add the body
        html = html + "<body>";

        // Header
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


        // Main section
        html += "<main>";
        html += "  <nav class='breadcrumb'>";
        html += "    <ul>";
        html += "      <li><a href='page2A.html'>Food Loss and Waste Analysis by Country</a></li>";
        html += "      <li>></li>";
        html += "      <li><a href='page2B.html'><b>Food Loss and Waste Analysis by Group</b></a></li>";
        html += "      <li>></li>";
        html += "      <li><a href='page3A.html'>Similarity Data Analysis by Country</a></li>";
        html += "      <li>></li>";
        html += "      <li><a href='page3B.html'>Similarity Data Analysis by Group</a></li>";
        html += "    </ul>";
        html += "  </nav>";
        html += "  <section class='hero'>";
        html += "    <div class='subheading'>";
        html += "      <h1>Food Loss and Waste Analysis by Group</h1>";
        html += "    </div>";
        html += "  </section>";

        // Form section for selecting food groups
        html += "  <section class='filter-section'>";
        html += "    <form method='post' action='" + URL + "#results'>";
        html += "      <div class='form-group'>";
        html += "        <label for='group'>Food Group:</label>";
        html += "        <div class='dropdown-group'>";
        html += "          <details>";
        html += "            <summary>Select Below</summary>";
        html += "            <div class='dropdown-groupMenu' id='group'>";
        for (String group : foodGroups) {
            html += "    <label><input type='checkbox' name='group' value='" + group + "'>" + group + "</label>";
        }
        html += "            </div>";
        html += "          </details>";
        html += "        </div>";
        html += "      </div>";

        // Year dropdowns
        html += "      <div class='form-group'>";
        html += "        <label for='start_year'>Yearly Comparison: From</label>";
        html += "        <select name='start_year' id='start_year'>";
        html += "          <option value='' disabled selected>Select Start Year</option>";
        for (Integer year : years) {
            html += "          <option value='" + year + "'";
            if (startYear != null && startYear.equals(year.toString())) {
                html += " selected";
            }
            html += ">" + year + "</option>";
        }
        html += "        </select>";
        html += "      </div>";
        html += "      <div class='form-group'>";
        html += "        <label for='end_year'>To</label>";
        html += "        <select name='end_year' id='end_year'>";
        html += "          <option value='' disabled selected>Select End Year</option>";
        for (Integer year : years) {
            html += "          <option value='" + year + "'";
            if (endYear != null && endYear.equals(year.toString())) {
                html += " selected";
            }
            html += ">" + year + "</option>";
        }
        html += "        </select>";
        html += "      </div>";


        // Additional filters
        html += "      <div class='form-group'>";
        html += "        <label for='fields'>Filter:</label>";
        html += "        <div class='multiselect'>";
        html += "          <label><input type='checkbox' name='fields' value='activity'";
        if (activityList != null && activityList.contains("activity")) {
            html += " checked";
        }
        html += "> Activity</label>";
        html += "          <label><input type='checkbox' name='fields' value='food_supply_stage'";
        if (activityList != null && activityList.contains("food_supply_stage")) {
            html += " checked";
        }
        html += "> Food Supply Stage</label>";
        html += "          <label><input type='checkbox' name='fields' value='cause_of_loss'";
        if (activityList != null && activityList.contains("cause_of_loss")) {
            html += " checked";
        }
        html += "> Cause of Loss</label>";
        html += "        </div>";
        html += "      </div>";

        // Sort order
        html += "      <div class='form-group'>";
        html += "        <label for='sort_order'>Sort By:</label>";
        html += "        <select name='sort_order' id='sort_order'>";
        html += "          <option value='asc'";
        if (sortType != null && sortType.equals("asc")) {
            html += " selected";
        }
        html += ">Ascending</option>";
        html += "          <option value='desc'";
        if (sortType != null && sortType.equals("desc")) {
            html += " selected";
        }
        html += ">Descending</option>";
        html += "        </select>";
        html += "      </div>";

        // Submit and Reset buttons
        html += "      <div class='form-buttons'>";
        html += "        <button type='submit'>Search</button>";
        html += "        <button type='reset'>Clear All</button>";
        html += "      </div>";
        html += "    </form>";
        html += "  </section>";

        // Results section
        html += "  <section id= 'results' class='result-section'>";
        
        if (errorMessage != null) {
            // html += "<p class='error-message'>" + errorMessage + "</p>";
            html += "<div class='error-container' style='display: flex; justify-content: center; align-items: center; height: 100px;'><p class='error-message' style='text-align: center; font-size: 20px; font-weight: bold;'>" + errorMessage + "</p></div>";
        } else {
            html += "<h2>Results</h2>";
            if (pageST2BBeanList.size() == 0) {
                html += "<div style='display: flex; justify-content: center; align-items: center; height: 100px;'><p style='text-align: center; font-size: 20px; font-weight: bold;'>No results found.</p></div>";
            } else {
                // Create a table for the selected filters
                html += "<div style='margin-bottom: 20px;'>";
                html += "<table style='width: 100%; table-layout: fixed;'><tr>";

                // Selected Groups
                html += "<td style='vertical-align: top;padding-left:40px;'><p><b>Selected Groups:</b></p>";
                if (!groupList.isEmpty()) {
                    html += "<ul style='list-style-type: disc; margin: 0; padding-left:20px;'>";
                    for (int i = 0; i < groupList.size(); i++) {
                        html += "<li>" + groupList.get(i) + "</li>";
                    }
                    html += "</ul>";
                } else {
                    html += " None";
                }
                html += "</td>";

                // Selected Filters
                html += "<td style='vertical-align: top;padding-left:60px;'><p><b>Selected Filters:</b></p>";
                if (!activityList.isEmpty()) {
                    html += "<ul style='list-style-type: disc; margin: 0; padding-left:20px'>";
                    for (int i = 0; i < activityList.size(); i++) {
                        if (activityList.get(i).equals("activity")) {
                            html += "<li>Activity</li>";
                        } else if(activityList.get(i).equals("food_supply_stage")) {
                            html += "<li>Food Supply Stage</li>";
                        } else if(activityList.get(i).equals("cause_of_loss")) {
                            html += "<li>Cause of Loss</li>";
                        }
                    }
                    html += "</ul>";
                } else {
                    html += "Default";
                }
                html += "</td>";


                // Selected Years
                html += "<td style='vertical-align: top;padding-left:70px;'><p><b>Selected Years:</b></p>";
                html += "<li>"+ startYear + " - " + endYear + "</li>";
                html += "</td>";

                // Sorted by
                html += "<td style='vertical-align: top;padding-left:70px;'><p><b>Sorted by:</b></p>";
                html += "<ul style='list-style-type: disc; margin: 0; padding-left:20px;'>";
                html += "<li>" + sortType + "</li>";
                html += "</td>";

                html += "</tr></table>";
                html += "</div>";
            }

            // Result table
            html += "<table class='custom-border' cellspacing='0' cellpadding='10'>";
            html += "<tr class='table-header'>";
            html += "<td><b>Group Name</b></td>";
            html += "<td><b>Start Year Average Loss %</b></td>";
            html += "<td><b>End Year Average Loss %</b></td>";
            html += "<td><b>Average Loss Difference %</b></td>";

            if(activityList.contains("activity") && activityList.contains("food_supply_stage") && activityList.contains("cause_of_loss")) {
                html += "<td><b>Activity</b></td>";
                html += "<td><b>Food Supply Stage</b></td>";
                html += "<td><b>Cause of Loss</b></td>";
            } else if(activityList.contains("activity") && activityList.contains("food_supply_stage")) {
                html += "<td><b>Activity</b></td>";
                html += "<td><b>Food Supply Stage</b></td>";
            } else if(activityList.contains("activity") && activityList.contains("cause_of_loss")) {
                html += "<td><b>Activity</b></td>";
                html += "<td><b>Cause of Loss</b></td>";
            } else if (activityList.contains("food_supply_stage") && activityList.contains("cause_of_loss")) {
                html += "<td><b>Food Supply Stage</b></td>";
                html += "<td><b>Cause of Loss</b></td>";
            } else {
                html += "<td><b>Activity</b></td>";
                html += "<td><b>Food Supply Stage</b></td>";
                html += "<td><b>Cause of Loss</b></td>";
            }
            html += "</tr>";

            for (PageST2BBean pageST2BBean : pageST2BBeanList) {
                html += "<tr>";
                html += "<td>" + pageST2BBean.getGroupName() + "</td>";
                if(pageST2BBean.getStartYearAvg() == null) {
                    html += "<td>N/A</td>";
                } else {
                    html += "<td>" + pageST2BBean.getStartYearAvg() + "</td>";
                }
                if(pageST2BBean.getEndYearAvg() == null) {
                    html += "<td>N/A</td>";
                } else {
                    html += "<td>" + pageST2BBean.getEndYearAvg() + "</td>";
                }
                html += "<td>" + pageST2BBean.getLossDifference() + "</td>";
                if(activityList.contains("activity") && activityList.contains("food_supply_stage") && activityList.contains("cause_of_loss")) {
                    html += "<td>" + pageST2BBean.getActivity() + "</td>";
                    html += "<td>" + pageST2BBean.getFoodSupplyStage() + "</td>";
                    html += "<td>" + pageST2BBean.getCauseOfLoss() + "</td>";
                } else if (activityList.contains("activity") && activityList.contains("food_supply_stage")) {
                    html += "<td>" + pageST2BBean.getActivity() + "</td>";
                    html += "<td>" + pageST2BBean.getFoodSupplyStage() + "</td>";
                } else if(activityList.contains("activity") && activityList.contains("cause_of_loss")) {
                    html += "<td>" + pageST2BBean.getActivity() + "</td>";
                    html += "<td>" + pageST2BBean.getCauseOfLoss() + "</td>";
                } else if(activityList.contains("food_supply_stage") && activityList.contains("cause_of_loss")) {
                    html += "<td>" + pageST2BBean.getFoodSupplyStage() + "</td>";
                    html += "<td>" + pageST2BBean.getCauseOfLoss() + "</td>";
                } else {
                    html += "<td>" + pageST2BBean.getActivity() + "</td>";
                    html += "<td>" + pageST2BBean.getFoodSupplyStage() + "</td>";
                    html += "<td>" + pageST2BBean.getCauseOfLoss() + "</td>";
                }

                html += "</tr>";
            }
            html += " </table>";

            html += "  </section>";
        }
        html += "<main>";

        // Footer
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

}
