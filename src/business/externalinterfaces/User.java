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

	public void addBillingAddress(Address address);

	public void addShippingAddress(Address address);
	
	public void setDefaultCreditCard(CreditCard defaultCreditCard);

	public List<? extends Address> getShippingAddress();

	public void setShippingAddress(List<? extends Address> shippingAddress);

	public List<? extends Address> getBillingAddress();

	public void setBillingAddress(List<? extends Address> billingAddress);

	public List<? extends CreditCard> getCreditCard();

	public void setCreditCard(List<? extends CreditCard> creditCard);

	public int getId();

	public int setId(Integer id);

	public boolean isEnabled();

	public void setEnabled(boolean enabled);

	public String getAuthority();

	public void setAuthority(String authority);
 
}
