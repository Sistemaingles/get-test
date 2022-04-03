
package com.entidades;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
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
@Table(name = "tbl_periodo")
@NamedQueries({
    @NamedQuery(name = "TblPeriodo.findAll", query = "SELECT t FROM TblPeriodo t"),
    @NamedQuery(name = "TblPeriodo.findByPeriodoId", query = "SELECT t FROM TblPeriodo t WHERE t.periodoId = :periodoId"),
    @NamedQuery(name = "TblPeriodo.findByPeriodoAnio", query = "SELECT t FROM TblPeriodo t WHERE t.periodoAnio = :periodoAnio"),
    @NamedQuery(name = "TblPeriodo.findByPeriodoDescripcion", query = "SELECT t FROM TblPeriodo t WHERE t.periodoDescripcion = :periodoDescripcion"),
    @NamedQuery(name = "TblPeriodo.findByPeriodoInstim", query = "SELECT t FROM TblPeriodo t WHERE t.periodoInstim = :periodoInstim"),
    @NamedQuery(name = "TblPeriodo.findByPeriodoInsusr", query = "SELECT t FROM TblPeriodo t WHERE t.periodoInsusr = :periodoInsusr"),
    @NamedQuery(name = "TblPeriodo.findByPeriodoSts", query = "SELECT t FROM TblPeriodo t WHERE t.periodoSts = :periodoSts")})
public class TblPeriodo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    //@NotNull
    @Column(name = "periodo_Id")
    private Long periodoId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "periodo_anio")
    private String periodoAnio;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "periodo_descripcion")
    private String periodoDescripcion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "periodo_instim")
    @Temporal(TemporalType.TIMESTAMP)
    private Date periodoInstim;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "periodo_insusr")
    private String periodoInsusr;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "periodo_sts")
    private String periodoSts;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "periodoId")
    private Collection<TblCursoparalelo> tblCursoparaleloCollection;

    public TblPeriodo() {
    }

    public TblPeriodo(Long periodoId) {
        this.periodoId = periodoId;
    }

    public TblPeriodo(Long periodoId, String periodoAnio, String periodoDescripcion, Date periodoInstim, String periodoInsusr, String periodoSts) {
        this.periodoId = periodoId;
        this.periodoAnio = periodoAnio;
        this.periodoDescripcion = periodoDescripcion;
        this.periodoInstim = periodoInstim;
        this.periodoInsusr = periodoInsusr;
        this.periodoSts = periodoSts;
    }

    public Long getPeriodoId() {
        return periodoId;
    }

    public void setPeriodoId(Long periodoId) {
        this.periodoId = periodoId;
    }

    public String getPeriodoAnio() {
        return periodoAnio;
    }

    public void setPeriodoAnio(String periodoAnio) {
        this.periodoAnio = periodoAnio;
    }

    
    public String getPeriodoDescripcion() {
        return periodoDescripcion;
    }

    public void setPeriodoDescripcion(String periodoDescripcion) {
        this.periodoDescripcion = periodoDescripcion;
    }

    public Date getPeriodoInstim() {
        return periodoInstim;
    }

    public void setPeriodoInstim(Date periodoInstim) {
        this.periodoInstim = periodoInstim;
    }

    public String getPeriodoInsusr() {
        return periodoInsusr;
    }

    public void setPeriodoInsusr(String periodoInsusr) {
        this.periodoInsusr = periodoInsusr;
    }

    public String getPeriodoSts() {
        return periodoSts;
    }

    public void setPeriodoSts(String periodoSts) {
        this.periodoSts = periodoSts;
    }

    public Collection<TblCursoparalelo> getTblCursoparaleloCollection() {
        return tblCursoparaleloCollection;
    }

    public void setTblCursoparaleloCollection(Collection<TblCursoparalelo> tblCursoparaleloCollection) {
        this.tblCursoparaleloCollection = tblCursoparaleloCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (periodoId != null ? periodoId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblPeriodo)) {
            return false;
        }
        TblPeriodo other = (TblPeriodo) object;
        if ((this.periodoId == null && other.periodoId != null) || (this.periodoId != null && !this.periodoId.equals(other.periodoId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entidades.TblPeriodo[ periodoId=" + periodoId + " ]";
    }
    
}
