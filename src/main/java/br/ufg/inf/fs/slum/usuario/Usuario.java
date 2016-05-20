/**
 * 
 */
package br.ufg.inf.fs.slum.usuario;

import br.ufg.inf.fs.slum.util.Jsonable;
import br.ufg.inf.fs.slum.util.Persistivel;
import org.json.JSONObject;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author Ademar
 *
 */
@Entity
@Table(name = "USUARIO")
public class Usuario implements Serializable,Persistivel,Jsonable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false, precision = 5, scale = 0)
	private Long id;
	
	@Column(name = "nome", nullable = false, length = 255)
	private String nome;

    @Column(name = "email", nullable = false, length = 255)
    private String email;

    @Column(name = "username", nullable = false, length = 255)
    private String username;

    @Column(name = "password", nullable = false, length = 255)
    private String password;

	public Usuario() {	}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public JSONObject toJSON() throws Exception {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("username" ,username);
        jsonObject.put("nome" ,nome);
        jsonObject.put("email" ,email);

        return jsonObject;
    }

    @Override
    public void populateFromStringJSON(String jsonString) {
        JSONObject json = new JSONObject(jsonString);

        setUsername(json.get("username").toString());
        setNome(json.get("nome").toString());
        setEmail(json.get("email").toString());
        setPassword(json.get("password").toString());
    }
}
