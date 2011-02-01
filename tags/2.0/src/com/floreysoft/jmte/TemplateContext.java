package com.floreysoft.jmte;

import java.util.LinkedList;
import java.util.List;

import com.floreysoft.jmte.ProcessListener.Action;
import com.floreysoft.jmte.token.Lexer;
import com.floreysoft.jmte.token.Token;

/**
 * Holds the combined current state of a template during evaluation.
 * 
 * @author olli
 *
 */
public class TemplateContext {

	public final ScopedMap model;
	/**
	 * Stack like hierarchy of structure giving tokens (if  and foreach)
	 */
	public final List<Token> scopes;
	public final String template;
	public final Engine engine;
	public final String sourceName;
	public final ModelAdaptor modelAdaptor;

	public TemplateContext(String template, String sourceName, ScopedMap model,
			ModelAdaptor modelAdaptor, Engine engine) {
		this.model = model;
		this.template = template;
		this.engine = engine;
		this.scopes = new LinkedList<Token>();
		this.sourceName = sourceName;
		this.modelAdaptor = modelAdaptor;
	}

	/**
	 * Pushes a token on the scope stack.
	 */
	public void push(Token token) {
		scopes.add(token);
	}

	/**
	 * Pops a token from the scope stack.
	 */
	public Token pop() {
		if (scopes.isEmpty()) {
			return null;
		} else {
			Token token = scopes.remove(scopes.size() - 1);
			return token;
		}
	}

	/**
	 * Gets the top element from the stack without removing it.
	 */
	public Token peek() {
		if (scopes.isEmpty()) {
			return null;
		} else {
			Token token = scopes.get(scopes.size() - 1);
			return token;
		}
	}

	@SuppressWarnings("unchecked")
	/**
	 * Gets the first element of the given type from the stack without removing it.
	 */
	public <T extends Token> T peek(Class<T> type) {
		for (int i = scopes.size() - 1; i >= 0; i--) {
			Token token = scopes.get(i);
			if (token.getClass().equals(type)) {
				return (T) token;
			}
		}
		return null;
	}

	/**
	 * Allows you to send additional notifications of executed processing steps. 
	 * 
	 * @param token the token that is handled
	 * @param action the action that is executed on the action
	 */
	public void notifyProcessListeners(Token token, Action action) {
		engine.notifyProcessListeners(this, token, action);
	}
}
