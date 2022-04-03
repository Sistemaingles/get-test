
package com.servicios.mb;


import com.entidades.TblTempactiv;
import com.entidades.TblUsuarios;
import com.entidades.listas.VocabularioList;
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
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;


/**
 *
 * @author jespinoza
 */
@ManagedBean(name = "verVocabularioMB")
@ViewScoped

public class verVocabularioMB implements Serializable{
    
    private static final long   serialVersionUID = 1L;
    private Long unidad_id;
    private Long Destreza_Id;
    private List<TblTempactiv>  temp_Activ;
    private List<VocabularioList>  vocabularioList;
    private final String key     =   "92AE31A79FEEB2A3"; //llave
    private final String iv      =   "0123456789ABCDEF"; // vector de inicialización
    
    @EJB
    private IservicioAplicacion  servicioAplicacion;
    
    @Inject
    @ITestUsuarioSession
    private IUsuarioSession<TblUsuarios> usuarioSession;
     
    @PostConstruct
    public void init()
    { 
        FacesContext facesContext = FacesContext. getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        Map params = externalContext.getRequestParameterMap();
        /*Integer UnidadId = new Integer((String) params.get("Id2" ));
        System.out.println(" UnidadId: " + UnidadId);
        */
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
        unidad_id = Long.valueOf(IdC2);
        buscaVocabulario(unidad_id);
        
        /*temp_Activ = servicioAplicacion.consultaTmpActividad(usuarioSession.getUsuarioSession().getId(), "D");
        for (int i = 0; i < temp_Activ.size(); i++) {
            if(temp_Activ.get(i).getTempActivTipo().equals("D"))
            {   
                Destreza_Id = temp_Activ.get(i).getTempActivIdRef();
                System.out.println("verVocabularioMB DestrezaId: "+Destreza_Id);
            }
        }*/
        
    }

    public void buscaVocabulario(Long unidadId) 
    {
        //Obtiene Detalle de Vocabularios registrados por Unidad.-
        System.out.println("buscaVocabulario unidad "+unidadId);
        vocabularioList = servicioAplicacion.consultaVocabulario( unidadId );
    }
         
      public String Regresar(){
        System.out.println("Regresar en verVocabularioMB destreza_Id: " +Destreza_Id);
        String IdC1 = "";
        try {
            IdC1 = StringEncript.encrypt(key, iv, Destreza_Id.toString()); 
        } catch (Exception ex) {
            Logger.getLogger(DestrezaMB.class.getName()).log(Level.SEVERE, null, ex);
        }
        servicioAplicacion.eliminaTempActiv(usuarioSession.getUsuarioSession().getId(), "U");
        return  "/estudiante/pag_unidades.jsf?faces-redirect=true&DestrezaId="+IdC1; 
    }

    public List<VocabularioList> getVocabularioList() {
        return vocabularioList;
    }

    public void setVocabularioList(List<VocabularioList> vocabularioList) {
        this.vocabularioList = vocabularioList;
    }
    
    
    public Long getUnidad_id() {
        return unidad_id;
    }

    public void setUnidad_id(Long unidad_id) {
        this.unidad_id = unidad_id;
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

    
}
