
package com.servicios.mb;



import com.entidades.TblSubtema;
import com.entidades.TblTempactiv;
import com.entidades.TblUsuarios;
import com.seguridad.StringEncript;
import com.servicios.impl.ITestUsuarioSession;
import com.servicios.impl.IUsuarioSession;
import com.servicios.impl.IservicioAplicacion;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

/**
 *
 * @author JESPINOZA
 */

/*
    CLASE JAVA MANAGED BEANS DE INDEX 
*/
@ManagedBean(name = "subtemaMB")
@SessionScoped

public class SubtemaMb implements Serializable
{
    
    private static final long serialVersionUID = 1L;
    private Long Unidad_Id;
    private List<TblSubtema> subtema;
    private Long Destreza_Id;
    private Long usuario_Cod;
    private List<TblTempactiv>  temp_Activ;
  
    private final String key     =   "92AE31A79FEEB2A3"; //llave
    private final String iv      =   "0123456789ABCDEF"; // vector de inicialización
    
    @Inject
    @ITestUsuarioSession
    private IUsuarioSession<TblUsuarios> usuarioSession;
    
    @EJB
    private IservicioAplicacion  servicioAplicacion;
    
    @PostConstruct
    public void init()
    {
        try {
            //String tipo = "T";
            //servicioAplicacion.eliminaTempActiv(usuarioSession.getUsuarioSession().getId(), tipo);
            temp_Activ = servicioAplicacion.consultaTmpActividad(usuarioSession.getUsuarioSession().getId(), "");
            for (int i = 0; i < temp_Activ.size(); i++) {
                if(temp_Activ.get(i).getTempActivTipo().equals("D"))
                {
                    Destreza_Id = temp_Activ.get(i).getTempActivIdRef();
                    System.out.println("SubtemaMb DestrezaId: "+Destreza_Id);
                }
                if(temp_Activ.get(i).getTempActivTipo().equals("U"))
                {
                    Unidad_Id = temp_Activ.get(i).getTempActivIdRef();
                    System.out.println("SubtemaMb Unidad_Id: "+Unidad_Id);
                }

            }
        usuario_Cod = usuarioSession.getUsuarioSession().getId();
        
        if (Unidad_Id == null){
            
            System.out.println("SubtemaMb Unidad_Id ==null");
            
             
                FacesContext facesContext = FacesContext. getCurrentInstance();
                ExternalContext externalContext = facesContext.getExternalContext();
                Map params = externalContext.getRequestParameterMap();
                String IdC1 = (String) params.get("Id1" );
                String IdC2 = (String) params.get("Id2" );
                try {
                        IdC1 = StringEncript.decrypt(key, iv, IdC1);
                        IdC2 = StringEncript.decrypt(key, iv, IdC2);
                 } catch (Exception ex) {
                    Logger.getLogger(SubtemaMb.class.getName()).log(Level.SEVERE, null, ex);
                }

                System.out.println("DestrezaId: " + IdC1);
                System.out.println(" UnidadId: " + IdC2);

                Destreza_Id = Long.valueOf(IdC1);
                Unidad_Id   = Long.valueOf(IdC2);
                    
                TblTempactiv tmp_Activ = new TblTempactiv();
                tmp_Activ.setTempActivUsrCod(usuario_Cod);
                tmp_Activ.setTempActivIdRef(Unidad_Id);
                tmp_Activ.setTempActivTipo("U");
                tmp_Activ.setTempActivSts("A");
                servicioAplicacion.ingresaTempActiv(tmp_Activ);
             
                System.out.println("consultaSubtemaUnidad Unidad_Id: "+ Unidad_Id); 
           }
            subtema      = servicioAplicacion.consultaSubtemaUnidad(Long.valueOf("0"), Unidad_Id, "A");
            
        } catch (Exception e) {
            System.out.println("Error en metodo init SubtemaMb: "+e.getMessage());
        }
       
    }
    
    public String ver_Actividad(Long subTemaId){
        System.out.println("ver_Actividad Unidad_Id: " +Unidad_Id+
                " subTemaId: "+subTemaId);
        
        String IdC1 = "";
        String IdC2 = "";
        try {
            IdC1 = StringEncript.encrypt(key, iv, Unidad_Id.toString()); 
            IdC2 = StringEncript.encrypt(key, iv, subTemaId.toString());
        } catch (Exception ex) {
            Logger.getLogger(DestrezaMB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return  "/estudiante/pag_presentataller.jsf?faces-redirect=true&Id1="+IdC1+"&Id2="+IdC2; 
        
    }
    
    public String Regresar(){
        System.out.println("Regresar en SubtemaMb destreza_Id: " +Destreza_Id);
        String IdC1 = "";
        try {
            IdC1 = StringEncript.encrypt(key, iv, Destreza_Id.toString()); 
        } catch (Exception ex) {
            Logger.getLogger(DestrezaMB.class.getName()).log(Level.SEVERE, null, ex);
        }
        servicioAplicacion.eliminaTempActiv(usuarioSession.getUsuarioSession().getId(), "U");
        return  "/estudiante/pag_unidades.jsf?faces-redirect=true&DestrezaId="+IdC1; 
    }

    public Long getUnidad_Id() {
        return Unidad_Id;
    }

    public void setUnidad_Id(Long Unidad_Id) {
        this.Unidad_Id = Unidad_Id;
    }

    public List<TblSubtema> getSubtema() {
        return subtema;
    }

    public void setSubtema(List<TblSubtema> subtema) {
        this.subtema = subtema;
    }

    public Long getDestreza_Id() {
        return Destreza_Id;
    }

    public void setDestreza_Id(Long Destreza_Id) {
        this.Destreza_Id = Destreza_Id;
    }

    public List<TblTempactiv> getTemp_Activ() {
        return temp_Activ;
    }

    public void setTemp_Activ(List<TblTempactiv> temp_Activ) {
        this.temp_Activ = temp_Activ;
    }

    public Long getUsuario_Cod() {
        return usuario_Cod;
    }

    public void setUsuario_Cod(Long usuario_Cod) {
        this.usuario_Cod = usuario_Cod;
    }
 
    
}
