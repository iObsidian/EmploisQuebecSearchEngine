package job;

import automaticPrune.Prune;

public class Job {

	public String offerNumber;
	public String url;
	public String nameOfTheJob;
	public String employer;
	public String numberOfPositions;
	public String education;
	public String yearsOfExperience;
	@Prune
	public String workPlace;

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
	}

	public String getNameOfTheJob() {
		return nameOfTheJob;
	}

	public void setNameOfTheJob(String nameOfTheJob) {
		this.nameOfTheJob = nameOfTheJob;
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

	@Override
	public String toString() {
		return "Job{" + "offerNumber='" + offerNumber + '\'' + ", url='" + url + '\'' + ", nameOfTheJob='"
				+ nameOfTheJob + '\'' + ", employer='" + employer + '\'' + ", numberOfPositions='" + numberOfPositions
				+ '\'' + ", education='" + education + '\'' + ", yearsOfExperience='" + yearsOfExperience + '\''
				+ ", workPlace='" + workPlace + '\'' + '}';
	}

}
