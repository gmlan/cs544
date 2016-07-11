package business.users;

import javax.persistence.Entity;
import javax.persistence.Id;

import business.externalinterfaces.Address;

@Entity(name = "Address")
public class AddressImpl implements Address {

	@Id
	private String zip;
	private String street;
	private String city;
	private String state;

	public AddressImpl(String zip, String street, String city, String state) {
		super();
		this.zip = zip;
		this.street = street;
		this.city = city;
		this.state = state;
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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isBillingAddress() {
		// TODO Auto-generated method stub
		return false;
	}

}
