package zimmermann.larissa.legislativoteste;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.uncopt.android.widget.text.justify.JustifiedTextView;

import java.util.List;

import zimmermann.larissa.legislativoteste.R;
import zimmermann.larissa.legislativoteste.Proposicao;

public class ProposicaoAdapter extends RecyclerView.Adapter<ProposicaoAdapter.ProposicaoViewHolder> {

    private List<Proposicao> props;
    private int rowLayout;
    private Context context;


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
        holder.situacao.setText(String.valueOf(props.get(position).getIdTipo()) + " - " +
                                String.valueOf(props.get(position).getAno()));
        //holder.ano.setText(String.valueOf(props.get(position).getAno()));
        holder.ementa.setText(props.get(position).getEmenta().toUpperCase());
    }

    @Override
    public int getItemCount() {
        return props.size();
    }
}