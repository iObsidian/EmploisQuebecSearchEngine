package browser;

import java.awt.Dimension;
import java.awt.Point;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.web.WebView;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class EmbeddedBrowser {

    static final Dimension DIMENSION = new Dimension(2000, 2000);

    /* Create a JFrame with a JButton and a JFXPanel containing the WebView. */
    public EmbeddedBrowser(String url) {
        JFrame frame = new JFrame("EQSE");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.getContentPane().setLayout(null); // do the layout manually

        final JFXPanel fxPanel = new JFXPanel();

        frame.add(fxPanel);
        frame.setVisible(true);

        fxPanel.setSize(DIMENSION);
        fxPanel.setLocation(new Point(0, 27));
        frame.setResizable(true);

        frame.getContentPane().setPreferredSize(DIMENSION);
        frame.pack();
        frame.setResizable(true);

        Platform.runLater(new Runnable() { // this will run initFX as EmbeddedBrowser-Thread
            @Override
            public void run() {
                initFX(fxPanel, url);
            }
        });
    }

    /* Creates a WebView and fires up google.com */
    private static void initFX(final JFXPanel fxPanel, String url) {
        Group group = new Group();
        Scene scene = new Scene(group);
        fxPanel.setScene(scene);

        WebView webView = new WebView();

        group.getChildren().add(webView);
        webView.setMinSize(DIMENSION.width, DIMENSION.height);
        webView.setMaxSize(DIMENSION.width, DIMENSION.height);

        webView.getEngine().load(url);
    }

    /* Start application */
    public static void main(final String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new EmbeddedBrowser(args[0]);
            }
        });
    }
}