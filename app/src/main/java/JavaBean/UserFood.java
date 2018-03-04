package JavaBean;

public class UserFood {
    private int _id;
    private String foodDate;
    private String foodClass;
    private String foodId;
    private String foodCk;
    private String foodIntake;
    private String foodIngre1;
    private String intake1;
    private String foodIngre2;
    private String intake2;
    private String foodIngre3;
    private String intake3;
    private String foodIngre4;
    private String intake4;
    private String foodIngre5;
    private String intake5;

    private String foodName;
    private String foodNutri;

    public UserFood(String foodDate, String foodClass, String foodName, String foodIntake, int item_Id, String foodNutri, String foodId) {
        this.foodId = foodId;
        this.foodDate = foodDate;
        this.foodClass = foodClass;
        this.foodName = foodName;
        this.foodIntake = foodIntake;
        this.foodNutri = foodNutri;
        this._id = item_Id;
    }


    public UserFood() {
    }

    public String getFoodNutri() {
        return foodNutri;
    }

    public void setFoodNutri(String foodNutri) {
        this.foodNutri = foodNutri;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getFoodDate() {
        return foodDate;
    }

    public void setFoodDate(String foodDate) {
        this.foodDate = foodDate;
    }

    public String getFoodClass() {
        return foodClass;
    }

    public void setFoodClass(String foodClass) {
        this.foodClass = foodClass;
    }

    public String getFoodId() {
        return foodId;
    }

    public void setFoodId(String foodId) {
        this.foodId = foodId;
    }

    public String getFoodCk() {
        return foodCk;
    }

    public void setFoodCk(String foodCk) {
        this.foodCk = foodCk;
    }

    public String getFoodIntake() {
        return foodIntake;
    }

    public void setFoodIntake(String foodIntake) {
        this.foodIntake = foodIntake;
    }

    public String getFoodIngre1() {
        return foodIngre1;
    }

    public void setFoodIngre1(String foodIngre1) {
        this.foodIngre1 = foodIngre1;
    }

    public String getIntake1() {
        return intake1;
    }

    public void setIntake1(String intake1) {
        this.intake1 = intake1;
    }

    public String getFoodIngre2() {
        return foodIngre2;
    }

    public void setFoodIngre2(String foodIngre2) {
        this.foodIngre2 = foodIngre2;
    }

    public String getIntake2() {
        return intake2;
    }

    public void setIntake2(String intake2) {
        this.intake2 = intake2;
    }

    public String getFoodIngre3() {
        return foodIngre3;
    }

    public void setFoodIngre3(String foodIngre3) {
        this.foodIngre3 = foodIngre3;
    }

    public String getIntake3() {
        return intake3;
    }

    public void setIntake3(String intake3) {
        this.intake3 = intake3;
    }

    public String getFoodIngre4() {
        return foodIngre4;
    }

    public void setFoodIngre4(String foodIngre4) {
        this.foodIngre4 = foodIngre4;
    }

    public String getIntake4() {
        return intake4;
    }

    public void setIntake4(String intake4) {
        this.intake4 = intake4;
    }

    public String getFoodIngre5() {
        return foodIngre5;
    }

    public void setFoodIngre5(String foodIngre5) {
        this.foodIngre5 = foodIngre5;
    }

    public String getIntake5() {
        return intake5;
    }

    public void setIntake5(String intake5) {
        this.intake5 = intake5;
    }
}
