package br.ufg.inf.fs.slum.usuario.bus;

import org.apache.log4j.Logger;

import br.ufg.inf.fs.slum.bus.ParentBO;
import br.ufg.inf.fs.slum.bus.RegraNegocioException;
import br.ufg.inf.fs.slum.medicamento.Medicamento;

/**
 * @author Ademar
 *
 */
public class MedicamentoBO extends ParentBO<Medicamento> {

    final static Logger logger = Logger.getLogger(MedicamentoBO.class);

    private static MedicamentoBO instance;

    public static MedicamentoBO getInstance(){
        if(instance== null){
            instance = new MedicamentoBO();
        }
            return instance;

    }
    
    public Medicamento consultar(Long id) {
    	return (Medicamento) super.consultar(Medicamento.class, id);
    }

    @Override
    public void beforeInsert(Medicamento medicamento) throws RegraNegocioException {
    	
    }

    @Override
    public void beforeUpdate(Medicamento medicamento) {

    }

}
