
package business.externalinterfaces;


public interface OrderItem {
    public int getOrderItemId();
    public int getProductId();
    public int getQuantity();
    public double getUnitPrice();
    public double getTotalPrice();
    public String getProductName();
    public void setProductName(String name);
    public void setOrderItemId(int id);
    public void setQuantity(int id);
    public void setUnitPrice(double u);
    public void setProductId(int id);
}
