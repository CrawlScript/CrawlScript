CrawlScript 基于JAVA的网络爬虫脚本语言。

网络爬虫即自动获取网页信息的一种程序，有很多JAVA、C++的网络爬虫类库，但是在这些类库的基础上开发十分繁琐，需要大量的代码才可以完成一个简单的操作。鉴于这个问题，我们开发了CrawlScript这种脚本语言，程序员只需要写2-3行简单的代码，就可以制作一个强大的网络爬虫。同时，CrawlScript由JAVA编写，可以在其他JAVA程序中被简单调用。

运行CrawlScript的方法：用命令行进入工程里的CrawlScript-bin文件夹，java -jar crawlscript.jar即可进入crawlscript的shell。输入doc=$("http://www.baidu.com") ，回车，可看到百度网页的所有文字。

运行CrawlScript源码的方法：将工程中的CrawlScript文件夹，用eclipse以导入已有项目的方式导入，运行项目中的MyShell.java即可打开脚本shell，在shell中即可编写CrawlScript脚本，例如: 
doc=$("http://www.baidu.com");
print(doc.div());

可看到百度网页的所有div中的文字。
