package io.asiam.tansiq.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Data
@Entity
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
@Table(name = "REQUEST", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"STUDENT_ID", "MAJOR_ID"})
})
public class Request implements Serializable {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @ManyToOne()
    @JoinColumn(name = "STUDENT_ID")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Student student;

    @Column(nullable = false)
    private int rank;

    @ManyToOne()
    @JoinColumn(name = "MAJOR_ID", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Major major;

    public Request(Student student, Major major, int rank) {
        this.student = student;
        this.rank = rank;
        this.major = major;
    }

    public Request() {
    }
}
