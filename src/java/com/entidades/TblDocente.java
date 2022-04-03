
package com.entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author mespinoza
 */
@Entity
@Table(name = "tbl_docente")
@NamedQueries({
    @NamedQuery(name = "TblDocente.findAll", query = "SELECT t FROM TblDocente t"),
    @NamedQuery(name = "TblDocente.findByDocenteId", query = "SELECT t FROM TblDocente t WHERE t.docenteId = :docenteId"),
    @NamedQuery(name = "TblDocente.findByDocenteNombres", query = "SELECT t FROM TblDocente t WHERE t.docenteNombres = :docenteNombres"),
    @NamedQuery(name = "TblDocente.findByDocenteApellidos", query = "SELECT t FROM TblDocente t WHERE t.docenteApellidos = :docenteApellidos"),
    @NamedQuery(name = "TblDocente.findByDocenteGenero", query = "SELECT t FROM TblDocente t WHERE t.docenteGenero = :docenteGenero"),
    @NamedQuery(name = "TblDocente.findByDocenteIdent", query = "SELECT t FROM TblDocente t WHERE t.docenteIdent = :docenteIdent"),
    @NamedQuery(name = "TblDocente.findByDocenteTelefono", query = "SELECT t FROM TblDocente t WHERE t.docenteTelefono = :docenteTelefono"),
    @NamedQuery(name = "TblDocente.findByDocenteCorreo", query = "SELECT t FROM TblDocente t WHERE t.docenteCorreo = :docenteCorreo"),
    @NamedQuery(name = "TblDocente.findByDocenteInsusr", query = "SELECT t FROM TblDocente t WHERE t.docenteInsusr = :docenteInsusr"),
    @NamedQuery(name = "TblDocente.findByDocenteInstim", query = "SELECT t FROM TblDocente t WHERE t.docenteInstim = :docenteInstim"),
    @NamedQuery(name = "TblDocente.findByDocenteUpdusr", query = "SELECT t FROM TblDocente t WHERE t.docenteUpdusr = :docenteUpdusr"),
    @NamedQuery(name = "TblDocente.findByDocenteUpdtim", query = "SELECT t FROM TblDocente t WHERE t.docenteUpdtim = :docenteUpdtim"),
    @NamedQuery(name = "TblDocente.findByDocenteDltusr", query = "SELECT t FROM TblDocente t WHERE t.docenteDltusr = :docenteDltusr"),
    @NamedQuery(name = "TblDocente.findByDocenteDlttim", query = "SELECT t FROM TblDocente t WHERE t.docenteDlttim = :docenteDlttim"),
    @NamedQuery(name = "TblDocente.findByDocenteSts", query = "SELECT t FROM TblDocente t WHERE t.docenteSts = :docenteSts")})


public class TblDocente implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "docente_id")
    private Long docenteId;
    
    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name ="Usuario_Cod",nullable = false)
    private TblUsuarios usuario_cod;
     
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "docente_nombres",nullable = false)
    private String docenteNombres;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "docente_apellidos",nullable = false)
    private String docenteApellidos;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "docente_genero",nullable = false)
    private String docenteGenero;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "docente_ident",nullable = false)
    private String docenteIdent;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "docente_telefono",nullable = false)
    private String docenteTelefono;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "docente_correo",nullable = false)
    private String docenteCorreo;
    @Size(max = 20)
    @Column(name = "docente_insusr",nullable = false)
    private String docenteInsusr;
    @Column(name = "docente_instim",nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date docenteInstim;
    @Size(max = 20)
    @Column(name = "docente_updusr",nullable = false)
    private String docenteUpdusr;
    @Column(name = "docente_updtim",nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date docenteUpdtim;
    @Size(max = 20)
    @Column(name = "docente_dltusr",nullable = false)
    private String docenteDltusr;
    @Column(name = "docente_dlttim",nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date docenteDlttim;
    @Size(max = 1)
    @Column(name = "docente_sts",nullable = false)
    private String docenteSts;

    public TblDocente() {
    }

    public TblDocente(Long docenteId) {
        this.docenteId = docenteId;
    }

    public TblDocente(Long docenteId, String docenteNombres, String docenteApellidos, String docenteGenero, String docenteIdent, String docenteTelefono, String docenteCorreo) {
        this.docenteId = docenteId;
        this.docenteNombres = docenteNombres;
        this.docenteApellidos = docenteApellidos;
        this.docenteGenero = docenteGenero;
        this.docenteIdent = docenteIdent;
        this.docenteTelefono = docenteTelefono;
        this.docenteCorreo = docenteCorreo;
    }

    public Long getDocenteId() {
        return docenteId;
    }

    public void setDocenteId(Long docenteId) {
        this.docenteId = docenteId;
    }
    
    public TblUsuarios getUsuario_cod() 
    {
        return usuario_cod;
    }

    public void setUsuario_cod(TblUsuarios usuario_cod) 
    {
        this.usuario_cod = usuario_cod;
    }
    
    public String getDocenteNombres() {
        return docenteNombres;
    }

    public void setDocenteNombres(String docenteNombres) {
        this.docenteNombres = docenteNombres;
    }

    public String getDocenteApellidos() {
        return docenteApellidos;
    }

    public void setDocenteApellidos(String docenteApellidos) {
        this.docenteApellidos = docenteApellidos;
    }



    public String getDocenteGenero() {
        return docenteGenero;
    }

    public void setDocenteGenero(String docenteGenero) {
        this.docenteGenero = docenteGenero;
    }

    public String getDocenteIdent() {
        return docenteIdent;
    }

    public void setDocenteIdent(String docenteIdent) {
        this.docenteIdent = docenteIdent;
    }

    public String getDocenteTelefono() {
        return docenteTelefono;
    }

    public void setDocenteTelefono(String docenteTelefono) {
        this.docenteTelefono = docenteTelefono;
    }

    public String getDocenteCorreo() {
        return docenteCorreo;
    }

    public void setDocenteCorreo(String docenteCorreo) {
        this.docenteCorreo = docenteCorreo;
    }

    public String getDocenteInsusr() {
        return docenteInsusr;
    }

    public void setDocenteInsusr(String docenteInsusr) {
        this.docenteInsusr = docenteInsusr;
    }

    public Date getDocenteInstim() {
        return docenteInstim;
    }

    public void setDocenteInstim(Date docenteInstim) {
        this.docenteInstim = docenteInstim;
    }

    public String getDocenteUpdusr() {
        return docenteUpdusr;
    }

    public void setDocenteUpdusr(String docenteUpdusr) {
        this.docenteUpdusr = docenteUpdusr;
    }

    public Date getDocenteUpdtim() {
        return docenteUpdtim;
    }

    public void setDocenteUpdtim(Date docenteUpdtim) {
        this.docenteUpdtim = docenteUpdtim;
    }

    public String getDocenteDltusr() {
        return docenteDltusr;
    }

    public void setDocenteDltusr(String docenteDltusr) {
        this.docenteDltusr = docenteDltusr;
    }

    public Date getDocenteDlttim() {
        return docenteDlttim;
    }

    public void setDocenteDlttim(Date docenteDlttim) {
        this.docenteDlttim = docenteDlttim;
    }

    public String getDocenteSts() {
        return docenteSts;
    }

    public void setDocenteSts(String docenteSts) {
        this.docenteSts = docenteSts;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (docenteId != null ? docenteId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblDocente)) {
            return false;
        }
        TblDocente other = (TblDocente) object;
        if ((this.docenteId == null && other.docenteId != null) || (this.docenteId != null && !this.docenteId.equals(other.docenteId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entidades.TblDocente[ docenteId=" + docenteId + " ]";
    }
    
}
