package common.analysis;

import common.pojo.AuthorData;
import common.pojo.InquireInfoData;
import common.system.AppContext;
import common.system.FileOperation;
import common.system.StringProcess;
import common.system.Systemconfig;
import org.apache.hadoop.util.hash.Hash;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Author {


    @Test
    public void test(){


        AppContext.initial();
        String[]names =FileOperation.read("src/main/resources/baseData/author").split("\n");


        Set<String> set1 = new HashSet<String>();


        for(String name:names){
            if(name.startsWith("#"))continue;
            name = name.trim();
            set1.add(name);
        }

        names =FileOperation.read("src/main/resources/baseData/author2").split("\n");

        for(String name:names){
            name=name.trim();
            if(name.startsWith("#"))continue;
            if(set1.contains(name))continue;
            System.out.println(name);
            AuthorData authorData = zhNameStr2Author(name);

            InquireInfoData inquireInfoData = new InquireInfoData();
            inquireInfoData.setTableName("author");
            inquireInfoData.setAuthorData(authorData);

            System.out.println(authorData.getName());
            //Systemconfig.authorService.saveMerge(inquireInfoData);

        }

    }

    public AuthorData zhNameStr2Author(String name){
        AuthorData author = new AuthorData();

        String firstName = name.substring(1);
        String lastName = name.substring(0,1);

        int firstNamelen= firstName.length();
        String abbFirstName="";
        for(int i=0;i<firstNamelen;++i){
            abbFirstName+=StringProcess.toHanyuPinyin(
                    firstName.substring(i,i+1)).substring(0,1).toUpperCase();
        }
        author.setZhName(name);
        author.setZhFirstName(firstName);
        author.setZhLastName(lastName);


        firstName = StringProcess.toHanyuPinyin(firstName);
        lastName = StringProcess.toHanyuPinyin(lastName);

        firstName = StringProcess.upCaseFirstLetter(firstName);
        lastName = StringProcess.upCaseFirstLetter(lastName);
        author.setEnFirstName(firstName);
        author.setEnLastName(lastName);
        author.setAbbName(abbFirstName+","+lastName);
        author.setEnName(firstName+","+lastName);
        author.setName(firstName+","+lastName);
        author.setEnFirstNameShort(abbFirstName);

        return author;
    }

    @Test
    public void test2(){

        String[]names =FileOperation.read("src/main/resources/baseData/author_fz").split("\n");
        Set<String> fzNameSet = new HashSet<String>();
        for(String name:names){
            if(name.startsWith("#"))continue;
            name = name.trim();
            AuthorData author =zhNameStr2Author(name);
            fzNameSet.add(name);
            fzNameSet.add(author.getEnName());
            fzNameSet.add(author.getAbbName());
        }

        names =FileOperation.read("src/main/resources/baseData/author_qd").split("\n");
        Set<String> qdNameSet = new HashSet<String>();
        for(String name:names){
            if(name.startsWith("#"))continue;
            name = name.trim();
            AuthorData author =zhNameStr2Author(name);
            qdNameSet.add(name);
            qdNameSet.add(author.getEnName());
            qdNameSet.add(author.getAbbName());
        }
    }


    @Test
    public void testPinyin(){

        String pinyin = StringProcess.toHanyuPinyin("Â·ÂÊÂÌÂ¿ÂÅÕ½ÂÅ°Ü");
        System.out.println(pinyin);
        System.out.println(pinyin);


    }


    @Test
    public void test10(){


        String str = "Du,Y(Du,Yang)[1];Liang,XL(Liang,Xiaolong)[2];Li,Y(Li,Yuan)[1,3];Sun,T(Sun,Ting)[1,3];Jin,ZY(Jin,Zhengyu)[3];Xue,HD(Xue,Huadan)[3];Tian,J(Tian,Jie)[1]";

        System.out.println(str.replaceAll("\\[.*?\\]",""));



    }
    @Test
    public void test1011(){



        System.out.println(StringProcess.isChinese("¿µÃÏÕä"));



    }
    @Test
    public void test12(){


        String str = "Du,Y(Du,Yang)[1];Liang,XL(Liang,Xiaolong)[2];Li,Y(Li,Yuan)[1,3];Sun,T(Sun,Ting)[1,3];Jin,ZY(Jin,Zhengyu)[3];Xue,HD(Xue,Huadan)[3];Tian,J(Tian,Jie)[1]";

        List<Integer> list= getBracketNums("Du,Y(Du,Yang)[100,2][2][333][333]");

        for(int i:list) {
            System.out.println(i);
        }



    }

    @Test
    public void test112(){


        String str = "Du,Y(Du,Yang)[1];Liang,XL(Liang,Xiaolong)[2];Li,Y(Li,Yuan)[1,3];Sun,T(Sun,Ting)[1,3];Jin,ZY(Jin,Zhengyu)[3];Xue,HD(Xue,Huadan)[3];Tian,J(Tian,Jie)[1]";

        if(str.contains("(")){
            System.out.println("true");

            System.out.println();
        }else{
            System.out.println("false");
        }



    }



    @Test
    public void test132(){


        String str = "Du,Y(Du,Yang)[1,232,3]";
        if(str.contains("(")){
            String name = str.replaceAll("\\[.*?\\]","");
            System.out.println(name);
            String abbname = name.replaceAll("\\(.*?\\)","");
            name = name.replaceFirst(abbname,"").replaceAll("[\\(|\\)]","");

            System.out.println(abbname);
            System.out.println(name);
        }else{
            System.out.println("false");
        }



    }

    @Test
    public void test1312(){


        String str = "[ 1 ] Chinese Acad Sci, 234 22 Inst Automat, State Key Lab Management & Control Complex Syst, Beijing, Peoples R China;";

        String str1 = StringProcess.regex2StrSplitByMark(str,"\\[.*?\\]","");
        String str2 = StringProcess.regex2StrSplitByMark(str1,"\\d","");

        System.out.println(str1);
        System.out.println(str2);
        System.out.println(Integer.parseInt(null));

    }

    @Test
    public void test13122(){





    }
    public List<Integer> getBracketNums(String str){
        if(str.contains("[")&&str.contains("]")){
            List<Integer> intList = new ArrayList();
            str=str.replace("\\s+","");
            List<String> strList = StringProcess.regex2List(str,"\\d+");
            for(String s :strList){
                s=StringProcess.regex2List(s,"\\d+").get(0);
                int num = Integer.parseInt(s);
                intList.add(num);
            }
            return intList;
        }
        return null;
    }




    @Test
    public void test222(){




        AppContext.initial();
        String[]names =FileOperation.read("src/main/resources/baseData/author").split("\n");

        names =FileOperation.read("src/main/resources/baseData/author2").split("\n");

        for(String name:names){


            AuthorData authorData = zhNameStr2Author(name);

            System.out.println(authorData.getName());
            //Systemconfig.authorService.saveMerge(inquireInfoData);

        }

    }
}
