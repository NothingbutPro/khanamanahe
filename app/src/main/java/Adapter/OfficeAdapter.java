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

import Model.OfficeModel;
import com.ics.cifatofoody.R;

public class OfficeAdapter extends RecyclerView.Adapter<OfficeAdapter.MyViewHolder> {
    private Context mContext;
    List<OfficeModel> officeModelList;

    public OfficeAdapter(Activity mContext, List<OfficeModel> officeModelList) {
        this.mContext = mContext;
        this.officeModelList = officeModelList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.office_row, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        OfficeModel officeModel = officeModelList.get(position);
        String id = officeModel.getId();
        String location_offers = officeModel.getLocationOffers();
        String type = officeModel.getType();
        String statu = officeModel.getStatus();
        holder.txtOffice.setText(Html.fromHtml(location_offers));


        /*for (OfficeModel officeModel1 : officeModelList){
            String id = officeModelList.get(0).getId();
            String location_offers = officeModelList.get(0).getLocationOffers();
            String type = officeModelList.get(0).getType();
            String statu = officeModelList.get(0).getStatus();
            holder.txtOffice.setText(Html.fromHtml(location_offers));
        }*/

    }

    @Override
    public int getItemCount() {
        return officeModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView txtOffice;

        public MyViewHolder(View itemView) {
            super(itemView);
            txtOffice = itemView.findViewById(R.id.txtOffice);
        }
    }
}
