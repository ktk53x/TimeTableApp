package com.example.timetableapp.utility;
import java.util.Calendar;
import java.util.HashMap;


public class TimeTableUtilities
{
    public static HashMap<String, Integer> roll_number_parser(String roll_number)
    {
        Calendar c = Calendar.getInstance();
        int current_year = c.get(Calendar.YEAR) % 2000;
        int current_month = c.get(Calendar.MONTH);

        HashMap<String, Integer> details = new HashMap<>();

        if(roll_number.length() == 9)
        {
            int year = Integer.parseInt(roll_number.substring(0, 2));
            int course_type = Integer.parseInt(roll_number.substring(2, 4));
            int branch = Integer.parseInt(roll_number.substring(4, 6));
            if(current_month > 7)
                year = current_year - year + 1;
            else
                year = current_year - year;
            if(year >= 1 && year <= 5)
            {
                details.put("year", year);
                if(course_type == 1 || course_type == 2)
                {
                    details.put("course_type", course_type);
                    if(course_type == 1)
                        details.put("branch", branch);
                }
            }
            return details;
        }
        return details;
    }
}
