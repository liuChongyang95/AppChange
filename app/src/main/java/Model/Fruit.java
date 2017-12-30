package Model;


/**
 * Created by Administrator on 2017/12/27.
 */

public class Fruit {
    private String name;
    private int imageId;
    private String nutrition;

    public Fruit(String name, int imageId,String nutrition) {
        this.name = name;
        this.imageId = imageId;
        this.nutrition= nutrition;
    }

    public String getNutrition() {
        return nutrition;
    }

    public String getName() {
        return name;
    }

    public int getImageId() {
        return imageId;
    }
}
