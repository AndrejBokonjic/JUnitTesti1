package papyrus.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import papyrus.models.Objava;
import papyrus.models.Tekmovanje;
import papyrus.models.Uporabnik;

import java.util.List;

public interface tekmovanjeRepository extends JpaRepository<Tekmovanje, Long> {

    @Query("SELECT t FROM Tekmovanje t JOIN FETCH t.objava WHERE t.id = ?1")
    List<Tekmovanje> pridobiPodatkeOTekmovanju(Long id);

    @Query("SELECT t.uporabniki FROM Tekmovanje t WHERE t.id = :tekmovanjeId")
    List<Uporabnik> pridobiUporabnikeZaTekmovanje(@Param("tekmovanjeId") Long tekmovanjeId);

}
