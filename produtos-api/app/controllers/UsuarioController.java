package controllers;

import java.util.Optional;

import javax.inject.Inject;

import akka.util.Crypt;
import daos.TokenDeCadastroDao;
import daos.UsuarioDao;
import models.EmailDeCadastro;
import models.TokenDeCadastro;
import models.Usuario;
import play.data.Form;
import play.data.FormFactory;
import play.libs.mailer.MailerClient;
import play.mvc.Controller;
import play.mvc.Result;
import validadores.ValidadorDeUsuario;
import views.html.formularioUsuario;

public class UsuarioController extends Controller{
	
	@Inject
	private FormFactory formularios;
	
	@Inject
	private ValidadorDeUsuario validadorDeUsuario;
	
	@Inject
	private MailerClient enviador;
	
	@Inject
	private UsuarioDao usuarioDao;
	
	@Inject 
	private TokenDeCadastroDao tokenDeCadastroDao;
	
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
		TokenDeCadastro token = new TokenDeCadastro(usuario);
		token.save();
		enviador.send(new EmailDeCadastro(token));
		flash("success","Um email foi enviado para você confirmar o cadastro");
		return redirect("/login");
	}
	
	public Result confirmaCadastro(String email, String codigo) {
		Optional<TokenDeCadastro> possivelToken = tokenDeCadastroDao.comCodigo(codigo);
		Optional<Usuario> possivelUsuario = usuarioDao.comEmail(email);
		if(possivelToken.isPresent() && possivelUsuario.isPresent()) {
			TokenDeCadastro tokenDeCadastro = possivelToken.get();
			Usuario usuario = possivelUsuario.get();
			if(tokenDeCadastro.getUsuario().equals(usuario)) {
				tokenDeCadastro.delete();
				usuario.setVerificado(true);
				usuario.update();
				flash("success","Seu usuário foi confirmado com sucesso");
				return redirect("/usuario/painel");
			}
		}
		flash("danger","Algo deu errado ao confirmar seu cadastro");
		return redirect("/login");
	}

}
