package Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.dapp.R;

import java.util.List;

import JavaBean.Food;

public class FoodAdapter extends ArrayAdapter<Food> implements Filterable {
    private int resourceId;

    public FoodAdapter(Context context, int textViewRes, List<Food> objects) {
        super(context, textViewRes, objects);
        resourceId = textViewRes;
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Food food = getItem(position);
        View view;
        ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.food_dic_energy = view.findViewById(R.id.search_fruit_nutrition);
            viewHolder.food_dic_name = view.findViewById(R.id.search_fruit_name);
            view.setTag(viewHolder);

        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();

        }
        if (food != null) {
            viewHolder.food_dic_energy.setText(food.getEnergy()+"千卡/100克");
            viewHolder.food_dic_name.setText(food.getName());
        }
        return view;
    }

    class ViewHolder {
        TextView food_dic_name;
        TextView food_dic_energy;
    }
}
