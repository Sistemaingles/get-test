/**
 * @
 * @version 1.1
 * @category Clases Generica Nucleares * 
 */


package com.configuracion.impl;


import java.io.Serializable;
import com.dominio.BeanDominio;

public interface IEjbGenericEAO_1<T extends BeanDominio, ID extends Serializable> extends IGenericEAO_1<T, ID>  
{	 
	
}
