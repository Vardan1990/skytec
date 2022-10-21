package dao;


import entity.User;

import java.util.Optional;

public interface UserDao {

    User insert(User user);

    User update(User user);

    Optional<User> findById(Long id);

    Optional<User> findByName(String name);
}
