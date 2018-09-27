import com.dxy.library.json.fastjson.FastjsonUtil;
import com.dxy.library.json.gson.GsonUtil;
import com.dxy.library.json.jackson.JacksonUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.gson.reflect.TypeToken;
import org.junit.Test;

import java.time.Clock;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
        System.out.println(GsonUtil.getBoolean(gsonStr, "man"));
        System.out.println(GsonUtil.getBigDecimal(gsonStr, "money"));
        System.out.println(GsonUtil.getList(gsonStr, "trait"));
        System.out.println(GsonUtil.format(gsonStr));
        Person from = GsonUtil.from(gsonStr, Person.class);
        System.out.println(from);
        Person fromLenient = GsonUtil.fromLenient(gsonStr, Person.class);
        System.out.println(fromLenient);
        GsonUtil.toFile("/data/test_to_gson.json", from);
        GsonUtil.toFile("/data/test_to_gson.json", Lists.newArrayList(from));
    }

    @Test
    public void testFastjson() {
        Person person = Person.newPerson();

        String fastjsonStr = FastjsonUtil.to(person);
        System.out.println(fastjsonStr);
        System.out.println(FastjsonUtil.getString(fastjsonStr, "name"));
        System.out.println(FastjsonUtil.getInt(fastjsonStr, "age"));
        System.out.println(FastjsonUtil.getBoolean(fastjsonStr, "man"));
        System.out.println(FastjsonUtil.getBigDecimal(fastjsonStr, "money"));
        System.out.println(FastjsonUtil.getList(fastjsonStr, "trait", String.class));
        System.out.println(FastjsonUtil.format(fastjsonStr));
        Person from = FastjsonUtil.from(fastjsonStr, Person.class);
        System.out.println(from);
    }

    @Test
    public void testJackson() {
        Person person = Person.newPerson();

        String jacksonStr = JacksonUtil.to(person);
        System.out.println(jacksonStr);
        System.out.println(JacksonUtil.getString(jacksonStr, "name"));
        System.out.println(JacksonUtil.getInt(jacksonStr, "age"));
        System.out.println(JacksonUtil.getBoolean(jacksonStr, "man"));
        System.out.println(JacksonUtil.getBigDecimal(jacksonStr, "money"));
        System.out.println(JacksonUtil.getList(jacksonStr, "trait"));
        System.out.println(JacksonUtil.format(jacksonStr));
        Person from = JacksonUtil.from(jacksonStr, Person.class);
        System.out.println(JacksonUtil.to(from));

        System.out.println(JacksonUtil.to(from));
        JacksonUtil.toFile("/data/test_to.json", from);
        JacksonUtil.toFile("/data/test_to.json", Lists.newArrayList(from));

        Person yamlPerson = JacksonUtil.fromYamlRecource("test.yml", Person.class);
        System.out.println("yamlPerson: " + JacksonUtil.to(yamlPerson));
        System.out.println("yamlPerson: " + JacksonUtil.toYaml(yamlPerson));
        JacksonUtil.toPropFile("/data/test_to.yml", yamlPerson);

        HashMap<String, Object> yamlMapPerson = JacksonUtil.fromYamlRecource("test.yml", new TypeReference<HashMap<String, Object>>() {});
        System.out.println("yamlPerson: " + JacksonUtil.to(yamlMapPerson));
        System.out.println("yamlPerson: " + JacksonUtil.toYaml(yamlMapPerson));
        JacksonUtil.toPropFile("/data/test_to.yml", yamlMapPerson);

        Person propPerson = JacksonUtil.fromPropRecource("test.properties", Person.class);
        System.out.println("propPerson: " + JacksonUtil.to(propPerson));
        System.out.println("propPerson: " + JacksonUtil.toProp(propPerson));
        JacksonUtil.toPropFile("/data/test_to.properties", propPerson);

        List<Person> csvPersons = JacksonUtil.fromCsvRecource("test.csv", "\t", Person.class);
        System.out.println("csvPerson: " + JacksonUtil.to(csvPersons));
        System.out.println("csvPerson: " + JacksonUtil.toCsv(csvPersons));
        System.out.println("csvPerson: " + JacksonUtil.toCsv(csvPersons.get(0)));
        JacksonUtil.toCsvFile("/data/test_to.csv", csvPersons);
        JacksonUtil.toCsvFile("/data/test_to.csv", csvPersons.get(0));

        Person xmlPerson = JacksonUtil.fromXmlRecource("test.xml", Person.class);
        System.out.println("xmlPerson: " + JacksonUtil.to(xmlPerson));
        System.out.println("xmlPerson: " + JacksonUtil.toXml(xmlPerson));
        JacksonUtil.toXmlFile("/data/test_to.xml", xmlPerson);
    }

    @Test
    public void testXml() {
        String s = "<?xml version=\"1.0\" encoding=\"utf-8\" ?>" +
                "<return>\n" +
                "    <status>Success</status>\n" +
                "    <amount>2.22</amount>\n" +
                "    <success>true</success>\n" +
                "    <count>2</count>\n" +

                "    <content>\n" +
                "    <name>ase</name>\n" +
                "    <age>10</age>\n" +
                "    </content>\n" +

                "    <list>\n" +
                "    <name>123</name>\n" +
                "    <name>231</name>\n" +
                "    </list>\n" +

                "    <contentList>\n" +
                "    <content>\n" +
                "    <name>ase</name>\n" +
                "    <age>123</age>\n" +
                "    </content>\n" +
                "    </contentList>\n" +

                "    <map>\n" +
                "    <key1>value1</key1>\n" +
                "    <key2>value2</key2>\n" +
                "    </map>\n" +

                "    <contentMap>\n" +
                "    <key1>" +
                "    <content>\n" +
                "    <name>ase</name>\n" +
                "    <age>20</age>\n" +
                "    </content>\n" +
                "    </key1>\n" +
                "    <key2>" +
                "    <content>\n" +
                "    <name>ase</name>\n" +
                "    <age>30</age>\n" +
                "    </content>\n" +
                "    </key2>\n" +
                "    </contentMap>\n" +

                "    </return>";
        TestPojo from = JacksonUtil.fromXml(s, TestPojo.class);
        System.out.println(JacksonUtil.to(from));
    }

    /**
     * 速度测试
     * 结果：
     * 数据量大于2万，gson序列化速度最快，大于百万
     */
    @Test
    public void testSpeed() {
        ImmutableList<Integer> sizes = ImmutableList.of(200, 1000, 5000, 10000, 50000);
//        ImmutableList<Integer> sizes = ImmutableList.of(5000);
        //取值次数
        int count = 1;
        for (Integer size : sizes) {
            testTo(size, count);
            testFrom(size, count);
            testFromTo(size, count);
        }
    }


    /**
     * Json序列化速度对比测试
     */
    public void testTo(int size, int count) {
        List<HashMap<String, Object>> hashMapList = getHashMapList(size);
        ArrayList<Long> gsonTos = Lists.newArrayList();

        ArrayList<Long> jacksonTos = Lists.newArrayList();

        ArrayList<Long> fastjsonTos = Lists.newArrayList();
        //取平均值
        for (int i = 0; i < count; i++) {
            long startToGson = Clock.systemUTC().millis();
            GsonUtil.to(hashMapList);
            long endToGson = Clock.systemUTC().millis();
            gsonTos.add(endToGson - startToGson);


            long startToJackson = Clock.systemUTC().millis();
            JacksonUtil.to(hashMapList);
            long endToJackson = Clock.systemUTC().millis();
            jacksonTos.add(endToJackson - startToJackson);

            long startToFastjson = Clock.systemUTC().millis();
            FastjsonUtil.to(hashMapList);
            long endToFastjson = Clock.systemUTC().millis();
            fastjsonTos.add(endToFastjson - startToFastjson);
        }

        System.out.println("size: " + size + ", to, gson: " + average(gsonTos) + ", jackson: " + average(jacksonTos) + ", fastjson: " + average(fastjsonTos));
    }


    /**
     * Json反序列化速度对比测试
     */
    public void testFrom(int size, int count) {
        List<HashMap<String, Object>> hashMapList = getHashMapList(size);
        ArrayList<Long> gsonFroms = Lists.newArrayList();
        ArrayList<Long> jacksonFroms = Lists.newArrayList();
        ArrayList<Long> fastjsonFroms = Lists.newArrayList();
        String to = GsonUtil.to(hashMapList);
        //取平均值
        for (int i = 0; i < count; i++) {
            long startFromGson = Clock.systemUTC().millis();
            GsonUtil.from(to, new TypeToken<ArrayList<Event>>() {});
            long endFromGson = Clock.systemUTC().millis();
            gsonFroms.add(endFromGson - startFromGson);

            long startFromJackson = Clock.systemUTC().millis();
            JacksonUtil.from(to, new TypeReference<ArrayList<Event>>() {});
            long endFromJackson = Clock.systemUTC().millis();
            jacksonFroms.add(endFromJackson - startFromJackson);

            long startFromFastjson = Clock.systemUTC().millis();
            FastjsonUtil.from(to, new com.alibaba.fastjson.TypeReference<ArrayList<Event>>() {});
            long endFromFastjson = Clock.systemUTC().millis();
            fastjsonFroms.add(endFromFastjson - startFromFastjson);
        }
        System.out.println("size: " + size + ", from, gson: " + average(gsonFroms) + ", jackson: " + average(jacksonFroms) + ", fastjson: " + average(fastjsonFroms));
    }


    /**
     * Json序列化和反序列化混合速度对比测试
     */
    public void testFromTo(int size, int count) {
        List<HashMap<String, Object>> hashMapList = getHashMapList(size);
        ArrayList<Long> gsonFromSingles = Lists.newArrayList();
        ArrayList<Long> jacksonFromSingles = Lists.newArrayList();
        ArrayList<Long> fastjsonFromSingles = Lists.newArrayList();
        //取平均值
        for (int i = 0; i < count; i++) {
            long startFromSingleGson = Clock.systemUTC().millis();
            List<Event> eventListGson = Lists.newArrayList();
            for (HashMap<String, Object> map : hashMapList) {
                eventListGson.add(GsonUtil.from(GsonUtil.to(map), Event.class));
            }
            long endFromSingleGson = Clock.systemUTC().millis();
            gsonFromSingles.add(endFromSingleGson - startFromSingleGson);

            long startFromSingleJackson = Clock.systemUTC().millis();
            List<Event> eventListJackson = Lists.newArrayList();
            for (HashMap<String, Object> map : hashMapList) {
                eventListJackson.add(JacksonUtil.from(JacksonUtil.to(map), Event.class));
            }
            long endFromSingleJackson = Clock.systemUTC().millis();
            jacksonFromSingles.add(endFromSingleJackson - startFromSingleJackson);

            long startFromSingleFastjson = Clock.systemUTC().millis();
            List<Event> eventListFastjson = Lists.newArrayList();
            for (HashMap<String, Object> map : hashMapList) {
                eventListFastjson.add(FastjsonUtil.from(FastjsonUtil.to(map), Event.class));
            }
            long endFromSingleFastjson = Clock.systemUTC().millis();
            fastjsonFromSingles.add(endFromSingleFastjson - startFromSingleFastjson);
        }

        System.out.println("size: " + size + ", from single, gson: " + average(gsonFromSingles) + ", jackson: " + average(jacksonFromSingles) + ", fastjson: " + average(fastjsonFromSingles));
    }

    public List<HashMap<String, Object>> getHashMapList(int size) {
        List<HashMap<String, Object>> hashMapList = Lists.newArrayList();
        for (int i = 0; i < size; i++) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("id", "AWJSIFlnwyY7hXLEWwBN");
            map.put("event_name", "startApp");
            hashMapList.add(map);
        }
        return hashMapList;
    }

    //计算平均数
    public static double average(List<Long> list) {
        long sum = 0;
        //遍历求和
        for (Long i : list) {
            sum += i;
        }
        //除以人数，计算平均值
        return (double) sum / list.size();
    }

}
