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
public class SelectedAnswer {
    private UUID id;
    private UUID questionId;
    private UUID selectedAnswerId;
    private String selectedAnswerName;
    private boolean isCorrect;
}
