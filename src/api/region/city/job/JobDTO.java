package api.region.city.job;

import util.StringUtil;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class JobDTO implements Serializable {

    private String offerNumber;
    private String url;
    private String nameOfTheJob;
    private String employer;
    private String numberOfPositions;
    private String education;
    private String yearsOfExperience;
    private String workPlace;
    private String details;

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

    public String getNameOfTheJob() {
        return nameOfTheJob;
    }

    public void setNameOfTheJob(String nameOfTheJob) {
        this.nameOfTheJob = StringUtil.capitalizeFirstLetter(nameOfTheJob);
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
