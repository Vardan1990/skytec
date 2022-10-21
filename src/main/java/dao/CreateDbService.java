package dao;

import config.DataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateDbService {

    private static  Connection connection;
    static {
        try {
            connection = DataSource.createConnection();
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }
    public void createTable() throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute("\n" +
                "create table users\n" +
                "(\n" +
                "    id               bigint primary key not null auto_increment,\n" +
                "    name             varchar(255)       not null,\n" +
                "    clan_id          bigint             not null,\n" +
                "    total_added_gold bigint,\n" +
                "    last_added_gold  bigint,\n" +
                "    created          TIMESTAMP          NOT NULL DEFAULT NOW(),\n" +
                "    updated          TIMESTAMP          NOT NULL DEFAULT NOW()\n" +
                ");\n" +
                "\n" +
                "create table clan\n" +
                "(\n" +
                "    id                  bigint primary key not null auto_increment,\n" +
                "    name                varchar(255)       not null,\n" +
                "    gold                bigint             not null,\n" +
                "    previous_gold_count bigint             not null,\n" +
                "    created             TIMESTAMP          NOT NULL DEFAULT NOW(),\n" +
                "    updated             TIMESTAMP          NOT NULL DEFAULT NOW()\n" +
                ");\n" +
                "\n" +
                "create table meta_task\n" +
                "(\n" +
                "    id      bigint primary key not null auto_increment,\n" +
                "    status  varchar(255)       not null,\n" +
                "    type    varchar(255)       not null,\n" +
                "    clan_id bigint             not null,\n" +
                "    gold    bigint             not null,\n" +
                "    created TIMESTAMP          NOT NULL DEFAULT NOW(),\n" +
                "    updated TIMESTAMP          NOT NULL DEFAULT NOW()\n" +
                ");");
    }
}
