package dao.sql;

import config.DataSource;
import dao.ClanDao;
import entity.Clan;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClanDaoSql implements ClanDao {

    private static final Logger log = Logger.getLogger(ClanDaoSql.class.getName());
    private static  Connection connection;
    static {
        try {
            connection = DataSource.createConnection();
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public Clan insert(Clan clan) {
        String query = ("INSERT INTO CLAN(name,gold,previous_gold_count) values(?,?,?);");
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, clan.getName());
            preparedStatement.setLong(2, clan.getGold());
            preparedStatement.setLong(3, clan.getPreviousGoldCount());
            preparedStatement.execute();
            log.info("The clan has been created successfully.");
            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    clan.setId(resultSet.getInt(1));
                }
            }
        } catch (SQLException exception) {
            log.log(Level.SEVERE, exception.getMessage());
        }
        return clan;
    }

    @Override
    public Optional<Clan> findById(Long clanId) {
        Clan clan = null;
        String query = ("SELECT * FROM CLAN where id=?");
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, clanId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    clan = createFromResultSet(resultSet);
                }
            }
        } catch (SQLException exception) {
            log.log(Level.SEVERE, exception.getMessage());
        }
        return clan == null ? Optional.empty() : Optional.of(clan);
    }

    @Override
    public Optional<Clan> findByName(String clanName) {
        Clan clan = null;
        String query = ("SELECT * FROM CLAN where name=?");
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, clanName);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    clan = createFromResultSet(resultSet);
                }
            }
        } catch (SQLException exception) {
            log.log(Level.SEVERE, exception.getMessage());
        }
        return clan == null ? Optional.empty() : Optional.of(clan);
    }

    @Override
    public Clan update(Clan clan) {
        String query = ("UPDATE CLAN SET gold=?,previous_gold_count=?,updated=? where id=?");
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(4, clan.getId());
            preparedStatement.setLong(1, clan.getGold());
            preparedStatement.setLong(2, clan.getPreviousGoldCount());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(clan.getUpdated()));
            preparedStatement.executeUpdate();
            log.info("The clan has been updated successfully.");
        } catch (SQLException exception) {
            log.log(Level.SEVERE, exception.getMessage());
        }
        return clan;
    }

    @Override
    public void deleteById(Long id) {
        String query = ("DELETE FROM CLAN where id=?");
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            log.info("The clan has been deleted successfully.");
        } catch (SQLException exception) {
            log.log(Level.SEVERE, exception.getMessage());
        }
    }

    private Clan createFromResultSet(ResultSet resultSet) throws SQLException {
        Clan clan = new Clan();
        clan.setId(resultSet.getLong("id"));
        clan.setName(resultSet.getString("name"));
        clan.setGold(resultSet.getLong("gold"));
        clan.setPreviousGoldCount(resultSet.getLong("previous_gold_count"));
        return clan;
    }
}
