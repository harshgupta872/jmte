# Most important API parts #

While the [template language itself](http://jmte.googlecode.com/svn/trunk/doc/index.html) is one part of the _Java Minimal Template Engine_, there is also the API that triggers the transformation and defines how a model is rendered.

All API classes and interface that are important for you reside in the main package [com.floreysoft.jmte](http://jmte.googlecode.com/svn/trunk/src/com/floreysoft/jmte/).

# Renderers #

As described in the [language specification](http://jmte.googlecode.com/svn/trunk/doc/index.html) renderers control how objects coming from the model are displayed in the result of the template transformation.

There are two types of renderers. The simple first type is registered for a certain class for values, like this:

```
engine.registerRenderer(Date.class, new DateRenderer());
```

This means when the template engine sees a value of type _Date_ it will render it using this _DateRenderer_:

```
public class DateRenderer implements Renderer<Date>{

	@Override
	public String render(Date date) {
		String rendered = DateFormat.getDateInstance(DateFormat.SHORT, Locale.GERMAN).format(date);
		return rendered;
	}

}
```

Renderers implement a parameterized interface. The generic parameter describes the type a renderer can render using its _render_ method that just takes the value without any additional formatting information.

# Named Renderers #

If you want more control over how a value is displayed you can also register named renderers like that

```
engine.registerNamedRenderer(new CurrencyRenderer());
```

As such a named renderer knows its name there is no need to supply it upon registration:

```
public class CurrencyRenderer implements NamedRenderer {

	@Override
	public RenderFormatInfo getFormatInfo() {
		return null;
	}

	@Override
	public String getName() {
		return "currency";
	}

	@Override
	public Class<?>[] getSupportedClasses() {
		return new Class<?>[] { BigDecimal.class };
	}

	@Override
	public String render(Object o, String format) {
		if (o instanceof BigDecimal) {
			return new DecimalFormat(format != null ? format
					: "##,##0.00 \u20AC", DecimalFormatSymbols
					.getInstance(Locale.GERMANY)).format(o);
		}
		return null;
	}
}

```

The special thing about such a renderer is that it is accessed by its name and that it can possible render a bunch of different types of value inputs. What its name is can be found out using _getName()_ and its supported value types are returned by _getSupportedClasses()_. The optional _RenderFormatInfo_ may provide any kind of information about the additional format string that is passed into the render method.

Inside the render have to check for the type of the value passed as a parameter and possibly convert it to whatever you need internally. As decribed above you have to declare which types you are able to handle in your renderer.

To access such a renderer you have to specify its name in a template by adding it to the name of the value separated by a semicolon:

```
${order.total;currency}
```

If you want to supply more formatting information you can do this inside parentheses

```
${order.total;currency(parameters for the named renderer)}
```

Whatever is inside the parentheses will be passed as the _format_ parameter to to the render method.