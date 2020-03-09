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
public class ExtendPerson {
    public String name;
    public Date date;
    public LocalDateTime localDateTime;
    public int age;
    public BigDecimal money;
    public boolean man;
    public ArrayList<String> trait;
    public HashMap<String, String> cards;

    public static ExtendPerson newPerson() {
        ExtendPerson extendPerson = new ExtendPerson();
        extendPerson.name = "张三";
        extendPerson.date = new Date();
        extendPerson.localDateTime = LocalDateTime.now();
        extendPerson.age = 100;
        extendPerson.money = BigDecimal.valueOf(500.21);
        extendPerson.man = true;
        extendPerson.trait = new ArrayList<>();
        extendPerson.trait.add("淡然");
        extendPerson.trait.add("温和");
        extendPerson.cards = new HashMap<>();
        extendPerson.cards.put("身份证", "4a6d456as");
        extendPerson.cards.put("建行卡", "649874545");
        return extendPerson;
    }

}
