
package com.servicios.eao;


import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import com.genericos.eao.EjbGenericEAO;
import com.entidades.TblMenu;


/**
 *
 * @author AMORAN
 */

@Stateless
@LocalBean
public class menuRolesEAO extends EjbGenericEAO<TblMenu, Long>
{
    
    private String sql;
    
    public menuRolesEAO() 
    {
       super(TblMenu.class);
    }
    
    public List<TblMenu> searchMenuList(String usuario,String text)
    {
        try 
        {
            sql =   " select * from tbl_menu m" +
                " inner join tbl_menu_roles mr" +
                " on mr.ID_MENU = m.ID" +
                " inner join tbl_usuarios_roles ur" +
                " on ur.ID_ROL = mr.ID_ROL" +
                " inner join tbl_usuarios u" +
                " on u.ID = ur.ID_USUARIO" +
                " where u.USUARIO       =  ?"+
                " and m.DESCRIPCION like  ? "+ 
                " order by m.ID  ";

            Query quer = administradorEntidad.createNativeQuery(sql,TblMenu.class);
            quer.setParameter(1, usuario);
            quer.setParameter(2, text+"%");
            
            return quer.getResultList();
        }catch(NoResultException e)
        {
     
           return null;
        }
        

        
    }
    
    public TblMenu getTopMenu(String usuario,Integer topMenuId) 
    {
        try 
        {
            sql =   " select * from tbl_menu m" +
                " inner join tbl_menu_roles mr" +
                " on mr.ID_MENU = m.ID" +
                " inner join tbl_usuarios_roles ur" +
                " on ur.ID_ROL = mr.ID_ROL" +
                " inner join tbl_usuarios u" +
                " on u.ID = ur.ID_USUARIO" +
                " where u.USUARIO =  ?"+
                " and m.SUBMENUID =  ? "+
                " order by m.ID  ";

            Query quer = administradorEntidad.createNativeQuery(sql,TblMenu.class);
            quer.setParameter(1, usuario);
            quer.setParameter(2, topMenuId);
            
            return (TblMenu) quer.getSingleResult();
            
        }catch(NoResultException e)
        {
           return null;
        } 
    }
    

    //PARA MENUCONTROLLER
    public List<TblMenu> obtenerMenuRoles(String usuario) throws Exception
    {
       
        try{
        
        sql =   " select * from tbl_menu m"         +
                " inner join tbl_menu_roles mr"     +
                " on mr.ID_MENU = m.ID"             +
                " inner join tbl_usuarios_roles ur" +
                " on ur.ID_ROL = mr.ID_ROL"         +
                " inner join tbl_usuarios u"        +
                " on u.ID = ur.ID_USUARIO"          +
                " where u.USUARIO =  ?"             +
                " and m.estado = ? "                +
                " order by m.ID  ";
 
        Query quer = administradorEntidad.createNativeQuery(sql,TblMenu.class);
        quer.setParameter(1, usuario);
        quer.setParameter(2, "A");
        return quer.getResultList();
         
        }catch(Exception e)
        {
           throw  e;
        }

    }
    
}


