package org.crawlscript.robot;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;
import org.jsoup.select.NodeVisitor;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;
import org.mozilla.javascript.Undefined;
import org.mozilla.javascript.annotations.JSConstructor;
import org.mozilla.javascript.annotations.JSFunction;

public class JSElement extends ScriptableObject{

	Element data;
	
	public JSElement()
	{
	
	}
	
	@JSConstructor
	public JSElement(Object data) {
		this.data=(Element) data;
	}
	
	@JSFunction
	public Object child(int i) {
		if(data.child(i)==null)
			return null;
		Object[] arg={data.child(i)};
		Scriptable resultdoc=Context.getCurrentContext().newObject(this, "JSElement",arg);
		return resultdoc;
		
	}
	
	
	

	
	
	
	
	@JSFunction
	public String attr(String attrname)
	{
		if(data.attr(attrname)!=null)
			return data.attr(attrname);
		else
			return null;
	}
	
	
	@JSFunction
	public String regex(String regexstr)
	{
        Pattern pattern = Pattern.compile(regexstr);
        Matcher matcher = pattern.matcher(data.html());
        if (matcher.find()) {// matcher.matchers() {
            String fqdnId = matcher.group();
            return fqdnId;
        }
        
        return null;
	}
	
	@JSFunction
	public void each(String selectorstr,Object fObj)
	{	 
		    Function f = (Function)fObj;		
		 	Elements eles=data.select(selectorstr);
			for(int i=0;i<eles.size();i++)
			{
				
				Object[] arg={eles.get(i)};
				Scriptable resultele=Context.getCurrentContext().newObject(this, "JSElement",arg);
			
				Object functionArgs[] = { resultele ,i};
				
				Object result = f.call(Context.getCurrentContext(), this, this, functionArgs);
				  //String report = "f('my args') = " + Context.toString(result);
				  //System.out.println(report);
			}		    		  
	}
	
	final int TYPE_TEXT=0;
	final int TYPE_ELEMENT=1;
	
	@JSFunction
	public void traverse(Object fObj)
	{	 
		    final Function f = (Function)fObj;		
		    data.traverse(new NodeVisitor() {
				
				@Override
				public void tail(Node arg0, int arg1) {
					
					int type=-1;
					Object argnode;
					if(arg0 instanceof TextNode)
					{
						type=TYPE_TEXT;
						Object[] arg={((TextNode) arg0).text()};
						argnode=Context.getCurrentContext().newObject(JSElement.this, "JSTextnode",arg);
					}
					else if(arg0 instanceof Element)
					{
						type=TYPE_ELEMENT;
						Object[] arg={arg0};
						argnode=Context.getCurrentContext().newObject(JSElement.this, "JSElement",arg);
	
					}
					else
						return;
					
					
					// TODO Auto-generated method stub
					Object[] functionArgs= {arg0,type};
					//if（arg0 instanceof TextNode)
					
					Object result = f.call(Context.getCurrentContext(), JSElement.this, JSElement.this, functionArgs);
					  //String report = "f('my args') = " + Context.toString(result);
					  //System.out.println(report);
				}
				
				@Override
				public void head(Node arg0, int arg1) {
					// TODO Auto-generated method stub
					
				}
			});
		 
				
				    		  
	}

	@JSFunction
	public String type()
	{
		return "element";
	}

	
	@JSFunction
	public String text()
	{
		return data.text();
	}
	
	@JSFunction
	public String html()
	{
		return data.html();
	}
	
	@JSFunction
	public String tag()
	{
		return tagName();
	}
	
	@JSFunction
	public String tagName()
	{
		return data.tagName();
	}
	
	@JSFunction
	public Object div(Object index)
	{
		return createDoc("div",index);
	}
	
	@JSFunction
	public Object table(Object index)
	{
		return createDoc("table",index);
	}
	
	@JSFunction
	public Object tbody(Object index)
	{
		return createDoc("tbody",index);
	}
	
	@JSFunction
	public Object tr(Object index)
	{
		return createDoc("tr",index);
	}
	
	@JSFunction
	public Object td(Object index)
	{
		return createDoc("td",index);
	}
	
	@JSFunction
	public Object body(Object index)
	{
		return createDoc("body",index);
	}
	
	public Scriptable createDoc(String selector,Object index)
	{
		Elements result;
		Elements temp=data.select(selector);
		
		if(index==Undefined.instance)
		{
			Object[] arg={temp};
			Scriptable resultdoc=Context.getCurrentContext().newObject(this, "JSElements",arg);
			return resultdoc;
		}
		else if(index instanceof Double)
		{
			result=new Elements();
			Double d=(Double) index;
			if(d.intValue()+1>temp.size())
				return null;
			Object[] arg={temp.get(d.intValue())};
			Scriptable resultdoc=Context.getCurrentContext().newObject(this, "JSElement",arg);
			return resultdoc;
		}
		else
		{
			result=new Elements();
			if((int)index+1>temp.size())
				return null;
			Object[] arg={temp.get((int) index)};
			Scriptable resultdoc=Context.getCurrentContext().newObject(this, "JSElement",arg);
			return resultdoc;
		}		
		
	}
	
	@JSFunction
	public Object p(Object index)
	{
		return createDoc("p",index);
	}
	
	@JSFunction
	public Object a(Object index)
	{
		return createDoc("a",index);
	}
	
	@JSFunction
	public Object ul(Object index)
	{
		return createDoc("ul",index);
	}
	
	@JSFunction
	public Object li(Object index)
	{
		return createDoc("li",index);
	}
	
	
	@JSFunction
	public Object img(Object index)
	{
		return createDoc("img",index);
	}
	
	
	@JSFunction
	public Object select(String selector)
	{
		
		Object[] arg={data.select(selector)};
		Scriptable resultdoc=Context.getCurrentContext().newObject(this, "JSElements",arg);
		return resultdoc;
	}
	
	
	
	@JSFunction
	@Override
	public String toString()
	{
		return text();
	}
	
	@Override
	public String getClassName() {
		// TODO Auto-generated method stub
		return "JSElement";
	}

	

	
	
	@JSFunction
	public Object pre() {		
		return data.previousElementSibling();
	}
	
	@JSFunction

	public Object next() {		
		return data.nextElementSibling();
				
	}

	

	@JSFunction
	public Object link() {
		// TODO Auto-generated method stub
		return select("a");
	}

	@JSFunction
	public Object children() throws Exception {
		return data.children();
			
	}
	
	

	
	
}
