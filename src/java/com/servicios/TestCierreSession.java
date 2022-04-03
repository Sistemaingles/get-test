package com.servicios;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import com.servicios.impl.ICierreSession;
import com.servicios.impl.ITestCierreSession;




@ITestCierreSession
@RequestScoped
public class TestCierreSession implements ICierreSession 
{
	public void cerrarSession()
        {
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext externalContext = context.getExternalContext();
		Object session = externalContext.getSession(false);
		HttpSession httpSession = (HttpSession) session;
		httpSession.invalidate();
	}

	
}
