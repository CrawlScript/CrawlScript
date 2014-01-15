var $;
(function() {

	_$.db = function(ipparam, dbname, user, password) {
		if (ipparam.constructor === {}.constructor) {
			ip = ipparam.ip;
			dbname = ipparam.dbname;
			user = ipparam.user;
			password = ipparam.password;
		} else
			ip = ipparam;
		mysql = new Mysql(ip, dbname, user, password);
		return mysql;
	};

	function _$(url, httpconfig) {

		if(httpconfig==undefined)
			httpconfig={};
		return new JSDocument(url, httpconfig.agent, httpconfig.proxyip, httpconfig.proxyport);
	}

	$ = _$;
})();
