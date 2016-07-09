package br.ufg.inf.fs.slum.rest;

import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.sun.jersey.api.client.ClientResponse;

import br.ufg.inf.fs.slum.bus.RegraNegocioException;
import br.ufg.inf.fs.slum.medicamento.Medicamento;
import br.ufg.inf.fs.slum.usuario.bus.MedicamentoBO;

/**
 * @author Ademar
 *
 */
@Path("/medicamento")
public class MedicamentoService {

    MedicamentoBO medicamentoBO = MedicamentoBO.getInstance();

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response incluir(String medicamentoJson) throws Exception {
        Response response = null;

        Medicamento medicamento = new Medicamento();
        medicamento.populateFromStringJSON(medicamentoJson);

        try {
        	medicamentoBO.incluir(medicamento);
            response = Response.ok(medicamento.toJSON().toString()).build();
        } catch (RegraNegocioException e) {
            response = Response.status(ClientResponse.Status.BAD_REQUEST).entity(e.getMensagens()).build();
        }

        return response;
    }
    
    @PUT
    @Path("alterar")
    @Produces(MediaType.APPLICATION_JSON)
    public Response alterar(String medicamentoJson) throws Exception {
        Response response = null;

        Medicamento medicamento = new Medicamento();
        medicamento.populateFromStringJSON(medicamentoJson);

        try {
        	medicamentoBO.alterar(medicamento);
            response = Response.ok(medicamento.toJSON().toString()).build();
        } catch (RegraNegocioException e) {
            response = Response.status(ClientResponse.Status.BAD_REQUEST).entity(e.getMensagens()).build();
        }

        return response;
    }
    
    /**
     * @deprecated use {@link #excluir()} instead.  
     */
    @Deprecated
    @Path("deletar")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response deletar(String medicamentoJson) throws Exception {
        Response response = null;

        Medicamento medicamento = new Medicamento();
        medicamento.populateFromStringJSON(medicamentoJson);

        medicamento = (Medicamento) medicamentoBO.consultar(Medicamento.class, medicamento.getId());
        
        try {
        	medicamentoBO.deletar(medicamento);
            response = Response.ok("medicamento excluído").build();
        } catch (Exception e) {
            response = Response.status(ClientResponse.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }

        return response;
    }
    
    @Path("excluir/{id}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response excluir(@PathParam("id") Long id) throws Exception {
        Response response = null;

        Medicamento medicamento = medicamentoBO.consultar(id);
        
        try {
        	medicamentoBO.deletar(medicamento);
            response = Response.ok("medicamento excluído").build();
        } catch (Exception e) {
            response = Response.status(ClientResponse.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }

        return response;
    }
}

