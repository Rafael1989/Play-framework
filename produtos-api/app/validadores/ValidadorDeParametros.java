package validadores;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import models.Produto;
import play.data.DynamicForm;
import play.data.FormFactory;
import play.data.validation.ValidationError;
import reflexions.ReflectionUtil;

public class ValidadorDeParametros {
	
	@Inject
	private Produto produto;
	
	public void validaParametros(DynamicForm formulario,FormFactory formularios) {
		List<String> atributos = ReflectionUtil.getAtributos(produto);
		validaSeOsParametrosSaoAtributos(formulario, atributos);
	}

	public void validaSeOsParametrosSaoAtributos(DynamicForm formulario, List<String> atributos) {
		Map<String, String> parametros = formulario.data();
		parametros.keySet().forEach(chave -> {
			if(!atributos.contains(chave)) {
				formulario.reject(new ValidationError("Atributos inválidos", chave));
			}
		});
	}

	

}
