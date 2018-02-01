package controllers;

import com.google.inject.Inject;

import models.Produto;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.formulario;

public class ProdutoController extends Controller{
	
	@Inject
	private FormFactory formularios;
	
	public Result salva() {
		Produto produto = formularios.form(Produto.class).bindFromRequest().get();
		produto.save();
		return redirect(routes.ProdutoController.formulario());
	}
	
	public Result formulario() {
		return ok(formulario.render("Cadastro de produto"));
	}

}
