package com.revature.rideforce.user.json;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

/**
 * A {@link JsonDeserializer} that resolves links in JSON input, converting them
 * to the linked objects.
 * 
 * @param <T> the type to which to deserialize
 * @since Iteration1: 10/01/2018
 */
public class LinkDeserializer<T extends Linkable> extends StdDeserializer<T> {
	private static final long serialVersionUID = 1L;

	private transient LinkResolver<T> resolver;

	public LinkDeserializer(JavaType valueType, LinkResolver<T> resolver) {
		super(valueType);

		this.resolver = resolver;
	}

	@Override
	public T deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
		return resolver.resolve(p.getValueAsString());
	}
}
