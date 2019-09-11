package smart.msocial.worker;


import java.io.Serializable;

public class Student implements Serializable {

    String sudentid;
    String image;
    String registerid;
    String studentname;
    String geotag;
    String fathername;
    String sex;
    String state;
    String district;
    String block;
    String panchayat;
    String department;
    String year;
    String shift;
    String type;
    String eduQualification;
    String degreeName;
    String percentGpa;
    String classMark;
    String address;
    String mobile;
    String whatsapp;
    String gmail;
    String religion;
    String caste;


    public Student() {
    }


    public Student(String sudentid, String image, String registerid, String studentname, String geotag, String degreeName) {
        this.sudentid = sudentid;
        this.image = image;
        this.registerid = registerid;
        this.studentname = studentname;
        this.geotag = geotag;
        this.degreeName = degreeName;
    }

    public void setId(String sudentid) {
        this.sudentid = sudentid;
    }

    public void setData(String sudentid, String image, String registerid, String studentname, String geotag, String fathername, String sex, String state, String district, String block, String panchayat, String department, String year, String shift, String type, String eduQualification, String degreeName, String percentGpa, String classMark, String address, String mobile, String whatsapp, String gmail, String religion, String caste) {
        this.sudentid = sudentid;
        this.image = image;
        this.registerid = registerid;
        this.studentname = studentname;
        this.geotag = geotag;
        this.fathername = fathername;
        this.sex = sex;
        this.state = state;
        this.district = district;
        this.block = block;
        this.panchayat = panchayat;
        this.department = department;
        this.year = year;
        this.shift = shift;
        this.type = type;
        this.eduQualification = eduQualification;
        this.degreeName = degreeName;
        this.percentGpa = percentGpa;
        this.classMark = classMark;
        this.address = address;
        this.mobile = mobile;
        this.whatsapp = whatsapp;
        this.gmail = gmail;
        this.religion = religion;
        this.caste = caste;
    }

    public String getSudentid() {
        return sudentid;
    }

    public void setSudentid(String sudentid) {
        this.sudentid = sudentid;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


    public String getStudentname() {
        return studentname;
    }

    public void setStudentname(String studentname) {
        this.studentname = studentname;
    }

    public String getGeotag() {
        return geotag;
    }

    public void setGeotag(String geotag) {
        this.geotag = geotag;
    }

    public String getFathername() {
        return fathername;
    }

    public void setFathername(String fathername) {
        this.fathername = fathername;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getEduQualification() {
        return eduQualification;
    }

    public void setEduQualification(String eduQualification) {
        this.eduQualification = eduQualification;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getWhatsapp() {
        return whatsapp;
    }

    public void setWhatsapp(String whatsapp) {
        this.whatsapp = whatsapp;
    }

    public String getGmail() {
        return gmail;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }

    public String getRegisterid() {
        return registerid;
    }

    public void setRegisterid(String registerid) {
        this.registerid = registerid;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    public String getPanchayat() {
        return panchayat;
    }

    public void setPanchayat(String panchayat) {
        this.panchayat = panchayat;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDegreeName() {
        return degreeName;
    }

    public void setDegreeName(String degreeName) {
        this.degreeName = degreeName;
    }


    public String getPercentGpa() {
        return percentGpa;
    }

    public void setPercentGpa(String percentGpa) {
        this.percentGpa = percentGpa;
    }

    public String getClassMark() {
        return classMark;
    }

    public void setClassMark(String classMark) {
        this.classMark = classMark;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public String getCaste() {
        return caste;
    }

    public void setCaste(String caste) {
        this.caste = caste;
    }
}
