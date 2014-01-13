var $;
(function() {
	
	_$.db= function(ipparam,dbname,user,password) {
		if(ipparam.constructor === {}.constructor)
		{
			ip=ipparam.ip;
			dbname=ipparam.dbname;
			user=ipparam.user;
			password=ipparam.password;			
		}
		else
			ip=ipparam;
		mysql=new Mysql(ip,dbname,user,password);
		return mysql;
	};
	
	function _$(url,agent)
	{
		return new JSDocument(url,agent);
	}

	
	$=_$;
})();
