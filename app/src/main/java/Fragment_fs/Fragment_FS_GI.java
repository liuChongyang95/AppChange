package Fragment_fs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dapp.R;

public class Fragment_FS_GI extends Fragment {
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fs_gi, container, false);
        return view;
    }

    public void loading_GI(String GI, String GI_per) {
        TextView gi_index = view.findViewById(R.id.GI_index);
        TextView gi_per_index = view.findViewById(R.id.GI_per_index);
        TextView gi_ps = view.findViewById(R.id.GI_ps);
        TextView gi_per_ps = view.findViewById(R.id.GI_per_ps);
        if (GI == null || GI.length() <= 0) {
            gi_index.setText("——");
        } else gi_index.setText(GI);

        if (GI_per == null || GI_per.length() <= 0) {
            gi_per_index.setText("——");
        } else gi_per_index.setText(GI_per);


    }
}
