package JavaBean;


import android.graphics.drawable.Drawable;

/**
 * Created by Administrator on 2017/12/27.
 */

public class Fruit {
    private String Ri_Food_name;
    private String Ri_Food_id;
    private Drawable Ri_Food_photo;
    private String Ri_Food_ep_id;

    public Fruit(String Ri_Food_name, Drawable Ri_Food_photo, String Ri_Food_id, String Ri_Food_ep_id) {
        this.Ri_Food_name = Ri_Food_name;
        this.Ri_Food_photo = Ri_Food_photo;
        this.Ri_Food_id = Ri_Food_id;
        this.Ri_Food_ep_id = Ri_Food_ep_id;
    }

    public String getRi_Food_name() {
        return Ri_Food_name;
    }

    public void setRi_Food_name(String ri_Food_name) {
        Ri_Food_name = ri_Food_name;
    }

    public String getRi_Food_id() {
        return Ri_Food_id;
    }

    public void setRi_Food_id(String ri_Food_id) {
        Ri_Food_id = ri_Food_id;
    }

    public Drawable getRi_Food_photo() {
        return Ri_Food_photo;
    }

    public void setRi_Food_photo(Drawable ri_Food_photo) {
        Ri_Food_photo = ri_Food_photo;
    }

    public String getRi_Food_ep_id() {
        return Ri_Food_ep_id;
    }

    public void setRi_Food_ep_id(String ri_Food_ep_id) {
        Ri_Food_ep_id = ri_Food_ep_id;
    }
}
