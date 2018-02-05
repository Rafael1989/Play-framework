package controllers;

import java.util.Optional;

import javax.inject.Inject;

import akka.util.Crypt;
import autenticadores.UsuarioAutenticado;
import daos.TokenDeCadastroDao;
import daos.UsuarioDao;
import models.EmailDeCadastro;
import models.TokenDaApi;
import models.TokenDeCadastro;
import models.Usuario;
import play.data.DynamicForm;
import play.data.Form;
import play.data.FormFactory;
import play.libs.mailer.MailerClient;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security.Authenticated;
import validadores.ValidadorDeUsuario;
import views.html.formularioLogin;
import views.html.formularioUsuario;
import views.html.painelUsuario;

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
	
	public static final String AUTH = "auth";
	
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
				TokenDaApi tokenDaApi = new TokenDaApi(usuario);
				tokenDaApi.save();
				usuario.setToken(tokenDaApi);
				usuario.update();
				flash("success","Seu usuário foi confirmado com sucesso");
				insereUsuarioNaSessao(usuario);
				return redirect(routes.UsuarioController.painel());
			}
		}
		flash("danger","Algo deu errado ao confirmar seu cadastro");
		return redirect(routes.UsuarioController.formularioLogin());
	}

	private void insereUsuarioNaSessao(Usuario usuario) {
		session(AUTH, usuario.getToken().getCodigo());
	}
	
	@Authenticated(UsuarioAutenticado.class)
	public Result painel() {
		String codigo = session(AUTH);
		Usuario usuario = usuarioDao.comToken(codigo).get();
		return ok(painelUsuario.render(usuario));
	}
	
	public Result formularioLogin() {
		return ok(formularioLogin.render(formularios.form()));
	}
	
	public Result fazLogin() {
		DynamicForm dynamicForm = formularios.form().bindFromRequest();
		String email = dynamicForm.get("email");
		String senhaCriptografada = Crypt.sha1(dynamicForm.get("senha"));
		Optional<Usuario> possivelUsuario = usuarioDao.comEmailESenha(email, senhaCriptografada);
		if(possivelUsuario.isPresent()) {
			Usuario usuario = possivelUsuario.get();
			if(usuario.getVerificado()) {
				insereUsuarioNaSessao(usuario);
				flash("success", "Login efetuado com sucesso");
				return redirect(routes.UsuarioController.painel());
			}else {
				flash("danger","Usuário não confirmou e-mail");
			}
		}else {
			flash("danger","Credenciais inválidas");
		}
		return redirect(routes.UsuarioController.formularioLogin());
	}
	
	@Authenticated(UsuarioAutenticado.class)
	public Result fazLogout() {
		session().clear();
		flash("success","Logout efetuado com sucesso");
		return redirect(routes.UsuarioController.formularioLogin());
	}

}
