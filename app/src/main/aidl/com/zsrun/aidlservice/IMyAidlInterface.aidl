// IMyAidlInterface.aidl
package com.zsrun.aidlservice;

// Declare any non-default types here with import statements

interface IMyAidlInterface {

    String getValue();//为 AIDL 服务的接口方法，调用AIDL服务的程序需要调用该方法

}
