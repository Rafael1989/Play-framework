package controllers;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import com.fasterxml.jackson.databind.JsonNode;

import acoes.AcaoDeRegistroDeAcesso;
import autenticadores.AcessoDaApiAutenticado;
import daos.ProdutoDao;
import models.EnvelopeDeProdutos;
import models.Produto;
import play.data.DynamicForm;
import play.data.FormFactory;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security.Authenticated;
import play.mvc.With;
import validadores.ValidadorDeParametros;

@Authenticated(AcessoDaApiAutenticado.class)
@With(AcaoDeRegistroDeAcesso.class)
public class ApiController extends Controller{
	
	@Inject
	private ProdutoDao produtoDao;
	
	@Inject
	private ValidadorDeParametros validador;
	
	@Inject
	private FormFactory formularios;
	
	public Result todosOsProdutos() {
		List<Produto> produtos = produtoDao.todos();
		return ok(Json.toJson(new EnvelopeDeProdutos(produtos)));
	}
	
	public Result doTipo(String tipo) {
		List<Produto> produtos = produtoDao.doTipo(tipo);
		return ok(Json.toJson(new EnvelopeDeProdutos(produtos)));
	}
	
	public Result comFiltro() {
		DynamicForm formulario = formularios.form().bindFromRequest();
		validador.validaParametros(formulario,formularios);
		if(formulario.hasErrors()) {
			JsonNode erros = Json.newObject().set("erros", formulario.errorsAsJson());
			return badRequest(erros);
		}
		Map<String, String> parametros = formularios.form().bindFromRequest().data();
		EnvelopeDeProdutos envelopeDeProdutos = new EnvelopeDeProdutos(produtoDao.comFiltro(parametros));
		return ok(Json.toJson(envelopeDeProdutos));
	}

}
