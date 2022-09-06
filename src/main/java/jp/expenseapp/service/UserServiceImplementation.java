package jp.expenseapp.service;

import jp.expenseapp.dto.UserDTO;
import jp.expenseapp.model.User;
import jp.expenseapp.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImplementation implements UserService{
    private final UserRepository repository;
    private final ModelMapper modelMapper;

    private final PasswordEncoder passwordEncoder;

    public UserServiceImplementation(UserRepository repository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void save(UserDTO userDTO) {
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        User user = mapToEntity(userDTO);
        user.setUserId(UUID.randomUUID().toString());
        repository.save(user);
    }

    @Override
    public Optional<User> findByName(String name) {
        return repository.findUserByName(name);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return repository.findUserByEmail(email);
    }

    private User mapToEntity(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }
}
