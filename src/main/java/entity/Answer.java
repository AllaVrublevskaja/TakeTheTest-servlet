package entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Answer {
    private UUID id;
    private String name;
    private boolean isCorrect;
    private boolean answer;
    private UUID topicId;
    private UUID testId;
    private UUID questionId;
}
