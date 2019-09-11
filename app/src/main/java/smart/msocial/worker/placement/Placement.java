package smart.msocial.worker.placement;

public class Placement {

    String organizationName;
    String arrival;
    String departure;
    String totalHours;
    String dateOfSubmission;
    String planForTheDay;
    String technicalDetails;
    String futurePlan;
    String conclusion;
    String supervisorRemarks;
    String facultyRemarks;

    public Placement(String organizationName, String arrival, String departure, String totalHours, String dateOfSubmission, String planForTheDay, String technicalDetails, String futurePlan, String conclusion, String supervisorRemarks, String facultyRemarks) {
        this.organizationName = organizationName;
        this.arrival = arrival;
        this.departure = departure;
        this.totalHours = totalHours;
        this.dateOfSubmission = dateOfSubmission;
        this.planForTheDay = planForTheDay;
        this.technicalDetails = technicalDetails;
        this.futurePlan = futurePlan;
        this.conclusion = conclusion;
        this.supervisorRemarks = supervisorRemarks;
        this.facultyRemarks = facultyRemarks;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getArrival() {
        return arrival;
    }

    public void setArrival(String arrival) {
        this.arrival = arrival;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getTotalHours() {
        return totalHours;
    }

    public void setTotalHours(String totalHours) {
        this.totalHours = totalHours;
    }

    public String getDateOfSubmission() {
        return dateOfSubmission;
    }

    public void setDateOfSubmission(String dateOfSubmission) {
        this.dateOfSubmission = dateOfSubmission;
    }

    public String getPlanForTheDay() {
        return planForTheDay;
    }

    public void setPlanForTheDay(String planForTheDay) {
        this.planForTheDay = planForTheDay;
    }

    public String getTechnicalDetails() {
        return technicalDetails;
    }

    public void setTechnicalDetails(String technicalDetails) {
        this.technicalDetails = technicalDetails;
    }

    public String getFuturePlan() {
        return futurePlan;
    }

    public void setFuturePlan(String futurePlan) {
        this.futurePlan = futurePlan;
    }

    public String getConclusion() {
        return conclusion;
    }

    public void setConclusion(String conclusion) {
        this.conclusion = conclusion;
    }

    public String getSupervisorRemarks() {
        return supervisorRemarks;
    }

    public void setSupervisorRemarks(String supervisorRemarks) {
        this.supervisorRemarks = supervisorRemarks;
    }

    public String getFacultyRemarks() {
        return facultyRemarks;
    }

    public void setFacultyRemarks(String facultyRemarks) {
        this.facultyRemarks = facultyRemarks;
    }
}
