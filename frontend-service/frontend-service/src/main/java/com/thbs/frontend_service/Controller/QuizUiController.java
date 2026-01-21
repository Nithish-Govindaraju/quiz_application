package com.thbs.frontend_service.Controller;

import com.thbs.frontend_service.Feign.QuizFeignClient;
import com.thbs.frontend_service.Model.QuestionWrapper;
import com.thbs.frontend_service.Model.QuizDto;
import com.thbs.frontend_service.Model.ResponseListWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class QuizUiController {

    @Autowired
    private QuizFeignClient quizFeignClient;

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/quiz/create")
    public String createQuizPage() {
        return "create-quiz";
    }

    @PostMapping("/quiz/create")
    public String createQuiz(
            @RequestParam String title,
            @RequestParam String category,
            @RequestParam Integer numQuestions
    ) {
        QuizDto quizDto = new QuizDto(title, category, numQuestions);
        ResponseEntity<String> response = quizFeignClient.createQuiz(quizDto);
        String body = response.getBody();
        body = body.substring(8);
        Integer id=Integer.parseInt(body);
        return "redirect:/quiz/start/"+id;
    }

    @GetMapping("/quiz/start/{id}")
    public String startQuiz(@PathVariable Integer id, Model model) {

        List<QuestionWrapper> questions =
                quizFeignClient.getQuizQuestions(id).getBody();

        model.addAttribute("id", id);
        model.addAttribute("questions", questions);

        return "quiz";
    }

    @PostMapping("/quiz/submit")
    public String submitQuiz(
            @RequestParam Integer id,
            @ModelAttribute ResponseListWrapper wrapper,
            Model model
    ) {
        Integer score =
                quizFeignClient
                        .submitQuiz(id, wrapper.getResponses())
                        .getBody();


        model.addAttribute("score", score);
        model.addAttribute("total", wrapper.getResponses().size());

        return "result";
    }
}

