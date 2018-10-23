package api.region.city.job;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import automaticPrune.Prune;
import util.CapitalizeFirstLetter;
import util.GetWebsite;

public class JobDTO implements Serializable {

	public String offerNumber;
	public String url;
	public String nameOfTheJob;
	public String employer;
	public String numberOfPositions;
	public String education;
	public String yearsOfExperience;
	@Prune
	public String workPlace;
	public String details;

	public void setDetails(String details) {
		this.details = details;
	}

	public String getOfferNumber() {
		return offerNumber;
	}

	public void setOfferNumber(String offerNumber) {
		this.offerNumber = offerNumber;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
		//this.setDetails(getDetails());
	}

	private String getDetails() {
		StringBuilder result = new StringBuilder();
		boolean isIn = false;

		for (String s : GetWebsite.getWebsiteAsStringList(this.url)) {
			if (s.contains("<form name")) {
				isIn = true;
			} else if (s.contains("</form>")) {
				isIn = false;
				break;
			}

			if (isIn) {
				result.append(s);
			}

		}

		return result.toString();
	}

	public String getNameOfTheJob() {
		return nameOfTheJob;
	}

	public void setNameOfTheJob(String nameOfTheJob) {
		this.nameOfTheJob = CapitalizeFirstLetter.capitalizeFirstLetter(nameOfTheJob);
	}

	public String getEmployer() {
		return employer;
	}

	public void setEmployer(String employer) {
		this.employer = employer;
	}

	public String getNumberOfPositions() {
		return numberOfPositions;
	}

	public void setNumberOfPositions(String numberOfPositions) {
		this.numberOfPositions = numberOfPositions;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public String getYearsOfExperience() {
		return yearsOfExperience;
	}

	public void setYearsOfExperience(String yearsOfExperience) {
		this.yearsOfExperience = yearsOfExperience;
	}

	public String getWorkPlace() {
		return workPlace;
	}

	public void setWorkPlace(String workPlace) {
		this.workPlace = workPlace;
	}

	public List<String> getValues() {

		List<String> values = new ArrayList<String>();

		for (Field f : this.getClass().getFields()) {

			Object value = null;
			try {
				value = f.get(this);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}

			if (value != null && value instanceof String) {
				values.add((String) value);
			}

		}

		return values;
	}

	@Override
	public String toString() {
		return "Job{" + "offerNumber='" + offerNumber + '\'' + ", url='" + url + '\'' + ", nameOfTheJob='" + nameOfTheJob + '\'' + ", employer='" + employer + '\'' + ", numberOfPositions='"
				+ numberOfPositions + '\'' + ", education='" + education + '\'' + ", yearsOfExperience='" + yearsOfExperience + '\'' + ", workPlace='" + workPlace + '\'' + '}';
	}

}
