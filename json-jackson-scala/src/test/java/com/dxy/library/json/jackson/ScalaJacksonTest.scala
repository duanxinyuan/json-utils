package com.dxy.library.json.jackson

import org.junit.{Assert, Test}

import java.math.{BigDecimal, BigInteger}
import java.time.{LocalDate, LocalDateTime, LocalTime}
import java.util
import java.util.Date

/**
 * scala单元测试
 *
 * @author duanxinyuan
 *         2019/10/29 14:11
 */
class ScalaJacksonTest {
  val json = "{\"name\":\"张三\",\"date\":\"2022-03-10 00:00:00\",\"localDateTime\":\"2022-03-10 00:00:00\",\"localDate\":\"2022-03-10\",\"localTime\":\"19:36:19\",\"age\":100,\"money\":500.21,\"man\":true,\"trait\":[\"淡然\",\"温和\"],\"cards\":{\"建行卡\":\"649874545\",\"身份证\":\"4a6d456as\"}}"

  def getPerson(): ScalaPerson = {
    val person = JacksonUtil.from(json, classOf[ScalaPerson])
    person;
  }

  @Test
  def testScala(): Unit = {
    val person = getPerson();
    println(JacksonUtil.to(person))
    Assert.assertEquals(json, JacksonUtil.to(person))
  }

  //  @Test
  //  def testJackson(): Unit = {
  //    val list = List("淡然", "温和")
  //    val map = Map("身份证" -> "4a6d456as", "建行卡" -> "649874545")
  //    val person = ScalaPerson("张三", new Date, LocalDateTime.now, 100, BigDecimal.valueOf(500.21), man = true, list, map)
  //    val personList = List(person)
  //    println(JacksonUtil.fromList(JacksonUtil.to(personList), classOf[ScalaPerson]))
  //    var jacksonStr = JacksonUtil.to(person)
  //    println(jacksonStr)
  //    println(JacksonUtil.getAsString(jacksonStr, "name"))
  //    println(JacksonUtil.getAsInt(jacksonStr, "age"))
  //    println(JacksonUtil.getAsBoolean(jacksonStr, "man"))
  //    println(JacksonUtil.getAsBigDecimal(jacksonStr, "money"))
  //    println(JacksonUtil.getAsList(jacksonStr, "trait", classOf[String]))
  //    println(JacksonUtil.format(jacksonStr))
  //
  //    println(JacksonUtil.to(JacksonUtil.from(jacksonStr, classOf[ScalaPerson])))
  //    person.`trait` = null
  //    jacksonStr = JacksonUtil.to(person)
  //    println(jacksonStr)
  //    println(JacksonUtil.to(JacksonUtil.from(jacksonStr, classOf[ScalaPerson])))
  //  }
  //
  //  /**
  //   * 测试兼容情况
  //   */
  //  @Test
  //  def test(): Unit = {
  //    val json: String = "{\"code\":\"200\",\"id\":\"2001215464647687987\",\"message\":\"success\",\"amount\":\"1.12345\",\"amount1\":\"0.12345\",\"isSuccess\":\"true\",\"isSuccess1\":\"1\",\"key\":\"8209167202090377654857374178856064487200234961995543450245362822537162918731039965956758726661669012305745755921310000297396309887550627402157318910581311\"}"
  //    println(JacksonUtil.getAsInt(json, "code"))
  //    println(JacksonUtil.getAsLong(json, "id"))
  //    println(JacksonUtil.getAsString(json, "message"))
  //    println(JacksonUtil.getAsDouble(json, "amount"))
  //    println(JacksonUtil.getAsDouble(json, "amount1"))
  //    println(JacksonUtil.getAsBigDecimal(json, "amount"))
  //    println(JacksonUtil.getAsBigDecimal(json, "amount1"))
  //    println(JacksonUtil.getAsBoolean(json, "isSuccess"))
  //    println(JacksonUtil.getAsBoolean(json, "isSuccess1"))
  //    println(JacksonUtil.getAsBigInteger(json, "key"))
  //    println(util.Arrays.toString(JacksonUtil.getAsBytes(json, "isSuccess1")))
  //  }

  @Test
  def testMapSetList() {
    val map = Map("test" -> 1)
    System.out.println(JacksonUtil.to(map));
    val newMap = JacksonUtil.fromMap(JacksonUtil.to(map), classOf[String], classOf[Integer]);
    Assert.assertEquals(JacksonUtil.to(map), JacksonUtil.to(newMap));

    val set = Set(1, 2);
    System.out.println(JacksonUtil.to(set));
    val newSet = JacksonUtil.fromSet(JacksonUtil.to(set), classOf[Integer]);
    Assert.assertNotNull(newSet);
    Assert.assertEquals(JacksonUtil.to(set), JacksonUtil.to(newSet));

    val list = List(1, 2);
    System.out.println(JacksonUtil.to(list));
    val newList = JacksonUtil.fromList(JacksonUtil.to(list), classOf[Integer]);
    Assert.assertNotNull(newList);
    Assert.assertEquals(JacksonUtil.to(list), JacksonUtil.to(newList));

    val listMap = Map("test" -> list);
    System.out.println(JacksonUtil.to(listMap));
    val newListMap = JacksonUtil.fromListMap(JacksonUtil.to(listMap), classOf[String], classOf[Integer]);
    Assert.assertNotNull(newListMap);
    Assert.assertEquals(JacksonUtil.to(listMap), JacksonUtil.to(newListMap));

    val mapList = List(map);
    System.out.println(JacksonUtil.to(mapList));
    val newMapList = JacksonUtil.fromMapList(JacksonUtil.to(mapList), classOf[String], classOf[Integer]);
    Assert.assertNotNull(newMapList);
    Assert.assertEquals(JacksonUtil.to(mapList), JacksonUtil.to(newMapList));

    val personMap = Map("testPerson" -> getPerson());
    val personMapList = List(personMap);
    System.out.println(JacksonUtil.to(personMapList));
    Assert.assertEquals(
      "[{\"testPerson\":{\"name\":\"张三\",\"date\":\"2022-03-10 00:00:00\",\"localDateTime\":\"2022-03-10 "
        + "00:00:00\",\"localDate\":\"2022-03-10\",\"localTime\":\"19:36:19\",\"age\":100,\"money\":500.21,"
        + "\"man\":true,\"trait\":[\"淡然\",\"温和\"],\"cards\":{\"建行卡\":\"649874545\",\"身份证\":\"4a6d456as\"}}}]",
      JacksonUtil.to(personMapList));
    val newPersonMapList = JacksonUtil.fromMapList(JacksonUtil.to(personMapList), classOf[String], classOf[ScalaPerson]);
    Assert.assertNotNull(newPersonMapList);
    Assert.assertEquals(JacksonUtil.to(personMapList), JacksonUtil.to(newPersonMapList));
  }

  @Test
  def test() {
    val person = getPerson();
    val personList = List(person);
    Assert.assertEquals(JacksonUtil.to(person), json);
    val personList1 = JacksonUtil.fromList(JacksonUtil.to(personList), classOf[ScalaPerson]);
    Assert.assertEquals(JacksonUtil.to(personList), JacksonUtil.to(personList1));

    val gsonStr = JacksonUtil.to(person);
    Assert.assertEquals("张三", JacksonUtil.getAsString(gsonStr, "name"));
    Assert.assertEquals(100, JacksonUtil.getAsInt(gsonStr, "age"));
    Assert.assertEquals(true, JacksonUtil.getAsBoolean(gsonStr, "man"));
    Assert.assertEquals(new BigDecimal("500.21"), JacksonUtil.getAsBigDecimal(gsonStr, "money"));
    Assert.assertEquals(util.Arrays.asList("淡然", "温和"), JacksonUtil.getAsList(gsonStr, "trait", classOf[String]));
    Assert.assertEquals("{\n"
      + "  \"name\" : \"张三\",\n"
      + "  \"date\" : \"2022-03-10 00:00:00\",\n"
      + "  \"localDateTime\" : \"2022-03-10 00:00:00\",\n"
      + "  \"localDate\" : \"2022-03-10\",\n"
      + "  \"localTime\" : \"19:36:19\",\n"
      + "  \"age\" : 100,\n"
      + "  \"money\" : 500.21,\n"
      + "  \"man\" : true,\n"
      + "  \"trait\" : [ \"淡然\", \"温和\" ],\n"
      + "  \"cards\" : {\n"
      + "    \"建行卡\" : \"649874545\",\n"
      + "    \"身份证\" : \"4a6d456as\"\n"
      + "  }\n"
      + "}", JacksonUtil.format(gsonStr));
  }

  /**
   * 测试兼容情况
   */
  @Test
  def testGet() {
    val json
    = "{\"code\":\"200\",\"id\":\"2001215464647687987\",\"message\":\"success\",\"amount\":\"1.12345\",\"amount1\":\"0.12345\",\"isSuccess\":\"true\",\"isSuccess1\":\"1\",\"key\":\"9223372036854775807\"}";
    Assert.assertEquals(200, JacksonUtil.getAsInt(json, "code"));
    Assert.assertEquals(2001215464647687987L, JacksonUtil.getAsLong(json, "id"));
    Assert.assertEquals("success", JacksonUtil.getAsString(json, "message"));
    Assert.assertEquals(1.12345, JacksonUtil.getAsDouble(json, "amount"), 5);
    Assert.assertEquals(0.12345, JacksonUtil.getAsDouble(json, "amount1"), 5);
    Assert.assertEquals(BigDecimal.valueOf(1.12345), JacksonUtil.getAsBigDecimal(json, "amount"));
    Assert.assertEquals(BigDecimal.valueOf(0.12345), JacksonUtil.getAsBigDecimal(json, "amount1"));
    Assert.assertEquals(true, JacksonUtil.getAsBoolean(json, "isSuccess"));
    Assert.assertEquals(true, JacksonUtil.getAsBoolean(json, "isSuccess1"));
    Assert.assertEquals(BigInteger.valueOf(Long.MaxValue),
      JacksonUtil.getAsBigInteger(json, "key"));
    Assert.assertEquals(1, JacksonUtil.getAsByte(json, "isSuccess1"));
  }

}

case class ScalaPerson(
                        var name: String,
                        var date: Date,
                        var localDateTime: LocalDateTime,
                        var localDate: LocalDate,
                        var localTime: LocalTime,
                        var age: Int,
                        var money: BigDecimal,
                        var man: Boolean,
                        var `trait`: List[String],
                        var cards: Map[String, String]
                      )


