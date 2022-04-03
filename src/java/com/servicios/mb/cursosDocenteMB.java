
package com.servicios.mb;

import com.entidades.listas.CursoAsignadoList;
import com.servicios.impl.IservicioAplicacion;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;
import org.primefaces.context.RequestContext;

/**
 *
 * @author mespinoza
 */
@ManagedBean(name = "cursoDocenteMB")
@RequestScoped

/*
    CLASE JAVA MANAGED BEANS DE PERIODO LECTIVO
*/

public class cursosDocenteMB implements Serializable  {
    
    private static final long serialVersionUID = 1L;
    private List<CursoAsignadoList> detperiodoList;
    
    private List<CursoAsignadoList> selectedCurso;
    @Inject
    private CursoAsignadoList PeriodoList;
    
    @EJB
    private IservicioAplicacion  servicioAplicacion;
    
    public List<CursoAsignadoList> getDetperiodoList() {
        detperiodoList     =  servicioAplicacion.consultaPeriodoCursoDocente(Long.valueOf("0"), Long.valueOf("0"), Long.valueOf("0"), "");
        return detperiodoList;
    }

    public void setDetperiodoList(List<CursoAsignadoList> detperiodoList) {
        this.detperiodoList = detperiodoList;
    }

    public List<CursoAsignadoList> getSelectedCurso() {
        return selectedCurso;
    }

    public void setSelectedCurso(List<CursoAsignadoList> selectedCurso) {
        this.selectedCurso = selectedCurso;
    }

    public void selecionar(CursoAsignadoList periodoList) 
        {
            System.out.println("Curso-Periodo Seleccionado: "+periodoList.getCursoparalelo_cod());
            RequestContext.getCurrentInstance().closeDialog(periodoList);
       
	}
    
}
