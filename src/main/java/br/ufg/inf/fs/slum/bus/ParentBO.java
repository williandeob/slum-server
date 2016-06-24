package br.ufg.inf.fs.slum.bus;

import br.ufg.inf.fs.slum.util.HibernateUtil;
import br.ufg.inf.fs.slum.util.Persistivel;

/**
 * Created by lucas.campos on 5/19/2016.
 */
public abstract class ParentBO<T extends Persistivel> {


    public void incluir(T persistivel) throws RegraNegocioException {
        beforeInsert(persistivel);
        HibernateUtil.saveOrUpdate(persistivel);
    }

    public void alterar(T persistivel){
        beforeUpdate(persistivel);
        HibernateUtil.saveOrUpdate(persistivel);
    }

    public void deletar(T persistivel){
        HibernateUtil.delete(persistivel);
    }

    public void consultar(Class clazz, Long id ){
        HibernateUtil.find(clazz,id);
    }

    public abstract void beforeInsert(T persistivel) throws RegraNegocioException;
    public abstract void beforeUpdate(T persistivel);
}
