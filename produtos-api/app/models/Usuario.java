package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.avaje.ebean.Model;

import play.data.validation.Constraints.Required;

@Entity
public class Usuario extends Model {

	@Id
	@GeneratedValue
	private Integer id;

	@Required(message = "O campo nome é obrigatório")
	private String nome;
	
	@Required(message = "O campo email é obrigatório")
	private String email;
	
	@Required(message = "O campo senha é obrigatório")
	private String senha;
	
	private Boolean verificado;
	
	@OneToOne(mappedBy="usuario")
	private TokenDaApi token;
	
	@OneToMany(mappedBy = "usuario")
	private List<RegistroDeAcesso> acessos;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Boolean getVerificado() {
		return verificado;
	}

	public void setVerificado(Boolean verificado) {
		this.verificado = verificado;
	}
	
	public TokenDaApi getToken() {
		return token;
	}
	
	public void setToken(TokenDaApi token) {
		this.token = token;
	}

	public List<RegistroDeAcesso> getAcessos() {
		return acessos;
	}

	public void setAcessos(List<RegistroDeAcesso> acessos) {
		this.acessos = acessos;
	}

}
