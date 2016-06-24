package br.ufg.inf.fs.slum.usuario.bus;

import br.ufg.inf.fs.slum.bus.ParentBO;
import br.ufg.inf.fs.slum.bus.RegraNegocioException;
import br.ufg.inf.fs.slum.usuario.Usuario;
import br.ufg.inf.fs.slum.util.HibernateUtil;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.List;

/**
 * Created by lucas.campos on 6/22/2016.
 */
public class LoginBO  extends ParentBO<Usuario> {

    final static Logger logger = Logger.getLogger(LoginBO.class);

    private static LoginBO instance;

    public static LoginBO getInstance(){
        if(instance== null){
            instance = new LoginBO();
        }
        return instance;

    }

    public Usuario autentificaUsuario(Usuario usuario) throws RegraNegocioException {
        RegraNegocioException regraNegocioException = new RegraNegocioException();

        HashMap<String, Object> mapProperties = new HashMap<String,Object>();
        mapProperties.put("username", usuario.getUsername());
        mapProperties.put("password", UsuarioBO.gerarPassword(usuario.getPassword()));

        usuario = (Usuario) HibernateUtil.getFieldEqUnique(Usuario.class, mapProperties);

        if(usuario == null){
            regraNegocioException.addmensagem("Username ou senha incorretos", RegraNegocioException.TipoMensagem.ALERT_ERROR);
        }

        regraNegocioException.lancar();

        return usuario;
    }

    public void incluirUsuario(Usuario usuario) throws RegraNegocioException {
        usuario.setPassword(UsuarioBO.gerarPassword(usuario.getPassword()));

        super.incluir(usuario);
    }

    @Override
    public void beforeInsert(Usuario usuario) throws RegraNegocioException {
        RegraNegocioException regraNegocioException = new RegraNegocioException();
        HashMap<String, Object> mapProperties = new HashMap<String,Object>();

        mapProperties.put("username",usuario.getUsername());

        List<Usuario> usuarios = HibernateUtil.getFieldEq(Usuario.class,mapProperties );

        if(usuarios.size() >= 1){
            regraNegocioException.addmensagem("Já existe um usuário com este username", RegraNegocioException.TipoMensagem.ALERT_ERROR);
        }

        regraNegocioException.lancar();
    }

    @Override
    public void beforeUpdate(Usuario persistivel) {

    }

}
