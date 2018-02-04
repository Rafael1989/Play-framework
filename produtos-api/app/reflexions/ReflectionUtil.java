package reflexions;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import models.Produto;

public class ReflectionUtil {
	
	public static List<String> getAtributos(Produto produto) {
		List<String> atributos = new ArrayList<>();
		Class<?> c = produto.getClass();
		for (Field f : c.getDeclaredFields()) {
			atributos.add(f.getName());
		}
		return atributos;
	}

}
