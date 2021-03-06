package controllers;


import javax.inject.Inject;

import autenticadores.AdminAutenticado;
import models.Produto;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security.Authenticated;
import validadores.ValidadorDeProduto;
import views.html.formulario;

@Authenticated(AdminAutenticado.class)
public class ProdutoController extends Controller{
	
	@Inject
	private FormFactory formularios;
	@Inject
	private ValidadorDeProduto validadorDeProduto;
	
	public Result salva() {
		Form<Produto> form = formularios.form(Produto.class).bindFromRequest();
		Produto produto = form.get();
		if(validadorDeProduto.temErros(form)) {
			flash("danger", "Há erros no envio do formulário");
			return badRequest(formulario.render(form));
		}
		produto.save();
		flash("success","Produto " + produto.getTitulo() + " cadastrado com sucesso");
		return redirect(routes.ProdutoController.formulario());
	}
	
	public Result formulario() {
		Produto produto = new Produto();
		produto.setTipo("livro");
		Form<Produto> form = formularios.form(Produto.class).fill(produto);
		return ok(formulario.render(form));
	}

}
