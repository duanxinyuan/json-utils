import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * @author duanxinyuan
 * 2018/6/29 14:17
 */
@Data
public class Person {
    public String name;
    public Date date;
    public LocalDateTime localDateTime;
    public int age;
    public BigDecimal money;
    public boolean man;
    public ArrayList<String> trait;
    public HashMap<String, String> cards;

    public static Person newPerson() {
        Person person = new Person();
        person.name = "张三";
        person.date = new Date();
        person.localDateTime = LocalDateTime.now();
        person.age = 100;
        person.money = BigDecimal.valueOf(500.21);
        person.man = true;
        person.trait = Lists.newArrayList(ImmutableList.of("淡然", "温和"));
        person.cards = Maps.newHashMap();
        person.cards.put("身份证", "4a6d456as");
        person.cards.put("建行卡", "649874545");
        return person;
    }

}
