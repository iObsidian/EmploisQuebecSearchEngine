package app;

import api.EmploisQuebecAPI;
import com.sun.javafx.webkit.WebConsoleListener;
import io.javalin.Javalin;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

public class Main {

    private final boolean DEBUG = true; // Run Angular (ng serve) as development (allows hot reload)

    public static void main(String[] args) {
        new Main();
    }

    Main() {
        if (!DEBUG) {
            new Thread(this::serveCompiledAngularApp).start();
        }

        new Thread(this::startEQSE).start();
        new Thread(this::showBrowser).start();
    }

    public static class WebBrowser extends Application {
        @Override
        public void start(Stage primaryStage) throws Exception {

            // Allows the app to communicate to the API (Javalin)
            System.setProperty("sun.net.http.allowRestrictedHeaders", "true");

            String START_URL = "http://localhost:4200/";
            TextField locationField = new TextField(START_URL);
            WebView webView = new WebView();
            webView.getEngine().load(START_URL);

            webView.setContextMenuEnabled(false);

            ContextMenu contextMenu = new ContextMenu();
            MenuItem reload = new MenuItem("Reload");
            reload.setOnAction(e -> webView.getEngine().reload());
            MenuItem savePage = new MenuItem("Save Page");
            savePage.setOnAction(e -> System.out.println("Save page..."));
            MenuItem hideImages = new MenuItem("Hide Images");
            hideImages.setOnAction(e -> System.out.println("Hide Images..."));
            contextMenu.getItems().addAll(reload, savePage, hideImages);

            webView.setOnMousePressed(e -> {
                if (e.getButton() == MouseButton.SECONDARY) {
                    contextMenu.show(webView, e.getScreenX(), e.getScreenY());
                } else {
                    contextMenu.hide();
                }
            });

            locationField.setOnAction(e -> {
                webView.getEngine().load(locationField.getText());
            });

            BorderPane root = new BorderPane(webView, locationField, null, null, null);
            primaryStage.setScene(new Scene(root, 800, 800));
            primaryStage.setTitle("app.EQSE");
            primaryStage.show();

            WebConsoleListener.setDefaultListener((wv, message, lineNumber, sourceId) -> {
                System.out.println("[Embedded Browser] : " + message + "[at " + lineNumber + "]");
            });

        }
    }

    public void showBrowser() {
        Application.launch(WebBrowser.class, null);
    }

    public void startEQSE() {

        EmploisQuebecAPI emploisQuebecAPI = new EmploisQuebecAPI();

        Javalin app = Javalin.create();

        app.enableCorsForAllOrigins();

        String explanation = ("Bienvenue sur EmploisQuebecSearchEngine API!" + "\n\n") +
                "Si vous souhaitez accedez à l'interface utilisateur, veuillez installer Angular CLI sur votre machine" + "\n" +
                "et utiliser la commande 'ng serve' dans le dossier 'webapp' de cette source." + "\n\n" +
                "C'est tout! Visitez localhost:4200 sur votre machine pour voir le site local." + "\n";
        app.get("/", ctx -> ctx.result(explanation));

        app.get("/regions", ctx -> ctx.json(emploisQuebecAPI.getCachedRegions())); // Get all regions

        app.get("/cities/:region-code", ctx -> {
            ctx.json(emploisQuebecAPI.getCachedCities(ctx.pathParam("region-code"))); // Get all cities for region
        });

        app.get("/jobs/:city-url", ctx -> {
            ctx.json(emploisQuebecAPI.getCachedJobs(ctx.pathParam("city-url"))); // Get all jobs for city
        });

        app.start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void serveCompiledAngularApp() {
        try {
            // The simple Jetty config here will serve static content from the webapp directory
            String webappDirLocation = "res/webapp/EQSE/dist";

            Server server = new Server(4200);

            WebAppContext webapp = new WebAppContext();
            webapp.setContextPath("/");
            webapp.setDescriptor(webappDirLocation + "/WEB-INF/web.xml");
            webapp.setResourceBase(webappDirLocation);

            server.setHandler(webapp);
            server.start();
            server.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

