package JavaBean;

import android.support.annotation.NonNull;


public class Career {
    private String careerName;
    private String intenSity;
    private String shape;
    private String minEnergy;
    private String maxEnergy;

    public Career(String careerName, String intenSity, String shape, String minEnergy, String maxEnergy) {
        this.careerName = careerName;
        this.intenSity = intenSity;
        this.shape = shape;
        this.minEnergy = minEnergy;
        this.maxEnergy = maxEnergy;
    }

    public String getCareerName() {
        return careerName;
    }

    public void setCareerName(String careerName) {
        this.careerName = careerName;
    }

    public String getIntenSity() {
        return intenSity;
    }

    public void setIntenSity(String intenSity) {
        this.intenSity = intenSity;
    }

    public String getShape() {
        return shape;
    }

    public void setShape(String shape) {
        this.shape = shape;
    }

    public String getMinEnergy() {
        return minEnergy;
    }

    public void setMinEnergy(String minEnergy) {
        this.minEnergy = minEnergy;
    }

    public String getMaxEnergy() {
        return maxEnergy;
    }

    public void setMaxEnergy(String maxEnergy) {
        this.maxEnergy = maxEnergy;
    }

}


