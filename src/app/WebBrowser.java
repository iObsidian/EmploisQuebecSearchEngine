package app;

import com.sun.javafx.webkit.WebConsoleListener;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class WebBrowser extends Application {

    // For some reason, Applications need a main method...
    public static void main(String[] args) {
        Application.launch(WebBrowser.class, args);
    }

    @Override
    public void start(Stage primaryStage) {





        String START_URL = "http://localhost:8080/";
        TextField locationField = new TextField(START_URL);
        WebView webView = new WebView();
        webView.getEngine().load(START_URL);





        // Allows the app to communicate to the API (Javalin)
        System.setProperty("sun.net.http.allowRestrictedHeaders", "true");


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