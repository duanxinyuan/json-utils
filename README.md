# Json-Utils
Json工具类，包含Gson、FastJson、Jackson三个库的工具类

## Maven依赖：
```xml
<dependency>
    <groupId>com.github.duanxinyuan</groupId>
    <artifactId>library-json</artifactId>
    <version>1.1.0</version>
</dependency>
```

## Gson使用示例：
```java
  //JSON解析
  Test test = GsonUtil.from(string, Test.class);
  
  //宽松JSON解析
  Test test = GsonUtil.lenientFrom(string, Test.class);
  
  //JSON序列化
  String testStr = GsonUtil.to(test);

  //获取JSON中的单个字符串
  String name = GsonUtil.to(string,"name");
```

```(java)
例：

Gson：
    GsonUtil.from()/GsonUtil.to()
    
FastjsonUtil：
     FastjsonUtil.from()/FastjsonUtil.to()

JacksonUtil：
    JacksonUtil.from()/JacksonUtil.to()
 
```