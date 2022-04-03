

package com.configuracion.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import org.hibernate.jdbc.Work;
import com.dominio.BeanDominio;

public interface IGenericEAO<T extends BeanDominio, ID extends Serializable> 
{

	public T findById(ID id, boolean lock);

	public List<T> findAll();

	public List<T> findAll(int... rowStartIdxAndCount);

	public List<T> findByProperty(String propertyName, Object value,	int... rowStartIdxAndCount);


	public List<T> findAllSizeBased(int maxSize);

	public List<T> findByExample(T exampleInstance, String... excludeProperty);

	public T makePersistent(T entity);

	public void flush();

	public void remove(T entity);

	public T removeById(ID id); 

	public void detach(T entity);

	public void clear();

	public T findUniqueByProperties(HashMap<String, Object> hashmap);

	public List<T> findByPatternPropertie(String s, Object obj, String s1);

	public List<T> findByProperties(HashMap<String, Object> hashmap);

	public void save(T entity);
	
	public void jdbcInvocationInsurance(Work work);
}