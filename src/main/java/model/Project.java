package model;

public class Project {

    private long id_Projeckt;
    private String projectName;
    private long employeeNummer;
    private float workhour;
    private float overtime;
    private float undertime;
    private String period;

    public Project(String projectName, long employeeNummer, float workhour, float overtime, float undertime, String period) {
        this.projectName = projectName;
        this.employeeNummer = employeeNummer;
        this.workhour = workhour;
        this.overtime = overtime;
        this.undertime = undertime;
        this.period = period;
    }

    public Project(long id_Projeckt, String projectName, long employeeNummer, float workhour, float overtime, float undertime, String period) {
        this.id_Projeckt = id_Projeckt;
        this.projectName = projectName;
        this.employeeNummer = employeeNummer;
        this.workhour = workhour;
        this.overtime = overtime;
        this.undertime = undertime;
        this.period = period;
    }

    public long getId_Projeckt() {
        return id_Projeckt;
    }

    public void setId_Projeckt(long id_Projeckt) {
        this.id_Projeckt = id_Projeckt;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public long getEmployeeNummer() {
        return employeeNummer;
    }

    public void setEmployeeNummer(long employeeNummer) {
        this.employeeNummer = employeeNummer;
    }

    public float getWorkhour() {
        return workhour;
    }

    public void setWorkhour(float workhour) {
        this.workhour = workhour;
    }

    public float getOvertime() {
        return overtime;
    }

    public void setOvertime(float overtime) {
        this.overtime = overtime;
    }

    public float getUndertime() {
        return undertime;
    }

    public void setUndertime(float undertime) {
        this.undertime = undertime;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }
}
