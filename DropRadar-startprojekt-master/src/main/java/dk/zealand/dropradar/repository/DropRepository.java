package dk.zealand.dropradar.repository;
import dk.zealand.dropradar.model.Drop;
import dk.zealand.dropradar.database.DatabaseConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DropRepository {
    public List<Drop> findFrom(LocalDate date) throws SQLException {
        String sql = """
                SELECT id, name, category, release_date FROM drops
                WHERE release_date >= ? ORDER BY release_date, id
                """;
        List<Drop> dropList = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setObject(1, date);
            try (ResultSet rows = statement.executeQuery()) {
                while (rows.next()) {
                    dropList.add(new Drop(rows.getInt("id"), rows.getString("name"),
                            rows.getString("category"), rows.getObject("release_date", LocalDate.class)));
                }
            }
        }
        //System.out.println(dropList);
        return dropList;
    }

    public void insert(Drop drop) throws SQLException {

        String sql = """
                INSERT INTO drops (name, category, release_date)
                VALUES (?,?,?)""";

        try(Connection con = DatabaseConnection.getConnection();
        PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, drop.name());
            stmt.setString(2, drop.category());
            stmt.setObject(3, drop.date());

            stmt.executeUpdate();
        }

    }

    public boolean updateDate(int id, LocalDate date) throws SQLException {

        String sql = """
                UPDATE drops 
                SET release_date = ? 
                WHERE id = ?""";

        try(Connection con = DatabaseConnection.getConnection();
        PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setObject(1,date);
            stmt.setInt(2,id);

            return stmt.executeUpdate() > 0;
        }

    }

    public boolean deleteById(int id) throws SQLException {

        String sql = """
                DELETE FROM drops
                WHERE id = ?
                """;
        try (Connection con = DatabaseConnection.getConnection();
        PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1,id);

            return stmt.executeUpdate() > 0;
        }
    }
}

