package papyrus.models;

import jakarta.persistence.*;

@Entity
@Table(name = "racun_storitev")
public class RacunStoritev {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "racun_id")
    private Racun racun;

    @ManyToOne
    @JoinColumn(name = "storitev_id")
    private Storitev storitev;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Racun getRacun() {
        return racun;
    }

    public void setRacun(Racun racun) {
        this.racun = racun;
    }

    public Storitev getStoritev() {
        return storitev;
    }

    public void setStoritev(Storitev storitev) {
        this.storitev = storitev;
    }

}

