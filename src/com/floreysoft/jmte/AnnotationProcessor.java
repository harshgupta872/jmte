package com.floreysoft.jmte;

import com.floreysoft.jmte.token.AnnotationToken;

/**
 * Processor for an annotation, like ${@type String simple}.
 * 
 * @param <T>
 *            what the processor produces
 */
public interface AnnotationProcessor<T> {

	String getType();
	/**
	 * Processes the annotation and possible returns a value.
	 * 
	 * <p>Note: for large chunks of output data - annotation processor can stream this output
	 * to Appendable using this construction:</p>
	 * <p><blockquote><pre>
	 * public String eval(AnnotationToken token, TemplateContext context) {
	 *			// NOTE: always check if Appendable is available - on some evaluation
	 *			//       stages it can be null!
	 *			if (context.out!=null){
	 *				context.out.append("MY APPENDED DATA");
	 *			} 
	 *			return null;
	 *		}
	 * </pre></blockquote><p>
	 * 
	 * @param token
	 *            the annotation token triggering this processor
	 * 
	 * @param context
	 *            current context during template evaluation
	 * @return the produced value
	 */
	T eval(AnnotationToken token, TemplateContext context);

}
