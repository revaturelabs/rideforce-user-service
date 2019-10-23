package com.revature.rideforce.user.json;

import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;

/**
 * A {@link BeanSerializerModifier} that looks for {@link JsonLink} annotations
 * and marks any such fields for conversion to links using the
 * {@link LinkWriter}.<p>
 * eg. say putting @JsonLink over User class' member field of Car list. Then that field is to be converted to a link
 * rather than pojo.
 * Allows for storing of less pojos and more accessing from database 
 * @since Iteration1: 10/01/2018
 * @author Iteration1, clpeng
 */
public class LinkSerializerModifier extends BeanSerializerModifier {
	@Override
	public List<BeanPropertyWriter> changeProperties(SerializationConfig config, BeanDescription beanDesc,
			List<BeanPropertyWriter> beanProperties) {
		return beanProperties.stream().map(writer -> {
			JsonLink jsonLink = writer.getAnnotation(JsonLink.class);
			return jsonLink == null ? writer : new LinkWriter(writer);
		}).collect(Collectors.toList());
	}
}
