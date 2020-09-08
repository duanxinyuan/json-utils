# json-utils
Json工具类, 包含Gson、FastJson、Jackson三个库的工具类，其中Jackson支持Scala版本

## Maven依赖：
```xml
<dependencies>
<dependency>
    <groupId>com.github.duanxinyuan</groupId>
    <artifactId>json-fastjson</artifactId>
    <version>1.8.0</version>
</dependency>

<dependency>
    <groupId>com.github.duanxinyuan</groupId>
    <artifactId>json-gson</artifactId>
     <version>1.8.0</version>
</dependency>

<dependency>
    <groupId>com.github.duanxinyuan</groupId>
    <artifactId>json-jackson</artifactId>
    <version>1.8.0</version>
</dependency>

<dependency>
    <groupId>com.github.duanxinyuan</groupId>
    <artifactId>json-jackson-extend</artifactId>
    <version>1.8.0</version>
</dependency>

<dependency>
    <groupId>com.github.duanxinyuan</groupId>
    <artifactId>json-jackson-scala</artifactId>
    <version>1.8.0</version>
</dependency>
</dependencies>
```

## Gson使用示例：
```text
     //JSON反序列化
      Test test = GsonUtil.from(string, Test.class);
      
      //宽松JSON反序列化
      Test test = GsonUtil.fromLenient(string, Test.class);
      
      //JSON序列化
      String testStr = GsonUtil.to(test);
    
      //JSON序列化为文件
      GsonUtil.toFile(path, test);
      
      //JSON格式化
      String testStr = GsonUtil.format(test);
    
      //获取JSON中的单个字符串
      String name = GsonUtil.getAsString(string,"name");
```

## Fastjson使用示例：
```text
     //JSON反序列化
      Test test = FastjsonUtil.from(string, Test.class);
      
      //JSON格式化
      String testStr = FastjsonUtil.format(test);
    
      //JSON序列化
      String testStr = FastjsonUtil.to(test);
    
      //获取JSON中的单个字符串
      String name = FastjsonUtil.getAsString(string,"name");
```

## Jackson使用示例：
```text
    
    //JSON反序列化
    Test test = JacksonUtil.from(string, Test.class);
    
    //JSON格式化
    String testStr = JacksonUtil.format(test);
    
    //JSON序列化
    String testStr = JacksonUtil.to(test);
    
    //JSON序列化为文件
    String testStr = JacksonUtil.toFile(path, test);
    
    //获取JSON中的单个字符串
    String name = JacksonUtil.getAsString(string,"name");
    
```

## Jackson-Extend使用示例：
```text
    //反序列化Yaml
    Test test = JacksonExtendUtil.fromYamlResource(string, Test.class);
    Test test = JacksonExtendUtil.fromYamlFile(string, Test.class);
    
    //序列化为Yaml
    JacksonExtendUtil.toYaml(test);
  
    //序列化为Yaml文件
    JacksonExtendUtil.toYamlFile(path, test);
    
    //反序列化Properties
    Test test = JacksonExtendUtil.fromPropResource(string, Test.class);
        
    //序列化为Properties
    JacksonExtendUtil.toProp(test);
  
    //序列化为Properties文件
    JacksonExtendUtil.toPropFile(path, test);

    //反序列化Csv
    Test test = JacksonExtendUtil.fromCsvResource(string, Test.class);
    Test test = JacksonExtendUtil.fromCsvFile(string, Test.class);
          
    //序列化为Csv
    JacksonExtendUtil.toCsv(test);
  
    //序列化为Csv文件
    JacksonExtendUtil.toCsvFile(path, test);
    JacksonExtendUtil.toCsvFile(path, separator, test);

    //反序列化Xml
    Test test = JacksonExtendUtil.fromXmlResource(string, Test.class);
    Test test = JacksonExtendUtil.fromXmlFile(string, Test.class);
    Test test = JacksonExtendUtil.fromXml(string, Test.class);
         
    //序列化为Xml
    JacksonExtendUtil.toXml(test);
  
    //序列化为Xml文件
    JacksonExtendUtil.toXmlFile(path, test);
```
