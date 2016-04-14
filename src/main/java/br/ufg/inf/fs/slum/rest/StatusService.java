/**
 * 
 */
package br.ufg.inf.fs.slum.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author Ademar
 *
 */

@Path("/statusService")
public class StatusService {
	
	@Path("getStatus")
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getStatus(
			@FormParam("json") String json, 
			@FormParam("outraKey") String outraKey) {
				
		Response response = null;
		
		//TODO usar alguma classe para construir o json mesmo.
		String jsonResposta = "{\"status\":\"ok\",\"json\":\"" + json + "\",\"outraKey\":\"" + outraKey + "\"}";
		response = Response.ok(jsonResposta).build();
		
		return response;
	}
}
