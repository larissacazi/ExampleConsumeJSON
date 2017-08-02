package zimmermann.larissa.legislativoteste;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private TextView id;
    private TextView uri;
    private TextView siglaTipo;
    private TextView idTipo;
    private TextView numero;
    private TextView ano;
    private TextView ementa;
    private Button getButton;
    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        id = (TextView)findViewById(R.id.idTextID);
        uri = (TextView)findViewById(R.id.uriTextID);
        siglaTipo = (TextView)findViewById(R.id.siglaTextID);
        idTipo = (TextView)findViewById(R.id.idTypeTextID);
        numero = (TextView)findViewById(R.id.numberTextID);
        ano = (TextView)findViewById(R.id.yearTextID);
        ementa = (TextView)findViewById(R.id.ementaTextID);

        getButton = (Button)findViewById(R.id.getButtonID);

        getButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progress = new ProgressDialog(MainActivity.this);
                progress.setTitle("Enviando...");
                progress.show();

                retrofitGetProposicao();
            }
        });
    }


    public void retrofitGetProposicao() {

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
                        Proposicao first = respostaServidor.getDados().get(0);
                        Log.d("MainActivity", "First proposicao was gotten.");

                        id.setText(Integer.toString(first.getId()));
                        uri.setText(first.getUri());
                        siglaTipo.setText(first.getSigla());
                        idTipo.setText(Integer.toString(first.getIdTipo()));
                        numero.setText(Integer.toString(first.getNumero()));
                        ano.setText(Integer.toString(first.getAno()));
                        ementa.setText(first.getEmenta());

                    } else {

                        Toast.makeText(getApplicationContext(),"Resposta nula do servidor", Toast.LENGTH_SHORT).show();
                    }

                } else {

                    Toast.makeText(getApplicationContext(),"Resposta não foi sucesso", Toast.LENGTH_SHORT).show();
                    // segura os erros de requisição
                    ResponseBody errorBody = response.errorBody();
                }

                progress.dismiss();
            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {

                Toast.makeText(getApplicationContext(),"Erro na chamada ao servidor", Toast.LENGTH_SHORT).show();
                Log.d("MainActivity", "Error OnFailure()");
            }
        });

    }
}
