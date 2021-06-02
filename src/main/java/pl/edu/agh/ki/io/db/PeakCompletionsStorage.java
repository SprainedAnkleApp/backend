package pl.edu.agh.ki.io.db;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edu.agh.ki.io.models.PeakCompletion;

import java.sql.Date;
import java.util.Collections;
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

    public Optional<PeakCompletion> findByPeakIdAndUserId(Long peakId, Long userId) {
        return this.peakCompletionsRepository.findByPeakIdAndUserId(peakId, userId);
    }

    public Optional<PeakCompletion> findFastestCompletionForPeak(Long peakId) {
        return this.peakCompletionsRepository.findTop1ByPeakIdOrderByCompletionTime(peakId);
    }

    public List<PeakCompletion> findAtMost5LastCompletionsLast7Days(Long peakId) {
        long msInDay = 24 * 60 * 60 * 1000;
        Date today = new Date(System.currentTimeMillis() + msInDay);
        Date weekAgo = new Date(today.getTime() - 7 * msInDay);

        return this.peakCompletionsRepository.findTop5ByPeakIdBetweenOrderedByCompletionTimeDesc(peakId, weekAgo, today);
    }

    public List<PeakWithCompletion> getPeaksWithCompletionIfExist(Long userId){
        return this.peakCompletionsRepository.findAllPeaksWithCompletionsIfExistByUserId(userId);
    }
}
