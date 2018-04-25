package ui;

import java.util.List;

import alde.commons.network.WebsiteReader;
import alde.commons.network.WebsiteReaderImpl;
import job.Job;
import job.JobService;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import city.City;
import city.CityService;
import region.Region;
import region.RegionService;

public class EmploiQuebecAPI {

	private static final Logger log = LoggerFactory.getLogger(EmploiQuebecAPI.class);

	private WebsiteReader websiteReader;

	private RegionService regionService;
	private CityService cityService;
	private JobService jobService;

	public EmploiQuebecAPI() {
		this.websiteReader = new WebsiteReaderImpl();

		this.regionService = new RegionService(websiteReader);
		this.cityService = new CityService(websiteReader);
		this.jobService = new JobService(websiteReader);
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
				System.out.println(r.getName() + " : " + c);

				for (Job j : e.jobService.getJobs(c)) {
					System.out.println(j);
				}
			}
		}

	}

}
