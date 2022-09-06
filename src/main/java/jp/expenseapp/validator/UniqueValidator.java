package jp.expenseapp.validator;

import jp.expenseapp.dto.UserDTO;
import jp.expenseapp.repository.UserRepository;
import jp.expenseapp.service.UserService;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class UniqueValidator implements Validator {

    private final UserService userService;

    public UniqueValidator(UserService userService) {
        this.userService = userService;
    }


    @Override
    public boolean supports(Class<?> clazz) {
        return UserDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserDTO userDTO = (UserDTO) target;
        if(userService.findByName(userDTO.getName()).isPresent()) {
            errors.rejectValue("name",
                    null,
                    "User with name: " + userDTO.getName() + " already exist");
        }

        if(userService.findByEmail(userDTO.getEmail()).isPresent()) {
            errors.rejectValue("email",
                    null,
                    "User with email: " + userDTO.getEmail() + " already exist");
        }
    }
}
