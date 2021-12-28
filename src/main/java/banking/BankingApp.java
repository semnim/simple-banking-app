package banking;

import java.util.Scanner;


public class BankingApp {

  private final Scanner in = new Scanner(System.in);
  private DBManager dbManager;

  public void execute(DBManager dbManager) {
    this.dbManager = dbManager;

    while (true) {
      UserMessagePrinter.globalMenuMessage();

      String userInput = getUserInput();
      switch (userInput) {
        case "1":
          createCreditCard();
          break;
        case "2":
          logIn();
          break;
        case "0":
          System.out.println("Bye!");
          System.exit(0);
        default:
          System.out.println("Please try again.\n");
      }

    }
  }

  private void createCreditCard() {
    CreditCard cc = new CreditCard();
    UserMessagePrinter.cardCreated(cc);
    dbManager.addAccount(cc);
  }

  private void logIn() {
    System.out.println("Enter your card number:");
    final String cardNumber = getUserInput();
    System.out.println("Enter your PIN:");
    final String cardPin = getUserInput();

    if (!dbManager.hasAuthentification(cardNumber, cardPin)) {
      System.out.println("Wrong card number or PIN!\n");
    } else {
      showLogInMenu(cardNumber);
    }
  }

  private void showLogInMenu(String cardNumber) {
    System.out.println("You have successfully logged in!");
    while (true) {
      UserMessagePrinter.loginMenuMessage();

      final String userInput = getUserInput();

      switch (userInput) {
        case "1":
          long balance = dbManager.getBalance(cardNumber);
          System.out.println("Current balance: " + balance);
          break;
        case "2":
          System.out.println("Enter income:");
          long deposit = 0L;
          try {
            deposit = Long.parseLong(getUserInput());
          } catch (NumberFormatException n) {
            break;
          }

          if (deposit < 0) {
            System.out.println("Cannot increase balance by a negative amount.");
          } else {
            dbManager.setBalance(cardNumber, deposit);
            System.out.println("Income was added!");
          }
          break;
        case "3":
          transferIncome(cardNumber);
          break;
        case "4":
          dbManager.deleteAccount(cardNumber);
          System.out.println("The account has been closed!\n");
          return;
        case "5":
          System.out.println("You have successfully logged out!\n");
          return;
        case "0":
          System.out.println("Bye!");
          System.exit(0);
        default:
          System.out.println("Please try again.\n");
      }
    }
  }

  private void transferIncome(String cardNumber) {
    System.out.println("Transfer\nEnter receipient:");
    String targetAccount = getUserInput();

    if (targetAccount.equals(cardNumber)) {
      System.out.println("You can't transfer money to the same account!\n");
      return;
    }

    if (dbManager.accountExists(targetAccount)) {
      initiateTransfer(cardNumber, targetAccount);
    } else if (targetAccount.length() != 16 || !CreditCard.generateNumberWithChecksum(targetAccount.substring(0, 15)).equals(targetAccount)) {
      System.out.println("Probably you made a mistake in the card number. Please try again!\n");
    } else {
      System.out.println("Such a card does not exist.\n");
    }
  }

  private void initiateTransfer(String cardNumber, String targetAccount) {
    System.out.println("Enter how much money you want to transfer:");
    long amount;
    try {
      amount = Long.parseLong(getUserInput());
    } catch (NumberFormatException e) {
      amount = 0;
    }
    if (amount <= 0) {
      return;
    }

    if (dbManager.getBalance(cardNumber) >= amount) {
      dbManager.setBalance(targetAccount, amount);
      dbManager.setBalance(cardNumber, -amount);
      System.out.println("Success!");
    } else {
      System.out.println("Not enough money!");
    }
  }

  private String getUserInput() {
    String userInput = "";
    try {
      userInput = in.nextLine().trim();
      System.out.println();
      if (!userInput.matches("[0-9]+")) {
        throw new NumberFormatException("Input must be a positive number.");
      }
    } catch (NumberFormatException e) {
      System.out.println(e.getMessage());
    }
    return userInput;
  }
}