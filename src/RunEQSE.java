import api.EmploisQuebecAPI;
import api.region.RegionDTO;
import api.region.city.CityDTO;
import api.region.city.job.JobDTO;
import browser.EmbeddedBrowser;
import io.javalin.Javalin;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RunEQSE {

    private static EmploisQuebecAPI emploisQuebecAPI = new EmploisQuebecAPI();

    public static void main(String[] args) {
        new RunEQSE();
    }

    public RunEQSE() {
        Javalin app = Javalin.create();

        app.enableCorsForAllOrigins();

        String explanation = ("Bienvenue sur EmploisQuebecSearchEngine API!" + "\n\n") +
                "Si vous souhaitez accedez à l'interface utilisateur, veuillez installer Angular CLI sur votre machine" + "\n" +
                "et utiliser la commande 'ng serve' dans le dossier 'webapp' de cette source." + "\n\n" +
                "C'est tout! Visitez localhost:4200 sur votre machine pour voir le site local." + "\n";
        app.get("/", ctx -> ctx.result(explanation));

        app.get("/regions", ctx -> ctx.json(getRegions())); // Get all regions

        app.get("/cities/:region-code", ctx -> {
            ctx.json(getCities(ctx.pathParam("region-code"))); // Get all cities for region
        });

        app.get("/jobs/:city-url", ctx -> {
            ctx.json(getJobs(ctx.pathParam("city-url"))); // Get all jobs for city
        });

        app.start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        EmbeddedBrowser.main(new String[]{"http://localhost:4200/"});

    }

    private void runAngular() {
        String s = null;

        try {

            String currentDirectory = new File(".").getCanonicalPath() + "\\res\\webapp\\EQSE";

            ProcessBuilder pb = new ProcessBuilder("ng", "serve");
            pb.directory(new File(currentDirectory));
            Process p = pb.start();

            BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
            BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));

            while ((s = stdInput.readLine()) != null) {
                System.out.println(s);
            }

            while ((s = stdError.readLine()) != null) {
                System.out.println(s);
            }

            System.exit(0);
        } catch (IOException e) {
            System.out.println("exception happened - here's what I know: ");
            e.printStackTrace();
            System.exit(-1);
        }
    }

    /**
     * Regions
     */
    private List<RegionDTO> cachedRegions = new ArrayList<>();

    private List<RegionDTO> getRegions() {
        if (cachedRegions.isEmpty()) {
            cachedRegions.addAll(emploisQuebecAPI.getRegions());
        }

        return cachedRegions;
    }

    /**
     * Cities
     */
    private Map<String, List<CityDTO>> cachedCities = new HashMap<>();

    private List<CityDTO> getCities(String regionCode) {
        if (cachedCities.get(regionCode) == null) {
            cachedCities.put(regionCode, emploisQuebecAPI.getCities(new RegionDTO(regionCode)));
        }
        return cachedCities.get(regionCode);
    }

    /**
     * Jobs
     */
    private Map<String, List<JobDTO>> cachedJobs = new HashMap<>();

    private List<JobDTO> getJobs(String cityUrl) {
        if (cachedJobs.get(cityUrl) == null) {
            cachedJobs.put(cityUrl, emploisQuebecAPI.getJobs(new CityDTO(cityUrl)));
        }

        return cachedJobs.get(cityUrl);
    }

}
