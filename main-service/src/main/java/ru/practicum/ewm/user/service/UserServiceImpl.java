package ru.practicum.ewm.user.service;

import org.springframework.stereotype.Service;
import ru.practicum.ewm.user.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

}
