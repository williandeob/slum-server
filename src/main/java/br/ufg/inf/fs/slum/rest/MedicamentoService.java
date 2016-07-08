package br.ufg.inf.fs.slum.rest;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
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
}

