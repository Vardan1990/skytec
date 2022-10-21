package service;


import entity.User;

import java.util.Optional;

public interface UserService {

    User save(User user);

    User update(User user);

    Optional<User> get(long userId);

    Optional<User> findByName(String name);
}
