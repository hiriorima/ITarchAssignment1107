package com.example.itarch.itarchassignment;

/**
 * Created by b1013043 on 2016/11/03.
 */

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.app.Activity;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.widget.TextView;
import android.content.Intent;

public class ItemDetailActivity extends Activity {
    private TextView mTitle;
    private TextView mDescr;

    private IServiceMethod mService;

    private ServiceConnection mServiceConnection =
            new ServiceConnection() { //【2】

                @Override
                public void onServiceDisconnected(ComponentName name) {

                    mService = null;
                }

                @Override
                public void onServiceConnected(
                        ComponentName name, IBinder service) { //【3】
                    mService = IServiceMethod.Stub.asInterface(service); //【4】
                    try {
                        mService.CallServiceMethod(mDescr.getText().toString());
                    }catch (RemoteException e){
                        Log.d("hoge","hoge");
                    }
                }
            };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_detail);

        Intent intent = getIntent();

        String title = intent.getStringExtra("TITLE");
        mTitle = (TextView) findViewById(R.id.item_detail_title);
        mTitle.setText(title);
        String descr = intent.getStringExtra("DESCRIPTION");
        mDescr = (TextView) findViewById(R.id.item_detail_descr);
        mDescr.setText(descr);

        Intent tempIntent = new Intent(IServiceMethod.class.getName());
        tempIntent.setPackage("com.example.itarch.itarchassignment");
        bindService(tempIntent, //【5】
                mServiceConnection, BIND_AUTO_CREATE); //【6】

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mService != null) {
            unbindService(mServiceConnection); //【7】
        }
    }
}
