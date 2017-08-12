package zimmermann.larissa.legislativoapp.communication;

/**
 * Created by laris on 11/08/2017.
 */

public class StatusProposicao {
        private String dataHora = "";
        private int sequencia = 0;
        private String siglaOrgao = "";
        private String uriOrgao = "";
        private String regime = "";
        private String descricaoTramitacao = "";
        private String idTipoTramitacao = "";
        private String descricaoSituacao = "";
        private int idSituacao = 0;
        private String despacho = "";
        private String url = "";

        public String getDataHora() {
            return dataHora;
        }

        public void setDataHora(String dataHora) {
            this.dataHora = dataHora;
        }

        public int getSequencia() {
            return sequencia;
        }

        public void setSequencia(int sequencia) {
            this.sequencia = sequencia;
        }

        public String getSiglaOrgao() {
            return siglaOrgao;
        }

        public void setSiglaOrgao(String siglaOrgao) {
            this.siglaOrgao = siglaOrgao;
        }

        public String getUriOrgao() {
            return uriOrgao;
        }

        public void setUriOrgao(String uriOrgao) {
            this.uriOrgao = uriOrgao;
        }

        public String getRegime() {
            return regime;
        }

        public void setRegime(String regime) {
            this.regime = regime;
        }

        public String getDescricaoTramitacao() {
            return descricaoTramitacao;
        }

        public void setDescricaoTramitacao(String descricaoTramitacao) {
            this.descricaoTramitacao = descricaoTramitacao;
        }

        public String getIdTipoTramitacao() {
            return idTipoTramitacao;
        }

        public void setIdTipoTramitacao(String idTipoTramitacao) {
            this.idTipoTramitacao = idTipoTramitacao;
        }

        public String getDescricaoSituacao() {
            return descricaoSituacao;
        }

        public void setDescricaoSituacao(String descricaoSituacao) {
            this.descricaoSituacao = descricaoSituacao;
        }

        public int getIdSituacao() {
            return idSituacao;
        }

        public void setIdSituacao(int idSituacao) {
            this.idSituacao = idSituacao;
        }

        public String getDespacho() {
            return despacho;
        }

        public void setDespacho(String despacho) {
            this.despacho = despacho;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
}
