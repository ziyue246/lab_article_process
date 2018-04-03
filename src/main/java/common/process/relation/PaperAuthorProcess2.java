package common.process.relation;

import common.analysis.EnAnalysis;
import common.pojo.*;
import common.system.StringProcess;
import common.system.Systemconfig;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class PaperAuthorProcess2 {

    private static Logger logger = Logger.getLogger(PaperAuthorProcess2.class);

    public void process(){

        List<PaperMergeData> paperMergeDataList = Systemconfig.paperService.getAllMergeDatas();
        List<AuthorData> dbAuthorList = getDbAuthorList();
        List<InstitutionData> dbInstitutionDataList = getMergeInstituionData();

        List<AuthorInstiData> authorInstiAllDataList = new ArrayList<AuthorInstiData>();
        for(PaperMergeData paperMergeData:paperMergeDataList){

            //zh
            if(paperMergeData.getInCnki()==1){
                List<AuthorInstiData> authorInstiDataList = zhAuthorInstiExtract(paperMergeData);
                for(AuthorInstiData authorInstiData:authorInstiDataList){
                    authorInstiData.setPaperId(paperMergeData.getId());
                    authorInstiAllDataList.add(authorInstiData);
                    //author
                    String zhName = authorInstiData.getAuthorZhName();
                    for(AuthorData dbAuthorData:dbAuthorList){
                        if(dbAuthorData.getZhLastName()==null||
                                dbAuthorData.getZhFirstName()==null)continue;
                        String dbzhName = dbAuthorData.getZhLastName()+dbAuthorData.getZhFirstName();
                        if(dbzhName.equals(zhName)){//find name in  author db data
                            authorInstiData.setAuthorNameId(dbAuthorData.getId());//author id
                            break;
                        }
                    }
                    //institution
                    List<String> institutionStrList = authorInstiData.getInstitutions();
                    if(institutionStrList==null){                       continue;
                    }
                    List<Integer> instiIdsList = new ArrayList<Integer>();
                    for(String instiStr:institutionStrList){
                        for(InstitutionData dbInstitutionData:dbInstitutionDataList){
                            if(dbInstitutionData.getNameZh()==null)continue;
                            if(dbInstitutionData.getNameZh().equals(instiStr)) {//find name in insti db data
                                instiIdsList.add(dbInstitutionData.getId());
                                break;
                            }
                        }
                    }

                }

            }else{//en

                List<AuthorInstiData> authorInstiDataList = enAuthorInstiExtract(paperMergeData);

                for(AuthorInstiData authorInstiData:authorInstiDataList){
                    authorInstiData.setPaperId(paperMergeData.getId());
                    authorInstiAllDataList.add(authorInstiData);
                    //author
                    String enName = authorInstiData.getAuthorEnName();
                    for(AuthorData dbAuthorData:dbAuthorList){
                        String dbenName = dbAuthorData.getEnLastName()+dbAuthorData.getEnFirstName();
                        if(dbAuthorData.getEnLastName()==null||
                                dbAuthorData.getEnFirstName()==null)continue;
                        if(dbenName.equals(enName)||
                                EnAnalysis.getSimilarity(dbenName,enName)>0.95){//find name in  author db data
                            authorInstiData.setAuthorNameId(dbAuthorData.getId());//author id
                            break;
                        }
                    }
                    //institution
                    List<String> institutionStrList = authorInstiData.getInstitutions();
                    List<Integer> instiIdsList = new ArrayList<Integer>();
                    authorInstiData.setInstitutionIds(instiIdsList);
                    for(String instiStr:institutionStrList){
                        for(InstitutionData dbInstitutionData:dbInstitutionDataList){
                            if(dbInstitutionData.getNameEn()==null)continue;
                            if(dbInstitutionData.getNameEn().equals(instiStr)||
                                    EnAnalysis.getSimilarity(dbInstitutionData.getNameEn(),enName)>0.95) {//find name in insti db data
                                instiIdsList.add(dbInstitutionData.getId());
                                break;
                            }
                        }
                    }

                }
            }
        }


        System.out.println("hello world");
    }



    /**
     * //get institution merge data from db
     * @return
     */
    private List<InstitutionData> getMergeInstituionData(){
        String tableName ="institution";
        InquireInfoData inquireInfoData = new InquireInfoData();
        inquireInfoData.setTableName(tableName);
        return Systemconfig.institutionService.getAllMergeDatas(inquireInfoData);

    }

    private int findDbAuthorId(List<AuthorData> dbAuthorList,AuthorInstiData authorInstiData,String type){

        if(type.equals("zh")){
            for(AuthorData authorData:dbAuthorList){
                String dbZhName= authorData.getZhLastName()+authorData.getZhFirstName();
                if(dbZhName.equals(authorInstiData.getAuthorZhName())){
                    return authorData.getId();
                }
            }
        }else if(type.equals("en")){
            for(AuthorData authorData:dbAuthorList){
                String dbZhName= authorData.getEnLastName()+authorData.getEnFirstName();
                if(dbZhName.equals(authorInstiData.getAuthorEnName())||
                        EnAnalysis.getSimilarity(dbZhName,authorInstiData.getAuthorEnName())>0.95){
                    return authorData.getId();
                }
            }
        }
        return 0;
    }



    /**
     *
     * @param paperMergeData
     * @return
     */
    private List<AuthorInstiData> zhAuthorInstiExtract(PaperMergeData paperMergeData){
        String authorsStr = paperMergeData.getAuthors();
        String institutionsStr = paperMergeData.getInstitutions();

        if(authorsStr==null||authorsStr.length()==0)return null;
        List<AuthorInstiData>  authorInstiDataList = new ArrayList<AuthorInstiData>();
        if(authorsStr.contains(";")) {
            String [] authorStrs = authorsStr.split(";");

            for(int i=0;i<authorStrs.length;i++){
                String author_tmp=authorStrs[i].trim();
                AuthorInstiData authorInstiData = new AuthorInstiData();
                authorInstiData.setAuthorZhName(author_tmp);
                authorInstiData.setAuthorRank(i+1);
                authorInstiDataList.add(authorInstiData);
                authorInstiData.setInstitutions(null);
            }
        }else{
            AuthorInstiData authorInstiData = new AuthorInstiData();
            authorInstiData.setAuthorZhName(authorsStr);
            authorInstiData.setAuthorRank(1);
            List<String> institutionsStrList = new ArrayList<String>();
            authorInstiData.setInstitutions(institutionsStrList);
            if(institutionsStr.contains(";")) {
                String [] institutionsStrs = institutionsStr.split(";");

                for(int i=0;i<institutionsStrs.length;i++){
                    String institution_tmp=institutionsStrs[i].trim();
                    institutionsStrList.add(institution_tmp);
                }
            }else{
                institutionsStrList.add(institutionsStr);
            }
            authorInstiDataList.add(authorInstiData);
        }
        return authorInstiDataList;
    }

    /**
     *
     * @param paperMergeData
     * @return
     */
    private List<AuthorInstiData> enAuthorInstiExtract(PaperMergeData paperMergeData){
        String authorsStr = paperMergeData.getAuthors();
        String institutionsStr = paperMergeData.getInstitutions();
        List<AuthorInstiData> authorInstiDataList = new ArrayList<AuthorInstiData>();

        String splitMark=";";
        if(institutionsStr==null){
            logger.info("institutionsStr is null");
        }
        if(institutionsStr.contains("####")){
            splitMark="####";
        }
        String []institutionsStrs = institutionsStr.split(splitMark);

        List<String> institutionList = new ArrayList<String>();
        for(int i=0;i<institutionsStrs.length;i++){
            String institutionsStr_tmp = institutionsStrs[i];
            //String numStr = StringProcess.regex2StrSplitByMark(institutionsStr_tmp,"\\[.*?\\]","");
            //numStr = StringProcess.regex2StrSplitByMark(numStr,"\\d","");
            //int num = StringProcess.str2Int(numStr);
            institutionsStr_tmp = getAbbInstitution(institutionsStr_tmp,",");
            institutionList.add(institutionsStr_tmp);
        }

        //process author
        String []authorsStrs = authorsStr.split(";");
        for(int i=0;i<authorsStrs.length;i++){
            String authorName_tmp=authorsStrs[i];
            authorName_tmp=authorName_tmp.replaceAll("\\s+","").trim();
            List<Integer> authorInstiList = getBracketNums(authorName_tmp);
            authorName_tmp = authorName_tmp.replaceAll("\\[.*?\\]","").trim();


            AuthorInstiData authorInstiData = new AuthorInstiData();
            if(authorName_tmp.contains("(")){
                String name = authorName_tmp.replaceAll("\\[.*?\\]","");
                String abbname = name.replaceAll("\\(.*?\\)","");
                name = name.replaceFirst(abbname,"").replaceAll("[\\(|\\)]","");

                authorInstiData.setAuthorEnName(name);
                authorInstiData.setAuthorEnName(abbname);

            }else{
                authorInstiData.setAuthorEnName(authorName_tmp);
            }
            List<String> institutionListInAuthor = new ArrayList<String>();
            if(authorInstiList==null){
                logger.info("paper:"+paperMergeData.getTitle()+" author:"+authorName_tmp+" ");
            }else {
                for (int index : authorInstiList) {

                    try {
                        institutionListInAuthor.add(institutionList.get(index - 1));
                    }catch (Exception e){
                        System.out.println("stop");
                    }
                }
            }
            authorInstiData.setAuthorRank(i+1);
            authorInstiData.setInstitutions(institutionListInAuthor);
            authorInstiDataList.add(authorInstiData);
        }
        return authorInstiDataList;
    }

    protected String getAbbInstitution(String institution,String splitMark){

        institution = institution.replace("&","and");
        String []inss= institution.split(splitMark);



        String result=inss[0];
        for(String ins:inss){
            if(ins.contains("University")||ins.contains("Univ ")||
                    ins.contains("College")||
                    ins.contains("Academy")||ins.contains("Acad ")||//Chinese Acad Sci
                    (ins.contains("Institute")&&!institution.contains("Academy"))){
                result= ins.trim();
            }//Chinese Academy of Sciences

            if(ins.contains("Chinese Academy of Sciences")){
                result="Chinese Academy of Sciences";
            }
            if((institution.contains("Institute of Automation")||
                    institution.toLowerCase().contains("casia")||
                    institution.toLowerCase().contains("Inst Automat"))&&
                    institution.contains("State Key")&&
                    institution.contains("Complex")&&institution.contains("Management")){
                result="State Key Laboratory of Management and Control for Complex Systems";
                break;
            }//Qingdao Academy of Intelligent Industries
            if((institution.toLowerCase().contains("qingdao")||
                    institution.toLowerCase().contains("academy")||
                    institution.toLowerCase().contains("intelligent")||
                    institution.toLowerCase().contains("industries"))){
                result="Qingdao Academy of Intelligent Industries";
                break;
            }
        }

        result = result.replaceAll("\\(.*?\\)","");
        result = result.replaceAll("\\[.*?\\]","");
        if(result.contains(";")){
            result = result.split(";")[0].trim();
        }
        return result;
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



    private List<AuthorData> getDbAuthorList(){
        InquireInfoData inquireInfoData = new InquireInfoData();
        inquireInfoData.setTableName("author");
        List<AuthorData> authorDataList = Systemconfig.authorService.getAllMergeDatas(inquireInfoData);
        return authorDataList;
    }



}
