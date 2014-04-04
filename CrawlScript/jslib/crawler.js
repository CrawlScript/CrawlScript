importPackage(org.teacrawler);
crawler={
    startNew:function(seedlist,visit,filter,indexpath,threads,depth,resumable){
        if(indexpath==undefined)
        {
            print("input error");
        }
        controller=new Controller();
        for(i in seedlist){
            controller.addSeed(seedlist[i]);
        }

        controller.start(visit,filter,indexpath,threads,depth,resumable);


    }

};