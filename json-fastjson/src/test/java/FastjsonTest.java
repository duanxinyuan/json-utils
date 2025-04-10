import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.dxy.library.json.fastjson.FastjsonUtil;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author duanxinyuan
 * 2019/1/21 18:14
 */
public class FastjsonTest {

    @Test
    public void testMapSetList() {
        Map<String, Integer> map = new HashMap<>();
        map.put("test", 1);
        System.out.println(FastjsonUtil.to(map));
        Map<String, Integer> newMap = FastjsonUtil.fromMap(FastjsonUtil.to(map), String.class, Integer.class);
        Assert.assertEquals(map, newMap);

        LinkedHashSet<Integer> set = new LinkedHashSet<>();
        set.add(1);
        set.add(2);
        System.out.println(FastjsonUtil.to(set));
        Set<Integer> newSet = FastjsonUtil.fromSet(FastjsonUtil.to(set), Integer.class);
        Assert.assertNotNull(newSet);
        Assert.assertEquals(set.toString(), newSet.toString());

        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        System.out.println(FastjsonUtil.to(list));
        List<Integer> newList = FastjsonUtil.fromList(FastjsonUtil.to(list), Integer.class);
        Assert.assertNotNull(newList);
        Assert.assertEquals(list.toString(), newList.toString());

        Map<String, List<Integer>> listMap = new HashMap<>();
        listMap.put("test", list);
        System.out.println(FastjsonUtil.to(listMap));
        Map<String, List<Integer>> newListMap = FastjsonUtil.fromListMap(FastjsonUtil.to(listMap), String.class,
            Integer.class);
        Assert.assertNotNull(newListMap);
        Assert.assertEquals(listMap.toString(), newListMap.toString());

        List<Map<String, Integer>> mapList = new ArrayList<>();
        mapList.add(map);
        System.out.println(FastjsonUtil.to(mapList));
        List<Map<String, Integer>> newMapList = FastjsonUtil.fromMapList(FastjsonUtil.to(mapList), String.class,
            Integer.class);
        Assert.assertNotNull(newMapList);
        Assert.assertEquals(mapList.toString(), newMapList.toString());

        List<Map<String, Person>> personMapList = new ArrayList<>();
        Map<String, Person> personMap = new HashMap<>();
        personMap.put("testPerson", Person.newPerson());
        personMapList.add(personMap);
        System.out.println(FastjsonUtil.to(personMapList));
        Assert.assertEquals(
            "[{\"testPerson\":{\"age\":100,\"cards\":{\"建行卡\":\"649874545\",\"身份证\":\"4a6d456as\"},"
                + "\"date\":\"2022-03-10 00:00:00\",\"localDate\":\"2022-03-10\",\"localDateTime\":\"2022-03-10 "
                + "00:00:00\",\"localTime\":\"19:36:19\",\"man\":true,\"money\":500.21,\"name\":\"张三\","
                + "\"trait\":[\"淡然\",\"温和\"]}}]",
            FastjsonUtil.to(personMapList));
        List<Map<String, Person>> newPersonMapList = FastjsonUtil.fromMapList(FastjsonUtil.to(personMapList),
            String.class, Person.class);
        Assert.assertNotNull(newPersonMapList);
        Assert.assertEquals(personMapList.toString(), newPersonMapList.toString());
    }

    @Test
    public void test() {
        Person person = Person.newPerson();
        ArrayList<Person> personList = new ArrayList<>();
        personList.add(person);
        String json = FastjsonUtil.to(personList);
        Assert.assertEquals(
            "[{\"age\":100,\"cards\":{\"建行卡\":\"649874545\",\"身份证\":\"4a6d456as\"},\"date\":\"2022-03-10 00:00:00\","
                + "\"localDate\":\"2022-03-10\",\"localDateTime\":\"2022-03-10 00:00:00\",\"localTime\":\"19:36:19\","
                + "\"man\":true,\"money\":500.21,\"name\":\"张三\",\"trait\":[\"淡然\",\"温和\"]}]",
            json);
        System.out.println(json);
        List<Person> personList1 = FastjsonUtil.fromList(json, Person.class);
        Assert.assertEquals(FastjsonUtil.to(personList), FastjsonUtil.to(personList1));

        String gsonStr = FastjsonUtil.to(person);
        Assert.assertEquals("张三", FastjsonUtil.getAsString(gsonStr, "name"));
        Assert.assertEquals(100, FastjsonUtil.getAsInt(gsonStr, "age"));
        Assert.assertEquals(true, FastjsonUtil.getAsBoolean(gsonStr, "man"));
        Assert.assertEquals(new BigDecimal("500.21"), FastjsonUtil.getAsBigDecimal(gsonStr, "money"));
        Assert.assertEquals(Arrays.asList("淡然", "温和"), FastjsonUtil.getAsList(gsonStr, "trait", String.class));
        Assert.assertEquals("{\n"
            + "\t\"date\":\"2022-03-10 00:00:00\",\n"
            + "\t\"localDateTime\":\"2022-03-10 00:00:00\",\n"
            + "\t\"localTime\":\"19:36:19\",\n"
            + "\t\"cards\":{\n"
            + "\t\t\"建行卡\":\"649874545\",\n"
            + "\t\t\"身份证\":\"4a6d456as\"\n"
            + "\t},\n"
            + "\t\"money\":500.21,\n"
            + "\t\"name\":\"张三\",\n"
            + "\t\"trait\":[\n"
            + "\t\t\"淡然\",\n"
            + "\t\t\"温和\"\n"
            + "\t],\n"
            + "\t\"localDate\":\"2022-03-10\",\n"
            + "\t\"man\":true,\n"
            + "\t\"age\":100\n"
            + "}", FastjsonUtil.format(gsonStr));
    }

    /**
     * 测试兼容情况
     */
    @Test
    public void testGet() {
        String json
            = "{\"code\":\"200\",\"id\":\"2001215464647687987\",\"message\":\"success\",\"amount\":\"1.12345\","
            + "\"amount1\":\"0.12345\",\"isSuccess\":\"true\",\"isSuccess1\":\"1\","
            + "\"key"
            +
            "\":\"8209167202090377654857374178856064487200234961995543450245362822537162918731039965956758726661669012305745755921310000297396309887550627402157318910581311\"}";
        Assert.assertEquals(200, FastjsonUtil.getAsInt(json, "code"));
        Assert.assertEquals(2001215464647687987L, FastjsonUtil.getAsLong(json, "id"));
        Assert.assertEquals("success", FastjsonUtil.getAsString(json, "message"));
        Assert.assertEquals(1.12345, FastjsonUtil.getAsDouble(json, "amount"), 5);
        Assert.assertEquals(0.12345, FastjsonUtil.getAsDouble(json, "amount1"), 5);
        Assert.assertEquals(new BigDecimal("1.12345"), FastjsonUtil.getAsBigDecimal(json, "amount"));
        Assert.assertEquals(new BigDecimal("0.12345"), FastjsonUtil.getAsBigDecimal(json, "amount1"));
        Assert.assertEquals(true, FastjsonUtil.getAsBoolean(json, "isSuccess"));
        Assert.assertEquals(true, FastjsonUtil.getAsBoolean(json, "isSuccess1"));
        Assert.assertEquals(new BigInteger(
                "8209167202090377654857374178856064487200234961995543450245362822537162918731039965956758726661669012305745755921310000297396309887550627402157318910581311"),
            FastjsonUtil.getAsBigInteger(json, "key"));
        Assert.assertEquals(1, FastjsonUtil.getAsByte(json, "isSuccess1"));
    }

}
