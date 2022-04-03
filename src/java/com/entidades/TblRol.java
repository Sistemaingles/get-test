
package com.entidades;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import com.dominio.BeanDominio;

/**
 *
 * @author jespinoza
 */

@Entity
@Table(name="TBL_ROL")
public class TblRol extends BeanDominio implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private List<TblUsuariosRoles> usuariosRoles;
//	private List<OpcionesRoles> opcionesRoles;
	private String rol;

	@Column(name="ROL")
	public String getRol() 
        {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	//@OneToMany(mappedBy="rol")
        @OneToMany(targetEntity = TblUsuariosRoles.class)
	public List<TblUsuariosRoles> getUsuariosRoles() {
		return usuariosRoles;
	}

	public void setUsuariosRoles(List<TblUsuariosRoles> usuariosRoles) {
		this.usuariosRoles = usuariosRoles;
	}

//	@OneToMany(mappedBy="rol")
//	public List<OpcionesRoles> getOpcionesRoles() {
//		return opcionesRoles;
//	}
//
//	public void setOpcionesRoles(List<OpcionesRoles> opcionesRoles) {
//		this.opcionesRoles = opcionesRoles;
//	}
	

}
