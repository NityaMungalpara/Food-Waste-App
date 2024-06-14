package app;

import java.util.ArrayList;
import java.util.List;

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
        String language = context.queryParam("lang", "en");

        if (isFormSubmitted && (startYear == null || startYear.isEmpty() || endYear == null || endYear.isEmpty())) {
            hasError = true;
            if ("zh".equals(language)) {
                errorMessage = "请选择起始和结束年份。";
            } else {
                errorMessage = "Please select both start and end years.";
            }
        }

        List<PageIndexResult> pageIndexResults = new ArrayList<>();
        if (!hasError && endYear != null && startYear != null && Integer.parseInt(endYear) <= Integer.parseInt(startYear)) {
            hasError = true;
            if ("zh".equals(language)) {
                errorMessage = "结束年份必须大于起始年份！";
            } else {
                errorMessage = "End year must be greater than Start year!";
            }
        }
        if (!hasError) {
            pageIndexResults = jdbc.getMaxYearLoss(startYear, endYear);
        }

        
        String html = generateHtml(years, startYear, endYear, hasError, errorMessage, pageIndexResults, language, isFormSubmitted);

        // DO NOT MODIFY THIS
        // Makes Javalin render the webpage
        context.html(html);
    }

    private String generateHtml(ArrayList<Integer> years, String startYear, String endYear, boolean hasError, String errorMessage, List<PageIndexResult> pageIndexResults, String language, boolean isFormSubmitted) {
        String html = "<!DOCTYPE html>";
        html += "<html lang='" + language + "'>";

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
        html += "         <nav>";
        html += "            <ul class='nav-links'>";
        html += "                <li><a href='/'>" + ("en".equals(language) ? "Home" : "首页") + "</a></li>";
        html += "                <li><a href='mission.html'>" + ("en".equals(language) ? "Our Mission" : "我们的使命") + "</a></li>";
        html += "                <li class='dropdown'>";
        html += "                    <a href='#data'>" + ("en".equals(language) ? "Data & Resources" : "数据和资源") + " <span class='arrow'>▼</span></a>";
        html += "                    <div class='dropdown-content'>";
        html += "                        <a href='page2A.html'>" + ("en".equals(language) ? "Food Loss and Waste Analysis by Country" : "按国家分析食物浪费") + "</a>";
        html += "                        <a href='page2B.html'>" + ("en".equals(language) ? "Food Loss and Waste Analysis by Group" : "按群组分析食物浪费") + "</a>";
        html += "                        <a href='page3A.html'>" + ("en".equals(language) ? "Similarity Data Analysis by Country" : "按国家相似性数据分析") + "</a>";
        html += "                        <a href='page3B.html'>" + ("en".equals(language) ? "Similarity Data Analysis by Group" : "按群组相似性数据分析") + "</a>";
        html += "                    </div>";
        html += "                </li>";
        html += "                <li><a href='Reference.html'>" + ("en".equals(language) ? "Reference" : "参考") + "</a></li>";
        html += "            </ul>";
        html += "        </nav>";
        html += "        <div class='nav-right'>";
        html += "            <div class='search-container'>";
        html += "                <input type='text' placeholder='Search'>";
        html += "                <button>Search</button>";
        html += "            </div>";
        html += "            <button class='help-center'>Help Center</button>";
        html += "            <div class='language-selector'>";
        html += "                <select onchange='window.location.href=\"?lang=\" + this.value'>";
        html += "                    <option value='en'" + ("en".equals(language) ? " selected" : "") + ">English</option>";
        html += "                    <option value='zh'" + ("zh".equals(language) ? " selected" : "") + ">Chinese</option>";
        html += "                </select>";
        html += "            </div>";
        html += "        </div>";
        html += "    </div>";
        html += "</header>";
        
        // Main section
        html += "<main>";
        html += "    <section class='hero'>";
        html += "        <div class='container'>";
        html += "            <h1>" + ("en".equals(language) ? "Less Waste, Better World" : "减少浪费，更美好的世界") + "</h1>";
        html += "            <div class='highlight'>";
        html += "                <p>" + ("en".equals(language) ? "Join us in exploring the crucial data behind food loss and waste." : "加入我们，探索食物浪费背后的重要数据。") + "</p>";
        html += "                <p>" + ("en".equals(language) ? "Let's work together towards a more sustainable future." : "让我们共同努力，走向更可持续的未来。") + "</p>";
        html += "                <a href='page2A.html'><button>" + ("en".equals(language) ? "Get Started" : "开始") + "</button></a>";
        html += "            </div>";
        html += "        </div>";
        html += "    </section>";
        html += "    <section class='overview'>";
        html += "        <div class='overview-header'>";
        html += "            <h2>" + ("en".equals(language) ? "Global Food Loss Overview" : "全球食物损失概览") + "</h2>";
        html += "            <h3>" + ("en".equals(language) ? "Analysing Loss Percentages of Various Commodities Over the Years." : "分析各种商品多年来的损失百分比。") + "</h3>";
        html += "            <h3>" + ("en".equals(language) ? "Search Below!" : "在下方搜索！") + "</h3>";
        html += "        </div>";

        // Get user input
        html += "  <section class='filter-section' id='filter-section'>";
        html += "    <form method='post' action='" + URL + "?lang=" + language + "#result-section'>";
        html += "      <input type='hidden' name='is_form_submitted' value='true'>";
                                
        // Year dropdowns
        html += "      <div class='form-group'>";
        html += "        <select name='start_year' id='start_year'>";
        html += "          <option value='' disabled selected>" + ("en".equals(language) ? "Select Start Year" : "选择起始年份") + "</option>";
        for (Integer year : years) {
            html += "          <option value='" + year + "'>" + year + "</option>";
        }
        html += "        </select>";
        html += "      </div>";
        html += "      <div class='form-group'>";
        html += "        <select name='end_year' id='end_year'>";
        html += "          <option value='' disabled selected>" + ("en".equals(language) ? "Select End Year" : "选择结束年份") + "</option>";
        for (Integer year : years) {
            html += "          <option value='" + year + "'>" + year + "</option>";
        }
        html += "        </select>";
        html += "      </div>";
        // Submit buttons
        html += "      <div class='form-buttons'>";
        html += "        <button type='submit'>" + ("en".equals(language) ? "Search" : "搜索") + "</button>";
        html += "        <button type='button' onclick='location.href=\"#overview-section\"'>" + ("en".equals(language) ? "Quick View" : "快速查看") + "</button>";
        html += "      </div>";
        html += "    </form>";
        html += "  </section>";

        // Results section
        html += "  <section class='result-section' id='result-section'>";
        if (hasError) {
            html += "<p class='error-message' style='text-align: center;font-size: 18px; font-weight: bold;'>" + errorMessage + "</p>";
        } else if (isFormSubmitted && (pageIndexResults == null || pageIndexResults.size() == 0)) {
            html += "<p class='no' style='text-align: center;font-size: 18px; font-weight: bold;'>" + ("en".equals(language) ? "No results found" : "未找到结果") + "</p>";
        } else {
            if (pageIndexResults.size() > 0) {
                html += "<div style='display: flex; justify-content: center; margin-bottom: 10px;'>";
                html += "<table style='width: auto; table-layout: fixed; text-align: center;'><tr>";
                //selected year
                if (startYear != null && !startYear.isEmpty() && endYear != null && !endYear.isEmpty()) {
                    html += "<td style='vertical-align: top;'><p><b>" + ("en".equals(language) ? "Selected Years:" : "选择的年份:") + "&nbsp;</b>" + startYear + " - " + endYear + "</p></td>";
                }
                html += "</tr></table>";
                html += "</div>"; 
                // result table
                html += "<table class='custom-border' cellspacing='0' cellpadding='10'>";
                html += "<tr class='table-header'>";
                html += "<td><b>" + ("en".equals(language) ? "Selected Year Range" : "选择的年份范围") + "</b></td>";
                html += "<td><b>" + ("en".equals(language) ? "Commodity" : "商品") + "</b></td>";
                html += "<td><b>" + ("en".equals(language) ? "Average Max Loss%" : "平均最大损失%") + "</b></td>";
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
        html += "        <p>" + ("en".equals(language) ? "The highest single-year food loss recorded was <b>65%</b>. This significant loss occurred for:" : "记录的最高单年食物损失为<b>65%</b>。这种重大损失发生在：") + "</p>";
        html += "        <ul>";
        html += "          <li>" + ("en".equals(language) ? "Cowpeas, dry in 1974" : "1974年干豇豆") + "</li>";
        html += "          <li>" + ("en".equals(language) ? "Strawberries in 2001" : "2001年草莓") + "</li>";
        html += "          <li>" + ("en".equals(language) ? "Cauliflowers and Broccoli in 2013" : "2013年菜花和西兰花") + "</li>";
        html += "        </ul>";
        html += "        <p>" + ("en".equals(language) ? "Food loss affects us all. Understanding the data is the first step towards reducing waste." : "食物损失影响着我们所有人。了解数据是减少浪费的第一步。") + "</p>";
        html += "        <a href='page2A.html'><button class='learn-more'>" + ("en".equals(language) ? "Learn More" : "了解更多") + "</button></a>";
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
        html += "            <a href='#about'>" + ("en".equals(language) ? "About" : "关于") + "</a>";
        html += "            <a href='#contact'>" + ("en".equals(language) ? "Contact Us" : "联系我们") + "</a>";
        html += "            <a href='#faqs'>" + ("en".equals(language) ? "FAQs" : "常见问题") + "</a>";
        html += "            <a href='#privacy'>" + ("en".equals(language) ? "Privacy Policy" : "隐私政策") + "</a>";
        html += "            <a href='#terms'>" + ("en".equals(language) ? "Terms of Use" : "使用条款") + "</a>";
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
        return html;
    }
}