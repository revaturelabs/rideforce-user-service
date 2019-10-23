package com.revature.rideforce.user.json;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.springframework.context.ApplicationContext;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.BeanDeserializerBuilder;
import com.fasterxml.jackson.databind.deser.BeanDeserializerModifier;
import com.fasterxml.jackson.databind.deser.SettableBeanProperty;
import com.fasterxml.jackson.databind.deser.std.CollectionDeserializer;
import com.fasterxml.jackson.databind.deser.std.StdValueInstantiator;

/**
 * A {@link BeanDeserializerModifier} that looks for {@link JsonLink}
 * annotations and marks corresponding fields for conversion using the
 * {@link LinkDeserializer}.
 * @since Iteration1: 10/01/2018
 */
public class LinkDeserializerModifier extends BeanDeserializerModifier {
	private ApplicationContext context;

	/**
	 * Constructs a new {@link LinkDeserializerModifier} using the given
	 * {@link ApplicationContext} to look up the necessary {@link LinkResolver}.
	 * 
	 * @param context the {@link ApplicationContext} to be used to look up
	 *                {@link LinkResolver} beans.
	 */
	public LinkDeserializerModifier(ApplicationContext context) {
		this.context = context;
	}

	@Override
	public BeanDeserializerBuilder updateBuilder(DeserializationConfig config, BeanDescription beanDesc,
			BeanDeserializerBuilder builder) {
		// Collect a list of all properties to be changed.
		List<SettableBeanProperty> toChange = new ArrayList<>();
		builder.getProperties().forEachRemaining(prop -> {
			JsonLink jsonLink = prop.getAnnotation(JsonLink.class);
			if (jsonLink != null) {
				LinkResolver<? extends Linkable> resolver = context.getBean(jsonLink.value());
				JavaType propType = prop.getType();
				LinkDeserializer<? extends Linkable> valueDeserializer = new LinkDeserializer<>(prop.getType(),
						resolver);
				if (propType.isCollectionLikeType()) {
					// All of this nonsense is needed to create a deserializer
					// for a set based on the deserializer for its values.
					// Someone please tell the Jackson team to make this easier
					// :(
					@SuppressWarnings({ "unchecked", "rawtypes" })
					CollectionDeserializer collectionDeserializer = new CollectionDeserializer(propType,
							(JsonDeserializer) valueDeserializer, null,
							new SetValueInstantiator(config, propType));
					toChange.add(prop.withValueDeserializer(collectionDeserializer));
				} else {
					toChange.add(prop.withValueDeserializer(valueDeserializer));
				}
			}
		});

		toChange.forEach(prop -> builder.addOrReplaceProperty(prop, true));
		return builder;
	}

	private static class SetValueInstantiator extends StdValueInstantiator {
		private static final long serialVersionUID = 1L;

		public SetValueInstantiator(DeserializationConfig config, JavaType valueType) {
			super(config, valueType);
		}

		@Override
		public Object createUsingDefault(DeserializationContext ctxt) throws IOException {
			return new HashSet<>();
		}
	}
}
