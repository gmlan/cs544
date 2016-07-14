package presentation.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.NotEmpty;

import business.externalinterfaces.Address;
import business.externalinterfaces.CreditCard;
import business.externalinterfaces.User;
import business.usersubsystem.AddressImpl;
import business.usersubsystem.CreditCardImpl;

@XmlRootElement
public class UserPres implements User, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// @Pattern(regexp="P[1-9]+",
	// message="{Pattern.Product.productId.validation}")
	private int id;
	@NotEmpty
	private String username;
	@NotEmpty
	private String password;
	@NotEmpty
	@Size(min = 4, max = 50, message = "Min length 4 characters")
	private String firstname;
	@NotEmpty
	@Size(min = 4, max = 50, message = "Min length 4 characters")
	private String lastname;
	 
	private boolean billingCheckboxs;
	private boolean enabled;
 
	// @DateTimeFormat(pattern = Convert.DATE_PATTERN)
	// private LocalDate mfgDate;
	private String authority;

	@Valid
	private Address defaultShippingAddress;
	private Address defaultBillingAddress;
	@Valid
	private CreditCard defaultCreditCard;
	
	private List<Address> shippingAddress;
	
	private List<Address> billingAddress;
	
	private List<CreditCard> creditCard;
	

	public UserPres() {
		/*if (billingCheckboxs == true) {
			setDefaultBillingAddress(
					new AddressImpl(id, shippingZip, shippingStreet, shippingCity, shippingState));
		} else {
			setDefaultBillingAddress(new AddressImpl(id, billingZip, billingStreet, billingCity, billingState));
		}
		setDefaultShippingAddress(new AddressImpl(id, shippingZip, shippingStreet, shippingCity, shippingState));
		setDefaultCreditCard(new CreditCardImpl(cardNum, nameOnCard, expirationDate, cardType));*/
		defaultBillingAddress = new AddressImpl();
		defaultShippingAddress  = new AddressImpl();
		defaultCreditCard = new CreditCardImpl();
		
		setAuthority("ROLE_USER");
		shippingAddress = new ArrayList<>();
		billingAddress = new ArrayList<>();
		creditCard = new ArrayList<>();
		shippingAddress.add(getDefaultShippingAddress());
		billingAddress.add(getDefaultBillingAddress());
		creditCard.add(getDefaultCreditCard());
		setEnabled(true);
	}

	/*
	public UserPres Clone(User user) {		
		UserPres use = new UserPres();
		if (billingCheckboxs == true) {
			use.setDefaultBillingAddress(
					new AddressImpl(id, shippingZip, shippingStreet, shippingCity, shippingState));
		} else {
			use.setDefaultBillingAddress(
					new AddressImpl(id, billingZip, billingStreet, billingCity, billingState));
		}
		use.setDefaultShippingAddress(
				new AddressImpl(id, shippingZip, shippingStreet, shippingCity, shippingState));
		use.setDefaultCreditCard(new CreditCardImpl(cardNum, nameOnCard, expirationDate, cardType));
		use.setId(user.getId());
		use.setUsername(user.getUsername());
		use.setPassword(user.getPassword());
		use.setFirstname(user.getFirstname());
		use.setLastname(user.getLastname());
		use.setEnabled(user.isEnabled());
		use.setAuthority("ROLE_USER");

		return use;
	}
*/
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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
	public List<? extends Address> getShippingAddress() {
		return this.shippingAddress;
	}

	@Override
	public void setShippingAddress(List<? extends Address> shippingAddress) {
		this.shippingAddress = shippingAddress.stream().map(add->AddressImpl.clone(add)).collect(Collectors.toList());
	}

	@Override
	public List<? extends Address> getBillingAddress() {
		return this.billingAddress;
	}

	@Override
	public void setBillingAddress(List<? extends Address> billingAddress) {
		this.billingAddress = billingAddress.stream().map(bill->AddressImpl.clone(bill)).collect(Collectors.toList());
	}

	@Override
	public List<? extends CreditCard> getCreditCard() {
		return this.creditCard;
	}

	@Override
	public void setCreditCard(List<? extends CreditCard> creditCard) {
		this.creditCard = creditCard.stream().map(cc->CreditCardImpl.clone(cc)).collect(Collectors.toList());
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public int setId(Integer id) {
		return id;

	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	@Override
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public String getAuthority() {
		return authority;
	}

	@Override
	public void setAuthority(String authority) {
		this.authority = authority;
	}
 
	public boolean isBillingCheckboxs() {
		return billingCheckboxs;
	}

	public void setBillingCheckboxs(boolean billingCheckboxs) {
		this.billingCheckboxs = billingCheckboxs;
	}

	@Override
	public void addBillingAddress(Address address) {
		this.billingAddress.add(address);
	}

	@Override
	public void addShippingAddress(Address address) {
		this.shippingAddress.add(address);
	}
	
	public static UserPres clone(User user){
		if(user instanceof UserPres){
			return (UserPres) user;
		}
		else{
			UserPres userPres = new UserPres();
			userPres.setFirstname(user.getFirstname());
			userPres.setLastname(user.getLastname());
			userPres.setUsername(user.getUsername());
			userPres.setPassword(user.getPassword());
			userPres.setDefaultShippingAddress(user.getDefaultShippingAddress());
			userPres.setDefaultBillingAddress(user.getDefaultBillingAddress());
			userPres.setDefaultCreditCard(user.getDefaultCreditCard());
						
			return userPres;
		}
	}
}
