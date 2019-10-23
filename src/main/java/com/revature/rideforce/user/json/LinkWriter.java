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

	/* (non-Javadoc)
	 * @see com.fasterxml.jackson.databind.ser.BeanPropertyWriter#serializeAsField(java.lang.Object, com.fasterxml.jackson.core.JsonGenerator, com.fasterxml.jackson.databind.SerializerProvider)
	 */
	/**
	 * Method called to access property that this bean stands for, from within given bean, and to serialize it as a JSON Object field using appropriate serializer.
	 * <p>Changes the field/member object in question to a url link, then serializes the url into json(for display in the controller view's response body) 
	 * @since Iteration1: 10/01/2018
	 * @author clpeng
	 */
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
