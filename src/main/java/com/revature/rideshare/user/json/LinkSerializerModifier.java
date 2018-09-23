package com.revature.rideshare.user.json;

import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;

/**
 * A {@link BeanSerializerModifier} that looks for {@link JsonLink} annotations
 * and marks any such fields for conversion to links using the
 * {@link JsonLinkWriter}.
 */
public class LinkSerializerModifier extends BeanSerializerModifier {
	@Override
	public List<BeanPropertyWriter> changeProperties(SerializationConfig config, BeanDescription beanDesc,
			List<BeanPropertyWriter> beanProperties) {
		return beanProperties.stream().map(writer -> {
			JsonLink jsonLink = writer.getAnnotation(JsonLink.class);
			return jsonLink == null ? writer : new JsonLinkWriter(writer);
		}).collect(Collectors.toList());
	}
}
