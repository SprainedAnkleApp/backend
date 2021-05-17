package pl.edu.agh.ki.io.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.agh.ki.io.models.PeakCompletion;
import pl.edu.agh.ki.io.models.PeakCompletionKey;

import java.util.List;
import java.util.Optional;

@Repository
public interface PeakCompletionsRepository extends JpaRepository<PeakCompletion, PeakCompletionKey> {
    List<PeakCompletion> findByPeakId(Long peakId);

    List<PeakCompletion> findByIdUserId(Long userId);

    Optional<PeakCompletion> findFirstByPeakIdOrderByCreateDate(Long peakId);
}
