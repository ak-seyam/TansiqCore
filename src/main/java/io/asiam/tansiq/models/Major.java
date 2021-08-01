package io.asiam.tansiq.models;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@Data
@Entity
public class Major {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;
    private String name;
    @Column(nullable = false)
    private int limit;
    @Column(nullable = false)
    private int studentsCount = 0;
    @Column
    private int originalLimit;

    public Major() {
    }

    public Major(String name, int limit) {
        this.name = name;
        this.limit = limit;
        this.originalLimit = limit;
    }

    @Override
    public String toString() {
        return "Major{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", limit=" + limit +
                ", studentsCount=" + studentsCount +
                '}';
    }
}
