package pl.edu.agh.ki.io.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.agh.ki.io.models.Gender;

import java.util.Optional;

@Repository
public interface GenderRepository extends JpaRepository<Gender, Integer> {
    Optional<Gender> findGenderByLabel(String label);
}
