package gerenciamentodefrota.infra.security;

import gerenciamentodefrota.model.enums.Perfil;

import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
public @interface Permission {
	
	Perfil[] value();
	
}