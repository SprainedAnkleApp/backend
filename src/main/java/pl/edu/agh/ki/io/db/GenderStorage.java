package pl.edu.agh.ki.io.db;

import org.springframework.stereotype.Service;
import pl.edu.agh.ki.io.models.Gender;

@Service
public class GenderStorage {
    private GenderRepository genderRepository;

    public GenderStorage(GenderRepository genderRepository) {
        this.genderRepository = genderRepository;
    }

    public Gender findGenderByLabel(String label) {
        return genderRepository.findGenderByLabel(label).orElseThrow();
    }
}
