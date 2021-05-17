package pl.edu.agh.ki.io.db;

import org.springframework.stereotype.Service;
import pl.edu.agh.ki.io.models.Peak;

import java.util.List;
import java.util.Optional;

@Service
public class PeakStorage {
    private final PeakRepository peakRepository;

    public PeakStorage(PeakRepository peakRepository) {
        this.peakRepository = peakRepository;
    }

    public Peak findPeakByName(String name) {
        return peakRepository.findPeakByName(name).orElseThrow();
    }

    public List<Peak> findAll(){
        return this.peakRepository.findAll();
    }

    public Optional<Peak> findPeakById(Long peakId) {
        return this.peakRepository.findById(peakId);
    }
}
