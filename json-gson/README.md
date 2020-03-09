# json-gson

* Gson工具类

## Maven依赖

```xml
<dependency>
    <groupId>com.github.duanxinyuan</groupId>
    <artifactId>json-gson</artifactId>
</dependency>
```

## 使用示例

```text
    //JSON反序列化
    Test test = GsonUtil.from(string, Test.class);
    
    //JSON反序列化
    List<Test> tests = GsonUtil.fromList(string, Test.class);
    
    //JSON反序列化
    Map<String, Object> map = GsonUtil.fromMap(string);
    
    //宽松JSON反序列化
    Test test = GsonUtil.fromLenient(string, Test.class);
    
    //JSON序列化
    String testStr = GsonUtil.to(test);
    
    //JSON序列化为文件
    GsonUtil.toFile(path, test);
   
    //向json中添加属性
    String testStr = GsonUtil.add(test, "name", value);

    //除去json中的某个属性
    String testStr = GsonUtil.remove(test, "name");
 
    //修改json中的属性
    String testStr = GsonUtil.update(test, "name", value);
       
    //JSON格式化
    String testStr = GsonUtil.format(test);
       
    //判断字符串是否是json
    boolean isJson = GsonUtil.isJson(test);
 
    //获取JSON中的单个字段
    String name = GsonUtil.getAsString(string, "name");
    
    //获取JSON中的单个字段
    int name = GsonUtil.getAsInt(string, "name");
    
    //获取JSON中的单个字段
    long name = GsonUtil.getAsLong(string, "name");
    
    //获取JSON中的单个字段
    double name = GsonUtil.getAsDouble(string, "name");
    
    //获取JSON中的单个字段
    BigInteger name = GsonUtil.getAsBigInteger(string, "name");
    
    //获取JSON中的单个字段
    BigDecimal name = GsonUtil.getAsBigDecimal(string, "name");
    
    //获取JSON中的单个字段
    boolean name = GsonUtil.getAsBoolean(string, "name");
    
    //获取JSON中的单个字段
    byte name = GsonUtil.getAsByte(string, "name");
    
    //获取JSON中的单个字段
    Test test = GsonUtil.getAsObject(string, "name", Test.class);
   
    //获取JSON中的单个字段
    List<Test> tests = GsonUtil.getAsList(string, "name", Test.class);
   
    //获取JSON中的单个字段
    JsonElement jsonElement = GsonUtil.getAsJsonObject(string, "name");
    
    
```
