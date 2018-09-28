package smart.social.worker.group;

public class GroupProject {


    String issuesIdentified;
    String literatureReview;
    String learingsFindings;
    String agencyIdentified;
    String arrival;
    String purposeOfVisit;
    String programSchedule;
    String programReport;
    String insigtsFromVisits;


    public GroupProject(String issuesIdentified, String literatureReview, String learingsFindings, String agencyIdentified, String arrival, String purposeOfVisit, String programSchedule, String programReport, String insigtsFromVisits) {
        this.issuesIdentified = issuesIdentified;
        this.literatureReview = literatureReview;
        this.learingsFindings = learingsFindings;
        this.agencyIdentified = agencyIdentified;
        this.arrival = arrival;
        this.purposeOfVisit = purposeOfVisit;
        this.programSchedule = programSchedule;
        this.programReport = programReport;
        this.insigtsFromVisits = insigtsFromVisits;
    }


    public String getIssuesIdentified() {
        return issuesIdentified;
    }

    public void setIssuesIdentified(String issuesIdentified) {
        this.issuesIdentified = issuesIdentified;
    }

    public String getLiteratureReview() {
        return literatureReview;
    }

    public void setLiteratureReview(String literatureReview) {
        this.literatureReview = literatureReview;
    }

    public String getLearingsFindings() {
        return learingsFindings;
    }

    public void setLearingsFindings(String learingsFindings) {
        this.learingsFindings = learingsFindings;
    }

    public String getAgencyIdentified() {
        return agencyIdentified;
    }

    public void setAgencyIdentified(String agencyIdentified) {
        this.agencyIdentified = agencyIdentified;
    }

    public String getArrival() {
        return arrival;
    }

    public void setArrival(String arrival) {
        this.arrival = arrival;
    }

    public String getPurposeOfVisit() {
        return purposeOfVisit;
    }

    public void setPurposeOfVisit(String purposeOfVisit) {
        this.purposeOfVisit = purposeOfVisit;
    }

    public String getProgramSchedule() {
        return programSchedule;
    }

    public void setProgramSchedule(String programSchedule) {
        this.programSchedule = programSchedule;
    }

    public String getProgramReport() {
        return programReport;
    }

    public void setProgramReport(String programReport) {
        this.programReport = programReport;
    }

    public String getInsigtsFromVisits() {
        return insigtsFromVisits;
    }

    public void setInsigtsFromVisits(String insigtsFromVisits) {
        this.insigtsFromVisits = insigtsFromVisits;
    }
}
