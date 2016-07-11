package business.externalinterfaces;

import java.util.List;

public interface User {


	public String getUsername();

	public void setUsername(String username);

	public String getPassword();

	public void setPassword(String password);

	public String getFirstname();

	public void setFirstname(String firstname);

	public String getLastname();

	public void setLastname(String lastname);

	public Address getDefaultShippingAddress();

	public void setDefaultShippingAddress(Address defaultShippingAddress);

	public Address getDefaultBillingAddress();

	public void setDefaultBillingAddress(Address defaultBillingAddress);

	public CreditCard getDefaultCreditCard();

	public void setDefaultCreditCard(CreditCard defaultCreditCard);

	public List<Address> getShippingAddress();

	public void setShippingAddress(List<Address> shippingAddress);

	public List<Address> getBillingAddress();

	public void setBillingAddress(List<Address> billingAddress);

	public List<CreditCard> getCreditCard();

	public void setCreditCard(List<CreditCard> creditCard);

	public int getId();
}
