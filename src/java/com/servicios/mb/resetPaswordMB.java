
package com.servicios.mb;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import org.primefaces.context.RequestContext;
import com.entidades.TblUsuarios;
import com.seguridad.StringEncript;
import com.servicios.impl.ICierreSession;
import com.servicios.impl.ITestCierreSession;
import com.servicios.impl.ITestUsuarioSession;
import com.servicios.impl.IUsuarioSession;
import com.servicios.impl.IservicioAplicacion;

/**
 *
 * @author AMORAN
 */
@ManagedBean(name = "resetPasswMb")
@ViewScoped
public class resetPaswordMB implements Serializable 
{
    
    private static final long serialVersionUID = 1L;
    private TblUsuarios usuario;
    private String newPass;
    private String confirmPass;
    private String actualPass;
    private String mensaje;
    
    private final String key     =   "92AE31A79FEEB2A3"; //llave
    private final String iv      =   "0123456789ABCDEF"; //vector de inicialización
     
    @Inject
    @ITestUsuarioSession
    private IUsuarioSession<TblUsuarios> usuarioSession;
    
    @Inject
    @ITestCierreSession
    private ICierreSession cierreSession;
    
    @EJB
    private IservicioAplicacion servicioAplicacion;
    
    @PostConstruct
    public void init()
    {
       cargarDatos();
       limpiar();
       actualPass = "";
       System.out.println("ACTUAL PASS"+"["+actualPass+"]");
    }
    
    
    public void cargarDatos()
    {
      usuario =  servicioAplicacion.resetPassword(usuarioSession.getUsuarioSession().getUsuario());
    }

    public TblUsuarios getUsuario() 
    {
        return usuario;
    }

    public void setUsuario(TblUsuarios usuario) 
    {
        this.usuario = usuario;
    }

    public String getNewPass() 
    {
        return newPass;
    }

    public void setNewPass(String newPass) 
    {
        this.newPass = newPass;
    }

    public String getConfirmPass() 
    {
        return confirmPass;
    }

    public String getActualPass() 
    {
        return actualPass;
    }

    public void setActualPass(String actualPass) 
    {
        this.actualPass = actualPass;
    }

    public String getMensaje() 
    {
        return mensaje;
    }

    public void setMensaje(String mensaje) 
    {
        this.mensaje = mensaje;
    }
        
    public void limpiar()
    {
        newPass="";
        confirmPass = "";
    }

    public void setConfirmPass(String confirmPass) 
    {
        this.confirmPass = confirmPass;
    }
       
    public void actualizaPass()
    {
        try 
        {
            System.out.println("NUEVA CONTRASEÑA ES: "+newPass);
            System.out.println("CONFIRMAR CONTRASEÑA: "+confirmPass);
            RequestContext  request = RequestContext.getCurrentInstance();
            //String pattern    = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}";
            String pattern    = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}";
            boolean passValid = newPass.matches(pattern);   
            System.out.println("Pattern: "+passValid);
            
            if (newPass.equals(confirmPass) && newPass.length() > 0 && confirmPass.length() > 0 &&
                passValid == true
                )
            {
                usuario.setClave(StringEncript.encrypt(key, iv, newPass));
                Long RolId = servicioAplicacion.obtieneRolUsuario(usuarioSession.getUsuarioSession().getId());
                System.out.println("Rol de Usuario de Sesion en ResetPasswordMb:"+RolId);
              
                usuario.setClave_Usr(newPass); //ADD 05082018
           
                
                servicioAplicacion.actualizaUsuarios(usuario);

                mensaje = "EL CAMBIO DE CONTRASEÑA SE REALIZÓ EXITOSAMENTE";

                request.execute("PF('dialogConfirPass').show();");
            }
            else
            {    
                if (!newPass.equals(confirmPass))
                {
                    System.out.println("CONTRASEÑAS NO COINCIDEN");
                    mensaje = "CONTRASEÑAS NO COINCIDEN";
                    request.execute("PF('dialogConfirPass').show();");
                }
                else
                {
                    if (passValid == false)
                    {
                        mensaje = "CONTRASEÑA NO VALIDA ......... "
                                + " EJEMPLO DE UNA CONTRASEÑA VALIDA: Example123";
                        request.execute("PF('dialogConfirPass').show();");           
                    }
    
                }
            }
        }catch(Exception ex) 
        {
            mensaje = "CONTRASEÑAS NO COINCIDEN";
            System.out.println("ERROR: "+ex.getMessage());
        }	
      
    }    
    
    public void cerrarSession() throws IOException 
    {
        cierreSession.cerrarSession();
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().redirect( "/AppWeb/login.jsf" );

    }
//        public void cerrarSession() 
//        {
//            try{
//		FacesContext context = FacesContext.getCurrentInstance();
//		ExternalContext externalContext = context.getExternalContext();
//		Object session = externalContext.getSession(false);
//		HttpSession httpSession = (HttpSession) session;
//		httpSession.invalidate();
//                System.out.println("Sesión invalidada");
//                FacesContext contex = FacesContext.getCurrentInstance();
//                contex.getExternalContext().redirect( "./login.jsf" );
//            }catch(Exception e)
//            {
//                System.out.println("ERROR: "+e.getMessage());
//            }
//	}
    
}
