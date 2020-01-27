package Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import Model.Offer_Model;
import com.ics.cifatofoody.R;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.MyViewHolder> {
    private Context mContext;
    List<Offer_Model> offerModelList;

    public OrderAdapter(Activity mContext, List<Offer_Model> offerModelList) {
        this.mContext = mContext;
        this.offerModelList = offerModelList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.offer_row, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        String id = offerModelList.get(0).getId();
        String location_offers = offerModelList.get(0).getLocationOffers();
        String type = offerModelList.get(0).getType();
        String statu = offerModelList.get(0).getStatus();
        holder.txtOffer.setText(Html.fromHtml(location_offers));
    }

    @Override
    public int getItemCount() {
        return offerModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView txtOffer;

        public MyViewHolder(View itemView) {
            super(itemView);
            txtOffer = itemView.findViewById(R.id.txtOffer);
        }
    }
}
