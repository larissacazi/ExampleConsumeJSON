package zimmermann.larissa.legislativoapp.communication;

import java.io.Serializable;
import java.util.List;

/**
 * Created by laris on 16/08/2017.
 */

public class DeputadoListResponse implements Serializable {

    private List<Deputado> dados;
    private List<Link> links;

    public DeputadoListResponse(List<Deputado> dados, List<Link> links) {
        this.dados = dados;
        this.links = links;
    }

    public List<Deputado> getDados() {
        return dados;
    }

    public void setDados(List<Deputado> dados) {
        this.dados = dados;
    }

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }
}
