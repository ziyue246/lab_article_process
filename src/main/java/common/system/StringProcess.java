package common.system;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringProcess {


    public static String regex2StrSplitByMark(String content,String pattern,String mark){
        //String line = "http://www11.drugfuture.com/cnpat/SecurePdf.aspx";
        //  String pattern = "http://www(\\d*).drugfuture.com/cnpat/SecurePdf.aspx";
        // 创建 Pattern 对象
        Pattern r = Pattern.compile(pattern);
        // 现在创建 matcher 对象
        Matcher m = r.matcher(content);
        StringBuffer sb = new StringBuffer();
        while (m.find( )) {
            if(sb.length()>0){
                sb.append(mark);
            }
            sb.append(m.group());
        }
        return sb.toString();
    }

    public static List<String> regex2List(String content, String pattern){
        //String line = "http://www11.drugfuture.com/cnpat/SecurePdf.aspx";
        //  String pattern = "http://www(\\d*).drugfuture.com/cnpat/SecurePdf.aspx";
        // 创建 Pattern 对象
        Pattern r = Pattern.compile(pattern);
        // 现在创建 matcher 对象
        Matcher m = r.matcher(content);
        List<String> list = new ArrayList<String>();
        while (m.find( )) {
            list.add(m.group());
        }
        return list;
    }
}
