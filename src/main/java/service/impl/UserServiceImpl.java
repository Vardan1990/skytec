package service.impl;


import dao.UserDao;
import dao.sql.UserDaoSql;
import entity.User;
import service.UserService;

import java.util.Optional;
import java.util.logging.Logger;

public class UserServiceImpl implements UserService {

    private static final Logger log = Logger.getLogger(UserServiceImpl.class.getName());

    private final UserDao userDao;

    public UserServiceImpl() {
        this.userDao = new UserDaoSql();
    }

    public void createUser() {
        Optional<User> userOptional = findByName("FirstName");
        if (!userOptional.isPresent()) {
            log.info("create default user");
            User user = new User();
            user.setName("FirstUser");
            user.setClanId(1);
            user.setTotalAddedGold(0);
            user.setLastAddedGold(0);
            save(user);
        }
    }

    @Override
    public User save(User user) {
        return userDao.insert(user);
    }

    @Override
    public User update(User user) {
        return userDao.update(user);
    }

    @Override
    public Optional<User> get(long userId) {
        return userDao.findById(userId);
    }

    @Override
    public Optional<User> findByName(String userName) {
        return userDao.findByName(userName);
    }
}
