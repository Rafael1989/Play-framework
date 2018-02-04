package controllers;

import javax.inject.Inject;

import akka.util.Crypt;
import models.Usuario;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import validadores.ValidadorDeUsuario;
import views.html.formularioUsuario;

public class UsuarioController extends Controller{
	
	@Inject
	private FormFactory formularios;
	
	@Inject
	private ValidadorDeUsuario validadorDeUsuario;
	
	public Result formulario() {
		Form<Usuario> form = formularios.form(Usuario.class);
		return ok(formularioUsuario.render(form));
	}
	
	public Result salva() {
		Form<Usuario> form = formularios.form(Usuario.class).bindFromRequest();
		Usuario usuario = form.get();
		if(validadorDeUsuario.temErros(form)) {
			flash("danger","Há erros no formulário");
			return badRequest(formularioUsuario.render(form));
		}
		String criptSenha = Crypt.sha1(usuario.getSenha());
		usuario.setSenha(criptSenha);
		usuario.save();
		flash("success","Usuário cadastrado com sucesso");
		return redirect("/login");
	}

}
