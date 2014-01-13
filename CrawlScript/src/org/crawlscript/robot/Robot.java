package org.crawlscript.robot;



import java.io.IOException;
import java.util.ArrayList;
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

public class Robot extends ScriptableObject {

	public String url;
	public Document document;
	
	public final String mobileagent="Mozilla/5.0 (Linux; U; Android 2.2; en-us; Nexus One Build/FRF91) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1";

	public final String winagent="Mozilla/5.0 (Windows NT 5.1; rv:22.0) Gecko/20100101 Firefox/22.0";
	
	public Robot()
	{
		
	}
	
	
	
	@JSConstructor
	public Robot(String url,Object agentObj) {
		
		
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
	}

	@Override
	public String getClassName() {
		// TODO Auto-generated method stub
		return "Robot";
	}

//	@Override
//	public Object getDefaultValue(Class<?> typeHint) {
//		// TODO Auto-generated method stub
//		return this;
//	}
	

	@JSFunction
	public Object doc()
	{
		 Object[] arg = { document.getAllElements()};
         Scriptable myCounter = Context.getCurrentContext().newObject(this, "JSDocument", arg);
         return myCounter;
	//	return new JSDocument(document.getAllElements());
	}
	
	
	/*
	@JSFunction
	public String select(String selectorstr)
	{
		return doc.select(selectorstr).text();
	}
	*/
	@JSFunction
	public void each(String selectorstr,Object fObj)
	{
		 
		    Function f = (Function)fObj;
		   
			
		 	Elements eles=document.select(selectorstr);
			for(int i=0;i<eles.size();i++)
			{
				Object functionArgs[] = { eles.get(i) ,i};
				Object result = f.call(Context.getCurrentContext(), this, this, functionArgs);
				  //String report = "f('my args') = " + Context.toString(result);
				  //System.out.println(report);
			}
		    
		  
	}
	
	
	
	@JSFunction
	public  void travelElement(Object eleObj,String selectorstr,Object fObj)
	{	 
			Element ele=(Element) eleObj;
		    Function f = (Function)fObj;		
		 	Elements eles=ele.select(selectorstr);
			for(int i=0;i<eles.size();i++)
			{
				Object functionArgs[] = { eles.get(i) ,i};
				Object result = f.call(Context.getCurrentContext(), this, this, functionArgs);

			}
		    
		  
	}
	
	@JSFunction
	public String html()
	{
		 return document.html();
		    
		  
	}
	
	@JSFunction
	public ArrayList<String> select(String selectorstr)
	{	ArrayList<String> result=new ArrayList<String>();
		Elements eles=document.select(selectorstr);
		for(int i=0;i<eles.size();i++)
		{
			result.add(eles.get(i).text());
		}
		return result;
	}
	
	@JSFunction
	public ArrayList<Element> selectEle(String selectorstr)
	{	ArrayList<Element> result=new ArrayList<Element>();
		Elements eles=document.select(selectorstr);
		for(int i=0;i<eles.size();i++)
		{
			result.add(eles.get(i));
		}
		return result;
	}
	
	@JSFunction
    public String title() 
	{ 
		return document.title();
	}
	
	/*
	@JSFunction
    public void travel() 
	{ 
		doc.traverse(new NodeVisitor() {
			
			
			
			@Override
			public void tail(Node arg0, int arg1) {
				// TODO Auto-generated method stub
				System.out.println(arg0.toString());
			}
			
			@Override
			public void head(Node arg0, int arg1) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	*/

}
