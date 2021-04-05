package pl.edu.agh.ki.io.db;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.agh.ki.io.models.Peak;

import java.util.Optional;

public interface PeakRepository extends JpaRepository<Peak, Long> {
    Optional<Peak> findPeakByName(String name);
}
