package entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Question {

    private UUID id;
    private String name;
    private String createdBy;
    private List<Answer> answers;
    private UUID topicId;
    private UUID testId;
}
