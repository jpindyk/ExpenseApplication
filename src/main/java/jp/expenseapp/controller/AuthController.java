package jp.expenseapp.controller;

import jp.expenseapp.dto.UserDTO;
import jp.expenseapp.repository.UserRepository;
import jp.expenseapp.service.UserService;
import jp.expenseapp.validator.UniqueValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
    }

    @GetMapping({"/login","/"})
    public String showLoginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegisterPage(Model model) {
        model.addAttribute("user", new UserDTO());
        return "register";
    }
    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("user") UserDTO userDTO,
                           BindingResult result,
                           Model model) {
        System.out.println("Printing the user details: " + userDTO);

        new UniqueValidator(userService).validate(userDTO, result);
        if(result.hasErrors()) {
            return "register";
        }
        userService.save(userDTO);
        model.addAttribute("successMessage", true);
        return "login";
    }
}
