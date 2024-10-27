
package papyrus.dto;

public class OcenaRequest {
    private Long uporabnikId;
    private Long objavaId;
    private int grade;


    public Long getUporabnikId() {
        return uporabnikId;
    }

    public void setUporabnikId(Long uporabnikId) {
        this.uporabnikId = uporabnikId;
    }

    public Long getObjavaId() {
        return objavaId;
    }

    public void setObjavaId(Long objavaId) {
        this.objavaId = objavaId;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }
}