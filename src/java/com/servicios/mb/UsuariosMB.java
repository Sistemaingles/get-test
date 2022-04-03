
package com.servicios.mb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import org.primefaces.event.RowEditEvent;
import com.entidades.TblRol;
import com.entidades.TblUsuarios;
import com.entidades.TblUsuariosRoles;
import com.seguridad.StringEncript;
//import roca.seguridad.UtilCryptography; COMENTADO 17062017
import com.servicios.impl.IservicioAplicacion;



/**
 *
 * @author AMORAN
 */
@Named("usrMB")
@SessionScoped
public class UsuariosMB extends BeanFormulario implements Serializable 
{
    
    private static final long serialVersionUID = 1L;
    private List<TblUsuarios> listUsr;
    private String nom_Usr;
    private String pass_usr;
    private TblUsuarios user;
    private String nombres;
    private String apellidos;
    private List<TblRol> listRol;
    private String idRol;
    private String observacion;
    private String usuarioB;
    private final String key     =   "92AE31A79FEEB2A3"; //llave
    private final String iv      =   "0123456789ABCDEF"; //vector de inicialización
    @EJB
    private IservicioAplicacion servicioAplicacion;
        
    @PostConstruct
    public void init() 
    {
        listUsr = new ArrayList<TblUsuarios>();
        llenarlistUsr();
          //  recorreLista();
        llenarlistRol();
        usuarioB = "";
    }
    
    public void inicializa() 
    {
	nombres     = "";
	apellidos   = "";
	pass_usr    = "";
        nom_Usr     = "";
	idRol       = null;
    }  
    
    public void llenarListaUsuarios()
    {
	try 
        {
            System.out.println("Ingreso Método llenarListaUsuarios");
            listUsr = servicioAplicacion.obtenerUsuario(usuarioB);
	}catch(Exception e)
        {
            System.out.println("ERROR en llenarListaUsuarios :"+e.getMessage());

	}
        usuarioB = "";
    }
        
    public void llenarlistUsr() 
    {
        try
        {
            listUsr = servicioAplicacion.obtenerUsrActivos();
        }catch(Exception e)
        {
            System.out.println("ERROR en llenarlistRol: "+e.getMessage());
        }
    }
        
    public void llenarlistRol() 
    {
        try
        {
            listRol = servicioAplicacion.listaRolesAsignar();
        }catch(Exception e)
        {
           System.out.println("ERROR en llenarlistRol: "+e.getMessage());
        }
    }
        
    public void actualizarUsuario(RowEditEvent event) 
    {  
        
        try 
        {
            TblUsuarios usr = new TblUsuarios(); 
            usr = (TblUsuarios) event.getObject();
            //usr.setFchUpd(new Date()); COMENTADO 09082017
            //usr.setClave(UtilCryptography.encriptar(usr.getClave())); COMENTADO 17062017
            usr.setClave(StringEncript.encrypt(key, iv, usr.getClave()));
            servicioAplicacion.actualizaUsuarios(usr);
            FacesContext context = FacesContext.getCurrentInstance();          
            context.addMessage(null, new FacesMessage("Actualización realizada con éxito", "Actualización con éxito"));  
        } catch (Exception e) 
        {
            System.out.println("ERROR en actualizarUsuario: "+e.getMessage());
            //e.printStackTrace();
        }
    }  
	
    public void cancelarUsuarios()
    {		
	llenarlistUsr();
	FacesContext context = FacesContext.getCurrentInstance();          
        context.addMessage(null, new FacesMessage("Ud. Canceló la actualización", "Cancelada Transacción de Actualizar"));
    }
        

	


    public List<TblUsuarios> getListUsr() 
    {
        return listUsr;
    }

    public void setListUsr(List<TblUsuarios> listUsr) 
    {
        this.listUsr = listUsr;
    }

    public String getNom_Usr() 
    {
        return nom_Usr;
    }

    public void setNom_Usr(String nom_Usr) 
    {
        this.nom_Usr = nom_Usr;
    }

    public String getPass_usr() 
    {
        return pass_usr;
    }

    public void setPass_usr(String pass_usr) 
    {
        this.pass_usr = pass_usr;
    }

    public TblUsuarios getUser() 
    {
        return user;
    }

    public void setUser(TblUsuarios user) 
    {
        this.user = user;
    }

    public String getNombres() 
    {
        return nombres;
    }

    public void setNombres(String nombres) 
    {
        this.nombres = nombres;
    }

    public String getApellidos() 
    {
        return apellidos;
    }

    public void setApellidos(String apellidos) 
    {
        this.apellidos = apellidos;
    }

    public List<TblRol> getListRol() 
    {
        return listRol;
    }

    public void setListRol(List<TblRol> listRol) 
    {
        this.listRol = listRol;
    }

    public String getIdRol() 
    {
        return idRol;
    }

    public void setIdRol(String idRol) 
    {
        this.idRol = idRol;
    }

    public String getUsuarioB() 
    {
        return usuarioB;
    }

    public void setUsuarioB(String usuarioB) 
    {
        this.usuarioB = usuarioB;
    }
   
}

