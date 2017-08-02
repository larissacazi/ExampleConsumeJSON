package zimmermann.larissa.legislativoteste;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by laris on 01/08/2017.
 */

public class Data implements Serializable{

    private List<Proposicao> dados;
    private List<Link> links;

    public Data(List<Proposicao> dados, List<Link> links) {
        this.dados = dados;
        this.links = links;
    }

    public List<Proposicao> getDados() {
        return dados;
    }

    public void setDados(List<Proposicao> dados) {
        this.dados = dados;
    }

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }
}
