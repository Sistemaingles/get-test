
package com.servicios.eao;

import com.genericos.eao.EjbGenericEAO;
import com.entidades.TblParam;
import java.math.BigInteger;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;


/**
 *
 * @author JESPINOZA
 */
@Stateless
@LocalBean
public class parametrosEAO  extends EjbGenericEAO<TblParam, Long>
{

    public parametrosEAO() 
    {
       super(TblParam.class);
    }

    public String obtenerParametro()
    {
        Query query =administradorEntidad.createNativeQuery("select param_chr from tbl_param where id = 1");
        String resultado = (String) query.getSingleResult();
        return resultado;
    }
    
    public BigInteger obtenerParametroNum(String paramId)
    {
        Query query =   administradorEntidad.createNativeQuery("select param_num from tbl_param where OBSERVACION = :ParamId");
        query.setParameter("ParamId", paramId);
        BigInteger resultado = (BigInteger) query.getSingleResult();
        return resultado;
    }
  
    public String obtenerRuta(String param)
    {
        Query query =administradorEntidad.createNativeQuery("select param_chr from tbl_param where observacion = ? and estado = ?");
        query.setParameter(1, param);
        query.setParameter(2, "A");
        String resultado = (String) query.getSingleResult();
        return resultado;
    }

    /*METODO OBTIENE PARAMETROS SEGUN ID */
    public String obtenerParametro(String param)
    {
        try
        {
            
            Query query = administradorEntidad.createNativeQuery("select param_chr from tbl_param where observacion = ? and estado = ?");
            query.setParameter(1, param);
            query.setParameter(2, "A");
            String resultado = (String) query.getSingleResult();
            return resultado;
        
         }catch(Exception e)
         {
            System.out.println("ERROR en parametrosEAO obtenerParametro: "+e.getMessage());
            return  null;
         }
    }
}
