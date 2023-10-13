# Solon Detector Prometheus

Solon Detector Prometheus 是一个基于 [Solon] 框架的 Prometheus 监控插件，借助 [Micrometer](https://micrometer.io/) 实现，减少在 Solon 中的配置。

允许将 Solon 应用程序的监控数据，导出到 Prometheus 端点。

## 添加依赖

**Maven**

在 pom.xml 中添加以下依赖：

```xml
<dependency>
    <groupId>xyz.chaofan.solon</groupId>
    <artifactId>solon.detector.prometheus</artifactId>
    <version>1.0.0</version>
</dependency>
```

**Gradle**

```groovy
implementation 'xyz.chaofan.solon:solon.detector.prometheus:1.0.0'
```
## 使用

应用程序启动后，访问 `/metrics` 即可查看监控数据。

## 配置

```yaml
solon.detector.prometheus.endpoint: /metrics  # 自定义端点，默认为 /metrics
```

## 示例：自定义指标

```java
@Controller
public class DemoController {
    @Inject
    MeterRegistry meterRegistry;  // 1

    @Init
    public void init() {  // 2
        Counter.builder("request.hello").description("Number of hello requests").register(meterRegistry); 
    }

    @Mapping("/")
    public String hello(@Param(defaultValue = "world") String name) {
        meterRegistry.counter("request.hello").increment(); // 3
        return String.format("Hello %s!", name);
    }
}
```
首先在 1 处注入 `MeterRegistry` 对象

然后在 2 处创建一个 `Counter` 对象，并注册到当前 meterRegistry

最后在 3 处使用 `Counter` 对象记录请求次数。

Counter 对象注册只需要一次，可以在应用程序启动时注册。

访问 `/metrics`

```
# HELP request_hello_total Number of hello requests
# TYPE request_hello_total counter
request_hello_total 1.0
```
