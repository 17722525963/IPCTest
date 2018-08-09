package com.zsrun.ipctest;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.zsrun.aidlservice.IMyAidlInterface;

public class MainActivity extends AppCompatActivity {

    public final String DICTIONARY_SINGLE_WORD_URI = "content://com.zsrun.test.DiationaryContentProvider/single";
    public final String DICTIONARY_PREFIX_WORD_URI = "content://com.zsrun.test.DiationaryContentProvider/prefix";

    private String BROADCAST_ACTION = "com.zsrun.broadcast.ACTION";

    private IMyAidlInterface mStub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * 访问其他程序的Activity
         */
        otherActivity();
        openCall();//通过启动系统通话界面 系统级Activity
        contentprovider_dictionary();
        contentprovidercontacts();
        broadcast();
        aidl();
    }

    private void aidl() {
        findViewById(R.id.aidl)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setAction("com.zsrun.aidlservice.MyService");
                        //android 5.0以后直设置action不能启动相应的服务，需要设置packageName或者Component。
                        intent.setPackage("com.zsrun.aidlservice"); //packageName 需要和服务端的一致. 即指定的ID
                        bindService(intent, serviceConnection, BIND_AUTO_CREATE);
                    }
                });
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //调用asInterface()方法获得IMyAidlInterface实例
            mStub = IMyAidlInterface.Stub.asInterface(service);
            if (mStub == null) {
                Log.i("???", "onServiceConnected: The Stub is null");
            } else {
                try {
                    Toast.makeText(MainActivity.this, mStub.getValue(), Toast.LENGTH_SHORT).show();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };


    private void broadcast() {
        findViewById(R.id.broadcast)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new AlertDialog.Builder(MainActivity.this).setMessage("让开，我要发送数据啦！")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        //  通过Intent类的构造方法指定广播的ID
                                        Intent intent = new Intent(BROADCAST_ACTION);
                                        //  将要广播的数据添加到Intent对象中
                                        intent.putExtra("text", "我发送的数据，你收到了吗？");
                                        //  发送广播
                                        sendBroadcast(intent);
                                    }
                                }).show();
                    }
                });
    }

    private void contentprovidercontacts() {
        findViewById(R.id.contentprovider_contacts)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ContentResolver contentResolver = MainActivity.this.getContentResolver();
                        Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
                        if (!cursor.moveToFirst()) {
                            Toast.makeText(MainActivity.this, "获取内容为空", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, "获取了好多数据~~" + cursor.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void contentprovider_dictionary() {
        findViewById(R.id.contentprovider_dictionary)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Uri uri = Uri.parse(DICTIONARY_SINGLE_WORD_URI);
                        //  通过ContentProvider查询单词，并返回Cursor对象，然后的操作就和直接从数据中获得
                        //  Cursor对象后的操作是一样的了
                        Cursor cursor = getContentResolver().query(uri, null, "english=?",
                                new String[]{""}, null);
                        String result = "未找到该单词.";
                        if (cursor.getCount() > 0) {
                            cursor.moveToFirst();
                            result = cursor.getString(cursor.getColumnIndex("chinese"));
                        }
                        new AlertDialog.Builder(MainActivity.this).setTitle("查询结果").setMessage(result)
                                .setPositiveButton("关闭", null).show();

//                        if ("".equals(s.toString()))
//                            return;
//                        Uri uri = Uri.parse(DICTIONARY_PREFIX_WORD_URI + "/" + s.toString());
//                        //  从ContentProvider中获得以某个字符串开头的所有单词的Cursor对象
//                        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
//                        DictionaryAdapter dictionaryAdapter = new DictionaryAdapter(this,
//                                cursor, true);
//                        actvWord.setAdapter(dictionaryAdapter);
                    }
                });
    }

    private void openCall() {
        findViewById(R.id.call_phone)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_CALL);
                        intent.setData(Uri.parse("tel:123456789"));
                        startActivity(intent);
                    }
                });
    }

    private void otherActivity() {
        findViewById(R.id.other_activity)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setAction("com.zsrun.test.TESTACTION");
                        intent.setData(Uri.parse("test://调用其他APP Activity"));
                        intent.putExtra("value", "我就是测试调用一下你的Activity");
                        startActivity(intent);
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(serviceConnection);
    }
}
