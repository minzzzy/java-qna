package codesquad.web;

import codesquad.domain.Question;
import codesquad.domain.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class QuestionController {
    @Autowired
    QuestionRepository questionRepository;

    @PostMapping("/questions")
    public String create(Question question) {
        questionRepository.save(question);
        return "redirect:/";
    }

    @GetMapping("/")
    public String list(Model model) {
        model.addAttribute("questions", questionRepository.findAll());
        return "index";
    }

    @GetMapping("/questions/{index}")
    public String show(@PathVariable long index, Model model) {
        model.addAttribute("question", questionRepository.findById(index).get());
        return "/qna/show";
    }

    @GetMapping("/questions/{id}/form")
    public String updateForm(@PathVariable long id, Model model) {
        model.addAttribute("question", questionRepository.findById(id).get());
        return "/qna/updateForm";
    }

    @PutMapping("/questions/{id}")
    public String updateQuestion(@PathVariable long id, String contents) {
        Question question = questionRepository.findById(id).get();
        question.setContents(contents);
        questionRepository.save(question);
        return "redirect:/";
    }

    @DeleteMapping("/questions/{id}")
    public String deleteQuestion(@PathVariable long id) {
        questionRepository.deleteById(id);
        return "redirect:/";
    }
}
