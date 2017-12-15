package common.process.author.merge;

import common.analysis.EnAnalysis;
import common.pojo.AuthorData;
import common.pojo.InquireInfoData;
import common.pojo.InstitutionData;
import common.pojo.PaperData;
import common.process.author.single.CnkiAuthorProcess;
import common.process.author.single.EiAuthorProcess;
import common.process.author.single.SciAuthorProcess;
import common.system.StringProcess;
import common.system.Systemconfig;
import org.apache.log4j.Logger;

import java.util.*;

public class AuthorMergeProcess {

    private static Logger logger = Logger.getLogger(AuthorMergeProcess.class);

    public void process(){

        HashMap<String,AuthorData> eiAuthorMap  = getEiNameSet();
        HashMap<String,AuthorData> sciAuthorMap  = getSciNameSet();
        HashMap<String,AuthorData> cnkiAuthorMap = getCnkiNameSet();


        List<AuthorData> dbAuthor = getDbAuthorSet();

//        for(String name:sciAuthorMap.keySet()){
//            enAuthorProcess(sciAuthorMap.get(name));
//            System.out.println((name));
//            System.out.println(sciAuthorMap.get(name).getName());
//        }
        Set<AuthorData> srcAuthorSet = new HashSet<AuthorData>();
        for(String name:eiAuthorMap.keySet()){
            enAuthorProcess(eiAuthorMap.get(name));
            srcAuthorSet.add(eiAuthorMap.get(name));
        }
        for(String name:sciAuthorMap.keySet()){
            enAuthorProcess(sciAuthorMap.get(name));
            srcAuthorSet.add(sciAuthorMap.get(name));
        }
        for(String name:cnkiAuthorMap.keySet()){
            zhAuthorProcess(cnkiAuthorMap.get(name));
            srcAuthorSet.add(cnkiAuthorMap.get(name));
        }
        Set<AuthorData> newAuthorSet = new HashSet<AuthorData>();
        mergeAuthor(dbAuthor,newAuthorSet,srcAuthorSet);

        for(AuthorData authorData:newAuthorSet){
            InquireInfoData inquireInfoData = new InquireInfoData();
            inquireInfoData.setTableName("author");
            inquireInfoData.setAuthorData(authorData);
            logger.info(authorData.getName());
            Systemconfig.authorService.saveMerge(inquireInfoData);
        }
    }
    public void mergeAuthor(List<AuthorData> dbAuthor,Set<AuthorData> newAuthorSet,Set<AuthorData> srcAuthorSet){
        for(AuthorData authorData:newAuthorSet){
            for(Iterator<AuthorData> it = srcAuthorSet.iterator(); it.hasNext(); ){
                AuthorData author_tmp = it.next();
                String name_tmp = author_tmp.getName();
                if (isAuthorMergeByName(authorData.getName(), name_tmp)) {
                    it.remove();
                }
            }
        }

        for(AuthorData authorData:dbAuthor){
            for(Iterator<AuthorData> it = srcAuthorSet.iterator(); it.hasNext(); ){
                AuthorData author_tmp = it.next();
                String name_tmp = author_tmp.getName();
                if (isAuthorMergeByName(authorData.getName(), name_tmp)) {
                    it.remove();
                }
            }

        }

        for(AuthorData authorData:srcAuthorSet){
            newAuthorSet.add(authorData);
        }
    }

    private boolean isAuthorMergeByName(String name1,String name2){

        if(name1.toLowerCase().equals(name2.toLowerCase())) return true;
        if(EnAnalysis.getSimilarity(name1,name2)>0.95)return true;
        return false;
    }

    private boolean isAuthorMergeByChName(String name1,String name2){
        if(name1.toLowerCase().equals(name2.toLowerCase())) return true;
        return false;
    }

    private List<AuthorData> getDbAuthorSet(){
        InquireInfoData inquireInfoData = new InquireInfoData();
        inquireInfoData.setTableName("author");
        List<AuthorData> authorDataList = Systemconfig.authorService.getAllMergeDatas(inquireInfoData);


        return authorDataList;
    }

    private HashMap<String,AuthorData> getEiNameSet(){
        InquireInfoData inquireInfoData = new InquireInfoData();
        inquireInfoData.setTableName("ei_data");
        List<PaperData> eiPaperDataList = Systemconfig.paperService.getAllDatas(inquireInfoData);

        EiAuthorProcess eiAuthorProcess = new EiAuthorProcess();
        eiAuthorProcess.process(eiPaperDataList,";");
        return eiAuthorProcess.extract(eiPaperDataList);
    }
    private HashMap<String,AuthorData> getSciNameSet(){
        InquireInfoData inquireInfoData = new InquireInfoData();
        inquireInfoData.setTableName("sci_data");
        List<PaperData> sciPaperDataList = Systemconfig.paperService.getAllDatas(inquireInfoData);

        SciAuthorProcess sciAuthorProcess = new SciAuthorProcess();
        sciAuthorProcess.process(sciPaperDataList,";");
        return sciAuthorProcess.extract(sciPaperDataList);
    }
    private HashMap<String,AuthorData> getCnkiNameSet(){

        InquireInfoData inquireInfoData = new InquireInfoData();
        inquireInfoData.setTableName("cnki_data");
        List<PaperData> cnkiPaperDataList = Systemconfig.paperService.getAllDatas(inquireInfoData);

        CnkiAuthorProcess cnkiAuthorProcess = new CnkiAuthorProcess();
        cnkiAuthorProcess.process(cnkiPaperDataList,";");
        return cnkiAuthorProcess.extract(cnkiPaperDataList);

    }

    public AuthorData enNameStr2Author(String name){
        AuthorData author = new AuthorData();

        String firstName = name.split(",")[1].trim();
        String lastName = name.split(",")[0].trim();


        author.setEnFirstName(firstName);
        author.setEnLastName(lastName);
        author.setEnName(firstName+","+lastName);
        author.setName(firstName+","+lastName);

        return author;
    }

    public void enAuthorProcess(AuthorData author){
        if(author==null)return ;
        String name = author.getName();

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

    public void zhAuthorProcess(AuthorData author){

        String name = author.getName();
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
        author.setEnName(firstName+","+lastName);
        author.setName(firstName+","+lastName);
        author.setEnFirstNameShort(abbFirstName);

    }


}
