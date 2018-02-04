package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.avaje.ebean.Model;

import akka.util.Crypt;

@Entity
public class TokenDeCadastro extends Model {

	@Id
	@GeneratedValue
	private Integer id;

	@OneToOne
	private Usuario usuario;

	private String codigo;
	
	public TokenDeCadastro(Usuario usuario) {
		this.usuario = usuario;
		this.codigo = Crypt.sha1(usuario.getNome()+usuario.getEmail()+Crypt.generateSecureCookie());
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

}
