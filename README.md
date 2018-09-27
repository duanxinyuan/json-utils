# Json-Utils
Json工具类, 包含Gson、FastJson、Jackson三个库的工具类

## Maven依赖：
```xml
<dependencies>
<dependency>
    <groupId>com.github.duanxinyuan</groupId>
    <artifactId>library-json-gson</artifactId>
    <version>1.1.0</version>
</dependency>

<dependency>
    <groupId>com.github.duanxinyuan</groupId>
    <artifactId>library-json-jackson</artifactId>
    <version>1.1.0</version>
</dependency>

<dependency>
    <groupId>com.github.duanxinyuan</groupId>
    <artifactId>library-json-fastjson</artifactId>
    <version>1.1.0</version>
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
      String name = GsonUtil.getString(string,"name");
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
      String name = FastjsonUtil.getString(string,"name");
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
    String name = JacksonUtil.getString(string,"name");
    
    //反序列化Yaml
    Test test = JacksonUtil.fromYamlRecource(string, Test.class);
    Test test = JacksonUtil.fromYamlFile(string, Test.class);
    
    //序列化为Yaml
    JacksonUtil.toYaml(test);
  
    //序列化为Yaml文件
    JacksonUtil.toYamlFile(path, test);
    
    //反序列化Properties
    Test test = JacksonUtil.fromPropRecource(string, Test.class);
        
    //序列化为Properties
    JacksonUtil.toProp(test);
  
    //序列化为Properties文件
    JacksonUtil.toPropFile(path, test);

    //反序列化Csv
    Test test = JacksonUtil.fromCsvRecource(string, Test.class);
    Test test = JacksonUtil.fromCsvFile(string, Test.class);
          
    //序列化为Csv
    JacksonUtil.toCsv(test);
  
    //序列化为Csv文件
    JacksonUtil.toCsvFile(path, test);
    JacksonUtil.toCsvFile(path, separator, test);

    //反序列化Xml
    Test test = JacksonUtil.fromXmlRecource(string, Test.class);
    Test test = JacksonUtil.fromXmlFile(string, Test.class);
    Test test = JacksonUtil.fromXml(string, Test.class);
         
    //序列化为Xml
    JacksonUtil.toXml(test);
  
    //序列化为Xml文件
    JacksonUtil.toXmlFile(path, test);
```