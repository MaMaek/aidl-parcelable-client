package com.example.client;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.aidl.IMyAidlInterface;
import com.example.aidl.UserInfo;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    IMyAidlInterface iMyAidlInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();

    }

    private void initViews() {
        Button btn1 = (Button) findViewById(R.id.btn1);
        btn1.setOnClickListener(this);
        Button btn2 = (Button) findViewById(R.id.btn2);
        btn2.setOnClickListener(this);
        Button btn3 = (Button) findViewById(R.id.btn3);
        btn3.setOnClickListener(this);
        Button btn4 = (Button) findViewById(R.id.btn4);
        btn4.setOnClickListener(this);
        Button btn5 = (Button) findViewById(R.id.btn5);
        btn5.setOnClickListener(this);
        Button btn6 = (Button) findViewById(R.id.btn6);
        btn6.setOnClickListener(this);
    }

        /**
     * 基础类型相加
     * @return
     * @throws RemoteException
     */
    public  int  sum() {
        if(iMyAidlInterface!=null)
        {
            int result=0;
            try {
                result = iMyAidlInterface.add(7, 8);
                Toast.makeText(getApplicationContext(), "基础类型相加结果:"+result, Toast.LENGTH_SHORT).show();
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return result;
        }
        return 0;
    }
    /**
     * in型传值到服务端
     */
    public void setaList()
    {
        if(iMyAidlInterface!=null)
        {
            try {
                iMyAidlInterface.setaList(new String[]{"战国剑"});
                Toast.makeText(getApplicationContext(), "传值'战国剑'到服务端", Toast.LENGTH_SHORT).show();
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
    /**
     * out型取服务端返回值
     */
    public void getaList()
    {
        if(iMyAidlInterface!=null)
        {
            String[] list =new String[1];
            try {
                iMyAidlInterface.getaList(list);
                Toast.makeText(getApplicationContext(), "服务端返回内容："+list[0], Toast.LENGTH_SHORT).show();
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
    /**
     * Parcelable类的传入
     */
    public void ParcelableUse()
    {
        if(iMyAidlInterface==null)
            return;
        UserInfo userInfo=new UserInfo();
        userInfo.setName("战国剑");
        userInfo.setAdress("中国");
        userInfo.setAge(18);
        try {
            String resultString=iMyAidlInterface.getUserInfo(userInfo);
            Toast.makeText(getApplicationContext(), "服务端返回内容："+resultString, Toast.LENGTH_SHORT).show();
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * inout类型修饰的使用
     */
    public void inoutUse()
    {
        if(iMyAidlInterface==null)
            return;
        try {
            String[] inStrings={"inout中in的传入"};
            iMyAidlInterface.gettList(inStrings);
            Toast.makeText(getApplicationContext(), "inout服务端返回内容："+inStrings[0], Toast.LENGTH_SHORT).show();

        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.btn1:
                Intent intent = new Intent();
                intent.setComponent(new ComponentName("com.example.aidl", "com.example.aidl.MyService"));
                bindService(intent, conn, BIND_AUTO_CREATE);
                break;
            case R.id.btn2:
                sum();
                break;
            case R.id.btn3:
                ParcelableUse();
                break;
            case R.id.btn4:
                setaList();
                break;
            case R.id.btn5:
                getaList();
                break;
            case R.id.btn6:
                inoutUse();
                break;
            default:
                break;
        }
    }

    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceDisconnected(ComponentName name) {
            // TODO Auto-generated method stub
            iMyAidlInterface=null;
            Toast.makeText(MainActivity.this, "连接断开", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            // TODO Auto-generated method stub
            iMyAidlInterface=IMyAidlInterface.Stub.asInterface(service);
            Toast.makeText(MainActivity.this, "连接成功", Toast.LENGTH_SHORT).show();
        }

    };




    @Override
    protected void onDestroy() {
        super.onDestroy();
        //解绑服务，回收资源
        unbindService(conn);
    }


}
