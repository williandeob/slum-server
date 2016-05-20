package br.ufg.inf.fs.slum.usuario.bus;

import br.ufg.inf.fs.slum.bus.ParentBO;
import br.ufg.inf.fs.slum.bus.RegraNegocioException;
import br.ufg.inf.fs.slum.usuario.Usuario;
import br.ufg.inf.fs.slum.util.HibernateUtil;
import org.apache.log4j.Logger;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * Created by lucas.campos on 5/19/2016.
 */
public class UsuarioBO extends ParentBO<Usuario> {

    final static Logger logger = Logger.getLogger(UsuarioBO.class);

    private static UsuarioBO instance;

    public static final String SALT = "take-a-nap";

    public static UsuarioBO getInstance(){
        if(instance== null){
            instance = new UsuarioBO();
        }
            return instance;

    }

    public void incluir(Usuario usuario) throws RegraNegocioException {
        beforeInsert(usuario);

        super.incluir(usuario);
    }

    /* [1] Não pode existir um usuario com o mesmo username
     *
     */
    @Override
    public void beforeInsert(Usuario usuario) throws RegraNegocioException {
        RegraNegocioException regraNegocioException = new RegraNegocioException();
        List<Usuario> usuarios = HibernateUtil.getFieldEq(Usuario.class, "username", usuario.getUsername());

        if(usuarios.size() >= 1){
            regraNegocioException.addmensagem("Já existe um usuário com este username", RegraNegocioException.TipoMensagem.ALERT_ERROR);
        }

        regraNegocioException.lancar();

        usuario.setPassword(gerarPassword(usuario.getPassword()));
    }

    @Override
    public void beforeUpdate(Usuario persistivel) {

    }

    private String gerarPassword(String password){
        String saltedPassword = SALT + password;
        String hashedPassword = generateHash(saltedPassword);

        return hashedPassword;
    }

    public static String generateHash(String input) {
        StringBuilder hash = new StringBuilder();

        try {
            MessageDigest sha = MessageDigest.getInstance("SHA-1");
            byte[] hashedBytes = sha.digest(input.getBytes());
            char[] digits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                    'a', 'b', 'c', 'd', 'e', 'f' };

            for (int idx = 0; idx < hashedBytes.length; ++idx) {
                byte b = hashedBytes[idx];
                hash.append(digits[(b & 0xf0) >> 4]);
                hash.append(digits[b & 0x0f]);
            }
        } catch (NoSuchAlgorithmException e) {
            logger.error("Erro ao gerar hash password" + e);
        }

        return hash.toString();
    }

}
