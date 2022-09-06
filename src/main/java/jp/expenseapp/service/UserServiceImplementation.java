package jp.expenseapp.service;

import jp.expenseapp.dto.UserDTO;
import jp.expenseapp.model.User;
import jp.expenseapp.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImplementation implements UserService{
    private final UserRepository repository;
    private final ModelMapper modelMapper;

    public UserServiceImplementation(UserRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void save(UserDTO userDTO) {
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
