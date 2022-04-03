
package com.entidades;

import java.io.Serializable;
import java.util.Date;
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
import javax.validation.constraints.Size;

/**
 *
 * @author jespinoza
 */
@Entity
@Table(name = "tbl_estudiantecurso")
@NamedQueries({
    @NamedQuery(name = "TblEstudiantecurso.findAll", query = "SELECT t FROM TblEstudiantecurso t"),
    @NamedQuery(name = "TblEstudiantecurso.findByEstudiantecursoId", query = "SELECT t FROM TblEstudiantecurso t WHERE t.estudiantecursoId = :estudiantecursoId"),
    @NamedQuery(name = "TblEstudiantecurso.findByEstudiantecursoInsusr", query = "SELECT t FROM TblEstudiantecurso t WHERE t.estudiantecursoInsusr = :estudiantecursoInsusr"),
    @NamedQuery(name = "TblEstudiantecurso.findByEstudiantecursoInstim", query = "SELECT t FROM TblEstudiantecurso t WHERE t.estudiantecursoInstim = :estudiantecursoInstim"),
    @NamedQuery(name = "TblEstudiantecurso.findByEstudiantecursoUpdusr", query = "SELECT t FROM TblEstudiantecurso t WHERE t.estudiantecursoUpdusr = :estudiantecursoUpdusr"),
    @NamedQuery(name = "TblEstudiantecurso.findByEstudiantecursoUpdtim", query = "SELECT t FROM TblEstudiantecurso t WHERE t.estudiantecursoUpdtim = :estudiantecursoUpdtim"),
    @NamedQuery(name = "TblEstudiantecurso.findByEstudiantecursoDltusr", query = "SELECT t FROM TblEstudiantecurso t WHERE t.estudiantecursoDltusr = :estudiantecursoDltusr"),
    @NamedQuery(name = "TblEstudiantecurso.findByEstudiantecursoDlttim", query = "SELECT t FROM TblEstudiantecurso t WHERE t.estudiantecursoDlttim = :estudiantecursoDlttim"),
    @NamedQuery(name = "TblEstudiantecurso.findByEstudiantecursoSts", query = "SELECT t FROM TblEstudiantecurso t WHERE t.estudiantecursoSts = :estudiantecursoSts")})
public class TblEstudiantecurso implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "estudiantecurso_Id")
    private Long estudiantecursoId;
    
    @Size(max = 20)
    @Column(name = "estudiantecurso_insusr")
    private String estudiantecursoInsusr;
    
    @Column(name = "estudiantecurso_instim")
    @Temporal(TemporalType.TIMESTAMP)
    private Date estudiantecursoInstim;
    
    @Size(max = 20)
    @Column(name = "estudiantecurso_updusr")
    private String estudiantecursoUpdusr;
    
    @Column(name = "estudiantecurso_updtim")
    @Temporal(TemporalType.TIMESTAMP)
    private Date estudiantecursoUpdtim;
    
    @Size(max = 20)
    @Column(name = "estudiantecurso_dltusr")
    private String estudiantecursoDltusr;
    
    @Column(name = "estudiantecurso_dlttim")
    @Temporal(TemporalType.DATE)
    private Date estudiantecursoDlttim;
    
    @Size(max = 1)
    @Column(name = "estudiantecurso_sts")
    private String estudiantecursoSts;
    
    @JoinColumn(name = "cursoparalelo_id", referencedColumnName = "cursoparalelo_id")
    @ManyToOne
    private TblCursoparalelo cursoparaleloId;
    @JoinColumn(name = "estudiante_id", referencedColumnName = "estudiante_id")
    @ManyToOne
    private TblEstudiante estudianteId;

    public TblEstudiantecurso() {
    }

    public TblEstudiantecurso(Long estudiantecursoId) {
        this.estudiantecursoId = estudiantecursoId;
    }

    public Long getEstudiantecursoId() {
        return estudiantecursoId;
    }

    public void setEstudiantecursoId(Long estudiantecursoId) {
        this.estudiantecursoId = estudiantecursoId;
    }

    public String getEstudiantecursoInsusr() {
        return estudiantecursoInsusr;
    }

    public void setEstudiantecursoInsusr(String estudiantecursoInsusr) {
        this.estudiantecursoInsusr = estudiantecursoInsusr;
    }

    public Date getEstudiantecursoInstim() {
        return estudiantecursoInstim;
    }

    public void setEstudiantecursoInstim(Date estudiantecursoInstim) {
        this.estudiantecursoInstim = estudiantecursoInstim;
    }

    public String getEstudiantecursoUpdusr() {
        return estudiantecursoUpdusr;
    }

    public void setEstudiantecursoUpdusr(String estudiantecursoUpdusr) {
        this.estudiantecursoUpdusr = estudiantecursoUpdusr;
    }

    public Date getEstudiantecursoUpdtim() {
        return estudiantecursoUpdtim;
    }

    public void setEstudiantecursoUpdtim(Date estudiantecursoUpdtim) {
        this.estudiantecursoUpdtim = estudiantecursoUpdtim;
    }

    public String getEstudiantecursoDltusr() {
        return estudiantecursoDltusr;
    }

    public void setEstudiantecursoDltusr(String estudiantecursoDltusr) {
        this.estudiantecursoDltusr = estudiantecursoDltusr;
    }

    public Date getEstudiantecursoDlttim() {
        return estudiantecursoDlttim;
    }

    public void setEstudiantecursoDlttim(Date estudiantecursoDlttim) {
        this.estudiantecursoDlttim = estudiantecursoDlttim;
    }

    public String getEstudiantecursoSts() {
        return estudiantecursoSts;
    }

    public void setEstudiantecursoSts(String estudiantecursoSts) {
        this.estudiantecursoSts = estudiantecursoSts;
    }

    public TblCursoparalelo getCursoparaleloId() {
        return cursoparaleloId;
    }

    public void setCursoparaleloId(TblCursoparalelo cursoparaleloId) {
        this.cursoparaleloId = cursoparaleloId;
    }

    public TblEstudiante getEstudianteId() {
        return estudianteId;
    }

    public void setEstudianteId(TblEstudiante estudianteId) {
        this.estudianteId = estudianteId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (estudiantecursoId != null ? estudiantecursoId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblEstudiantecurso)) {
            return false;
        }
        TblEstudiantecurso other = (TblEstudiantecurso) object;
        if ((this.estudiantecursoId == null && other.estudiantecursoId != null) || (this.estudiantecursoId != null && !this.estudiantecursoId.equals(other.estudiantecursoId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entidades.TblEstudiantecurso[ estudiantecursoId=" + estudiantecursoId + " ]";
    }
    
}
