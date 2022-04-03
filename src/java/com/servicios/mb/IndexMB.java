
package com.servicios.mb;


import com.servicios.impl.IservicioAplicacion;
import java.io.Serializable;
import java.math.BigInteger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author AMORAN
 */

/*
    CLASE JAVA MANAGED BEANS DE INDEX 
*/
@ManagedBean(name = "indexMB")
@SessionScoped

public class IndexMB implements Serializable
{
    
    private static final long serialVersionUID = 1L;

   
    @EJB
    private IservicioAplicacion  servicioAplicacion;
    
    @PostConstruct
    public void init()
    {
    
                
    }

  
      
}
