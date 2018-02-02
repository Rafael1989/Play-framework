package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.avaje.ebean.Model;

import play.data.validation.Constraints.Required;

@Entity
public class Produto extends Model{
	@Id
	@GeneratedValue
	private Integer id;
	@Required(message = "O campo título é obrigatório")
	private String titulo;
	@Required(message = "O campo código é obrigatório")
	private String codigo;
	@Required(message = "O campo tipo é obrigatório")
	private String tipo;
	private String descricao;
	@Required(message = "O campo preço é obrigatório")
	private Double preco;

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}

}
