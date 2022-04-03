
package com.servicios.eao;


import com.entidades.TblDestreza;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;

/**
 *
 * @author mespinoza
 */

@Stateless
@LocalBean
public class DestrezaEAO {
    @PersistenceContext(unitName = "project",type = PersistenceContextType.TRANSACTION)
    
    protected EntityManager administradorEntidad;
    
     /*Metodo obtiene Destrezas*/
    public List<TblDestreza> obtieneDestreza()
    {
        List<Object[]> lResultado         = new ArrayList<Object[]>();
        List<TblDestreza> listDestreza    = new ArrayList<TblDestreza>();
        System.out.println("Inicio obtieneDestreza en DestrezaEAO");
        
        String sql  = " select p.destreza_Id, p.destreza_nombre, p.destreza_clase, p.destreza_icono" +
                      "  from tbl_destreza p "        +
                      " where p.destreza_sts = 'A' "  +  
                      " order by p.destreza_Id    "  ;
    
        Query query = administradorEntidad.createNativeQuery(sql);
        lResultado = query.getResultList();
        
        System.out.println("Cantidad de Destrezas: "+lResultado.size());
        
        for (Object[] objs : lResultado) 
        {
            TblDestreza destreza = new TblDestreza();
            
            Long destreza_cod = Long.valueOf(objs[0].toString());
            destreza.setDestrezaId(destreza_cod);
            destreza.setDestrezaNombre((String) objs[1]);
            destreza.setDestrezaClase((String) objs[2]);
            destreza.setDestrezaIcono((String) objs[3]);
            
            listDestreza.add(destreza);   
        }
        
        return listDestreza;
    }
    
}
