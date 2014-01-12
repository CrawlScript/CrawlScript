
function visit(ele)
{
		if(ele.text().contains("返回"))
		{
			write("resultdata/mylog.txt",ele.text());
		}
}


var list=Array();

function visitLink(ele)
{
	if(!ele.attr("href").startsWith("http"))
		return;
	list[list.length]=ele.attr("href");
	print(list[list.length-1]);
}


function getLinkList(seedurl)
{	

	list=Array();
	
	var robot=$.robot(seedurl);
	
	robot.each("a",visitLink);
	return list;
}


var index=0;
function download(pageurl)
{
	print("download:"+pageurl);
	index++;
	var myr=$.robot(pageurl);
	write("resultdata/"+index.toString()+".txt",myr.html());
}


function crawlList(urllist,pagevisitor,max)
{
	var count=urllist.length;
	if(max!=undefined)
	{
		
		if(urllist.length>max)
			count=max;
	}
	
	for(var i=0;i<count;i++)
	{
		pagevisitor(urllist[i]);
	}
	
}

function crawl(url,pagevisitor,max)
{
	var urllist=getLinkList(url);
	var count=urllist.length;
	if(max!=undefined)
	{
		
		if(urllist.length>max)
			count=max;
	}
	
	for(var i=0;i<count;i++)
	{
		pagevisitor(urllist[i]);
	}
	
}
