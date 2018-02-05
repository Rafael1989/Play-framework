package models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.avaje.ebean.Model;

import akka.util.Crypt;

@Entity
public class TokenDaApi extends Model{
	
	@Id
	@GeneratedValue
	private Integer id;
	
	@OneToOne
	private Usuario usuario;
	
	private String codigo;
	
	private Date expiracao;
	
	public TokenDaApi(Usuario usuario) {
		this.usuario = usuario;
		this.expiracao = new Date();
		this.codigo = Crypt.sha1(expiracao.toString()+usuario.toString()+Crypt.generateSecureCookie());
	}	
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getCodigo() {
		return codigo;
	}
	
	public Date getExpiracao() {
		return expiracao;
	}
	
	public void setExpiracao(Date expiracao) {
		this.expiracao = expiracao;
	}
	
	public Usuario getUsuario() {
		return usuario;
	}

}
