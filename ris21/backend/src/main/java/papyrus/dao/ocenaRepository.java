package papyrus.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import papyrus.models.Ocena;

public interface ocenaRepository  extends JpaRepository<Ocena, Long> {
}