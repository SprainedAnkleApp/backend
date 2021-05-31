package pl.edu.agh.ki.io.api;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.ki.io.api.models.PeakCompletionRequest;
import pl.edu.agh.ki.io.api.models.PeakCompletionResponse;
import pl.edu.agh.ki.io.api.models.UserResponse;
import pl.edu.agh.ki.io.api.providers.PeakStatisticsProvider;
import pl.edu.agh.ki.io.db.PeakCompletionsStorage;
import pl.edu.agh.ki.io.db.PeakStorage;
import pl.edu.agh.ki.io.models.Peak;
import pl.edu.agh.ki.io.models.PeakCompletion;
import pl.edu.agh.ki.io.models.PeakCompletionKey;
import pl.edu.agh.ki.io.models.User;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@Api(tags = "PeakCompletions")
@RequestMapping("api/public/peakCompletions")
public class PeakCompletionsApiController {
    private final PeakCompletionsStorage peakCompletionsStorage;
    private final PeakStorage peakStorage;
    private final PeakStatisticsProvider peakStatisticsProvider;

    private boolean peakExists(Long peakId) {
        Optional<Peak> peakOptional = this.peakStorage.findPeakById(peakId);
        return peakOptional.isPresent();
    }

    @GetMapping()
    public List<PeakCompletionResponse> peakCompletions() {
        return this.peakCompletionsStorage.findAll().stream()
                .map(PeakCompletionResponse::fromPeakCompletion)
                .collect(Collectors.toList());
    }

    @GetMapping("/{peakid}/first")
    public ResponseEntity<UserResponse> peakFirstCompletion(@PathVariable("peakid") Long peakId) {
        if (!peakExists(peakId)) return ResponseEntity.notFound().build();
        return this.peakStatisticsProvider.getFirstCompletionForId(peakId)
                .map(peakCompletion -> new ResponseEntity<>(UserResponse.fromUser(peakCompletion.getUser()), HttpStatus.OK))
                .orElse(ResponseEntity.noContent().build());
    }

    @GetMapping("/{peakid}/totalCompletions")
    public ResponseEntity<Long> peakTotalCompletions(@PathVariable("peakid") Long peakId) {
        if (!peakExists(peakId)) return ResponseEntity.notFound().build();

        return new ResponseEntity<>(this.peakStatisticsProvider.getTotalCompletionForId(peakId), HttpStatus.OK);
    }

    @GetMapping("/{peakid}/averageTime")
    public ResponseEntity<Double> peakAverageTimeCompletion(@PathVariable("peakid") Long peakId) {
        if (!peakExists(peakId)) return ResponseEntity.notFound().build();
        return new ResponseEntity<>(this.peakStatisticsProvider.getAverageTimeCompletionForId(peakId), HttpStatus.OK);
    }

    @GetMapping("/{peakid}/fastest")
    public ResponseEntity<PeakCompletionResponse> peakFastestCompletionBy(@PathVariable("peakid") Long peakId) {
        if (!peakExists(peakId)) return ResponseEntity.notFound().build();
        return this.peakStatisticsProvider.getFastestCompletionForId(peakId)
                .map(completion -> new ResponseEntity<>(PeakCompletionResponse.fromPeakCompletion(completion), HttpStatus.OK))
                .orElse(ResponseEntity.noContent().build());
    }

    @GetMapping("/{peakid}/latest")
    public ResponseEntity<List<UserResponse>> peakLatestCompletions(@PathVariable("peakid") Long peakId) {
        if (!peakExists(peakId)) return ResponseEntity.notFound().build();

        List<PeakCompletion> latestCompletions = this.peakStatisticsProvider.getLatestCompletionsForId(peakId);
        if (latestCompletions.isEmpty()) return ResponseEntity.noContent().build();
        return new ResponseEntity<>(
                latestCompletions.stream()
                        .map(PeakCompletion::getUser)
                        .map(UserResponse::fromUser)
                        .collect(Collectors.toList()),
                HttpStatus.OK
        );
    }

    @PostMapping()
    public ResponseEntity<PeakCompletionResponse> completePeak(@RequestBody PeakCompletionRequest request, @AuthenticationPrincipal User user) {
        Optional<Peak> peakOptional = peakStorage.findPeakById(request.getPeakId());
        return peakOptional
                .map(peak -> {
                    PeakCompletion peakCompletion = new PeakCompletion(
                            new PeakCompletionKey(user.getId(), request.getPeakId()),
                            user,
                            peakOptional.get(),
                            Duration.ofMinutes(request.getTime())
                    );
                    peakCompletionsStorage.createPeakCompletion(peakCompletion);
                    return new ResponseEntity<>(PeakCompletionResponse.fromPeakCompletion(peakCompletion), HttpStatus.CREATED);
                }).orElse(ResponseEntity.notFound().build());
    }
}
