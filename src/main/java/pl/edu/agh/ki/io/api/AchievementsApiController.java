package pl.edu.agh.ki.io.api;


import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.ki.io.api.providers.AchievementsProvider;
import pl.edu.agh.ki.io.db.PeakCompletionsStorage;
import pl.edu.agh.ki.io.db.UserStorage;
import pl.edu.agh.ki.io.models.User;

import java.util.List;
import java.util.Optional;


@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@Api(tags = "Achievements")
@RequestMapping("api/public/achievements")
public class AchievementsApiController {
    private final AchievementsProvider achievementsProvider;
    private final UserStorage userStorage;

    @GetMapping("/{userId}")
    public ResponseEntity<List<AchievementsProvider.Achievement>> getAchievements(@PathVariable("userId") Long userId) {
        Optional<User> user = this.userStorage.findUserById(userId);

        return user.map(currentUser -> new ResponseEntity<>(this.achievementsProvider.getAchievements(currentUser), HttpStatus.OK))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/short")
    public ResponseEntity<List<AchievementsProvider.Achievement>> getAchievements(@AuthenticationPrincipal User user) {
        return new ResponseEntity<>(this.achievementsProvider.getAchievementsShort(user), HttpStatus.OK);
    }
}
