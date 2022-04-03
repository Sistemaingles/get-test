
package com.entidades.listas;

import java.io.Serializable;

/**
 *
 * @author mespinoza
 */
public class ActividadList implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String periodo;
    private Long cursoparaleloid;
    private Long actividad_id;
    private String actividad;
    private String pregunta;
    private int correcta;
    private String archivo;
    private String curso_nombre;
    private String paralelo_nombre;
    private String estado;

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public Long getCursoparaleloid() {
        return cursoparaleloid;
    }

    public void setCursoparaleloid(Long cursoparaleloid) {
        this.cursoparaleloid = cursoparaleloid;
    }

    public Long getActividad_id() {
        return actividad_id;
    }

    public void setActividad_id(Long actividad_id) {
        this.actividad_id = actividad_id;
    }

    public String getActividad() {
        return actividad;
    }

    public void setActividad(String actividad) {
        this.actividad = actividad;
    }

    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    public int getCorrecta() {
        return correcta;
    }

    public void setCorrecta(int correcta) {
        this.correcta = correcta;
    }

    public String getArchivo() {
        return archivo;
    }

    public void setArchivo(String archivo) {
        this.archivo = archivo;
    }

    
    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCurso_nombre() {
        return curso_nombre;
    }

    public void setCurso_nombre(String curso_nombre) {
        this.curso_nombre = curso_nombre;
    }

    public String getParalelo_nombre() {
        return paralelo_nombre;
    }

    public void setParalelo_nombre(String paralelo_nombre) {
        this.paralelo_nombre = paralelo_nombre;
    }
   
    
   
    
    
    
}
