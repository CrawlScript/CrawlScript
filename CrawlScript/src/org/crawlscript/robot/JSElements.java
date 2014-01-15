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

public class JSElements extends ScriptableObject {

	Elements data;
	
	public JSElements()
	{
		
	}
	
	@JSConstructor
	public JSElements(Object data) {
			this.data=(Elements)data;
	}
	
	@JSFunction
	public Object a(Object index)
	{
		return createDoc("a",index);
	}
	
	@JSFunction
	public String attr(String attrname)
	{
		String result="";
		for(int i=0;i<data.size();i++)
		{
			if(data.get(i).hasAttr(attrname))
				result+=data.get(i).attr(attrname)+"\n";
		}
		if(result.length()>0)
		{
			result=result.substring(0,result.length()-1);
		}
		return result;
	}
	
	@JSFunction
	public Object body(Object index)
	{
		return createDoc("body",index);
	}
	
	
	
	protected Scriptable createDoc(String selector,Object index)
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
	public Object div(Object index)
	{
		return createDoc("div",index);
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
			}		    		  
	}
	

	@JSFunction
	public Object first()
	{
		Object[] arg={data.first()};
		Scriptable resultdoc=Context.getCurrentContext().newObject(this, "JSElement",arg);
		return resultdoc;
	}
	
	@JSFunction
	public Object get(int i) {
		if(data.get(i)==null)
			return null;
		Object[] arg={data.get(i)};
		Scriptable resultdoc=Context.getCurrentContext().newObject(this, "JSElement",arg);
		return resultdoc;
		
	}
	
	
	
	@Override
	public String getClassName() {
		// TODO Auto-generated method stub
		return "JSElements";
	}
	

	
	@JSFunction
	public String html()
	{
		return data.outerHtml();
	}
	
	
	
	@JSFunction
	public Object img(Object index)
	{
		return createDoc("img",index);
	}

	@JSFunction
	public String innerhtml()
	{
		return data.html();
	}
	
	
	@JSFunction
	public Object last()
	{
		Object[] arg={data.last()};
		Scriptable resultdoc=Context.getCurrentContext().newObject(this, "JSElement",arg);
		return resultdoc;
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
	public Object td(Object index)
	{
		return createDoc("td",index);
	}
	
	@JSFunction
	public Object tr(Object index)
	{
		return createDoc("tr",index);
	}
	
	
	
	
	
	
	
	@JSFunction
	public Object p(Object index)
	{
		return createDoc("p",index);
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
	public Object select(String selector)
	{
		
		Object[] arg={data.select(selector)};
		Scriptable resultdoc=Context.getCurrentContext().newObject(this, "JSElements",arg);
		return resultdoc;
	}
	
	@JSFunction
	public int size()
	{
		return data.size();
		
	}
	
	
	
	@JSFunction
	public String text()
	{
		return data.text();
	}
	
	@JSFunction
	public String type()
	{
		return "elements";
	}
	
	@JSFunction
	@Override
	public String toString()
	{
		return text();
	}
	
	

	
	

	



	
	
}
