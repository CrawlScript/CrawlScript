importPackage(org.jsoup);
jsoup={
	parse:function(html){
		return Jsoup.parse(html);		
	}	,

	connect:function(url){
		return Jsoup.connect(url);
	}
	

		
}