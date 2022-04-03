
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
 * @author jespinoza
 */
@Entity
@Table(name = "tbl_curso")
@NamedQueries({
    @NamedQuery(name = "TblCurso.findAll", query = "SELECT t FROM TblCurso t"),
    @NamedQuery(name = "TblCurso.findByCursoId", query = "SELECT t FROM TblCurso t WHERE t.cursoId = :cursoId"),
    @NamedQuery(name = "TblCurso.findByCursoNumero", query = "SELECT t FROM TblCurso t WHERE t.cursoNumero = :cursoNumero"),
    @NamedQuery(name = "TblCurso.findByCursoDescripcion", query = "SELECT t FROM TblCurso t WHERE t.cursoDescripcion = :cursoDescripcion"),
    @NamedQuery(name = "TblCurso.findByCursoInsusr", query = "SELECT t FROM TblCurso t WHERE t.cursoInsusr = :cursoInsusr"),
    @NamedQuery(name = "TblCurso.findByCursoInstim", query = "SELECT t FROM TblCurso t WHERE t.cursoInstim = :cursoInstim"),
    @NamedQuery(name = "TblCurso.findByCursoSts", query = "SELECT t FROM TblCurso t WHERE t.cursoSts = :cursoSts")})
public class TblCurso implements Serializable {
    private static final long serialVersionUID 
            = 1L;
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "curso_id")
    private Long cursoId;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "curso_numero")
    private String cursoNumero;
    

    @Size( max = 50)
    @Column(name = "curso_descripcion")
    private String cursoDescripcion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "curso_insusr")
    private String cursoInsusr;
    @Basic(optional = false)
    @NotNull
    @Column(name = "curso_instim")
    @Temporal(TemporalType.TIMESTAMP)
    private Date cursoInstim;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "curso_sts")
    private String cursoSts;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cursoId")
    private Collection<TblCursoparalelo> tblCursoparaleloCollection;

    public TblCurso() {
    }

    public TblCurso(Long cursoId) {
        this.cursoId = cursoId;
    }

    public TblCurso(Long cursoId, String cursoNumero, String cursoDescripcion, String cursoInsusr, Date cursoInstim, String cursoSts) {
        this.cursoId = cursoId;
        this.cursoNumero = cursoNumero;
        this.cursoDescripcion = cursoDescripcion;
        this.cursoInsusr = cursoInsusr;
        this.cursoInstim = cursoInstim;
        this.cursoSts = cursoSts;
    }

    public Long getCursoId() {
        return cursoId;
    }

    public void setCursoId(Long cursoId) {
        this.cursoId = cursoId;
    }

    public String getCursoNumero() {
        return cursoNumero;
    }

    public void setCursoNumero(String cursoNumero) {
        this.cursoNumero = cursoNumero;
    }

    public String getCursoDescripcion() {
        return cursoDescripcion;
    }

    public void setCursoDescripcion(String cursoDescripcion) {
        this.cursoDescripcion = cursoDescripcion;
    }

    public String getCursoInsusr() {
        return cursoInsusr;
    }

    public void setCursoInsusr(String cursoInsusr) {
        this.cursoInsusr = cursoInsusr;
    }

    public Date getCursoInstim() {
        return cursoInstim;
    }

    public void setCursoInstim(Date cursoInstim) {
        this.cursoInstim = cursoInstim;
    }

    public String getCursoSts() {
        return cursoSts;
    }

    public void setCursoSts(String cursoSts) {
        this.cursoSts = cursoSts;
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
        hash += (cursoId != null ? cursoId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblCurso)) {
            return false;
        }
        TblCurso other = (TblCurso) object;
        if ((this.cursoId == null && other.cursoId != null) || (this.cursoId != null && !this.cursoId.equals(other.cursoId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entidades.TblCurso[ cursoId=" + cursoId + " ]";
    }
    
}
