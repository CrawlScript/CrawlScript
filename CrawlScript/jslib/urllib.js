

importPackage(java.net);
importPackage(org.tea.core);

function urlencode(str){
	return URLEncoder.encode(str);
}

function urldecode(str){
	return URLDecoder.decode(str);
}

urllib={
		
		urlopen:function(url, httpconfig) {
			importPackage(org.teaplugin.httpclient);
			
			if(httpconfig!=undefined){
				if(httpconfig.params!=undefined)
					url=url+"?"+urllib.param_encode(httpconfig.params);				
			}
			
			httppage=new HttpPage();	
			return httppage.getHtmlAuto(url,null,null,null);
		},

		download:function(url,filename){
            HttpUtils.download(url,filename);
		},
		
		param_encode:function(params){
			var result="";
			var i=0;
			for(param in params){
				result+=param+"="+urlencode(params[param])+"&";
				i++;
			}
			if(i>0)
				result=result.substring(0,result.length-1);
			return result;			
		},

		getRealURL:function(url){
		    //return HttpUtils.getRealURL(url);
            jurl=new URL(url);
            con=HttpURLConnection(jurl.openConnection());
            con.getResponseCode();
            return con.getURL();
		}


		
}


