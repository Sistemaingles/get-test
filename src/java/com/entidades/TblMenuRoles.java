package com.entidades;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import com.dominio.BeanDominio;

@Entity
@Table(name="TBL_MENU_ROLES")
public class TblMenuRoles extends BeanDominio implements Serializable
{

	private static final long serialVersionUID = 1L;
	private TblMenu    menu;
	private TblRol     rol;

	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="ID_MENU")
        public TblMenu getMenu() 
        {
            return menu;
        }

        public void setMenu(TblMenu menu) 
        {
            this.menu = menu;
        }
	

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="ID_ROL")
	public TblRol getRol() 
        {
		return rol;
	}


	public void setRol(TblRol rol) 
        {
		this.rol = rol;
	}
	

	
}

