package com.revature.rideforce.user.json;

import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;

/**
 * A {@link BeanSerializerModifier} that looks for {@link EnumLike} annotations
 * and marks any such fields for conversion to values using the
 * {@link EnumLikeWriter}.
 * @since Iteration1: 10/01/2018
 */
public class EnumLikeSerializerModifier extends BeanSerializerModifier {
	/* (non-Javadoc)
	 * @see com.fasterxml.jackson.databind.ser.BeanSerializerModifier#changeProperties(com.fasterxml.jackson.databind.SerializationConfig, com.fasterxml.jackson.databind.BeanDescription, java.util.List)
	 */
	@Override
	public List<BeanPropertyWriter> changeProperties(SerializationConfig config, BeanDescription beanDesc,
			List<BeanPropertyWriter> beanProperties) {
		return beanProperties.stream().map(writer -> {
			JsonEnumLike jsonEnumLike = writer.getAnnotation(JsonEnumLike.class);
			return jsonEnumLike == null ? writer : new EnumLikeWriter(writer);
		}).collect(Collectors.toList());
	}
}
