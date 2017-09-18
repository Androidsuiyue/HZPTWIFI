package qzhenghao.cn.hzptwifi;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.provider.Telephony;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by 89399 on 2017/9/17.
 */

public class GetSMS extends ContentObserver {


    private Activity activity;
    private String body;
    private EditText edp;

    public GetSMS(Handler handler, Activity activity, EditText ed_pa) {
        super(handler);
        this.activity=activity;
        edp =ed_pa;
    }

    @Override
    public void onChange(boolean selfChange) {
        System.out.println("短信数据库发生变化了。");
        String s = querSMS();
        System.out.println(s);
        String dynamicPassword = getDynamicPassword(s);
        System.out.println(dynamicPassword);
        edp.setText(dynamicPassword);


        super.onChange(selfChange);
    }


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            querSMS();

        }
    };

















    public String querSMS() {
        //得到访问者ContentResolver
        ContentResolver cr = activity.getContentResolver();
        //定义一个接收短信的集合
        Message message = new Message();
        Uri uri = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            uri = Uri.parse(String.valueOf(Telephony.Sms.CONTENT_URI));
        }

        Cursor cursor = cr.query(uri, null, null, null, null);

        while (cursor.moveToNext()) {

            body = cursor.getString(cursor.getColumnIndex(Telephony.Sms.BODY));

        }

        return body;

    }

    /**
     * 从字符串中截取连续4位数字
     * 用于从短信中获取动态密码
     *
     * @param str 短信内容
     * @return 截取得到的4位动态密码
     */
    public String getDynamicPassword(String str) {
        Pattern continuousNumberPattern = Pattern.compile("[0-9\\.]+");
        Matcher m = continuousNumberPattern.matcher(str);
        String dynamicPassword = "";
        while (m.find()) {
            if (m.group().length() == 6) {
                dynamicPassword = m.group();
            }
        }

        return dynamicPassword;
    }


}
