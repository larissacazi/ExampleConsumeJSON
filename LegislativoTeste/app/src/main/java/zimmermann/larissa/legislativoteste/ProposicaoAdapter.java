package zimmermann.larissa.legislativoteste;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import zimmermann.larissa.legislativoteste.R;
import zimmermann.larissa.legislativoteste.Proposicao;

public class ProposicaoAdapter extends RecyclerView.Adapter<ProposicaoAdapter.ProposicaoViewHolder> {

    private List<Proposicao> props;
    private int rowLayout;
    private Context context;


    public static class ProposicaoViewHolder extends RecyclerView.ViewHolder {
        LinearLayout propsLayout;
        TextView Id;
        TextView ano;
        TextView ementa;
        TextView numero;


        public ProposicaoViewHolder(View v) {
            super(v);
            propsLayout = (LinearLayout) v.findViewById(R.id.proposicao_layout);
            Id = (TextView) v.findViewById(R.id.title);
            ano = (TextView) v.findViewById(R.id.subtitle);
            ementa = (TextView) v.findViewById(R.id.description);
            numero = (TextView) v.findViewById(R.id.rating);
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
        holder.Id.setText(String.valueOf(props.get(position).getId()));
        holder.ano.setText(String.valueOf(props.get(position).getAno()));
        holder.ementa.setText(props.get(position).getEmenta());
        holder.numero.setText(String.valueOf(props.get(position).getNumero()));
    }

    @Override
    public int getItemCount() {
        return props.size();
    }
}