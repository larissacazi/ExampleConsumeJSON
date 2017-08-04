package zimmermann.larissa.legislativoteste;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by laris on 01/08/2017.
 */

public class Link implements Serializable{
    private String rel;
    private String href;

    public Link(String rel, String href) {
        this.rel = rel;
        this.href = href;
    }

    public String getRel() {
        return rel;
    }

    public void setRel(String rel) {
        this.rel = rel;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }
}
