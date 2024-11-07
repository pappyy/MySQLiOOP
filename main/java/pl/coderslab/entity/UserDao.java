package pl.coderslab.entity;

import pl.coderslab.DbUtil;
import org.mindrot.jbcrypt.BCrypt;
import java.sql.*;
import java.util.Arrays;

public class UserDao {
    private static final String CREATE_USER_QUERY =
            "INSERT INTO users(username, email, password) VALUES (?, ?, ?)";

    public static final String READ_USER_QUERY = "SELECT * FROM users WHERE id = ?";

    public static final String UPDATE_USER_QUERY = "UPDATE users SET username = ?, email = ?, password = ? WHERE id = ?";

    public static final String DELETE_USER_QUERY = "DELETE FROM users WHERE id = ?";

    public static final String FIND_ALL_USERS_QUERY = "SELECT * FROM users";

    public User create(User user) {
        try(Connection conn = DbUtil.conn()) {
            PreparedStatement statement = conn.prepareStatement(CREATE_USER_QUERY, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getUserName());
            statement.setString(2, user.getEmail());
            statement.setString(3, hashPassword(user.getPassword()));
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                user.setId(rs.getInt(1));
            }
            return user;
        } catch ( SQLException e ) {
            e.printStackTrace();
            return null;
        }
    }
    public String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public User read(int userid) {
        try (Connection conn1 = DbUtil.conn()) {
            PreparedStatement statement = conn1.prepareStatement(READ_USER_QUERY);
            statement.setInt(1, userid);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUserName(rs.getString("username"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void update(User user) {
        try(Connection conn2 = DbUtil.conn()) {
            PreparedStatement statement = conn2.prepareStatement(UPDATE_USER_QUERY);
            statement.setString(1, user.getUserName());
            statement.setString(2, user.getEmail());
            statement.setString(3, this.hashPassword(user.getPassword()));
            statement.setInt(4, user.getId());
            statement.executeUpdate();
        } catch ( SQLException e ) {
            e.printStackTrace();
        }
    }

    public void delete(int userid) {
        try(Connection conn3 = DbUtil.conn()) {
            PreparedStatement statement = conn3.prepareStatement(DELETE_USER_QUERY);
            statement.setInt(1, userid);
            statement.executeUpdate();
        }catch ( SQLException e ) {
            e.printStackTrace();
        }
    }

    private User[] addToArray(User u, User[] users) {
        User[] tmpUsers = Arrays.copyOf(users, users.length + 1);
        tmpUsers[users.length] = u;
        return tmpUsers;
    }

    public User[] findAll() {
        try(Connection conn4 = DbUtil.conn()) {
            PreparedStatement statement = conn4.prepareStatement(FIND_ALL_USERS_QUERY);
            ResultSet rs = statement.executeQuery();
            User[] users = new User[0];
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUserName(rs.getString("username"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                users = addToArray(user, users);
            }
            return users;
        } catch ( SQLException e ) {
            e.printStackTrace();
            return null;
        }
    }


}
