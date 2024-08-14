package vikin007;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Test {
    public static void main(String[] args) {
        LocalTime lt = LocalTime.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
        String time = dtf.format(lt);
        System.out.println(time);


        LocalDate ld = LocalDate.now();
        System.out.println(ld);
    }
}
