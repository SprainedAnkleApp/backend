package pl.edu.agh.ki.io.api.providers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edu.agh.ki.io.db.PeakCompletionsStorage;
import pl.edu.agh.ki.io.models.PeakCompletion;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PeakStatisticsProvider {
    private final PeakCompletionsStorage peakCompletionsStorage;

    public Optional<PeakCompletion> getFastestCompletionForId(Long peakId){
        return this.peakCompletionsStorage.findFastestCompletionForPeak(peakId);
    }

    public Long getTotalCompletionForId(Long peakId) {
        return (long) this.peakCompletionsStorage.findByPeakId(peakId).size();
    }

    public Double getAverageTimeCompletionForId(Long peakId) {
        List<PeakCompletion> peakCompletions = peakCompletionsStorage.findByPeakId(peakId);
        return peakCompletions.size() == 0 ? 0.0 : Math.round(peakCompletions
                .stream()
                .map(peakCompletion -> peakCompletion.getCompletionTime().toMinutes())
                .reduce((long) 0, Long::sum).doubleValue() / peakCompletions.size());
    }

    public Optional<PeakCompletion> getFirstCompletionForId(Long peakId) {
        return this.peakCompletionsStorage.findFirstByPeakId(peakId);
    }

    public List<PeakCompletion> getLatestCompletionsForId(Long peakId) {
        return this.peakCompletionsStorage.findAtMost5LastCompletions(peakId);
    }
}
