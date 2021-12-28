package banking;

public class CreditCard {

  private final String cardNumber;
  private final String cardPin;

  public CreditCard() {
    this.cardNumber = generateCardNumber();
    this.cardPin = generateCardPin();
  }
  public CreditCard(String cardNumber, String cardPin) {
    this.cardNumber = cardNumber;
    this.cardPin = cardPin;
  }

  String getCardNumber() {
    return this.cardNumber;
  }

  String getCardPin() {
    return this.cardPin;
  }

  private String generateCardPin() {
    return String.format("%4d", (int) (Math.random() * 10000)).replace(' ', '0');
  }

  private String generateCardNumber() {
    final String cardNumber = String.format("4000000%9d", (long) (Math.random() * 1000000000L))
                              .replace(' ', '0');
    return generateNumberWithChecksum(cardNumber);
  }

  public static String generateNumberWithChecksum(String number) {
    int sum = 0;
    for (int i = 0; i < number.length(); i++) {
      int factor = i % 2 == 0 ? 2 : 1;

      int digit = Character.digit(number.charAt(i), 10) * factor;

      if (digit > 9) {
        digit -= 9;
      }

      sum += digit;
    }

    final int checkSumDigit = (10 - (sum % 10)) % 10;
    return number + checkSumDigit;
  }
}
