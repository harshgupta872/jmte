package com.floreysoft.jmte.token;

import java.util.List;

import com.floreysoft.jmte.NamedRenderer;
import com.floreysoft.jmte.Renderer;
import com.floreysoft.jmte.TemplateContext;

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

	public StringToken(String variableName) {
		this(variableName, variableName, null, null, null, null, null);
	}

	public StringToken(String text, List<String> segments, String variableName, String defaultValue,
			String prefix, String suffix, String rendererName, String parameters) {
		super(segments, variableName);
		this.defaultValue = defaultValue;
		this.prefix = prefix;
		this.suffix = suffix;
		this.rendererName = rendererName;
		this.parameters = parameters;
		setText(text);
	}

	public StringToken(List<String> segments, String variableName) {
		super(segments, variableName);
		this.defaultValue = null;
		this.prefix = null;
		this.suffix = null;
		this.rendererName = null;
		this.parameters = null;
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
	public Object evaluate(TemplateContext context) {
		final Object value = evaluatePlain(context);

		final String renderedResult;
		if (value == null || value.equals("")) {
			renderedResult = getDefaultValue();
		} else {
			String namedRendererResult = null;
			if (rendererName != null && !rendererName.equals("")) {
				NamedRenderer rendererForName = context.engine
						.resolveNamedRenderer(rendererName);
				if (rendererForName != null) {
					namedRendererResult = rendererForName.render(value,
							parameters);
				}
			}
			if (namedRendererResult != null) {
				renderedResult = namedRendererResult;
			} else {
				Renderer<Object> rendererForClass = context.engine
						.resolveRendererForClass(value.getClass());
				if (rendererForClass != null) {
					renderedResult = rendererForClass.render(value);
				} else {
					renderedResult = value.toString();
				}
			}
		}

		if (renderedResult == null || renderedResult.equals("")) {
			return renderedResult;
		} else {
			return getPrefix() + renderedResult + getSuffix();
		}
	}

	public String getRendererName() {
		return rendererName;
	}

	public String getParameters() {
		return parameters;
	}
}