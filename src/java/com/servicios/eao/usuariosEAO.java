
package com.servicios.eao;

import com.genericos.eao.EjbGenericEAO;
import com.entidades.TblUsuarios;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;
import java.util.HashMap;
import com.seguridad.StringEncript;


/**
 *
 * @author AMORAN
 */
@Stateless
@LocalBean
public class usuariosEAO extends EjbGenericEAO<TblUsuarios, Long>
{
    
    @EJB
    private parametrosEAO paramEAO;
    private String sql;
    private final String key     =   "92AE31A79FEEB2A3"; //llave
    private final String iv      =   "0123456789ABCDEF"; // vector de inicialización
    
    public usuariosEAO() 
    {
        super(TblUsuarios.class);
    }
    
    public List<TblUsuarios> obtenerUsrActivos() throws Exception
    {
        try 
        {
            sql = paramEAO.obtenerParametro();
            System.out.println("resultado "+sql);
            //Query query =administradorEntidad.createNativeQuery("Select *from tbl_usuarios u where u.estado = 'A' ",Usuarios.class);
             Query query = administradorEntidad.createNativeQuery(sql,TblUsuarios.class);
            System.out.println("EJECUTO LA SENTENCIA ");
            return query.getResultList();
           
        }catch(Exception ex) 
        {
            throw new Exception("ERROR QUERY " + ex.toString());
	}	

    }
    
    
    public List<TblUsuarios> obtenerUsuario(String usuario) throws Exception
    {
        try 
        {
            sql = "select * from tbl_usuarios where usuario = ? ";
            Query query = administradorEntidad.createNativeQuery(sql,TblUsuarios.class);
            query.setParameter(1, usuario);
            System.out.println("EJECUTO LA SENTENCIA ");
            return query.getResultList();
           
        }catch(Exception ex) 
        {
            throw new Exception("ERROR QUERY " + ex.toString());
	}	

    }
    
    
    public String obtenerNombreUsuarioSesion(String usuario)
    {

	String nombre = " ";
        
        sql = "select nomape from tbl_usuarios where usuario = ?"; 

        
	Query quer = administradorEntidad.createNativeQuery(sql);
	quer.setParameter(1, usuario);

        try{
            nombre = (String) quer.getSingleResult();
//            System.out.println("NOMBRE: "+nombre);
            }
        catch(Exception e)
        {
            System.out.println("ERROR"+e.toString());
            return usuario;
	}
        if(nombre == null || nombre.equals(""))
        {
            return usuario;
	}else
        {
	return nombre;
	}
        
    }
    
 
        
    //METODO RESETEAR CONTRASEÑA .-
    public TblUsuarios resetPassword(String identificacion)
    {
        try {
            sql = "select * from tbl_usuarios where usuario = ? "; 
            

             Query query = administradorEntidad.createNativeQuery(sql,TblUsuarios.class);
             query.setParameter(1, identificacion);
             System.out.println("EJECUTO LA SENTENCIA resetPassword ");
             TblUsuarios userPass = (TblUsuarios) query.getSingleResult();
             //System.out.println("NOMBRE: "+userPass.getNomApe()); COMENTADO AMORAN 11102017
             System.out.println("IDENTIFICACION: "+userPass.getUsuario());
             
             return userPass;
             
            }catch(Exception ex) 
            {
                System.out.println("ERROR: "+ex.getMessage());
                return null;
            }	

    }    

    public TblUsuarios Autenticar(String pUsuario, String pClave) 
    {
        HashMap<String, Object> parametros = new HashMap<String, Object>();
        parametros.put("usuario", pUsuario);
        
        TblUsuarios user = null ;
        
        try
	{
            user = usuariosEAO.this.findUniqueByProperties(parametros);
            
            System.err.println("Usuario: "+user.getUsuario());
            
            if (user.getUsuario()==null || user.getUsuario()=="")
            {
		System.err.println("El usuario no existe");
		//          logger.info("El usuario no existe");
		return null;
            }
				
            try {
                //if (!UtilCryptography.desencriptar(user.getClave()).equals(pClave)) COMENTADO 17062017
                if(! StringEncript.decrypt(key, iv, user.getClave()).equals(pClave))
                {
                    System.err.println("La clave no es la correcta");
//			logger.info("La clave no es la correcta");
                    return null;
                }
            } catch (Exception e) 
            {
                    System.err.println("Error al Autenticar2: "+e.getMessage());
                    //e.printStackTrace();
            } 
	
        }catch (Exception e) 
        {
            System.err.println("Error al Autenticar: "+e.getMessage());
            //e.printStackTrace();
        } 

	return user;
    }
    
    public void actualizaUsuarios(TblUsuarios usuarios)
    {
	administradorEntidad.merge(usuarios);	
    }
    
    public void actualizaEstadoUsuario(TblUsuarios usuarios)
    {
	try 
        {
            System.out.println("Ingreso actualizaEstadoUsuario");
            sql  = "update tbl_usuarios set estado = :estado where id = :id_usuario  " ;
                
            Query query = administradorEntidad.createNativeQuery(sql);
            query.setParameter("id_usuario", usuarios.getId());
            query.setParameter("estado", usuarios.getEstado());
            
            query.executeUpdate();
            
        } catch (Exception e) 
        {
            System.out.println("Error al actualiza Estado de Usuario: "+e.getMessage());
        }	
    }
       
    public void eliminaUsuario(TblUsuarios usuarios)
    {
        try 
            {
                sql  = "delete from tbl_usuarios where id = :id_usuario  " ;
                
                Query query = administradorEntidad.createNativeQuery(sql);
                query.setParameter("id_usuario", usuarios.getId());
                query.executeUpdate();
            
               

            } catch (Exception e) 
            {
                System.out.println("Error al eliminar UsuarioRol en EAO: "+e.getMessage());
            }
    }
    
    
    public Long añadirUsuarios(TblUsuarios usuarios)
    {
	administradorEntidad.persist(usuarios);	
        administradorEntidad.flush(); //AGREGADO AMORAN 11102017
        System.out.println("Id en Añadir Usuarios en usuariosEAO : "+usuarios.getId());
        return usuarios.getId(); 
    }
    
    //ADD 26022018
    public TblUsuarios ValidaUsuario(String pUsuario)
    {
        HashMap<String, Object> parametros = new HashMap<String, Object>();
        parametros.put("usuario", pUsuario);
        try{
            TblUsuarios user = usuariosEAO.this.findUniqueByProperties(parametros);
            return user;
        }catch(Exception e){
            TblUsuarios user = new TblUsuarios();
            System.out.println("EL USUARIO NO EXISTE "+e.getMessage());
            return user;
        }
    }
}


