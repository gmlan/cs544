package core.domain;

import java.util.ArrayList;
import java.util.List;

public class QueryHelper {
	
	private String fromClause = "";
	private String whereClause = "";
	private String orderByClause = "";
	
	private List<Object> parameters;
	public static String ORDER_BY_DESC = "DESC";
	public static String ORDER_BY_ASC = "ASC";
	
	public QueryHelper(Class clazz, String alias){
		fromClause = "FROM " + clazz.getSimpleName() + " " + alias;
	}
	
	
	public void addCondition(String condition, Object... params){
		if (whereClause.length() > 1) {
			whereClause += " AND " + condition;
		} else{
			whereClause += " WHERE " + condition;
		}
		
		if(parameters == null){
			parameters = new ArrayList<Object>();
		}
		if(params != null){
			for(Object param: params){
				parameters.add(param);
			}
		}
	}
	
	public void addOrderByProperty(String property, String order){
		if (orderByClause.length() > 1) {
			orderByClause += "," + property + " " + order;
		} else {
			orderByClause = " ORDER BY " + property + " " + order;
		}
	}

	public String getQueryListHql(){
		return fromClause + whereClause + orderByClause;
	}
	
	public String getQueryCountHql(){
		return "SELECT COUNT(*) " + fromClause + whereClause;
	}
	
	public List<Object> getParameters(){
		return parameters;
	}
}
