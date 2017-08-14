package zimmermann.larissa.legislativoapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import zimmermann.larissa.legislativoapp.adapter.ProposicaoAdapter;
import zimmermann.larissa.legislativoapp.adapter.SituationAdapter;
import zimmermann.larissa.legislativoapp.communication.PropListResponse;
import zimmermann.larissa.legislativoapp.communication.Proposicao;
import zimmermann.larissa.legislativoapp.communication.Situation;
import zimmermann.larissa.legislativoapp.communication.SituationListResponse;
import zimmermann.larissa.legislativoapp.recycler.ClickListener;
import zimmermann.larissa.legislativoapp.recycler.DividerItemDecoration;
import zimmermann.larissa.legislativoapp.recycler.RecyclerTouchListener;
import zimmermann.larissa.legislativoapp.service.RetrofitService;
import zimmermann.larissa.legislativoapp.service.ServiceGenerator;

public class SituationPropListMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_situation_prop_list_main);

        loadSituationPropsList();
    }

    public void loadSituationPropsList(){
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.props_recyclerview);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Call first time
        RetrofitService service = ServiceGenerator.getClient().create(RetrofitService.class);

        Log.d("MainActivity", "loadSituationPropsList: Passou1");

        // Call<PropListResponse> call = service.getDefaultProposicaoList();
        Call<SituationListResponse> call = service.getAllPropSituation();

        call.enqueue(new Callback<SituationListResponse>() {
            @Override
            public void onResponse(Call<SituationListResponse> call, Response<SituationListResponse> response) {

                Log.d("MainActivity", "loadSituationPropsList: Recebeu resposta.");
                if (response.isSuccessful()) {
                    Log.d("MainActivity", "loadSituationPropsList: Resposta recebida com sucesso.");
                    SituationListResponse respostaServidor = response.body();
                    Log.d("MainActivity", "loadSituationPropsListResponse saved!");

                    //verifica aqui se o corpo da resposta não é nulo
                    if (respostaServidor != null) {
                        Log.d("MainActivity", "PropListResponse: structure received!");
                        final List<Situation> situationList = respostaServidor.getDados();

                        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new ClickListener() {
                            //@Override
                            public void onClick(View view, int position) {
                                Situation situation = situationList.get(position);
                                Toast.makeText(getApplicationContext(), situation.getId() + " is selected!", Toast.LENGTH_SHORT).show();
                            }

                            //@Override
                            public void onLongClick(View view, int position) {

                            }
                        }));

                        recyclerView.setAdapter(new SituationAdapter(situationList, R.layout.situation_prop_list, getApplicationContext()));

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
            public void onFailure(Call<SituationListResponse> call, Throwable t) {

                Toast.makeText(getApplicationContext(), "Erro na chamada ao servidor", Toast.LENGTH_SHORT).show();
                Log.d("MainActivity", "loadSituationPropsList:Error OnFailure()");
            }
        });
    }
}
