package Adapter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
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

import Database.DBHelper;
import JavaBean.UserFood;
import JavaBean.UserIntake;
import SearchDao.FoodDao;
import Util.DBUtil;

public class UserfoodAdapter extends ArrayAdapter<UserFood> implements Filterable {
    private int resId;

    public UserfoodAdapter(Context context, int TVresId, List<UserFood> objects) {
        super(context, TVresId, objects);
        resId = TVresId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        UserFood userFood = getItem(position);
        View view;
        ViewHolder viewHolder;
        if (convertView != null) {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        } else {
            view = LayoutInflater.from(getContext()).inflate(resId, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.TVdot = view.findViewById(R.id.tvDot);
            viewHolder.Foodclass = view.findViewById(R.id.foodClass);
            viewHolder.Foodtime = view.findViewById(R.id.tvTime);
            viewHolder.Bottomline = view.findViewById(R.id.tvBottomLine);
            viewHolder.TopLine = view.findViewById(R.id.tvTopLine);
            viewHolder.Foodintake = view.findViewById(R.id.foodIntake);
            viewHolder.Foodname = view.findViewById(R.id.foodName);
            viewHolder.Foodenergy = view.findViewById(R.id.foodEnergy);
            view.setTag(viewHolder);
        }
        if (getItemViewType(position) == 3) {
            viewHolder.TopLine.setVisibility(View.INVISIBLE);
            viewHolder.Foodintake.setTextColor(Color.BLACK);
            viewHolder.Foodname.setTextColor(Color.BLACK);
            viewHolder.Foodclass.setTextColor(Color.BLACK);
            viewHolder.Foodtime.setTextColor(Color.RED);
            viewHolder.Foodenergy.setTextColor(Color.RED);
        } else if (getItemViewType(position) == 4) {
            viewHolder.TopLine.setVisibility(View.VISIBLE);
            viewHolder.Foodintake.setTextColor(Color.BLACK);
            viewHolder.Foodname.setTextColor(Color.BLACK);
            viewHolder.Foodclass.setTextColor(Color.BLACK);
            viewHolder.Foodtime.setTextColor(Color.BLACK);
            viewHolder.Foodenergy.setTextColor(Color.BLACK);
        }

        if (userFood != null) {
            viewHolder.Foodclass.setText(userFood.getFoodClass());
            viewHolder.Foodname.setText(userFood.getFoodName());
            String intake = userFood.getFoodIntake() + "克";
            viewHolder.Foodintake.setText(intake);
            viewHolder.Foodtime.setText(userFood.getFoodDate());
            viewHolder.Foodenergy.setText(userFood.getFoodNutri());
        }
        return view;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return 3;
        } else return 4;
    }

    class ViewHolder {
        TextView TopLine;
        TextView Bottomline;
        TextView TVdot;
        TextView Foodtime;
        TextView Foodclass;
        TextView Foodintake;
        TextView Foodname;
        TextView Foodenergy;
    }
}
