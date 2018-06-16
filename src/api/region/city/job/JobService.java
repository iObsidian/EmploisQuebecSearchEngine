package api.region.city.job;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import alde.commons.network.GetWebsite;
import api.region.city.CityDTO;
import ui.StringUtil;

public class JobService {

	private static final Logger log = LoggerFactory.getLogger(JobService.class);

	private JobPageService pageService = new JobPageService();

	private static final String JOBS_START = "<tbody>";
	private static final String JOBS_END = "</tbody>";

	private static final String JOB_END = "</tr>";
	private static final String JOB_START = "<td style=\"";

	public List<JobDTO> getJobs(CityDTO c) {

		System.out.println("Request to load jobs from " + c.getUrl());
		
		List<JobDTO> jobs = new ArrayList<>();

		List<String> pageUrls = pageService.getPages(c);

		System.out.println("Found " + pageUrls.size() + " page url(s).");

		for (String pageUrl : pageUrls) {

			List<String> jobsRaw = StringUtil.getStringsBetween(GetWebsite.get().getWebsiteAsStringList(pageUrl),
					JOBS_START, JOBS_END);

			JobDTO currentJob = new JobDTO();

			int index = 0;

			for (String s : jobsRaw) {

				if (s.contains(JOB_START)) {
					index = -1;
				}

				if (s.contains(JOB_END)) {
					jobs.add(currentJob);
					currentJob = new JobDTO();
				} else {

					index++;

					/*
					 * <th style="width:45px;">Offer N°</th>
					 <th style="width:230px;">Name of the job</th>
					 <th style="width:225px;">Employer</th>
					 <th style="width:60px;">Number<br />of<br />position(s)</th>
					 <th style="width:60px;">Education</th>
					 <th style="width:75px;">Years of experience</th>
					 <th style="width:75px;">Work place</th>
					 *
					 0 : <td style="text-align:left;vertical-align:top;">6337089</td>
					 1 : <td><a href="http://placement.emploiquebec.gouv.qc.ca/mbe/ut/suivroffrs/apercoffr.asp?nooffr=6337089&page=rech&prov=http%3A%2F%2Fplacement%2Eemploiquebec%2Egouv%2Eqc%2Eca%2Fmbe%2Fut%2Frechroffr%2Flistoffr%2Easp%3Fmtcle%3D%26cle%3D45%26offrdisptoutqc%3D2%26pp%3D1%26prov%3Dhttp%253A%252F%252Fplacement%252Eemploiquebec%252Egouv%252Eqc%252Eca%252Fmbe%252Fut%252Frechroffr%252Frechrcle%252Easp%253Fmtcle%253D%2526pp%253D1%2526prov%253Dhttp%25253A%25252F%25252Fplacement%25252Eemploiquebec%25252Egouv%25252Eqc%25252Eca%25252Fmbe%25252Fut%25252Frechroffr%25252Ferechroffr%25252Easp%2526date%253D3%2526creg%253D08%26date%3D3&CL=english" >fashion co-ordinator</a></td>
					 2 : <td>FUSION JEUNESSE</td>
					 3 : <td>1</td>
					 4 : <td>Collegial</td>
					 5 : <td>none, or information not available</td>
					 6 : <td>Notre-Dame-du-Nord</td>
					 */

					if (!s.contains("<!DOCTYPE html PUBLIC ")) {

						if (index == 0) {
							currentJob.setOfferNumber(StringUtil.getStringBetween(s, "\">", "</td>"));
						} else if (index == 1) {
							currentJob.setUrl(StringUtil.getStringBetween(s, "<a href=\"", "\" >"));
							currentJob.setNameOfTheJob(StringUtil.getStringBetween(s, "\" >", "</a>"));
						} else if (index == 2) {
							currentJob.setEmployer(StringUtil.getStringBetween(s, "<td>", "</td>"));
						} else if (index == 3) {
							currentJob.setNumberOfPositions(StringUtil.getStringBetween(s, "<td>", "</td>"));
						} else if (index == 4) {
							currentJob.setEducation(StringUtil.getStringBetween(s, "<td>", "</td>"));
						} else if (index == 5) {
							currentJob.setYearsOfExperience(StringUtil.getStringBetween(s, "<td>", "</td>"));
						} else if (index == 6) {
							currentJob.setWorkPlace(StringUtil.getStringBetween(s, "<td>", "</td>"));
						}

					}
				}

			}

			log.info(pageUrl);
			//break; //TODO REMOVE THIS AFTER DEBUGGING

		}

		return jobs;

	}

}

/**
 * Gets the page URLs
 */
class JobPageService {

	private static final String PAGE_LINE_STARS_WITH = "<a href=\"http://placement.emploiquebec.gouv.qc.ca";
	private static final String PAGE_ENDS_WITH = "</a>&nbsp;&nbsp;";

	public List<String> getPages(CityDTO c) {

		List<String> pageUrls = new ArrayList<>();

		List<String> website = GetWebsite.get().getWebsiteAsStringList(c.getUrl());

		String pageLine = "";

		for (String s : website) { // Find the 'page' selector at the bottom of the page
			if (s.startsWith(PAGE_LINE_STARS_WITH)) {
				pageLine = s;
				break;
			}
		}

		if (pageLine.equals("")) { //If there is no page line (only one page)
			pageUrls.add(c.getUrl());
			return pageUrls;
		} else {
			String[] a = pageLine.split(PAGE_ENDS_WITH);

			for (String page : a) {

				if (!page.equals("</p>")) { // Avoids wrongful parsing of html tag
					pageUrls.add(
							StringUtil.getStringBetween(page, "<a href=\"", "\" ").replace("\" class=\"pgactive", ""));
				}
			}
		}

		return pageUrls;

	}

}