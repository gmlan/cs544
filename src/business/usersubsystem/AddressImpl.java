package business.usersubsystem;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import business.externalinterfaces.Address;

@Entity(name = "Address")
public class AddressImpl implements Address {

	@Id
	@GeneratedValue
	private int id;
	private String zip;
	private String street;
	private String city;
	private String state;
	private boolean isShip;

	public AddressImpl(int id, String zip, String street, String city, String state, boolean isShip) {
		super();
		this.id = id;
		this.zip = zip;
		this.street = street;
		this.city = city;
		this.state = state;
		this.isShip = isShip;
	}

	public AddressImpl() {
		super();
	}

	@Override
	public String getZip() {
		return zip;
	}

	@Override
	public void setZip(String zip) {
		this.zip = zip;
	}

	@Override
	public String getStreet() {
		return street;
	}

	@Override
	public void setStreet(String street) {
		this.street = street;
	}

	@Override
	public String getCity() {
		return city;
	}

	@Override
	public void setCity(String city) {
		this.city = city;
	}

	@Override
	public String getState() {
		return state;
	}

	@Override
	public void setState(String state) {
		this.state = state;
	}

	@Override
	public boolean isShippingAddress() {
		return isShip;
	}

	@Override
	public boolean isBillingAddress() {
		return !isShip;
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public void setId(int id) {
		this.id = id;

	}

	public static AddressImpl clone(Address address) {
		AddressImpl addressImpl = new AddressImpl(address.getId(), address.getStreet(), address.getCity(),
				address.getState(), address.getZip(), address.isShippingAddress());
		addressImpl.setId(address.getId());
		return addressImpl;
	}

}