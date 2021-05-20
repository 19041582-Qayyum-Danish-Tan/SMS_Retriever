package com.example.smsretriever;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class GetNumFragment extends Fragment {

    EditText etNum;
    Button btnRetrieveNum;
    TextView tvNum;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_get_num,container,false);

        etNum = view.findViewById(R.id.etNum);
        btnRetrieveNum = view.findViewById(R.id.btnRetrieveNum);
        tvNum = view.findViewById(R.id.tvNum);

        btnRetrieveNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String data = tvNum.getText().toString() + "\n" + "New Data";
                tvNum.setText(data);
            }
        });
        return view;
    }
}