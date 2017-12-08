# SpringBoot 学习文档

#### 优化：AOP切面的优先级
由于通过AOP实现，程序得到了很好的解耦，但是也会带来一些问题，比如：我们可能会对Web层做多个切面，校验用户，校验头信息等等，这个时候经常会碰到切面的处理顺序问题。
所以，我们需要定义每个切面的优先级，我们需要@Order(i)注解来标识切面的优先级。i的值越小，优先级越高。假设我们还有一个切面是CheckNameAspect用来校验name必须为didi，
我们为其设置@Order(10)，而上文中WebLogAspect设置为@Order(5)，所以WebLogAspect有更高的优先级，这个时候执行顺序是这样的：
在@Before中优先执行@Order(5)的内容，再执行@Order(10)的内容
在@After和@AfterReturning中优先执行@Order(10)的内容，再执行@Order(5)的内容
所以我们可以这样子总结：
- 在切入点前的操作，按order的值由小到大执行
- 在切入点后的操作，按order的值由大到小执行
在实际中order值可以设置为负值，确保是第一个进行执行的。

#### 测试
Spring Boot 1.4 解决的另外一个问题是，可以测试一段代码。不用启动服务器。并且不用启动整个Spring上下文，Spring Boot 1.4 通过新的Test Slicing的特性 就可以完成，这个特性被设计成可以至启动一小片的Spring上下文。这时的测试单个的代码片段更加容易了。你可以这样去测试你的应用中的特定代码片段
- MVC 片段： 通过@WebMvcTest注解测试Controller代码；
- JPA 片段： 通过@DataJpaTest注解测试Spring Data JPA repository代码；
- JSON 片段： 通过@JsonTest注解JSON序列化代码。

注解
- @RunWith(SpringRunner.class) 告诉Spring运行使用的JUnit测试支持。SpringRunner是SpringJUnit4ClassRunner的新名字，这个名字只是让名字看起来简单些。
- @SpringBootTest意思是“带有Spring Boot支持的引导程序”（例如，加载应用程序、属性，为我们提供Spring Boot的所有精华部分）。
- webEnvironment属性允许为测试配置特定的“网络环境”。

MOCK：提供一个Mock的Servlet环境，内置的Servlet容器并没有真实的启动，主要搭配使用@AutoConfigureMockMvc
RANDOM_PORT： 提供一个真实的Servlet环境，也就是说会启动内置容器，然后使用的是随机端口
DEFINED_PORT：这个配置也是提供一个真实的Servlet环境，使用的默认的端口，如果没有配置就是8080
NONE：这是个神奇的配置，跟Mock一样也不提供真实的Servlet环境。
classes如果想要加载一个特定的配置，可以用@SpringBootTest的classes属性。在这个实例中，省略classes就意味着测试要首次尝试从任意一个inner-classes中加载@configuration，如果这个尝试失败了，它会在你主要的@SpringBootApplicationclass中进行搜索。


#### 关于日志
1.4版本后改了很多东西，如果想用log4j，就要引入log4j2
```
<dependency> 
   <groupId>org.springframework.boot</groupId>
   <artifactId>spring-boot-starter-log4j2</artifactId>
</dependency>
```
在引入了log4j依赖之后，只需要在src/main/resources目录下加入log4j-spring.properties配置文件，就可以开始对应用的日志进行配置使用。
在开发环境，我们只是输出到控制台没有问题，但是到了生产或测试环境，或许持久化日志内容，方便追溯问题原因。可以通过添加如下的appender内容，按天输出到不同的文件中去，同时还需要为log4j.rootCategory添加名为file的appender，这样root日志就可以输出到logs/springboot.log文件中了。
```
# LOG4J配置
log4j.rootCategory=INFO,stdout,file
```
我们还可以对日志进行分类输出，输出到数据库等等。

#### 多环境配置
相信使用过一段时间Spring Boot的用户，一定知道这条命令：java -jar xxx.jar --server.port=8888，通过使用--server.port属性来设置xxx.jar应用的端口为8888。
在命令行运行时，连续的两个减号--就是对application.properties中的属性值进行赋值的标识。
在SpringBoot中使用多环境配置的前提是，配置文件名需要满足application-{profile}.properties的格式，其中{profile}对应你的环境标识，比如：
- application-dev.properties：开发环境
- application-test.properties：测试环境
- application-prod.properties：生产环境
至于哪个具体的配置文件会被加载，需要在application.properties文件中通过spring.profiles.active属性来设置，其值对应{profile}值。
如：spring.profiles.active=test就会加载application-test.properties配置文件内容
在这三个文件均都设置不同的server.port属性，如：dev环境设置为8080，test环境设置为9090，prod环境设置为80，application.properties中设置spring.profiles.active=dev，就是说默认以dev环境设置。

将项目打成jar包，执行java -jar xxx.jar --spring.profiles.active=test，可以观察到服务端口被设置为9090，也就是测试环境的配置（test）。
**同样我们可以使用其来限制不同的业务路逻辑，使用@Profile即可，还可以控制日志输出级别**
#### 注意
```java -jar xxx.jar  --spring.profiles.active=prod```，我们在java Run configurations配置的启动参数在单元测试中是无效的，如果想在单元测试中激活profiles的话，那么是需要进行配置的：
```
/*
 * @ActiveProfiles，可以指定一个或者多个 profile，
 * 这样我们的测试类就仅仅加载这些名字的 profile 中定义的 bean 实例。
 * 这里激活application-prod.properties配置文件.
 */
@ActiveProfiles("prod")
public class AppTest...
```

#### 内嵌Server配置
```
#项目contextPath，一般在正式发布版本中，我们不配置
server.context-path=/springboot
# 错误页：指定发生错误时，跳转的URL。请查看BasicErrorController。
server.error.path=/error
# 服务端口，默认为8080
server.port=8080
# session最大超时时间(分钟)，默认为30
server.session-timeout=60
# 该服务绑定IP地址，启动服务器时如本机不是该IP地址则抛出异常启动失败，只有特殊需求的情况下才配置
# server.address=192.168.16.11

# tomcat最大线程数，默认为200
server.tomcat.max-threads=800
# tomcat的URI编码
server.tomcat.uri-encoding=UTF-8
# 存放Tomcat的日志、Dump等文件的临时文件夹，默认为系统的tmp文件夹（如：C:\Users\Angel\AppData\Local\Temp）
server.tomcat.basedir=D:/springboot-tomcat-tmp
# 打开Tomcat的Access日志，并可以设置日志格式的方法：
#server.tomcat.access-log-enabled=true
#server.tomcat.access-log-pattern=
# accesslog目录，默认在basedir/logs
#server.tomcat.accesslog.directory=
# 日志文件目录
logging.path=H:/springboot-tomcat-tmp
# 日志文件名称，默认为spring.log
logging.file=myapp.log

```

#### 使用@Async实现异步调用
 “异步调用”对应的是“同步调用”，同步调用指程序按照定义顺序依次执行，每一行程序都必须等待上一行程序执行完成之后才能执行；异步调用指程序在顺序执行时，不等待异步调用的语句返回结果就执行后面的程序。
 加上@@Async注解后，就能简单的将原来的同步函数变为异步函数，为了让@Async注解能够生效，还需要在Spring Boot的主程序中配置@EnableAsync


#### 自定义属性

```@Value(“${key:defaultVlaue}”) ```的形式进行默认值设置；
我们还可以定义数组，定义List<String>接收就可以了；
**松散的绑定；**Spring Boot使用宽松的规则用于绑定属性到@ConfigurationProperties beans,所以Environment属性名和bean属性名不需要精确匹配。常见的示例中有虚线分隔的（比如，context-path绑定到contextPath），环境属性大写转为小写字母（比如：PORT绑定port）。
这就是为什么context-path,spring.jpa.show-sql，其实是被解释为contextPath和showSql了，不然要是指定定义一个show-sql变量是无法编译通过的。
我们还可以自定义**随机数**，
我们可以自定义一个company.properties文件，通过@ConfigurationProperties(prefix = "com.kfit.company",locations="classpath:company.properties")导入
之后我们在App.java启动类中或者其它的类也是可以的。我们甚至可以@Max(value = 99)，对属性进行校验。


#### mybatis的一些小问题
当 insert 语句中只有一个参数的，对应的void save方法不需要做任何特殊处理（不需要加@Param也是可以对应上的），当有多个参数的时候，需要使用@Param注解进行字段的对应。。


#### IOC
IOC全称Inversion of Control，控制反转，这是一种设计思想。在Java开发中，ioc意味着将你设计好的对象交给容器控制，而不是传统的在你的对象内部直接控制。


#### SpringBoot默认是动态代理模式


#### Swagger2
Swagger 是一个规范和完整的框架，用于生成、描述、调用和可视化 RESTful 风格的 Web 服务。总体目标是使客户端和文件系统作为服务器以同样的速度来更新。文件的方法，参数和模型紧密集成到服务器端的代码，允许API来始终保持同步。Swagger 让部署管理和使用功能强大的API从未如此简单。


