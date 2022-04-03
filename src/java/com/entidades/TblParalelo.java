
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
@Table(name = "tbl_paralelo")
@NamedQueries({
    @NamedQuery(name = "TblParalelo.findAll", query = "SELECT t FROM TblParalelo t"),
    @NamedQuery(name = "TblParalelo.findByParaleloId", query = "SELECT t FROM TblParalelo t WHERE t.paraleloId = :paraleloId"),
    @NamedQuery(name = "TblParalelo.findByParaleloNum", query = "SELECT t FROM TblParalelo t WHERE t.paraleloNum = :paraleloNum"),
    @NamedQuery(name = "TblParalelo.findByParaleloNombre", query = "SELECT t FROM TblParalelo t WHERE t.paraleloNombre = :paraleloNombre"),
    @NamedQuery(name = "TblParalelo.findByParaleloInsusr", query = "SELECT t FROM TblParalelo t WHERE t.paraleloInsusr = :paraleloInsusr"),
    @NamedQuery(name = "TblParalelo.findByParaleloInstim", query = "SELECT t FROM TblParalelo t WHERE t.paraleloInstim = :paraleloInstim"),
    @NamedQuery(name = "TblParalelo.findByParaleloUpdusr", query = "SELECT t FROM TblParalelo t WHERE t.paraleloUpdusr = :paraleloUpdusr"),
    @NamedQuery(name = "TblParalelo.findByParaleloUpdtim", query = "SELECT t FROM TblParalelo t WHERE t.paraleloUpdtim = :paraleloUpdtim"),
    @NamedQuery(name = "TblParalelo.findByParaleloDltusr", query = "SELECT t FROM TblParalelo t WHERE t.paraleloDltusr = :paraleloDltusr"),
    @NamedQuery(name = "TblParalelo.findByParaleloDlttim", query = "SELECT t FROM TblParalelo t WHERE t.paraleloDlttim = :paraleloDlttim"),
    @NamedQuery(name = "TblParalelo.findByParaleltoSts", query = "SELECT t FROM TblParalelo t WHERE t.paraleltoSts = :paraleltoSts")})

public class TblParalelo implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "paralelo_id")
    private Long paraleloId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "paralelo_num")
    private String paraleloNum;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "paralelo_nombre")
    private String paraleloNombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "paralelo_insusr")
    private String paraleloInsusr;
    @Basic(optional = false)
    @NotNull
    @Column(name = "paralelo_instim")
    @Temporal(TemporalType.TIMESTAMP)
    private Date paraleloInstim;
    @Size(max = 20)
    @Column(name = "paralelo_updusr")
    private String paraleloUpdusr;
    @Column(name = "paralelo_updtim")
    @Temporal(TemporalType.TIMESTAMP)
    private Date paraleloUpdtim;
    @Size(max = 20)
    @Column(name = "paralelo_dltusr")
    private String paraleloDltusr;
    @Column(name = "paralelo_dlttim")
    @Temporal(TemporalType.TIMESTAMP)
    private Date paraleloDlttim;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "paralelto_sts")
    private String paraleltoSts;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "paraleloId")
    private Collection<TblCursoparalelo> tblCursoparaleloCollection;

    public TblParalelo() {
    }

    public TblParalelo(Long paraleloId) {
        this.paraleloId = paraleloId;
    }

    public TblParalelo(Long paraleloId, String paraleloNum, String paraleloNombre, String paraleloInsusr, Date paraleloInstim, String paraleltoSts) {
        this.paraleloId = paraleloId;
        this.paraleloNum = paraleloNum;
        this.paraleloNombre = paraleloNombre;
        this.paraleloInsusr = paraleloInsusr;
        this.paraleloInstim = paraleloInstim;
        this.paraleltoSts = paraleltoSts;
    }

    public Long getParaleloId() {
        return paraleloId;
    }

    public void setParaleloId(Long paraleloId) {
        this.paraleloId = paraleloId;
    }

    public String getParaleloNum() {
        return paraleloNum;
    }

    public void setParaleloNum(String paraleloNum) {
        this.paraleloNum = paraleloNum;
    }

    public String getParaleloNombre() {
        return paraleloNombre;
    }

    public void setParaleloNombre(String paraleloNombre) {
        this.paraleloNombre = paraleloNombre;
    }

    public String getParaleloInsusr() {
        return paraleloInsusr;
    }

    public void setParaleloInsusr(String paraleloInsusr) {
        this.paraleloInsusr = paraleloInsusr;
    }

    public Date getParaleloInstim() {
        return paraleloInstim;
    }

    public void setParaleloInstim(Date paraleloInstim) {
        this.paraleloInstim = paraleloInstim;
    }

    public String getParaleloUpdusr() {
        return paraleloUpdusr;
    }

    public void setParaleloUpdusr(String paraleloUpdusr) {
        this.paraleloUpdusr = paraleloUpdusr;
    }

    public Date getParaleloUpdtim() {
        return paraleloUpdtim;
    }

    public void setParaleloUpdtim(Date paraleloUpdtim) {
        this.paraleloUpdtim = paraleloUpdtim;
    }

    public String getParaleloDltusr() {
        return paraleloDltusr;
    }

    public void setParaleloDltusr(String paraleloDltusr) {
        this.paraleloDltusr = paraleloDltusr;
    }

    public Date getParaleloDlttim() {
        return paraleloDlttim;
    }

    public void setParaleloDlttim(Date paraleloDlttim) {
        this.paraleloDlttim = paraleloDlttim;
    }

    public String getParaleltoSts() {
        return paraleltoSts;
    }

    public void setParaleltoSts(String paraleltoSts) {
        this.paraleltoSts = paraleltoSts;
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
        hash += (paraleloId != null ? paraleloId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblParalelo)) {
            return false;
        }
        TblParalelo other = (TblParalelo) object;
        if ((this.paraleloId == null && other.paraleloId != null) || (this.paraleloId != null && !this.paraleloId.equals(other.paraleloId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entidades.TblParalelo[ paraleloId=" + paraleloId + " ]";
    }
    
}
