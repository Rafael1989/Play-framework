package daos;

import java.util.Optional;

import com.avaje.ebean.Finder;

import models.Usuario;

public class UsuarioDao {
	
	private Finder<Long, Usuario> usuarios = new Finder<>(Usuario.class);

	public Optional<Usuario> comEmail(String email) {
		Usuario usuario = usuarios.query().where().eq("email", email).findUnique();
		return Optional.ofNullable(usuario);
	}

}
