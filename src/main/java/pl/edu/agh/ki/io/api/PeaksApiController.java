package pl.edu.agh.ki.io.api;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.ki.io.api.models.PeakResponse;
import pl.edu.agh.ki.io.db.PeakCompletionsStorage;
import pl.edu.agh.ki.io.db.PeakStorage;
import pl.edu.agh.ki.io.models.Peak;
import pl.edu.agh.ki.io.models.PeakCompletion;
import pl.edu.agh.ki.io.models.User;

import java.util.List;
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

    @GetMapping()
    public List<PeakResponse> peaks(@AuthenticationPrincipal User user) {
        return this.peakStorage.findAll()
                .stream()
                .map(peak -> PeakResponse.fromPeakWithCompletion(peak, peakCompletionsStorage.findByPeakIdAndUserId(peak.getId(), user.getId()).isPresent()))
                .collect(Collectors.toList());
    }

    @GetMapping("/{peakid}")
    public ResponseEntity<PeakResponse> peak(@PathVariable("peakid") Long peakId, @AuthenticationPrincipal User user) {
        Optional<Peak> peak = this.peakStorage.findPeakById(peakId);
        if(peak.isPresent()) {
           Optional<PeakCompletion> peakCompletion = peakCompletionsStorage.findByPeakIdAndUserId(peakId, user.getId());
           return  new ResponseEntity<>(PeakResponse.fromPeakWithCompletion(peak.get(), peakCompletion.isPresent()), HttpStatus.OK);
        }
        return ResponseEntity.notFound().build();
    }
}
