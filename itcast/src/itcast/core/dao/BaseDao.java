package itcast.core.dao;

import itcast.core.util.QueryHelper;
import itcast.nsfw.info.entity.Info;

import java.io.Serializable;
import java.util.List;

public interface BaseDao<T> {
	
	//新增
	public void save(T entity);
	//更新
	public void update(T entity);
	//根据id删除
	public void delete(Serializable id);
	//根据id查找
	public T findObjectById(Serializable id);
	//查找列表
	public List<T> findObjects();
	//模糊查询
	public List<T> findObjects(String hql,List<Object> parameters);
	//条件查询实体列表--查询助手queryHelper
	public List<Info> findObjects(QueryHelper queryHelper);
}
