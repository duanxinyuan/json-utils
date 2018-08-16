# Json-Utils
Json工具类, 包含Gson、FastJson、Jackson三个库的工具类

## Maven依赖：
```xml
<dependency>
    <groupId>com.github.duanxinyuan</groupId>
    <artifactId>library-json</artifactId>
    <version>1.3.0</version>
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
  
  //JSON格式化
  String testStr = GsonUtil.format(test);

  //获取JSON中的单个字符串
  String name = GsonUtil.getString(string,"name");
  
```

## Fastjson使用示例：
```java
例：
  //JSON解析
  Test test = FastjsonUtil.from(string, Test.class);
  
  //JSON格式化
  String testStr = FastjsonUtil.format(test);

  //JSON序列化
  String testStr = FastjsonUtil.to(test);

  //获取JSON中的单个字符串
  String name = FastjsonUtil.getString(string,"name");

```

## Jackson使用示例：
```java
  //JSON解析
  Test test = JacksonUtil.from(string, Test.class);
   
  //JSON格式化
  String testStr = JacksonUtil.format(test);

  //JSON序列化
  String testStr = JacksonUtil.to(test);

  //获取JSON中的单个字符串
  String name = JacksonUtil.getString(string,"name");

```