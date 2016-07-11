package business.users;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import business.externalinterfaces.Address;
import business.externalinterfaces.CreditCard;
import business.externalinterfaces.User;

@Entity
public class UserImpl implements User {

	@Id
	@GeneratedValue
	private int id;
	private String username;
	private String password;
	private String firstname;
	private String lastname;
	private Address defaultShippingAddress;
	private Address defaultBillingAddress;
	private CreditCard defaultCreditCard;
	@OneToMany
	private List<Address> shippingAddress;
	@OneToMany
	private List<Address> billingAddress;
	@OneToMany
	private List<CreditCard> creditCard;

	public UserImpl() {
	}

	public UserImpl(String username, String password, String firstname, String lastname, Address defaultShippingAddress,
			Address defaultBillingAddress, CreditCard defaultCreditCard, List<Address> shippingAddress,
			List<Address> billingAddress, List<CreditCard> creditCard) {
		super();
		this.username = username;
		this.password = password;
		this.firstname = firstname;
		this.lastname = lastname;
		this.defaultShippingAddress = defaultShippingAddress;
		this.defaultBillingAddress = defaultBillingAddress;
		this.defaultCreditCard = defaultCreditCard;
		this.shippingAddress = shippingAddress;
		this.billingAddress = billingAddress;
		this.creditCard = creditCard;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String getFirstname() {
		return firstname;
	}

	@Override
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	@Override
	public String getLastname() {
		return lastname;
	}

	@Override
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	@Override
	public Address getDefaultShippingAddress() {
		return defaultShippingAddress;
	}

	@Override
	public void setDefaultShippingAddress(Address defaultShippingAddress) {
		this.defaultShippingAddress = defaultShippingAddress;
	}

	@Override
	public Address getDefaultBillingAddress() {
		return defaultBillingAddress;
	}

	@Override
	public void setDefaultBillingAddress(Address defaultBillingAddress) {
		this.defaultBillingAddress = defaultBillingAddress;
	}

	@Override
	public CreditCard getDefaultCreditCard() {
		return defaultCreditCard;
	}

	@Override
	public void setDefaultCreditCard(CreditCard defaultCreditCard) {
		this.defaultCreditCard = defaultCreditCard;
	}

	@Override
	public List<Address> getShippingAddress() {
		return shippingAddress;
	}

	@Override
	public void setShippingAddress(List<Address> shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	@Override
	public List<Address> getBillingAddress() {
		return billingAddress;
	}

	@Override
	public void setBillingAddress(List<Address> billingAddress) {
		this.billingAddress = billingAddress;
	}

	@Override
	public List<CreditCard> getCreditCard() {
		return creditCard;
	}

	@Override
	public void setCreditCard(List<CreditCard> creditCard) {
		this.creditCard = creditCard;
	}

	@Override
	public int getId() {
		return id;
	}
}
