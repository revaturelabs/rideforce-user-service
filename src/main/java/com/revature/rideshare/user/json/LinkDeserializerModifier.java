package com.revature.rideshare.user.json;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.deser.BeanDeserializerBuilder;
import com.fasterxml.jackson.databind.deser.BeanDeserializerModifier;
import com.fasterxml.jackson.databind.deser.SettableBeanProperty;

public class LinkDeserializerModifier extends BeanDeserializerModifier {
	private ApplicationContext context;

	public LinkDeserializerModifier(ApplicationContext context) {
		this.context = context;
	}

	@Override
	public BeanDeserializerBuilder updateBuilder(DeserializationConfig config, BeanDescription beanDesc,
			BeanDeserializerBuilder builder) {
		// Collect a list of all properties to be changed.
		List<SettableBeanProperty> toChange = new ArrayList<>();
		builder.getProperties().forEachRemaining(prop -> {
			JsonLink link = prop.getAnnotation(JsonLink.class);
			if (link != null) {
				LinkResolver<? extends Linkable> resolver = context.getBean(link.value());
				LinkDeserializer<? extends Linkable> deserializer = new LinkDeserializer<>(prop.getType(), resolver);
				toChange.add(prop.withValueDeserializer(deserializer));
			}
		});

		toChange.forEach(prop -> builder.addOrReplaceProperty(prop, true));
		return builder;
	}
}
