package daos;

import java.util.Optional;

import com.avaje.ebean.Finder;

import models.TokenDeCadastro;

public class TokenDeCadastroDao {

	private Finder<Long, TokenDeCadastro> tokens = new Finder<>(TokenDeCadastro.class);
	
	public Optional<TokenDeCadastro> comCodigo(String codigo) {
		TokenDeCadastro tokenDeCadastro = tokens.query().where().eq("codigo", codigo).findUnique();
		return Optional.ofNullable(tokenDeCadastro);
	}
	
	

}
