# json-jackson

* Jackson工具类

## Maven依赖

```xml
<dependency>
    <groupId>com.github.duanxinyuan</groupId>
    <artifactId>json-jackson</artifactId>
</dependency>
```

## 使用示例

```java
import com.dxy.library.json.jackson.JacksonUtil;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

/**
 * @author duanxinyuan
 * 2019/1/21 18:17
 */
public class JacksonTest {

    @Test
    public void testJackson() {
        Person person = Person.newPerson();
        ArrayList<Person> personList = new ArrayList<>();
        personList.add(person);
        System.out.println(JacksonUtil.fromList(JacksonUtil.to(personList), Person.class));

        String jacksonStr = JacksonUtil.to(person);
        System.out.println(jacksonStr);
        System.out.println(JacksonUtil.getAsString(jacksonStr, "name"));
        System.out.println(JacksonUtil.getAsInt(jacksonStr, "age"));
        System.out.println(JacksonUtil.getAsBoolean(jacksonStr, "man"));
        System.out.println(JacksonUtil.getAsBigDecimal(jacksonStr, "money"));
        System.out.println(JacksonUtil.getAsList(jacksonStr, "trait", String.class));
        System.out.println(JacksonUtil.format(jacksonStr));
        Person from = JacksonUtil.from(jacksonStr, Person.class);
        System.out.println(JacksonUtil.to(from));

        Assert.assertNotNull(JacksonUtil.getAsString(jacksonStr, "name"));
    }

    /**
     * 测试兼容情况
     */
    @Test
    public void test() {
        String json = "{\"code\":\"200\",\"id\":\"2001215464647687987\",\"message\":\"success\",\"amount\":\"1.12345\",\"amount1\":\"0.12345\",\"isSuccess\":\"true\",\"isSuccess1\":\"1\",\"key\":\"8209167202090377654857374178856064487200234961995543450245362822537162918731039965956758726661669012305745755921310000297396309887550627402157318910581311\"}";
        System.out.println(JacksonUtil.getAsInt(json, "code"));
        System.out.println(JacksonUtil.getAsLong(json, "id"));
        System.out.println(JacksonUtil.getAsString(json, "message"));
        System.out.println(JacksonUtil.getAsDouble(json, "amount"));
        System.out.println(JacksonUtil.getAsDouble(json, "amount1"));
        System.out.println(JacksonUtil.getAsBigDecimal(json, "amount"));
        System.out.println(JacksonUtil.getAsBigDecimal(json, "amount1"));
        System.out.println(JacksonUtil.getAsBoolean(json, "isSuccess"));
        System.out.println(JacksonUtil.getAsBoolean(json, "isSuccess1"));
        System.out.println(JacksonUtil.getAsBigInteger(json, "key"));
        System.out.println(Arrays.toString(JacksonUtil.getAsBytes(json, "isSuccess1")));

        Assert.assertEquals(new BigDecimal(Objects.requireNonNull(JacksonUtil.getAsString(json, "amount"))), JacksonUtil.getAsBigDecimal(json, "amount"));
        Assert.assertEquals(new String(Objects.requireNonNull(JacksonUtil.getAsBytes(json, "isSuccess1"))), JacksonUtil.getAsString(json, "isSuccess1"));
    }

    @Test
    public void testWrite() {
        Person person = Person.newPerson();
        //写到target/test-classes/目录下
        JacksonUtil.toFile(this.getClass().getClassLoader().getResource("testwrite.json").getPath(), person);
    }

}
```