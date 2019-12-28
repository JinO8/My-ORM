一、简单题

1、Mybatis动态sql是做什么的？都有哪些动态sql？简述一下动态sql的执行原理？
答：a、mybatis通过逻辑判断和解析XML中的sql标签，来动态拼接sql，然后执行拼接好的sql，和数据库交互；
    b、9个SQL标签：<if/>、<choose/>、<when/>、<otherwise/>、<trim/>、<when/>、<set/>、<foreach/>、<bind/>；
    c、执行原理为，使用OGNL的表达式，从SQL参数对象中计算表达式的值,根据表达式的值动态拼接SQL，以此来完成动态SQL的功能。
    
2、Mybatis是否支持延迟加载？如果支持，它的实现原理是什么？
答：a、Mybatis仅支持association关联对象和collection关联集合对象的延迟加载，association指的就是一对一，
    collection指的就是一对多查询。在Mybatis配置文件中，可以配置是否启用延迟加载lazyLoadingEnabled=true|false。
    b、原理是，使用CGLIB创建目标对象的代理对象，当调用目标方法时，进入拦截器方法，比如调用a.getB().getName()，
    拦截器invoke()方法发现a.getB()是null值，那么就会单独发送事先保存好的查询关联B对象的sql，把B查询上来，然后调用a.setB(b)，
    于是a的对象b属性就有值了，接着完成a.getB().getName()方法的调用。这就是延迟加载的基本原理。
    
3、Mybatis都有哪些Executor执行器？它们之间的区别是什么？
答：a、Mybatis-3.5.3中总共有5种Executor执行器：BatchExecutor、CachingExecutor、ClosedExecutor、ReuseExecutor、SimpleExecutor；
    b、BatchExecutor：支持批处理的执行器，执行update（没有select，JDBC批处理不支持select），将所有sql都添加到批处理中addBatch()，
                      等待统一执行executeBatch()，它缓存了多个Statement对象，每个Statement对象都是addBatch()完毕后，
                      等待逐一执行executeBatch()批处理。与JDBC批处理相同。
       CachingExecutor：自带事务管理的缓存执行器，可以很灵活的支持一级缓存和二级缓存。
       ClosedExecutor：关闭的执行器，这是在ResultLoaderMap中继承的一个final的静态内部类（不推荐使用）。
       ReuseExecutor：重复利用Statement对象的执行器，执行update或select，以sql作为key查找Statement对象，存在就使用，不存在就创建，
                    用完后，不关闭Statement对象，而是放置于Map内，供下一次使用。简言之，就是重复使用Statement对象。
       SimpleExecutor：简单执行器（默认使用），每执行一次update或select，就开启一个Statement对象，用完立刻关闭Statement对象。
       
4、简述下Mybatis的一级、二级缓存（分别从存储结构、范围、失效场景。三个方面来作答）？
答：存储结构：Mybatis的一级、二级缓存底层存储结构都是map;
    范围:一级缓存是sqlSession级别的，二级缓存是基于namespace设计的夸session的缓存；
    失效场景：a、分布式环境下，二级缓存会失效，因为二级缓存默认是基于本地存储的，可以借助第三方存储，如redis来解决；
             b、Mybatis与Spring进行整合时，一级缓存会失效，mybatis会在每次查询之后自动关闭sqlSession，可以加上@Transactional注解来解决；
             
5、简述Mybatis的插件运行原理，以及如何编写一个插件？
答：Mybatis底层创建Executor/ParameterHandler/ResultSetHander/StatementHandler这4大对象时，都会调用interceptorChain的pluginAll方法，
    这个pluginAll方法就是遍历所有的拦截器，然后顺序执行我们插件的plugin方法,一层一层返回我们原对象(Executor/ParameterHandler/ResultSetHander
    /StatementHandler)的代理对象。当我们调用四大接口对象的方法时候，实际上是调用代理对象的响应方法，代理对象又会调用四大接口对象的实例；
    通过 MyBatis 提供的强大机制，使用插件是非常简单的，只需实现 Interceptor 接口，并指定想要拦截的方法签名即可。
    如下：（伪代码）
    // ExamplePlugin.java
    @Intercepts({@Signature(
      type= Executor.class,
      method = "update",
      args = {MappedStatement.class,Object.class})})
    public class ExamplePlugin implements Interceptor {
      private Properties properties = new Properties();
      public Object intercept(Invocation invocation) throws Throwable {
        // 执行方法之前。。
        Object returnObject = invocation.proceed();
        // 执行方法之后。。
        return returnObject;
      }
      public void setProperties(Properties properties) {
        this.properties = properties;
      }
    }
    <!-- mybatis-config.xml -->
    <plugins>
      <plugin interceptor="org.mybatis.example.ExamplePlugin">
        <property name="someProperty" value="100"/>
      </plugin>
    </plugins>

二、编程题

请完善自定义持久层框架IPersistence，在现有代码基础上添加、修改及删除功能。
（已完成，详见代码My-ORM中的IPersistence模块）
