package pl.edu.agh.ki.io.db;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class PeakWithCompletion {
    private Long peakId;
    private String peakName;
    private Date createDate;

    public PeakWithCompletion(Long peakId, String peakName, Date createDate) {
        this.peakId = peakId;
        this.peakName = peakName;
        this.createDate = createDate;
    }
}
