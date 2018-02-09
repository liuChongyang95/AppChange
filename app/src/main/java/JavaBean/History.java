package JavaBean;

public class History {
    private String Foodname;

    public History(String fn) {
        this.Foodname = fn;
    }

    public String getFoodname() {
        return Foodname;
    }

    public void setFoodname(String foodname) {
        Foodname = foodname;
    }
}
