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

public class PageST3B implements Handler {

    // URL of this page relative to http://localhost:7001/
    public static final String URL = "/page3B.html";

    @Override
    public void handle(Context context) throws Exception {
        // database
        JDBCConnection jdbc = new JDBCConnection();
        List<String> foodNames = jdbc.getAllFoodName();
        // ArrayList<Integer> similarNums = jdbc.getGroupCount();
        String selectedName = context.formParam("commodity");
        String orderType = context.formParam("fields");
        String selectedNumber = context.formParam("similar_groups");
        String sortType = context.formParam("sort_order");
        if (orderType == null) {
            orderType = "ratio";
        }
        List<PageST3BBean> pageST3BBeanList = new ArrayList<>();
        List<Sub3BResult> sub3BResultList = new ArrayList<>();

        String errorMessage = null;
        if (selectedName == null && orderType != null) {
            errorMessage = "Please select a commodity!";
        }

        String groupName = null;
        if (selectedName != null) {
            groupName = jdbc.getGroupNameByCommodity(selectedName);
        }

        if (errorMessage == null) {
            pageST3BBeanList = jdbc.getWasteToLoss(selectedName, selectedNumber, orderType, sortType);
            sub3BResultList = jdbc.getMaxSimilarityScore(selectedName, selectedNumber, orderType, sortType);
        }

        String html = "<html>";
        html += "<html lang='en'>";
        html += "<head>";
        html +=     "<meta charset='UTF-8'>";
        html +=     "<title>Subtask 3.2</title>";
        html +=     "<link rel='stylesheet' type='text/css' href='sub3B.css' />";
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


        // // Main section
        html += "<main>";
        html += "  <nav class='breadcrumb'>";
        html += "    <ul>";
        html += "      <li><a href='page2A.html'>Food Loss and Waste Analysis by Country</a></li>";
        html += "      <li>></li>";
        html += "      <li><a href='page2B.html'>Food Loss and Waste Analysis by Group</a></li>";
        html += "      <li>></li>";
        html += "      <li><a href='page3A.html'>Similarity Data Analysis by Country</a></li>";
        html += "      <li>></li>";
        html += "      <li><a href='page3B.html'><b>Similarity Data Analysis by Group</b></a></li>";
        html += "    </ul>";
        html += "  </nav>";
        html += "  <section class='hero'>";
        html += "    <div class='subheading'>";
        html += "      <h1>Similarity Data Analysis by Group</h1>";
        html += "    </div>";
        html += "  </section>";

        // Form section for selecting commodity
        html += "  <section class='filter-section'>";
        html += "    <form method='post' action='" + URL + "#results'>";
        html += "      <div class='form-group'>";
        html += "        <label for='group'> Food Commodity:</label>";
        html += "        <div class='dropdown-group'>";
        html += "          <details>";
        html += "            <summary>Select Below</summary>";
        html += "            <div class='dropdown-groupMenu' id='group'>";
        for (String name : foodNames) {
            html += "       <label><input type='radio' name='commodity' value='" + name + "'";
            if (name.equals(selectedName)) {
                html += " checked";
            }
            html += ">" + name + "</label>";
        }
        html += "            </div>";
        html += "          </details>";
        html += "        </div>";
        html += "      </div>";

        // similarity criterion filter
        html += "<div class='form-group'>";
        html += "  <label for='fields'>Similarity Criterion:</label>";
        html += "    <select name='fields'>";
        html += "      <option value='' disabled>Select Below</option>";
        html += "      <option value='ratio'";
        if ("ratio".equals(orderType)) {
            html += " selected";
        }
        html += ">Ratio of Food Waste to Loss</option>";
        html += "      <option value='max'";
        if ("max".equals(orderType)) {
            html += " selected";
        }
        html += ">Highest Percentage of Loss</option>";
        html += "      <option value='min'";
        if ("min".equals(orderType)) {
            html += " selected";
        }
        html += ">Lowest Percentage of Loss</option>";
        html += "  </select>";
        html += "</div>";

        // similar groups number
        html += "<div class='form-group'>";
        html += " <label for='group'>Number of Similar Groups</label>";
        html += "   <select name='similar_groups'>";
        html += "     <option value='' disabled";
        if (selectedNumber == null || selectedNumber.isEmpty()) {
            html += " selected";
        }
        html += ">Select Below</option>";
        // for (Integer number : similarNums) {
        //     html += "<option value='" + number + "'>" + number + "</option>";
        // }
        for (int i = 1; i <= 5; i++) {
            html += " <option value='" + i + "'";
            if (String.valueOf(i).equals(selectedNumber)) {
                html += " selected";
            }
            html += ">" + i + "</option>";
        }
        html += "</select>";
        html += "</div>";

        // Sort order
        html += "<div class='form-group'>";
        html += " <label for='sort_order'>Sort By:</label>";
        html += "  <select name='sort_order' id='sort_order'>";
        html += "    <option value='ASC'";
        if ("ASC".equals(sortType)) {
            html += " selected";
        }
        html += ">Ascending</option>";
        html += "    <option value='DESC'";
        if ("DESC".equals(sortType)) {
            html += " selected";
        }
        html += ">Descending</option>";
        html += "  </select>";
        html += "</div>";

        // Submit and Reset buttons
        html += "<div class='form-buttons'>";
        html += "  <button type='submit'>Find Similar Groups</button>";
        html += "  <button type='reset'>Clear All</button>";
        html += "</div>";
        html += "</form>";
        html += "</section>";

        // Results section
        html += "  <section id= 'results' class='result-section'>";
        if (errorMessage != null) {
            html += "<div class='error-container' style='display: flex; justify-content: center; align-items: center; height: 100px;'><p class='error-message' style='text-align: center; font-size: 20px; font-weight: bold;'>" + errorMessage + "</p></div>";
        } else {
            html += "<h2>Results</h2>";
            if (pageST3BBeanList.size() == 0) {
                html += "<div style='display: flex; justify-content: center; align-items: center; height: 100px;'><p style='text-align: center; font-size: 20px; font-weight: bold;'>No results found.</p></div>";
            } else {
                // Create a table for the selected filters
                html += "<div style='margin-bottom: 20px;'>";
                html += "<table style='width: 100%; table-layout: fixed;'><tr>";

                // Selected commodity
                html += "<td style='vertical-align: top;padding-left:40px;'><p><b>Selected Commodity:</b></p>";
                if (selectedName != null) {
                    html += "<ul style='list-style-type: disc; margin: 0; padding-left:20px;'>";
                    html += "<li>" + selectedName + "</li>";
                    html += "</ul>";
                } else {
                    // html += " None";
                }
                html += "</td>";

                // Corresponding group
                html += "<td style='vertical-align: top;padding-left:40px;'><p><b>Corresponding Group:</b></p>";
                html += "<ul style='list-style-type: disc; margin: 0; padding-left:20px;'>";

                html += "<li>" + groupName + "</li>";
                html += "</ul></td>";

                // Selected Criterion
                html += "<td style='vertical-align: top; padding-left: 30px;'>";
                html += "<p><b>Similarity Criterion:</b></p>";
                html += "<ul style='list-style-type: disc; margin: 0; padding-left: 20px'>";
                html += orderType.equals("ratio") ? "<li>Ratio of Food Waste to Loss</li>" : "";
                html += orderType.equals("min") ? "<li>Lowest% of Loss</li>" : "";
                html += orderType.equals("max") ? "<li>Highest% of Loss</li>" : "";
                html += "</ul></td>";

                // Selected numbers
                html += "<td style='vertical-align: top;padding-left:40px;'><p><b>Number of Similar Groups:</b></p>";
                html += "<ul style='list-style-type: disc; margin: 0; padding-left:20px;'>";
                html += "<li>";
                if(selectedNumber == null) {
                    html += "1";
                } else {
                    html += selectedNumber + "</li>";
                }
                
                html += "</ul></td>";

                // Sorted by
                html += "<td style='vertical-align: top;padding-left:60px;'><p><b>Sorted by:</b></p>";
                html += "<ul style='list-style-type: disc; margin: 0; padding-left:20px;'>";
                html += "<li>" + sortType + "</li>";
                html += "</ul></td>";

                html += "</tr></table>";
                html += "</div>";

            }

            // Result table
            html += "<table class='custom-border' cellspacing='0' cellpadding='10' style='margin-top: 40px;'>";
            html += "<tr class='table-header'>";
            html += "<td><b>Group Name</b></td>";
            if ("ratio".equals(orderType)) {
                html += "<td><b>Ratio of Food Waste to Loss</b> <span style='float: right;'><a href='javascript:void(0)' onclick='toggleExplanation(\"section-ratio\")'><img src='query.png' alt='query' width='20' style='vertical-align: middle;'></a></span></td>";
            } else if ("max".equals(orderType)) {
                html += "<td><b>Max Loss Commodity By Group</b></td>";
                html += "<td><b>Loss Percentage%</b></td>";
                html += "<td><b>Similarity Score</b> <span style='float: right;'><a href='javascript:void(0)' onclick='toggleExplanation(\"section-max\")'><img src='query.png' alt='query' width='20' style='vertical-align: middle;'></a></span></td>";
            } else if ("min".equals(orderType)) {
                html += "<td><b>Min Loss Commodity By Group</b></td>";
                html += "<td><b>Loss Percentage%</b></td>";
                html += "<td class='similarity-score'><b>Similarity Score</b> <span style='float: right;'><a href='javascript:void(0)' onclick='toggleExplanation(\"section-min\")'><img src='query.png' alt='query' width='20' style='vertical-align: middle;'></a></span></td>";
            }
            html += "</tr>";

            if ("ratio".equals(orderType)) {
                boolean isFirstRow = true;
                for (PageST3BBean pageST3BBean : pageST3BBeanList) {
                    if (isFirstRow) {
                        html += "<tr class='first-row'>";
                        isFirstRow = false;
                    } else {
                        html += "<tr>";
                    }
                    html += "<td>" + pageST3BBean.getName() + "</td>";
                    html += "<td>" + pageST3BBean.getRatio() + "</td>";
                    html += "</tr>";
                }
            } else if ("min".equals(orderType) || "max".equals(orderType)) {
                boolean isFirstRow = true;
                for (Sub3BResult sub3bResult : sub3BResultList) {
                    if (isFirstRow) {
                        html += "<tr class='first-row'>";
                        isFirstRow = false;
                    } else {
                        html += "<tr>";
                    }
                    html += "<td>" + sub3bResult.getName() + "</td>";
                    html += "<td>" + sub3bResult.getFoodName() + "</td>";
                    if ("max".equals(orderType)) {
                        html += "<td>" + sub3bResult.getMaxPer() + "</td>";
                    } else if ("min".equals(orderType)) {
                        html += "<td>" + sub3bResult.getMinPer() + "</td>";
                    }
                    html += "<td>" + sub3bResult.getScore() + "</td>";
                    html += "</tr>";
                }
            }

            html += "</table>";

            //explanation
            html += "<div id='section-ratio' class='explanation'>";
            html += "<h3>What is the Waste to Loss Ratio?</h3>";
            html += "   <p>The waste to loss ratio is a measure that helps compare the average percentage of waste to the average percentage of loss for different categories of commodities.</p>";
            html += "   <ul>";
            html += "       <li><strong>Waste Percentage:</strong> This refers to the average percentage of a commodity that occurs at the retail and consumption level.</li>";
            html += "       <li><strong>Loss Percentage:</strong> This refers to the average percentage of a commodity that occurs from the beginning of food production through to, but not including, the retail stage, often due to issues like pest infestation or improper storage conditions. </li>";
            html += "   </ul>";
            html += "<h3>How is the Ratio Calculated?</h3>";
            html += "   <ol>";
            html += "       <li><strong>Group the Data:</strong>";
            html += "   <p>The commodities are grouped based on the first three digits of their cpc_code, which categorises them into group categories.</p></li>";
            html += "       <li><strong>Calculate Averages:</strong>";
            html += "   <p>For each group, the average waste % and the average loss % are calculated.</p></li>";
            html += "       <li><strong>Compute the Ratio:</strong>";
            html += "   <p>The waste to loss ratio for each group is then computed by dividing the average waste % by the average loss %:</p>";
            html += "<div style='text-align: center;'><code>Waste to Loss Ratio = Average Waste Percentage / Average Loss Percentage</code></div></li>";
            html += "   </ol>";
            html += "<h3>Why is this Useful?</h3>";
            html += "   <p>The waste to loss ratio provides insights into the efficiency of handling and processing commodities. A higher ratio indicates that a larger proportion of the commodity is wasted compared to what is lost, which can highlight areas where improvements are needed to reduce waste.</p>";
            html += "   <p>By analysing these ratios, users can identify categories that need more efficient practices or better management to minimise waste and loss.</p>";
            html += "</div>";

            html += "<div id='section-max' class='explanation'>";
            html += "<h3>How Does Similarity Score Work?</h3>";
            html += "<div style='text-align: center;'><code>";
            html += "   <p>Similarity Score = | Max Loss % of Other Commodity in Corresponding Group - Max Loss % of Highest Loss Commodity in Selected Commodity's Group |</p>";
            html += "</code></div>";
            html += "   <p>In this formula:</p>";
            html += "<ul>";
            html += "   <li><strong>Max Loss % of Other Commodity in Corresponding Group:</strong> This is the highest loss % of a commodity within each group other than the group to which the selected commodity belongs.</li>";
            html += "   <li><strong>Max Loss % of Highest Loss Commodity in Selected Commodity's Group:</strong> This is the highest loss % of any commodity within the group to which the selected commodity belongs.</li>";
            html += "</ul>";
            html += "   <p>By comparing these percentages, the similarity score measures how similar the loss percentages are, with lower scores indicating higher similarity and higher scores indicating lower similarity.</p>";
            html += "</div>";

            html += "<div id='section-min' class='explanation'>";
            html += "<h3>How Does Similarity Score Work?</h3>";
            html += "<div style='text-align: center;'><code>";
            html += "   <p>Similarity Score = | Min Loss % of Other Commodity in Corresponding Group - Min Loss % of Lowest Loss Commodity in Selected Commodity's Group |</p>";
            html += "</code></div>";
            html += "   <p>In this formula:</p>";
            html += "<ul>";
            html += "   <li><strong>Min Loss % of Other Commodity in Corresponding Group:</strong> This is the lowest loss % of a commodity within each group other than the group to which the selected commodity belongs.</li>";
            html += "   <li><strong>Min Loss % of Lowest Loss Commodity in Selected Commodity's Group:</strong> This is the lowest loss % of any commodity within the group to which the selected commodity belongs.</li>";
            html += "</ul>";
            html += "   <p>By comparing these percentages, the similarity score measures how similar the loss percentages are, with lower scores indicating higher similarity and higher scores indicating lower similarity.</p>";
            html += "</div>";

            // JS to toggle explanation visibility
            html += "<script>";
            html += "function toggleExplanation(id) {";
            html += "  document.querySelectorAll('.explanation').forEach(el => el.style.display = 'none');";
            html += "  var elem = document.getElementById(id);";
            html += "  if (elem) {";
            html += "    elem.style.display = 'block';"; // clicked explanation
            html += "    elem.scrollIntoView({ behavior: 'smooth' });"; // Scroll to the explanation
            html += "  }";
            html += "}";
            html += "</script>";

            html += "  </section>";
        }

        html += "</main>";

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
