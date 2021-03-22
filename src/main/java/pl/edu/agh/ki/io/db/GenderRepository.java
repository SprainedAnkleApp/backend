package pl.edu.agh.ki.io.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.agh.ki.io.models.Gender;

@Repository
public interface GenderRepository extends JpaRepository<Gender, Integer> {
    // TODO: is it necessary?
}
