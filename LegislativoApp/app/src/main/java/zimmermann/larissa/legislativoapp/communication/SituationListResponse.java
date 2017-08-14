package zimmermann.larissa.legislativoapp.communication;

import java.io.Serializable;
import java.util.List;

/**
 * Created by laris on 14/08/2017.
 */

public class SituationListResponse implements Serializable {

    private List<Situation> dados;
    private List<Link> links;

    public SituationListResponse(List<Situation> dados, List<Link> links) {
        this.dados = dados;
        this.links = links;
    }

    public List<Situation> getDados() {
        return dados;
    }

    public void setDados(List<Situation> dados) {
        this.dados = dados;
    }

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }
}
