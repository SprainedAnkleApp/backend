package pl.edu.agh.ki.io.api;

import io.swagger.annotations.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.agh.ki.io.db.PeakStorage;
import pl.edu.agh.ki.io.models.Peak;

import java.util.List;
import java.util.Optional;

@RestController
@Api(tags = "Peaks")
@RequestMapping("api/public/peaks")
public class PeaksApiController {
    private final PeakStorage peakStorage;

    public PeaksApiController(PeakStorage peakStorage) {
        this.peakStorage = peakStorage;
    }

    @GetMapping()
    public List<Peak> peaks() {
        return this.peakStorage.findAll();
    }

    @GetMapping("{peakid}")
    public ResponseEntity<Peak> peak(@PathVariable("peakid") Long peakId) {
        Optional<Peak> peak = this.peakStorage.findPeakById(peakId);
        return peak.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
