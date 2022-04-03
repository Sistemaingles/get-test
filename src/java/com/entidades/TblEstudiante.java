
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
@Table(name = "tbl_estudiante")
@NamedQueries({
    @NamedQuery(name = "TblEstudiante.findAll", query = "SELECT t FROM TblEstudiante t"),
    @NamedQuery(name = "TblEstudiante.findByEstudianteId", query = "SELECT t FROM TblEstudiante t WHERE t.estudianteId = :estudianteId"),
    @NamedQuery(name = "TblEstudiante.findByEstudianteIdent", query = "SELECT t FROM TblEstudiante t WHERE t.estudianteIdent = :estudianteIdent"),
    @NamedQuery(name = "TblEstudiante.findByEstudianteNom", query = "SELECT t FROM TblEstudiante t WHERE t.estudianteNom = :estudianteNom"),
    @NamedQuery(name = "TblEstudiante.findByEstudianteApe", query = "SELECT t FROM TblEstudiante t WHERE t.estudianteApe = :estudianteApe"),
    @NamedQuery(name = "TblEstudiante.findByEstudianteGenero", query = "SELECT t FROM TblEstudiante t WHERE t.estudianteGenero = :estudiantegenero"),
    @NamedQuery(name = "TblEstudiante.findByEstudiantePapa", query = "SELECT t FROM TblEstudiante t WHERE t.estudiantePapa = :estudiantePapa"),
    @NamedQuery(name = "TblEstudiante.findByEstudianteMama", query = "SELECT t FROM TblEstudiante t WHERE t.estudianteMama = :estudianteMama"),
    @NamedQuery(name = "TblEstudiante.findByEstudianteCelular", query = "SELECT t FROM TblEstudiante t WHERE t.estudianteCelular = :estudianteCelular"),
    @NamedQuery(name = "TblEstudiante.findByEstudianteInsusr", query = "SELECT t FROM TblEstudiante t WHERE t.estudianteInsusr = :estudianteInsusr"),
    @NamedQuery(name = "TblEstudiante.findByEstudianteInstim", query = "SELECT t FROM TblEstudiante t WHERE t.estudianteInstim = :estudianteInstim"),
    @NamedQuery(name = "TblEstudiante.findByEstudianteUpdusr", query = "SELECT t FROM TblEstudiante t WHERE t.estudianteUpdusr = :estudianteUpdusr"),
    @NamedQuery(name = "TblEstudiante.findByEstudianteUpdtim", query = "SELECT t FROM TblEstudiante t WHERE t.estudianteUpdtim = :estudianteUpdtim"),
    @NamedQuery(name = "TblEstudiante.findByEstudianteDltusr", query = "SELECT t FROM TblEstudiante t WHERE t.estudianteDltusr = :estudianteDltusr"),
    @NamedQuery(name = "TblEstudiante.findByEstudianteDlttim", query = "SELECT t FROM TblEstudiante t WHERE t.estudianteDlttim = :estudianteDlttim"),
    @NamedQuery(name = "TblEstudiante.findByEstudianteSts", query = "SELECT t FROM TblEstudiante t WHERE t.estudianteSts = :estudianteSts")})

public class TblEstudiante implements Serializable {
    
    /*@OneToMany(mappedBy = "estudianteId")
    private Collection<TblEstudiantecurso> tblEstudiantecursoCollection;
    */
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "estudiante_id")
    private Long estudianteId;
    
    @OneToOne
    @JoinColumn(name ="Usuario_Cod",nullable = false)
    private TblUsuarios usuario_cod;
     
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "estudiante_ident")
    private String estudianteIdent;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "estudiante_nom")
    private String estudianteNom;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "estudiante_ape")
    private String estudianteApe;
    
    @Column(name = "estudiante_fechaNac")
    private Date estudianteFechaNac;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    
    
    @Column(name = "estudiante_genero")
    private String estudianteGenero;
    
    @Basic(optional = false)
    @Column(name = "estudiante_papa")
    private String estudiantePapa;
    
    @Basic(optional = false)
    @Column(name = "estudiante_mama")
    private String estudianteMama;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "estudiante_celular")
    private String estudianteCelular;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "estudiante_insusr")
    private String estudianteInsusr;
    @Basic(optional = false)
    @NotNull
    @Column(name = "estudiante_instim")
    @Temporal(TemporalType.TIMESTAMP)
    private Date estudianteInstim;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "estudiante_updusr")
    private String estudianteUpdusr;
    @Basic(optional = false)
    @NotNull
    @Column(name = "estudiante_updtim")
    @Temporal(TemporalType.TIMESTAMP)
    private Date estudianteUpdtim;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "estudiante_dltusr")
    private String estudianteDltusr;
    @Basic(optional = false)
    @NotNull
    @Column(name = "estudiante_dlttim")
    @Temporal(TemporalType.TIMESTAMP)
    private Date estudianteDlttim;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "estudiante_sts")
    private String estudianteSts;

    public TblEstudiante() {
    }

    public TblEstudiante(Long estudianteId) {
        this.estudianteId = estudianteId;
    }

    public TblEstudiante(Long estudianteId, String estudianteIdent, String estudianteNom, String estudianteApe, Date estudianteFechaNac, String estudianteGenero, String estudiantePapa, String estudianteMama, String estudianteCelular, String estudianteInsusr, Date estudianteInstim, String estudianteUpdusr, Date estudianteUpdtim, String estudianteDltusr, Date estudianteDlttim, String estudianteSts) {
        this.estudianteId = estudianteId;
        this.estudianteIdent = estudianteIdent;
        this.estudianteNom = estudianteNom;
        this.estudianteApe = estudianteApe;
        this.estudianteFechaNac = estudianteFechaNac;
        this.estudianteGenero = estudianteGenero;
        this.estudiantePapa = estudiantePapa;
        this.estudianteMama = estudianteMama;
        this.estudianteCelular = estudianteCelular;
        this.estudianteInsusr = estudianteInsusr;
        this.estudianteInstim = estudianteInstim;
        this.estudianteUpdusr = estudianteUpdusr;
        this.estudianteUpdtim = estudianteUpdtim;
        this.estudianteDltusr = estudianteDltusr;
        this.estudianteDlttim = estudianteDlttim;
        this.estudianteSts = estudianteSts;
    }

    public Long getEstudianteId() {
        return estudianteId;
    }

    public void setEstudianteId(Long estudianteId) {
        this.estudianteId = estudianteId;
    }

    public TblUsuarios getUsuario_cod() {
        return usuario_cod;
    }

    public void setUsuario_cod(TblUsuarios usuario_cod) {
        this.usuario_cod = usuario_cod;
    }
    
    
    public String getEstudianteIdent() {
        return estudianteIdent;
    }

    public void setEstudianteIdent(String estudianteIdent) {
        this.estudianteIdent = estudianteIdent;
    }

    public String getEstudianteNom() {
        return estudianteNom;
    }

    public void setEstudianteNom(String estudianteNom) {
        this.estudianteNom = estudianteNom;
    }

    public String getEstudianteApe() {
        return estudianteApe;
    }

    public void setEstudianteApe(String estudianteApe) {
        this.estudianteApe = estudianteApe;
    }

    public Date getEstudianteFechaNac() {
        return estudianteFechaNac;
    }

    public void setEstudianteFechaNac(Date estudianteFechaNac) {
        this.estudianteFechaNac = estudianteFechaNac;
    }

    public String getEstudiantePapa() {
        return estudiantePapa;
    }

    public void setEstudiantePapa(String estudiantePapa) {
        this.estudiantePapa = estudiantePapa;
    }

    public String getEstudianteMama() {
        return estudianteMama;
    }

    public void setEstudianteMama(String estudianteMama) {
        this.estudianteMama = estudianteMama;
    }

    public String getEstudianteCelular() {
        return estudianteCelular;
    }

    public void setEstudianteCelular(String estudianteCelular) {
        this.estudianteCelular = estudianteCelular;
    }

    public String getEstudianteInsusr() {
        return estudianteInsusr;
    }

    public void setEstudianteInsusr(String estudianteInsusr) {
        this.estudianteInsusr = estudianteInsusr;
    }

    public Date getEstudianteInstim() {
        return estudianteInstim;
    }

    public void setEstudianteInstim(Date estudianteInstim) {
        this.estudianteInstim = estudianteInstim;
    }

    public String getEstudianteUpdusr() {
        return estudianteUpdusr;
    }

    public void setEstudianteUpdusr(String estudianteUpdusr) {
        this.estudianteUpdusr = estudianteUpdusr;
    }

    public Date getEstudianteUpdtim() {
        return estudianteUpdtim;
    }

    public void setEstudianteUpdtim(Date estudianteUpdtim) {
        this.estudianteUpdtim = estudianteUpdtim;
    }

    public String getEstudianteDltusr() {
        return estudianteDltusr;
    }

    public void setEstudianteDltusr(String estudianteDltusr) {
        this.estudianteDltusr = estudianteDltusr;
    }

    public Date getEstudianteDlttim() {
        return estudianteDlttim;
    }

    public void setEstudianteDlttim(Date estudianteDlttim) {
        this.estudianteDlttim = estudianteDlttim;
    }

    public String getEstudianteSts() {
        return estudianteSts;
    }

    public void setEstudianteSts(String estudianteSts) {
        this.estudianteSts = estudianteSts;
    }

    public String getEstudianteGenero() {
        return estudianteGenero;
    }

    public void setEstudianteGenero(String estudianteGenero) {
        this.estudianteGenero = estudianteGenero;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (estudianteId != null ? estudianteId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblEstudiante)) {
            return false;
        }
        TblEstudiante other = (TblEstudiante) object;
        if ((this.estudianteId == null && other.estudianteId != null) || (this.estudianteId != null && !this.estudianteId.equals(other.estudianteId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entidades.TblEstudiante[ estudianteId=" + estudianteId + " ]";
    }

    /*public Collection<TblEstudiantecurso> getTblEstudiantecursoCollection() {
        return tblEstudiantecursoCollection;
    }

    public void setTblEstudiantecursoCollection(Collection<TblEstudiantecurso> tblEstudiantecursoCollection) {
        this.tblEstudiantecursoCollection = tblEstudiantecursoCollection;
    }*/
    
}
