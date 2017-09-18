package qzhenghao.cn.hzptwifi;

import org.apache.http.ParseException;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }
    @Test
    public void test1() throws IOException {
        try {
            String send = HzptUtils.send("http://10.50.50.2:801/eportal/controller/Action.php", "utf-8","17194110228");
            System.out.println(send);
            boolean b = StringUtils.retString(send);
            System.out.println(b);
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}