package api;

import api.region.RegionDTO;
import api.region.RegionService;
import api.region.city.CityDTO;
import api.region.city.CityService;
import api.region.city.job.JobDTO;
import api.region.city.job.JobService;

import java.util.List;

public class EmploiQuebecAPI {

	private RegionService regionService;
	private CityService cityService;
	private JobService jobService;

	public EmploiQuebecAPI() {
		jobService = new JobService();
		cityService = new CityService();
		regionService = new RegionService();
	}

	public List<RegionDTO> getRegions() {
		return regionService.getRegions();
	}

	public List<CityDTO> getCities(RegionDTO r) {
		return cityService.getCities(r);
	}

	public List<JobDTO> getJobs(CityDTO c) {
		return jobService.getJobs(c);
	}

}
