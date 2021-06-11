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

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AchievementsProvider {
    private final PeakCompletionsStorage peakCompletionsStorage;

    Logger logger = LoggerFactory.getLogger(AchievementsProvider.class);

    public List<Achievement> getAchievements(User user) {
        String peakCompletionTemplate = "Zdobądź szczyt %s";
        String numberPeakCompletionTemplate = "Zdobądź %d szczyt%s";
        String numberTimeConstrainedPeakCompletionTemplate = "Zdobądź %d szczyt%s w %d dni";
        String regionPeakCompletionTemplate = "Zdobądź wszystkie szczyty w regionie %s";

        List<Achievement> achievements = new LinkedList<>();

        List<PeakWithCompletion> peakWithCompletions = this.peakCompletionsStorage.getPeaksWithCompletionIfExist(user.getId());
        for (PeakWithCompletion p : peakWithCompletions) {
            Achievement achievement = new Achievement();
            achievement.setAchievementTitle(peakCompletionTemplate.formatted(p.getPeakName()));
            achievement.setPeakId(p.getPeakId());
            achievement.setToComplete(1);

            if (p.getCreateDate() != null) {
                achievement.setCompleted(true);
                achievement.setProgress(1);
            } else {
                achievement.setCompleted(false);
                achievement.setProgress(0);
            }

            achievements.add(achievement);
        }

        int achievementsCount = achievements.stream().map(achievement -> achievement.isCompleted() ? 1 : 0).reduce(0, Integer::sum);

        for (int i : List.of(2, 5, 10)){
            Achievement achievement = new Achievement();
            achievement.setAchievementTitle(numberPeakCompletionTemplate.formatted(i, i != 2 ? "ów" : "y"));
            achievement.setToComplete(i);

            achievement.setProgress(Math.min(i, achievementsCount));
            if (achievementsCount >= i) {
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

        Map<String, Achievement> regionAchievements = new HashMap<>();
        for (PeakWithCompletion p: peakWithCompletions) {
            if (regionAchievements.containsKey(p.getRegion())){
                Achievement achievement = regionAchievements.get(p.getRegion());
                achievement.setToComplete(achievement.toComplete + 1);
                if (p.getCreateDate() != null) achievement.setProgress(achievement.progress + 1);
                achievement.setCompleted(achievement.progress == achievement.toComplete);
            } else {
                Achievement achievement = new Achievement();
                achievement.setAchievementTitle(regionPeakCompletionTemplate.formatted(p.getRegion()));
                achievement.setToComplete(1);
                achievement.setProgress(p.getCreateDate() != null ? 1 : 0);
                achievement.setCompleted(achievement.progress == achievement.toComplete);
                regionAchievements.put(p.getRegion(), achievement);
            }
        }

        achievements.addAll(regionAchievements.values());

        Achievement achievement = new Achievement();
        achievement.setAchievementTitle("Zdobądź wszystkie szczyty");
        achievement.setToComplete(peakWithCompletions.size());
        achievement.setProgress(achievementsCount);
        achievement.setCompleted(achievementsCount == peakWithCompletions.size());

        achievements.add(achievement);

        return achievements;
    }

    public List<Achievement> getAchievementsShort(User user) {
        List<Achievement> achievements = getAchievements(user);

        List<Achievement> completed = achievements.stream().filter(Achievement::isCompleted).limit(5).collect(Collectors.toList());
        List<Achievement> uncompleted = achievements.stream().filter(achievement -> !achievement.isCompleted()).sorted().limit(5).collect(Collectors.toList());

        List<Achievement> shortAchievements = new LinkedList<>();
        if(completed.size() < 2){
            shortAchievements.addAll(completed);
            shortAchievements.addAll(uncompleted.subList(0, 5 - completed.size()));
        } else {
            if (uncompleted.size() < 3) {
                shortAchievements.addAll(completed.subList(0, 5 - uncompleted.size()));
                shortAchievements.addAll(uncompleted);
            } else {
                shortAchievements.addAll(completed.subList(0 ,2));
                shortAchievements.addAll(uncompleted.subList(0, 3));
            }
        }
        return shortAchievements;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Achievement implements Comparable<Achievement>{
        private String achievementTitle;
        private boolean completed = false;
        private int progress;
        private int toComplete;
        private Long peakId;


        @Override
        public int compareTo(Achievement o) {
            return Integer.compare(o.progress, this.progress);
        }
    }
}
