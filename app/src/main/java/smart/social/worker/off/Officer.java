package smart.social.worker.off;

/**
 * Created by Lincoln on 15/01/16.
 */
public class Officer {
    private String name, designation, senior;
    private String password;

    public Officer() {
    }

    public Officer(String name, String designation, String senior) {
        this.name = name;
        this.designation = designation;
        this.senior = senior;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getSenior() {
        return senior;
    }

    public void setSenior(String senior) {
        this.senior = senior;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
