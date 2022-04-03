
package com.servicios.eao;

import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;
import com.genericos.eao.EjbGenericEAO;
import com.entidades.TblRol;
import java.math.BigInteger;
import java.util.ArrayList;

/**
 *
 * @author jespinoza
 */
@Stateless
@LocalBean
public class rolEAO extends EjbGenericEAO<TblRol, Long>
{
    private Query query;
    
    public rolEAO() 
    {
       super(TblRol.class);
    }
    
    	@SuppressWarnings("unchecked")
	public List<TblRol> listaRolesAsignar()
	{
            query = administradorEntidad.createNativeQuery(" select * from tbl_rol where estado = ? ",TblRol.class);
            query.setParameter(1, 'A');
            return query.getResultList();
	}
        
        public void añadirRol(TblRol rol)
	{
            administradorEntidad.persist(rol);	
	}
          
        @SuppressWarnings("unchecked")
	public List<TblRol> listaRol(String rol)
	{
            query = administradorEntidad.createNativeQuery(" select * from tbl_rol where rol = ? ",TblRol.class);
            query.setParameter(1, rol);
            return query.getResultList();
	}
        
        @SuppressWarnings("unchecked")
	public TblRol obtieneRolxId(Long rolId)
	{
            List<Object[]> lResult = new ArrayList<Object[]>();
            
            query = administradorEntidad.createNativeQuery(" select r.id, r.estado, r.observacion , r.rol , r.fecha_insercion , r.usuario_insercion " +
                                                                 " from tbl_rol r "  +
                                                                 " where r.id  = ? " +
                                                                 " and r.estado = ? ");
            query.setParameter(1, rolId);
            query.setParameter(2, "A");
            
            TblRol rol      =   new TblRol();
            
            lResult = query.getResultList();
            for (Object[] objects : lResult) 
            {
                rol.setId(((BigInteger) objects[0]).longValue());
                System.out.println("Id de Rol en EAO: "+objects[0].toString()); 
                rol.setEstado((String) objects[1]);
                System.out.println("eSTADO en EAO: "+(String) objects[1]);   
                rol.setObservacion((String) objects[2]);
                System.out.println("Observacion de Rol en EAO: "+(String) objects[2]);   
                rol.setRol((String) objects[3]);
                System.out.println("Rol de Rol en EAO: "+(String) objects[3]);   

            } 
            
            return rol;
	}

        public String obtieneRolxUsuario(String usuario){
           
            try {
                query = administradorEntidad.createNativeQuery(" select UPPER(r.rol) rol " +
                                                                    " from tbl_rol r "  +
                                                                    " inner join tbl_usuarios u on u.usuario = :Usuario and u.estado = :estado " + 
                                                                    " inner join tbl_usuarios_roles ur on ur.id_usuario = u.id and ur.ID_ROL = r.id and ur.estado = :estado " +    
                                                                    " where r.estado = :estado ");
                query.setParameter("Usuario", usuario);
                query.setParameter("estado", "A");

            } catch (Exception e) {
                System.out.println("Error al obtieneRolxUsuario: "+e.getMessage());
                return "";
            }
            
            return query.getSingleResult().toString();
    
        }
        
         public String obtieneObservxUsuario(String usuario){
           
            try {
                query = administradorEntidad.createNativeQuery(" select UPPER(r.observacion) observacion " +
                                                                    " from tbl_rol r "  +
                                                                    " inner join tbl_usuarios u on u.usuario = :Usuario and u.estado = :estado " + 
                                                                    " inner join tbl_usuarios_roles ur on ur.id_usuario = u.id and ur.ID_ROL = r.id and ur.estado = :estado " +    
                                                                    " where r.estado = :estado ");
                query.setParameter("Usuario", usuario);
                query.setParameter("estado", "A");

            } catch (Exception e) {
                System.out.println("Error en Metodo obtieneObservxUsuario: "+e.getMessage());
                return "";
            }
            
            return query.getSingleResult().toString();
    
        }
}
