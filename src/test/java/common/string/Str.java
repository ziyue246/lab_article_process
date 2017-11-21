package common.string;

import common.system.StringProcess;
import org.junit.Test;

import java.util.List;

public class Str {

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
        String str= "[1]ani[2]2cs, Pe[4444]king University, Beijing, 100871, China";

        List<String> list = (StringProcess.regex2List(str,"\\[\\d+\\]"));


        for(String s:list){
            System.out.println(StringProcess.regex2List(s,"\\d+").get(0));
        }


        System.out.println("Ai, Yunfeng".replaceAll("\\s*",""));
    }
}
