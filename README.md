# dubbo全局打印trace id
#### 1、引入依赖
```xml
<dependency>
    <groupId>com.github.dreamroute</groupId>
    <artifactId>tracer-spring-boot-starter</artifactId>
    <version>最新版本</version>
</dependency>
```

##### 支持如下配置
```properties
# 是否开启trace id打印【默认开启】
tracer.enable-trace-id = true
```

#### 2、初始化trace id
在服务消费端创建一个过滤器，如果已经存在过滤器了（拦截器也可以，只要是在请求入口处即可），那么在存在的过滤器基础之上改造也可，如果有多个过滤器，最好将这部分逻辑放在最外层过滤器中，在过滤器中实现大概如下逻辑：
```java
public class JavaFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {
        // traceId可以自定义，可以使用uuid，也可以把用户信息拼在里面，排查问题方便一点
        // 这里举例使用的是uuid
        String traceId = UUID.randomUUID().toString().replace("-", "");
        TracerUtil.setTraceId(traceId);
        chain.doFilter(request, response);
        TracerUtil.clearTraceId();
    }
}
```

#### 3、定义引入dubbo过滤器
1. 创建`src/main/resource/META-INF/dubbo`目录（如果已经存在就忽略）；
3. 在`dubbo`目录下创建文件`com.alibaba.dubbo.rpc.Filter`；
4. 在`com.alibaba.dubbo.rpc.Filter`文件里加入内容：
   1. 如果是服务消费方，添加`TracerConsumer = com.github.dreamroute.tracer.starter.TracerConsumer`
   2. 如果是服务提供方，添加`TracerProvider = com.github.dreamroute.tracer.starter.TracerProvider`
   3. 如果既是消费方又是提供方，则将上面两行都填加进去，写成两行

#### 4、logback.xml文件格式的修改
在logback.xml的`<appender>/<encoder>/<Pattern>`标签下增加[%X{traceId}]配置，例如:
```xml
 <appender name="CONSOLE" class="ch.qos.logback.core.ConsoleAppender">
     <filter class="ch.qos.logback.classic.filter.ThresholdFilter">
         <level>info</level>
     </filter>
     <encoder>
         <Pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} %-5p [${logging.app-name}] [%X{traceUserId}] [%X{traceLogId}] [%X{traceId}] --- [%t] %logger:%L : %m%n
         </Pattern>
         <charset>UTF-8</charset>
     </encoder>
 </appender>
```

#### 5、如此配置下来，在你的服务消费方和提供方每一行日志都会带上traceId了
