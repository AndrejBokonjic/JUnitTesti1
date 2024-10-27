package papyrus.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import papyrus.models.Komentar;

public interface komentarRepository extends JpaRepository<Komentar, Long> {
}
