package dao.sql;

import config.DataSource;
import dao.MetaTaskDao;
import entity.MetaTask;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MetaTaskDaoSql implements MetaTaskDao {

    private static final Logger log = Logger.getLogger(MetaTaskDaoSql.class.getName());
    private static  Connection connection;
    static {
        try {
            connection = DataSource.createConnection();
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public MetaTask insert(MetaTask metaTask) {
        String query = ("INSERT INTO META_TASK(status,type,clan_id,gold) values(?,?,?,?);");
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, metaTask.getStatus());
            preparedStatement.setString(2, metaTask.getType());
            preparedStatement.setLong(3, metaTask.getClanId());
            preparedStatement.setLong(4, metaTask.getGold());
            preparedStatement.execute();
            log.info("The metaTask has been created successfully.");
            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    metaTask.setId(resultSet.getInt(1));
                }
            }
        } catch (SQLException exception) {
            log.log(Level.SEVERE, exception.getMessage());
        }
        return metaTask;
    }

    @Override
    public Optional<MetaTask> findById(long id) {
        MetaTask metaTask = null;
        String query = ("SELECT * FROM META_TASK where id=?");
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    metaTask = new MetaTask();
                    metaTask.setId(resultSet.getLong("id"));
                    metaTask.setStatus(resultSet.getString("status"));
                    metaTask.setType(resultSet.getString("type"));
                    metaTask.setClanId(resultSet.getLong("clan_id"));
                    metaTask.setGold(resultSet.getLong("gold"));
                }
            }
        } catch (SQLException exception) {
            log.log(Level.SEVERE, exception.getMessage());
        }
        return metaTask == null ? Optional.empty() : Optional.of(metaTask);
    }

    @Override
    public List<MetaTask> findByStatus(String status) {
        List<MetaTask> metaTaskList = new ArrayList<>();
        String query = ("SELECT * FROM META_TASK where status=?");
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, status);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                MetaTask metaTask = new MetaTask();
                metaTask.setId(resultSet.getLong("id"));
                metaTask.setStatus(resultSet.getString("status"));
                metaTask.setType(resultSet.getString("type"));
                metaTask.setClanId(resultSet.getLong("clan_id"));
                metaTask.setGold(resultSet.getLong("gold"));
                metaTaskList.add(metaTask);
            }
        } catch (SQLException exception) {
            log.log(Level.SEVERE, exception.getMessage());
        }
        return metaTaskList;
    }

    @Override
    public MetaTask update(MetaTask metaTask) {
        String query = ("UPDATE META_TASK SET status=?,updated=? where id=?");
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(3, metaTask.getId());
            preparedStatement.setString(1, metaTask.getStatus());
            preparedStatement.setTimestamp(2, Timestamp.valueOf(metaTask.getUpdated()));
            preparedStatement.executeUpdate();
            log.info("The metaTask has been updated successfully.");
        } catch (SQLException exception) {
            log.log(Level.SEVERE, exception.getMessage());
        }
        return metaTask;
    }
}
