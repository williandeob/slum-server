package br.ufg.inf.fs.slum.rest;

import br.ufg.inf.fs.slum.usuario.Usuario;
import org.json.JSONObject;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by lucas.campos on 5/5/2016.
 */


@Path("/usuario")
public class UsuarioService {

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response registrar(String usuarioJson){
        Response response;

        Usuario usuario = populateUsuarioFromJson(usuarioJson);

        response = Response.ok("").build();

        return response;
    }

    private Usuario populateUsuarioFromJson(String usuarioJson){
        Usuario usuario = new Usuario();

        JSONObject json = new JSONObject(usuarioJson);

        usuario.setNome(json.get("username").toString());

        return usuario;
    }
}

