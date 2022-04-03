
package com.seguridad;

import com.entidades.TblUsuarios;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import com.servicios.impl.ICierreSession;
import com.servicios.impl.ITestCierreSession;
import com.servicios.impl.ITestUsuarioSession;
import com.servicios.impl.IUsuarioSession;
import com.servicios.impl.IservicioAplicacion;
import javax.ejb.EJB;


@Named("cierreSession")
@RequestScoped
public class CierreSession 
{

	@Inject
	@ITestCierreSession
	private ICierreSession cierreSession;
	
        @EJB
        private IservicioAplicacion  servicioAplicacion;

         
        @Inject
        @ITestUsuarioSession
        private IUsuarioSession<TblUsuarios> usuarioSession;
        
	public String cerrarSession ()
	{
		System.out.println("Cerrando session");
                
		cierreSession.cerrarSession();
                
                servicioAplicacion.eliminaTempActiv(usuarioSession.getUsuarioSession().getId(), "");
        
                System.out.println("Cerró session");
		return "/login.jsf?faces-redirect=true";
	}
	
	public String getServletCierreSession ()
	{
            return "/login.jsf?faces-redirect=true";
	}
	
}
