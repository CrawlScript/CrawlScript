package org.crawlscript.robot;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;
import org.mozilla.javascript.Undefined;
import org.mozilla.javascript.annotations.JSConstructor;
import org.mozilla.javascript.annotations.JSFunction;

public class JSDocument extends ScriptableObject implements DomDoc{

	Elements data;
	
	public JSDocument()
	{
		
	}
	
	@JSFunction
	public Object get(int i) {
		Object[] arg={data.get(i)};
		Scriptable resultdoc=Context.getCurrentContext().newObject(this, "JSDocument",arg);
		return resultdoc;
		
	}
	
	@JSFunction
	public int size()
	{
		return data.size();
		
	}
	

	
	
	@JSConstructor
	public JSDocument(Object data) {
		if(data instanceof Element)
		{
			this.data=new Elements();
			this.data.add((Element) data);
		}
		else
			this.data=(Elements)data;
	}
	
	@JSFunction
	public String attr(String attrname)
	{
		String result="";
		for(int i=0;i<data.size();i++)
		{
			result+=data.get(i).attr(attrname);
		}
		return result;
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
				Object functionArgs[] = { eles.get(i) ,i};
				Object result = f.call(Context.getCurrentContext(), this, this, functionArgs);
				  //String report = "f('my args') = " + Context.toString(result);
				  //System.out.println(report);
			}		    		  
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
			result=temp;
		}
		else if(index instanceof Double)
		{
			result=new Elements();
			Double d=(Double) index;
			result.add(temp.get(d.intValue()));
		}
		else
		{
			result=new Elements();
			result.add(temp.get((int) index));
		}		
		Object[] arg={result};
		Scriptable resultdoc=Context.getCurrentContext().newObject(this, "JSDocument",arg);
		return resultdoc;
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
		Scriptable resultdoc=Context.getCurrentContext().newObject(this, "JSDocument",arg);
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
		return "JSDocument";
	}

	@JSFunction
	@Override
	public Object hasText() {
		// TODO Auto-generated method stub
		return data.hasText();
	}

	@JSFunction
	@Override
	public Object first() {
		// TODO Auto-generated method stub
		if(data.first()==null)
			return null;
		Object[] arg={data.first()};
		Scriptable resultdoc=Context.getCurrentContext().newObject(this, "JSDocument", arg);
		return resultdoc;
	}

	
	@JSFunction
	@Override
	public Object pre() throws Exception {		
		// TODO Auto-generated method stub
		if(size()==1)
		{
			if(data.get(0).previousElementSibling()==null)
				return null;
			else
			{
				Object[] arg={data.get(0).previousElementSibling()};
				Scriptable resultdoc=Context.getCurrentContext().newObject(this, "JSDocument", arg);
				return resultdoc;
			}
		}
		else
		{
			throw new Exception("not an element");
		}
				
	}
	
	@JSFunction
	@Override
	public Object next() throws Exception {		
		// TODO Auto-generated method stub
		if(size()==1)
		{
			if(data.get(0).nextElementSibling()==null)
				return null;
			else
			{
				Object[] arg={data.get(0).nextElementSibling()};
				Scriptable resultdoc=Context.getCurrentContext().newObject(this, "JSDocument", arg);
				return resultdoc;
			}
		}
		else
		{
			throw new Exception("not an element");
		}
				
	}

	@JSFunction
	@Override
	public Object last() {
		// TODO Auto-generated method stub
		if(data.last()==null)
			return null; 
		Object[] arg={data.last()};
		Scriptable resultdoc=Context.getCurrentContext().newObject(this, "JSDocument", arg);
		return resultdoc;
	}

	
	
}
