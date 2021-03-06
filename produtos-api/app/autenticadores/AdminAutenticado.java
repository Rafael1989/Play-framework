package autenticadores;

import java.util.Optional;

import javax.inject.Inject;

import controllers.UsuarioController;
import daos.UsuarioDao;
import controllers.routes;
import models.Usuario;
import play.mvc.Result;
import play.mvc.Http.Context;
import play.mvc.Security.Authenticator;

public class AdminAutenticado extends Authenticator{
	
	@Inject
	private UsuarioDao usuarioDao;
	
	@Override
	public String getUsername(Context context) {
		String codigo = context.session().get(UsuarioController.AUTH); 
		Optional<Usuario> possivelUsuario = usuarioDao.comToken(codigo);
		if(possivelUsuario.isPresent()) {
			Usuario usuario = possivelUsuario.get();
			if(usuario.isAdmin()) {
				context.args.put("usuario", usuario);
				return usuario.getNome();
			}
		}
		return null;
	}
	
	@Override
	public Result onUnauthorized(Context context) {
		return redirect(routes.UsuarioController.painel());
	}

}
