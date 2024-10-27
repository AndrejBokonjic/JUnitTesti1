package papyrus.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import papyrus.models.Storitev;

import java.util.List;

public interface storitevRepository extends JpaRepository<Storitev, Long> {

    @Query("SELECT s FROM Storitev s WHERE s.trgovina_umetnika.id = ?1")
    List<Storitev> vrniStoritvePoTrgoviniId(Long trgovinaId);
}


