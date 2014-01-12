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
import org.mozilla.javascript.annotations.JSConstructor;
import org.mozilla.javascript.annotations.JSFunction;

public class RobotUtil extends ScriptableObject {

	
	
	@JSConstructor
	public RobotUtil()
	{
		
	}
	
	

	@Override
	public String getClassName() {
		// TODO Auto-generated method stub
		return "RobotUtil";
	}

//	@Override
//	public Object getDefaultValue(Class<?> typeHint) {
//		// TODO Auto-generated method stub
//		return this;
//	}
	
	

	
	@JSFunction
	public  void each(Object eleObj,String selectorstr,Object fObj)
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
	
	

}
