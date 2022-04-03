
package com.entidades.listas;

import java.io.Serializable;

/**
 *
 * @author jespinoza
 */
public class CursoAsignadoList implements Serializable{
    private static final long serialVersionUID = 1L;
    
    private Long     cursoparalelo_cod;
    private Long     periodo_cod;
    private String   periodo_descripcion;
    private String   seccion_nombre;
    private Long     curso_cod;
    private String   curso_descripcion;
    private Long     paralelo_cod;
    private String   paralelo_nombre;
    private String   estado;
    
    private Long     cursoasignado_cod;
    private Long     persona_cod;
    private String   persona_apeNom;
    private Long     usuario_cod;
    
    public Long getCursoparalelo_cod() {
        return cursoparalelo_cod;
    }

    public void setCursoparalelo_cod(Long cursoparalelo_cod) {
        this.cursoparalelo_cod = cursoparalelo_cod;
    }
    
    public Long getPeriodo_cod() {
        return periodo_cod;
    }

    public void setPeriodo_cod(Long periodo_cod) {
        this.periodo_cod = periodo_cod;
    }

    public String getPeriodo_descripcion() {
        return periodo_descripcion;
    }

    public void setPeriodo_descripcion(String periodo_descripcion) {
        this.periodo_descripcion = periodo_descripcion;
    }

    public String getSeccion_nombre() {
        return seccion_nombre;
    }

    public void setSeccion_nombre(String seccion_nombre) {
        this.seccion_nombre = seccion_nombre;
    }

    public Long getCurso_cod() {
        return curso_cod;
    }

    public void setCurso_cod(Long curso_cod) {
        this.curso_cod = curso_cod;
    }

    public String getCurso_descripcion() {
        return curso_descripcion;
    }

    public void setCurso_descripcion(String curso_descripcion) {
        this.curso_descripcion = curso_descripcion;
    }

    public Long getParalelo_cod() {
        return paralelo_cod;
    }

    public void setParalelo_cod(Long paralelo_cod) {
        this.paralelo_cod = paralelo_cod;
    }

    public String getParalelo_nombre() {
        return paralelo_nombre;
    }

    public void setParalelo_nombre(String paralelo_nombre) {
        this.paralelo_nombre = paralelo_nombre;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Long getPersona_cod() {
        return persona_cod;
    }

    public void setPersona_cod(Long persona_cod) {
        this.persona_cod = persona_cod;
    }

    public String getPersona_apeNom() {
        return persona_apeNom;
    }

    public void setPersona_apeNom(String persona_apeNom) {
        this.persona_apeNom = persona_apeNom;
    }

    public Long getCursoasignado_cod() {
        return cursoasignado_cod;
    }

    public void setCursoasignado_cod(Long cursoasignado_cod) {
        this.cursoasignado_cod = cursoasignado_cod;
    }

    public Long getUsuario_cod() {
        return usuario_cod;
    }

    public void setUsuario_cod(Long usuario_cod) {
        this.usuario_cod = usuario_cod;
    }
    
    
    
}
