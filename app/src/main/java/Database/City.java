package Database;

import org.litepal.crud.DataSupport;

/*
* litepal中通过javabean创建数据库映射 用于用户地理信息更改中的县选项。应该是，记不清了。
* */


public class City extends DataSupport {
    private int id;
    private String cityName;
    private int cityCode;
//    Record the id of the current city's province
    private int provinceId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public int getCityCode() {
        return cityCode;
    }

    public void setCityCode(int cityCode) {
        this.cityCode = cityCode;
    }

    public int getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }
}
