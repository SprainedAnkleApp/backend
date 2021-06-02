package pl.edu.agh.ki.io.api;


import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.agh.ki.io.api.providers.AchievementsProvider;
import pl.edu.agh.ki.io.db.PeakCompletionsStorage;
import pl.edu.agh.ki.io.models.User;

import java.util.List;


@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@Api(tags = "Achievements")
@RequestMapping("api/public/achievements")
public class AchievementsApiController {
    private final PeakCompletionsStorage peakCompletionsStorage;
    private final AchievementsProvider achievementsProvider;

    @GetMapping("/achievements")
    public ResponseEntity<List<AchievementsProvider.Achievement>> getAchievements(@AuthenticationPrincipal User user) {
        return new ResponseEntity<>(this.achievementsProvider.getAchievements(user), HttpStatus.OK);
    }
}
