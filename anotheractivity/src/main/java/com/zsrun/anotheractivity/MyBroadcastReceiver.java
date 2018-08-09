package com.zsrun.anotheractivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

/**
 * 2018/8/9 15:49
 *
 * @author zsr
 * @version 1.0
 */

public class MyBroadcastReceiver extends BroadcastReceiver {

    private String BROADCAST_ACTION = "com.zsrun.broadcast.ACTION";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (BROADCAST_ACTION.equals(intent.getAction())) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                Toast.makeText(context, "成功接收到广播了~" + bundle.getString("text"), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
