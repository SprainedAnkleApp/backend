package pl.edu.agh.ki.io.api;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.ki.io.api.models.PeakCompletionResponse;
import pl.edu.agh.ki.io.api.models.PeakShortResponse;
import pl.edu.agh.ki.io.api.models.PeakResponse;
import pl.edu.agh.ki.io.api.providers.PeakStatisticsProvider;
import pl.edu.agh.ki.io.db.PeakCompletionsStorage;
import pl.edu.agh.ki.io.db.PeakStorage;
import pl.edu.agh.ki.io.models.Peak;
import pl.edu.agh.ki.io.models.PeakCompletion;
import pl.edu.agh.ki.io.models.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@Api(tags = "Peaks")
@RequestMapping("api/public/peaks")
public class PeaksApiController {
    private final PeakStorage peakStorage;
    private final PeakCompletionsStorage peakCompletionsStorage;
    private final PeakStatisticsProvider peakStatisticsProvider;

    private Map<String, Object> getPeakStats(Long peakId) {
        Map<String, Object> stats = new HashMap<>();
        stats.put("time_fastest", this.peakStatisticsProvider.getFastestCompletionForId(peakId)
                .map(PeakCompletionResponse::fromPeakCompletionWithUser).orElse(null));
        stats.put("time_average", this.peakStatisticsProvider.getAverageTimeCompletionForId(peakId));

        stats.put("completion_total", this.peakStatisticsProvider.getTotalCompletionForId(peakId));
        stats.put("completion_first", this.peakStatisticsProvider.getFirstCompletionForId(peakId)
                .map(PeakCompletionResponse::fromPeakCompletionWithUser).orElse(null));
        stats.put("completion_latest", this.peakStatisticsProvider.getLatestCompletionsForId(peakId).stream()
                .map(PeakCompletionResponse::fromPeakCompletionWithUser).collect(Collectors.toList()));

        return stats;
    }

    @GetMapping()
    public List<PeakResponse> peaks(@AuthenticationPrincipal User user) {
        return this.peakStorage.findAll()
                .stream()
                .map(peak -> {
                    Optional<PeakCompletion> peakCompletion = peakCompletionsStorage.findByPeakIdAndUserId(peak.getId(), user.getId());
                    return PeakResponse.fromPeakWithCompletionAndStatistics(
                            peak,
                            peakCompletion.isPresent(),
                            peakCompletion.map(completion -> completion.getCompletionTime().toMinutes()).orElse(0L),
                            getPeakStats(peak.getId())
                    );
                })
                .collect(Collectors.toList());
    }

    @GetMapping("/names")
    public List<PeakShortResponse> peaksNames(@AuthenticationPrincipal User user) {
        return this.peakStorage.findAll()
                .stream()
                .map(PeakShortResponse::fromPeak)
                .collect(Collectors.toList());
    }

    @GetMapping("/{peakid}")
    public ResponseEntity<PeakResponse> peak(@PathVariable("peakid") Long peakId, @AuthenticationPrincipal User user) {
        Optional<Peak> peak = this.peakStorage.findPeakById(peakId);
        if (peak.isPresent()) {
            Optional<PeakCompletion> peakCompletion = peakCompletionsStorage.findByPeakIdAndUserId(peakId, user.getId());
            return new ResponseEntity<>(PeakResponse.fromPeakWithCompletionAndStatistics(
                    peak.get(),
                    peakCompletion.isPresent(),
                    peakCompletion.map(completion -> completion.getCompletionTime().toMinutes()).orElse(0L),
                    getPeakStats(peakId)), HttpStatus.OK
            );
        }
        return ResponseEntity.notFound().build();
    }
}
