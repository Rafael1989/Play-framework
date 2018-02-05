package autenticadores;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.inject.Inject;

import daos.UsuarioDao;
import models.Usuario;
import play.libs.Json;
import play.mvc.Result;
import play.mvc.Http.Context;
import play.mvc.Security.Authenticator;

public class AcessoDaApiAutenticado extends Authenticator{
	
	@Inject
	private UsuarioDao usuarioDao;
	
	@Override
	public String getUsername(Context context) {
		String codigo = context.request().getHeader("API-Token");
		Optional<Usuario> possivelUsuario = usuarioDao.comToken(codigo);
		if(possivelUsuario.isPresent()) {
			return possivelUsuario.get().getNome();
		}
		return null;
	}
	
	@Override
	public Result onUnauthorized(Context arg0) {
		Map<String, String> parametrosDoErro = new HashMap<>();
		parametrosDoErro.put("codigo","401");
		parametrosDoErro.put("mensagem", "Não autorizado!");
		Map<String, Object> erros = new HashMap<>();
		erros.put("errors", parametrosDoErro);
		return unauthorized(Json.toJson(erros));
	}

}
