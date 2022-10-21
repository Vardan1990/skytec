package dao.sql;


import config.DataSource;
import dao.UserDao;
import entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDaoSql implements UserDao {

    private static final Logger log = Logger.getLogger(UserDaoSql.class.getName());
    private static  Connection connection;
    static {
        try {
             connection = DataSource.createConnection();
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }
    @Override
    public User insert(User user) {
        String query = ("INSERT INTO USERS(name,clan_id,total_added_gold,last_added_gold) values(?,?,?,?);");
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, user.getName());
            preparedStatement.setLong(2, user.getClanId());
            preparedStatement.setLong(3, user.getTotalAddedGold());
            preparedStatement.setLong(4, user.getLastAddedGold());
            preparedStatement.execute();
            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    user.setId(resultSet.getInt(1));
                }
            }
        } catch (SQLException exception) {
            log.log(Level.SEVERE, exception.getMessage());
        }
        return user;
    }

    @Override
    public Optional<User> findById(Long id) {
        User user = null;
        String query = ("SELECT * FROM USERS where id=?");
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    user = createFromResultSet(resultSet);
                }
            }
        } catch (SQLException exception) {
            log.log(Level.SEVERE, "user not found", exception.getMessage());
        }
        return user == null ? Optional.empty() : Optional.of(user);
    }

    @Override
    public Optional<User> findByName(String name) {
        User user = null;
        String query = ("SELECT * FROM USERS where name=?");
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, name);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    user = createFromResultSet(resultSet);
                }
            }
        } catch (SQLException exception) {
            log.log(Level.SEVERE, "user not found", exception.getMessage());
        }
        return user == null ? Optional.empty() : Optional.of(user);
    }

    @Override
    public User update(User user) {
        String query = ("UPDATE USERS SET total_added_gold=?,last_added_gold=?,updated=?where id=?");
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(4, user.getId());
            preparedStatement.setLong(1, user.getTotalAddedGold());
            preparedStatement.setLong(2, user.getLastAddedGold());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(user.getUpdated()));
            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            log.log(Level.SEVERE, exception.getMessage());
        }
        return user;
    }

    private User createFromResultSet(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getLong("id"));
        user.setName(resultSet.getString("name"));
        user.setClanId(resultSet.getLong("clan_id"));
        user.setTotalAddedGold(resultSet.getLong("total_added_gold"));
        user.setLastAddedGold(resultSet.getLong("last_added_gold"));
        return user;
    }
}
