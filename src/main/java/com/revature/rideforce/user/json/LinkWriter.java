package com.revature.rideforce.user.json;

import java.util.Collection;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;

/**
 * A {@link BeanPropertyWriter} that converts {@link Linkable} objects to links.
 */
public class LinkWriter extends BeanPropertyWriter {
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a new {@link LinkWriter} with the given base
	 * {@link BeanPropertyWriter}.
	 * 
	 * @param writer the base writer, to be overridden with link-specific
	 *               functionality
	 */
	public LinkWriter(BeanPropertyWriter writer) {
		super(writer);
	}

	@Override
	public void serializeAsField(Object bean, JsonGenerator gen, SerializerProvider prov) throws Exception {
		// The bean to be linked.
		Object linkBean = getMember().getValue(bean);
		if (linkBean instanceof Collection<?>) {
			gen.writeArrayFieldStart(getName());
			for (Object e : (Collection<?>) linkBean) {
				if (e instanceof Linkable) {
					gen.writeString(((Linkable) e).toLink());
				}
			}
			gen.writeEndArray();
		} else if (linkBean instanceof Linkable) {
			gen.writeStringField(getName(), ((Linkable) linkBean).toLink());
		}
	}
}
