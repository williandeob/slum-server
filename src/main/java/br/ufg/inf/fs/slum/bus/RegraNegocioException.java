package br.ufg.inf.fs.slum.bus;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by lucas.campos on 5/19/2016.
 */
public class RegraNegocioException extends Exception {

    private JSONArray mensagens;

    public RegraNegocioException(){
        mensagens = new JSONArray();
    }

    public static enum TipoMensagem{
        ALERTA_SUCESS("alert-success"),ALERT_WARNING("alert-warning"),ALERT_ERROR("alert-danger");

        private String tipo;

        TipoMensagem(String tipo){
            this.tipo = tipo;
        }

        public String getTipo() {
            return tipo;
        }
    }

    public void addmensagem(String mensagem,TipoMensagem tipoMensagem){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("mensagem",mensagem);
        jsonObject.put("tipo",tipoMensagem.getTipo());

        mensagens.put(jsonObject);
    }

    public void lancar() throws  RegraNegocioException{
        if(mensagens.length() > 0){
            throw this;
        }
    }

    public String getMensagens() {
        return mensagens.toString();
    }
}
