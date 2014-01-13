doc=$("http://www.baidu.com");

doc.traverse(function(e,type){
	if(type==0)
		print(e);
	else if(type==1)
		print(e.tag());
		
});
