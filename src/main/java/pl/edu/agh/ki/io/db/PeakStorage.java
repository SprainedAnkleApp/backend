package pl.edu.agh.ki.io.db;

import org.springframework.stereotype.Service;
import pl.edu.agh.ki.io.models.Peak;

@Service
public class PeakStorage {
    private PeakRepository peakRepository;

    public PeakStorage(PeakRepository peakRepository) {
        this.peakRepository = peakRepository;
    }

    public Peak findPeakByName(String name) {
        return peakRepository.findPeakByName(name).orElseThrow();
    }
}
