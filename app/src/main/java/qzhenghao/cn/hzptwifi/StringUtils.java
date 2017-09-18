package qzhenghao.cn.hzptwifi;

/**
 * Created by 89399 on 2017/9/16.
 */
public class StringUtils {

    public static String infoString(String data) {
        String result;
        int str = data.indexOf("msga");
        int i  = data.indexOf(";",str);

        if (str != -1) {
            String string = data.substring(str, i);
            int i1 = string.indexOf("'");
            int i2 = string.indexOf("'", i1 + 1);
            String s = string.substring(i1 + 1, i2);

            if (s.equals(""))

            {
                result = "账号或密码不对，请重新输入";
            } else {
                result = s;
            }

        } else {
            result="您已经成功登录";
        }


        return result;
    }
    public static boolean retString(String result){
        int aTrue = result.indexOf("true");


        if (aTrue!=-1) {
            return true;
        }
        return false;

    }

}
