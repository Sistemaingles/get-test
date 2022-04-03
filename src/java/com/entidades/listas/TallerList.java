
package com.entidades.listas;


import java.io.Serializable;
import java.util.List;

/**
 *
 * @author mespinoza
 */
public class TallerList implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Long taller_id;
    private String periodo;
    private String destreza;
    private Long unidad_id;
    private String unidad;
    private String subtema;
    private Long cursoparaleloid;
    private Long actividad_id;
    private String actividad;
    private String pregunta;
    private String tipo_pregunta;
    private int correcta;
    private Long idRespuestSelec;
    private String archivo;
    private String curso_nombre;
    private String paralelo_nombre;
    private String estado;
    private List<RespuestasList>    respuestas;

    public Long getTaller_id() {
        return taller_id;
    }

    public void setTaller_id(Long taller_id) {
        this.taller_id = taller_id;
    }
    
    
    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public String getDestreza() {
        return destreza;
    }

    public void setDestreza(String destreza) {
        this.destreza = destreza;
    }

    public Long getUnidad_id() {
        return unidad_id;
    }

    public void setUnidad_id(Long unidad_id) {
        this.unidad_id = unidad_id;
    }
    
    
    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public String getSubtema() {
        return subtema;
    }

    public void setSubtema(String subtema) {
        this.subtema = subtema;
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

    public String getTipo_pregunta() {
        return tipo_pregunta;
    }

    public void setTipo_pregunta(String tipo_pregunta) {
        this.tipo_pregunta = tipo_pregunta;
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

    public List<RespuestasList> getRespuestas() {
        return respuestas;
    }

    public void setRespuestas(List<RespuestasList> respuestas) {
        this.respuestas = respuestas;
    }

    public Long getIdRespuestSelec() {
        return idRespuestSelec;
    }

    public void setIdRespuestSelec(Long idRespuestSelec) {
        this.idRespuestSelec = idRespuestSelec;
    }

    
   
    
    
}
