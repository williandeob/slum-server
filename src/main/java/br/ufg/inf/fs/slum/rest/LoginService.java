package br.ufg.inf.fs.slum.rest;

import br.ufg.inf.fs.slum.appToken.AppToken;
import br.ufg.inf.fs.slum.bus.RegraNegocioException;
import br.ufg.inf.fs.slum.usuario.Usuario;
import br.ufg.inf.fs.slum.usuario.bus.LoginBO;
import br.ufg.inf.fs.slum.util.HibernateUtil;
import com.sun.jersey.api.client.ClientResponse;
import org.json.JSONObject;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

/**
 * Created by lucas.campos on 6/22/2016.
 */

@Path("/loginService")
public class LoginService {

    LoginBO loginBO = LoginBO.getInstance();

    @Path("logar")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response logar(String jsonString) throws Exception {
        Response response = null;

        JSONObject jsonLogin = new JSONObject(jsonString);

        Usuario usuario =new Usuario();

        String username = jsonLogin.get("username").toString();
        String password = jsonLogin.get("password").toString();

        usuario.setUsername(username);
        usuario.setPassword(password);

        try{
            usuario = loginBO.autentificaUsuario(usuario);

            String token = getToken(username);

            JSONObject autenticacao = new JSONObject();
            autenticacao.put("token", token);
            autenticacao.put("login", username);

            JSONObject usuarioJson = new JSONObject();
            usuarioJson.put("id", usuario.getId());
            usuarioJson.put("nome", usuario.getNome());
            usuarioJson.put("email", usuario.getEmail());

            JSONObject json = new JSONObject();
            json.put("autenticacao", autenticacao);
            json.put("usuario", usuarioJson);

            response = Response.ok(json.toString()).build();
        }catch (RegraNegocioException e){
            response = Response.status(ClientResponse.Status.BAD_REQUEST).entity(e.getMensagens()).build();
        }

        return response;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response registrar(String usuarioJson) throws Exception {
        Response response = null;

        Usuario usuario =new Usuario();
        usuario.populateFromStringJSON(usuarioJson);

        try {
            loginBO.incluirUsuario(usuario);
            response = Response.ok(usuario.toJSON().toString()).build();
        } catch (RegraNegocioException e) {
            response = Response.status(ClientResponse.Status.BAD_REQUEST).entity(e.getMensagens()).build();
        }

        return response;
    }

    private String getToken(String username) throws Exception {
        HashMap<String, Object> mapProperties = new HashMap<String,Object>();
        mapProperties.put("username", username);

        AppToken appToken = (AppToken) HibernateUtil.getFieldEqUnique(AppToken.class,mapProperties);
        if (appToken != null) {
            atualizarToken(appToken);
        } else {
            appToken = gerarNovoToken(username);
        }
        return appToken.getToken();
    }

    private AppToken gerarNovoToken(String username) throws Exception {
        AppToken appToken;
        appToken = new AppToken();
        appToken.setDataCriacao(new Date());
        appToken.setUsername(username);
        appToken.setToken(UUID.randomUUID().toString());
        HibernateUtil.saveOrUpdate(appToken);
        return appToken;
    }

    private void atualizarToken(AppToken appToken) throws Exception {
        appToken.setDataCriacao(new Date());
        appToken.setToken(UUID.randomUUID().toString());
        HibernateUtil.saveOrUpdate(appToken);
    }
}
