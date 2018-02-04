package models;

import java.util.Optional;

import javax.inject.Inject;

import controllers.UsuarioController;
import controllers.routes;
import daos.UsuarioDao;
import play.mvc.Result;
import play.mvc.Http.Context;
import play.mvc.Security.Authenticator;

public class UsuarioAutenticado extends Authenticator{
	
	@Inject 
	private UsuarioDao usuarioDao;
	
	@Override
	public String getUsername(Context context) {
		String email = context.session().get(UsuarioController.AUTH);
		Optional<Usuario> possivelUsuario = usuarioDao.comEmail(email);
		if(possivelUsuario.isPresent()) {
			return possivelUsuario.get().getNome();
		}
		return null;
	}
	
	@Override
	public Result onUnauthorized(Context context) {
		context.flash().put("danger", "Não autorizado");
		return redirect(routes.UsuarioController.formularioLogin());
	}

}
