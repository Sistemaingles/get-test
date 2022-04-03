
package com.servicios.mb;


import com.entidades.TblDestreza;
import com.entidades.TblUsuarios;
import com.seguridad.StringEncript;
import com.servicios.impl.ITestUsuarioSession;
import com.servicios.impl.IUsuarioSession;
import com.servicios.impl.IservicioAplicacion;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
//import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;

/**
 *
 * @author JESPINOZA
 */

/*
    CLASE JAVA MANAGED BEANS DE INDEX 
*/
@ManagedBean(name = "destrezaMB")
@RequestScoped

public class DestrezaMB implements Serializable
{
    
    private static final long serialVersionUID = 1L;
    private BigInteger cantidadListening;
    private BigInteger cantidadSpeaking;
    private BigInteger cantidadWriting;
    private BigInteger cantidadReading;
    private List<TblDestreza> destreza;
    private final String key     =   "92AE31A79FEEB2A3"; //llave
    private final String iv      =   "0123456789ABCDEF"; // vector de inicialización
    
    @EJB
    private IservicioAplicacion  servicioAplicacion;
    
    @Inject
    @ITestUsuarioSession
    private IUsuarioSession<TblUsuarios> usuarioSession;
    private String IdUserSession;
    
    @PostConstruct
    public void init()
    {

        servicioAplicacion.eliminaTempActiv(usuarioSession.getUsuarioSession().getId(), "");
        destreza = servicioAplicacion.obtieneDestreza();
                
    }
    
    public DestrezaMB() 
    {
    }
    
    public String ver_unidad(Long destrezaId){
        System.out.println("ver_unidad DestrezaId: "+destrezaId);
        String destrezaIdC = "";
        try {
             destrezaIdC= StringEncript.encrypt(key, iv, destrezaId.toString());
        } catch (Exception ex) {
            Logger.getLogger(DestrezaMB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return  "/estudiante/pag_unidades.jsf?faces-redirect=true&DestrezaId="+destrezaIdC; 
    }
            
    public List<TblDestreza> getDestreza() {
        return destreza;
    }

    public void setDestreza(List<TblDestreza> destreza) {
        this.destreza = destreza;
    }
  
      
}
