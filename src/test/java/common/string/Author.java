package common.string;

import common.system.StringProcess;
import org.junit.Test;

import java.util.List;

public class Author {

    @Test
    public void process(){
        String ss= "National Laboratory for Turbulence and Complex Systems, Department of Mechanics, Peking University, Beijing, 100871, China";

        String mark=",";

        String []inss= ss.split(mark);
        for(String ins:inss){
            if(ins.contains("University")){
                System.out.println(ins.trim());
            }
        }
    }
    @Test
    public void test(){
        String str= "Zheng,EH(Zheng,Enhao)[1,2];";

        List<String> list = (StringProcess.regex2List(str,".*"));


        for(String s:list){
            System.out.println(StringProcess.regex2List(s,"\\d+").get(0));
        }
    }

    @Test
    public void test01(){
        String str= "Zheng,EH(Zheng,Enhao)[1,2];";

        System.out.println(StringProcess.regex2StrSplitByMark(str,"\\(.*\\)",""));
        System.out.println(StringProcess.regex2StrSplitByMark(
                str,"\\[.*\\]","").replaceAll(",","]["));
    }
}
