package zimmermann.larissa.legislativoapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bluejamesbond.text.DocumentView;

import java.util.List;

import zimmermann.larissa.legislativoapp.R;
import zimmermann.larissa.legislativoapp.communication.Proposicao;

/**
 * Created by laris on 11/08/2017.
 */

public class ProposicaoAdapter extends RecyclerView.Adapter<ProposicaoAdapter.ProposicaoViewHolder> {

    private List<Proposicao> props;
    private int rowLayout;
    private Context context;


    public static class ProposicaoViewHolder extends RecyclerView.ViewHolder {
        LinearLayout propsLayout;
        TextView idLabel, anoLabel;
        TextView id, ano;
        DocumentView ementa;

        public ProposicaoViewHolder(View v) {
            super(v);
            propsLayout = (LinearLayout) v.findViewById(R.id.proposicao_layout);
            idLabel = (TextView) v.findViewById(R.id.idLabel);
            id = (TextView) v.findViewById(R.id.id);
            anoLabel = (TextView) v.findViewById(R.id.anoLabel);
            ano = (TextView) v.findViewById(R.id.ano);
            ementa = (DocumentView) v.findViewById(R.id.ementa);
        }
    }

    public ProposicaoAdapter(List<Proposicao> props, int rowLayout, Context context) {
        this.props = props;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @Override
    public ProposicaoAdapter.ProposicaoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        ProposicaoViewHolder holder = new ProposicaoViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(final ProposicaoViewHolder holder, final int position) {

        holder.ementa.setText(null);
        holder.idLabel.setText(null);
        holder.id.setText(null);

        holder.ementa.setText("Ementa: " + props.get(position).getEmenta().toUpperCase());
        holder.idLabel.setText("Código: ");
        holder.id.setText(String.valueOf(props.get(position).getId()));
        holder.anoLabel.setText("Ano: ");
        holder.ano.setText(String.valueOf(props.get(position).getAno()));

    }

    @Override
    public int getItemCount() {
        return props.size();
    }
}
