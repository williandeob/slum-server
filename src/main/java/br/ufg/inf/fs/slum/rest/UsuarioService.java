package br.ufg.inf.fs.slum.rest;

import br.ufg.inf.fs.slum.bus.RegraNegocioException;
import br.ufg.inf.fs.slum.usuario.Usuario;
import br.ufg.inf.fs.slum.usuario.bus.UsuarioBO;
import com.sun.jersey.api.client.ClientResponse;

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

    UsuarioBO usuarioBO = UsuarioBO.getInstance();

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response registrar(String usuarioJson) throws Exception {
        Response response = null;

        Usuario usuario =new Usuario();
        usuario.populateFromStringJSON(usuarioJson);

        try {
            usuarioBO.incluir(usuario);
            response = Response.ok(usuario.toJSON().toString()).build();
        } catch (RegraNegocioException e) {
            response = Response.status(ClientResponse.Status.BAD_REQUEST).entity(e.getMensagens()).build();
        }

        return response;
    }
}

