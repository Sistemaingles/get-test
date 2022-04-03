
package com.servicios.mb;

import com.conexion.ConexionBD;
import com.entidades.TblDestreza;
import com.entidades.TblPeriodo;
import com.entidades.TblUsuarios;
import com.entidades.listas.ResultadoTallerList;
import com.servicios.impl.ITestUsuarioSession;
import com.servicios.impl.IUsuarioSession;
import com.servicios.impl.IservicioAplicacion;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;

/**
 *
 * @author jespinoza
 */

@ManagedBean(name = "reporteActividadMB")
@SessionScoped
public class reporteActividadesMB implements Serializable{
    
    private static final long   serialVersionUID = 1L;
    private List<TblDestreza>            destrezaList;
    private List<ResultadoTallerList>    resultadoList;
    private Long destreza_id;
    private String                url = "";
    private List<TblPeriodo> periodoList;
    private String periodoAnio;
    
    @EJB
    private IservicioAplicacion  servicioAplicacion;
    @Inject
    @ITestUsuarioSession
    private IUsuarioSession<TblUsuarios> usuarioSession;
    private String IdUserSession;
    
    @PostConstruct
    public void init()
    { 
        //Obtinene Lista de Destrezas Activas  .-
        destrezaList  = servicioAplicacion.obtieneDestreza();
        
        //Obtiene Periodo Activo.-
        periodoList   =   servicioAplicacion.obtenerPeriodo("A");
        periodoAnio   =   "";

        for (int i = 0; i < periodoList.size(); i++) {
            periodoAnio = periodoList.get(i).getPeriodoAnio();
            System.out.println("Periodo Activo: "+periodoAnio);

        }
        
    }
    
    public void buscaResultadoActividades()
    {
        System.out.println("reporteActividadesMB buscaResultadoActividades: UsuarioCod: "+usuarioSession.getUsuarioSession().getId()+
                           " DestrezaId: "+destreza_id);
        
        resultadoList = servicioAplicacion.consultaResultadoxDestreza(usuarioSession.getUsuarioSession().getId(), destreza_id);
    } 
    
    public void generarReporte() throws JRException, IOException
    {   
        resultadoList = servicioAplicacion.consultaResultadoxDestreza(usuarioSession.getUsuarioSession().getId(), destreza_id);
        
        url = generaPDF("rpt_ActividadesEstudiantes.jasper", destreza_id);
        
    }
    
    /*
       METODO GENERA REPORTE EN PDF Y RETORNA RUTA DE GENERACION.-
    */
    public String generaPDF(String jasper, Long id) throws JRException 
    {
        String  path = "";
        ConexionBD con      =   new ConexionBD();
  
        path                =   servicioAplicacion.obtenerParametro("RUTA_TEMP");   
        path                =   path+ jasper;
        System.out.println("RUTA JASPER: "+path);
        
        File file = new File(path);
        
        if (file.exists())
        {
            System.out.println("Encontro Jasper Report Generado");  
            String ruta_tmpo     =   servicioAplicacion.obtenerParametro("RUTA_REPORT");
            String ruta_tmp      =   ruta_tmpo + "Rpt.pdf";
            
            String ruta          =   servicioAplicacion.obtenerParametro("RUTA_RESOURCES");
            String ruta_sello    =   ruta + "logo.PNG" ;  

            File pdf            =   new File(ruta_tmp);

            JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
            JasperPrint jasperPrint;

            System.out.println("Ruta_sello: "+ruta_sello);
            System.out.println("Destreza_Id: "+id);
            System.out.println("UsuarioCod: "+usuarioSession.getUsuarioSession().getId());
          
 
            Map parametros = new HashMap();
            parametros.put("Periodo",periodoAnio);
            parametros.put("ruta_sello",ruta_sello);
            parametros.put("DestrezaId", id);
            parametros.put("UsuarioCod", usuarioSession.getUsuarioSession().getId());
            
            jasperPrint = JasperFillManager.fillReport(reporte, parametros, con.getConnection());
            
            JRExporter exporter = new JRPdfExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            exporter.setParameter(JRExporterParameter.OUTPUT_FILE, new java.io.File(ruta_tmp));
            exporter.exportReport();
            
            url = pdf.getAbsolutePath();
            
            return pdf.getAbsolutePath();
        }
        else
        {
            System.out.println("UtilJsf oucrrió un error al generarPDF");    
            return null;
        }
        
    }
    
    public Long getDestreza_id() {
        return destreza_id;
    }

    public void setDestreza_id(Long destreza_id) {
        this.destreza_id = destreza_id;
    }
    
    public String getUserSessionId() 
    {
        System.out.println("Usuario de Sesion: " + usuarioSession.getUsuarioSession().getUsuario());
        return usuarioSession.getUsuarioSession().getUsuario();
    }

    public List<TblDestreza> getDestrezaList() {
        return destrezaList;
    }

    public void setDestrezaList(List<TblDestreza> destrezaList) {
        this.destrezaList = destrezaList;
    }

    public List<ResultadoTallerList> getResultadoList() {
        return resultadoList;
    }

    public void setResultadoList(List<ResultadoTallerList> resultadoList) {
        this.resultadoList = resultadoList;
    }

    public IUsuarioSession<TblUsuarios> getUsuarioSession() {
        return usuarioSession;
    }

    public void setUsuarioSession(IUsuarioSession<TblUsuarios> usuarioSession) {
        this.usuarioSession = usuarioSession;
    }

    public String getIdUserSession() {
        return IdUserSession;
    }

    public void setIdUserSession(String IdUserSession) {
        this.IdUserSession = IdUserSession;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    
}
