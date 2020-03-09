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
    private String name;
    private Date date;
    private LocalDateTime localDateTime;
    private int age;
    private BigDecimal money;
    private boolean man;
    private ArrayList<String> trait;
    private HashMap<String, String> cards;

    public static Person newPerson() {
        Person person = new Person();
        person.setName("张三");
        person.date = new Date();
        person.setLocalDateTime(LocalDateTime.now());
        person.setAge(100);
        person.setMoney(BigDecimal.valueOf(500.21));
        person.setMan(true);
        person.setTrait(new ArrayList<>());
        person.getTrait().add("淡然");
        person.getTrait().add("温和");
        person.setCards(new HashMap<>());
        person.getCards().put("身份证", "4a6d456as");
        person.getCards().put("建行卡", "649874545");
        return person;
    }

}
