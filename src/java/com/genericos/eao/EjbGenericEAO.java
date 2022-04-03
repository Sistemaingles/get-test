package com.genericos.eao;



import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import com.configuracion.impl.IEjbGenericEAO;
import com.dominio.BeanDominio;





public abstract class EjbGenericEAO <T extends BeanDominio, ID extends Serializable> 
extends AGenericEAO<T, ID> implements IEjbGenericEAO<T, ID>
{
	
	
	@PersistenceContext(unitName="project",type=PersistenceContextType.TRANSACTION)
	protected EntityManager administradorEntidad;
	        
	public EjbGenericEAO(Class<? extends T> entityBeanType) {
		super(entityBeanType);
		// TODO Auto-generated constructor stub
	}
	
	protected EntityManager getEntityManager()
	{
		return administradorEntidad;
	}
        
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public T findById(ID id, boolean lock)
	{ 
		try
		{
			return super.findById(id, lock);
		}catch (NoResultException e)
		{
			return null;
		}
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<T> findAll(final int... rowStartIdxAndCount) 
	{
		return super.findAll(rowStartIdxAndCount);
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<T> findByProperty(String propertyName,
			final Object value, final int... rowStartIdxAndCount)
			{
		return super.findByProperty(propertyName, value, rowStartIdxAndCount);
			}   
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<T> findAll()
	{
            return super.findAll();
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)    
	public List<T> findAllSizeBased(int maxSize) 
        {
		return super.findAllSizeBased(maxSize);
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<T> findByExample(T exampleInstance, String... excludeProperty) 
        {
		return super.findByExample(exampleInstance, excludeProperty);
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public T findUniqueByProperties(HashMap<String, Object> hashmap)
	{
		try
		{
			return super.findUniqueByProperties(hashmap);
		}catch (NoResultException e)
		{
			return null;
		}
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<T> findByProperties(HashMap<String, Object> hashmap)
	{
		return super.findByProperties(hashmap);
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<T> findByPatternPropertie(String s, Object obj, String s1)
	{
		return super.findByPatternPropertie(s, obj, s1);
	}

}
