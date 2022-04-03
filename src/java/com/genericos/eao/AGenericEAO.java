package com.genericos.eao;




import java.io.Serializable;
import java.util.*;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.Query;


import org.hibernate.Session;
import org.hibernate.jdbc.Work;
import com.configuracion.impl.IGenericEAO;


import com.dominio.BeanDominio;

public abstract class AGenericEAO<T extends BeanDominio, ID extends Serializable> implements IGenericEAO<T,ID>
{

	protected Class<? extends T> entityBeanType;

//	protected abstract Logger getLogger (); 
	
	public AGenericEAO(Class<? extends T> entityBeanType) {
		this.entityBeanType= entityBeanType;

	}

	protected abstract EntityManager getEntityManager(); 
	

	public T findById(ID id, boolean lock)
	{
		T entity;
		if(lock)
		{
			entity = (T)getEntityManager().find(entityBeanType, id);
			getEntityManager().lock(entity, LockModeType.WRITE);
		} else
		{
			entity = (T)getEntityManager().find(entityBeanType, id);
		}
		return entity;
	}

	@SuppressWarnings("unchecked")
	public List<T> findAll(final int... rowStartIdxAndCount) 
	{
//		try
//		{
			Query query = getEntityManager().createQuery("select model from Follower model");
			if(rowStartIdxAndCount != null && rowStartIdxAndCount.length > 0)
			{
				int i = Math.max(0, rowStartIdxAndCount[0]);
				if(i > 0)
					query.setFirstResult(i);
				if(rowStartIdxAndCount.length > 1)
				{
					int j = Math.max(0, rowStartIdxAndCount[1]);
					if(j > 0)
						query.setMaxResults(j);
				}
			}
			return query.getResultList();
		}
//		catch(RuntimeException runtimeexception)
//		{
//			getLogger().error("find all failed", runtimeexception);
//			throw runtimeexception;
//		}
//	}

	@SuppressWarnings("unchecked")
	public List<T> findByProperty(String propertyName,
			final Object value, final int... rowStartIdxAndCount) {
//		try
//		{
			String s1 = (new StringBuilder()).append("select model from ").append(entityBeanType.getSimpleName()).append(" model where model.").append(propertyName).append("= :propertyValue").toString();
			Query query = getEntityManager().createQuery(s1);
			query.setParameter("propertyValue", value);
			if(rowStartIdxAndCount != null && rowStartIdxAndCount.length > 0)
			{
				int i = Math.max(0, rowStartIdxAndCount[0]);
				if(i > 0)
					query.setFirstResult(i);
				if(rowStartIdxAndCount.length > 1)
				{
					int j = Math.max(0, rowStartIdxAndCount[1]);
					if(j > 0)
						query.setMaxResults(j);
				}
			}
			return query.getResultList();
		}
//		catch(RuntimeException runtimeexception)
//		{
//			getLogger().error("find by property name failed", runtimeexception);
//			throw runtimeexception;
//		}
//	}

	@SuppressWarnings("unchecked")
	public List<T> findAll() {

//		getLogger().debug((new StringBuilder()).append("from ").append(entityBeanType.getName()).toString());
		return getEntityManager().createQuery((new StringBuilder()).append("from ").append(entityBeanType.getName()).toString()).getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<T> findAllSizeBased(int maxSize) {
		return getEntityManager().createQuery((new StringBuilder()).append("from ").append(entityBeanType.getName()).toString()).setMaxResults(maxSize).getResultList();
	}


	@SuppressWarnings("unchecked")
	public List<T> findByExample(T exampleInstance, String... excludeProperty) {
		//Using Hibernate, more difficult with EntityManager and EJB-QL
		org.hibernate.Criteria crit = ((org.hibernate.ejb.HibernateEntityManager) getEntityManager())
		.getSession().createCriteria(entityBeanType);
		org.hibernate.criterion.Example example = org.hibernate.criterion.Example
		.create(exampleInstance);
		for (String exclude : excludeProperty) {
			example.excludeProperty(exclude);
		}
		crit.add(example);
		return crit.list();
	}

	public void save(T entity)
	{
		getEntityManager().persist(entity);        
	}

	public T makePersistent(T entity) {
		return getEntityManager().merge(entity);
	}

	public void remove(T entity) {
		getEntityManager().remove(entity);
		flush();
	}

	public T removeById(ID id) {
		T t = findById(id, true);
		getEntityManager().remove(t);
		flush();
		return t;
	}

	public void detach(T entity){
		getEntityManager().detach(entity);	
		flush();
	}

	public void flush() {
		getEntityManager().flush();
	}

	public void clear() {
		getEntityManager().clear();
	}

	

	protected Query queryFormatByProperties(HashMap<String, Object> hashmap)
	{
		String s = (new StringBuilder()).append("select modelo from ").append(entityBeanType.getSimpleName()).append(" modelo ").toString();
		Set<String> set = hashmap.keySet();
		int i = 0;
		for( String sKey : set)
		{
			if(i == 0)
				s = s + " where " + sKey + " = :valor_" + i;
			else
				s = s + " and " + sKey + " = :valor_" + i;
			i++;
		}
//		getLogger().info(s);// NOPMD by desarrollo01.ec on 25/11/10 05:07 PM
		Query query = getEntityManager().createQuery(s);
		i = 0;
		for( String sKey : set)
		{
//			getLogger().info("valor_"+i + " = " +   hashmap.get(sKey)); // NOPMD by desarrollo01.ec on 25/11/10 05:10 PM
			query.setParameter("valor_"+i, hashmap.get(sKey));
			i++;
		}

		return query;
	} 

	@SuppressWarnings("unchecked")
	public T findUniqueByProperties(HashMap<String, Object> hashmap)
	{
		return (T)queryFormatByProperties(hashmap).getSingleResult();
	}


	@SuppressWarnings("unchecked")
	public List<T> findByProperties(HashMap<String, Object> hashmap)
	{
		return queryFormatByProperties(hashmap).getResultList();
	}


	@SuppressWarnings("unchecked")
	public List<T> findByPatternPropertie(String s, Object obj, String s1)
	{
		String s2 = (new StringBuilder()).append("select modelo from ").append(entityBeanType.getSimpleName()).append(" modelo where UPPER(modelo.").append(s).append(") like :valor ").toString();
		if(s1 != null)
			s2 = (new StringBuilder()).append(s2).append(" and modelo.estado = :estado").toString();
		Query query = getEntityManager().createQuery(s2);
		query.setParameter("valor", (new StringBuilder()).append("%").append(obj).append("%").toString());
		if(s1 != null)
			query.setParameter("estado", s1);
		return query.getResultList();
	}
	
	public void jdbcInvocationInsurance(Work work)
	{
		getEntityManager().flush();
		Session sesion = (Session)getEntityManager().getDelegate();
		sesion.doWork(work);
	}

}