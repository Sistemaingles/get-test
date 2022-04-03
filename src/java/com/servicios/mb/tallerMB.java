
package com.servicios.mb;


import com.entidades.TblDestreza;
import com.entidades.TblDestrezaunidad;
import com.entidades.TblPresentaciontaller;
import com.entidades.TblUnidad;
import com.entidades.TblUsuarios;
import com.entidades.listas.PresentaTallerList;
import com.entidades.listas.UnidadList;

import com.servicios.impl.ITestUsuarioSession;
import com.servicios.impl.IUsuarioSession;
import com.servicios.impl.IservicioAplicacion;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;


/**
 *
 * @author mespinoza
 */
@ManagedBean(name = "tallerMB")
@ViewScoped
public class tallerMB implements Serializable{
    
    private static final long   serialVersionUID = 1L;

    private String              taller_nombre;
    private String              taller_descripcion;
    private Long                unidad_id;
    private Long                destreza_id;
    private List<UnidadList>    unidadList;
    private List<PresentaTallerList>    tallerList;
    private Date                fechaActual;
    private BigInteger          cantidad;
    private List<TblDestreza>   destrezaList;
    
    @EJB
    private IservicioAplicacion  servicioAplicacion;

    
    @Inject
    @ITestUsuarioSession
    private IUsuarioSession<TblUsuarios> usuarioSession;
    private String IdUserSession;
    
    @PostConstruct
    public void init()
    { 
        taller_nombre     =     "";
        taller_descripcion =    "";
        //Obtiene Lista de Destrezas Activas  .-
        destrezaList           = servicioAplicacion.obtieneDestreza();
        unidadList        =     servicioAplicacion.consultaUnidad( usuarioSession.getUsuarioSession().getId(), Long.valueOf("0"), "A");
        tallerList        =     servicioAplicacion.listaTallerRegistrado(usuarioSession.getUsuarioSession().getId(), "", "");
    }

    public void buscaTallerRegistrado()
    {
        System.out.println("buscaTallerRegistrado Nombre:"+taller_nombre + "DescripcioN:"+taller_descripcion);
        tallerList = servicioAplicacion.listaTallerRegistrado(usuarioSession.getUsuarioSession().getId(), taller_nombre, taller_descripcion);
    } 
    
    public void cerrarTaller(PresentaTallerList tallerList){
        
        System.out.println("Codigo de Taller a Cerrar: "+tallerList.getTaller_id());
        boolean cerrado = false;
        cerrado = servicioAplicacion.cierraTaller(tallerList.getTaller_id());
        
        if(cerrado){
         
            FacesContext context = FacesContext.getCurrentInstance();          
            context.addMessage(null, new FacesMessage("Taller ha sido cerrado con éxito.", "Taller ha sido cerrado con éxito."));
            
           buscaTallerRegistrado();
        }
    }
    
    public void ingresaTaller(){
        
        FacesContext context = FacesContext.getCurrentInstance();
        System.out.println("**************ingresaTaller <tallerMB>*****************");
        String Estado = "A";
        if( 
            (taller_nombre           == null) || (taller_nombre.equals(""))             || 
            
            (unidadList.size()       == 0 )   ||
            (destrezaList.size()       == 0 )                                  
          )
          {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Por favor, llene información obligatoria (*) del formulario para continuar.", " " );
                FacesContext.getCurrentInstance().addMessage(null, message);
                System.out.println("No ingreso informacion obligatoria del Formulario: ");
          }
          else
          {
              guardaTaller( taller_nombre, taller_descripcion, Estado);
          }
    }
    
    public void guardaTaller( String taller_nombre1, String taller_descripcion1, String Estado1){
        
        cantidad = servicioAplicacion.cantidadTallerAbierto(usuarioSession.getUsuarioSession().getId(), unidad_id);
        System.out.println(" Cantidad de Talleres Abiertos: "+cantidad);
        
        if (cantidad == BigInteger.ZERO){
            fechaActual             =   new Date();
            IdUserSession           =   getUserSessionId();
            TblUnidad unidad        =   new TblUnidad();
            unidad.setUnidadId(unidad_id);
            
            TblDestreza destreza    =   new TblDestreza();
            destreza.setDestrezaId(destreza_id);
            
            TblPresentaciontaller taller = new TblPresentaciontaller();
            taller.setTallerNombre(taller_nombre1);
            taller.setTallerDescripcion(taller_descripcion1);
            taller.setTallerSts(Estado1);
            taller.setTallerInsusr(IdUserSession);
            taller.setTallerInstim(fechaActual);
            taller.setUnidadId(unidad);
            taller.setDestrezaId(destreza);
            
            TblDestrezaunidad  destrezaUnidad = new TblDestrezaunidad();
            destrezaUnidad.setDestrezaId(destreza);
            destrezaUnidad.setUnidad_Id(unidad);
            destrezaUnidad.setDestrezaunidadSts(Estado1);
           
            servicioAplicacion.ingresaDestrezaUnidad(destrezaUnidad);
            
            Long taller_cod = servicioAplicacion.ingresaTaller(taller);
            System.out.println("guardaTaller taller_cod: "+taller_cod);

            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Taller se ha registrado con éxito.", " " );
            FacesContext.getCurrentInstance().addMessage(null, message);
            
            tallerList        =     servicioAplicacion.listaTallerRegistrado(usuarioSession.getUsuarioSession().getId(), "", "");
        }
        else
        {
           FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ya existe un taller abierto para el Curso seleccionado.", " " );
           FacesContext.getCurrentInstance().addMessage(null, message); 
        }    
    }
    
    public void reestableceTaller()
    {
        taller_nombre       = "";
        taller_descripcion  = "";
    }       
    
    public String getUserSessionId() 
    {
        System.out.println("Usuario de Sesion: " + usuarioSession.getUsuarioSession().getUsuario());
        return usuarioSession.getUsuarioSession().getUsuario();
    }
    
    public String getTaller_nombre() {
        return taller_nombre;
    }

    public void setTaller_nombre(String taller_nombre) {
        this.taller_nombre = taller_nombre;
    }

    public String getTaller_descripcion() {
        return taller_descripcion;
    }

    public void setTaller_descripcion(String taller_descripcion) {
        this.taller_descripcion = taller_descripcion;
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

    public List<PresentaTallerList> getTallerList() {
        return tallerList;
    }

    public void setTallerList(List<PresentaTallerList> tallerList) {
        this.tallerList = tallerList;
    }

    public Long getDestreza_id() {
        return destreza_id;
    }

    public void setDestreza_id(Long destreza_id) {
        this.destreza_id = destreza_id;
    }

    public List<TblDestreza> getDestrezaList() {
        return destrezaList;
    }

    public void setDestrezaList(List<TblDestreza> destrezaList) {
        this.destrezaList = destrezaList;
    }
            
    
 
}
