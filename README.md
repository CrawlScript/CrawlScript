CrawlScript 基于JAVA的网络爬虫脚本语言。

CrawlScript主页：http://crawlscript.github.io/

网络爬虫即自动获取网页信息的一种程序，有很多JAVA、C++的网络爬虫类库，但是在这些类库的基础上开发十分繁琐，需要大量的代码才可以完成一个简单的操作。鉴于这个问题，我们开发了CrawlScript这种脚本语言，程序员只需要写2-3行简单的代码，就可以制作一个强大的网络爬虫。同时，CrawlScript由JAVA编写，可以在其他JAVA程序中被简单调用。

CrawlScript是跨平台的,在任何有JDK环境的电脑上都可以运行，无论是windows、linux还是unix。

运行CrawlScript的方法：
1.WINDOWS：进入CrawlScript文件夹，双击crawlscript.bat。
2.LINUX：用命令行进入CrawlScript文件夹，执行sh crawlscript.sh。
测试：输入doc=$("http://www.baidu.com") ，回车，可看到百度网页的所有文字。
      继续输入print(doc.a()) ，回车，可看到百度网页的所有超链接中的文字。

相关教程和API可查看CrawlScript主页：http://crawlscript.github.io/
