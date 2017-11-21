package common.string;

import common.system.StringProcess;
import org.junit.Test;

import java.util.*;

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


    @Test
    public void test5(){
        String str= "ƒ„≤¬Œ“ «À≠À≠À≠";
        String str2= "Õı∑…‘æ";

        System.out.println(StringProcess.toHanyuPinyin(str2));
    }
    @Test
    public void test51(){
        Set<String> set = new HashSet<String>();
        set.add("111");
        set.add("222");
        set.add("333");
        System.out.println(set);
        for(Iterator<String> it = set.iterator(); it.hasNext(); ){

            String s = it.next();
            System.out.println(s);
            if(s.equals("222")){
                it.remove();
            }

        }

        System.out.println(set);
    }


    @Test
    public void test511(){
        List<String> set = new ArrayList<String>();
        set.add("111");
        set.add("222");
        set.add("333");
        System.out.println(set);
        for(Iterator<String> it = set.iterator(); it.hasNext(); ){

            String s = it.next();
            System.out.println(s);
            if(s.equals("222")){
                it.remove();
            }

        }

        System.out.println(set);
    }
}
