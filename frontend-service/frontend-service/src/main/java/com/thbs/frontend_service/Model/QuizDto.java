package com.thbs.frontend_service.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuizDto {
    String title;
    String categoryName;
    Integer numQuestions;

}
