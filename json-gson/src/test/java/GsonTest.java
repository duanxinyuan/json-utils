import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.dxy.library.json.gson.GsonUtil;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author duanxinyuan
 * 2019/1/21 18:16
 */
public class GsonTest {

    @Test
    public void testMapSetList() {
        Map<String, Integer> map = new HashMap<>();
        map.put("test", 1);
        System.out.println(GsonUtil.to(map));
        Map<String, Integer> newMap = GsonUtil.fromMap(GsonUtil.to(map), String.class, Integer.class);
        Assert.assertEquals(map, newMap);

        LinkedHashSet<Integer> set = new LinkedHashSet<>();
        set.add(1);
        set.add(2);
        System.out.println(GsonUtil.to(set));
        Set<Integer> newSet = GsonUtil.fromSet(GsonUtil.to(set), Integer.class);
        Assert.assertNotNull(newSet);
        Assert.assertEquals(set.toString(), newSet.toString());

        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        System.out.println(GsonUtil.to(list));
        List<Integer> newList = GsonUtil.fromList(GsonUtil.to(list), Integer.class);
        Assert.assertNotNull(newList);
        Assert.assertEquals(list.toString(), newList.toString());

        Map<String, List<Integer>> listMap = new HashMap<>();
        listMap.put("test", list);
        System.out.println(GsonUtil.to(listMap));
        Map<String, List<Integer>> newListMap = GsonUtil.fromListMap(GsonUtil.to(listMap), String.class, Integer.class);
        Assert.assertNotNull(newListMap);
        Assert.assertEquals(listMap.toString(), newListMap.toString());

        List<Map<String, Integer>> mapList = new ArrayList<>();
        mapList.add(map);
        System.out.println(GsonUtil.to(mapList));
        List<Map<String, Integer>> newMapList = GsonUtil.fromMapList(GsonUtil.to(mapList), String.class, Integer.class);
        Assert.assertNotNull(newMapList);
        Assert.assertEquals(mapList.toString(), newMapList.toString());

        List<Map<String, Person>> personMapList = new ArrayList<>();
        Map<String, Person> personMap = new HashMap<>();
        personMap.put("testPerson", Person.newPerson());
        personMapList.add(personMap);
        System.out.println(GsonUtil.to(personMapList));
        Assert.assertEquals(
            "[{\"testPerson\":{\"name\":\"张三\",\"date\":\"2022-03-10 00:00:00\",\"localDateTime\":\"2022-03-10 "
                + "00:00:00\",\"localDate\":\"2022-03-10\",\"localTime\":\"19:36:19\",\"age\":100,\"money\":500.21,"
                + "\"man\":true,\"trait\":[\"淡然\",\"温和\"],\"cards\":{\"建行卡\":\"649874545\",\"身份证\":\"4a6d456as\"}}}]",
            GsonUtil.to(personMapList));
        List<Map<String, Person>> newPersonMapList = GsonUtil.fromMapList(GsonUtil.to(personMapList), String.class,
            Person.class);
        Assert.assertNotNull(newPersonMapList);
        Assert.assertEquals(personMapList.toString(), newPersonMapList.toString());
    }

    @Test
    public void test() {
        Person person = Person.newPerson();
        ArrayList<Person> personList = new ArrayList<>();
        personList.add(person);
        String json = GsonUtil.to(personList);
        Assert.assertEquals(
            "[{\"name\":\"张三\",\"date\":\"2022-03-10 00:00:00\",\"localDateTime\":\"2022-03-10 00:00:00\","
                + "\"localDate\":\"2022-03-10\",\"localTime\":\"19:36:19\",\"age\":100,\"money\":500.21,\"man\":true,"
                + "\"trait\":[\"淡然\",\"温和\"],\"cards\":{\"建行卡\":\"649874545\",\"身份证\":\"4a6d456as\"}}]",
            json);
        System.out.println(json);
        List<Person> personList1 = GsonUtil.fromList(json, Person.class);
        Assert.assertEquals(GsonUtil.to(personList), GsonUtil.to(personList1));

        String gsonStr = GsonUtil.to(person);
        Assert.assertEquals("张三", GsonUtil.getAsString(gsonStr, "name"));
        Assert.assertEquals(100, GsonUtil.getAsInt(gsonStr, "age"));
        Assert.assertEquals(true, GsonUtil.getAsBoolean(gsonStr, "man"));
        Assert.assertEquals(new BigDecimal("500.21"), GsonUtil.getAsBigDecimal(gsonStr, "money"));
        Assert.assertEquals(Arrays.asList("淡然", "温和"), GsonUtil.getAsList(gsonStr, "trait", String.class));
        Assert.assertEquals("{\n"
            + "  \"name\": \"张三\",\n"
            + "  \"date\": \"2022-03-10 00:00:00\",\n"
            + "  \"localDateTime\": \"2022-03-10 00:00:00\",\n"
            + "  \"localDate\": \"2022-03-10\",\n"
            + "  \"localTime\": \"19:36:19\",\n"
            + "  \"age\": 100,\n"
            + "  \"money\": 500.21,\n"
            + "  \"man\": true,\n"
            + "  \"trait\": [\n"
            + "    \"淡然\",\n"
            + "    \"温和\"\n"
            + "  ],\n"
            + "  \"cards\": {\n"
            + "    \"建行卡\": \"649874545\",\n"
            + "    \"身份证\": \"4a6d456as\"\n"
            + "  }\n"
            + "}", GsonUtil.format(gsonStr));
        Person lenientFrom = GsonUtil.fromLenient(gsonStr, Person.class);
        Assert.assertEquals(gsonStr, GsonUtil.to(lenientFrom));
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
        Assert.assertEquals(200, GsonUtil.getAsInt(json, "code"));
        Assert.assertEquals(2001215464647687987L, GsonUtil.getAsLong(json, "id"));
        Assert.assertEquals("success", GsonUtil.getAsString(json, "message"));
        Assert.assertEquals(1.12345, GsonUtil.getAsDouble(json, "amount"), 5);
        Assert.assertEquals(0.12345, GsonUtil.getAsDouble(json, "amount1"), 5);
        Assert.assertEquals(new BigDecimal("1.12345"), GsonUtil.getAsBigDecimal(json, "amount"));
        Assert.assertEquals(new BigDecimal("0.12345"), GsonUtil.getAsBigDecimal(json, "amount1"));
        Assert.assertEquals(true, GsonUtil.getAsBoolean(json, "isSuccess"));
        Assert.assertEquals(true, GsonUtil.getAsBoolean(json, "isSuccess1"));
        Assert.assertEquals(new BigInteger(
                "8209167202090377654857374178856064487200234961995543450245362822537162918731039965956758726661669012305745755921310000297396309887550627402157318910581311"),
            GsonUtil.getAsBigInteger(json, "key"));
        Assert.assertEquals(1, GsonUtil.getAsByte(json, "isSuccess1"));
    }

}
