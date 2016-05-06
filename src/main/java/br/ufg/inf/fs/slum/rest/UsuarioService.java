package br.ufg.inf.fs.slum.rest;

import br.ufg.inf.fs.slum.usuario.Usuario;

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

        Usuario usuario = new Usuario();

        response = Response.ok("").build();

        return response;
    }
}
