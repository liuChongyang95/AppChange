package Model;


import android.graphics.drawable.Drawable;
import android.widget.ImageView;

/**
 * Created by Administrator on 2017/12/27.
 */

public class Fruit {
    private String name;
    private String nutrition;
    private Drawable picture;

    public Fruit(String name, Drawable picture, String nutrition) {
        this.name = name;
        this.picture = picture;
        this.nutrition = nutrition;
    }

    public Drawable getPicture() {
        return picture;
    }

    public String getNutrition() {
        return nutrition;
    }

    public String getName() {
        return name;
    }
}
