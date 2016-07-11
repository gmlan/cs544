package business.productsubsystem;

import org.springframework.stereotype.Repository;

import core.dao.impl.BaseDaoImpl;

@Repository
public class ProductDAO extends BaseDaoImpl<ProductImpl> {
	public ProductDAO() {
	}

}
