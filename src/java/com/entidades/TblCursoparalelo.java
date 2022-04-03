
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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
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
@Table(name = "tbl_cursoparalelo")
@NamedQueries({
    @NamedQuery(name = "TblCursoparalelo.findAll", query = "SELECT t FROM TblCursoparalelo t"),
    @NamedQuery(name = "TblCursoparalelo.findByCursoparaleloId", query = "SELECT t FROM TblCursoparalelo t WHERE t.cursoparaleloId = :cursoparaleloId"),
    @NamedQuery(name = "TblCursoparalelo.findByCursoparaleloInsusr", query = "SELECT t FROM TblCursoparalelo t WHERE t.cursoparaleloInsusr = :cursoparaleloInsusr"),
    @NamedQuery(name = "TblCursoparalelo.findByCursoparaleloInstim", query = "SELECT t FROM TblCursoparalelo t WHERE t.cursoparaleloInstim = :cursoparaleloInstim"),
    @NamedQuery(name = "TblCursoparalelo.findByCursoparaleloSts", query = "SELECT t FROM TblCursoparalelo t WHERE t.cursoparaleloSts = :cursoparaleloSts")})

public class TblCursoparalelo implements Serializable {
    
    @OneToMany(mappedBy = "cursoparaleloId")
    private Collection<TblEstudiantecurso> tblEstudiantecursoCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cursoparaleloId")
    private Collection<TblDocentecurso> tblDocentecursoCollection;
   
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "cursoparalelo_id")
    private Long cursoparaleloId;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "cursoparalelo_insusr")
    private String cursoparaleloInsusr;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cursoparalelo_instim")
    @Temporal(TemporalType.TIMESTAMP)
    private Date cursoparaleloInstim;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "cursoparalelo_sts")
    private String cursoparaleloSts;
    @JoinColumn(name = "periodo_id", referencedColumnName = "periodo_Id")
    @ManyToOne(optional = false)
    private TblPeriodo periodoId;
    @JoinColumn(name = "paralelo_id", referencedColumnName = "paralelo_id")
    @ManyToOne(optional = false)
    private TblParalelo paraleloId;
    @JoinColumn(name = "curso_id", referencedColumnName = "curso_id")
    @ManyToOne(optional = false)
    private TblCurso cursoId;

    public TblCursoparalelo() {
    }

    public TblCursoparalelo(Long cursoparaleloId) {
        this.cursoparaleloId = cursoparaleloId;
    }

    public TblCursoparalelo(Long cursoparaleloId, String cursoparaleloInsusr, Date cursoparaleloInstim, String cursoparaleloSts) {
        this.cursoparaleloId = cursoparaleloId;
        this.cursoparaleloInsusr = cursoparaleloInsusr;
        this.cursoparaleloInstim = cursoparaleloInstim;
        this.cursoparaleloSts = cursoparaleloSts;
    }

    public Long getCursoparaleloId() {
        return cursoparaleloId;
    }

    public void setCursoparaleloId(Long cursoparaleloId) {
        this.cursoparaleloId = cursoparaleloId;
    }

    public String getCursoparaleloInsusr() {
        return cursoparaleloInsusr;
    }

    public void setCursoparaleloInsusr(String cursoparaleloInsusr) {
        this.cursoparaleloInsusr = cursoparaleloInsusr;
    }

    public Date getCursoparaleloInstim() {
        return cursoparaleloInstim;
    }

    public void setCursoparaleloInstim(Date cursoparaleloInstim) {
        this.cursoparaleloInstim = cursoparaleloInstim;
    }

    public String getCursoparaleloSts() {
        return cursoparaleloSts;
    }

    public void setCursoparaleloSts(String cursoparaleloSts) {
        this.cursoparaleloSts = cursoparaleloSts;
    }

    public TblPeriodo getPeriodoId() {
        return periodoId;
    }

    public void setPeriodoId(TblPeriodo periodoId) {
        this.periodoId = periodoId;
    }

    public TblParalelo getParaleloId() {
        return paraleloId;
    }

    public void setParaleloId(TblParalelo paraleloId) {
        this.paraleloId = paraleloId;
    }

    public TblCurso getCursoId() {
        return cursoId;
    }

    public void setCursoId(TblCurso cursoId) {
        this.cursoId = cursoId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cursoparaleloId != null ? cursoparaleloId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblCursoparalelo)) {
            return false;
        }
        TblCursoparalelo other = (TblCursoparalelo) object;
        if ((this.cursoparaleloId == null && other.cursoparaleloId != null) || (this.cursoparaleloId != null && !this.cursoparaleloId.equals(other.cursoparaleloId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entidades.TblCursoparalelo[ cursoparaleloId=" + cursoparaleloId + " ]";
    }

    public Collection<TblDocentecurso> getTblDocentecursoCollection() {
        return tblDocentecursoCollection;
    }

    public void setTblDocentecursoCollection(Collection<TblDocentecurso> tblDocentecursoCollection) {
        this.tblDocentecursoCollection = tblDocentecursoCollection;
    }

    public Collection<TblEstudiantecurso> getTblEstudiantecursoCollection() {
        return tblEstudiantecursoCollection;
    }

    public void setTblEstudiantecursoCollection(Collection<TblEstudiantecurso> tblEstudiantecursoCollection) {
        this.tblEstudiantecursoCollection = tblEstudiantecursoCollection;
    }
    
}
