
package business.externalinterfaces;


public interface CreditCard {
	int getId();
    String getNameOnCard();
    String getExpirationDate();
    String getCardNum();
    String getCardType();
    void setNameOnCard(String nameOnCard);
    void setExpirationDate(String expirationDate);
    void setCardNum(String cardNum);
    void setCardType(String cardType);
}
