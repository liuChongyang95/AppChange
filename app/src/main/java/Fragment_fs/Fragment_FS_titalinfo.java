package Fragment_fs;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dapp.R;

public class Fragment_FS_titalinfo extends Fragment {
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fs_tital_info, container, false);
        return view;
    }

    @SuppressLint("SetTextI18n")
    public void loading_title(String name, String energy) {
        TextView food_name = view.findViewById(R.id.fs_Tital_info_name);
        TextView food_energy = view.findViewById(R.id.fs_Tital_info_energy);
        food_name.setText(name);
        food_energy.setText(energy + "千卡");
    }
}
