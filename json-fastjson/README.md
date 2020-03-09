# json-fastjson

* FastJson工具类

## Maven依赖

```xml
<dependency>
    <groupId>com.github.duanxinyuan</groupId>
    <artifactId>json-fastjson</artifactId>
</dependency>
```

## Fastjson使用示例

```text
    //JSON反序列化
    Test test = FastjsonUtil.from(string, Test.class);
    
    //JSON反序列化
    List<Test> tests = JacksonUtil.fromList(string, Test.class);
    
    //JSON反序列化
    Map<String, Object> map = JacksonUtil.fromMap(string);
    
    //JSON序列化
    String testStr = FastjsonUtil.to(test);
    
    //获取JSON中的单个字符串
    String name = FastjsonUtil.getAsString(string, "name");
              
    //向json中添加属性
    String testStr = JacksonUtil.add(test, "name", value);

    //除去json中的某个属性
    String testStr = JacksonUtil.remove(test, "name");
 
    //修改json中的属性
    String testStr = JacksonUtil.update(test, "name", value);
  
    //JSON格式化
    String testStr = FastjsonUtil.format(test);
       
    //判断字符串是否是json
    boolean isJson = JacksonUtil.isJson(test);
 
    //获取JSON中的单个字段
    String name = JacksonUtil.getAsString(string, "name");
    
    //获取JSON中的单个字段
    int name = JacksonUtil.getAsInt(string, "name");
    
    //获取JSON中的单个字段
    long name = JacksonUtil.getAsLong(string, "name");
    
    //获取JSON中的单个字段
    double name = JacksonUtil.getAsDouble(string, "name");
    
    //获取JSON中的单个字段
    BigInteger name = JacksonUtil.getAsBigInteger(string, "name");
    
    //获取JSON中的单个字段
    BigDecimal name = JacksonUtil.getAsBigDecimal(string, "name");
    
    //获取JSON中的单个字段
    boolean name = JacksonUtil.getAsBoolean(string, "name");
    
    //获取JSON中的单个字段
    byte name = JacksonUtil.getAsByte(string, "name");
    
    //获取JSON中的单个字段
    Test test = JacksonUtil.getAsObject(string, "name", Test.class);
   
    //获取JSON中的单个字段
    List<Test> tests = JacksonUtil.getAsList(string, "name", Test.class);
   
    //获取JSON中的单个字段
    JSONObject jsonObject = JacksonUtil.getAsJsonObject(string, "name");

```
