package Fragment_fs;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.GProject.DiabetesApp.Nutrition_all;
import com.GProject.DiabetesApp.R;


public class Fragment_FS_nutritioninfo extends Fragment {
    private View view;
    private String foodName;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fs_nutrition_info, container, false);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    public void loading_nutrition(String energy, String protein, String CH, String DF, String fat) {
        TextView energy_index = view.findViewById(R.id.food_dic_energy_index);
        TextView energy_ps = view.findViewById(R.id.food_dic_energy_ps);
        TextView protein_index = view.findViewById(R.id.food_dic_protein_index);
        TextView protein_ps = view.findViewById(R.id.food_dic_protein_ps);
        TextView CH_ps = view.findViewById(R.id.food_dic_CH_ps);
        TextView CH_index = view.findViewById(R.id.food_dic_CH_index);
        TextView DF_ps = view.findViewById(R.id.food_dic_DF_ps);
        TextView DF_index = view.findViewById(R.id.food_dic_DF_index);
        TextView fat_index = view.findViewById(R.id.food_dic_fat_index);
        TextView fat_ps = view.findViewById(R.id.food_dic_fat_ps);
        LinearLayout more_info_ll = view.findViewById(R.id.more_nutrition);


        energy_index.setText(String.format(getResources().getString(R.string.nutrition_energy), energy));
        protein_index.setText(String.format(getResources().getString(R.string.nutrition_protein), protein));
        CH_index.setText(String.format(getResources().getString(R.string.nutrition_CH), CH));
        DF_index.setText(String.format(getResources().getString(R.string.nutrition_DF), DF));
        fat_index.setText(String.format(getResources().getString(R.string.nutrition_fat), fat));

        if (!energy.equals("—")) {
            if (Float.valueOf(energy) <= 500) {
                energy_ps.setText("低热量");
            }
            if (Float.valueOf(energy) >= 2000) {
                energy_ps.setText("高热量");
            }
        }

        if (!protein.equals("—")) {
            if (Float.valueOf(protein) >= 10) {
                protein_ps.setText("高蛋白");
            }
            if (Float.valueOf(protein) <= 1) {
                protein_ps.setText("低蛋白");
            }
        }

        if (!fat.equals("—")) {
            if (Float.valueOf(fat) <= 3) {
                fat_ps.setText("低脂肪");
            }
            if (Float.valueOf(fat) >= 20) {
                fat_ps.setText("高脂肪");
            }
        }

        if (!DF.equals("—")) {
            if (Float.valueOf(DF) >= 6) {
                DF_ps.setText("高纤维");
            }
        }
        more_info_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                foodName = getArguments().getString("fruit_name");
                Intent intent = new Intent(getActivity(), Nutrition_all.class);
                Bundle bundle = new Bundle();
                bundle.putString("all_nutrition_name", foodName);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}