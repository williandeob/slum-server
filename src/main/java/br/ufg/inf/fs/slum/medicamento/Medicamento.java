/**
 * 
 */
package br.ufg.inf.fs.slum.medicamento;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import org.json.JSONObject;

import br.ufg.inf.fs.slum.usuario.Usuario;
import br.ufg.inf.fs.slum.util.Jsonable;
import br.ufg.inf.fs.slum.util.Persistivel;

/**
 * @author Ademar
 *
 */
@Entity
@Table(name = "MEDICAMENTO")
public class Medicamento implements Serializable,Persistivel,Jsonable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false, precision = 5, scale = 0)
	private Long id;
	
	@Column(name = "nome", nullable = false, length = 255)
	private String nome;

    @Column(name = "descricao", nullable = false, length = 3000)
    private String descricao;

    @Column(name = "datainicio", nullable = false)
    private LocalDateTime dataInicio;

    @Column(name = "intervalo", nullable = false)
    private Integer intervalo;
    
    @JoinColumn(name = "usuario", nullable = false)
    private Usuario usuario;

	public Medicamento() {	}
	
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



	public String getDescricao() {
		return descricao;
	}



	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}



	public LocalDateTime getDataInicio() {
		return dataInicio;
	}



	public void setDataInicio(LocalDateTime dataInicio) {
		this.dataInicio = dataInicio;
	}



	public Integer getIntervalo() {
		return intervalo;
	}



	public void setIntervalo(Integer intervalo) {
		this.intervalo = intervalo;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@Override
    public JSONObject toJSON() throws Exception {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("id", id);
        jsonObject.put("nome", nome);
        jsonObject.put("descricao", descricao);
        jsonObject.put("dataInicio", dataInicio);
        jsonObject.put("intervalo", intervalo);
        jsonObject.put("usuarioId", usuario.getId());

        return jsonObject;
    }

    @Override
    public void populateFromStringJSON(String jsonString) {
        JSONObject json = new JSONObject(jsonString);

        setId(json.getLong("id"));
        setNome(json.getString("nome"));
        setDescricao(json.getString("descricao"));
        setDataInicio(LocalDateTime.parse(json.getString("dataInicio")));
        setIntervalo(json.getInt("intervalo"));
        setUsuario(new Usuario(json.getLong("usuarioId")));
    }
}
