package JavaBean;

import java.util.Comparator;

public class MyComparator implements Comparator<Career> {
    @Override
    public int compare(Career career, Career t1) {
        int i=0;
        if (career.getIntenSity() + career.getCareerName() == t1.getIntenSity() + t1.getCareerName())
            i = 0;
        return i;
    }
}
