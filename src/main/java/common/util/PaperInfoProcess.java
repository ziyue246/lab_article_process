package common.util;

import common.pojo.AuthorData;
import common.system.StringProcess;
import org.apache.log4j.Logger;

/**
 * Created by ziyue on 2018/1/29.
 */
public class PaperInfoProcess {


    private static Logger logger = Logger.getLogger(PaperInfoProcess.class);


    public static void authorProcess(AuthorData author){
        if(StringProcess.isChinese(author.getName())){
            zhAuthorProcess(author);
        }else{
            enAuthorProcess(author);
        }
    }
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
            abbFirstName += StringProcess.toHanyuPinyin(
                    firstName.substring(i, i + 1)).substring(0, 1).toUpperCase();

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
     * chinese name process to author
     * @param name
     * @return AuthorData author
     */
    public static AuthorData zhAuthorProcess(String name){
        AuthorData author = new AuthorData();
        author.setZhName(name);
        author.setName(name);
        zhAuthorProcess(author);
        return author;
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
        if(author.getEnFirstNameShort()!=null)
            author.setEnFirstNameShort(author.getEnFirstNameShort().replace("null",""));
        author.setEnFirstName(firstName.replace(".","").replace("-",""));
        author.setEnLastName(lastName.replace(".","").replace("-",""));
        author.setEnName(author.getEnFirstName()+","+author.getEnLastName());
        author.setName(author.getEnName());

    }

    public  static String getZhAbbInstitution(String institution,String splitMark){

        splitMark = StringProcess.Str2Utf8(splitMark);

        institution = institution.replace("&","and");
        String []inss= institution.split(splitMark);


        String result=inss[0];
        for(String ins:inss){

            try{
                if(ins.contains("大学")){
                    result= ins.substring(0,ins.indexOf("大学")+2);
                }else if(ins.contains("中国科学院")){
                    result="中国科学院";
                }else if(ins.contains("研究院")){
                    result= ins.substring(0,ins.indexOf("研究院")+3);
                }//中国科学院自动化研究所复杂系统管理与控制国家重点实验室;青岛智能产业技术研究院智慧教育研究所;中国科学院大学;北方自动控制技术研究所;中国指挥与控制学会
                institution = StringProcess.Str2Utf8(institution);


                if(institution.contains( StringProcess.Str2Utf8("自动化研究所"))&&
                        institution.contains(StringProcess.Str2Utf8("复杂"))&&
                        institution.contains(StringProcess.Str2Utf8("控制"))&&
                        institution.contains(StringProcess.Str2Utf8("国家重点"))){
                    result =  StringProcess.Str2Utf8("复杂系统管理与控制国家重点实验室");
                    break;
                }//青岛智能产业技术研究院
                if((institution.contains("青岛智能产业技术研究院"))){
                    result="青岛智能产业技术研究院";
                    break;
                }
            }catch (Exception e){

            }
        }
        return result;
    }

    public  static String getEnAbbInstitution(String institution,String splitMark){

        institution = institution.replace("&","and");
        String []inss= institution.split(splitMark);


        //截取机构部分；
        String result=inss[0];
        for(String ins:inss){
            if(ins.contains("University")||ins.contains("Univ ")||
                    ins.contains("College")||
                    ins.contains("Academy")||ins.contains("Acad ")||//Chinese Acad Sci  Inst Automat
                    (ins.contains("Institute")&&!institution.contains("Academy"))){
                result= ins.trim();
            }//Chinese Academy of Sciences  Chinese Academy of Sciences

            if(ins.contains("Chinese Academy of Science")||ins.contains("Chinese Acad Sci")){
                result="Chinese Academy of Sciences";
            }
//            [1] State Key Laboratory of Management and Control for Complex Systems,
//            Institute of Automation, Chinese Academy of Sciences, Beijing; 100190, China

            //
            //Univ Duisburg Essen, Inst Automat Control & Complex Syst, D-47057 Duisburg, Germany
            if((institution.toLowerCase().contains("institute of automation")||
                    institution.toLowerCase().contains("casia")||
                    institution.toLowerCase().contains("inst automat"))
                    && (!institution.toLowerCase().contains("univ"))
                    &&(!institution.toLowerCase().contains("shenyang"))){
                if(institution.contains("State Key")&&
                        institution.contains("Complex")&&institution.contains("Management")) {
                    result = "State Key Laboratory of Management and Control for Complex Systems";
                }else if(institution.toLowerCase().contains("lab mol imaging")
                        ||ins.toLowerCase().contains("laboratory of molecular imaging")) {
                    //Beijing Key Laboratory of Molecular Imaging
                    //Key Lab Mol Imaging,
                    result = "Beijing Key Laboratory of Molecular Imaging";
                }else{
                    result = "Institute of Automation";
                }
                break;
            }

            //Qingdao Academy of Intelligent Industries
            if((institution.toLowerCase().contains("qingdao")&&
                    institution.toLowerCase().contains("academy")&&
                    institution.toLowerCase().contains("intelligent")&&
                    institution.toLowerCase().contains("industries"))){
                result="Qingdao Academy of Intelligent Industries";
                break;
            }
        }
        //对机构部分进行整理
        result=result.trim();
        result = result.replaceAll("\\(.*?\\)","");
        result = result.replaceAll("\\[.*?\\]","");
        if(result.contains(";")){
            result = result.split(";")[0].trim();
        }
        logger.info("origin insti:"+institution+",extract insti:"+result);
        return result;
    }

}
