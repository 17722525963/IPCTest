package com.zsrun.aidlservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

/**
 * 2018/8/9 16:28
 *
 * @author zsr
 * @version 1.0
 */

public class MyService extends Service {

    public class MyServiceImpl extends IMyAidlInterface.Stub {

        @Override
        public String getValue() throws RemoteException {
            return "这就是从AIDL服务器传输的数据~";
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MyServiceImpl();
    }
}
