package com.example.dapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;



public class CareerList extends AppCompatActivity implements View.OnClickListener {

    private Button light_1;
    private Button light_2;
    private Button light_3;
    private Button light_4;

    private Button secondary_1;
    private Button secondary_2;
    private Button secondary_3;
    private Button secondary_4;
    private Button secondary_5;

    private Button heavy_1;
    private Button heavy_2;
    private Button heavy_3;
    private Button heavy_5;
    private Button heavy_4;
    private Button heavy_6;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.career_select);
        Toolbar toolbar = findViewById(R.id.career_toolBar);
        setSupportActionBar(toolbar);
        toolbar.getBackground().setAlpha(0);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CareerList.this.finish();
            }
        });
        light_1 = findViewById(R.id.teacher);
        light_2 = findViewById(R.id.office);
        light_3 = findViewById(R.id.saller);
        light_4 = findViewById(R.id.home_man);

        secondary_1 = findViewById(R.id.student);
        secondary_2 = findViewById(R.id.drive);
        secondary_3 = findViewById(R.id.doctor_sur);
        secondary_4 = findViewById(R.id.PE_teacher);
        secondary_5 = findViewById(R.id.work_normal);

        heavy_1 = findViewById(R.id.work_struct);
        heavy_2 = findViewById(R.id.trans);
        heavy_3 = findViewById(R.id.smelt);
        heavy_4 = findViewById(R.id.work_heavy);
        heavy_5 = findViewById(R.id.sports);
        heavy_6 = findViewById(R.id.dancer);

        light_1.setOnClickListener(this);
        light_2.setOnClickListener(this);
        light_3.setOnClickListener(this);
        light_4.setOnClickListener(this);

        secondary_1.setOnClickListener(this);
        secondary_2.setOnClickListener(this);
        secondary_3.setOnClickListener(this);
        secondary_4.setOnClickListener(this);
        secondary_5.setOnClickListener(this);

        heavy_1.setOnClickListener(this);
        heavy_2.setOnClickListener(this);
        heavy_3.setOnClickListener(this);
        heavy_4.setOnClickListener(this);
        heavy_5.setOnClickListener(this);
        heavy_6.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.teacher:
                String career_name = light_1.getText().toString();
                Intent teacher = new Intent();
                teacher.putExtra("career_name", career_name);
                setResult(RESULT_OK, teacher);
                finish();
                break;
            case R.id.office:
                career_name = light_2.getText().toString();
                Intent office = new Intent();
                office.putExtra("career_name", career_name);
                setResult(RESULT_OK, office);
                finish();
                break;
            case R.id.saller:
                career_name = light_3.getText().toString();
                Intent saller = new Intent();
                saller.putExtra("career_name", career_name);
                setResult(RESULT_OK, saller);
                finish();
                break;
            case R.id.home_man:
                career_name = light_4.getText().toString();
                Intent home_man = new Intent();
                home_man.putExtra("career_name", career_name);
                setResult(RESULT_OK, home_man);
                finish();
                break;
            case R.id.student:
                career_name = secondary_1.getText().toString();
                Intent student = new Intent();
                student.putExtra("career_name", career_name);
                setResult(RESULT_OK, student);
                finish();
                break;
            case R.id.drive:
                career_name = secondary_2.getText().toString();
                Intent drive = new Intent();
                drive.putExtra("career_name", career_name);
                setResult(RESULT_OK, drive);
                finish();
                break;
            case R.id.doctor_sur:
                career_name = secondary_3.getText().toString();
                Intent doctor_sur = new Intent();
                doctor_sur.putExtra("career_name", career_name);
                setResult(RESULT_OK, doctor_sur);
                finish();
                break;
            case R.id.PE_teacher:
                career_name = secondary_4.getText().toString();
                Intent PE_teacher = new Intent();
                PE_teacher.putExtra("career_name", career_name);
                setResult(RESULT_OK, PE_teacher);
                finish();
                break;
            case R.id.work_normal:
                career_name = secondary_5.getText().toString();
                Intent work_normal = new Intent();
                work_normal.putExtra("career_name", career_name);
                setResult(RESULT_OK, work_normal);
                finish();
                break;

            case R.id.work_struct:
                career_name = heavy_1.getText().toString();
                Intent work_struct = new Intent();
                work_struct.putExtra("career_name", career_name);
                setResult(RESULT_OK, work_struct);
                finish();
                break;
            case R.id.trans:
                career_name = heavy_2.getText().toString();
                Intent trans = new Intent();
                trans.putExtra("career_name", career_name);
                setResult(RESULT_OK, trans);
                finish();
                break;
            case R.id.smelt:
                career_name = heavy_3.getText().toString();
                Intent smelt = new Intent();
                smelt.putExtra("career_name", career_name);
                setResult(RESULT_OK, smelt);
                finish();
                break;
            case R.id.work_heavy:
                career_name = heavy_4.getText().toString();
                Intent work_heavy = new Intent();
                work_heavy.putExtra("career_name", career_name);
                setResult(RESULT_OK, work_heavy);
                finish();
                break;
            case R.id.sports:
                career_name = heavy_5.getText().toString();
                Intent sports = new Intent();
                sports.putExtra("career_name", career_name);
                setResult(RESULT_OK, sports);
                finish();
                break;
            case R.id.dancer:
                career_name = heavy_6.getText().toString();
                Intent dancer = new Intent();
                dancer.putExtra("career_name", career_name);
                setResult(RESULT_OK, dancer);
                finish();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("career_name", "未设置");
        setResult(RESULT_OK, intent);
        finish();
    }
}
