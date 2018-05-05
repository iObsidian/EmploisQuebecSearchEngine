package ui;

import java.util.List;

import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import city.City;
import city.CityService;
import job.Job;
import job.JobService;
import region.Region;
import region.RegionService;

public class EmploiQuebecAPI {

	private static final Logger log = LoggerFactory.getLogger(EmploiQuebecAPI.class);

	private RegionService regionService;
	private CityService cityService;
	private JobService jobService;

	public EmploiQuebecAPI() {

		this.regionService = new RegionService();
		this.cityService = new CityService();
		this.jobService = new JobService();
	}

	public List<Region> getRegions() {
		return regionService.getRegions();
	}

	public List<City> getCities(Region r) {
		return cityService.getCities(r);
	}

	public List<Job> getJobs(City c) {
		return jobService.getJobs(c);
	}

	public static void main(String[] args) {

		BasicConfigurator.configure();
		log.info("Testing...");

		EmploiQuebecAPI e = new EmploiQuebecAPI();

		for (Region r : e.getRegions()) {
			for (City c : e.getCities(r)) {
				log.info(r.getName() + " : " + c);

				for (Job j : e.jobService.getJobs(c)) {
					log.info(j.toString());
				}
			}
		}

	}

}
