package pl.edu.agh.ki.io.api.providers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.edu.agh.ki.io.db.PeakCompletionsStorage;
import pl.edu.agh.ki.io.db.PeakWithCompletion;
import pl.edu.agh.ki.io.models.User;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class AchievementsProvider {
    private final PeakCompletionsStorage peakCompletionsStorage;

    Logger logger = LoggerFactory.getLogger(AchievementsProvider.class);

    public List<Achievement> getAchievements(User user) {
        String peakCompletionTemplate = "Zdobądź szczyt %s";
        String numberPeakCompletionTemplate = "Zdobądź %d szczyt%s";
        String numberTimeConstrainedPeakCompletionTemplate = "Zdobądź %d szczyt%s w %d dni";

        List<Achievement> achievements = new LinkedList<>();

        int all_achievements = 0;
        int completed = 0;

        List<PeakWithCompletion> peakWithCompletions = this.peakCompletionsStorage.getPeaksWithCompletionIfExist(user.getId());
        for (PeakWithCompletion p : peakWithCompletions) {
            all_achievements++;
            Achievement achievement = new Achievement();
            achievement.setAchievementTitle(peakCompletionTemplate.formatted(p.getPeakName()));
            achievement.setPeakId(p.getPeakId());
            achievement.setToComplete(1);

            if (p.getCreateDate() != null) {
                completed++;
                achievement.setCompleted(true);
                achievement.setProgress(1);
                achievement.setCompletedAt(p.getCreateDate().getTime());
            } else {
                achievement.setCompleted(false);
                achievement.setProgress(0);
            }

            achievements.add(achievement);
        }

        int achievementsCount = achievements.stream().map(achievement -> achievement.isCompleted() ? 1 : 0).reduce(0, Integer::sum);

        for (int i : List.of(2, 5, 10)){
            all_achievements++;
            Achievement achievement = new Achievement();
            achievement.setAchievementTitle(numberPeakCompletionTemplate.formatted(i, i != 2 ? "ów" : "y"));
            achievement.setToComplete(i);

            achievement.setProgress(Math.min(i, achievementsCount));
            if (achievementsCount >= i) {
                completed++;
                achievement.setCompleted(true);
            }

            achievements.add(achievement);
        }

        for (Map.Entry<Integer, Integer> daysCompletions : Map.of(14, 2, 30, 5, 7, 2).entrySet()) {
            Achievement achievement = new Achievement();
            achievement.setAchievementTitle(numberTimeConstrainedPeakCompletionTemplate.formatted(
                    daysCompletions.getValue(),
                    daysCompletions.getValue() != 2 ? "ów" : "y",
                    daysCompletions.getKey()
            ));
            achievement.setToComplete(daysCompletions.getValue());

            int completedInTime = 0;
            for (PeakWithCompletion peakCompletion : peakWithCompletions){
                if (peakCompletion.getCreateDate() == null) break;

                long lowerLimit = peakCompletion.getCreateDate().getTime() - TimeUnit.DAYS.toMillis(daysCompletions.getKey());
                completedInTime = Math.max(completedInTime, peakWithCompletions.stream().map(completion ->
                        completion.getCreateDate() != null && completion.getCreateDate().getTime() >= lowerLimit ? 1 : 0).reduce(0, Integer::sum));
            }

            if (completedInTime >= daysCompletions.getValue()) {
                achievement.setProgress(daysCompletions.getValue());
                achievement.setCompleted(true);
            } else {
                achievement.setProgress(completedInTime);
                achievement.setCompleted(false);
            }
            achievements.add(achievement);
        }

        return achievements;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Achievement {
        private String achievementTitle;
        private boolean completed = false;
        private int progress;
        private int toComplete;
        private Long peakId;
        private Long timeLeft;
        private Long completedAt;
    }
}
