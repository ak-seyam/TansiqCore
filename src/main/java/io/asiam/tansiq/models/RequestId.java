package io.asiam.tansiq.models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@EqualsAndHashCode
@Data
public class RequestId {
    private UUID majorId;
    private UUID studentId;

    public RequestId(UUID majorId, UUID studentId) {
        this.majorId = majorId;
        this.studentId = studentId;
    }
}
