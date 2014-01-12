var $={};
(function() {
	

	_$.initrobotutil = function() {
		return new RobotUtil();
	},

	_$.robot = function(urlparam) {
		var url;
		if (urlparam.url != undefined) {
			url = urlparam.url;
		} else
			url = urlparam;
		r=new Robot(url);
		return r;
	},
	
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
	},

	_$.extract = function(settings) {
		var url = settings.url;
		var selector = settings.selector;
		var func = settings.func;
		var r = $.robot(url);
		r.each(selector, func);
	},
	
	_$.each= function(elements, selector, func) {
		if(selector==undefined)
			selector="*";
		if(func==undefined)
		{
			func=function(ele){	
				print(ele.text());
			};
		}
		_$.robotutil.each(elements, selector, func);
	},

	_$.crawl = function() {
		print("crawl");
		return null;
	}

	
	function _$(urlparam)
	{
		return _$.robot(urlparam).doc();
	}

	
	var init = function() {
		_$.robotutil = _$.initrobotutil();
	};
	init();
	$=_$;
})();

var sum = function(ele) {
	print(ele.text());
	write("resultdata/mylog.txt", ele.text());
}
