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

public class JSTextnode extends ScriptableObject{

	public String text=null;
	@Override
	public String getClassName() {
		// TODO Auto-generated method stub
		return "JSTextnode";
	}
	
	public JSTextnode()
	{
		
	}
	
	@JSConstructor
	public JSTextnode(String text)
	{
		this.text=text;
	}
	
	@JSFunction
	@Override
	public String toString()
	{
		return text;
	}

	@JSFunction
	public String text()
	{
		return text;
	}
	
}
