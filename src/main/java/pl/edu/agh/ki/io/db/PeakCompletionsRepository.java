package pl.edu.agh.ki.io.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.edu.agh.ki.io.models.PeakCompletion;
import pl.edu.agh.ki.io.models.PeakCompletionKey;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface PeakCompletionsRepository extends JpaRepository<PeakCompletion, PeakCompletionKey> {
    List<PeakCompletion> findByPeakId(Long peakId);

    List<PeakCompletion> findByIdUserId(Long userId);

    Optional<PeakCompletion> findFirstByPeakIdOrderByCreateDate(Long peakId);

    Optional<PeakCompletion> findByPeakIdAndUserId(Long peakId, Long userId);

    Optional<PeakCompletion> findTop1ByPeakIdOrderByCompletionTime(Long peakId);

    @Query(value = "select * from peak_completions c " +
            "where (c.peak_id = :peakId) and (c.create_date between :startDate and :endDate)" +
            "order by c.create_date Desc",
    nativeQuery = true)
    List<PeakCompletion> findTop5ByPeakIdBetweenOrderedByCompletionTimeDesc(@Param("peakId") Long peakId,
                                                                            @Param("startDate") Date start,
                                                                            @Param("endDate") Date end);

    List<PeakCompletion> findTop5ByPeakIdOrderByCompletionTimeDesc(Long peakId);
}
