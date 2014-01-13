doc=$("http://www.baidu.com");
links=doc.a();
for(var i=0;i<links.size();i++)
{
	link=links.get(i);
	print(link.text());
	print(link.attr("href"));
}
