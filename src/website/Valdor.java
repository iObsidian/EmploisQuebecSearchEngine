package website;

import java.util.ArrayList;
import java.util.List;

import api.EmploiQuebecAPI;
import api.region.RegionDTO;
import api.region.city.CityDTO;
import api.region.city.job.JobDTO;

public class Valdor {

	private static List<JobDTO> valdorJobs;

	public static void main(String[] args) {
		System.out.println(getValdorJobs().size());
	}

	public static List<JobDTO> getValdorJobs() {

		if (valdorJobs == null) {
			List<JobDTO> jobs = new ArrayList<JobDTO>();

			EmploiQuebecAPI e = new EmploiQuebecAPI();

			for (RegionDTO r : e.getRegions()) {
				if (r.getCode().equals("08")) {
					System.out.println("Found region");

					for (CityDTO c : e.getCities(r)) {

						if (c.getName().equals("Val d'Or")) {

							System.out.println("Found city");

							for (JobDTO j : e.getJobs(c)) {

								System.out.println("Found jobs");

								jobs.add(j);
							}
						}
					}
				}
			}

			valdorJobs = jobs;

		}



		return valdorJobs;

	}

}
