package common.process.relation;

import common.analysis.EnAnalysis;
import common.pojo.*;
import common.system.FileOperation;
import common.system.MD5Util;
import common.system.StringProcess;
import common.system.Systemconfig;
import common.util.PaperInfoProcess;
import org.apache.log4j.Logger;
import sun.plugin.javascript.navig.Array;

import java.util.*;


/**
 * Created by ziyue on 2017/12/15
 */
public class PaperAuthorProcess {

    private static Logger logger = Logger.getLogger(PaperAuthorProcess.class);

    public void process(){

        List<PaperMergeData> paperMergeDataList = Systemconfig.paperService.getAllMergeDatas();
        List<AuthorData> dbAuthorList = getDbAuthorList();
        List<InstitutionData> dbInstitutionDataList = getMergeInstituionData();

        List<AuthorData> authorAllDataList = extractAuthorInsti(paperMergeDataList);

        logger.info("get authorInstiCountMap");
        Map<String,Map<String,Integer>> authorInstiCountMap =countAuthorInstitution(authorAllDataList);

        List<PaperAuthorInstiData> paperAuthorInstiDataList = new ArrayList<PaperAuthorInstiData>();
        logger.info("get paperAuthorInstiDataList from paperMergeDataList");
        for(PaperMergeData paperMergeData:paperMergeDataList){

            //zh
            if(paperMergeData.getInCnki()==1){
                //insti get dbid
                for(InstitutionData institutionData:paperMergeData.getInstitutionDataList()){
                    for(InstitutionData dbInstitutionData:dbInstitutionDataList){
                        if(dbInstitutionData.getNameZh()==null)continue;
                        if(dbInstitutionData.getNameZh().equals(institutionData.getNameZh())) {//find name in insti db data
                            institutionData.setOriginId(dbInstitutionData.getId());
                            break;
                        }
                    }
                }

                //author
                for(AuthorData authorData:paperMergeData.getAuthorDataList()){

                    PaperAuthorInstiData paperAuthorInstiData = new PaperAuthorInstiData();
                    paperAuthorInstiData.setPaperId(paperMergeData.getId());// paper id
                    paperAuthorInstiData.setAuthorRank(authorData.getRank());//paper rank;

                    //author
                    String zhName = authorData.getZhName();
                    for(AuthorData dbAuthorData:dbAuthorList){
                        if(dbAuthorData.getZhLastName()==null||
                                dbAuthorData.getZhFirstName()==null)continue;
                        String dbzhName = dbAuthorData.getZhLastName()+dbAuthorData.getZhFirstName();
                        if(dbzhName.equals(zhName)){//find name in  author db data
                            paperAuthorInstiData.setAuthorNameId(dbAuthorData.getId());//author id
                            break;
                        }
                    }
                    //institution
                    if(authorData.getInstiIds()!=null){
                        List<Integer> instiIdsList =authorData.getInstiIds();
                        for(int i=0;i<instiIdsList.size();i++){
                            PaperAuthorInstiData paperAuthorInstiData_tmp = null;
                            if(i==0) {
                                paperAuthorInstiData_tmp = paperAuthorInstiData;
                            }else {
                                paperAuthorInstiData_tmp = new PaperAuthorInstiData();// new
                                paperAuthorInstiData_tmp.setHypoInsti(false);// not hypo
                                paperAuthorInstiData_tmp.setAuthorRank(paperAuthorInstiData.getAuthorRank());//author rank
                                paperAuthorInstiData_tmp.setAuthorNameId(paperAuthorInstiData.getAuthorNameId());// author id
                                paperAuthorInstiData_tmp.setPaperId(paperAuthorInstiData.getPaperId());// paper id
                            }
                            int instiRank = instiIdsList.get(i);
                            for (InstitutionData institutionData : paperMergeData.getInstitutionDataList()) {
                                if (instiRank == institutionData.getRank()) {
                                    paperAuthorInstiData_tmp.setInstitutionId(institutionData.getOriginId());//insti id
                                    paperAuthorInstiData_tmp.setInstitutionRank(institutionData.getRank());// insti rank
                                    break;
                                }
                            }
                            paperAuthorInstiDataList.add(paperAuthorInstiData_tmp);
                        }
                    }else{//Hypothesis
                        paperAuthorInstiData.setHypoInsti(true);
                        //authorInstiCountMap
                        int maxCount=0;
                        InstitutionData corrInstitutionData = paperMergeData.getInstitutionDataList().get(0);
                        Map<String,Integer> instiCount=authorInstiCountMap.get(zhName);
                        if(instiCount==null){
                            logger.info("zhName:["+zhName+"] authorInstiCountMap is null");
                        }else {
                            for (InstitutionData institutionData : paperMergeData.getInstitutionDataList()) {
                                if (instiCount.get(institutionData.getNameZh()) != null &&
                                        instiCount.get(institutionData.getNameZh()) > maxCount) {
                                    maxCount = instiCount.get(institutionData.getNameZh());
                                    corrInstitutionData = institutionData;
                                }
                                //only instituion not get author->institution relationship

                                PaperAuthorInstiData paperOnlyInstiData = new PaperAuthorInstiData();
                                paperOnlyInstiData.setPaperId(paperMergeData.getId());// paper id
                                paperOnlyInstiData.setInstitutionId(institutionData.getOriginId());//insti rank
                                paperOnlyInstiData.setInstitutionRank(institutionData.getRank());// insti rank
                                paperOnlyInstiData.setHypoInsti(false);
                                paperAuthorInstiDataList.add(paperOnlyInstiData);
                            }
                        }
                        paperAuthorInstiData.setInstitutionId(corrInstitutionData.getOriginId());// insti id
                        paperAuthorInstiData.setInstitutionRank(corrInstitutionData.getRank());// insti rank
                        paperAuthorInstiDataList.add(paperAuthorInstiData);
                    }
                }

            }else{//en
                //insti get dbid
                for(InstitutionData institutionData:paperMergeData.getInstitutionDataList()){
                    double maxSimilar = 0.70;
                    InstitutionData institutionDataRecord = null;
                    for(InstitutionData dbInstitutionData:dbInstitutionDataList){
                        if(dbInstitutionData.getNameEn()==null)continue;
                        if(dbInstitutionData.getNameEn().equals(institutionData.getNameEn())) {
                            institutionDataRecord = dbInstitutionData;
                            maxSimilar=1;
                            break;
                        }
                        else{
                            double similarValue = EnAnalysis.getSimilarity(dbInstitutionData.getNameEn(),
                                    institutionData.getNameEn());
                            if(similarValue>maxSimilar){
                                maxSimilar = similarValue;
                                institutionDataRecord = dbInstitutionData;
                            }
                        }
                    }
                    if(institutionDataRecord==null){
                        logger.info("paperid:"+paperMergeData.getId()+",name find in author table dbinsti:"
                                + null
                                + ",  eninsti:"+institutionData.getNameEn()
                                + ",  maxSimilar:"+maxSimilar);
                        //insert
                        InquireInfoData inquireInfoData = new InquireInfoData();
                        inquireInfoData.setTableName("institution");
                        inquireInfoData.setInstitutionData(institutionData);
                        Systemconfig.institutionService.saveMerge(inquireInfoData);
                        institutionDataRecord=institutionData;
                    }

                    if(institutionDataRecord!=null){
                        logger.info("paperid:"+paperMergeData.getId()+",name find in author table dbinsti:"
                                + institutionDataRecord.getNameEn()
                                + ",  eninsti:"+institutionData.getNameEn()
                                + ",  maxSimilar:"+maxSimilar);
                        institutionData.setOriginId(institutionDataRecord.getId());
                    }
                }
                //author
                for(AuthorData authorData:paperMergeData.getAuthorDataList()){

                    PaperAuthorInstiData paperAuthorInstiData = new PaperAuthorInstiData();
                    paperAuthorInstiData.setPaperId(paperMergeData.getId());// paper id
                    paperAuthorInstiData.setAuthorRank(authorData.getRank());//paper rank;
                    //author
                    String enName = authorData.getEnName();

                    double maxSimilar = 0.90;
                    AuthorData authorDataRecord = null;
                    for(AuthorData dbAuthorData:dbAuthorList){
                        if(dbAuthorData.getEnLastName()==null||
                                dbAuthorData.getEnFirstName()==null)continue;

                        String dbenName = dbAuthorData.getEnLastName()+" "+dbAuthorData.getEnFirstName();
                        if(dbenName.equals(enName)){//find name in  author db data
                            authorDataRecord = dbAuthorData;//author id
                            maxSimilar=1;
                            break;
                        }else{
                            double similarValue = EnAnalysis.getSimilarity(dbenName,enName);
                            if(similarValue>maxSimilar){
                                maxSimilar = similarValue;
                                authorDataRecord = dbAuthorData;
                            }
                        }
                    }

                    if(authorDataRecord==null){

                        //if current authorData not exit db , just insert
                        logger.info("paperid:"+paperMergeData.getId()+",name find in author table dbName:"
                                + null + ",  enName:"+enName+",  maxSimilar:"+maxSimilar);
                        InquireInfoData inquireInfoData = new InquireInfoData();
                        inquireInfoData.setTableName("author");
                        inquireInfoData.setAuthorData(authorData);
                        Systemconfig.authorService.saveMerge(inquireInfoData);
                        authorDataRecord=authorData;
                    }
                    if(authorDataRecord!=null) {
                        String dbenName = authorDataRecord.getEnLastName() + " "
                                + authorDataRecord.getEnFirstName();
                        logger.info("paperid:"+paperMergeData.getId()+",name find in author table dbName:"
                                + dbenName + ",  enName:"+enName+",  maxSimilar:"+maxSimilar);
                        paperAuthorInstiData.setAuthorNameId(authorDataRecord.getId());
                    }


                    //institution
                    if(authorData.getInstiIds()!=null){
                        List<Integer> instiIdsList =authorData.getInstiIds();
                        for(int i=0;i<instiIdsList.size();i++){
                            PaperAuthorInstiData paperAuthorInstiData_tmp = null;
                            if(i==0) {
                                paperAuthorInstiData_tmp = paperAuthorInstiData;
                            }else {
                                paperAuthorInstiData_tmp = new PaperAuthorInstiData();
                                paperAuthorInstiData_tmp.setHypoInsti(false);
                                paperAuthorInstiData_tmp.setAuthorRank(paperAuthorInstiData.getAuthorRank());//author rank
                                paperAuthorInstiData_tmp.setAuthorNameId(paperAuthorInstiData.getAuthorNameId());//author id
                                paperAuthorInstiData_tmp.setPaperId(paperAuthorInstiData.getPaperId());// paper id
                            }
                            int instiRank = instiIdsList.get(i);
                            for (InstitutionData institutionData : paperMergeData.getInstitutionDataList()) {
                                if (instiRank == institutionData.getRank()) {
                                    paperAuthorInstiData_tmp.setInstitutionId(institutionData.getOriginId());//insti id
                                    paperAuthorInstiData_tmp.setInstitutionRank(institutionData.getRank());// insti rank
                                    break;
                                }
                            }
                            paperAuthorInstiDataList.add(paperAuthorInstiData_tmp);

                        }
                    }else{//Hypothesis
                        paperAuthorInstiData.setHypoInsti(true);
                        //authorInstiCountMap
                        int maxCount=0;
                        InstitutionData corrInstitutionData = paperMergeData.getInstitutionDataList().get(0);
                        Map<String,Integer> instiCount=authorInstiCountMap.get(enName);
                        if(instiCount==null){
                            logger.info("enName:["+enName+"] authorInstiCountMap is null");
                        }else{
                            for(InstitutionData institutionData:paperMergeData.getInstitutionDataList()){
                                if(instiCount.get(institutionData.getNameEn())!=null&&
                                        instiCount.get(institutionData.getNameEn())>maxCount){
                                    maxCount=instiCount.get(institutionData.getNameEn());
                                    corrInstitutionData = institutionData;
                                }
                                //only instituion not get author->institution relationship
                                PaperAuthorInstiData paperOnlyInstiData = new PaperAuthorInstiData();
                                paperOnlyInstiData.setPaperId(paperMergeData.getId());
                                paperOnlyInstiData.setInstitutionId(institutionData.getOriginId());// insti id
                                paperOnlyInstiData.setInstitutionRank(institutionData.getRank());// insti rank
                                paperOnlyInstiData.setHypoInsti(false);
                                paperAuthorInstiDataList.add(paperOnlyInstiData);
                            }
                        }
                        paperAuthorInstiData.setInstitutionId(corrInstitutionData.getOriginId());
                        paperAuthorInstiData.setInstitutionRank(corrInstitutionData.getRank());
                        paperAuthorInstiDataList.add(paperAuthorInstiData);
                    }
                }
            }
        }
        logger.info("savepaperAuthorInstiData");
        savepaperAuthorInstiData(paperAuthorInstiDataList);

    }


    private void save2PaperAuthorDb(List<PaperAuthorInstiData> paperAuthorInstiDataList){

        Set<String> md5Set = new HashSet<String>();
        for (PaperAuthorInstiData paperAuthorInstiData: paperAuthorInstiDataList){
            if(paperAuthorInstiData.getAuthorNameId()!=0) {
                try {
                    String md5  = MD5Util.MD5(""+paperAuthorInstiData.getPaperId()
                            +paperAuthorInstiData.getAuthorNameId());
                    if(md5Set.contains(md5))continue;
                    Systemconfig.paperService.savePaperAuthor(paperAuthorInstiData);
                    md5Set.add(md5);
                }catch (Exception e){
                    logger.warn(e.getMessage()+"\n"+paperAuthorInstiData);
                }
            }

        }
    }

    private void save2PaperAuthorInstiDb(List<PaperAuthorInstiData> paperAuthorInstiDataList){

        Set<String> md5Set = new HashSet<String>();
        for (PaperAuthorInstiData paperAuthorInstiData: paperAuthorInstiDataList){
            if(paperAuthorInstiData.getAuthorNameId()!=0) {
                try {
                    String md5  = MD5Util.MD5(""+paperAuthorInstiData.getPaperId()
                            +paperAuthorInstiData.getAuthorNameId()
                            +paperAuthorInstiData.getInstitutionId()+"");
                    if(md5Set.contains(md5))continue;
                    Systemconfig.paperService.savePaperAuthorInsti(paperAuthorInstiData);
                    md5Set.add(md5);
                }catch (Exception e){
                    logger.warn(e.getMessage()+"\n"+paperAuthorInstiData);
                }
            }

        }
    }
    private void save2PaperInstiDb(List<PaperAuthorInstiData> paperAuthorInstiDataList){

        Set<String> md5Set = new HashSet<String>();
        for (PaperAuthorInstiData paperAuthorInstiData: paperAuthorInstiDataList){
            if(paperAuthorInstiData.getAuthorNameId()!=0) {
                try {
                    String md5  = MD5Util.MD5(""+paperAuthorInstiData.getPaperId()
                            +paperAuthorInstiData.getInstitutionId()+"");
                    if(md5Set.contains(md5))continue;
                    Systemconfig.paperService.savePaperInsti(paperAuthorInstiData);
                    md5Set.add(md5);
                }catch (Exception e){
                    logger.warn(e.getMessage()+"\n"+paperAuthorInstiData);
                }
            }

        }
    }
    private void save2AuthorInstiGroupDb(List<PaperAuthorInstiData> paperAuthorInstiDataList){

        Set<String> md5Set = new HashSet<String>();
        for (PaperAuthorInstiData paperAuthorInstiData: paperAuthorInstiDataList){
            if(paperAuthorInstiData.getAuthorNameId()!=0) {
                try {
                    String md5  = MD5Util.MD5(""+paperAuthorInstiData.getAuthorNameId()
                            +paperAuthorInstiData.getInstitutionId()+"");
                    if(md5Set.contains(md5))continue;
                    Systemconfig.paperService.saveAuthorInstiGroup(paperAuthorInstiData);
                    md5Set.add(md5);
                }catch (Exception e){
                    logger.warn(e.getMessage()+"\n"+paperAuthorInstiData);
                }
            }

        }
    }

    /**
     * save data to paperAuthorDb ,paperAuthorInstiDb,  paperInstiDb
     * @param paperAuthorInstiDataList
     */

    private void savepaperAuthorInstiData(List<PaperAuthorInstiData> paperAuthorInstiDataList){
        logger.info("save to paperAuthorDb");
        save2PaperAuthorDb(paperAuthorInstiDataList);
        logger.info("paperAuthorDb save completed");


        logger.info("save to paperAuthorInstiDb");
        save2PaperAuthorInstiDb(paperAuthorInstiDataList);
        logger.info("paperAuthorInstiDb save completed");


        logger.info("save to paperInstiDb");
        save2PaperInstiDb(paperAuthorInstiDataList);
        logger.info("paperInstiDb save completed");


        //logger.info("save to authorInstiGroupDb");
        //save2AuthorInstiGroupDb(paperAuthorInstiDataList);
        //logger.info("authorInstiGroupDb save completed");

    }

    /**
     *  extractAuthorInsti include chinese and englist
     * @param paperMergeDataList
     * @return
     */

    private List<AuthorData> extractAuthorInsti(List<PaperMergeData> paperMergeDataList){
        List<AuthorData> authorAllDataList = new ArrayList<AuthorData>();
        for(PaperMergeData paperMergeData:paperMergeDataList){

            //zh
            if(paperMergeData.getInCnki()==1){
                zhAuthorInstiExtract(paperMergeData);
                for(AuthorData authorData:paperMergeData.getAuthorDataList()){
                    authorAllDataList.add(authorData);
                    if(authorData.getInstiIds()==null){
                        continue;
                    }
                    for(int instiId:authorData.getInstiIds()){
                        for(InstitutionData institutionData:paperMergeData.getInstitutionDataList()){
                            if(institutionData.getRank()==instiId){
                                authorData.getInstiCountMap().put(institutionData.getNameZh(),1);
                            }
                        }
                    }
                }
            }else{//en
                enAuthorInstiExtract(paperMergeData);
                for(AuthorData authorData:paperMergeData.getAuthorDataList()){
                    authorAllDataList.add(authorData);
                    if(authorData.getInstiIds()==null){
                        continue;
                    }
                    for(int instiId:authorData.getInstiIds()){
                        for(InstitutionData institutionData:paperMergeData.getInstitutionDataList()){
                            if(institutionData.getRank()==instiId){
                                authorData.getInstiCountMap().put(institutionData.getNameEn(),1);
                            }
                        }
                    }

                }
            }
        }
        return authorAllDataList;
    }


    /**
     * count author
     * @param authorAllDataList
     * @return
     */

    private  Map<String,Map<String,Integer>>  countAuthorInstitution(List<AuthorData> authorAllDataList){

        //authorName institutionName count
        Map<String,Map<String,Integer>> authorInstiCountMap = new HashMap<String, Map<String,Integer>>();
        for(AuthorData authorData:authorAllDataList){
            String authorName = authorData.getEnName();
            if(authorName==null) {
                authorName=authorData.getZhName();
            }
            if(authorInstiCountMap.get(authorName)==null){
                authorInstiCountMap.put(authorName,authorData.getInstiCountMap());
            }else{
                Map<String,Integer> instiCount01=authorData.getInstiCountMap();
                Map<String,Integer> instiCount02=authorInstiCountMap.get(authorName);
                Set<String> instiSet01 = instiCount01.keySet();
                for(String insti:instiSet01){
                    if(instiCount02.get(insti)==null){
                        instiCount02.put(insti,instiCount01.get(insti));
                    }else{
                        instiCount02.put(insti,instiCount01.get(insti)+instiCount02.get(insti));
                    }
                }
                authorInstiCountMap.put(authorName,instiCount02);
            }
        }

        //
        Set<String> fzNameSet = new HashSet<String>();
        Set<String> qdNameSet = new HashSet<String>();
        getFileAuthorInsti(fzNameSet,qdNameSet);
        Set<String> authorNameSet = authorInstiCountMap.keySet();
        for(String authorName : authorNameSet){

            if(fzNameSet.contains(authorName)){
                String fzZhStr = "����ϵͳ��������ƹ����ص�ʵ����";
                String fzEnStr = "State Key Laboratory of Management and Control for Complex Systems";
                if(StringProcess.isChinese(authorName)){
                    if(authorInstiCountMap.get(authorName).get(fzZhStr)==null){
                        authorInstiCountMap.get(authorName).put(fzZhStr,1);
                    }
                }else{
                    if(authorInstiCountMap.get(authorName).get(fzEnStr)==null){
                        authorInstiCountMap.get(authorName).put(fzEnStr,1);
                    }

                }
            }
            if(qdNameSet.contains(authorName)){

                String qdZhStr = "�ൺ���ܲ�ҵ�����о�Ժ";
                String qdEnStr = "Qingdao Academy of Intelligent Industries";
                if(StringProcess.isChinese(authorName)){
                    if(authorInstiCountMap.get(authorName).get(qdZhStr)==null){
                        authorInstiCountMap.get(authorName).put(qdZhStr,1);
                    }
                }else{
                    if(authorInstiCountMap.get(authorName).get(qdEnStr)==null){
                        authorInstiCountMap.get(authorName).put(qdEnStr,1);
                    }

                }
            }
        }
        return authorInstiCountMap;
    }

    private void getFileAuthorInsti(Set<String> fzNameSet,Set<String> qdNameSet){
        String []citys = FileOperation.read("src/main/resources/baseData/cityName&Pinyin").split("\n");
        for (String city:citys) {
            //cityPinyinList.add(city.split("##")[1].trim());
        }

        String[]names =FileOperation.read("src/main/resources/baseData/author_fz").split("\n");
        //Set<String> fzNameSet = new HashSet<String>();
        for(String name:names){
            if(name.startsWith("#"))continue;
            name = name.trim();
            AuthorData author =zhNameStr2Author(name);
            fzNameSet.add(name);
            fzNameSet.add(author.getEnName());
            fzNameSet.add(author.getAbbName());
        }

        names =FileOperation.read("src/main/resources/baseData/author_qd").split("\n");
        //Set<String> qdNameSet = new HashSet<String>();
        for(String name:names){
            if(name.startsWith("#"))continue;
            name = name.trim();
            AuthorData author =zhNameStr2Author(name);
            qdNameSet.add(name);
            qdNameSet.add(author.getEnName());
            qdNameSet.add(author.getAbbName());
        }
    }

    private AuthorData zhNameStr2Author(String name){
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
     * ��ȡ�������߻�����ϵ�б�
     * @param paperMergeData
     * @return
     */
    private void zhAuthorInstiExtract(PaperMergeData paperMergeData){
        String authorsStr = paperMergeData.getAuthors();
        String institutionsStr = paperMergeData.getInstitutions();

        if(authorsStr==null||authorsStr.length()==0)return ;

        List<AuthorData>  authorDataList = new ArrayList<AuthorData>();
        List<InstitutionData>  institutionDataList = new ArrayList<InstitutionData>();

        if(institutionsStr.contains(";")) {
            String [] institutionsStrs = institutionsStr.split(";");

            for(int i=0;i<institutionsStrs.length;i++){
                String institution_tmp=institutionsStrs[i].trim();
                institution_tmp = getZhAbbInstitution(institution_tmp,"��");
                InstitutionData institutionData = new InstitutionData();
                institutionData.setNameZh(institution_tmp);
                institutionData.setRank(1+i);
                institutionDataList.add(institutionData);
            }
        }else{
            InstitutionData institutionData = new InstitutionData();
            institutionData.setName(institutionsStr);
            institutionData.setRank(1);
            institutionDataList.add(institutionData);
        }
        if(authorsStr.contains(";")) {
            String [] authorStrs = authorsStr.split(";");
            for(int i=0;i<authorStrs.length;i++){
                String author_tmp=authorStrs[i].trim();
                AuthorData authorData = new AuthorData();
                authorData.setZhName(author_tmp);
                PaperInfoProcess.zhAuthorProcess(authorData);
                authorData.setRank(i+1);
                authorData.setInstiIds(null);
                authorDataList.add(authorData);

            }
        }else{
            AuthorData authorData = new AuthorData();
            authorDataList.add(authorData);
            authorData.setZhName(authorsStr);
            PaperInfoProcess.zhAuthorProcess(authorData);
            authorData.setRank(1);
            List<Integer> instiIdsList = new ArrayList<Integer>();
            authorData.setInstiIds(instiIdsList);
            for(InstitutionData instiData:institutionDataList){
                instiIdsList.add(instiData.getRank());
            }
        }
        paperMergeData.setAuthorDataList(authorDataList);
        paperMergeData.setInstitutionDataList(institutionDataList);
    }

    /**
     *��ȡӢ�����߻�����ϵ�б�
     * @param paperMergeData
     * @return
     */
    private void enAuthorInstiExtract(PaperMergeData paperMergeData){
        String authorsStr = paperMergeData.getAuthors();
        String institutionsStr = paperMergeData.getInstitutions();

        List<AuthorData> authorDataList = new ArrayList<AuthorData>();
        List<InstitutionData> institutionDataList = new ArrayList<InstitutionData>();
        paperMergeData.setAuthorDataList(authorDataList);
        paperMergeData.setInstitutionDataList(institutionDataList);

        String splitMark=";";
        if(institutionsStr==null){
            logger.info("institutionsStr is null");
        }
        if(institutionsStr.contains("####")||
                (paperMergeData.getInEi()==1&&paperMergeData.getInSci()==0)){
            splitMark="####";
        }

        String []institutionsStrs = institutionsStr.split(splitMark);

        for(int i=0;i<institutionsStrs.length;i++){
            String institutionsStr_tmp = institutionsStrs[i];
            institutionsStr_tmp = getEnAbbInstitution(institutionsStr_tmp,",");
            InstitutionData institutionData = new InstitutionData();
            institutionData.setNameEn(institutionsStr_tmp);
            institutionData.setRank(i+1);
            institutionDataList.add(institutionData);
        }//Chinese Academy of Science  Chinese Academy of Sciences

        //process author
        String []authorsStrs = authorsStr.split(";");
        for(int i=0;i<authorsStrs.length;i++){
            String authorName_tmp=authorsStrs[i];
            authorName_tmp=authorName_tmp.replaceAll("\\s+","").trim();
            List<Integer> authorInstiList = getBracketNums(authorName_tmp);
            authorName_tmp = authorName_tmp.replaceAll("\\[.*?\\]","").trim();
            AuthorData authorData = new AuthorData();
            if(authorName_tmp.contains("(")){
                String name = authorName_tmp.replaceAll("\\[.*?\\]","");
                String abbname = name.replaceAll("\\(.*?\\)","");
                name = name.replaceFirst(abbname,"").replaceAll("[\\(|\\)]","");
                authorData.setEnName(name);
                PaperInfoProcess.enAuthorProcess(authorData);
                authorData.setAbbName(abbname);
                authorData.setInstiIds(authorInstiList);

            }else{
                authorData.setEnName(authorName_tmp);
                PaperInfoProcess.enAuthorProcess(authorData);
                authorData.setInstiIds(authorInstiList);
            }
            authorData.setRank(i+1);
            authorDataList.add(authorData);
        }
    }
    protected String getZhAbbInstitution(String institution,String splitMark){

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

    protected String getEnAbbInstitution(String institution,String splitMark){

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
