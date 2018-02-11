package Adapter;

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

import java.security.PublicKey;
import java.util.List;

import JavaBean.Fruit;
import JavaBean.History;

public class HistoryAdapter extends ArrayAdapter<History> implements Filterable {
    private int resId;

    public HistoryAdapter(Context context, int tvResId, List<History> objects) {
        super(context, tvResId, objects);
        resId = tvResId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        History history = getItem(position);
        View view;
        ViewHolder viewHolder;
        if (convertView != null) {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        } else {
            view = LayoutInflater.from(getContext()).inflate(resId, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.name = view.findViewById(R.id.history_name_item);
            view.setTag(viewHolder);
        }
        if (history != null) {
            viewHolder.name.setText(history.getFoodname());
        }
        return view;
    }

    class ViewHolder {
        TextView name;
    }
}
