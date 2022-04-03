
package com.entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author jespinoza
 */
@Entity
@Table(name = "tbl_tempactiv")
@NamedQueries({
    @NamedQuery(name = "TblTempactiv.findAll", query = "SELECT t FROM TblTempactiv t")})
public class TblTempactiv implements Serializable {
   
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "tempActiv_Id")
    private Long tempActivId;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "tempActiv_UsrCod")
    private long tempActivUsrCod;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "tempActiv_IdRef")
    private long tempActivIdRef;
    
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "tempActiv_Tipo")
    private String tempActivTipo;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "tempActiv_Sts")
    private String tempActivSts;

    public TblTempactiv() {
    }

    public TblTempactiv(Long tempActivId) {
        this.tempActivId = tempActivId;
    }

    public TblTempactiv(Long tempActivId, long tempActivUsrCod, long tempActiv_IdRef,String tempActivTipo, String tempActivSts) {
        this.tempActivId = tempActivId;
        this.tempActivUsrCod = tempActivUsrCod;
        this.tempActivIdRef = tempActiv_IdRef;
        this.tempActivSts = tempActivSts;
        this.tempActivTipo = tempActivTipo;
    }

    public Long getTempActivId() {
        return tempActivId;
    }

    public void setTempActivId(Long tempActivId) {
        this.tempActivId = tempActivId;
    }

    public long getTempActivUsrCod() {
        return tempActivUsrCod;
    }

    public void setTempActivUsrCod(long tempActivUsrCod) {
        this.tempActivUsrCod = tempActivUsrCod;
    }

    public long getTempActivIdRef() {
        return tempActivIdRef;
    }

    public void setTempActivIdRef(long tempActivIdRef) {
        this.tempActivIdRef = tempActivIdRef;
    }

    public String getTempActivSts() {
        return tempActivSts;
    }

    public void setTempActivSts(String tempActivSts) {
        this.tempActivSts = tempActivSts;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tempActivId != null ? tempActivId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblTempactiv)) {
            return false;
        }
        TblTempactiv other = (TblTempactiv) object;
        if ((this.tempActivId == null && other.tempActivId != null) || (this.tempActivId != null && !this.tempActivId.equals(other.tempActivId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entidades.TblTempactiv[ tempActivId=" + tempActivId + " ]";
    }

    public String getTempActivTipo() {
        return tempActivTipo;
    }

    public void setTempActivTipo(String tempActivTipo) {
        this.tempActivTipo = tempActivTipo;
    }
    
    
}
