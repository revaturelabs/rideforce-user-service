package com.revature.rideforce.user.json;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

/**
 * A {@link JsonDeserializer} that resolves enum-like values in JSON input,
 * converting them to the corresponding enum-like objects.
 * 
 * @param <T> the type to which to deserialize
 */
public class EnumLikeDeserializer<T extends EnumLike> extends StdDeserializer<T> {
	private static final long serialVersionUID = 1L;

	private transient EnumLikeResolver<T> resolver;

	
	/**Constructor for EnumLikeDeserializer class
	 * @param valueType <code>JavaType</code> for its super class' constructor 
	 * @param resolver provide the <code>EnumLikeResolver</code> interface's instance class 
	 */
	public EnumLikeDeserializer(JavaType valueType, EnumLikeResolver<T> resolver) {
		super(valueType);
		this.resolver = resolver;
	}

	/* (non-Javadoc)
	 * @see com.fasterxml.jackson.databind.JsonDeserializer#deserialize(com.fasterxml.jackson.core.JsonParser, com.fasterxml.jackson.databind.DeserializationContext)
	 */
	@Override
	public T deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
		return resolver.resolve(p.getValueAsString());
	}
}
