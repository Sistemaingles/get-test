
package com.servicios;




import com.servicios.eao.usuariosEAO;
import com.entidades.TblUsuarios;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import com.servicios.eao.rolEAO;
import com.servicios.eao.usuariosRolesEAO;
import com.entidades.TblRol;
import com.entidades.TblActividad;
import com.entidades.TblCurso;
import com.entidades.TblCursoparalelo;
import com.entidades.TblDestreza;
import com.entidades.TblDestrezaunidad;
import com.entidades.TblDocente;
import com.entidades.TblDocentecurso;
import com.entidades.TblEstudiante;
import com.entidades.TblEstudiantecurso;
import com.entidades.TblParalelo;
import com.entidades.TblPeriodo;
import com.entidades.TblPresentaciontaller;
import com.entidades.TblRespuesta;
import com.entidades.TblResultado;
import com.entidades.TblResultadotaller;
import com.entidades.TblSubtema;
import com.entidades.TblTempactiv;
import com.entidades.TblUnidad;
import com.entidades.TblVocabulario;
import com.entidades.TblUsuariosRoles;
import com.entidades.listas.ActividadList;
import com.entidades.listas.DocenteList;
import com.entidades.listas.EstudianteList;
import com.entidades.listas.CursoAsignadoList;
import com.entidades.listas.PresentaTallerList;
import com.entidades.listas.ResultadoTallerList;
import com.entidades.listas.TallerList;
import com.entidades.listas.UnidadList;
import com.entidades.listas.VocabularioList;
import com.servicios.eao.ActividadCursoEAO;
import com.servicios.eao.CursoEAO;
import com.servicios.eao.DestrezaEAO;
import com.servicios.eao.AsignaDocenteEAO;
import com.servicios.eao.DocenteEAO;
import com.servicios.eao.AsignaEstudianteEAO;
import com.servicios.eao.EstudianteEAO;
import com.servicios.eao.ParaleloEAO;
import com.servicios.eao.AsignaCursoEAO;
import com.servicios.eao.DestrezaUnidadEAO;
import com.servicios.eao.EstudiantexDocenteEAO;
import com.servicios.eao.PeriodoEAO;
import com.servicios.eao.SubtemaEAO;
import com.servicios.eao.TempActividadEAO;
import com.servicios.eao.UnidadCursoEAO;
import com.servicios.eao.VocabularioEAO;
import com.servicios.eao.enviaCorreoEAO;
import com.servicios.eao.indexEAO;
import com.servicios.eao.parametrosEAO;
import com.servicios.eao.presentatallerEAO;
import com.servicios.eao.respuestasEAO;
import com.servicios.eao.resultadoEAO;
import com.servicios.eao.resultadoTallerEao;
import com.servicios.eao.tallerEAO;
import com.servicios.impl.IservicioAplicacion;
import java.math.BigInteger;

/**
 *
 * @author AMORAN
 */
@Stateless
public class ServicioAplicacion  implements IservicioAplicacion
{
    
    @EJB
    private usuariosEAO usrEAO;
      
    @EJB
    private rolEAO roleao;
    
    @EJB
    private usuariosRolesEAO usuaRolesEAO;
    
    @EJB
    private parametrosEAO parametrosEAO;

    @EJB
    private enviaCorreoEAO          correoEAO;
        
    @EJB
    private indexEAO                indexEAO;
    
    @EJB
    private AsignaCursoEAO              periodoEAO;
    
    @EJB
    private PeriodoEAO            periodoEAO1;
    
    @EJB
    private AsignaDocenteEAO        docentecursoEAO;
    
    @EJB
    private DocenteEAO              docenteEAO;
    
    @EJB
    private EstudianteEAO           estudianteEAO;
    
    @EJB
    private AsignaEstudianteEAO     estudiantecursoEAO;
    
    @EJB
    private UnidadCursoEAO          unidadcursoEAO;
    
    @EJB
    private ActividadCursoEAO       actividadcursoEAO;
    
    @EJB
    private respuestasEAO           respuestaEAO;
    
    @EJB
    private DestrezaEAO             destrezaEAO;
    
    @EJB
    private SubtemaEAO              subtemaEAO;
    
    @EJB
    private tallerEAO               tallerEAO;
    
    @EJB
    private ParaleloEAO             paraleloEAO;
    
    @EJB
    private presentatallerEAO       presentaTallerEAO;
    
    @EJB
    private resultadoTallerEao      resultadoTallerEAO;
    
    @EJB
    private resultadoEAO            resultadoEAO;
    
    @EJB
    private VocabularioEAO            vocabularioEAO;
      
    @EJB
    private TempActividadEAO          tempActividadEAO;
    
    @EJB
    private CursoEAO          cursoEAO;
    
    @EJB
    private EstudiantexDocenteEAO estudiantexDocenteEAO;
    
    @EJB
    private DestrezaUnidadEAO destrezaUnidadEAO;
    
    @Override
    public List<TblUsuarios> obtenerUsrActivos() throws Exception
    {
       return usrEAO.obtenerUsrActivos();
    }
    
    @Override
    public String obtenerNombreUsuarioSesion(String usuario) 
    {
        return usrEAO.obtenerNombreUsuarioSesion(usuario);
    } 
    
    @Override
    public TblUsuarios Autenticar(String pUsuario, String pClave) 
    {
        return usrEAO.Autenticar(pUsuario, pClave);
    }
 
    @Override
    public void actualizaUsuarios(TblUsuarios usuarios)
    {
        usrEAO.actualizaUsuarios(usuarios);
    }
    
    @Override
    public void actualizaEstadoUsuario(TblUsuarios usuarios)
    {
        usrEAO.actualizaEstadoUsuario(usuarios);
    }
    @Override
    public List<TblRol> listaRolesAsignar()
    {
        return roleao.listaRolesAsignar();
    }
    
    @Override
    public Long añadirUsuarios(TblUsuarios usuarios)
    {
        return usrEAO.añadirUsuarios(usuarios);
    }
    
    @Override
    public void añadirUsuariosRoles(TblUsuariosRoles usuariosRoles)
    {
        usuaRolesEAO.añadirUsuariosRoles(usuariosRoles);
    }
    
    @Override
    public void añadirRol(TblRol rol)
    {
        roleao.añadirRol(rol);
    }
    
    @Override
    public void asignarUsuarioRoles(List<TblRol> roles, TblUsuarios usuario)throws Throwable 
    {
    }   
   
    @Override
    public List<TblRol> listaRol(String rol)
    {
        return  roleao.listaRol(rol);
    }
    
    @Override
    public TblRol obtieneRolxId(Long rolId)
    {
        return  roleao.obtieneRolxId(rolId);
    }
    
    @Override
    public String obtieneRolxUsuario(String usuario)
    {
        return  roleao.obtieneRolxUsuario(usuario);
    }
    
    @Override
    public List<TblUsuarios> obtenerUsuario(String usuario) throws Exception
    {
        return usrEAO.obtenerUsuario(usuario);
    } 
    
    @Override
    public BigInteger obtenerParametroNum(String paramId)
    {
        return parametrosEAO.obtenerParametroNum(paramId);
    }   
    
    @Override
    public String obtenerRuta(String param)
    {
        return parametrosEAO.obtenerRuta(param);
    }
    
    @Override
    public TblUsuarios resetPassword(String identificacion)
    {
        return usrEAO.resetPassword(identificacion);
    }
    
    @Override
    public TblUsuarios ValidaUsuario(String pUsuario)
    {
        return usrEAO.ValidaUsuario(pUsuario);
    }
    
    @Override
    public void eliminaUsuario(TblUsuarios usuarios)
    {
         usrEAO.eliminaUsuario(usuarios);
    }
    
    @Override
    public String obtenerParametro(String param)
    {
        return parametrosEAO.obtenerParametro(param);
    }


    @Override
    public void eliminaUsuarioRol(TblUsuariosRoles usuariosRoles)
    {
        usuaRolesEAO.eliminaUsuarioRol(usuariosRoles);
    }
    @Override
    public Long obtieneRolUsuario(Long id_usuario)
    {
        return usuaRolesEAO.obtieneRolUsuario(id_usuario);
    }


    @Override
    public void enviaMail(String usuario,String correo,String plantilla,String referencia) 
    {
        correoEAO.enviaMail(usuario, correo, plantilla, referencia);
    }
    

    
    @Override
    public BigInteger cantidadUsuarios()
    {
        return indexEAO.cantidadUsuarios();
    }
    
  
    @Override
    public List<TblPeriodo> obtienePeriodo()
    {
       return periodoEAO.obtienePeriodo(); 
    }
    
    @Override
    public List<TblCurso> obtieneCursos(String curso)
    {
       return periodoEAO.obtieneCursos(curso); 
    }
    
    
    @Override
    public List<TblParalelo> obtieneParalelos(String paralelo)
    {
       return periodoEAO.obtieneParalelos(paralelo); 
    }
    
    @Override
    public int cantidadPeriodo(String periodo){
        return periodoEAO1.cantidadPeriodo(periodo);
    }
    
    @Override
    public void ingresaPeriodo(TblCursoparalelo periodo)
    {
       periodoEAO.ingresaPeriodo(periodo);
    }
    
    @Override
    public List<TblPeriodo> obtenerPeriodo(String estado)
    {
       return periodoEAO1.obtenerPeriodo(estado);
    }
    
    @Override
    public void ingresarPeriodo(TblPeriodo periodo)
    {
       periodoEAO1.ingresarPeriodo(periodo);
    }
    
    @Override
    public void actualizaPeriodo(TblPeriodo periodo)
    {
	periodoEAO1.actualizaPeriodo(periodo);
    }
    @Override
    public void eliminaPeriodo(TblPeriodo periodo)
    {
       periodoEAO1.eliminaPeriodo(periodo); 
    }
    
    @Override
    public void reestableceSecuencia(String entidad)
    {
       periodoEAO1.reestableceSecuencia(entidad); 
    }
    
    @Override
    public List<TblDocente> obtenerDocente()
    {
       return docenteEAO.obtenerDocente();
    }
    
    @Override
    public Long ingresaDocente(TblDocente docente)
    {
       return docentecursoEAO.ingresaDocente(docente);
    }
    
    @Override
    public void actualizaDocente(TblDocente docente)
    {
	docenteEAO.actualizaDocente(docente);
    }
    
    @Override
    public void eliminaDocente(TblDocente docente)
    {
       docenteEAO.eliminaDocente(docente); 
    }
    
    @Override
    public List<TblEstudiante> obtenerEstudiante()
    {
       return estudianteEAO.obtenerEstudiante();
    }
    
    @Override
    public void ingresarEstudiante(TblEstudiante estudiante)
    {
        estudianteEAO.ingresarEstudiante(estudiante);
    }
    
    @Override
    public void actualizaEstudiante(TblEstudiante estudiante)
    {
	estudianteEAO.actualizaEstudiante(estudiante);
    }
    
    @Override
    public void eliminaEstudiante(TblEstudiante estudiante)
    {
       estudianteEAO.eliminaEstudiante(estudiante); 
    }
    
    @Override
    public List<CursoAsignadoList> consultaPeriodoCurso( Long periodo_cod, Long curso_cod, Long paralelo_cod, String estado)
    {
       return periodoEAO.consultaPeriodoCurso(periodo_cod , curso_cod, paralelo_cod, estado);
    }
    
    @Override
    public List<TblCurso> consultaCursosAsignados( Long periodo_cod)
    {
       return periodoEAO.consultaCursosAsignados(periodo_cod);
    }
    
    @Override
    public List<CursoAsignadoList> consultaPeriodoCursoDocente( Long periodo_cod, Long curso_cod, Long paralelo_cod, String estado)
    {
       return periodoEAO.consultaPeriodoCursoDocente(periodo_cod , curso_cod, paralelo_cod, estado);
    }
    
    @Override
    public List<CursoAsignadoList> consultaPeriodoCursoEstudiante( Long periodo_cod, Long curso_cod, Long paralelo_cod, Long estudiante_cod, String estado)
    {
       return periodoEAO.consultaPeriodoCursoEstudiante(periodo_cod , curso_cod, paralelo_cod, estudiante_cod, estado);
    }
    
    @Override
    public void actualizaCursoAsignado(CursoAsignadoList cursoAsignado)
    {
        periodoEAO.actualizaCursoAsignado(cursoAsignado);
    }
    
    @Override
    public void actualizaCursoAsignadoDocente(CursoAsignadoList cursoAsignado)
    {
        periodoEAO.actualizaCursoAsignadoDocente(cursoAsignado);
    }
    
    @Override
    public void actualizaCursoAsignadoEstudiante(CursoAsignadoList cursoAsignado)
    {
        periodoEAO.actualizaCursoAsignadoEstudiante(cursoAsignado);
    }
    
    @Override
    public List<DocenteList> consultaDocenteCurso( String identificacion, String nombres, String apellidos)
    {
        return docentecursoEAO.consultaDocenteCurso(identificacion, nombres , apellidos);
    }
    
    @Override
    public void ingresarDocente(TblDocente docente)
    {
        docenteEAO.ingresarDocente(docente);
    }
    
     @Override
    public void ingresaDocenteCurso(TblDocentecurso docentecurso)
    {
         docentecursoEAO.ingresaDocenteCurso(docentecurso);
    }  
    
    @Override
    public List<EstudianteList> consultaEstudianteCurso( String identificacion, String nombres, String apellidos)
    {
        return estudiantecursoEAO.consultaEstudianteCurso(identificacion, nombres , apellidos);
    }
    
    @Override
    public Long ingresaEstudiante(TblEstudiante estudiante)
    {
         return estudiantecursoEAO.ingresaEstudiante(estudiante);
    }
    
    @Override
    public void ingresaEstudianteCurso(TblEstudiantecurso estudiantecurso)
    {
         estudiantecursoEAO.ingresaEstudianteCurso(estudiantecurso);
    }  
    
    @Override
    public List<UnidadList> consultaUnidad( Long usuario_cod, Long unidad_id, String estado )
    {        
        return unidadcursoEAO.consultaUnidad(usuario_cod, unidad_id, estado);
    }
    
    @Override
    public List<UnidadList> consultaUnidadCurso( Long usuario_cod, Long unidad_id, String nombre , String descripcion)
    {        
        return unidadcursoEAO.consultaUnidadCurso( usuario_cod ,unidad_id, nombre , descripcion);
    }
    
    @Override
    public List<UnidadList> consultaUnidadRegistrada( Long unidad_id)
    {        
        return unidadcursoEAO.consultaUnidadRegistrada(unidad_id);
    }
    
    @Override   
    public List<UnidadList> consultaUnidadporDestreza( Long destrezaId, Long usuarioCod )
    {
         return unidadcursoEAO.consultaUnidadporDestreza(destrezaId, usuarioCod);
    }
    
    @Override
    public Long ingresaUnidad(TblUnidad unidad)
    {
         return unidadcursoEAO.ingresaUnidad(unidad);
    }
    
    @Override
    public void actualizaUnidad(TblUnidad unidad)
    {
        unidadcursoEAO.actualizaUnidad(unidad);
    }
    
    @Override
    public List<ActividadList> consultaActividadCurso( String actividad, String pregunta)
    {        
        return actividadcursoEAO.consultaActividadCurso( actividad,  pregunta);
    }
    
    @Override
    public List<TallerList> consultaActividad(  Long destrezaId, Long unidadId, Long subtemaId)
    {
        return actividadcursoEAO.consultaActividad( destrezaId,  unidadId, subtemaId);
    }
    
    @Override
    public List<PresentaTallerList> listaTallerRegistrado( Long usuarioCod, String taller_nombre, String taller_descripcion)
    { 
        return presentaTallerEAO.listaTallerRegistrado( usuarioCod , taller_nombre, taller_descripcion);
    }
    
    @Override
    public List<TallerList> consultaTaller( Long usuarioCod, Long destrezaId, Long subtemaId )
    {        
        return presentaTallerEAO.consultaTaller( usuarioCod, destrezaId, subtemaId);
    }
    
    @Override
    public Long ingresaActividad(TblActividad actividad)
    {
         return actividadcursoEAO.ingresaActividad(actividad);
    }
    
    @Override
    public boolean eliminaActividad(Long actividad_id)
    {
         return actividadcursoEAO.eliminaActividad(actividad_id);
    }
    
    @Override
    public Long ingresaRespuesta(TblRespuesta respuesta)
    {   
    
       return respuestaEAO.ingresaRespuesta(respuesta);
    }
    
    
    @Override
    public boolean eliminaRespuesta(Long actividad_id)
    {
        return respuestaEAO.eliminaRespuesta(actividad_id);
    }
        
    @Override
    public List<TblDestreza> obtieneDestreza()
    {
        return destrezaEAO.obtieneDestreza();
    }
    @Override
    public List<TblSubtema> consultaSubtemaUnidad( Long cursoparalelo_cod, Long unidad_id , String estado )
    {
        return subtemaEAO.consultaSubtemaUnidad(cursoparalelo_cod, unidad_id, estado);
    }
    
    @Override
    public Long ingresaSubtema(TblSubtema subtema)
    {
        return subtemaEAO.ingresaSubtema(subtema);
    } 
    
    @Override
    public void actualizaSubtema(TblSubtema subtema)
    {
         subtemaEAO.actualizaSubtema(subtema);
    }
    
    @Override
    public boolean eliminaSubtema(Long subtemaId)
    {
        return subtemaEAO.eliminaSubtema(subtemaId);
    } 
    
    @Override
    public BigInteger cantidadTallerAbierto(Long usuario_cod, Long unidad_Id)
    {
       return tallerEAO.cantidadTallerAbierto(usuario_cod, unidad_Id); 
    }
    
    @Override
    /*Metodo ingresa Taller si no existe*/
    public Long ingresaTaller(TblPresentaciontaller taller)
    {
        return tallerEAO.ingresaTaller(taller);
    }
    
    @Override
    public boolean cierraTaller(Long tallerId)
    {
        return tallerEAO.cierraTaller(tallerId);
    } 
    
    @Override
    public List<TblCurso> obtenerCurso(String estado)
    {
        return cursoEAO.obtenerCurso(estado);
    }
    
    @Override
    public void ingresarCurso(TblCurso curso)
    {
        cursoEAO.ingresarCurso(curso);
    }
    
    @Override
    public void actualizaCurso(TblCurso curso)
    {
        cursoEAO.actualizaCurso(curso);
    } 
    @Override
    public void eliminaCurso(TblCurso curso)
    {
        cursoEAO.eliminaCurso(curso);
    }
    
    @Override
    public void ingresaParalelo(TblParalelo paralelo)
    {
        paraleloEAO.ingresaParalelo(paralelo);
    }
    
    @Override
    public List<TblParalelo> obtenerParalelo(String estado)
    {
        return paraleloEAO.obtenerParalelo(estado);
    }
    
    @Override
    public void actualizaParalelo(TblParalelo paralelo)
    {
        paraleloEAO.actualizaParalelo(paralelo);
    }
    
    @Override
    public void eliminaParalelo(TblParalelo paralelo)
    {
        paraleloEAO.eliminaParalelo(paralelo);
    }
    
    @Override
    public void ingresaResultadoTaller(TblResultadotaller resultado)
    {
        resultadoTallerEAO.ingresaResultadoTaller(resultado);
    }
    
    @Override
    public void eliminaResultadoTaller(TblResultadotaller resultado)
    {
        resultadoTallerEAO.eliminaResultadoTaller(resultado);
    }
    
    @Override
    public void inactivaResultadoTaller(Long tallerId,  Long subtemaId)
    {
        resultadoTallerEAO.inactivaResultadoTaller(tallerId, subtemaId);
    }
     
    @Override
    public List<TblResultadotaller> obtenerResultadoTaller() 
    {
        return resultadoTallerEAO.obtenerResultadoTaller();
    }
    
    @Override
    public void ingresaResultado(TblResultado resultado)
    {
        resultadoEAO.ingresaResultado(resultado);
    }
    
    @Override
    public void eliminaResultado(TblResultado resultado)
    {
        resultadoEAO.eliminaResultado(resultado);
    }
    
    @Override      
    public List<TblResultado> obtenerResultados() 
    {
        return resultadoEAO.obtenerResultados();
    }
    
    @Override      
    public TblResultado obtieneResultadosporUnidad( Long usuario_cod, Long unidad_id, Long subtema_id) 
    {
        return resultadoEAO.obtieneResultadosporUnidad( usuario_cod, unidad_id , subtema_id);
    }
    
    @Override      
    public List<UnidadList> consultaUnidadResultado( Long usuario_cod)
    {
        return resultadoEAO.consultaUnidadResultado( usuario_cod);
    }
    
    @Override      
    public List<ResultadoTallerList> consultaResultadoxUnidad( Long usuario_cod, Long unidad_id)
    {
        return resultadoEAO.consultaResultadoxUnidad( usuario_cod, unidad_id);
    }
    
    @Override      
    public List<ResultadoTallerList> consultaResultadoxDestreza( Long usuario_cod, Long destreza_id)
    {
        return resultadoEAO.consultaResultadoxDestreza( usuario_cod, destreza_id);
    }
    
     /*Consulta Resultados de Talleres segun Codigo de Usuario del Docente */
    @Override
    public List<ResultadoTallerList> consultaResultadoEstudiantes( Long usuario_cod, Long unidad_id)
    {
        return resultadoEAO.consultaResultadoEstudiantes( usuario_cod, unidad_id);
    }
    
    @Override
    public Long ingresaVocabulario(TblVocabulario vocabulario)
    {
         return vocabularioEAO.ingresaVocabulario(vocabulario);
    }
    
    @Override
    public List<VocabularioList> consultaVocabulario( Long unidad_id)
    {
         return vocabularioEAO.consultaVocabulario(unidad_id);
    }
    
    @Override
    public void actualizaVocabulario(TblVocabulario vocabulario)
    {
          vocabularioEAO.actualizaVocabulario(vocabulario);
    }
    @Override
    public boolean eliminaVocabulario(Long vocabulario_id)
    {
        return vocabularioEAO.eliminaVocabulario(vocabulario_id);
    }
    
    @Override
    public Long ingresaTempActiv(TblTempactiv tmp_activ)
    {
         return tempActividadEAO.ingresaTempActiv( tmp_activ);
    }
    
    @Override
    public boolean eliminaTempActiv(Long usuario_cod, String tipo )
    {
         return tempActividadEAO.eliminaTempActiv( usuario_cod, tipo);
    }
    
     @Override
    public List<TblTempactiv> consultaTmpActividad( Long usuario_cod, String tipo  )
    {
         return tempActividadEAO.consultaTmpActividad( usuario_cod, tipo);
    }
    
    @Override
    public List<DocenteList> consultaEstudiantesxDocente( Long periodo_cod, Long curso_cod, Long docente_cod, Long paralelo_cod)
    {
         return estudiantexDocenteEAO.consultaEstudiantesxDocente( periodo_cod, curso_cod, docente_cod, paralelo_cod);
    }
    
    @Override
    /*Metodo ingresa Taller si no existe*/
    public void ingresaDestrezaUnidad(TblDestrezaunidad destrezaUnidad)
    {
        destrezaUnidadEAO.ingresaDestrezaUnidad(destrezaUnidad);
    } 
    
    
  }