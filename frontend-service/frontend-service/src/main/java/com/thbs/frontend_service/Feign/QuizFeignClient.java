package com.thbs.frontend_service.Feign;

import com.thbs.frontend_service.Model.QuestionWrapper;
import com.thbs.frontend_service.Model.QuizDto;
import com.thbs.frontend_service.Model.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "API-GATEWAY")
public interface QuizFeignClient {

    @PostMapping("/quiz/create")
    ResponseEntity<String> createQuiz(@RequestBody QuizDto quizDto);

    @PostMapping("/quiz/get/{id}")
    ResponseEntity<List<QuestionWrapper>> getQuizQuestions(@PathVariable Integer id);

    @PostMapping("/quiz/submit")
    ResponseEntity<Integer> submitQuiz(
            @RequestParam  Integer id,
            @RequestBody List<Response> responses
    );
}
