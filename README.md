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























