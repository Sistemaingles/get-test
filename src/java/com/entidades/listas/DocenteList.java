
package com.entidades.listas;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

/**
 *
 * @author mespinoza
 */
public class DocenteList implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Long docentecursoid;
    private Long cursoparaleloid;
    private Long docente_id;
    private String identificacion;
    private String nombres;
    private String apellidos;
    private Date fecha_nacimiento;
    private String genero;
    private String correo;
    private String curso_nombre;
    private String paralelo_nombre;
    private String estado;
    private String periodo;
    private BigInteger cant_estudiante;
    
    public Long getDocentecursoid() {
        return docentecursoid;
    }

    public void setDocentecursoid(Long docentecursoid) {
        this.docentecursoid = docentecursoid;
    }

    

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

   

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Long getCursoparaleloid() {
        return cursoparaleloid;
    }

    public void setCursoparaleloid(Long cursoparaleloid) {
        this.cursoparaleloid = cursoparaleloid;
    }

    public Date getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public void setFecha_nacimiento(Date fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }

    
    public String getGenero() {
        return genero;
    }

    public Long getDocente_id() {
        return docente_id;
    }

    public void setDocente_id(Long docente_id) {
        this.docente_id = docente_id;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public BigInteger getCant_estudiante() {
        return cant_estudiante;
    }

    public void setCant_estudiante(BigInteger cant_estudiante) {
        this.cant_estudiante = cant_estudiante;
    }
    
    
    
    
    
}
