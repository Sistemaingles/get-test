package com.entidades;


import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import com.dominio.BeanDominio;

/**
 *
 * @author jespinoza
 */

@Entity
@Table(name="TBL_USUARIOS_ROLES")
public class TblUsuariosRoles extends BeanDominio implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private TblUsuarios usuarios;
	private TblRol rol;
	private String descripcion;
	
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name="ID_USUARIO")
	public TblUsuarios getUsuario() {
		return usuarios;
	}
	public void setUsuario(TblUsuarios usuarios) {
		this.usuarios = usuarios;
	}
	

	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name="ID_ROL")
	public TblRol getRol() {
		return rol;
	}
	public void setRol(TblRol rol) {
		this.rol = rol;
	}
	
	@Column(name="DESCRIPCION")
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	
	
}

