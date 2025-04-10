import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import com.alibaba.fastjson.annotation.JSONField;

import lombok.Data;

/**
 * @author duanxinyuan
 * 2018/6/29 14:17
 */
@Data
public class Person {
    private String name;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date date;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime localDateTime;
    @JSONField(format = "yyyy-MM-dd")
    private LocalDate localDate;
    @JSONField(format = "HH:mm:ss")
    private LocalTime localTime;
    private int age;
    private BigDecimal money;
    private boolean man;
    private ArrayList<String> trait;
    private HashMap<String, String> cards;

    public static Person newPerson() {
        Person person = new Person();
        person.setName("张三");
        LocalDateTime localDateTime = LocalDateTime.parse("2022-03-10 00:00:00",
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        person.date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        person.setLocalDateTime(localDateTime);
        person.setLocalDate(LocalDate.parse("2022-03-10", DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        person.setLocalTime(LocalTime.parse("19:36:19", DateTimeFormatter.ofPattern("HH:mm:ss")));
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
