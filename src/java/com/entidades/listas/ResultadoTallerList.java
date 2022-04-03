
package com.entidades.listas;

import com.entidades.TblSubtema;
import com.entidades.TblUnidad;
import com.entidades.TblUsuarios;
import java.io.Serializable;

/**
 *
 * @author jespinoza
 */


public class ResultadoTallerList implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Long resultadoId;
    private TblUnidad unidadId;
    private Long subtemaId;
    private String subtemaDesc;
    private String unidad_nombre;
    private String unidad_descripcion;
    private TblUsuarios usuarioCod;
    private float resultadototRespuesta;
    private float resultadototAciertos;
    private float resultadoPorcentaje;
    private int resultadoIntento;
    private String resultadoEstado;
    private String resultadoSts;
    private String estudiante;
    private Long cursoId;
    private String curso;
    private String destreza;
    private String resultados;
    private String aciertos;
    private String puntaje;
    private String promedio;
    private String pruebas;
    
    public Long getResultadoId() {
        return resultadoId;
    }

    public void setResultadoId(Long resultadoId) {
        this.resultadoId = resultadoId;
    }

    public TblUnidad getUnidadId() {
        return unidadId;
    }

    public void setUnidadId(TblUnidad unidadId) {
        this.unidadId = unidadId;
    }

    public String getUnidad_nombre() {
        return unidad_nombre;
    }

    public Long getSubtemaId() {
        return subtemaId;
    }

    public void setSubtemaId(Long subtemaId) {
        this.subtemaId = subtemaId;
    }

    public String getSubtemaDesc() {
        return subtemaDesc;
    }

    public void setSubtemaDesc(String subtemaDesc) {
        this.subtemaDesc = subtemaDesc;
    }

 
    public void setUnidad_nombre(String unidad_nombre) {
        this.unidad_nombre = unidad_nombre;
    }

    public TblUsuarios getUsuarioCod() {
        return usuarioCod;
    }

    public void setUsuarioCod(TblUsuarios usuarioCod) {
        this.usuarioCod = usuarioCod;
    }

    public float getResultadototRespuesta() {
        return resultadototRespuesta;
    }

    public void setResultadototRespuesta(float resultadototRespuesta) {
        this.resultadototRespuesta = resultadototRespuesta;
    }

    public float getResultadototAciertos() {
        return resultadototAciertos;
    }

    public void setResultadototAciertos(float resultadototAciertos) {
        this.resultadototAciertos = resultadototAciertos;
    }

    public float getResultadoPorcentaje() {
        return resultadoPorcentaje;
    }

    public void setResultadoPorcentaje(float resultadoPorcentaje) {
        this.resultadoPorcentaje = resultadoPorcentaje;
    }

    public int getResultadoIntento() {
        return resultadoIntento;
    }

    public void setResultadoIntento(int resultadoIntento) {
        this.resultadoIntento = resultadoIntento;
    }

    public String getResultadoEstado() {
        return resultadoEstado;
    }

    public void setResultadoEstado(String resultadoEstado) {
        this.resultadoEstado = resultadoEstado;
    }

    public String getResultadoSts() {
        return resultadoSts;
    }

    public void setResultadoSts(String resultadoSts) {
        this.resultadoSts = resultadoSts;
    }

    public String getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(String estudiante) {
        this.estudiante = estudiante;
    }

    public Long getCursoId() {
        return cursoId;
    }

    public void setCursoId(Long cursoId) {
        this.cursoId = cursoId;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public String getDestreza() {
        return destreza;
    }

    public void setDestreza(String destreza) {
        this.destreza = destreza;
    }

    public String getPromedio() {
        return promedio;
    }

    public void setPromedio(String promedio) {
        this.promedio = promedio;
    }

    public String getResultados() {
        return resultados;
    }

    public void setResultados(String resultados) {
        this.resultados = resultados;
    }

    public String getAciertos() {
        return aciertos;
    }

    public void setAciertos(String aciertos) {
        this.aciertos = aciertos;
    }

    public String getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(String puntaje) {
        this.puntaje = puntaje;
    }

    public String getPruebas() {
        return pruebas;
    }

    public void setPruebas(String pruebas) {
        this.pruebas = pruebas;
    }

    public String getUnidad_descripcion() {
        return unidad_descripcion;
    }

    public void setUnidad_descripcion(String unidad_descripcion) {
        this.unidad_descripcion = unidad_descripcion;
    }
    
   
}
