package papyrus.dao;

import org.springframework.data.jpa.repository.Query;
import papyrus.models.Objava;
import papyrus.models.Trgovina_Umetnika;
import org.springframework.data.repository.CrudRepository;
import papyrus.models.Uporabnik;

import java.util.List;
import java.util.Optional;

public interface trgovinaUporabnikaRepository extends CrudRepository<Trgovina_Umetnika, Long>{

    @Query("SELECT tu.name AS umetnik_ime, u.name AS uporabnik_ime FROM Trgovina_Umetnika tu JOIN tu.uporabnik u")
    List<Trgovina_Umetnika> vrniTrgovinoUmetnika();

    Optional<Trgovina_Umetnika> findByUporabnikId(Long uporabnikId);


}
