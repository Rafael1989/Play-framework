package autenticadores;

import java.util.Optional;

import javax.inject.Inject;

import controllers.UsuarioController;
import controllers.routes;
import daos.UsuarioDao;
import models.Usuario;
import play.mvc.Http.Context;
import play.mvc.Result;
import play.mvc.Security.Authenticator;

public class UsuarioAutenticado extends Authenticator{
	
	@Inject 
	private UsuarioDao usuarioDao;
	
	@Override
	public String getUsername(Context context) {
		String codigo = context.session().get(UsuarioController.AUTH);
		Optional<Usuario> possivelUsuario = usuarioDao.comToken(codigo);
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
