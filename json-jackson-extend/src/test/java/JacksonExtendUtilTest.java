import com.fasterxml.jackson.core.type.TypeReference;
import com.dxy.library.json.jackson.extend.JacksonExtendUtil;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;

/**
 * @author duanxinyuan
 * 2020/1/13 21:25
 */
public class JacksonExtendUtilTest {

    @Test
    public void testYamlPropCsvXml() {
        ExtendPerson extendPerson = ExtendPerson.newPerson();
        ExtendPersonCsv extendPersonCsv = ExtendPersonCsv.newPerson();

        //写到target/test-classes/目录下
        JacksonExtendUtil.toYamlFile(this.getClass().getClassLoader().getResource("testwrite.yaml").getPath(), extendPerson);
        JacksonExtendUtil.toPropFile(this.getClass().getClassLoader().getResource("testwrite.properties").getFile(), extendPerson);
        JacksonExtendUtil.toCsvFile(this.getClass().getClassLoader().getResource("testwrite.csv").getFile(), extendPersonCsv);
        JacksonExtendUtil.toXmlFile(this.getClass().getClassLoader().getResource("testwrite.xml").getFile(), extendPerson);

        //读取文件
        System.out.println("yaml: " + JacksonExtendUtil.toYaml(extendPerson));
        System.out.println("prop: " + JacksonExtendUtil.toProp(extendPerson));
        System.out.println("csv: " + JacksonExtendUtil.toCsv(extendPersonCsv));
        System.out.println("xml: " + JacksonExtendUtil.toXml(extendPerson));

        ExtendPerson yamlExtendPerson = JacksonExtendUtil.fromYamlResource("test.yml", ExtendPerson.class);
        System.out.println("yamlPerson: " + JacksonExtendUtil.to(yamlExtendPerson));

        HashMap<String, Object> yamlMapPerson = JacksonExtendUtil.fromYamlResource("test.yml", new TypeReference<HashMap<String, Object>>() {});
        System.out.println("yamlPerson: " + JacksonExtendUtil.to(yamlMapPerson));

        ExtendPerson propExtendPerson = JacksonExtendUtil.fromPropResource("test.properties", ExtendPerson.class);
        System.out.println("propPerson: " + JacksonExtendUtil.to(propExtendPerson));

        List<ExtendPersonCsv> csvPerson = JacksonExtendUtil.fromCsvResource("test.csv", ExtendPersonCsv.class);
        System.out.println("csvPerson: " + JacksonExtendUtil.to(csvPerson));

        ExtendPerson xmlExtendPerson = JacksonExtendUtil.fromXmlResource("test.xml", ExtendPerson.class);
        System.out.println("xmlPerson: " + JacksonExtendUtil.to(xmlExtendPerson));
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
        TestPojo from = JacksonExtendUtil.fromXml(s, TestPojo.class);
        System.out.println(JacksonExtendUtil.to(from));
    }


}
