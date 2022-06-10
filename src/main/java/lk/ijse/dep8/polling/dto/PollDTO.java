package lk.ijse.dep8.polling.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Data @AllArgsConstructor @NoArgsConstructor
public class PollDTO implements Serializable {
    private Integer id;
    private String title;
    private int upVotes;
    private int downVotes;
    private String createdBy;

    public PollDTO(String title, int upVotes, int downVotes, String createdBy) {
        this.title = title;
        this.upVotes = upVotes;
        this.downVotes = downVotes;
        this.createdBy = createdBy;
    }
}
