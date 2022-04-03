
package com.servicios.impl;



import com.entidades.TblUsuarios;
import java.util.List;
import javax.ejb.Remote;
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
import java.math.BigInteger;


/**
 *
 * @author User - PC
 */
@Remote
public interface IservicioAplicacion 
{
    
    public List<TblUsuarios> obtenerUsrActivos() throws Exception;

    public String obtenerNombreUsuarioSesion(String usuario);

    public TblUsuarios Autenticar(String pUsuario, String pClave);

    public void actualizaUsuarios(TblUsuarios usuarios);
    
    public void actualizaEstadoUsuario(TblUsuarios usuarios);

    public List<TblRol> listaRolesAsignar();

    public Long añadirUsuarios(TblUsuarios usuarios);

    public void añadirUsuariosRoles(TblUsuariosRoles usuariosRoles);

    public void añadirRol(TblRol rol);

    public void asignarUsuarioRoles(List<TblRol> roles, TblUsuarios usuario) throws Throwable;

    public List<TblRol> listaRol(String rol);
    
    public TblRol obtieneRolxId(Long rolId); 

    public List<TblUsuarios> obtenerUsuario(String usuario) throws Exception;

    public String obtenerRuta(String param);

    public TblUsuarios resetPassword(String identificacion);

    public String obtenerParametro(String param);

    public void eliminaUsuarioRol(TblUsuariosRoles usuariosRoles);

    public void eliminaUsuario(TblUsuarios usuarios);

    public Long obtieneRolUsuario(Long id_usuario);

    public TblUsuarios ValidaUsuario(String pUsuario);

    public void enviaMail(String usuario, String correo, String plantilla, String referencia);

    public BigInteger cantidadUsuarios();

    public List<TblPeriodo> obtienePeriodo();

    public List<TblCurso> obtieneCursos(String curso);

    public List<TblParalelo> obtieneParalelos(String paralelo);

    public void ingresaPeriodo(TblCursoparalelo periodo);


    public List<DocenteList> consultaDocenteCurso(String identificacion, String nombres, String apellidos);


    public void ingresaDocenteCurso(TblDocentecurso docentecurso);

    public List<EstudianteList> consultaEstudianteCurso(String identificacion, String nombres, String apellidos);

    public Long ingresaEstudiante(TblEstudiante estudiante);

    public void ingresaEstudianteCurso(TblEstudiantecurso estudiantecurso);

    public List<CursoAsignadoList> consultaPeriodoCursoDocente(Long periodo_cod, Long curso_cod, Long paralelo_cod, String estado);

    public List<CursoAsignadoList> consultaPeriodoCursoEstudiante(Long periodo_cod, Long curso_cod, Long paralelo_cod, Long estudiante_cod, String estado);

    public List<CursoAsignadoList> consultaPeriodoCurso(Long periodo_cod, Long curso_cod, Long paralelo_cod, String estado);

    public List<UnidadList> consultaUnidadCurso(Long usuario_cod, Long unidad_id, String nombre, String descripcion);
    
    public Long ingresaUnidad(TblUnidad unidad);

    public List<ActividadList> consultaActividadCurso(String actividad, String pregunta);

    public Long ingresaActividad(TblActividad actividad);

    public Long ingresaRespuesta(TblRespuesta respuesta);

    public List<TblDestreza> obtieneDestreza();

    public List<TblSubtema> consultaSubtemaUnidad(Long cursoparalelo_cod, Long unidad_id, String estado);

    public Long ingresaTaller(TblPresentaciontaller taller);

    public void ingresaParalelo(TblParalelo paralelo);

    public boolean eliminaRespuesta(Long actividad_id);

    public boolean eliminaActividad(Long actividad_id);

    public List<TallerList> consultaTaller( Long usuarioCod, Long destrezaId, Long subtemaId );

    public void ingresaResultadoTaller(TblResultadotaller resultado);

    public void eliminaResultadoTaller(TblResultadotaller resultado);

    public List<TblResultadotaller> obtenerResultadoTaller();

    public void ingresaResultado(TblResultado resultado);

    public void eliminaResultado(TblResultado resultado);

    public List<TblResultado> obtenerResultados();

    public TblResultado obtieneResultadosporUnidad(Long usuario_cod, Long unidad_id, Long subtema_id);

    public List<PresentaTallerList> listaTallerRegistrado(Long usuarioCod, String taller_nombre, String taller_descripcion);

    public boolean cierraTaller(Long tallerId);

    public BigInteger cantidadTallerAbierto(Long usuario_cod, Long unidad_Id);

    public void inactivaResultadoTaller(Long tallerId,  Long subtemaId);

    public BigInteger obtenerParametroNum(String paramId);

    public List<UnidadList> consultaUnidadResultado(Long usuario_cod);

    public List<ResultadoTallerList> consultaResultadoxUnidad(Long usuario_cod, Long unidad_id);

    public List<ResultadoTallerList> consultaResultadoEstudiantes(Long usuario_cod, Long unidad_id);

    public List<UnidadList> consultaUnidadRegistrada(Long unidad_id);

    public Long ingresaVocabulario(TblVocabulario vocabulario);

    public List<VocabularioList> consultaVocabulario(Long unidad_id);

    public boolean eliminaVocabulario(Long vocabulario_id);

    public List<UnidadList> consultaUnidad(Long usuario_cod, Long unidad_id, String estado);

    public List<UnidadList> consultaUnidadporDestreza(Long destrezaId, Long usuarioCod);

    public Long ingresaTempActiv(TblTempactiv tmp_activ);

    public boolean eliminaTempActiv(Long usuario_cod, String tipo);

    public List<TblTempactiv> consultaTmpActividad(Long usuario_cod, String tipo);

    public List<TallerList> consultaActividad(Long destrezaId, Long unidadId, Long subtemaId);

    public void ingresarPeriodo(TblPeriodo periodo);

    public List<TblPeriodo> obtenerPeriodo(String estado);

    public void actualizaPeriodo(TblPeriodo periodo);

    public void eliminaPeriodo(TblPeriodo periodo);

    public void actualizaCurso(TblCurso curso);

    public void ingresarCurso(TblCurso curso);

    public List<TblCurso> obtenerCurso(String estado);

    public void eliminaCurso(TblCurso curso);

    public List<TblParalelo> obtenerParalelo(String estado);

    public void actualizaParalelo(TblParalelo paralelo);

    public void eliminaParalelo(TblParalelo paralelo);

    public void actualizaDocente(TblDocente docente);

    public void eliminaDocente(TblDocente docente);

    public void ingresarDocente(TblDocente docente);

    public List<TblDocente> obtenerDocente();

    public Long ingresaDocente(TblDocente docente);

    public List<TblEstudiante> obtenerEstudiante();

    public void ingresarEstudiante(TblEstudiante estudiante);

    public void actualizaEstudiante(TblEstudiante estudiante);

    public void eliminaEstudiante(TblEstudiante estudiante);

    public void actualizaCursoAsignado(CursoAsignadoList cursoAsignado);

    public void actualizaCursoAsignadoDocente(CursoAsignadoList cursoAsignado);

    public void actualizaCursoAsignadoEstudiante(CursoAsignadoList cursoAsignado);

    public int cantidadPeriodo(String periodo);

    public String obtieneRolxUsuario(String usuario);

    public void actualizaVocabulario(TblVocabulario vocabulario);

    public Long ingresaSubtema(TblSubtema subtema);

    public void actualizaSubtema(TblSubtema subtema);

    public boolean eliminaSubtema(Long subtemaId);

    public void actualizaUnidad(TblUnidad unidad);

    public List<TblCurso> consultaCursosAsignados(Long periodo_cod);

    public List<ResultadoTallerList> consultaResultadoxDestreza(Long usuario_cod, Long destreza_id);

    public List<DocenteList> consultaEstudiantesxDocente(Long periodo_cod, Long curso_cod, Long docente_cod, Long paralelo_cod);

    public void reestableceSecuencia(String entidad);

    public void ingresaDestrezaUnidad(TblDestrezaunidad destrezaUnidad);
    

}
