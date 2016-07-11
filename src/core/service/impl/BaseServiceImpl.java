package core.service.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.transaction.Transactional;


import core.dao.BaseDao;
import core.domain.PageResult;
import core.domain.QueryHelper;
import core.service.BaseService;
 
public class BaseServiceImpl<T> implements BaseService<T> {
	
	private BaseDao<T> baseDao;
	private Class<T> clazz;
		
	
	@SuppressWarnings("unchecked")
	public BaseServiceImpl() {
		ParameterizedType pt =  (ParameterizedType)this.getClass().getGenericSuperclass();
		clazz = (Class<T>)pt.getActualTypeArguments()[0];		
	}
			
	public void setBaseDao(BaseDao<T> baseDao) {
		this.baseDao = baseDao;
	}

	@Override
	@Transactional
	public void save(T entity) {		
		baseDao.save(entity);
	}

	@Override
	@Transactional
	public void update(T entity) {
		baseDao.update(entity);
	}

	@Override
	@Transactional
	public void delete(Serializable id) {
		baseDao.delete(id);
	}

	@Override
	@Transactional
	public T findObjectById(Serializable id) {
		return baseDao.findObjectById(id);
	}

	@Override
	@Transactional
	public List<T> findObjects() {
		return baseDao.findObjects();
	}

	@Override
	@Transactional
	public List<T> findObjects(String hql, List<Object> parameters) {
		return baseDao.findObjects(hql, parameters);
	}

	@Override
	@Transactional
	public List<T> findObjects(QueryHelper queryHelper) {
		return baseDao.findObjects(queryHelper);
	}

	@Override
	@Transactional
	public PageResult getPageResult(QueryHelper queryHelper, int pageNo, int pageSize) {
		return baseDao.getPageResult(queryHelper, pageNo, pageSize);
	}
}
