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
原因就是调试步骤中设置的"address=5005"端口被占用了导致的.如果想要调试命名正常运行,需要结束占用5005端口的进程.  
在windows dos中执行命令:  
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

### 修改输出apk名字  
在Plugin#apply(Project)方法中:  
```
// If you use each() to iterate through the variant objects,
// you need to start using all(). That's because each() iterates
// through only the objects that already exist during configuration time—
// but those object don't exist at configuration time with the new model.
// However, all() adapts to the new model by picking up object as they are
// added during execution.
 project.android.applicationVariants.all { variant ->
            variant.outputs.all {
                outputFileName = "${variant.name}-${variant.versionName}.apk"
                //output.outputFile = new File("\", nameMap(file.getName()))
            }
        }
```
1.在3.0以上不能通过output.outputFile = ...的方式修改输出路径了,只能修改名字.  
[Android 插件 3.0.0 移除了某些 API。如果您使用这些 API，您的 build 将会出现异常。例如，您无法再使用 Variants API 访问 outputFile() 对象](https://developer.android.com/studio/releases/gradle-plugin?hl=zh-cn#behavior_changes)  
[API 变更](https://developer.android.com/studio/known-issues?hl=zh-cn#variant_api) 
<br>
<br>
2.android.applicationVariants.all.这里要使用all而不是each.  
```
   /**
     * Executes the given closure against all objects in this collection,
     * and any objects subsequently added to this collection.
     * The object is passed to the closure as the     closure
     * delegate. Alternatively, it is also passed as a parameter.
     *
     * @param action The action to be executed
     */
    void all(Closure action);
```
可以得知只要来个新的就会调用一下.each估计只会当时调用下.  
<br>
3.在project.afterEvaluate{}中尝试修改outputFileName也是不行的.貌似已经配置完毕了所以会报错.

### 调试
插件代码模块设置成独立的项目不配置到主项目的settings.gradle中.这样做的好处是:  
1.如果插件代码模块配置到主项目里.一旦发布了一个会导致构建中断的插件,重新发布插件的时候必须先从主项目中把插件依赖移除,等插件发布成功以后
再将插件依赖加入.非常麻烦.而将插件代码模块设置成独立项目以后,重新发布插件代码到本地仓库的时候不会执行主项目的构建脚本,如果上一次发布的插件  
有问题也没有关系,因为不会执行了.  
<br>
2.这样也能进行调试  

### 资料
[Android Gradle 插件开发入门指南（一）,hello world](https://juejin.cn/post/6887581345384497165#heading-11)  
[Gradle 插件hello world](https://github.com/lenebf/GradlePluginTutorial/tree/main/hello-plugin)  
[The Gradle Daemon](https://docs.gradle.org/6.1.1/userguide/gradle_daemon.html#daemon_faq)  
[Android Gradle Plugin3.1版本,新版是另一个地址](https://google.github.io/android-gradle-dsl/3.1/com.android.build.gradle.AppExtension.html)  
[gradle插件,脚本调试方法](https://blog.csdn.net/xx326664162/article/details/91456018)
