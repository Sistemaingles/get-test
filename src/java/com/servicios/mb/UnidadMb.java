
package com.servicios.mb;

import com.entidades.TblTempactiv;
import com.entidades.TblUsuarios;
import com.entidades.listas.UnidadList;
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
@ManagedBean(name = "unidadMB")
@SessionScoped

public class UnidadMb implements Serializable
{
    
    private static final long serialVersionUID = 1L;
    private Long destreza_Id;
    private Long usuario_Cod;
    private List<UnidadList> unidad;
    private List<TblTempactiv>  temp_Activ;
    private String tipo = "D"; //Destreza
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
            temp_Activ = servicioAplicacion.consultaTmpActividad(usuarioSession.getUsuarioSession().getId(), tipo);
        
            for (int i = 0; i < temp_Activ.size(); i++) {
                if(temp_Activ.get(i).getTempActivTipo().equals("D"))
                {
                    destreza_Id = temp_Activ.get(i).getTempActivIdRef();
                    System.out.println("UnidadMb DestrezaId: "+destreza_Id);
                }

            }
            System.out.println("DestrezaId en Init UnidadMB: "+destreza_Id); 
            usuario_Cod = usuarioSession.getUsuarioSession().getId();
            
            if (destreza_Id == null){
                
                FacesContext facesContext = FacesContext. getCurrentInstance();
                ExternalContext externalContext = facesContext.getExternalContext();
                Map params = externalContext.getRequestParameterMap();
                String DestrezaIdC = (String) params.get("DestrezaId" );
                try {
                    DestrezaIdC= StringEncript.decrypt(key, iv, DestrezaIdC);
                } catch (Exception ex) {
                 System.out.println("Error al desencriptar DestrezaId: "+ex.getMessage());
                }
                Long DestrezaId = Long.valueOf(DestrezaIdC);
                destreza_Id = DestrezaId;

             TblTempactiv tmp_Activ = new TblTempactiv();
             tmp_Activ.setTempActivUsrCod(usuario_Cod);
             tmp_Activ.setTempActivIdRef(destreza_Id);
             tmp_Activ.setTempActivTipo(tipo);
             tmp_Activ.setTempActivSts("A");
             servicioAplicacion.ingresaTempActiv(tmp_Activ);

            } 
            System.out.println("consultaUnidadporDestreza Destreza Id: "+ destreza_Id+
                        " usuario_Cod: " +usuario_Cod ); 
            unidad      = servicioAplicacion.consultaUnidadporDestreza(destreza_Id, usuario_Cod);
        
        } catch (Exception e) {
            System.out.println("Error en Metodo init UnidadMb: "+e.getMessage());
        }
       
       
    }
    
     public String ver_Leccion(Long unidadId){
        System.out.println("ver_Leccion destreza_Id: " +destreza_Id+
                           " unidadId: "+unidadId);
        String IdC1 = "";
        String IdC2 = "";
        try {
            IdC1 = StringEncript.encrypt(key, iv, destreza_Id.toString()); 
            IdC2 = StringEncript.encrypt(key, iv, unidadId.toString());
        } catch (Exception ex) {
            Logger.getLogger(DestrezaMB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return  "/estudiante/pag_subtemas.jsf?faces-redirect=true&Id1="+IdC1+"&Id2="+IdC2; 
    }
    
    public String ver_Vocabulario(Long unidadId){
        System.out.println("ver_Vocabulario destreza_Id: " +destreza_Id+
                " unidadId: "+unidadId);
        String IdC1 = "";
        String IdC2 = "";
        try {
            IdC1 = StringEncript.encrypt(key, iv, destreza_Id.toString()); 
            IdC2 = StringEncript.encrypt(key, iv, unidadId.toString());
        } catch (Exception ex) {
            Logger.getLogger(DestrezaMB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return  "/estudiante/pag_vocabulario.jsf?faces-redirect=true&Id1="+IdC1+"&Id2="+IdC2; 
        
    }
     

    public Long getDestreza_Id() {
        return destreza_Id;
    }

    public void setDestreza_Id(Long destreza_Id) {
        this.destreza_Id = destreza_Id;
    }

    public List<UnidadList> getUnidad() {
        return unidad;
    }

    public void setUnidad(List<UnidadList> unidad) {
        this.unidad = unidad;
    }

    public Long getUsuario_Cod() {
        return usuario_Cod;
    }

    public void setUsuario_Cod(Long usuario_Cod) {
        this.usuario_Cod = usuario_Cod;
    }

    public List<TblTempactiv> getTemp_Activ() {
        return temp_Activ;
    }

    public void setTemp_Activ(List<TblTempactiv> temp_Activ) {
        this.temp_Activ = temp_Activ;
    }
    
    
}
