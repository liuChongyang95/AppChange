//package Adapter;
//
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.TextView;
//
//import com.example.dapp.R;
//
//import java.util.List;
//
//import JavaBean.Career;
//
//public class CareerAdapter extends RecyclerView.Adapter<CareerAdapter.ViewHolder> {
//    private List<Career> mCareerList;
//
//    public CareerAdapter(List<Career> careerList) {
//        mCareerList = careerList;
//    }
//
//    static class ViewHolder extends RecyclerView.ViewHolder {
//        TextView careerIntensity;
//        Button careerName;
//
//        public ViewHolder(View v) {
//            super(v);
//            careerIntensity = v.findViewById(R.id.career_item_intensity);
//            careerName = v.findViewById(R.id.career_item_name);
//        }
//    }
//
//    @Override
//    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.career_select_item, parent, false);
//        ViewHolder holder = new ViewHolder(view);
//        return holder;
//    }
//
//    @Override
//    public void onBindViewHolder(ViewHolder holder, int position) {
//        Career career = mCareerList.get(position);
//        holder.careerName.setText(career.getCareerName());
//        holder.careerIntensity.setText(career.getIntenSity());
//    }
//
//    @Override
//    public int getItemCount() {
//        return mCareerList.size();
//    }
//}
