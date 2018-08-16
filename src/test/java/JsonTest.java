import com.dxy.library.json.FastjsonUtl;
import com.dxy.library.json.GsonUtil;
import com.dxy.library.json.JacksonUtil;
import org.junit.Test;

/**
 * @author duanxinyuan
 * 2018/6/29 13:52
 */
public class JsonTest {

    @Test
    public void testGson() {
        Person person = Person.newPerson();

        String gsonStr = GsonUtil.to(person);
        System.out.println(gsonStr);
        System.out.println(GsonUtil.getString(gsonStr, "name"));
        System.out.println(GsonUtil.getInt(gsonStr, "age"));
        System.out.println(GsonUtil.getBoolean(gsonStr, "isMan"));
        System.out.println(GsonUtil.getBigDecimal(gsonStr, "money"));
        System.out.println(GsonUtil.getList(gsonStr, "trait"));
        System.out.println(GsonUtil.format(gsonStr));
        Person from = GsonUtil.from(gsonStr, Person.class);
        System.out.println(from);
        Person lenientFrom = GsonUtil.lenientFrom(gsonStr, Person.class);
        System.out.println(lenientFrom);
    }

    @Test
    public void testFastjson() {
        Person person = Person.newPerson();

        String fastjsonStr = FastjsonUtl.to(person);
        System.out.println(fastjsonStr);
        System.out.println(FastjsonUtl.getString(fastjsonStr, "name"));
        System.out.println(FastjsonUtl.getInt(fastjsonStr, "age"));
        System.out.println(FastjsonUtl.getBoolean(fastjsonStr, "isMan"));
        System.out.println(FastjsonUtl.getBigDecimal(fastjsonStr, "money"));
        System.out.println(FastjsonUtl.getList(fastjsonStr, "trait", String.class));
        System.out.println(FastjsonUtl.format(fastjsonStr));
        Person from = FastjsonUtl.from(fastjsonStr, Person.class);
        System.out.println(from);
    }

    @Test
    public void testJackson() {
        Person person = Person.newPerson();

        String jacksonStr = JacksonUtil.to(person);
        System.out.println(jacksonStr);
        System.out.println(JacksonUtil.getString(jacksonStr, "name"));
        System.out.println(JacksonUtil.getInt(jacksonStr, "age"));
        System.out.println(JacksonUtil.getBoolean(jacksonStr, "isMan"));
        System.out.println(JacksonUtil.getBigDecimal(jacksonStr, "money"));
        System.out.println(JacksonUtil.getList(jacksonStr, "trait"));
        System.out.println(JacksonUtil.format(jacksonStr));
        Person from = JacksonUtil.from(jacksonStr, Person.class);
        System.out.println(from);
    }

}
