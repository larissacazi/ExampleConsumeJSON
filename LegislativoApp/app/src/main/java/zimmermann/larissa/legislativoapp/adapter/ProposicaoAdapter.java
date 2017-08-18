package zimmermann.larissa.legislativoapp.adapter;

import android.animation.ValueAnimator;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
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


    public static class ProposicaoViewHolder extends RecyclerView.ViewHolder{

        LinearLayout propsLayout;
        TextView id, ano;
        TextView ementa;

        public ProposicaoViewHolder(View v) {
            super(v);

            id = (TextView) v.findViewById(R.id.id);
            ano = (TextView) v.findViewById(R.id.ano);
            ementa = (TextView) v.findViewById(R.id.ementa);
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

        holder.ementa.setText(context.getString(R.string.propListEmenta) + props.get(position).getEmenta());
        holder.id.setText(context.getString(R.string.propListCodigo) + String.valueOf(props.get(position).getId()));
        holder.ano.setText(context.getString(R.string.propListAno) + String.valueOf(props.get(position).getAno()));

    }

    @Override
    public int getItemCount() {
        return props.size();
    }

    @Override
    public long getItemId(int position) {
        return props.get(position).getId();
    }

}
