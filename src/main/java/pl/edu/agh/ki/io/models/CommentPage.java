package pl.edu.agh.ki.io.models;

import lombok.Data;
import org.springframework.data.domain.Sort;

@Data
public class CommentPage {
        private int pageNumber = 0;
        private int pageSize = 10;
        private Sort.Direction sortDirection = Sort.Direction.DESC;
        private String sortBy = "createDate";
}
