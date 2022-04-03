
package com.servicios.mb;

import com.conexion.ConexionBD;
import com.entidades.TblCurso;
import com.entidades.TblDocente;
import com.entidades.TblParalelo;
import com.entidades.TblPeriodo;
import com.entidades.TblUsuarios;
import com.entidades.listas.DocenteList;
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
import javax.enterprise.context.RequestScoped;
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

@ManagedBean(name = "reporteEstudianteDocenteMB")
@RequestScoped
public class reporteEstudianteDocenteMB implements Serializable{
    
    private static final long   serialVersionUID = 1L;
    private List<TblPeriodo> periodoList;
    private Long periodo_cod;
    private List<TblCurso> cursoList;
    private Long curso_cod;
    private Long docente_cod;
    private List<TblDocente> docenteList;
    private String                url = "";
    private List<DocenteList> estudiantexDocenteList;
    private String periodoAnio;
    private List<TblParalelo> paraleloList;
    private Long paralelo_cod;
    
    @EJB
    private IservicioAplicacion  servicioAplicacion;
    @Inject
    @ITestUsuarioSession
    private IUsuarioSession<TblUsuarios> usuarioSession;
    private String IdUserSession;
    
    @PostConstruct
    public void init()
    { 
        //Obtiene Lista de Periodo Lectivo (AÑO) .-
        periodoList          =   servicioAplicacion.obtenerPeriodo("");
        
        for (int i = 0; i < periodoList.size(); i++) {
  
            if (periodoList.get(i).getPeriodoSts().equals("ACTIVO")){
                periodoAnio = periodoList.get(i).getPeriodoAnio();
                System.out.println("Periodo Activo: "+periodoAnio);
            }
            
        }
        //Obtiene Lista de Cursos.-
        cursoList          =   servicioAplicacion.obtieneCursos("");
        
        //Obtiene Lista de Docentes.-
        docenteList        =   servicioAplicacion.obtenerDocente();
        
        //Obtiene Lista de Paralelos.-
        paraleloList       =   servicioAplicacion.obtieneParalelos("");
        
        //Obtiene Lista de Estudiantes por Docentes.-
        estudiantexDocenteList = servicioAplicacion.consultaEstudiantesxDocente(Long.valueOf("0"), Long.valueOf("0"), Long.valueOf("0"), Long.valueOf("0"));
                
    }
    
    public void buscaEstudiantesxDocente()
    {
        //Obtiene Lista de Estudiantes por Docentes.-
        estudiantexDocenteList = servicioAplicacion.consultaEstudiantesxDocente(periodo_cod, curso_cod, docente_cod, paralelo_cod);
    } 
    
    public void generarReporte() throws JRException, IOException
    {   
        //Obtiene Lista de Estudiantes por Docentes.-
        estudiantexDocenteList = servicioAplicacion.consultaEstudiantesxDocente(periodo_cod, curso_cod, docente_cod, paralelo_cod);
        
        url = generaPDF("rpt_EstudiantexDocente.jasper",periodo_cod, curso_cod, docente_cod, paralelo_cod);
        
    }
    
    /*
       METODO GENERA REPORTE EN PDF Y RETORNA RUTA DE GENERACION.-
    */
    public String generaPDF(String jasper, Long id1 , Long id2, Long id3, Long id4) throws JRException 
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
            System.out.println("Usuario: "+getUserSessionId());
            System.out.println("PeriodoId: "+id1);
            System.out.println("CursoId: "+id2);
            System.out.println("DocenteId: "+id3);
            System.out.println("ParaleloId: "+id4);
            
            Map parametros = new HashMap();
            parametros.put("Periodo",periodoAnio);
            parametros.put("ruta_sello",ruta_sello);
            parametros.put("Usuario", getUserSessionId());
            parametros.put("PeriodoId", id1);
            parametros.put("CursoId", id2);
            parametros.put("DocenteId", id3);
            parametros.put("ParaleloId", id4);
            
            
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
            System.out.println("reporteEstudianteDocenteMB ocurrió un error al generarPDF: No encontro archivo" );    
            return null;
        }
        
    }
    

    
    public String getUserSessionId() 
    {
        System.out.println("Usuario de Sesion: " + usuarioSession.getUsuarioSession().getUsuario());
        return usuarioSession.getUsuarioSession().getUsuario();
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

    public List<TblPeriodo> getPeriodoList() {
        return periodoList;
    }

    public void setPeriodoList(List<TblPeriodo> periodoList) {
        this.periodoList = periodoList;
    }

    public Long getPeriodo_cod() {
        return periodo_cod;
    }

    public void setPeriodo_cod(Long periodo_cod) {
        this.periodo_cod = periodo_cod;
    }

    public List<TblCurso> getCursoList() {
        return cursoList;
    }

    public void setCursoList(List<TblCurso> cursoList) {
        this.cursoList = cursoList;
    }

    public Long getCurso_cod() {
        return curso_cod;
    }

    public void setCurso_cod(Long curso_cod) {
        this.curso_cod = curso_cod;
    }

    public Long getDocente_cod() {
        return docente_cod;
    }

    public void setDocente_cod(Long docente_cod) {
        this.docente_cod = docente_cod;
    }

    public List<TblDocente> getDocenteList() {
        return docenteList;
    }

    public void setDocenteList(List<TblDocente> docenteList) {
        this.docenteList = docenteList;
    }

    public List<DocenteList> getEstudiantexDocenteList() {
        return estudiantexDocenteList;
    }

    public void setEstudiantexDocenteList(List<DocenteList> estudiantexDocenteList) {
        this.estudiantexDocenteList = estudiantexDocenteList;
    }

    public List<TblParalelo> getParaleloList() {
        return paraleloList;
    }

    public void setParaleloList(List<TblParalelo> paraleloList) {
        this.paraleloList = paraleloList;
    }

    public Long getParalelo_cod() {
        return paralelo_cod;
    }

    public void setParalelo_cod(Long paralelo_cod) {
        this.paralelo_cod = paralelo_cod;
    }
    
    
    
}
