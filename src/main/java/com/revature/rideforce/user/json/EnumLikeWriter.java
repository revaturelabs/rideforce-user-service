package com.revature.rideforce.user.json;

import java.util.Collection;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;

/**
 * A {@link BeanPropertyWriter} that converts {@link EnumLike} objects to value
 * strings.
 * @since Iteration1: 10/01/2018
 */
public class EnumLikeWriter extends BeanPropertyWriter {
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a new {@link EnumLikeWriter} with the given base
	 * {@link BeanPropertyWriter}.
	 * 
	 * @param writer the base writer, to be overridden with link-specific
	 *               functionality
	 */
	public EnumLikeWriter(BeanPropertyWriter writer) {
		super(writer);
	}
	
	/* (non-Javadoc)
	 * @see com.fasterxml.jackson.databind.ser.BeanPropertyWriter#serializeAsField(java.lang.Object, com.fasterxml.jackson.core.JsonGenerator, com.fasterxml.jackson.databind.SerializerProvider)
	 */
	@Override
	public void serializeAsField(Object bean, JsonGenerator gen, SerializerProvider prov) throws Exception {
		// The enum-like bean to be converted.        //guess it's serializing the whole bean class
		Object enumLike = getMember().getValue(bean);
		if (enumLike instanceof Collection<?>) {
			gen.writeArrayFieldStart(getName());
			for (Object e : (Collection<?>) enumLike) {
				if (e instanceof EnumLike) {
					gen.writeString(((EnumLike) e).toEnumString());
				}
			}
			gen.writeEndArray();
		} else if (enumLike instanceof EnumLike) {
			gen.writeStringField(getName(), ((EnumLike) enumLike).toEnumString());
		}
	}
}
