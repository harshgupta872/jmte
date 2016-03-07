# Download #

There is a single distribution containing all sources, all binaries and all require libraries. [Download the latest version from here](http://code.google.com/p/jmte/downloads/list).

# Installation #

The _Java Minimal Template Engine_ supports both interpreted and compiled templates. Compiled templates typically evaluate 2.5-10x faster then interpreted templates.

If you want to use the compiled mode you need the additional ASM library which is part of the distribution. There even is single jar that contains JMTE and a repackaged version of ASM.

If you do not want or the need compilation, all you need to use the JMTE is the single 80k JMTE jar.

# What is the Java Minimal Template Engine #

A _template_ consists of fixed text having holes in it that is filled by content dynamically provided by a model.

While the template is the static part, the the _model_ contains the dynamic data to be filled into the gaps of the template.

The _template engine_ takes a template and the model as input and transforms it into a final string as output.

For the _Java Minimal Template Engine_ the template is stored in a String and the model is a simple Map from String to Object.

This is typical code to do a template evaluation using the _Java Minimal Template Engine_:

```
Engine engine = new Engine();
Map<String, Object> model = new HashMap<String, Object>();
model.put("address", "Filbert");

String template = "${if address}<h1>${address}</h1>${end}"
String output = engine.transform(template, model);

assert "Filbert".equals(output);

```

# The Template Language #

The template language is very simple and in a sense minimal: It only has those language constructs you can't do without:

## Pure text ##

Just static text that is copied from input to output without modification. Like the `<h1>` and the `</h1>` in the example above.

## Plain gaps ##

The main reason for a template is that you not only have static text, but also dynamic parts. Like in this Java statement

```
String output = "<h1>" + model.get("address") + "</h1>" ;
```

Now

```
${address}
```

in JMTE does pretty much the same as

```
model.get("address")
```

in Java.

## Conditional blocks ##

Sometimes you might need to output static text or even larger sections of your template when certain conditions hold. Like in the first example

```
${if address}<h1>${address}</h1>${end}
```

you only want to the heading tags `<h1>` and `</h1>` to be displayed when there actually is something stored inside address. That's what you can use the _if_ statement for.

When there is no data in the model matching a gap it is simply filled with nothing anyway.

## Loops ##

Every time date from the model is not a single value, but rather a collection of them, you need a way to specify how to display them. This is done by the _foreach_ loop like in

```
${foreach list item}${item}\n${end}
```

where _list_ contains a number of items which are stored - one after the other - into the variable _item_. From inside the loop you can now access that variable just as if it was part of your model. The part between the _foreach_ and the _end_ is the body of the loop. It is evaluated as many times as you have items in your list.

## And now? ##

[This is the complete language description](http://jmte.googlecode.com/svn/trunk/doc/index.html).