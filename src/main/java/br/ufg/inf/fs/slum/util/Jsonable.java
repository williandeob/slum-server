package br.ufg.inf.fs.slum.util;

import org.json.JSONObject;

/**
 * Created by lucas.campos on 5/19/2016.
 */
public interface Jsonable {

    JSONObject toJSON() throws Exception;

    void populateFromStringJSON(String json);

}
