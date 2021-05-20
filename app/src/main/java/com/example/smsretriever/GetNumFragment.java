package com.example.smsretriever;


import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.PermissionChecker;
import androidx.fragment.app.Fragment;

import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


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

                int permissionCheck = PermissionChecker.checkSelfPermission(getActivity(), Manifest.permission.READ_SMS);
                if (permissionCheck != PermissionChecker.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_SMS}, 0);
                    return;
                }

                Uri uri = Uri.parse("content://sms");
                String[] reqcols = new String[]{"date", "address", "body", "type"};

                ContentResolver cr = getActivity().getContentResolver();
                // filter
                String filter = "address LIKE ?";
                String[] filterArgs = {"%" + etNum.getText().toString() + "%"};

                Cursor cursor = cr.query(uri, reqcols, filter, filterArgs, null);
                String smsbody = "";

                if(cursor.moveToFirst()){
                    do{
                        Long dateInMillis = cursor.getLong(0);
                        String date = (String) DateFormat.format("dd MM yyyy h:mm:ss aa", dateInMillis);
                        String address = cursor.getString(1);
                        String body = cursor.getString(2);
                        String type = cursor.getString(3);
                        if(type.equalsIgnoreCase("1")){
                            type = "Inbox";
                        }else{
                            type = "Sent";
                        }
                        smsbody += type + " " + address + "\n at " + date + "\n\"" + body + "\"\n\n";
                    }while (cursor.moveToNext());
                }
                tvNum.setText(smsbody);
            }
        });
        return  view;
    }
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults){
        switch(requestCode){
            case 0: {
                if (grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    btnRetrieveNum.performClick();
                }
                else{
                    Toast.makeText(getActivity(), "Permission not granted", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}