package zimmermann.larissa.legislativoapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import zimmermann.larissa.legislativoapp.adapter.ProposicaoAdapter;
import zimmermann.larissa.legislativoapp.communication.PropListResponse;
import zimmermann.larissa.legislativoapp.communication.PropResponse;
import zimmermann.larissa.legislativoapp.communication.Proposicao;
import zimmermann.larissa.legislativoapp.recycler.ClickListener;
import zimmermann.larissa.legislativoapp.recycler.DividerItemDecoration;
import zimmermann.larissa.legislativoapp.recycler.RecyclerTouchListener;
import zimmermann.larissa.legislativoapp.service.RetrofitService;
import zimmermann.larissa.legislativoapp.service.ServiceGenerator;

public class PropDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prop_details);

        Bundle b = getIntent().getExtras();
        int value = -1; // or other values
        if (b != null) {
            value = b.getInt("Id");
        }

        loadPropById(value);
    }

    public void loadPropById(int codigo) {
        final TextView id, dataApresentacao, siglaTipo, tipoAutor, descricaoTipo, ementa, keywords;
        final TextView descricaoSituacao, dataHora, siglaOrgao, regime, descricaoTramitacao, despacho;

        id = (TextView) this.findViewById(R.id.id);
        dataApresentacao = (TextView) this.findViewById(R.id.dataApresentacao);
        siglaTipo = (TextView) this.findViewById(R.id.siglaTipo);
        tipoAutor = (TextView) this.findViewById(R.id.tipoAutor);
        descricaoTipo = (TextView) this.findViewById(R.id.descricaoTipo);
        ementa = (TextView) this.findViewById(R.id.ementa);
        keywords = (TextView) this.findViewById(R.id.keywords);
        descricaoSituacao = (TextView) this.findViewById(R.id.descricaoSituacao);
        dataHora = (TextView) this.findViewById(R.id.dataHora);
        siglaOrgao = (TextView) this.findViewById(R.id.siglaOrgao);
        regime = (TextView) this.findViewById(R.id.regime);
        descricaoTramitacao = (TextView) this.findViewById(R.id.descricaoTramitacao);
        despacho = (TextView) this.findViewById(R.id.despacho);

        //Call first time
        RetrofitService service = ServiceGenerator.getClient().create(RetrofitService.class);

        Log.d("MainActivity", "Passou1");

        Call<PropResponse> call = service.getProposicaoById(codigo);

        call.enqueue(new Callback<PropResponse>() {
            @Override
            public void onResponse(Call<PropResponse> call, Response<PropResponse> response) {

                Log.d("MainActivity", "Recebeu resposta.");
                if (response.isSuccessful()) {
                    Log.d("MainActivity", "Resposta recebida com sucesso.");
                    PropResponse respostaServidor = response.body();
                    Log.d("MainActivity", "Response saved!");

                    //verifica aqui se o corpo da resposta não é nulo
                    if (respostaServidor != null) {
                        Log.d("MainActivity", "PropListResponse structure received!");
                        final Proposicao prop = respostaServidor.getDados();

                        id.setText(getString(R.string.propId) + prop.getId());
                        dataApresentacao.setText(getString(R.string.propDataApresentacao) + prop.getDataApresentacao());
                        siglaTipo.setText(getString(R.string.propSiglaTipo) + prop.getSiglaTipo());
                        tipoAutor.setText(getString(R.string.propTipoAutor) + prop.getTipoAutor());
                        descricaoTipo.setText(getString(R.string.propDescricaoTipo) + prop.getDescricaoTipo());
                        ementa.setText(getString(R.string.propEmenta) + prop.getEmenta());
                        keywords.setText(getString(R.string.propKeywords) + prop.getKeywords());
                        descricaoSituacao.setText(getString(R.string.propDescricaoSituacao) + prop.getStatusProposicao().getDescricaoSituacao());
                        dataHora.setText(getString(R.string.propDataHora) + prop.getStatusProposicao().getDataHora());
                        siglaOrgao.setText(getString(R.string.propSiglaOrgao) + prop.getStatusProposicao().getSiglaOrgao());
                        regime.setText(getString(R.string.propRegime) + prop.getStatusProposicao().getRegime());
                        descricaoTramitacao.setText(getString(R.string.propDescricaoTramitacao) + prop.getStatusProposicao().getDescricaoTramitacao());
                        despacho.setText(getString(R.string.propDespacho) + prop.getStatusProposicao().getDespacho());

                    } else {

                        Toast.makeText(getApplicationContext(), "Resposta nula do servidor", Toast.LENGTH_SHORT).show();
                    }

                } else {

                    Toast.makeText(getApplicationContext(), "Resposta não foi sucesso", Toast.LENGTH_SHORT).show();
                    // segura os erros de requisição
                    ResponseBody errorBody = response.errorBody();
                }
            }

            @Override
            public void onFailure(Call<PropResponse> call, Throwable t) {

                Toast.makeText(getApplicationContext(), "Erro na chamada ao servidor", Toast.LENGTH_SHORT).show();
                Log.d("MainActivity", "Error OnFailure()");
            }
        });
    }
}
