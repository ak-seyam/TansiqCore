package io.asiam.tansiq.models;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
public class Result {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @OneToOne
    @JoinColumn(name = "STUDENT_ID")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Student student;

    @OneToOne
    @JoinColumn(name = "MAJOR_ID")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Major major;
}