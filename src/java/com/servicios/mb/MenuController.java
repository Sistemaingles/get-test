package com.servicios.mb;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
//import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
//import roca.configuracion.eao.EjbGenericEAO;
import com.entidades.TblMenu;
import com.entidades.TblUsuarios;
import com.servicios.eao.menuRolesEAO;
import com.servicios.eao.rolEAO;
import com.servicios.impl.ITestUsuarioSession;
import com.servicios.impl.IUsuarioSession;


@ManagedBean
@RequestScoped

public class MenuController  extends BeanFormulario implements java.io.Serializable 
{

    private static final long serialVersionUID = 7481372634818437093L;
    
    @EJB
    private final menuRolesEAO menuRolesEAO;
    
    @EJB
    private final rolEAO rolEAO;

    private String      pageLink;
    private String      pageName;
    private List<TblMenu>  menuList;
    private String      searhText;
    
    @Inject
    @ITestUsuarioSession
    private IUsuarioSession<TblUsuarios> usuarioSession;

    public MenuController() 
    {
        menuRolesEAO = new menuRolesEAO();
        pageLink     = "blankPage";
        pageName     = "Main Page";
        rolEAO       = new rolEAO();
    }
    
    public List<TblMenu> getMenuList() 
    {
        try 
        {   
            if (menuList == null) 
            {
                menuList    =   menuRolesEAO.obtenerMenuRoles(getUserSession());
            }   
        } catch (Exception ex) 
        {
            System.out.println("Error Al extraer Lista de Menu:"+ex.getMessage());
        }

        return menuList;
    }
    
    public String obtieneRol(){
        return rolEAO.obtieneRolxUsuario(getUserSession());
    }
      
    public String obtieneObservRol(){
        return rolEAO.obtieneObservxUsuario(getUserSession());
    }
         
    public void setMenuList(List<TblMenu> menuList) 
    {
        this.menuList = menuList;
    }


    public void setPage(String link, String name) 
    {
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, Object> map = context.getViewRoot().getAttributes();
        List<String> list = new ArrayList<String>();

        for (String key : map.keySet()) 
        {
            if (!key.equals("menuController")) 
            {
                list.add(key);
            }
        }

        if (list != null && !list.isEmpty()) 
        {
            for (String get : list) {
                map.remove(get);
            }
        }
        
        setPageLink(link);
        setPageName(name);
    }

    public void menuSearchValueChange(ValueChangeEvent event) 
    {
        menuList.clear();
        if (event.getOldValue() == null || !event.getOldValue().equals(event.getNewValue())) 
        {
            try 
            {
                menuList = menuRolesEAO.searchMenuList(getUserSession(),event.getNewValue().toString());
                
            } catch (Exception ex) 
            {
                Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        
        for (int i = 0; i < menuList.size(); i++) 
        {

            TblMenu get = menuList.get(i);
            if (get.getId() != null) 
            {
                
                TblMenu topMenu;
                try 
                {
                    topMenu = menuRolesEAO.getTopMenu(getUserSession(),get.getId().intValue());
                    if (topMenu != null) 
                    {
                        if (!menuList.contains(topMenu)) 
                        {
                            menuList.add(topMenu);
                        }
                    }
                    
                } catch (Exception ex) 
                {
                    Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }
    }

    public String getUserSession()
    {
        System.out.println("Usuario de Sesion: "+usuarioSession.getUsuarioSession().getUsuario()); 
        return usuarioSession.getUsuarioSession().getUsuario(); 
    }
    
    public String getPageLink() 
    {
        return pageLink;
    }

    public void setPageLink(String pageLink) 
    {
        this.pageLink = pageLink;
    }

    public String getSearhText() 
    {
        return searhText;
    }

    public void setSearhText(String searhText) 
    {
        this.searhText = searhText;
    }
    
    public String getPageName() 
    {
        return pageName;
    }

    public void setPageName(String pageName) 
    {
        this.pageName = pageName;
    }
    
}
