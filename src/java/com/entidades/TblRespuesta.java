
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
@Table(name = "tbl_respuesta")
@NamedQueries({
@NamedQuery(name = "TblRespuesta.findAll", query = "SELECT t FROM TblRespuesta t")})

public class TblRespuesta implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "respuesta_id")
    private Long respuestaId;
    
    @JoinColumn(name = "actividad_id", referencedColumnName = "actividad_id")
    @ManyToOne(optional = false)
    private TblActividad actividadId;
    

    @Column(name = "respuesta_desc")
    private String respuestaDesc;

    @Column(name = "respuesta_calif")
    private int respuestaCalif;
    
    @NotNull
    @Column(name = "respuesta_orden")
    private int respuestaOrden;
    
    @Column(name = "respuesta_correcta")
    private String respuestaCorrecta;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "respuesta_insusr")
    private String respuestaInsusr;
    @Basic(optional = false)
    @NotNull
    @Column(name = "respuesta_instim")
    @Temporal(TemporalType.TIMESTAMP)
    private Date respuestaInstim;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "respuesta_updusr")
    private String respuestaUpdusr;
    
    @Basic(optional = false)
    @Column(name = "respuesta_updtim")
    @Temporal(TemporalType.TIMESTAMP)
    private Date respuestaUpdtim;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "respuesta_dltusr")
    private String respuestaDltusr;
    
    @Basic(optional = false)
    @Column(name = "respuesta_dlttim")
    @Temporal(TemporalType.TIMESTAMP)
    private Date respuestaDlttim;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "respuesta_sts")
    private String respuestaSts;

    public TblRespuesta() {
    }

    public TblRespuesta(Long respuestaId) {
        this.respuestaId = respuestaId;
    }

    public TblRespuesta(Long respuestaId, int respuestaCalif, int respuestaOrden, String respuestaInsusr, Date respuestaInstim, String respuestaUpdusr, Date respuestaUpdtim, String respuestaDltusr, Date respuestaDlttim, String respuestaSts) {
        this.respuestaId = respuestaId;
        this.respuestaCalif = respuestaCalif;
        this.respuestaOrden = respuestaOrden;
        this.respuestaInsusr = respuestaInsusr;
        this.respuestaInstim = respuestaInstim;
        this.respuestaUpdusr = respuestaUpdusr;
        this.respuestaUpdtim = respuestaUpdtim;
        this.respuestaDltusr = respuestaDltusr;
        this.respuestaDlttim = respuestaDlttim;
        this.respuestaSts = respuestaSts;
    }

    public Long getRespuestaId() {
        return respuestaId;
    }

    public void setRespuestaId(Long respuestaId) {
        this.respuestaId = respuestaId;
    }

    public int getRespuestaCalif() {
        return respuestaCalif;
    }

    public void setRespuestaCalif(int respuestaCalif) {
        this.respuestaCalif = respuestaCalif;
    }

    public int getRespuestaOrden() {
        return respuestaOrden;
    }

    public void setRespuestaOrden(int respuestaOrden) {
        this.respuestaOrden = respuestaOrden;
    }

    public String getRespuestaInsusr() {
        return respuestaInsusr;
    }

    public void setRespuestaInsusr(String respuestaInsusr) {
        this.respuestaInsusr = respuestaInsusr;
    }

    public Date getRespuestaInstim() {
        return respuestaInstim;
    }

    public void setRespuestaInstim(Date respuestaInstim) {
        this.respuestaInstim = respuestaInstim;
    }

    public String getRespuestaUpdusr() {
        return respuestaUpdusr;
    }

    public void setRespuestaUpdusr(String respuestaUpdusr) {
        this.respuestaUpdusr = respuestaUpdusr;
    }

    public Date getRespuestaUpdtim() {
        return respuestaUpdtim;
    }

    public void setRespuestaUpdtim(Date respuestaUpdtim) {
        this.respuestaUpdtim = respuestaUpdtim;
    }

    public String getRespuestaDltusr() {
        return respuestaDltusr;
    }

    public void setRespuestaDltusr(String respuestaDltusr) {
        this.respuestaDltusr = respuestaDltusr;
    }

    public Date getRespuestaDlttim() {
        return respuestaDlttim;
    }

    public void setRespuestaDlttim(Date respuestaDlttim) {
        this.respuestaDlttim = respuestaDlttim;
    }

    public String getRespuestaSts() {
        return respuestaSts;
    }

    public void setRespuestaSts(String respuestaSts) {
        this.respuestaSts = respuestaSts;
    }

    public TblActividad getActividadId() {
        return actividadId;
    }

    public void setActividadId(TblActividad actividadId) {
        this.actividadId = actividadId;
    }

    public String getRespuestaDesc() {
        return respuestaDesc;
    }

    public void setRespuestaDesc(String respuestaDesc) {
        this.respuestaDesc = respuestaDesc;
    }

    public String getRespuestaCorrecta() {
        return respuestaCorrecta;
    }

    public void setRespuestaCorrecta(String respuestaCorrecta) {
        this.respuestaCorrecta = respuestaCorrecta;
    }
    
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (respuestaId != null ? respuestaId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblRespuesta)) {
            return false;
        }
        TblRespuesta other = (TblRespuesta) object;
        if ((this.respuestaId == null && other.respuestaId != null) || (this.respuestaId != null && !this.respuestaId.equals(other.respuestaId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entidades.TblRespuesta[ respuestaId=" + respuestaId + " ]";
    }
    
    
}
