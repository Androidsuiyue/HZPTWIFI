//使用httpclient请求框架
package qzhenghao.cn.hzptwifi;

import org.apache.http.ParseException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public  class HzptUtils{



        public static String send(String url, String encoding,String DDDD) throws ParseException, IOException {
                String body = "";

                //创建httpclient对象 URL httpPost;
                URL url1 = new URL(url);

//        CloseableHttpClient client = HttpClients.createDefault();

                //创建post方式请求对象
                HttpURLConnection urlConnection = (HttpURLConnection) url1.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setConnectTimeout(5000);
                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);
                //装填参数
                HashMap<String, String> nvps = new HashMap<String, String>();


                //这里传入手机号码
                nvps.put("telephone", DDDD);
                nvps.put("regURL", "172.18.180.128:8080");
                nvps.put("machineno", "DR15NM8800380");
                //注意这里的mac地址需要自己动态获取本机的mac地址.不能写死
                nvps.put("mac", "f0796018002e");
                //设置参数到请求对象中
                StringBuilder sb = new StringBuilder();//把要提交的 数据类型定义为 username=哈哈&password=psw的格式
                //通过Map的遍历的到
                for (Map.Entry<String, String> en : nvps.entrySet()) {
                        sb.append(en.getKey())
                                .append("=")
                                .append(URLEncoder.encode(en.getValue(), "utf-8"))//这里的编码别忘记了。
                                .append("&");
                }
                sb.deleteCharAt(sb.length() - 1);

                System.out.println("请求地址：" + url);
                System.out.println("请求参数：" + nvps.toString());

                //设置header信息
                //指定报文头【Content-type】、【User-Agent】
                urlConnection.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
                urlConnection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
//
//        //执行请求操作，并拿到结果（同步阻塞）
//        CloseableHttpResponse response = urlConnection.execute(httpPost);
//        //获取结果实体
//        HttpEntity entity = response.getEntity();
//        if (entity != null) {
//        //按指定编码转换结果实体为String类型
//        body = EntityUtils.toString(entity, encoding);
//        }
//        EntityUtils.consume(entity);
//        //释放链接
//        response.close();
//        return body;

                byte[] data = sb.toString().getBytes("utf-8");
                OutputStream outputStream = urlConnection.getOutputStream();
                outputStream.write(data);
                outputStream.close();

                InputStream in = null;
                if (urlConnection.getResponseCode() == 200) {
                        in = urlConnection.getInputStream();
                        ByteArrayOutputStream bos = new ByteArrayOutputStream();
                        byte[] arr = new byte[1024];
                        int len = 0;
                        while ((len = in.read(arr)) != -1) {
                                bos.write(arr, 0, len);
                        }

                        byte[] b = bos.toByteArray();
                        body = new String(b, "utf-8");

                }


                //关闭流
                in.close();
                outputStream.close();
                return body;

        }
}