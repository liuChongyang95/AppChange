package Adapter;

/**
 * Created by Administrator on 2017/12/28.
 */

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dapp.R;

import JavaBean.Fruit;

import java.util.List;

/**
 * Created by Administrator on 2017/12/27.
 */

public class FruitAdapter extends ArrayAdapter<Fruit> implements Filterable {
    private int resourceId;

    public FruitAdapter(Context context, int textViewResourceId, List<Fruit> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Fruit fruit = getItem(position);
        View view;
        ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.food_Ri_photo = view.findViewById(R.id.search_fruit_image);
            viewHolder.food_Ri_Name = view.findViewById(R.id.search_fruit_name);
//            viewHolder.fruitNutrition = view.findViewById(R.id.search_fruit_nutrition);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.food_Ri_Name.setText(fruit.getRi_Food_name());
        viewHolder.food_Ri_photo.setImageDrawable(fruit.getRi_Food_photo());
//        viewHolder.fruitNutrition.setText(fruit.getNutrition());
        return view;
    }

    class ViewHolder {
        ImageView food_Ri_photo;
        TextView food_Ri_Name;
//        TextView fruitNutrition;
    }
}
