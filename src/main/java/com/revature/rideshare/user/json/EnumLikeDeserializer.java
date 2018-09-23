package com.revature.rideshare.user.json;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

/**
 * A {@link JsonDeserializer} that resolves enum-like values in JSON input, converting them
 * to the corresponding enum-like objects.
 * 
 * @param <T> the type to which to deserialize
 */
public class EnumLikeDeserializer<T extends EnumLike> extends StdDeserializer<T>{
	private static final long serialVersionUID = 1L;

	private EnumLikeResolver<T> resolver;

	public EnumLikeDeserializer(JavaType valueType, EnumLikeResolver<T> resolver) {
		super(valueType);
		this.resolver = resolver;
	}

	@Override
	public T deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		return resolver.resolve(p.getValueAsString());
	}
}
