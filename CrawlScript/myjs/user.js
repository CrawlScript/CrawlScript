function crawl()
{
	var baseurl="http://www.ahnw.gov.cn/2006schq/price.asp?BigClass=&MarketID=0&LittleClass=&Seltype=1&KeyStr=%B0%B2%BB%D5&page=";
	for(var i=1;i<=27;i++)
	{
		
		var url=baseurl+i;
		pagevisitor(url);
		
	}
	
}

function pagevisitor(pageurl)
{
	print(pageurl);
	var settings={  "url":pageurl,
					"selector":"table[cellpadding=2]>tbody>tr",
					"func":elementvisitor};
	$.extract(settings);
}

function elementvisitor(ele,index)
{
	if(index==0)
		return;
	$.each(ele,"td",function(e,i){
	print(e.text());
		//	write("resultdata/price.txt",i.toString()+e.text()+" ");
	});
	//write("resultdata/price.txt","\n");
	
}




