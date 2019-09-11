package smart.msocial.worker.assignment;

import java.io.Serializable;

public class Assignment implements Serializable {

    String assignmentNo;
    String dateOfAssignment;
    String acknowledgement;
    String submissionDate;
    String title;
    String abstractData;
    String introduction;
    String sectoralOverview;
    String context;
    String findings;
    String suggestions;
    String conclusion;
    String references;
    String annexure1;
    String annexure2;
    String annexure3;
    String staff;
    String marks;
    String status;


    public Assignment(String assignmentNo, String dateOfAssignment, String acknowledgement, String submissionDate, String title, String abstractData, String introduction, String sectoralOverview, String context, String findings, String suggestions, String conclusion, String references, String annexure1, String annexure2, String annexure3, String staff, String marks, String status) {
        this.assignmentNo = assignmentNo;
        this.dateOfAssignment = dateOfAssignment;
        this.acknowledgement = acknowledgement;
        this.submissionDate = submissionDate;
        this.title = title;
        this.abstractData = abstractData;
        this.introduction = introduction;
        this.sectoralOverview = sectoralOverview;
        this.context = context;
        this.findings = findings;
        this.suggestions = suggestions;
        this.conclusion = conclusion;
        this.references = references;
        this.annexure1 = annexure1;
        this.annexure2 = annexure2;
        this.annexure3 = annexure3;
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

    public String getAssignmentNo() {
        return assignmentNo;
    }

    public void setAssignmentNo(String assignmentNo) {
        this.assignmentNo = assignmentNo;
    }

    public String getDateOfAssignment() {
        return dateOfAssignment;
    }

    public void setDateOfAssignment(String dateOfAssignment) {
        this.dateOfAssignment = dateOfAssignment;
    }

    public String getAcknowledgement() {
        return acknowledgement;
    }

    public void setAcknowledgement(String acknowledgement) {
        this.acknowledgement = acknowledgement;
    }

    public String getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(String submissionDate) {
        this.submissionDate = submissionDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAbstractData() {
        return abstractData;
    }

    public void setAbstractData(String abstractData) {
        this.abstractData = abstractData;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getSectoralOverview() {
        return sectoralOverview;
    }

    public void setSectoralOverview(String sectoralOverview) {
        this.sectoralOverview = sectoralOverview;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getFindings() {
        return findings;
    }

    public void setFindings(String findings) {
        this.findings = findings;
    }

    public String getSuggestions() {
        return suggestions;
    }

    public void setSuggestions(String suggestions) {
        this.suggestions = suggestions;
    }

    public String getConclusion() {
        return conclusion;
    }

    public void setConclusion(String conclusion) {
        this.conclusion = conclusion;
    }

    public String getReferences() {
        return references;
    }

    public void setReferences(String references) {
        this.references = references;
    }

    public String getAnnexure1() {
        return annexure1;
    }

    public void setAnnexure1(String annexure1) {
        this.annexure1 = annexure1;
    }

    public String getAnnexure2() {
        return annexure2;
    }

    public void setAnnexure2(String annexure2) {
        this.annexure2 = annexure2;
    }

    public String getAnnexure3() {
        return annexure3;
    }

    public void setAnnexure3(String annexure3) {
        this.annexure3 = annexure3;
    }
}


