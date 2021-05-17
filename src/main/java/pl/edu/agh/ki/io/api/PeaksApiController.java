package pl.edu.agh.ki.io.api;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.ki.io.db.PeakStorage;
import pl.edu.agh.ki.io.models.Peak;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@Api(tags = "Peaks")
@RequestMapping("api/public/peaks")
public class PeaksApiController {
    private final PeakStorage peakStorage;

    @GetMapping()
    public List<Peak> peaks() {
        return this.peakStorage.findAll();
    }

    @GetMapping("/{peakid}")
    public ResponseEntity<Peak> peak(@PathVariable("peakid") Long peakId) {
        Optional<Peak> peak = this.peakStorage.findPeakById(peakId);
        return peak.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
