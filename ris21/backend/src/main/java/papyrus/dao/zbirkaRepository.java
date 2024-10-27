package papyrus.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import papyrus.models.Uporabnik;
import papyrus.models.Zbirka;

import java.util.List;

@Repository
public interface zbirkaRepository extends JpaRepository<Zbirka, Long> {
    @Query("SELECT z FROM Zbirka z WHERE z.id = ?1")
    List<Zbirka> vrniZbirkeID(Long id);

    @Query("SELECT z FROM Zbirka z WHERE z.uporabnik.id = ?1 AND z.id = ?2")
    List<Zbirka> vrniZbirkeUporabnikaID(Long uporabnikId, Long zbirkaId);

    // Query to select the latest zbirka
    @Query("SELECT MAX(z.id) FROM Zbirka z")
    Long findMaxZbirkaId();

}
