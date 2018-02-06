package daos;

import java.util.List;
import java.util.Optional;

import com.avaje.ebean.Finder;

import models.Usuario;

public class UsuarioDao {
	
	private Finder<Long, Usuario> usuarios = new Finder<>(Usuario.class);

	public Optional<Usuario> comEmail(String email) {
		Usuario usuario = usuarios.query().where().eq("email", email).findUnique();
		return Optional.ofNullable(usuario);
	}
	
	public Optional<Usuario> comEmailESenha(String email, String senha){
		Usuario usuario = usuarios.query().where().eq("email", email).eq("senha", senha).findUnique();
		return Optional.ofNullable(usuario);
	}
	
	public Optional<Usuario> comToken(String codigo){
		Usuario usuario = usuarios.query().where().eq("token.codigo", codigo).findUnique();
		return Optional.ofNullable(usuario);
	}

	public List<Usuario> todos() {
		return usuarios.all();
	}

}
