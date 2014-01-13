package org.crawlscript.robot;

import org.mozilla.javascript.annotations.JSFunction;

public interface DomDoc {
	public String attr(String attrname);
	public Object a(Object index);
	public Object hasText();
	public Object first();
	public Object pre() throws Exception ;
	public Object next() throws Exception ;
	public Object last();

}
