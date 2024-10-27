package papyrus.dto;

import java.util.List;

public class RacunRequest {

    private Long uporabnikId;
    private List<Long> storitveIds;


    public Long getUporabnikId() {
        return uporabnikId;
    }

    public void setUporabnikId(Long uporabnikId) {
        this.uporabnikId = uporabnikId;
    }

    public List<Long> getStoritveIds() {
        return storitveIds;
    }

    public void setStoritveIds(List<Long> storitveIds) {
        this.storitveIds = storitveIds;
    }

}



