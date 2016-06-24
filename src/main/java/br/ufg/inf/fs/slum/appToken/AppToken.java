package br.ufg.inf.fs.slum.appToken;

import br.ufg.inf.fs.slum.util.Persistivel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * Created by lucas.campos on 6/23/2016.
 */

@Entity
@Table(name = "APP_TOKEN")
public class AppToken implements Persistivel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_app_token")
    private Long id;

    @Column(name = "token", length = 36, nullable = false, unique = true)
    private String token;

    @Column(name = "username", length = 255, nullable = false, unique = true)
    private String username;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data_criacao",nullable = false)
    private Date dataCriacao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
}
