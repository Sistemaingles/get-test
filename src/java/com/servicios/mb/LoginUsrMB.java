
package com.servicios.mb;


import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import com.entidades.TblUsuarios;
import com.seguridad.StringEncript;
import com.servicios.impl.IGuardaUsuarioSession;
import com.servicios.impl.ITestUsuarioSession;
import com.servicios.impl.IUsuarioSession;
import com.servicios.impl.IservicioAplicacion;

/**
 *
 * @author AMORAN
 */
@ManagedBean(name = "loginUsrMb")
@RequestScoped
public class LoginUsrMB extends BeanFormulario implements Serializable 
{
    
    private static final long serialVersionUID = 1L;
    private String usuario;
    private String clave;
    private String nombreUsr;
    private Long   RolId;   //ADD 26022018
    private final String key     =   "92AE31A79FEEB2A3"; //llave
    private final String iv      =   "0123456789ABCDEF"; // vector de inicialización
    
    @Inject
    @ITestUsuarioSession
    private IUsuarioSession<TblUsuarios> usuarioSession;
    
    @Inject
    private ErrorMB errorMB;   
    
    @EJB
    private IservicioAplicacion servicioAplicacion;

    public LoginUsrMB() 
    {
    }
    
    @PostConstruct
    public void init()
    {
        usuario = "";
        clave   = "";
    }

    public String AutenticarUsr() 
    {
        try
        {
            FacesContext context =  FacesContext.getCurrentInstance();        
            TblUsuarios usr         =  servicioAplicacion.Autenticar(usuario,clave);
            
           if (usr == null)
           {    
                //context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"ERROR", "USUARIO/CLAVE Invalidos" ) );
                addError("ERROR", "USUARIO/CLAVE Invalidos"); 
                
                return "";  
           } 
           else if(usr.getEstado().equals("I"))
            {
                context.addMessage(null, new FacesMessage("Successful",  "Usuario Inactivo"+usr.getUsuario() ) );
		addMensaje("MENSAJE", "Usuario Inactivo");
                return "";
            }
            else
            {
                if(usr.getEstado().equals("A"))
               
                usuarioSession.setUsuarioSession(usr);   
                generarSemillaSegura(usr);
                System.out.println("Usuario de Sesion en LoginUsrMB: "+usuarioSession.getUsuarioSession().getId()); //ADD 26022018
                RolId = servicioAplicacion.obtieneRolUsuario(usuarioSession.getUsuarioSession().getId());
                
                
                servicioAplicacion.eliminaTempActiv(usuarioSession.getUsuarioSession().getId(), "");
                
          
                return  "/index?faces-redirect=true"; 
   
                        
            }
        }
        catch(Exception e)
        {
            if(e.getMessage().equals("No existe"))
            {
//                FacesContext context = FacesContext.getCurrentInstance();    
//                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"ERROR","USUARIO NO EXISTE"));
                addError("ERROR", "USUARIO NO EXISTE"); 
                return "";
            }else
            {
                 FacesContext context = FacesContext.getCurrentInstance();    
                 context.addMessage(null, new FacesMessage("Successful",  "Your message: Hola " ) );
                 //errorMB.llenarVariablesError(ajaxErrorHandler(e));
            }
	}
        return "";
    }
    
    private void generarSemillaSegura(TblUsuarios usuario)
    {
	try
	{
            //((HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false)).setAttribute(IGuardaUsuarioSession.SEMILLA, UtilCryptography.encriptar(usuario.getUsuario().toUpperCase()) ); COMENTADO 17062017
            ((HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false)).setAttribute(IGuardaUsuarioSession.SEMILLA, StringEncript.encrypt(key, iv, usuario.getUsuario().toUpperCase()));
            
	}catch (Throwable e) 
        {
            e.printStackTrace();
	}
    }
    
    
    public String obtenerNombreUsuarioSesion()
    {     
        nombreUsr = servicioAplicacion.obtenerNombreUsuarioSesion(usuarioSession.getUsuarioSession().getUsuario());
             
//        System.out.println("Usuario Id: "+usuarioSession.getUsuarioSession().getId());
//        System.out.println("Nombre de Usuario de Sesion: "+nombreUsr);
        return nombreUsr;     
    }
             
    public String goHome()
    {
	return "/index?faces-redirect=true";
    }
       
    public String goRegistro()
    {
       return "/pag_usuarios?faces-redirect=true";
    }
       
    public String goOlvidePass()
    {
       return "/olvidoPass?faces-redirect=true";
    }

    public String getUsuario() 
    {
        return usuario;
    }

    public void setUsuario(String usuario) 
    {
        this.usuario = usuario;
    }

    public String getClave() 
    {
        return clave;
    }

    public void setClave(String clave) 
    {
        this.clave = clave;
    } 

    public String getNombreUsr() 
    {
        nombreUsr = obtenerNombreUsuarioSesion();
        return nombreUsr;
    }

    public void setNombreUsr(String nombreUsr) 
    {
        this.nombreUsr = nombreUsr;
    }

    public Long getRolId() 
    {
        return RolId;
    }

    public void setRolId(Long RolId) 
    {
        this.RolId = RolId;
    }

    
}
