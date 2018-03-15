package common.string;

import common.analysis.EnAnalysis;
import common.pojo.EnumType;
import common.system.AppContext;
import common.system.OperationExcel;
import common.system.StringProcess;
import common.system.Systemconfig;
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

    @Test
    public void test513(){

        int a=1;
        int b=2;
        int c=3;
        System.out.println(""+a+b+c);
    }

    @Test
    public void test5131(){

        String insti="[ 1 ] Chinese Acad Sci, " +
                "Inst Automat, Intelligent Med Res Ctr, " +
                "State Key Lab Management and Control Complex Syst, " +
                "Beijing 100190, Peoples R China";


        insti="[1] State Key Laboratory of Management and Control for Complex Systems, Institute of Automation, Chinese Academy of Sciences, Beijing; 100190, China";
        String result = "1";
        if((insti.toLowerCase().contains("institute of automation")||
                insti.toLowerCase().contains("casia")||
                insti.toLowerCase().contains("inst automat"))
                &&
                insti.contains("State Key")&&
                insti.contains("Complex")&&insti.contains("Management")) {
            result = "State Key Laboratory of Management and Control for Complex Systems";

        }

        System.out.println(result);
    }






    @Test
    public void test3515(){

        AppContext.initial();


        String astr="Improving the Critic Learning for Event-Based Nonlinear H°ﬁControl Design";
        String bstr="Improving the critic learning for event-based nonlinear h-infinity control design";


        double simi = EnAnalysis.getSimilarity(astr,bstr);


        System.out.println(simi);

    }



    @Test
    public void test35151(){


        String astr="AN ACCURATE PATH PLANNING ALGORITHM BASED ON TRIANGULAR MESHES IN ROBOTIC FIBRE PLACEMENT";
        String bstr="Local Discriminant Canonical Correlation Analysis for Supervised PolSAR Image Classification";

        String cstr="Local ABC Discriminant Canonical Correlation Analysis for Supervised PolSAR Image Classification";




        System.out.println(OperationExcel.dealEnTitle(astr));
        System.out.println(OperationExcel.dealEnTitle(bstr));
        System.out.println(OperationExcel.dealEnTitle(cstr));

    }


    @Test
    public void test351511(){


        String astr="Qiao, H (reprint author);Qiao, H (reprint author);Qiao, H (reprint author)";



        System.out.println(astr.split("\\(")[0].trim());

        System.out.println(astr.replace(("(reprint author)"),("")));


        System.out.println(astr.replace(("(reprint author)"),(""))
                .replaceAll(("\\s+"),("")).trim());

        System.out.println(astr.contains("reprint author"));

        System.out.println(astr.contains("(reprint author)"));


        System.out.println(EnumType.AuthorType.REPRINT.equals(test351511(EnumType.AuthorType.REPRINT)));


        System.out.println(EnumType.AuthorType.REPRINT.name());
        System.out.println(EnumType.AuthorType.REPRINT.getName());
        System.out.println(EnumType.AuthorType.REPRINT.getIndex());

        (new Systemconfig()).initial();

    }

    public EnumType.AuthorType test351511(EnumType.AuthorType authorType){
        return authorType;
    }

}
