importPackage(java.io);

/* open a file to read,write,append */
function open(filepath,mode,charset){
	if(charset==undefined)
		charset="utf-8";
	if(mode==undefined)
		mode='r';
	/* read file */	
	if(mode=='r'){
		var raw=new BufferedReader(new InputStreamReader(new FileInputStream(filepath),charset));
		return new TextIOWrapper(raw,mode,charset); 	
	}
	/* write file */
	else if((mode=="w")||(mode="a")){
		var append=false;
		if(mode=="a")
			append=true;
		var raw=new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filepath,append),charset));
		return new TextIOWrapper(raw,mode,charset);
	}
}

/*a wrapper to operate text in file,read,write*/
function TextIOWrapper(_raw,mode,charset){
	this.raw=_raw;	
	this.mode=mode;
	this.read=function(){
		var result="";
		var i=0;
		while((temp=this.readline())!=null){
			if(i==0)
			{
				i=1;			
			}
			else
				result+='\n';		
			result+=temp;	
		}	
		return result;
	};
	
	this.readline=function(){
		return this.raw.readLine();
	};

	this.write=function(text){
		this.raw.write(text,0,text.length);	
	};

	this.flush=function(){
		this.raw.flush();	
	}
	
	this.close=function(){
		this.raw.close();	
	}
	
}
