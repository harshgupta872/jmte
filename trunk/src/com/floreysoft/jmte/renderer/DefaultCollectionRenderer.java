package com.floreysoft.jmte.renderer;

import java.util.Collection;

import com.floreysoft.jmte.Renderer;


@SuppressWarnings("unchecked")
public class DefaultCollectionRenderer implements Renderer<Collection> {

	@Override
	public String render(Collection collection) {
		final String renderedResult;

		if (collection.size() == 0) {
			renderedResult = "";
		} else if (collection.size() == 1) {
			renderedResult = collection.iterator().next().toString();
		} else {
			renderedResult = collection.toString();
		}
		return renderedResult;

	}

}
