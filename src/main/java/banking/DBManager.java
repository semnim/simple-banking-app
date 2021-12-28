package banking;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.sqlite.SQLiteDataSource;

public class DBManager {

  private SQLiteDataSource dataSource;
  final String url;

  public DBManager(String[] args) {
    if (args.length > 1) {
      System.out.println("\n" + "=".repeat(70));
      System.out.printf("Choosing DB '%s' in project directory.%n", args[1]);
      System.out.println("-".repeat(70));
      url = "jdbc:sqlite:".concat(args[1]);
    } else {
      System.out.println("\n" + "=".repeat(70));
      System.out.println("No DB specified. Choosing default DB 'card.s3db' in project directory.");
      System.out.println("-".repeat(70));
      url = "jdbc:sqlite:card.s3db";
    }
    this.dataSource = new SQLiteDataSource();
    dataSource.setUrl(url);
    createTable();
  }

  private void createTable() {
    System.out.print("Initializing connection..." + " . ".repeat(12));
    try (Connection connection = dataSource.getConnection()) {
      System.out.print("Success!\nChecking for table, else creating new table... " + " . ".repeat(5));
      try (Statement createTable = connection.createStatement()) {

        createTable.executeUpdate(
            "CREATE TABLE IF NOT EXISTS card "
                + "(id INTEGER PRIMARY KEY,"
                + "number TEXT,"
                + "pin TEXT,"
                + "balance INTEGER DEFAULT 0)"
        );
        System.out.println("Success!");
        System.out.println("=".repeat(70) + "\n");
      } catch (SQLException s) {
        s.printStackTrace();
      }
    } catch (SQLException s) {
      s.printStackTrace();
    }
  }

  public void addAccount(CreditCard cc) {
    try (Connection connection = dataSource.getConnection()) {
      final String insert = "INSERT INTO card (number, pin) VALUES (?, ?)";
      try (PreparedStatement statement = connection.prepareStatement(insert)) {
        statement.setString(1, cc.getCardNumber());
        statement.setString(2, cc.getCardPin());
        statement.executeUpdate();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public void deleteAccount(String cardNumber) {
    try (Connection connection = dataSource.getConnection()) {
      final String delete = "DELETE FROM card WHERE number = ?";
      try (PreparedStatement statement = connection.prepareStatement(delete)) {
        statement.setString(1, cardNumber);
        statement.executeUpdate();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public long getBalance(String cardNumber) {
    try (Connection connection = dataSource.getConnection()) {
      final String select = "SELECT balance FROM card WHERE number = ?";
      try (PreparedStatement statement = connection.prepareStatement(select)) {
        statement.setString(1, cardNumber);
        ResultSet result = statement.executeQuery();
        return result.getLong("balance");
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return 0;
  }

  public void setBalance(String cardNumber, long deposit) {
    try (Connection connection = dataSource.getConnection()) {
      final String update = "UPDATE card SET balance = balance + ? WHERE number = ?";
      try (PreparedStatement statement = connection.prepareStatement(update)) {
        statement.setLong(1, deposit);
        statement.setString(2, cardNumber);
        statement.executeUpdate();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public boolean hasAuthentification(String cardNumber, String cardPin) {
    try (Connection connection = dataSource.getConnection()) {
      final String select = "SELECT * FROM card WHERE number = ? AND pin = ?";
      try (PreparedStatement statement = connection.prepareStatement(select)) {
        statement.setString(1, cardNumber);
        statement.setString(2, cardPin);
        ResultSet hasCard = statement.executeQuery();
        return hasCard.next();
      } catch (SQLException s) {
        s.printStackTrace();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return false;
  }

  public boolean accountExists(String cardNumber) {
    try (Connection connection = dataSource.getConnection()) {
      final String select = "SELECT number, COUNT(*) as cnt FROM card WHERE number = ?";
      try (PreparedStatement statement = connection.prepareStatement(select)) {
        statement.setString(1, cardNumber);
        ResultSet result = statement.executeQuery();
        return result.getInt("cnt") > 0;
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return false;
  }
}
