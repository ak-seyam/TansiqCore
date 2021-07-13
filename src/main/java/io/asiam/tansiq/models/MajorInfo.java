package io.asiam.tansiq.models;

import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "MAJOR_INFO")
@Data
public class MajorInfo {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @OneToOne()
    @JoinColumn(name = "MAJOR_ID", referencedColumnName = "id", unique = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Major major;

    @OneToOne()
    @JoinColumn(name = "STUDENT_ID", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Student lowestStudent;

    public MajorInfo() {
    }

    public MajorInfo(Major major, Student lowestStudent) {
        this.major = major;
        this.lowestStudent = lowestStudent;
    }

    public MajorInfo(Major major) {
        this.major = major;
    }

}
