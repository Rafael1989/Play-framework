package controllers;

import javax.inject.Inject;

import autenticadores.AdminAutenticado;
import daos.ProdutoDao;
import daos.UsuarioDao;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security.Authenticated;
import views.html.usuarios;
import views.html.produtos;

@Authenticated(AdminAutenticado.class)
public class AdminController extends Controller{
	
	@Inject
	private UsuarioDao usuarioDao;
	
	@Inject
	private ProdutoDao produtoDao;
	
	public Result usuarios() {
		return ok(usuarios.render(usuarioDao.todos()));
	}
	
	public Result produtos() {
		return ok(produtos.render(produtoDao.todos()));
	}

}
