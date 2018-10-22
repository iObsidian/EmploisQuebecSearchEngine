package website;

import java.io.IOException;
import java.util.List;

import api.EmploiQuebecAPI;
import api.region.RegionDTO;
import api.region.city.CityDTO;
import api.region.city.job.JobDTO;
import io.javalin.Javalin;

public class RunEQSE {

	private static EmploiQuebecAPI emploiQuebecAPI = new EmploiQuebecAPI();

	public static void main(String[] args) {

		Javalin app = Javalin.create();

		app.enableCorsForAllOrigins();

		StringBuilder explanation = new StringBuilder();
		explanation.append("Bienvenue sur EmploisQuebecSearchEngine API!" + "\n\n");
		explanation.append("Si vous souhaitez accedez à l'interface utilisateur, veuillez installer Angular CLI sur votre machine," + "\n");
		explanation.append("puis utiliser la commande 'ng serve' dans le dossier 'webapp' de cette source." + "\n\n");
		explanation.append("C'est tout! Visitez localhost:4200 sur votre machine pour voir le site local." + "\n");

		app.get("/", ctx -> ctx.result(explanation.toString()));

		app.get("/regions", ctx -> ctx.json(getRegions())); // Get all regions

		app.get("/cities/:region-code", ctx -> {
			ctx.json(getCities(ctx.pathParam("region-code"))); // Get all cities for region
		});

		app.get("/jobs/:city-url", ctx -> {
			ctx.json(getJobs(ctx.pathParam("city-url"))); // Get all jobs for city
		});

		app.start();
	}

	public static List<RegionDTO> getRegions() {
		return emploiQuebecAPI.getRegions();
	}

	public static List<CityDTO> getCities(String regionCode) {
		return emploiQuebecAPI.getCities(new RegionDTO(regionCode));
	}

	public static List<JobDTO> getJobs(String cityUrl) {
		return emploiQuebecAPI.getJobs(new CityDTO(cityUrl));
	}

}
