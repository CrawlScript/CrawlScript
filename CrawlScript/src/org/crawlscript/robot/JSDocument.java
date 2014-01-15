package org.crawlscript.robot;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.jsoup.select.NodeVisitor;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;
import org.mozilla.javascript.Undefined;
import org.mozilla.javascript.annotations.JSConstructor;
import org.mozilla.javascript.annotations.JSFunction;

public class JSDocument extends JSElement{
	public String url;
	public Document document;
	public final String mobileagent="Mozilla/5.0 (Linux; U; Android 2.2; en-us; Nexus One Build/FRF91) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1";
	public final String winagent="Mozilla/5.0 (Windows NT 5.1; rv:22.0) Gecko/20100101 Firefox/22.0";
	
	
	public JSDocument()
	{
		
	}
	
	@JSConstructor
	public JSDocument(String url,Object agentObj) {
		
		if(url=="null")
			return;
		String agent=winagent;
		if(agentObj!=Undefined.instance)
		{
			String type=Context.toString(agentObj);
			if(type.equals("windows")||type.equals("win")||type.equals("w"))
			{
				agent=winagent;
			}
			else if(type.equals("mobile")||type.equals("m"))
			{
				agent=mobileagent;
			}
			else
				agent=type;
		}
		
		this.url = url;
		try {
			document = Jsoup
					.connect(url)
					.userAgent(winagent).get();
			System.err.println("ok");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			
		}
		data=document;
	}

	
	
	
	
	@JSFunction
	@Override
	public Object a(Object index) {
		// TODO Auto-generated method stub
		return super.a(index);
	}

	@JSFunction
	@Override
	public String attr(String attrname) {
		// TODO Auto-generated method stub
		return super.attr(attrname);
	}

	@JSFunction
	@Override
	public Object body(Object index) {
		// TODO Auto-generated method stub
		return super.body(index);
	}

	@JSFunction
	@Override
	public Object child(int i) {
		// TODO Auto-generated method stub
		return super.child(i);
	}

	@JSFunction
	@Override
	public Object children() throws Exception {
		// TODO Auto-generated method stub
		return super.children();
	}

	@JSFunction
	@Override
	public Object div(Object index) {
		// TODO Auto-generated method stub
		return super.div(index);
	}

	@JSFunction
	@Override
	public void each(String selectorstr, Object fObj) {
		// TODO Auto-generated method stub
		super.each(selectorstr, fObj);
	}

	@Override
	public String getClassName() {
		// TODO Auto-generated method stub
		return "JSDocument";
	}
	
	
	
	
	

	
	
	/*
	 * super
	 */
	
	@JSFunction
	@Override
	public String html() {
		// TODO Auto-generated method stub
		return super.html();
	}


	@JSFunction
	@Override
	public Object img(Object index) {
		// TODO Auto-generated method stub
		return super.img(index);
	}

	@JSFunction
	@Override
	public String innerhtml() {
		// TODO Auto-generated method stub
		return super.innerhtml();
	}

	@JSFunction
	@Override
	public Object li(Object index) {
		// TODO Auto-generated method stub
		return super.li(index);
	}

	@JSFunction
	@Override
	public Object link() {
		// TODO Auto-generated method stub
		return super.link();
	}

	@JSFunction
	@Override
	public Object next() {
		// TODO Auto-generated method stub
		return super.next();
	}

	@JSFunction
	@Override
	public Object p(Object index) {
		// TODO Auto-generated method stub
		return super.p(index);
	}

	@JSFunction
	@Override
	public Object parent() {
		// TODO Auto-generated method stub
		return super.parent();
	}
	
	@JSFunction
	@Override
	public Object pre() {
		// TODO Auto-generated method stub
		return super.pre();
	}

	@JSFunction
	public String title()
	{
		return document.title();
	}

	@JSFunction
	@Override
	public String type()
	{
		return "document";
	}
	
	@JSFunction
	@Override
	public String regex(String regexstr) {
		// TODO Auto-generated method stub
		return super.regex(regexstr);
	}
	@JSFunction
	@Override
	public Object select(String selector) {
		// TODO Auto-generated method stub
		return super.select(selector);
	}
	
	
	@JSFunction
	@Override
	public Object siblings() {
		// TODO Auto-generated method stub
		return super.siblings();
	}

	@JSFunction
	@Override
	public void traverse(Object fObj) {
		// TODO Auto-generated method stub
		super.traverse(fObj);
	}
	@JSFunction
	@Override
	public Object table(Object index) {
		// TODO Auto-generated method stub
		return super.table(index);
	}
	@JSFunction
	@Override
	public Object tbody(Object index) {
		// TODO Auto-generated method stub
		return super.tbody(index);
	}

	@JSFunction
	@Override
	public Object td(Object index) {
		// TODO Auto-generated method stub
		return super.td(index);
	}

	@JSFunction
	@Override
	public String text() {
		// TODO Auto-generated method stub
		return super.text();
	}

	@JSFunction
	@Override
	public Object tr(Object index) {
		// TODO Auto-generated method stub
		return super.tr(index);
	}
	@JSFunction
	@Override
	public String tag() {
		// TODO Auto-generated method stub
		return super.tag();
	}
	@JSFunction
	@Override
	public String tagName() {
		// TODO Auto-generated method stub
		return super.tagName();
	}

	@JSFunction
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}

	@JSFunction
	@Override
	public Object ul(Object index) {
		// TODO Auto-generated method stub
		return super.ul(index);
	}

	

	

	
	
	
	
	
}
