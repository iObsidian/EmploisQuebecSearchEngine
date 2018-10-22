package website;

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

		app.enableCorsForOrigin("127.0.0.1");

		app.get("/", ctx -> ctx.result("Javalin works!"));

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
