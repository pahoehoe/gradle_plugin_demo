# gradle_plugin_demo

### 调试时遇到问题  
执行调试步骤
```
gradlew --no-daemon -Dorg.gradle.debug=true -Dkotlin.daemon.jvm.options="-Xdebug,-Xrunjdwp:transport=dt_socket,address=5005,server=y,suspend=n" :app:clean :app:assemble
```
但是提示:
```
ERROR: transport error 202: bind failed: Address already in use
ERROR: JDWP Transport dt_socket failed to initialize, TRANSPORT_INIT(510)
JDWP exit error AGENT_ERROR_TRANSPORT_INIT(197): No transports initialized [debugInit.c:750]

```
原因就是调试步骤中设置的"address=5005"端口被占用了.在windows dos中执行命令:  
```
netstat -ano | findstr "5005"
```
输出:  
```
C:\Users\Administrator>netstat -ano | findstr "5005"
  TCP    0.0.0.0:5005           0.0.0.0:0              LISTENING       4448
  TCP    [::1]:50732            [::1]:50051            SYN_SENT        1540
```
可以看到5005端口被pid=4448的进程占用了.这个时候可以从资源管理起中通过pid找到对应的进程来关闭他:  
1.打开资源管理器-点击进程选项卡  
2.点击 查看-选择列-把pid勾上  
3.然后在进程选项卡的进程列表中找到对应pid的进程,右键-结束进程  
<br>
再次输入开头的调试命令就不会报错了  
[Maven Eclipse Debug “JDWP Transport dt_socket failed to initialize, TRANSPORT_INIT(510)”](https://stackoverflow.com/questions/8428333/maven-eclipse-debug-jdwp-transport-dt-socket-failed-to-initialize-transport-in)


### 资料
[Android Gradle 插件开发入门指南（一）,hello world](https://juejin.cn/post/6887581345384497165#heading-11)  
[Gradle 插件hello world](https://github.com/lenebf/GradlePluginTutorial/tree/main/hello-plugin)  
[The Gradle Daemon](https://docs.gradle.org/6.1.1/userguide/gradle_daemon.html#daemon_faq)
