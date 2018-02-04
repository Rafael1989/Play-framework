package validadores;

import javax.inject.Inject;

import daos.UsuarioDao;
import models.Usuario;
import play.data.Form;
import play.data.validation.ValidationError;

public class ValidadorDeUsuario {
	
	@Inject
	private UsuarioDao usuarioDao;

	public boolean temErros(Form<Usuario> form) {
		validaEmail(form);
		validaSenha(form);
		return form.hasErrors();
	}

	private void validaSenha(Form<Usuario> form) {
		String senha = form.field("senha").valueOr("");
		String confirmaSenha = form.field("confirmaSenha").valueOr("");
		if(confirmaSenha.isEmpty()) {
			form.reject(new ValidationError("confirmaSenha", "Favor confirmar a senha"));
		}else if(!senha.equals(confirmaSenha)) {
			form.reject(new ValidationError("senha", ""));
			form.reject(new ValidationError("confirmaSenha", "As senha devem ser iguais"));
		}
	}

	private void validaEmail(Form<Usuario> form) {
		Usuario usuario = form.get();
		if(usuarioDao.comEmail(usuario.getEmail()).isPresent()) {
			form.reject(new ValidationError("email", "Esse email já foi cadastrado"));
		}
	}

}
