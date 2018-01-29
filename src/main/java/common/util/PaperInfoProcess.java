package common.util;

import common.pojo.AuthorData;
import common.system.StringProcess;

/**
 * Created by ziyue on 2018/1/29.
 */
public class PaperInfoProcess {


    /**
     * chinese author name process to author
     * @param author
     */
    public static void zhAuthorProcess(AuthorData author){

        String name = author.getZhName();
        String firstName = name.substring(1);
        String lastName = name.substring(0,1);


        int firstNamelen= firstName.length();
        String abbFirstName="";
        for(int i=0;i<firstNamelen;++i){
            abbFirstName+= StringProcess.toHanyuPinyin(
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
        author.setEnName(firstName+","+lastName);
        author.setName(firstName+","+lastName);
        author.setEnFirstNameShort(abbFirstName);

    }


    /**
     * chinese author name process to author
     * @param author
     */
    public static void enAuthorProcess(AuthorData author){
        if(author==null)return ;
        String name = author.getEnName();

        if(name==null||!name.contains(",")|| name.split(",").length<2){
            return ;
        }
        String firstName = name.split(",")[1].trim();
        String lastName = name.split(",")[0].trim();
        String abbName = author.getAbbName();
        if(abbName!=null&&abbName.contains(",")){
            String abbFirstName = abbName.split(",")[1];
            author.setEnFirstNameShort(abbFirstName);
        }else if(firstName.contains("-")){
            String []firstNames = firstName.split("-");
            for(String firstName_tmp : firstNames){
                if(firstName_tmp!=null&&firstName_tmp.length()>=1)
                    author.setEnFirstNameShort(author.getEnFirstNameShort()+
                            firstName_tmp.substring(0,1).toUpperCase());
            }
            firstName.replace("-","");
        }
        author.setEnFirstName(firstName.replace(".","").replace("-",""));
        author.setEnLastName(lastName.replace(".","").replace("-",""));
        author.setEnName(author.getEnFirstName()+","+author.getEnLastName());
        author.setName(author.getEnName());

    }




}
