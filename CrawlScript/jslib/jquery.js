load("jslib/urllib.js");
load("jslib/jsoup.js");
var $;
(function() {
	
	
	function _$(url, httpconfig) {
		var html=urllib.urlopen(url,httpconfig);
		return jsoup.parse(html);
	}

	$ = _$;
})();