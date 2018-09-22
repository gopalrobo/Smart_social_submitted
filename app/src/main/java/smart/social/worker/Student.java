package smart.social.worker;


public class Student {

    String sudentid;
    String image;
    String aadharnumber;
    String studentname;
    String geotag;
    String fathername;
    String sex;
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
    String accountnumber;
    String ifscnumber;


    public Student() {
    }


    public Student(String sudentid, String image, String aadharnumber, String studentname, String geotag, String degreeName) {
        this.sudentid = sudentid;
        this.image = image;
        this.aadharnumber = aadharnumber;
        this.studentname = studentname;
        this.geotag = geotag;
        this.degreeName = degreeName;
    }

    public void setId(String sudentid) {
        this.sudentid = sudentid;
    }

    public void setData(String sudentid, String image, String aadharnumber, String studentname, String geotag, String fathername, String sex, String eduQualification, String degreeName, String percentGpa, String classMark, String address, String mobile, String whatsapp, String gmail, String religion, String caste, String accountnumber, String ifscnumber) {
        this.sudentid = sudentid;
        this.image = image;
        this.aadharnumber = aadharnumber;
        this.studentname = studentname;
        this.geotag = geotag;
        this.fathername = fathername;
        this.sex = sex;
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
        this.accountnumber = accountnumber;
        this.ifscnumber = ifscnumber;
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

    public String getAadharnumber() {
        return aadharnumber;
    }

    public void setAadharnumber(String aadharnumber) {
        this.aadharnumber = aadharnumber;
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

    public String getAccountnumber() {
        return accountnumber;
    }

    public void setAccountnumber(String accountnumber) {
        this.accountnumber = accountnumber;
    }

    public String getIfscnumber() {
        return ifscnumber;
    }

    public void setIfscnumber(String ifscnumber) {
        this.ifscnumber = ifscnumber;
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
