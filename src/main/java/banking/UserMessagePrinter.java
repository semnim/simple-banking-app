package banking;

public class UserMessagePrinter {

  static void globalMenuMessage() {
    System.out.println("1. Create an account\n"
        + "2. Log into account\n"
        + "0. Exit");
  }

  static void cardCreated(CreditCard cc) {
    System.out.println("|" + "=".repeat(39) + "|");
    System.out.printf("|%s           |%n", " Your card has been created!");
    System.out.println("-".repeat(41));
    System.out.printf("|%s   |%n", " Your card number: " + cc.getCardNumber());
    System.out.printf("|%s                   |%n", " Your card pin: " + cc.getCardPin());
    System.out.println("|" + "=".repeat(39) + "|\n");
  }

  static void loginMenuMessage() {
    System.out.println("\n1. Balance\n"
        + "2. Add income\n"
        + "3. Do transfer\n"
        + "4. Close account\n"
        + "5. Log out\n"
        + "0. Exit");
  }
}
