package zimmermann.larissa.legislativoteste;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.List;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import zimmermann.larissa.legislativoteste.Data;
import zimmermann.larissa.legislativoteste.Proposicao;
import zimmermann.larissa.legislativoteste.ProposicaoAdapter;
import zimmermann.larissa.legislativoteste.R;
import zimmermann.larissa.legislativoteste.RetrofitService;
import zimmermann.larissa.legislativoteste.ServiceGenerator;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.props_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        RetrofitService service = ServiceGenerator.getClient().create(RetrofitService.class);

        Log.d("MainActivity", "Passou1");

        Call<Data> call = service.getProposicaoList();

        call.enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {

                Log.d("MainActivity", "Recebeu resposta.");
                if (response.isSuccessful()) {
                    Log.d("MainActivity", "Resposta recebida com sucesso.");
                    Data respostaServidor = response.body();

                    Log.d("MainActivity", "Response saved!");

                    //verifica aqui se o corpo da resposta não é nulo
                    if (respostaServidor != null) {
                        Log.d("MainActivity", "Data structure received!");
                        List<Proposicao> movies = respostaServidor.getDados();
                        recyclerView.setAdapter(new ProposicaoAdapter(movies, R.layout.list_item_movie, getApplicationContext()));

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
            public void onFailure(Call<Data> call, Throwable t) {

                Toast.makeText(getApplicationContext(), "Erro na chamada ao servidor", Toast.LENGTH_SHORT).show();
                Log.d("MainActivity", "Error OnFailure()");
            }
        });

    }
}
