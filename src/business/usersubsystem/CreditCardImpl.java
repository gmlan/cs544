package business.usersubsystem;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.validator.constraints.NotEmpty;

import business.externalinterfaces.CreditCard;

@Entity(name = "CreditCard")
public class CreditCardImpl implements CreditCard {

	@Id
	@GeneratedValue
	private int id;
	@NotEmpty
	private String cardNum;
	@NotEmpty
	private String nameOnCard;
	@NotEmpty
	private String expirationDate;
	@NotEmpty
	private String cardType;

	public CreditCardImpl() {
		super();
	}

	public CreditCardImpl(int id, String cardNum, String nameOnCard, String expirationDate, String cardType) {
		super();
		this.id = id;
		this.cardNum = cardNum;
		this.nameOnCard = nameOnCard;
		this.expirationDate = expirationDate;
		this.cardType = cardType;
	}

	public CreditCardImpl(String cardNum, String nameOnCard, String expirationDate, String cardType) {
		super();
		this.cardNum = cardNum;
		this.nameOnCard = nameOnCard;
		this.expirationDate = expirationDate;
		this.cardType = cardType;
	}

	@Override
	public String getNameOnCard() {
		return nameOnCard;
	}

	@Override
	public String getExpirationDate() {
		return expirationDate;
	}

	@Override
	public String getCardNum() {
		return cardNum;
	}

	@Override
	public String getCardType() {
		return cardType;
	}

	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
	}

	public void setNameOnCard(String nameOnCard) {
		this.nameOnCard = nameOnCard;
	}

	public void setExpirationDate(String expirationDate) {
		this.expirationDate = expirationDate;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	@Override
	public int getId() {
		return id;
	}
	

	public static CreditCardImpl clone(CreditCard creditCard){
		if(creditCard instanceof CreditCardImpl){
			return (CreditCardImpl) creditCard;
		}
		else{
			return new CreditCardImpl(
				creditCard.getId(),
				creditCard.getCardNum(),
				creditCard.getNameOnCard(),
				creditCard.getExpirationDate(),				
				creditCard.getCardType());
		}
	}

}
