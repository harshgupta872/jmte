package com.floreysoft.jmte.sample;

import java.util.HashSet;
import java.util.Set;

import com.floreysoft.jmte.Engine;
import com.floreysoft.jmte.TemplateContext;
import com.floreysoft.jmte.template.AbstractCompiledTemplate;
import com.floreysoft.jmte.token.StringToken;
import com.floreysoft.jmte.util.RuntimeExAppendable;

// ${<h1>,address(NIX),</h1>;long(full)}
public class SampleComplexExpressionCompiledTemplate extends
		AbstractCompiledTemplate {

	public SampleComplexExpressionCompiledTemplate(Engine engine) {
		super(engine);
	}

	@Override
	public Set<String> getUsedVariables() {
		Set<String> usedVariables = new HashSet<String>();
		usedVariables.add("address");
		return usedVariables;
	}

	@Override
	protected Appendable transformCompiled(Appendable out,TemplateContext context) {
		RuntimeExAppendable buffer = new RuntimeExAppendable(out);

		buffer.append(new StringToken("address", "address", "NIX", "<h1>",
				"</h1>", "long", "full").evaluate(context));

		return buffer;
	}

}
