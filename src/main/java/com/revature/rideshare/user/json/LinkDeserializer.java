package com.revature.rideshare.user.json;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

public class LinkDeserializer<T extends Linkable> extends StdDeserializer<T> {
	private static final long serialVersionUID = 1L;

	private LinkResolver<T> resolver;

	public LinkDeserializer(JavaType valueType, LinkResolver<T> resolver) {
		super(valueType);

		this.resolver = resolver;
	}

	@Override
	public T deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		return resolver.resolve(p.getValueAsString());
	}
}
