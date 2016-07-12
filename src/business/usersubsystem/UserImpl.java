package business.usersubsystem;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SecondaryTable;
import javax.persistence.Table;

import business.externalinterfaces.Address;
import business.externalinterfaces.CreditCard;
import business.externalinterfaces.User;

@Entity
@Table(name = "users")
@SecondaryTable(name = "user_roles", pkJoinColumns = {
		@PrimaryKeyJoinColumn(name = "user_id", referencedColumnName = "id") }, indexes = {
				@Index(columnList = "user_id") })
public class UserImpl implements User {

	@Id
	@GeneratedValue
	private int id;
	@Column(length = 45)
	private String username;
	@Column(length = 45)
	private String password;
	private String firstname;
	private String lastname;
	private boolean enabled;
	@Column(table = "user_roles")
	private String authority;

	@OneToOne(cascade = CascadeType.ALL)
	private AddressImpl defaultShippingAddress;

	@OneToOne(cascade = CascadeType.ALL)
	private AddressImpl defaultBillingAddress;

	@OneToOne(cascade = CascadeType.ALL)
	private CreditCardImpl defaultCreditCard;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_shipping")
	private List<AddressImpl> shippingAddress;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_billing")
	private List<AddressImpl> billingAddress;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_creditcard")
	private List<CreditCardImpl> creditCard;

	public UserImpl() {
		shippingAddress = new ArrayList<>();
		billingAddress = new ArrayList<>();
		creditCard = new ArrayList<>();
	}

	public UserImpl(int id, String username, String password, String firstname, String lastname, boolean enabled,
			String authority, AddressImpl defaultShippingAddress, AddressImpl defaultBillingAddress,
			CreditCardImpl defaultCreditCard, List<AddressImpl> shippingAddress, List<AddressImpl> billingAddress,
			List<CreditCardImpl> creditCard) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.firstname = firstname;
		this.lastname = lastname;
		this.enabled = enabled;
		this.authority = authority;
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
		return this.lastname;
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
		this.defaultShippingAddress = AddressImpl.clone(defaultShippingAddress);
	}

	@Override
	public Address getDefaultBillingAddress() {
		return defaultBillingAddress;
	}

	@Override
	public void setDefaultBillingAddress(Address defaultBillingAddress) {
		this.defaultBillingAddress = AddressImpl.clone(defaultBillingAddress);

	}

	@Override
	public CreditCard getDefaultCreditCard() {
		return defaultCreditCard;
	}

	@Override
	public void setDefaultCreditCard(CreditCard defaultCreditCard) {
		this.defaultCreditCard = CreditCardImpl.clone(defaultCreditCard);
	}

	@Override
	public List<? extends Address> getShippingAddress() {
		return this.shippingAddress;
	}

	@Override
	public void setShippingAddress(List<? extends Address> shippingAddress) {
		this.shippingAddress = shippingAddress.stream().map(add -> AddressImpl.clone(add)).collect(Collectors.toList());
	}

	@Override
	public List<? extends Address> getBillingAddress() {
		return this.billingAddress;
	}

	@Override
	public void setBillingAddress(List<? extends Address> billingAddress) {
		this.billingAddress = billingAddress.stream().map(add -> AddressImpl.clone(add)).collect(Collectors.toList());

	}

	@Override
	public List<? extends CreditCard> getCreditCard() {
		return this.creditCard;
	}

	@Override
	public void setCreditCard(List<? extends CreditCard> creditCard) {
		this.creditCard = creditCard.stream().map(c -> CreditCardImpl.clone(c)).collect(Collectors.toList());
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public int setId(Integer id) {
		return this.id = id;
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

	public static UserImpl clone(User user) {
		UserImpl userClone = new UserImpl();
		userClone.setId(user.getId());
		userClone.setUsername(user.getUsername());
		userClone.setPassword(user.getPassword());
		userClone.setFirstname(user.getFirstname());
		userClone.setLastname(user.getLastname());
		userClone.setDefaultBillingAddress(user.getDefaultBillingAddress());
		userClone.setDefaultShippingAddress(user.getDefaultShippingAddress());
		userClone.setDefaultCreditCard(user.getDefaultCreditCard());
		userClone.setShippingAddress(user.getShippingAddress());
		userClone.setBillingAddress(user.getBillingAddress());
		userClone.setCreditCard(user.getCreditCard());
		userClone.setEnabled(user.isEnabled());
		userClone.setAuthority(user.getAuthority());
		return userClone;
	}

}
