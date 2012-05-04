package com.floreysoft.jmte.util;

import java.io.IOException;

/**
 * This class wrapps checked java.io.IOException to 
 * unchecked RuntimeException - so it makes code less Invasive...
 * @author hp
 *
 */
public class RuntimeExAppendable implements Appendable {

	protected Appendable orig;
	
	public RuntimeExAppendable(Appendable orig){
		this.orig = orig;
	}
	
	@Override
	public Appendable append(CharSequence csq) {
		try{
			return orig.append(csq);
		}catch(IOException ex){
			throw new RuntimeException(ex);
		}
	}

	@Override
	public Appendable append(CharSequence csq, int start, int end){
		try{
			return orig.append(csq,start,end);
		}catch(IOException ex){
			throw new RuntimeException(ex);
		}
	}

	@Override
	public Appendable append(char c) {
		try{
			return orig.append(c);
		}catch(IOException ex){
			throw new RuntimeException(ex);
		}
	}

	/**
	 * Better compatibility with StringBuilder/StringBuffer
	 * @param o
	 * @return
	 */
	public Appendable append(Object o ){
		try{
			return orig.append(o.toString());
		}catch(IOException ex){
			throw new RuntimeException(ex);
		}		
	}
	
	@Override
	public String toString() {
		return orig.toString();
	}
}
