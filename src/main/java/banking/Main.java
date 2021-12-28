package banking;

public class Main {

  public static void main(String[] args) {
    final DBManager dbManager = new DBManager(args);
    new BankingApp().execute(dbManager);
  }
}
