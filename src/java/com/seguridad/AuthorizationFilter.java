
package com.seguridad;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author AMORAN
 */
@WebFilter(filterName = "AuthorizationFilter", urlPatterns = {"/*"})
public class AuthorizationFilter implements Filter 
{
        /**
     * Checks if user is logged in. If not it redirects to the login.xhtml page.
     *
     * @param request
     * @param response
     * @param chain
     * @throws java.io.IOException
     * @throws javax.servlet.ServletException
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException 
    {
            // Comprueba si la variable de sesión está configurada
            HttpServletRequest req  = (HttpServletRequest) request;
            HttpServletResponse res = (HttpServletResponse) response;
            HttpSession ses         = req.getSession(false);
            String reqURI           = req.getRequestURI();
            
            // permite que el usuario proccede si url es login.xhtml o el usuario ha iniciado sesión o el usuario accede a cualquier página en carpeta pública
            if (reqURI.indexOf("/login.jsf") >= 0 || (ses != null)|| reqURI.contains("javax.faces.resource")) 
            {
                chain.doFilter(request, response);
            } else 
            // el usuario no accedió pero pidió una página que no está permitida, así que tome el usuario a la página de inicio de sesión
            {
                
                System.out.println("Se expiro Sesión o entró a una pagina no permitida");
                res.sendRedirect(req.getContextPath()+ "/login.jsf");  // Anonymous user. Redirect to login page
//                req.removeAttribute("staff");
            }
    }

    @Override
    public void init(FilterConfig config) throws ServletException 
    {
        // Nothing to do here!
    }

    @Override
    public void destroy() 
    {
        // Nothing to do here!
    }
}
