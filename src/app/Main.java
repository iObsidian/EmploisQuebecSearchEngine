package app;

import api.EmploisQuebecAPI;
import io.javalin.Javalin;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

public class Main {

    public static void main(String[] args) {
        new Main();
    }

    Main() {
        int port = 8080;

        new Thread(this::startAPI).start();

        new Thread(() -> serveAngularApp(port)).start();

        WebBrowser.main(null);

    }


    public void startAPI() {

        EmploisQuebecAPI emploisQuebecAPI = new EmploisQuebecAPI();

        Javalin app = Javalin.create();

        app.port(8080);

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

    public void serveAngularApp(int port) {
        try {
            // The simple Jetty config here will serve static content from the webapp directory
            String webappDirLocation = "res/webapp/app.EQSE/dist";

            Server server = new Server(port);

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

