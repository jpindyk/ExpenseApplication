package jp.expenseapp.service;

import jp.expenseapp.dto.UserDTO;
import jp.expenseapp.model.User;

import java.util.Optional;

public interface UserService {
    public void save (UserDTO userDTO);
    public Optional<User> findByName (String name);
    public Optional<User> findByEmail (String email);
    public User getLoggedUser();
}
