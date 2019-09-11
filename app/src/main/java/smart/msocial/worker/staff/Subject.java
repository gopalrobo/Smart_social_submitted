package smart.msocial.worker.staff;

import java.io.Serializable;

public class Subject implements Serializable {
    String name;
    String code;
    String status;

    public Subject(String name, String code, String status) {
        this.name = name;
        this.code = code;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
