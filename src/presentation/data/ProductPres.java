package presentation.data;

import java.io.Serializable;
import java.time.LocalDate;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.id.IncrementGenerator;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import business.externalinterfaces.Catalog;
import business.externalinterfaces.Product;
import business.util.Convert;

@XmlRootElement 
public class ProductPres implements Product, Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//@Pattern(regexp="P[1-9]+", message="{Pattern.Product.productId.validation}")	
	private int productId;
	
	@Size(min=4, max=50, message="Min length 4 characters")
	private String productName;
	
	@Min(value=0, message="Min value is 0")
	@Digits(integer=8, fraction=2, message="Must be all digits")
	@NotNull(message= "**Must**")
	private int quantityAvail;
	private int catalogId;
	
	@Min(value=0, message="Min value is 0")
	@Digits(integer=8, fraction=2, message="Must be all digits")
	@NotNull(message= "**Must**")
	private double unitPrice;
	
	@DateTimeFormat(pattern = Convert.DATE_PATTERN)
	private LocalDate mfgDate;
	private String description;
	private int quantityRequested;
	private int rating;
	private int review;

	@JsonIgnore 
	private MultipartFile  productImage;
		
	private Catalog catalog;
	
	public int getCatalogId() {
		return catalogId;
	}

	public void setCatalogId(int catalogId) {
		this.catalogId = catalogId;
	}

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


	@XmlTransient  
	public MultipartFile getProductImage() {
		return productImage;
	}
	
	public void setProductImage(MultipartFile productImage) {
		this.productImage = productImage;
	}
	
	public ProductPres(){}
	
	public ProductPres(Integer productId) {
		this.productId = productId;
	} 
	 

	public Catalog getCatalog() {
		return catalog;
	}

	public Catalog setCatalog(Catalog catalog) {
		return this.catalog = catalog;
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
	
	public static ProductPres Clone(Product product) {
		ProductPres pres = new ProductPres();
		pres.setProductId(product.getProductId());
		pres.setProductName(product.getProductName());
		pres.setCatalogId(product.getCatalog().getId());
		pres.setDescription(product.getDescription());
		pres.setMfgDate(product.getMfgDate());
		pres.setQuantityAvail(product.getQuantityAvail());
		pres.setUnitPrice(product.getUnitPrice());
		return pres;
	}
 

	@Override
	public String toString() {
		return "ProductImpl [productId=" + productId + ", productName=" + productName + ", quantityAvail="
				+ quantityAvail + ", catalog=" + catalog + ", unitPrice=" + unitPrice + ", mfgDate=" + mfgDate
				+ ", description=" + description + ", quantityRequested=" + quantityRequested + ", rating=" + rating
				+ ", review=" + review + "]";
	}
	
}
