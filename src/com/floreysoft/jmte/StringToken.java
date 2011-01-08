package com.floreysoft.jmte;

import java.util.Map;
import java.util.concurrent.Callable;

public class StringToken extends ExpressionToken {
	// ${<h1>,address(NIX),</h1>;long(full)}
	private final String defaultValue; // NIX
	private final String prefix; // <h1>
	private final String suffix; // </h1>
	private final String rendererName; // long
	private final String parameters; // full

	public StringToken() {
		this("", "", null, null, null, null, null);
	}

	public StringToken(String text, String variableName, String defaultValue,
			String prefix, String suffix, String rendererName, String parameters) {
		super(variableName);
		this.defaultValue = defaultValue;
		this.prefix = prefix;
		this.suffix = suffix;
		this.rendererName = rendererName;
		this.parameters = parameters;
		setText(text);
	}

	public String getPrefix() {
		return prefix != null ? prefix : "";
	}

	public String getSuffix() {
		return suffix != null ? suffix : "";
	}

	public String getDefaultValue() {
		return defaultValue != null ? defaultValue : "";
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object evaluate(Engine engine, Map<String, Object> model,
			ErrorHandler errorHandler) {

		final String renderedResult;
		Object value = traverse(getSegments(), model, errorHandler);
		if (value == null || value.equals("")) {
			renderedResult = getDefaultValue();
		} else {
			if (value instanceof Callable) {
				try {
					value = ((Callable) value).call();
				} catch (Exception e) {
				}
			}

			String namedRendererResult = null;
			if (rendererName != null && !rendererName.equals("")) {
				NamedRenderer rendererForName = engine
						.resolveNamedRenderer(rendererName);
				if (rendererForName != null) {
					namedRendererResult = rendererForName.render(value,
							parameters);
				}
			}
			if (namedRendererResult != null) {
				renderedResult = namedRendererResult;
			} else {
				Renderer<Object> rendererForClass = engine
						.resolveRendererForClass(value.getClass());
				if (rendererForClass != null) {
					renderedResult = rendererForClass.render(value);
				} else {
					renderedResult = value.toString();
				}
			}
		}

		return getPrefix() + renderedResult + getSuffix();
	}
}
