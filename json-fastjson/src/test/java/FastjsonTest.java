import com.dxy.library.json.fastjson.FastjsonUtil;
import org.junit.Test;

import java.util.ArrayList;

/**
 * @author duanxinyuan
 * 2019/1/21 18:14
 */
public class FastjsonTest {

    @Test
    public void testFastjson() {
        Person person = Person.newPerson();
        ArrayList<Person> personList = new ArrayList<>();
        personList.add(person);
        System.out.println(FastjsonUtil.fromList(FastjsonUtil.to(personList), Person.class));

        String fastjsonStr = FastjsonUtil.to(person);
        System.out.println(fastjsonStr);
        System.out.println(FastjsonUtil.getAsString(fastjsonStr, "name"));
        System.out.println(FastjsonUtil.getAsInt(fastjsonStr, "age"));
        System.out.println(FastjsonUtil.getAsBoolean(fastjsonStr, "man"));
        System.out.println(FastjsonUtil.getAsBigDecimal(fastjsonStr, "money"));
        System.out.println(FastjsonUtil.getAsList(fastjsonStr, "trait", String.class));
        System.out.println(FastjsonUtil.format(fastjsonStr));
        Person from = FastjsonUtil.from(fastjsonStr, Person.class);
        System.out.println(from);
    }


    /**
     * 测试兼容情况
     */
    @Test
    public void test() {
        String json = "{\"code\":\"200\",\"id\":\"2001215464647687987\",\"message\":\"success\",\"amount\":\"1.12345\",\"amount1\":\"0.12345\",\"isSuccess\":\"true\",\"isSuccess1\":\"1\",\"key\":\"8209167202090377654857374178856064487200234961995543450245362822537162918731039965956758726661669012305745755921310000297396309887550627402157318910581311\"}";
        System.out.println(FastjsonUtil.getAsInt(json, "code"));
        System.out.println(FastjsonUtil.getAsLong(json, "id"));
        System.out.println(FastjsonUtil.getAsString(json, "message"));
        System.out.println(FastjsonUtil.getAsDouble(json, "amount"));
        System.out.println(FastjsonUtil.getAsDouble(json, "amount1"));
        System.out.println(FastjsonUtil.getAsBigDecimal(json, "amount"));
        System.out.println(FastjsonUtil.getAsBigDecimal(json, "amount1"));
        System.out.println(FastjsonUtil.getAsBoolean(json, "isSuccess"));
        System.out.println(FastjsonUtil.getAsBoolean(json, "isSuccess1"));
        System.out.println(FastjsonUtil.getAsBigInteger(json, "key"));
        System.out.println(FastjsonUtil.getAsByte(json, "isSuccess1"));
    }

}
