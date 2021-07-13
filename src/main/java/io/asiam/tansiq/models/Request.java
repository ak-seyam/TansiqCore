package io.asiam.tansiq.models;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
public class Request {
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
