package zimmermann.larissa.legislativoapp.communication;

import android.util.Log;

/**
 * Created by laris on 11/08/2017.
 */

public class PropResponse {
    private Proposicao dados;
    private Link links;

    public PropResponse(Proposicao dados, Link links) {
        this.dados = dados;
        this.links = links;
    }

    public Proposicao getDados() {
        return dados;
    }

    public void setDados(Proposicao dados) {
        this.dados = dados;
    }

    public Link getLinks() {
        return links;
    }

    public void setLinks(Link links) {
        this.links = links;
    }
}
