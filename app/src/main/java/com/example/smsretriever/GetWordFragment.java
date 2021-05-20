package com.example.smsretriever;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class GetWordFragment extends Fragment {

    EditText etWord;
    Button btnRetrieveWord;
    TextView tvWord;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_get_word,container,false);

        etWord = view.findViewById(R.id.etWord);
        btnRetrieveWord = view.findViewById(R.id.btnRetrieveWord);
        tvWord = view.findViewById(R.id.tvWord);

        btnRetrieveWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String data = tvWord.getText().toString() + "\n" + "New Data";
                tvWord.setText(data);
            }
        });
        return view;
    }
}