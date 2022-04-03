
package com.servicios.mb;


import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;
import com.entidades.TblUsuarios;
import com.seguridad.StringEncript;
import com.servicios.impl.IservicioAplicacion;

/**
 *
 * @author AMORAN
 */
@ManagedBean(name = "olvidePassMb")
@SessionScoped
public class olvidePassMB extends BeanFormulario implements Serializable 
{
    private static final long serialVersionUID = 1L;
    private String usuario;
    private String txt_confirmacion;
    private String correo;
    private String texto;
    private TblUsuarios usr;
    private String texto_plantilla;
    private String plantilla;
    private String referencia;
    
    private final String key     =   "92AE31A79FEEB2A3"; //llave
    private final String iv      =   "0123456789ABCDEF"; //vector de inicialización
    
    @EJB
    private IservicioAplicacion  servicioAplicacion;
    
    
    public void validaUsr()
    { 
       FacesContext context = FacesContext.getCurrentInstance();
      try 
      {
            System.out.println("Usuario en validaUsr: "+usuario);
            usr = servicioAplicacion.ValidaUsuario(usuario);
            System.out.println("Clave en validaUsr: "+usr.getClave());
            
            if (!usr.equals(null))
            {
                System.out.println("Usuario Id: "+usr.getId());
                
                //persona = servicioAplicacion.obtieneinfoPersona(usr.getId());
                
                //System.out.println("Correo para envio de Contraseña: "+persona.getEmail());
                
                //texto     = reemplazaComodin(usr , persona.getEmail());
                plantilla = reemplazaComodinMail(usr);
                
                //METODO DE ENVIA CORREO
                referencia = "RESETEO DE CONTRASEÑA - SYSTEM MED";
    
                //servicioAplicacion.enviaMail(usr.getNomApe(), persona.getEmail() , plantilla,referencia);

                System.out.println("METODO VALIDAR: "+texto);
                RequestContext  request = RequestContext.getCurrentInstance();
                request.execute("PF('dialogConfir').show();");

            }
      
      }catch(Exception e)
      {
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"ERROR", "USUARIO "+usuario+" NO EXISTE" ) );
      }
    }
    
  
    public String reemplazaComodin(TblUsuarios usuario , String correo)
    {
        txt_confirmacion =  servicioAplicacion.obtenerParametro("TEXTO_CONFIRMACION");
        txt_confirmacion =  txt_confirmacion.replace("[USUARIO]", usuario.getNomApe().trim());
        txt_confirmacion = txt_confirmacion.replace("[EMAIL]", correo.trim());
        System.out.println("TEXTO METODO REEMPLAZA: "+txt_confirmacion);
        return txt_confirmacion;
    }
    
    public String reemplazaComodinMail(TblUsuarios usuario)
    {   
    
        DateFormat dateFormat   = new SimpleDateFormat("dd/MM/yyyy");
        Calendar cal            = Calendar.getInstance();
        String fecha            = dateFormat.format(cal.getTime()); 
         
        String clave_generica = generaClaveTemporal();
       
        System.out.println("LA CLAVE GENERADA ES: "+clave_generica);
         
        try
        {
            /*usuario.setClave(UtilCryptography.encriptar(clave_generica));
            usuario.setToken(clave_generica);
            usuario.setFchUpd(new Date());*/
          
            usuario.setClave(StringEncript.encrypt(key, iv, clave_generica));
          
          
            servicioAplicacion.actualizaUsuarios(usuario);
          
         }catch(Exception e)
         {
             System.out.println("ERROR EN ACTUALIZAR CONTRASEÑA USUARIO: "+e.getMessage());
         }

         String link     = servicioAplicacion.obtenerParametro("LINK_APP");
         String nomcia   = servicioAplicacion.obtenerParametro("NOM_CIA");
         texto_plantilla = servicioAplicacion.obtenerParametro("TEXTO_OLVIDE_PASS");
        
        texto_plantilla = texto_plantilla.replace("[USUARIO]", usuario.getNomApe().toUpperCase());
        texto_plantilla = texto_plantilla.replace("[FECHA]", fecha);
        texto_plantilla = texto_plantilla.replace("[IDENTIFICACION]", usuario.getUsuario());
        texto_plantilla = texto_plantilla.replace("[PASSWORD]", clave_generica);
        texto_plantilla = texto_plantilla.replace("[LINK]",link.trim());
        texto_plantilla = texto_plantilla.replace("[NOMCIA]",nomcia.trim());
        System.out.println("LA PLANTILLA DEL CORREO ES: "+texto_plantilla);
        return texto_plantilla;
        
    }
    

//}
    public static String generaClaveTemporal() 
    {
        String base = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

        String pswd = "";

        for (int i = 0; i < 13; i++) 
        {
            pswd    +=  (base.charAt((int)(Math.random() * base.length())));
        }

        return pswd;
    }

    public String getUsuario() 
    {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getTxt_confirmacion() 
    {
        txt_confirmacion =  servicioAplicacion.obtenerParametro("TEXTO_CONFIRMACION");
        return txt_confirmacion;
    }

    public void setTxt_confirmacion(String txt_confirmacion) {
        this.txt_confirmacion = txt_confirmacion;
    }  

    public String getTexto() 
    {
        return texto;
    }

    public void setTexto(String texto) 
    {
        this.texto = texto;
    }


    public String getCorreo() 
    {
        return correo;
    }

    public void setCorreo(String correo) 
    {
        this.correo = correo;
    }

    public String goLogin()
    {
       return "/login?faces-redirect=true";
    }
    
}
