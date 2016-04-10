/**
 * 
 */
package br.ufg.inf.fs.slum.usuario;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Ademar
 *
 */
@Entity
@Table(name = "USUARIO")
public class Usuario implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false, precision = 5, scale = 0)
	private Long id;
	
	@Column(name = "NOME", nullable = false, length = 255)
	private String nome;

	public Usuario() {
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

}
