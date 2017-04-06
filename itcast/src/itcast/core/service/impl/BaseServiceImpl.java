package itcast.core.service.impl;

import itcast.core.dao.BaseDao;
import itcast.core.service.BaseService;
import itcast.core.util.QueryHelper;
import itcast.nsfw.info.dao.InfoDao;
import itcast.nsfw.info.entity.Info;

import java.io.Serializable;
import java.util.List;

public abstract class BaseServiceImpl<T> implements BaseService<T> {
	private BaseDao<T> baseDao;
	
	public void setBaseDao(BaseDao<T> baseDao) {
		this.baseDao=baseDao;
	}
	@Override
	public void save(T entity) {
		baseDao.save(entity);
	}

	@Override
	public void update(T entity) {
		baseDao.update(entity);
	}

	@Override
	public void delete(Serializable id) {
		baseDao.delete(id);
	}

	@Override
	public T findObjectById(Serializable id) {
		return baseDao.findObjectById(id);
	}

	@Override
	public List<T> findObjects() {
		return baseDao.findObjects();
	}
	@Override
	public List<T> findObjects(String hql,List<Object> parameters){
		return baseDao.findObjects(hql, parameters);
	}
	@Override
	public List<Info> findObjects(QueryHelper queryHelper){
		return baseDao.findObjects(queryHelper);
	}
}
