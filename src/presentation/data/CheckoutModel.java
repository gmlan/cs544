package presentation.data;

import java.io.Serializable;

import business.externalinterfaces.Address;
import business.externalinterfaces.CreditCard;

public class CheckoutModel implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	CreditCard creditCard;
	Address shippingAddress;
	Address billingAddress;
	boolean saveBillingAddress;
	boolean saveShippingAddress;
	
	
	public CreditCard getCreditCard() {
		return creditCard;
	}

	public void setCreditCard(CreditCard creditCard) {
		this.creditCard = creditCard;
	}

	public boolean isSaveBillingAddress() {
		return saveBillingAddress;
	}

	public void setSaveBillingAddress(boolean saveBillingAddress) {
		this.saveBillingAddress = saveBillingAddress;
	}

	public boolean isSaveShippingAddress() {
		return saveShippingAddress;
	}

	public void setSaveShippingAddress(boolean saveShippingAddress) {
		this.saveShippingAddress = saveShippingAddress;
	}

	public CheckoutModel(){
		/*shippingAddress  = new AddressImpl();
		billingAddress = new AddressImpl();
		creditCard = new CreditCardImpl();
		
		((AddressImpl)shippingAddress).setShippingAddress(true);
		((AddressImpl)billingAddress).setBillingAddress(true);*/
	}

	public Address getShippingAddress() {
		return shippingAddress;
	}

	public void setShippingAddress(Address shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	public Address getBillingAddress() {
		return billingAddress;
	}

	public void setBillingAddress(Address billingAddress) {
		this.billingAddress = billingAddress;
	}
}