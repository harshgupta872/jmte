# Objective #

This page is a naturally biased comparison of JMTE to other template engines.

# Facts (the less biased parts) #

|Template Engine|Runs on GAE|Expressions|[MVC](http://www.cs.usfca.edu/~parrt/papers/mvc.templates.pdf)|Dependencies|Compiler|Target language|
|:--------------|:----------|:----------|:-------------------------------------------------------------|:-----------|:-------|:--------------|
|FreeMarker     |Yes        |Full       |Formatting inside template, expressions inside template       |Single JAR 880k|No      |Java           |
|JMTE           |Yes        |No operators, but = for strings|Full                                                          |Jar bundled with ASM 115k (unbundled 75k)|Optionally to Java byte code|Java           |
|StringTemplate3|Yes        |No operators|Full                                                          |ANTLR2, Jar 120k|No      |Java, C#, Python, Ruby, Scala|
|StringTemplate4|Yes        |Logical operators|Full                                                          |ANTLR3, StringTemplate3, Jar 200k|To internal byte code|Java, Python in preparation (more to come?)|
|Velocity       |Yes        |Full       |Not at all                                                    |Jar bundled with commons and oro 800k|No      |Java           |

# Performance measurements #

This is a tough one. There are so many assumptions you have to make to come up with anything close to a fair comparison.

My basic assumptions what a realistic scenario would include
  1. a template like [this](http://jmte.googlecode.com/svn/trunk/test/com/floreysoft/jmte/realLive/template/email.jmte)
  1. loading a template once and have many calls to it - that means loading the template is not part of the measurement, if it was the numbers would look way different

I used the caliper tool for the micro benchmarks, all numbers in micro seconds:

![http://chart.apis.google.com/chart?chxl=1:|Velocity|Freemarker|ST3|ST4|JMTECompiled|JMTE|Handwritten&chxr=0,0,120&chxt=x,r&chbh=r&chs=450x250&cht=bhs&chco=4D89F9&chds=0,120&chd=t:13.4,33.8,29.6,39.2,112.4,54.5,23.8&chdlp=l&image.png](http://chart.apis.google.com/chart?chxl=1:|Velocity|Freemarker|ST3|ST4|JMTECompiled|JMTE|Handwritten&chxr=0,0,120&chxt=x,r&chbh=r&chs=450x250&cht=bhs&chco=4D89F9&chds=0,120&chd=t:13.4,33.8,29.6,39.2,112.4,54.5,23.8&chdlp=l&image.png)

# Oliver's personal estimation (the really biased part) #

My approach for this comparison was to start with a real life template for JMTE and redo it for all the other template engines. I did not try to use any special features of JMTE not present in other template languages - partially because there are none :).

These were my feelings when I did the ports to the other engines
  * **FreeMarker**: Good stuff, easy API, did not like missing model-view separation, some odd behavior at times
  * **Velocity**: Without doubt the worst experience in a while - felt like in the ancient Java days, crazy API, no separation of concerns, no clear concept (or I just did not grok it), outstanding performance probably achieved by lack of abstraction
  * **StringTemplate 3/4**: Most complete and sound template language, best concept, a little bit harder to learn, API good, biggest burden are the dependencies
  * **JMTE**: just minimal

# Sources #

You can [download all sources](http://jmte.googlecode.com/files/templatesurvey.zip) of this comparison.

# Performance measurements revisited #

Here are some additional more low level measurements:

## Simple expression ##

```
${simple} stuff
```

Measured in nanoseconds (ns)

![http://chart.apis.google.com/chart?chxl=1:|Velocity|Freemarker|ST3|ST4|JMTECompiled|JMTE&chxr=0,0,2500&chxt=x,r&chbh=r&chs=450x200&cht=bhs&chco=4D89F9&chds=0,2500&chd=t:1286,911,2416,1800,2027,766&chdlp=l&image.png](http://chart.apis.google.com/chart?chxl=1:|Velocity|Freemarker|ST3|ST4|JMTECompiled|JMTE&chxr=0,0,2500&chxt=x,r&chbh=r&chs=450x200&cht=bhs&chco=4D89F9&chds=0,2500&chd=t:1286,911,2416,1800,2027,766&chdlp=l&image.png)

## Reflection ##

```
${order.orderDate}
```

Measured in microseconds (µs)

![http://chart.apis.google.com/chart?chxl=1:|Velocity|Freemarker|ST3|ST4|JMTECompiled|JMTE&chxr=0,0,10&chxt=x,r&chbh=r&chs=450x200&cht=bhs&chco=4D89F9&chds=0,10&chd=t:1.43,1.23,2.09,4.37,9.3,1.24&chdlp=l&image.png](http://chart.apis.google.com/chart?chxl=1:|Velocity|Freemarker|ST3|ST4|JMTECompiled|JMTE&chxr=0,0,10&chxt=x,r&chbh=r&chs=450x200&cht=bhs&chco=4D89F9&chds=0,10&chd=t:1.43,1.23,2.09,4.37,9.3,1.24&chdlp=l&image.png)

## If ##

```
${if order.orderDate}${order.orderDate}${else}NIX${end}
```

Measured in microseconds (µs)

![http://chart.apis.google.com/chart?chxl=1:|Velocity|Freemarker|ST3|ST4|JMTECompiled|JMTE&chxr=0,0,15&chxt=x,r&chbh=r&chs=450x200&cht=bhs&chco=4D89F9&chds=0,15&chd=t:3.82,3.15,2.26,6.85,14.75,2.42&chdlp=l&image.png](http://chart.apis.google.com/chart?chxl=1:|Velocity|Freemarker|ST3|ST4|JMTECompiled|JMTE&chxr=0,0,15&chxt=x,r&chbh=r&chs=450x200&cht=bhs&chco=4D89F9&chds=0,15&chd=t:3.82,3.15,2.26,6.85,14.75,2.42&chdlp=l&image.png)

## Foreach ##

```
${foreach order.items item}${item}${end}
```

Measured in microseconds (µs)

![http://chart.apis.google.com/chart?chxl=1:|Velocity|Freemarker|ST3|ST4|JMTECompiled|JMTE&chxr=0,0,10&chxt=x,r&chbh=r&chs=450x200&cht=bhs&chco=4D89F9&chds=0,10&chd=t:6.63,5.96,5.44,9.9,6.09,4.65&chdlp=l&image.png](http://chart.apis.google.com/chart?chxl=1:|Velocity|Freemarker|ST3|ST4|JMTECompiled|JMTE&chxr=0,0,10&chxt=x,r&chbh=r&chs=450x200&cht=bhs&chco=4D89F9&chds=0,10&chd=t:6.63,5.96,5.44,9.9,6.09,4.65&chdlp=l&image.png)