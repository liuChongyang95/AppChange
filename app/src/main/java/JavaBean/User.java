package JavaBean;

import android.graphics.drawable.Drawable;

/**
 * Created by Administrator on 2018/1/7.
 */

public class User {
    private int _id;
    private String name;
    private Drawable user_pic;
    private String sex;
    private String birth;
    private String tall;
    private String real_weight;
    private String expect_weight;
    private String career;

    public int getId() {
        return _id;
    }

    public void setId(int _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Drawable getUser_pic() {
        return user_pic;
    }

    public void setUser_pic(Drawable user_pic) {
        this.user_pic = user_pic;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getTall() {
        return tall;
    }

    public void setTall(String tall) {
        this.tall = tall;
    }

    public String getReal_weight() {
        return real_weight;
    }

    public void setReal_weight(String real_weight) {
        this.real_weight = real_weight;
    }

    public String getExpect_weight() {
        return expect_weight;
    }

    public void setExpect_weight(String expect_weight) {
        this.expect_weight = expect_weight;
    }

    public String getCareer() {
        return career;
    }

    public void setCareer(String career) {
        this.career = career;
    }

}
