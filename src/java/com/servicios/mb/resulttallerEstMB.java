
package com.servicios.mb;


import com.entidades.TblUsuarios;
import com.entidades.listas.ResultadoTallerList;
import com.entidades.listas.UnidadList;
import com.servicios.impl.ITestUsuarioSession;
import com.servicios.impl.IUsuarioSession;
import com.servicios.impl.IservicioAplicacion;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;


/**
 *
 * @author jespinoza
 */
@ManagedBean(name = "resultTallerEstMB")
@ViewScoped
public class resulttallerEstMB implements Serializable{
    
    private static final long   serialVersionUID = 1L;

    private Long                         unidad_id;
    private List<UnidadList>             unidadList;
    private List<ResultadoTallerList>    resultadoList;
    @EJB
    private IservicioAplicacion          servicioAplicacion;

    @Inject
    @ITestUsuarioSession
    private IUsuarioSession<TblUsuarios> usuarioSession;
    private String IdUserSession;
    
    @PostConstruct
    public void init()
    { 
        System.out.println("resulttallerMB UsuarioCod: "+usuarioSession.getUsuarioSession().getId());
        unidadList        =     servicioAplicacion.consultaUnidadRegistrada(Long.valueOf("0"));
    }

    public void buscaResultadoTaller()
    {
        System.out.println("resulttallerMB buscaResultadoTaller: UsuarioCod: "+usuarioSession.getUsuarioSession().getId()+
                           " unidad_id: "+unidad_id);
        resultadoList = servicioAplicacion.consultaResultadoEstudiantes(usuarioSession.getUsuarioSession().getId(), unidad_id );
    } 

    public String getUserSessionId() 
    {
        System.out.println("Usuario de Sesion: " + usuarioSession.getUsuarioSession().getUsuario());
        return usuarioSession.getUsuarioSession().getUsuario();
    }
    


    public Long getUnidad_id() {
        return unidad_id;
    }

    public void setUnidad_id(Long unidad_id) {
        this.unidad_id = unidad_id;
    }

    public List<UnidadList> getUnidadList() {
        return unidadList;
    }

    public void setUnidadList(List<UnidadList> unidadList) {
        this.unidadList = unidadList;
    }

    public List<ResultadoTallerList> getResultadoList() {
        return resultadoList;
    }

    public void setResultadoList(List<ResultadoTallerList> resultadoList) {
        this.resultadoList = resultadoList;
    }
    
    
 
}
