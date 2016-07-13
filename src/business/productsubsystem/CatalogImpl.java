package business.productsubsystem;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import business.externalinterfaces.Catalog;

@Entity
@Table(name = "catalogtype")
class CatalogImpl implements Catalog, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue	
	@Column(name="catalogid")
	private int id;
	@Column(name="catalogname")
	private String name;
	
	public CatalogImpl() {	}
	
	public CatalogImpl(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String n) {
		name = n;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public boolean equals(Object ob) {
		if(ob == null) return false;
		if(this == ob) return true;
		if(getClass() != ob.getClass()) return false;
		CatalogImpl c = (CatalogImpl)ob;
		return name.equals(c.name);
	}
	
	public int hashCode() {
		int result = 17;
		result += 31 * result + name.hashCode();
		return result;
	}
	
	public static CatalogImpl Clone(Catalog catalog){
		if(catalog instanceof CatalogImpl){
			return (CatalogImpl) catalog;
		}
		else{
			return new CatalogImpl(catalog.getId(), catalog.getName());
		}
	}

	@Override
	public String toString() {
		return "CatalogImpl [id=" + id + ", name=" + name + "]";
	}
	
	
}
