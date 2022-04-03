package com.seguridad;

import java.io.IOException;
import javax.faces.context.FacesContext;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.servicios.TestCierreSession;
import com.servicios.impl.ITestCierreSession;




/**
 * Servlet implementation class ServletCierreSession
 */
@WebServlet("/ServletCierreSession.do")
public class ServletCierreSession extends HttpServlet 
{
    private static final long serialVersionUID = 1L;
       
    @Inject
    @ITestCierreSession
    private TestCierreSession cierreSession;
	
	
    public ServletCierreSession() 
    {
        super();        
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
	System.out.println("Cierre de session");
	cierreSession.cerrarSession();
	response.sendRedirect(((HttpServletRequest)(FacesContext.getCurrentInstance().getExternalContext().getRequest())).getContextPath()+"/ServletCierreSession.do");
    }
}
