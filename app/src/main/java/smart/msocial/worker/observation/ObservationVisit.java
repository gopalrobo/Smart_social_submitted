package smart.msocial.worker.observation;

import java.io.Serializable;

public class ObservationVisit implements Serializable {
    String agencyName;
    String establishmentYear;
    String organigram;
    String workingAreas;
    String targetGroup;
    String currentProjectsActivities;
    String roleSocialWorker;
    String observations;
    String learnings;
    String staff;
    String marks;
    String status;

    public ObservationVisit(String agencyName, String establishmentYear, String organigram,
                            String workingAreas, String targetGroup, String currentProjectsActivities,
                            String roleSocialWorker, String observations, String learnings,
                            String staff, String marks,String status) {
        this.agencyName = agencyName;
        this.establishmentYear = establishmentYear;
        this.organigram = organigram;
        this.workingAreas = workingAreas;
        this.targetGroup = targetGroup;
        this.currentProjectsActivities = currentProjectsActivities;
        this.roleSocialWorker = roleSocialWorker;
        this.observations = observations;
        this.learnings = learnings;
        this.staff = staff;
        this.marks = marks;
        this.status=status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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

    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }

    public String getEstablishmentYear() {
        return establishmentYear;
    }

    public void setEstablishmentYear(String establishmentYear) {
        this.establishmentYear = establishmentYear;
    }

    public String getOrganigram() {
        return organigram;
    }

    public void setOrganigram(String organigram) {
        this.organigram = organigram;
    }

    public String getWorkingAreas() {
        return workingAreas;
    }

    public void setWorkingAreas(String workingAreas) {
        this.workingAreas = workingAreas;
    }

    public String getTargetGroup() {
        return targetGroup;
    }

    public void setTargetGroup(String targetGroup) {
        this.targetGroup = targetGroup;
    }

    public String getCurrentProjectsActivities() {
        return currentProjectsActivities;
    }

    public void setCurrentProjectsActivities(String currentProjectsActivities) {
        this.currentProjectsActivities = currentProjectsActivities;
    }

    public String getRoleSocialWorker() {
        return roleSocialWorker;
    }

    public void setRoleSocialWorker(String roleSocialWorker) {
        this.roleSocialWorker = roleSocialWorker;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public String getLearnings() {
        return learnings;
    }

    public void setLearnings(String learnings) {
        this.learnings = learnings;
    }
}
