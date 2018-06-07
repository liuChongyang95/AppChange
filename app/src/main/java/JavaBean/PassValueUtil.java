package JavaBean;

import java.io.Serializable;
import java.util.List;


public class PassValueUtil implements Serializable {
    private static final long serializableID = 1L;
    private String datepickContent;
    private List<String> datepickList;

    public static long getSerializableID() {
        return serializableID;
    }

    public String getDatepickContent() {
        return datepickContent;
    }

    public void setDatepickContent(String datepickContent) {
        this.datepickContent = datepickContent;
    }

    public List<String> getDatepickList() {
        return datepickList;
    }

    public void setDatepickList(List<String> datepickList) {
        this.datepickList = datepickList;
    }
}
