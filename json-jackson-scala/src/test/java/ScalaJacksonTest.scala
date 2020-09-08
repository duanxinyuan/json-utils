import java.math.BigDecimal
import java.time.LocalDateTime
import java.util
import java.util.Date

import com.dxy.library.json.jackson.JacksonUtil
import org.junit.Test

/**
 * scala单元测试
 * @author duanxinyuan
 * 2019/10/29 14:11
 */
class ScalaJacksonTest {

  @Test
  def testScala(): Unit = {
    val json = "{\"name\":\"张三\",\"date\":\"2019-10-29 14:16:01\",\"localDateTime\":\"2019-10-29 14:16:02\",\"age\":100,\"money\":500.21,\"man\":true,\"trait\":[\"淡然\",\"温和\"],\"cards\":{\"建行卡\":\"649874545\",\"身份证\":\"4a6d456as\"}}"
    val person = JacksonUtil.from(json, classOf[ScalaPerson])
    println(JacksonUtil.to(person))
  }

  @Test
  def testJackson(): Unit = {
    val list = List("淡然", "温和")
    val map = Map("身份证" -> "4a6d456as", "建行卡" -> "649874545")
    val person = ScalaPerson("张三", new Date, LocalDateTime.now, 100, BigDecimal.valueOf(500.21), man = true, list, map)
    val personList = List(person)
    println(JacksonUtil.fromList(JacksonUtil.to(personList), classOf[ScalaPerson]))
    var jacksonStr = JacksonUtil.to(person)
    println(jacksonStr)
    println(JacksonUtil.getAsString(jacksonStr, "name"))
    println(JacksonUtil.getAsInt(jacksonStr, "age"))
    println(JacksonUtil.getAsBoolean(jacksonStr, "man"))
    println(JacksonUtil.getAsBigDecimal(jacksonStr, "money"))
    println(JacksonUtil.getAsList(jacksonStr, "trait", classOf[String]))
    println(JacksonUtil.format(jacksonStr))

    println(JacksonUtil.to(JacksonUtil.from(jacksonStr, classOf[ScalaPerson])))
    person.`trait` = null
    jacksonStr = JacksonUtil.to(person)
    println(jacksonStr)
    println(JacksonUtil.to(JacksonUtil.from(jacksonStr, classOf[ScalaPerson])))
  }

  /**
   * 测试兼容情况
   */
  @Test
  def test(): Unit = {
    val json: String = "{\"code\":\"200\",\"id\":\"2001215464647687987\",\"message\":\"success\",\"amount\":\"1.12345\",\"amount1\":\"0.12345\",\"isSuccess\":\"true\",\"isSuccess1\":\"1\",\"key\":\"8209167202090377654857374178856064487200234961995543450245362822537162918731039965956758726661669012305745755921310000297396309887550627402157318910581311\"}"
    println(JacksonUtil.getAsInt(json, "code"))
    println(JacksonUtil.getAsLong(json, "id"))
    println(JacksonUtil.getAsString(json, "message"))
    println(JacksonUtil.getAsDouble(json, "amount"))
    println(JacksonUtil.getAsDouble(json, "amount1"))
    println(JacksonUtil.getAsBigDecimal(json, "amount"))
    println(JacksonUtil.getAsBigDecimal(json, "amount1"))
    println(JacksonUtil.getAsBoolean(json, "isSuccess"))
    println(JacksonUtil.getAsBoolean(json, "isSuccess1"))
    println(JacksonUtil.getAsBigInteger(json, "key"))
    println(util.Arrays.toString(JacksonUtil.getAsBytes(json, "isSuccess1")))
  }

}

case class ScalaPerson(
                        var name: String,
                        var date: Date,
                        var localDateTime: LocalDateTime,
                        var age: Int,
                        var money: BigDecimal,
                        var man: Boolean,
                        var `trait`: List[String],
                        var cards: Map[String, String]
                      )


