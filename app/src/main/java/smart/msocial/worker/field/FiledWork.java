package smart.msocial.worker.field;

import java.util.LinkedHashMap;

public class FiledWork {

    public String organizationName;
    public String arrival;
    public String departure;
    public String totalHours;
    public String dateOfSubmission;
    public String planForTheDay;
    public String technicalDetails;
    public String futurePlan;
    public String conclusion;
    public String supervisorRemarks;
    public String facultyRemarks;
    public String staff;
    public String marks;
    public String status;

    public FiledWork(String organizationName, String arrival, String departure, String totalHours, String dateOfSubmission, String planForTheDay, String technicalDetails, String futurePlan, String conclusion, String supervisorRemarks, String facultyRemarks) {
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

    public FiledWork(String organizationName, String arrival, String departure, String totalHours, String dateOfSubmission, String planForTheDay, String technicalDetails, String futurePlan, String conclusion, String supervisorRemarks, String facultyRemarks, String staff, String marks, String status) {
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
        this.staff = staff;
        this.marks = marks;
        this.status = status;
    }

    public String getStaff() {
        return staff;
    }

    public void setStaff(String staff) {
        this.staff = staff;
    }

    public String getMarks() {
        return marks;
    }

    public void setMarks(String marks) {
        this.marks = marks;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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


    public LinkedHashMap<String, String> toMap() {
        LinkedHashMap<String, String> linkedHashMap = new LinkedHashMap<>();
        linkedHashMap.put("Organization Name :", organizationName);
        linkedHashMap.put("Arrival:", arrival);
        linkedHashMap.put("Departure:", departure);
        linkedHashMap.put("Total Hours:", totalHours);
        linkedHashMap.put("Date Of Submission:", dateOfSubmission);
        linkedHashMap.put("Plan For The Day:", planForTheDay);
        linkedHashMap.put("Technical Details:", technicalDetails);
        linkedHashMap.put("Future Plan:", futurePlan);
        linkedHashMap.put("Conclusion:", conclusion);
        linkedHashMap.put("Supervisor Remarks:", supervisorRemarks);
        linkedHashMap.put("Faculty Remarks:", facultyRemarks);
        linkedHashMap.put("Staff:", staff);
        linkedHashMap.put("Marks:", marks);
        linkedHashMap.put("Status:", status);
        return linkedHashMap;
    }
}
