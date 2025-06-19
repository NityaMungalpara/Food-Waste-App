package app;

import io.javalin.Javalin;
import io.javalin.core.util.RouteOverviewPlugin;

/**
 * Main Application Class.
 * <p>
 * Running this class as a regular Java application will start the
 * Javalin HTTP Server and the web application.
 * 
 * Make sure to use a dynamic port if deploying to cloud services like Railway.
 * 
 * Authors:
 * - Timothy Wiley, 2023. email: timothy.wiley@rmit.edu.au
 * - Santha Sumanasekara, 2021. email: santha.sumanasekara@rmit.edu.au
 * - Halil Ali, 2024. email: halil.ali@rmit.edu.au
 */

public class App {

    // Use PORT from environment (e.g., Railway or Heroku), fallback to 7001
    public static final int JAVALIN_PORT =
        Integer.parseInt(System.getenv().getOrDefault("PORT", "7001"));

    public static final String CSS_DIR = "css/";
    public static final String IMAGES_DIR = "images/";

    public static void main(String[] args) {
        // Create and start Javalin server
        Javalin app = Javalin.create(config -> {
            config.registerPlugin(new RouteOverviewPlugin("/help/routes"));

            // Serve static files
            config.addStaticFiles(CSS_DIR);
            config.addStaticFiles(IMAGES_DIR);
        }).start(JAVALIN_PORT);

        // Configure routes
        configureRoutes(app);
    }

    public static void configureRoutes(Javalin app) {
        // GET routes (web pages)
        app.get(PageIndex.URL, new PageIndex());
        app.get(PageMission.URL, new PageMission());
        app.get(PageST2A.URL, new PageST2A());
        app.get(PageST2B.URL, new PageST2B());
        app.get(PageST3A.URL, new PageST3A());
        app.get(PageST3B.URL, new PageST3B());
        app.get(PageReference.URL, new PageReference());

        // POST routes (form submissions)
        app.post(PageIndex.URL, new PageIndex());
        app.post(PageST2A.URL, new PageST2A());
        app.post(PageST2B.URL, new PageST2B());
        app.post(PageST3A.URL, new PageST3A());
        app.post(PageST3B.URL, new PageST3B());
    }
}
