package zimmermann.larissa.legislativoteste;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.uncopt.android.widget.text.justify.JustifiedTextView;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProposicaoAdapter extends RecyclerView.Adapter<ProposicaoAdapter.ProposicaoViewHolder> {

    private List<Proposicao> props;
    private int rowLayout;
    private Context context;
    public Proposicao prop = new Proposicao();

    public String dataApresentacao, descricaoSigla;


    public static class ProposicaoViewHolder extends RecyclerView.ViewHolder {
        LinearLayout propsLayout;
        TextView situacao;
        //TextView ano;
        JustifiedTextView ementa;


        public ProposicaoViewHolder(View v) {
            super(v);
            propsLayout = (LinearLayout) v.findViewById(R.id.proposicao_layout);
            situacao = (TextView) v.findViewById(R.id.situacao);
            //ano = (TextView) v.findViewById(R.id.data);
            ementa = (JustifiedTextView) v.findViewById(R.id.ementa);
        }
    }

    public ProposicaoAdapter(List<Proposicao> props, int rowLayout, Context context) {
        this.props = props;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @Override
    public ProposicaoAdapter.ProposicaoViewHolder onCreateViewHolder(ViewGroup parent,
                                                            int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new ProposicaoViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ProposicaoViewHolder holder, final int position) {

        int id = props.get(position).getId();

        RetrofitService service = ServiceGenerator.getClient().create(RetrofitService.class);

        Call<PropResponse> call = service.getProposicaoById();

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
                        Log.d("MainActivity", "PropResponse structure received!");

                        Proposicao proposicao = respostaServidor.getDados();
                        Log.d("MainActivity", "dataApresentacao.....");
                        Log.d("MainActivity", "dataApresentacao " + proposicao.getDataApresentacao());


                        //descricaoSigla = respostaServidor.getDados().getStatusProposicao().getDescricaoSituacao();
                        Log.d("MainActivity", "1");
                       // dataApresentacao = respostaServidor.getDados().getDataApresentacao();
                        Log.d("MainActivity", "2");

                        //prop.setStatusProposicao(respostaServidor.getDados().getStatusProposicao());
                        //prop.setDataApresentacao(respostaServidor.getDados().getDataApresentacao());

                    } else {
                        Log.d("MainActivity", "Error onResponse()");
                    }

                } else {

                    Log.d("MainActivity", "Error OnResponse()");
                    // segura os erros de requisição
                    ResponseBody errorBody = response.errorBody();
                }
            }

            @Override
            public void onFailure(Call<PropResponse> call, Throwable t) {
                Log.d("MainActivity", "Error OnFailure()");
            }
        });

        Log.d("MainActivity", "3");
        holder.situacao.setText(prop.getStatusProposicao().getDescricaoSituacao() + " - " +
                                prop.getDataApresentacao());
        Log.d("MainActivity", "4");
        holder.ementa.setText(props.get(position).getEmenta().toUpperCase());
        Log.d("MainActivity", "5");

    }

    @Override
    public int getItemCount() {
        return props.size();
    }
}