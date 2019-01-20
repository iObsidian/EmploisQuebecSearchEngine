package api;

import api.region.RegionDTO;
import api.region.RegionService;
import api.region.city.CityDTO;
import api.region.city.CityService;
import api.region.city.job.JobDTO;
import api.region.city.job.JobService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmploisQuebecAPI {

    private RegionService regionService;
    private CityService cityService;
    private JobService jobService;

    public EmploisQuebecAPI() {
        System.out.println("Launching EmploisQuebecAPI...");

        jobService = new JobService();
        cityService = new CityService();
        regionService = new RegionService();
    }

    /**
     * Regions
     */
    public List<RegionDTO> cachedRegions = new ArrayList<>();

    public List<RegionDTO> getCachedRegions() {
        System.out.println("Received request...");

        if (cachedRegions.isEmpty()) {
            cachedRegions.addAll(getRegions());
        }

        return cachedRegions;
    }

    /**
     * Cities
     */
    public Map<String, List<CityDTO>> cachedCities = new HashMap<>();

    public List<CityDTO> getCachedCities(String regionCode) {
        System.out.println("Received request...");

        if (cachedCities.get(regionCode) == null) {
            cachedCities.put(regionCode, getCities(new RegionDTO(regionCode)));
        }
        return cachedCities.get(regionCode);
    }

    /**
     * Jobs
     */
    public Map<String, List<JobDTO>> cachedJobs = new HashMap<>();

    public List<JobDTO> getCachedJobs(String cityUrl) {
        System.out.println("Received request...");

        if (cachedJobs.get(cityUrl) == null) {
            cachedJobs.put(cityUrl, getJobs(new CityDTO(cityUrl)));
        }

        return cachedJobs.get(cityUrl);
    }

    private List<RegionDTO> getRegions() {
        return regionService.getRegions();
    }

    private List<CityDTO> getCities(RegionDTO r) {
        return cityService.getCities(r);
    }

    private List<JobDTO> getJobs(CityDTO c) {
        return jobService.getJobs(c);
    }

}