package business.productsubsystem;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import business.externalinterfaces.Catalog;
import business.externalinterfaces.Product;
import business.util.LocalDateConverter;

@Entity
@Table(name = "product")
class ProductImpl implements Product, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private int productId;
	private String productName;

	
	@Column(name = "totalquantity")
	private int quantityAvail;

	@ManyToOne
	@JoinColumn(name = "catalogid")
	private CatalogImpl catalog;

	@Column(name = "priceperunit")
	private double unitPrice;
	
	@Convert(converter = LocalDateConverter.class)
	private LocalDate mfgDate;

	private String description;

	private int quantityRequested;
	private int rating;
	private int review;

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public int getReview() {
		return review;
	}

	public void setReview(int review) {
		this.review = review;
	}

	// convert all non-string types to strings if used in a table; otherwise,
	// don't convert
	public int getQuantityRequested() {
		return quantityRequested;
	}

	public void setQuantityRequested(int quantityRequested) {
		this.quantityRequested = quantityRequested;
	}

	public ProductImpl(){}
	
	public ProductImpl(Catalog c, Integer pi, String pn, int qa, double up, LocalDate md, String d) {
		if (pi != null)
			productId = pi;
		this.productName = pn;
		quantityAvail = qa;
		unitPrice = up;
		mfgDate = md;
		catalog = CatalogImpl.Clone(c);
		Optional.ofNullable(d).ifPresent(x -> description = x);
	}

	// this constructor is used when getting user-entered data in adding a new
	// product
	public ProductImpl(Catalog c, String name, LocalDate date, int numAvail, double price) {
		this(c, null, name, numAvail, price, date, null);
	}

	public ProductImpl(Catalog c, String name, String date, String numAvail, String price, String desc) {
		this(c, null, name, Integer.parseInt(numAvail), Double.parseDouble(price), LocalDate.parse(date),
				desc);
	}

	public Catalog getCatalog() {
		return catalog;
	}

	/* @added by Ngoc Nguyen */
	public Catalog setCatalog(Catalog catalog) {
		return this.catalog = CatalogImpl.Clone(catalog);
	}

	public LocalDate getMfgDate() {
		return mfgDate;
	}

	public int getProductId() {
		return productId;
	}

	/**
	 * @return Returns the productName.
	 */
	public String getProductName() {
		return productName;
	}

	/**
	 * @return Returns the quantityAvail.
	 */
	public int getQuantityAvail() {
		return quantityAvail;
	}

	/**
	 * @return Returns the unitPrice.
	 */
	public double getUnitPrice() {
		return unitPrice;
	}

	/**
	 * @return Returns the description.
	 */
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public void setQuantityAvail(int quantityAvail) {
		this.quantityAvail = quantityAvail;
	}

	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public void setMfgDate(LocalDate mfgDate) {
		this.mfgDate = mfgDate;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public static ProductImpl Clone(Product product) {
		return new ProductImpl(product.getCatalog(), product.getProductId(), product.getProductName(), product.getQuantityAvail(),
				product.getUnitPrice(), product.getMfgDate(), product.getDescription());
	}

	@Override
	public String toString() {
		return "ProductImpl [productId=" + productId + ", productName=" + productName + ", quantityAvail="
				+ quantityAvail + ", catalog=" + catalog + ", unitPrice=" + unitPrice + ", mfgDate=" + mfgDate
				+ ", description=" + description + ", quantityRequested=" + quantityRequested + ", rating=" + rating
				+ ", review=" + review + "]";
	}
	
	
	
}
