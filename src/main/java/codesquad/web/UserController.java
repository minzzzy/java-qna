package codesquad.web;

import codesquad.domain.User;
import codesquad.domain.UserRepository;
import codesquad.dto.UserRegisterDto;
import codesquad.dto.UserUpdateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @PostMapping("")
    public String create(UserRegisterDto dto) {
        userRepository.save(dto.toEntity());
        return "redirect:/users";
    }

    @GetMapping("")
    public String list(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "/user/list";
    }

    @GetMapping("/{index}")
    public String show(@PathVariable long index, Model model) {
        model.addAttribute("user", userRepository.findById(index).get());
        return "/user/profile";
    }

    @GetMapping("/{id}/form")
    public String updateForm(@PathVariable long id, Model model) {
        model.addAttribute("user", userRepository.findById(id).get());
        return "/user/updateForm";
    }

    @PutMapping("/{id}")
    public String updateUser(@PathVariable long id, UserUpdateDto dto) {
        User user = userRepository.findById(id).get();
        if (user.equalsPassword(dto.getCurrentPassword())) {
            user.update(dto);
            userRepository.save(user);
        }
        return "redirect:/users";
    }


}
