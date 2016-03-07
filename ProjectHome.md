# Java project Minimal Template Engine #

Java project Minimal Template Engine is meant to fill the gap between simple string formatting with basic Java classes like String.format and complex template solutions like Velocity or StringTemplate.

It is complete but minimal in a sense that you can express everything you need in a template language including 'if' and 'foreach', but nothing else. Because of this it is small, easy to learn and clearly focused. It does not try to solve what Java can do better anyway.

It supports separation of model and view, runs without external dependencies, can be extended and configured in many ways and runs in almost all environments including Google App Engine.

## Sample template code ##
```
 ${foreach cart.items item}
   ${item.amount}x ${item.name} ${item.price} each = ${item.total}
 ${end}

 ${if cart.addShipping}
   Shipping = ${cart.shippingCost}
 ${end}
```

## How to call the engine ##

```
 String input = "${name}";
 Map<String, Object> model = new HashMap<String, Object>();
 model.put("name", "Minimal Template Engine");
 Engine engine = new Engine();
 String transformed = engine.transform(input, model);
 assert (transformed.equals("Minimal Template Engine"));

```

## What now? ##

> [Read on...](GettingStarted.md)

> [Comparing JMTE to other template engines](Comparison.md)

> [Complete language Specification](http://jmte.googlecode.com/svn/trunk/doc/index.html)