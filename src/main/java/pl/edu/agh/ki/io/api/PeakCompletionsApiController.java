package pl.edu.agh.ki.io.api;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.ki.io.db.PeakCompletionsStorage;
import pl.edu.agh.ki.io.db.PeakStorage;
import pl.edu.agh.ki.io.models.Peak;
import pl.edu.agh.ki.io.models.PeakCompletion;
import pl.edu.agh.ki.io.models.PeakCompletionKey;
import pl.edu.agh.ki.io.models.User;

import java.sql.Time;
import java.time.Duration;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@Api(tags = "PeakCompletions")
@RequestMapping("api/public/peakCompletions")
public class PeakCompletionsApiController {
    private final PeakCompletionsStorage peakCompletionsStorage;
    private final PeakStorage peakStorage;


    @GetMapping()
    public List<PeakCompletion> peakCompletions() {
        return this.peakCompletionsStorage.findAll();
    }


    @PostMapping()
    public ResponseEntity<PeakCompletion> completePeak(@RequestBody PeakCompletionRequest request, @AuthenticationPrincipal User user) {
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
                    return new ResponseEntity<>(peakCompletion, HttpStatus.CREATED);
                }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
