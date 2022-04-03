/**
 * @
 * @version 1.1
 * @category Clases Generica Nucleares * 
 */


package com.configuracion.impl;


import java.io.Serializable;
import com.dominio.BeanDominio;

public interface IEjbGenericEAO<T extends BeanDominio, ID extends Serializable> extends IGenericEAO<T, ID>  
{	 
	
}
