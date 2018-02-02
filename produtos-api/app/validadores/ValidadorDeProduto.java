package validadores;

import javax.inject.Inject;

import daos.ProdutoDao;
import models.Produto;
import play.data.Form;
import play.data.validation.ValidationError;

public class ValidadorDeProduto {
	
	@Inject
	private ProdutoDao produtoDao;
	
	public boolean temErros(Form<Produto> formulario) {
		Produto produto = formulario.get();
		if(produto.getPreco() < 0.0) {
			formulario.reject(new ValidationError("preco", "Informe um preço maior ou igual a zero"));
		}
		if(produtoDao.comCodigo(produto.getCodigo()).isPresent()) {
			formulario.reject(new ValidationError("codigo", "O produto informado já existe"));
		}
		return formulario.hasErrors();
	}

}
