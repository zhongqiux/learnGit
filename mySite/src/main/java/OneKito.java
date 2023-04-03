import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet("/oneWord")
public class OneKito extends HttpServlet {
    private void copy(InputStream source, PrintWriter target) throws IOException {
        byte[] buf = new byte[1024];
        int length;
        while ((length = source.read(buf)) > 0) {
            String tem = new String(buf,0,length,"UTF-8");
            tem = convertUnicodeToCh(tem);
            target.write(tem);
        }
    }
    private static String convertUnicodeToCh(String str) {
        Pattern pattern = Pattern.compile("(\\\\u(\\w{4}))");
        Matcher matcher = pattern.matcher(str);

        // 迭代，将str中的所有unicode转换为正常字符
        while (matcher.find()) {
            String unicodeFull = matcher.group(1); // 匹配出的每个字的unicode，比如\u67e5
            String unicodeNum = matcher.group(2); // 匹配出每个字的数字，比如\u67e5，会匹配出67e5

            // 将匹配出的数字按照16进制转换为10进制，转换为char类型，就是对应的正常字符了
            char singleChar = (char) Integer.parseInt(unicodeNum, 16);

            // 替换原始字符串中的unicode码
            str = str.replace(unicodeFull, singleChar + "");
        }
        return str;
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpURLConnection con = null;

        BufferedReader buffer = null;
        StringBuffer resultBuffer = null;


        try {
            URL url = new URL("https://api.wrdan.com/hitokoto");
            //得到连接对象
            con = (HttpURLConnection) url.openConnection();
            //设置请求类型
            con.setRequestMethod("GET");
            //设置请求需要返回的数据类型和字符集类型
            con.setRequestProperty("Content-Type", "application/json;charset=utf-8");
            //允许写出
            con.setDoOutput(true);
            //允许读入
            con.setDoInput(true);
            //不使用缓存
            con.setUseCaches(false);
            //得到响应码
            int responseCode = con.getResponseCode();

            if(responseCode == HttpURLConnection.HTTP_OK){
                //得到响应流
                InputStream inputStream = con.getInputStream();

                resp.setContentType("application/json;charset=utf-8");
//                resp.setCharacterEncoding("utf-8");
                PrintWriter writer = resp.getWriter();
                copy(inputStream,writer);
            }

        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }
}
