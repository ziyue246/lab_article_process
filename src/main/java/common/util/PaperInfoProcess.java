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
        author.setEnFirstName(firstName.replace(".","").replace("-",""));
        author.setEnLastName(lastName.replace(".","").replace("-",""));
        author.setEnName(author.getEnFirstName()+","+author.getEnLastName());
        author.setName(author.getEnName());

    }

    public  static String getZhAbbInstitution(String institution,String splitMark){

        institution = institution.replace("&","and");
        String []inss= institution.split(splitMark);


        String result=inss[0];
        for(String ins:inss){
            if(ins.contains("��ѧ")){
                result= ins.substring(0,ins.indexOf("��ѧ")+2);
            }else if(ins.contains("�й���ѧԺ")){
                result="�й���ѧԺ";
            }else if(ins.contains("�о�Ժ")){
                result= ins.substring(0,ins.indexOf("�о�Ժ")+3);
            }
            if((institution.contains("�Զ����о���"))&&
                    institution.contains("����")&&
                    institution.contains("����")&&institution.contains("�����ص�")){
                result="����ϵͳ��������ƹ����ص�ʵ����";
                break;
            }//�ൺ���ܲ�ҵ�����о�Ժ
            if((institution.contains("�ൺ���ܲ�ҵ�����о�Ժ"))){
                result="�ൺ���ܲ�ҵ�����о�Ժ";
                break;
            }
        }
        return result;
    }

    public  static String getEnAbbInstitution(String institution,String splitMark){

        institution = institution.replace("&","and");
        String []inss= institution.split(splitMark);


        //��ȡ�������֣�
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
            if(institution.toLowerCase().contains("institute of automation")||
                    institution.toLowerCase().contains("casia")||
                    institution.toLowerCase().contains("inst automat")){
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
        //�Ի������ֽ�������
        result = result.replaceAll("\\(.*?\\)","");
        result = result.replaceAll("\\[.*?\\]","");
        if(result.contains(";")){
            result = result.split(";")[0].trim();
        }
        logger.info("origin insti:"+institution+",extract insti:"+result);
        return result;
    }

}
