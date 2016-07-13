
package business.externalinterfaces;

public interface Address {
	public int getId();
    public String getStreet();
    public String getCity();
    public String getState();
    public String getZip();
    public void setId(int id);
    public void setStreet(String s);
    public void setCity(String s);
    public void setState(String s);
    public void setZip(String s);
}
