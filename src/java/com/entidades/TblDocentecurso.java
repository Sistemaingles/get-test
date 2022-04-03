
package com.entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
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
@Table(name = "tbl_docentecurso")
@NamedQueries({
    @NamedQuery(name = "TblDocentecurso.findAll", query = "SELECT t FROM TblDocentecurso t"),
    @NamedQuery(name = "TblDocentecurso.findByDocentecursoId", query = "SELECT t FROM TblDocentecurso t WHERE t.docentecursoId = :docentecursoId"),
    @NamedQuery(name = "TblDocentecurso.findByDocentecursoInsusr", query = "SELECT t FROM TblDocentecurso t WHERE t.docentecursoInsusr = :docentecursoInsusr"),
    @NamedQuery(name = "TblDocentecurso.findByDocentecursoInstim", query = "SELECT t FROM TblDocentecurso t WHERE t.docentecursoInstim = :docentecursoInstim"),
    @NamedQuery(name = "TblDocentecurso.findByDocentecursoUpdusr", query = "SELECT t FROM TblDocentecurso t WHERE t.docentecursoUpdusr = :docentecursoUpdusr"),
    @NamedQuery(name = "TblDocentecurso.findByDocentecursoUpdtim", query = "SELECT t FROM TblDocentecurso t WHERE t.docentecursoUpdtim = :docentecursoUpdtim"),
    @NamedQuery(name = "TblDocentecurso.findByDocentecursoDltusr", query = "SELECT t FROM TblDocentecurso t WHERE t.docentecursoDltusr = :docentecursoDltusr"),
    @NamedQuery(name = "TblDocentecurso.findByDocentecursoDlttim", query = "SELECT t FROM TblDocentecurso t WHERE t.docentecursoDlttim = :docentecursoDlttim"),
    @NamedQuery(name = "TblDocentecurso.findByDocentecursoSts", query = "SELECT t FROM TblDocentecurso t WHERE t.docentecursoSts = :docentecursoSts")})

public class TblDocentecurso implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "docentecurso_Id")
    private Long docentecursoId;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "docentecurso_insusr")
    private String docentecursoInsusr;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "docentecurso_instim")
    @Temporal(TemporalType.TIMESTAMP)
    private Date docentecursoInstim;
    
    @Basic(optional = false)
    @Column(name = "docentecurso_updusr")
    private String docentecursoUpdusr;
    
    @Basic(optional = false)
    @Column(name = "docentecurso_updtim")
    @Temporal(TemporalType.TIMESTAMP)
    private Date docentecursoUpdtim;
    
    @Basic(optional = false)
    @Column(name = "docentecurso_dltusr")
    private String docentecursoDltusr;
    
    @Basic(optional = false)
    @Column(name = "docentecurso_dlttim")
    @Temporal(TemporalType.TIMESTAMP)
    private Date docentecursoDlttim;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "docentecurso_sts")
    private String docentecursoSts;
    @JoinColumn(name = "docente_id", referencedColumnName = "docente_id")
    @ManyToOne(optional = false)
    private TblDocente docenteId;
    @JoinColumn(name = "cursoparalelo_id", referencedColumnName = "cursoparalelo_id")
    @ManyToOne(optional = false)
    private TblCursoparalelo cursoparaleloId;

    public TblDocentecurso() {
    }

    public TblDocentecurso(Long docentecursoId) {
        this.docentecursoId = docentecursoId;
    }

    public TblDocentecurso(Long docentecursoId, String docentecursoInsusr, Date docentecursoInstim, String docentecursoUpdusr, Date docentecursoUpdtim, String docentecursoDltusr, Date docentecursoDlttim, String docentecursoSts) {
        this.docentecursoId = docentecursoId;
        this.docentecursoInsusr = docentecursoInsusr;
        this.docentecursoInstim = docentecursoInstim;
        this.docentecursoUpdusr = docentecursoUpdusr;
        this.docentecursoUpdtim = docentecursoUpdtim;
        this.docentecursoDltusr = docentecursoDltusr;
        this.docentecursoDlttim = docentecursoDlttim;
        this.docentecursoSts = docentecursoSts;
    }

    public Long getDocentecursoId() {
        return docentecursoId;
    }

    public void setDocentecursoId(Long docentecursoId) {
        this.docentecursoId = docentecursoId;
    }

    public String getDocentecursoInsusr() {
        return docentecursoInsusr;
    }

    public void setDocentecursoInsusr(String docentecursoInsusr) {
        this.docentecursoInsusr = docentecursoInsusr;
    }

    public Date getDocentecursoInstim() {
        return docentecursoInstim;
    }

    public void setDocentecursoInstim(Date docentecursoInstim) {
        this.docentecursoInstim = docentecursoInstim;
    }

    public String getDocentecursoUpdusr() {
        return docentecursoUpdusr;
    }

    public void setDocentecursoUpdusr(String docentecursoUpdusr) {
        this.docentecursoUpdusr = docentecursoUpdusr;
    }

    public Date getDocentecursoUpdtim() {
        return docentecursoUpdtim;
    }

    public void setDocentecursoUpdtim(Date docentecursoUpdtim) {
        this.docentecursoUpdtim = docentecursoUpdtim;
    }

    public String getDocentecursoDltusr() {
        return docentecursoDltusr;
    }

    public void setDocentecursoDltusr(String docentecursoDltusr) {
        this.docentecursoDltusr = docentecursoDltusr;
    }

    public Date getDocentecursoDlttim() {
        return docentecursoDlttim;
    }

    public void setDocentecursoDlttim(Date docentecursoDlttim) {
        this.docentecursoDlttim = docentecursoDlttim;
    }

    public String getDocentecursoSts() {
        return docentecursoSts;
    }

    public void setDocentecursoSts(String docentecursoSts) {
        this.docentecursoSts = docentecursoSts;
    }

    public TblDocente getDocenteId() {
        return docenteId;
    }

    public void setDocenteId(TblDocente docenteId) {
        this.docenteId = docenteId;
    }

    public TblCursoparalelo getCursoparaleloId() {
        return cursoparaleloId;
    }

    public void setCursoparaleloId(TblCursoparalelo cursoparaleloId) {
        this.cursoparaleloId = cursoparaleloId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (docentecursoId != null ? docentecursoId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblDocentecurso)) {
            return false;
        }
        TblDocentecurso other = (TblDocentecurso) object;
        if ((this.docentecursoId == null && other.docentecursoId != null) || (this.docentecursoId != null && !this.docentecursoId.equals(other.docentecursoId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entidades.TblDocentecurso[ docentecursoId=" + docentecursoId + " ]";
    }
    
}
