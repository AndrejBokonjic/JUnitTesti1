package papyrus.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name="uporabnik_tekmovanje")
public class UporabnikTekmovanje {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "uporabnik_id")
    @JsonIgnore
    private Uporabnik uporabnik;

    @ManyToOne
    @JoinColumn(name = "tekmovanje_id")
    @JsonIgnore
    private Tekmovanje tekmovanje;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Uporabnik getUporabnik() {
        return uporabnik;
    }

    public void setUporabnik(Uporabnik uporabnik) {
        this.uporabnik = uporabnik;
    }

    public Tekmovanje getTekmovanje() {
        return tekmovanje;
    }

    public void setTekmovanje(Tekmovanje tekmovanje) {
        this.tekmovanje = tekmovanje;
    }
}
