package zimmermann.larissa.legislativoteste;

import android.util.Log;

/**
 * Created by gms19 on 04/08/2017.
 */

public class PropResponse {
    private Proposicao dados;
    private Link links;

    public PropResponse(Proposicao dados, Link links) {
        this.dados = dados;
        this.links = links;
    }

    public Proposicao getDados() {
        Log.d("MainActivity", "getDados()");
        Log.d("MainActivity", "Dados: " + this.dados);
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
