import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author duanxinyuan
 * 2018/6/29 14:17
 */
@Data
public class ExtendPersonCsv {
    public String name;
    public Date date;
    public LocalDateTime localDateTime;
    public int age;
    public BigDecimal money;
    public boolean man;

    public static ExtendPersonCsv newPerson() {
        ExtendPersonCsv person = new ExtendPersonCsv();
        person.name = "张三";
        person.date = new Date();
        person.localDateTime = LocalDateTime.now();
        person.age = 100;
        person.money = BigDecimal.valueOf(500.21);
        person.man = true;
        return person;
    }

}
