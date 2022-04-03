
package com.servicios.eao;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import com.genericos.eao.EjbGenericEAO;
import com.entidades.TblUsuariosRoles;
import java.math.BigInteger;
import javax.persistence.Query;

/**
 *
 * @author AMORAN
 */
@Stateless
@LocalBean
public class usuariosRolesEAO extends EjbGenericEAO<TblUsuariosRoles, Long> 
{
    private String sql  = " " ;
    
	public usuariosRolesEAO() 
        {
            super(TblUsuariosRoles.class);
	}
    
        public void añadirUsuariosRoles(TblUsuariosRoles usuariosRoles)
	{
            /*System.out.println("*** Metodo añadirUsuariosRoles en UsuariosRolesEAO ***");
            System.out.println("Id:"+usuariosRoles.getId());
            System.out.println("Desc:"+usuariosRoles.getDescripcion());
            System.out.println("Observ:"+usuariosRoles.getObservacion());
            System.out.println("Id_Rol:"+usuariosRoles.getRol().toString());
            System.out.println("Id_Usuario:"+usuariosRoles.getUsuario().toString());
            System.out.println("FchIns:"+usuariosRoles.getFchIns());
            System.out.println("UsrIns:"+usuariosRoles.getUsrIns());*/
            administradorEntidad.persist(usuariosRoles);	
	}
        
        public void eliminaUsuarioRol(TblUsuariosRoles usuariosRoles)
        {
            try 
            {
                administradorEntidad.remove(usuariosRoles);
                
                sql  = "delete from tbl_usuarios_roles where id_usuario = :id_usuario  " ;
                
                Query query = administradorEntidad.createNativeQuery(sql);
                query.setParameter("id_usuario", usuariosRoles.getUsuario().getId());
                query.executeUpdate();

            } catch (Exception e) 
            {
                System.out.println("Error al eliminar UsuarioRol en EAO: "+e.getMessage());
            }
        }
   
        /*METODO OBTIENE ROL DE USUARIO POR ID <ADD 26022018>*/
        public Long obtieneRolUsuario(Long id_usuario)
        {
            Long id_rol = Long.valueOf("0");
            try 
            {
                sql  = "select u.id_rol from tbl_usuarios_roles u where u.id_usuario = :id_usuario  " ;
                Query query = administradorEntidad.createNativeQuery(sql);
                query.setParameter("id_usuario", id_usuario);
                
                BigInteger rol = (BigInteger) query.getSingleResult();
                
                id_rol  = rol.longValue();
                        
            } catch (Exception e) 
            {
                System.out.println("Error al obtieneRolUsuario en usuariosRolesEAO: "+e.getMessage());
            }
            return id_rol;
        }
}
