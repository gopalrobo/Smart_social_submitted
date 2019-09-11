package smart.msocial.worker.staff;

import java.io.Serializable;
import java.util.ArrayList;

import smart.msocial.worker.Student;

public class Staff implements Serializable {
    String id;
    String name;
    String bachelorDegree;
    String masterDegree;
    String mphil;
    String phd;
    String phone;
    String education;
    String experience;
    String socialmedia;
    String password;
    String confirmpassword;
    ArrayList<Subject> subjects;
    ArrayList<Student> students;

    public Staff(String name, String bachelorDegree, String masterDegree, String mphil, String phd, String phone, String education, String experience, String socialmedia, String password, String confirmpassword, ArrayList<Subject> subjects, ArrayList<Student> students) {
        this.name = name;
        this.bachelorDegree = bachelorDegree;
        this.masterDegree = masterDegree;
        this.mphil = mphil;
        this.phd = phd;
        this.phone = phone;
        this.education = education;
        this.experience = experience;
        this.socialmedia = socialmedia;
        this.password = password;
        this.confirmpassword = confirmpassword;
        this.subjects = subjects;
        this.students = students;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmpassword() {
        return confirmpassword;
    }

    public void setConfirmpassword(String confirmpassword) {
        this.confirmpassword = confirmpassword;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBachelorDegree() {
        return bachelorDegree;
    }

    public void setBachelorDegree(String bachelorDegree) {
        this.bachelorDegree = bachelorDegree;
    }

    public String getMasterDegree() {
        return masterDegree;
    }

    public void setMasterDegree(String masterDegree) {
        this.masterDegree = masterDegree;
    }

    public String getMphil() {
        return mphil;
    }

    public void setMphil(String mphil) {
        this.mphil = mphil;
    }

    public String getPhd() {
        return phd;
    }

    public void setPhd(String phd) {
        this.phd = phd;
    }


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getSocialmedia() {
        return socialmedia;
    }

    public void setSocialmedia(String socialmedia) {
        this.socialmedia = socialmedia;
    }

    public ArrayList<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(ArrayList<Subject> subjects) {
        this.subjects = subjects;
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    public void setStudents(ArrayList<Student> students) {
        this.students = students;
    }
}
