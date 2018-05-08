package JavaBean;

public class Dietary {
    public Dietary() {
    }

    private String _id;
    private String foodId;
    private String foodUnit;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getFoodId() {
        return foodId;
    }

    public void setFoodId(String foodId) {
        this.foodId = foodId;
    }

    public String getFoodUnit() {
        return foodUnit;
    }

    public void setFoodUnit(String foodUnit) {
        this.foodUnit = foodUnit;
    }
}
