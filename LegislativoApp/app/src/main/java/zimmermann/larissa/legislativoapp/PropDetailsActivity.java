package zimmermann.larissa.legislativoapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import zimmermann.larissa.legislativoapp.adapter.StringAdapter;
import zimmermann.larissa.legislativoapp.communication.PropResponse;
import zimmermann.larissa.legislativoapp.communication.Proposicao;
import zimmermann.larissa.legislativoapp.service.RetrofitService;
import zimmermann.larissa.legislativoapp.service.ServiceGenerator;

public class PropDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prop_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.prop_toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Bundle b = getIntent().getExtras();
        int value = -1; // or other values
        if (b != null) {
            value = b.getInt("Id");
        }
        loadPropById(value);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void loadPropById(int codigo) {

        //Call first time
        RetrofitService service = ServiceGenerator.getClient().create(RetrofitService.class);

        Log.d("MainActivity", "Passou1");

        Call<PropResponse> call = service.getProposicaoById(codigo);

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.prop_detail_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

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

                        List<SpannableStringBuilder> dataList = new ArrayList<SpannableStringBuilder>();

                        addCheckedText(dataList, getString(R.string.propId), String.valueOf(prop.getId()));
                        addCheckedText(dataList, getString(R.string.propDataApresentacao), prop.getDataApresentacao());
                        addCheckedText(dataList, getString(R.string.propDataHora), prop.getStatusProposicao().getDataHora());
                        addCheckedText(dataList, getString(R.string.propTipoAutor), prop.getTipoAutor());
                        addCheckedText(dataList, getString(R.string.propDescricaoSituacao), prop.getStatusProposicao().getDescricaoSituacao());
                        addCheckedText(dataList, getString(R.string.propDescricaoTipo), prop.getDescricaoTipo());
                        addCheckedText(dataList, getString(R.string.propSiglaTipo), prop.getSiglaTipo());
                        addCheckedText(dataList, getString(R.string.propEmenta), prop.getEmenta());
                        addCheckedText(dataList, getString(R.string.propKeywords), prop.getKeywords());
                        addCheckedText(dataList, getString(R.string.propSiglaOrgao), prop.getStatusProposicao().getSiglaOrgao());
                        addCheckedText(dataList, getString(R.string.propRegime), prop.getStatusProposicao().getRegime());
                        addCheckedText(dataList, getString(R.string.propDescricaoTramitacao), prop.getStatusProposicao().getDescricaoTramitacao());
                        addCheckedText(dataList, getString(R.string.propDespacho), prop.getStatusProposicao().getDespacho());

                        StringAdapter adapter = new StringAdapter(dataList, R.layout.list_string);
                        recyclerView.setAdapter(adapter);

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

    public void addCheckedText(List<SpannableStringBuilder> view, String text1, String text2){
        if(text2 == null || text2.equals(".") || text2.equals("")){
            return;
        }else{
            SpannableStringBuilder str = new SpannableStringBuilder(text1 + text2);
            str.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 0, text1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            view.add(str);
        }
    }

}
