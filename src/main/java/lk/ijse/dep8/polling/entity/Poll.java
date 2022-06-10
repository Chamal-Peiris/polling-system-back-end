package lk.ijse.dep8.polling.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data @AllArgsConstructor @NoArgsConstructor @Entity
public class Poll implements SuperEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private String title;
    @Column(name = "up_votes", nullable = false)
    private int upVotes;
    @Column(name = "down_votes", nullable = false)
    private int downVotes;
    @Column(name = "created_by", nullable = false)
    private String createdBy;

    public Poll(String title, int upVotes, int downVotes, String createdBy) {
        this.title = title;
        this.upVotes = upVotes;
        this.downVotes = downVotes;
        this.createdBy = createdBy;
    }
}
