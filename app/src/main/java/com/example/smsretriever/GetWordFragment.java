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

                int permissionCheck = PermissionChecker.checkSelfPermission(getActivity(), Manifest.permission.READ_SMS);
                if (permissionCheck != PermissionChecker.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_SMS}, 0);
                    return;
                }

                Uri uri = Uri.parse("content://sms");
                String[] reqCols = new String[]{"date", "address", "body", "type"};

                ContentResolver cr = getActivity().getContentResolver();

                String filter = "body LIKE ?";
                String[] tempArgs = tvWord.getText().toString().split(" ");
                String[] filterArgs = new String[tempArgs.length];
                for (int i = 1; i < tempArgs.length; i++) {
                    filter += " AND body LIKE ?";
                    filterArgs[i] = '%' + tempArgs[i] + '%';
                }

                Cursor cursor = cr.query(uri, reqCols, filter ,filterArgs, null);
                String smsBody = "";
                if (cursor.moveToFirst()){
                    do {
                        long dateInMillis = cursor.getLong(0);
                        String date = (String) DateFormat.format("dd MMM yyyy h:mm:ss aa", dateInMillis);
                        String address = cursor.getString(1);
                        String body = cursor.getString(2);
                        String type = cursor.getString(3);
                        if (type.equalsIgnoreCase("1")) {
                            type = "Inbox";
                        } else {
                            type = "Sent";
                        }
                        smsBody += type + " " + address + "\n at "+ date + "\n\"" + body + "\"\n\n";
                    } while (cursor.moveToNext());
                }
                tvWord.setText(smsBody);
            }
        });
        return view;
    }
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults){
        switch(requestCode){
            case 0: {
                if (grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    btnRetrieveWord.performClick();
                } else {
                    Toast.makeText(getActivity(), "Permission not granted", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}