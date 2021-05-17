package pl.edu.agh.ki.io.db;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edu.agh.ki.io.models.PeakCompletion;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PeakCompletionsStorage {
    private final PeakCompletionsRepository peakCompletionsRepository;

    public List<PeakCompletion> findAll() {
        return this.peakCompletionsRepository.findAll();
    }

    public void createPeakCompletion(PeakCompletion peakCompletion) {
        if (peakCompletionsRepository.findById(peakCompletion.getId()).isEmpty())
            peakCompletionsRepository.save(peakCompletion);
    }

    public Optional<PeakCompletion> findFirstByPeakId(Long peakId) {
        return this.peakCompletionsRepository.findFirstByPeakIdOrderByCreateDate(peakId);
    }

    public List<PeakCompletion> findByPeakId(Long peakId) {
        return this.peakCompletionsRepository.findByPeakId(peakId);
    }
}
