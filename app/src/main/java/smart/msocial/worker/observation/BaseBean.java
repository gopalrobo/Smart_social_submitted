package smart.msocial.worker.observation;

public class BaseBean {
    public String semester;
    public String sheet;
    public String userId;
    public String name;
    public String marks;
    public String data;
    public String status;


    public BaseBean(String semester ,String sheet,String userId,String name, String marks,String data,
    String status) {
        this.semester = semester;
        this.userId=userId;
        this.sheet = sheet;
        this.name = name;
        this.marks = marks;
        this.data=data;
        this.status=status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getSheet() {
        return sheet;
    }

    public void setSheet(String sheet) {
        this.sheet = sheet;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMarks() {
        return marks;
    }

    public void setMarks(String marks) {
        this.marks = marks;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
