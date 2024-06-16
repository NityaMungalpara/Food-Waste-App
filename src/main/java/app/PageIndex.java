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

public class PageIndex implements Handler {

    // URL of this page relative to http://localhost:7001/
    public static final String URL = "/";
    @Override
    public void handle(Context context) throws Exception {
        JDBCConnection jdbc = new JDBCConnection();
        ArrayList<Integer> years = jdbc.getAllYears();
        String startYear = context.formParam("start_year");
        String endYear = context.formParam("end_year");
        boolean hasError = false;
        String errorMessage = null;

        boolean isFormSubmitted = (context.formParam("is_form_submitted") != null);

        if (isFormSubmitted && (startYear == null || startYear.isEmpty() || endYear == null || endYear.isEmpty())) {
            hasError = true;
            errorMessage = "Please select both start and end years.";
        }

        List<PageIndexResult> pageIndexResults = new ArrayList<>();
        if (!hasError && endYear != null && startYear != null && Integer.parseInt(endYear) <= Integer.parseInt(startYear)) {
            hasError = true;
            errorMessage = "End year must be greater than Start year!";
        }
        if (!hasError) {
            pageIndexResults = jdbc.getMaxYearLoss(startYear, endYear);
        }


        // Create a simple HTML webpage in a String
        String html = "<!DOCTYPE html>";
        html += "<html lang='en'>";

        // Add some Header information
        html += "<head>" + "<meta charset='UTF-8'>";
        html += "<title>Homepage</title>";
        html = html + "<link rel='stylesheet' type='text/css' href='PageIndex.css' />";
        html = html + "</head>";
        html = html + "<body>";

        // Add header content block
        html += "<header>";
        html += "    <div class='container'>";
        html += "        <div class='Weblogo'>";
        html += "            <img src='Weblogo.png' alt='Logo'>";
        html += "        </div>";
        html += "        <nav>";
        html += "            <ul class='nav-links'>";
        html += "                <li><a href='/'>Home</a></li>";
        html += "                <li><a href='mission.html'>Our Mission</a></li>";
        html += "                <li class='dropdown'>";
        html += "                    <a href='#data'>Data & Resources <span class='arrow'>▼</span></a>";
        html += "                    <div class='dropdown-content'>";
        html += "                        <a href='page2A.html'>Food Loss and Waste Analysis by Country</a>";
        html += "                        <a href='page2B.html'>Food Loss and Waste Analysis by Group</a>";
        html += "                        <a href='page3A.html'>Similarity Data Analysis by Country</a>";
        html += "                        <a href='page3B.html'>Similarity Data Analysis by Group</a>";
        html += "                    </div>";
        html += "                </li>";
        html += "                <li><a href='Reference.html'>Reference</a></li>";
        html += "            </ul>";
        html += "        </nav>";
        html += "        <div class='nav-right'>";
        html += "            <div class='search-container'>";
        html += "                <input type='text' placeholder='Search'>";
        html += "                <button>Search</button>";
        html += "            </div>";
        html += "            <button class='help-center'>Help Center</button>";
        html += "            <div class='language-selector'>";
        html += "                <select>";
        html += "                    <option value='PageIndex.html'>English</option>";
        html += "                    <option value='cn'>Chinese</option>";
        html += "                </select>";
        html += "            </div>";
        html += "        </div>";
        html += "    </div>";
        html += "</header>";

        // Main section
        html += "<main>";
        html += "    <section class='hero'>";
        html += "        <div class='container'>";
        html += "            <h1>Less Waste, Better World</h1>";
        html += "            <div class='highlight'>";
        html += "                <p>Join us in exploring the crucial data behind food loss and waste.</p>";
        html += "                <p>Let's work together towards a more sustainable future.</p>";
        html += "                <a href='page2A.html'><button>Get Started</button></a>";
        html += "            </div>";
        html += "        </div>";
        html += "    </section>";
        html += "    <section class='overview'>";
        html += "        <div class='overview-header'>";
        html += "            <h2>Global Food Loss Overview</h2>";
        html += "            <h3>Analysing Loss Percentages of Various Commodities Over the Years.</h3>";
        html += "            <h3>Search Below!</h3>";
        html += "        </div>";
        // Get user input
        html += "  <section class='filter-section' id='filter-section'>";
        html += "    <form method='post' action='" + URL + "#result-section'>";
        html += "      <input type='hidden' name='is_form_submitted' value='true'>";

        // Year dropdowns
        html += "      <div class='form-group'>";
        // html += "        <label for='start_year'>Yearly Comparison: From</label>";
        html += "        <select name='start_year' id='start_year'>";
        html += "          <option value='' disabled selected>Select Start Year</option>";
        for (Integer year : years) {
            html += "          <option value='" + year + "'>" + year + "</option>";
        }
        html += "        </select>";
        html += "      </div>";
        html += "      <div class='form-group'>";
        // html += "        <label for='end_year'>To</label>";
        html += "        <select name='end_year' id='end_year'>";
        html += "          <option value='' disabled selected>Select End Year</option>";
        for (Integer year : years) {
            html += "          <option value='" + year + "'>" + year + "</option>";
        }
        html += "        </select>";
        html += "      </div>";
        // Submit
        html += "      <div class='form-buttons'>";
        html += "        <button type='submit'>Search</button>";
        html += "        <button type='button' onclick='location.href=\"#overview-section\"'>Quick View</button>";
        html += "      </div>";
        html += "    </form>";
        html += "  </section>";
        // Results section
        html += "  <section class='result-section' id='result-section'>";
        if (hasError) {
            html += "<p class='error-message' style='text-align: center;font-size: 18px; font-weight: bold;'>" + errorMessage + "</p>";
        } else if (isFormSubmitted && pageIndexResults.size() == 0) {
            html += "<p class='no' style='text-align: center;font-size: 18px; font-weight: bold;'>No results found</p>";
        } else {
            if (pageIndexResults.size() > 0) {
                html += "<div style='display: flex; justify-content: center; margin-bottom: 10px;'>";
                html += "<table style='width: auto; table-layout: fixed; text-align: center;'><tr>";
                //selected year
                if (startYear != null && !startYear.isEmpty() && endYear != null && !endYear.isEmpty()) {
                    html += "<td style='vertical-align: top;'><p><b>Selected Years:&nbsp;</b>" + startYear + " - " + endYear + "</p></td>";
                }
                html += "</tr></table>";
                html += "</div>";
                // result table
                html += "<table class='custom-border' cellspacing='0' cellpadding='10'>";
                html += "<tr class='table-header'>";
                html += "<td><b>Selected Year Range</b></td>";
                html += "<td><b>Commodity </b></td>";
                html += "<td><b>Average Max Loss%</b></td>";
                html += "</tr>";
                for (PageIndexResult pageIndexResult : pageIndexResults) {
                    html += "<tr>";
                    if (pageIndexResult.getYear() != null) {
                        html += "<td>" + pageIndexResult.getYear() + "</td>";
                    }
                    if (pageIndexResult.getName() != null) {
                        html += "<td>" + pageIndexResult.getName() + "</td>";
                    }
                    if (pageIndexResult.getAvgMaxLossPercentage() != null) {
                        html += "<td>" + pageIndexResult.getAvgMaxLossPercentage() + "</td>";
                    }
                    html += "</tr>";
                }
                html += " </table>";
            }
        }
        html += "  </section>";
        // Overview
        html += "  <section id='overview-section' class='hidden'>";
        html += "    <div class='content-wrapper'>";
        html += "      <div class='text-box'>";
        html += "        <p>The highest single-year food loss recorded was <b>65%</b>. This significant loss occurred for:</p>";
        html += "        <ul>";
        html += "          <li>Cowpeas, dry in 1974</li>";
        html += "          <li>Strawberries in 2001</li>";
        html += "          <li>Cauliflowers and Broccoli in 2013</li>";
        html += "        </ul>";
        html += "        <p>Food loss affects us all. Understanding the data is the first step towards reducing waste.</p>";
        html += "        <a href='page2A.html'><button class='learn-more'>Learn More</button></a>";
        html += "      </div>";
        html += "      <div class='chart-box'>";
        html += "        <img src='chart.png' alt='Loss Percentage Chart'>";
        html += "      </div>";
        html += "    </div>";
        html += "  </section>";
        html += "</main>";

        // Footer section
        html += "<footer>";
        html += "    <div class='footer-container'>";
        html += "        <div class='footer-links'>";
        html += "            COSC2803 - Studio Project Starter Code (Apr24)";
        html += "            <a href='#about'>About</a>";
        html += "            <a href='#contact'>Contact Us</a>";
        html += "            <a href='#faqs'>FAQs</a>";
        html += "            <a href='#privacy'>Privacy Policy</a>";
        html += "            <a href='#terms'>Terms of Use</a>";
        html += "        </div>";
        html += "        <div class='footer-social'>";
        html += "            <a href='https://x.com'><img src='twitter-icon.png' alt='Twitter'></a>";
        html += "            <a href='https://www.facebook.com/'><img src='facebook-icon.png' alt='Facebook'></a>";
        html += "            <a href='https://www.linkedin.com/'><img src='linkedin-icon.png' alt='LinkedIn'></a>";
        html += "            <a href='https://www.instagram.com/'><img src='instagram-icon.png' alt='Instagram'></a>";
        html += "        </div>";
        html += "    </div>";
        html += "</footer>";

        // Finish the HTML webpage
        html = html + "</body>" + "</html>";
        // DO NOT MODIFY THIS
        // Makes Javalin render the webpage
        context.html(html);
    }
}

