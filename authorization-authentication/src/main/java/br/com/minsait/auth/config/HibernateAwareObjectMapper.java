package br.com.minsait.auth.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

/**
 * Configurar jackson.
 * 
 * @author Leonardo Bernardino
 *
 */
public class HibernateAwareObjectMapper extends ObjectMapper {

	private static final long serialVersionUID = 1L;

	public HibernateAwareObjectMapper() {

		registerModule(new JavaTimeModule());
		disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		setDefaultPropertyInclusion(JsonInclude.Include.NON_NULL);
		setDefaultPropertyInclusion(JsonInclude.Include.NON_EMPTY);
		configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}

}
