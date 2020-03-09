import com.dxy.library.json.gson.GsonUtil;
import org.junit.Test;

import java.util.ArrayList;

/**
 * @author duanxinyuan
 * 2019/1/21 18:16
 */
public class GsonTest {

    @Test
    public void testGson() {
        Person person = Person.newPerson();
        ArrayList<Person> personList = new ArrayList<>();
        personList.add(person);
        System.out.println(GsonUtil.fromList(GsonUtil.to(personList), Person.class));

        String gsonStr = GsonUtil.to(person);
        System.out.println(gsonStr);
        System.out.println(GsonUtil.getAsString(gsonStr, "name"));
        System.out.println(GsonUtil.getAsInt(gsonStr, "age"));
        System.out.println(GsonUtil.getAsBoolean(gsonStr, "man"));
        System.out.println(GsonUtil.getAsBigDecimal(gsonStr, "money"));
        System.out.println(GsonUtil.getAsList(gsonStr, "trait", String.class));
        System.out.println(GsonUtil.format(gsonStr));
        Person from = GsonUtil.from(gsonStr, Person.class);
        System.out.println(from);
        Person lenientFrom = GsonUtil.fromLenient(gsonStr, Person.class);
        System.out.println(lenientFrom);
    }

    /**
     * 测试兼容情况
     */
    @Test
    public void test() {
        String json = "{\"code\":\"200\",\"id\":\"2001215464647687987\",\"message\":\"success\",\"amount\":\"1.12345\",\"amount1\":\"0.12345\",\"isSuccess\":\"true\",\"isSuccess1\":\"1\",\"key\":\"8209167202090377654857374178856064487200234961995543450245362822537162918731039965956758726661669012305745755921310000297396309887550627402157318910581311\"}";
        System.out.println(GsonUtil.getAsInt(json, "code"));
        System.out.println(GsonUtil.getAsLong(json, "id"));
        System.out.println(GsonUtil.getAsString(json, "message"));
        System.out.println(GsonUtil.getAsDouble(json, "amount"));
        System.out.println(GsonUtil.getAsDouble(json, "amount1"));
        System.out.println(GsonUtil.getAsBigDecimal(json, "amount"));
        System.out.println(GsonUtil.getAsBigDecimal(json, "amount1"));
        System.out.println(GsonUtil.getAsBoolean(json, "isSuccess"));
        System.out.println(GsonUtil.getAsBoolean(json, "isSuccess1"));
        System.out.println(GsonUtil.getAsBigInteger(json, "key"));
        System.out.println(GsonUtil.getAsByte(json, "isSuccess1"));
    }


}
