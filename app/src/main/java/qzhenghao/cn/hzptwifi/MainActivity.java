package qzhenghao.cn.hzptwifi;

import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btn_login;
    private CheckBox cb_autoconn;
    private EditText et_password;
    private EditText et_username;
    private String URL_SMS="http://10.50.50.2:801/eportal/controller/Action.php";
    private String ENCODING_UTF_8="utf-8";
    private String ENCODING_GBK="gbk";
    private String URL_LOGIN="http://10.50.50.2/a70.htm";
    private String username="";
    private String password="";
    private GetSMS getSMS;
    private Button btn;
    private Thread thread;
    boolean flag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_33);

        btn_login = (Button) findViewById(R.id.btn_login);
        cb_autoconn = (CheckBox) findViewById(R.id.cb_logina);
        et_username = (EditText) findViewById(R.id.et_username);
        et_password = (EditText) findViewById(R.id.et_passw);
        btn = (Button) findViewById(R.id.button3);
        btn.setOnClickListener(this);
        getSMS = new GetSMS(new Handler(),this,et_password);
         //注册短信内容监听
        this.getContentResolver().registerContentObserver(Uri.parse("content://sms/"), true, getSMS);


        btn_login.setOnClickListener(this);
        init();




            }






    @Override
    public void onClick(View view) {
        switch (view.getId()) {


            case R.id.button3:
                username = et_username.getText().toString();
                if (!"".equals(username)) {


                    try {

                        boolean b = networkRequest();


                        if (b) {
                            Toast.makeText(MainActivity.this, "验证码已发送", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, "请再来一遍", Toast.LENGTH_SHORT).show();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                break;
            case R.id.btn_login:
                final String[] info = new String[1];
                username = et_username.getText().toString();
                password = et_password.getText().toString();
                System.out.println(username+"-------"+password);
               new Thread(){
                   @Override
                   public void run() {
                       super.run();
                       String s = HttpReqestUtils.sendPost(URL_LOGIN, "DDDDD=" + username + "&" +
                               "upass=" + password + "&" +
                               "R1=0&" +
                               "R2=&" +
                               "R6=0&" + "para:00&" +
                               "MKKey:123456");
                       String s1 = StringUtils.infoString(s);
                       System.out.println(s);
                      info[0] =s1;
                   }

               }.start();
                System.out.println(info[0]);
                break;
        }

    }

        @Override
        protected void onDestroy() {
            super.onDestroy();
            this.getContentResolver().unregisterContentObserver(getSMS);

        }

    public boolean networkRequest() throws IOException {

        final boolean[] s = new boolean[1];
        new Thread() {
            @Override
            public void run() {
                super.run();
                String send1 = null;
                try {
                    send1 = HzptUtils.send(URL_SMS, "utf-8", username);
                } catch (IOException e) {
                    e.printStackTrace();
                }

//                s[0] = StringUtils.retString(send1);
            }


        }.start();
        return s[0];
    }
    private void getSMSDataPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                requestPermissions(new String[]{Manifest.permission.READ_SMS}, 2);
            return;
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 2) {
            Toast.makeText(MainActivity.this, "必须需要读取短信权限!!!", Toast.LENGTH_SHORT).show();
            getSMSDataPermission();
            final int REQUEST_CODE_ASK_PERMISSIONS = 123;
            ActivityCompat.requestPermissions(this, new String[]{"android.permission.READ_SMS"}, REQUEST_CODE_ASK_PERMISSIONS);
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    private void init() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_NETWORK_STATE}, 3);
            }
            return;
        }

        getSMSDataPermission();




    }



}
