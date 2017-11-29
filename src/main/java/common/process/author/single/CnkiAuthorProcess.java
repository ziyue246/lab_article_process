package common.process.author.single;

import common.pojo.AuthorData;
import common.pojo.InquireInfoData;
import common.pojo.InstitutionData;
import common.pojo.PaperData;
import common.system.StringProcess;
import common.system.Systemconfig;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class CnkiAuthorProcess {

    private static Logger logger = Logger.getLogger(CnkiAuthorProcess.class);

    public void process(List<PaperData> paperDataList, String splitMain){
        for(PaperData paperData:paperDataList){
            if(paperData.getAuthor()==null)continue;
            String []authors = paperData.getAuthor().split(splitMain);
            List<InstitutionData> institutionDataList =paperData.getInstitutionDataList();
            List<AuthorData> authorDataList = new ArrayList<AuthorData>();
            paperData.setAuthorDataList(authorDataList);
            for(String author:authors){
                author=author.replaceAll("\\s+","").trim();
                AuthorData authorData = new AuthorData();
                authorData.setName(author);
                authorData.setTitles(";"+paperData.getTitle()+";");
                authorData.setOriginIds(";"+paperData.getId()+";");
                authorData.setWeight(1);
                authorDataList.add(authorData);
            }
        }
    }



    public HashMap<String,AuthorData> extract(List<PaperData> paperDataList) {

        //粗合并，名称相同即合并
        HashMap<String,AuthorData> authorDataHashMap = new HashMap<String, AuthorData>();
        for(PaperData paperData:paperDataList){
            List<AuthorData> authorDataList = paperData.getAuthorDataList();
            if(authorDataList==null||authorDataList.size()==0)continue;
            for(AuthorData auth:authorDataList){
                if(authorDataHashMap.get(auth.getName())==null){
                    authorDataHashMap.put(auth.getName(),auth);
                }else{
                    AuthorData auth_tmp =  authorDataHashMap.get(auth.getName());
                    auth_tmp.setInstitution(auth_tmp.getInstitution()+auth.getInstitution());
                    auth_tmp.setOriginIds(auth_tmp.getOriginIds()+auth.getOriginIds());
                    auth_tmp.setTitles(auth_tmp.getTitles()+auth.getTitles());
                    auth_tmp.setWeight(auth_tmp.getWeight()+auth.getWeight());
                }
            }

        }
        return authorDataHashMap;

//        //保存数据库
//        Set<String> authorNameKeySet=authorDataHashMap.keySet();
//        logger.info("tatol author datas size:"+authorNameKeySet.size());
//        for(String authorName:authorNameKeySet){
//            InquireInfoData inquireInfoData = new InquireInfoData();
//            inquireInfoData.setTableName(tableName);
//            inquireInfoData.setAuthorData(authorDataHashMap.get(authorName));
//            Systemconfig.authorService.save(inquireInfoData);
//        }
//        logger.info("all author datas save sucessful");
    }

    public List<Integer> getBracketNums(String str){
        if(str.contains("[")&&str.contains("]")){
            List<Integer> intList = new ArrayList();
            str=str.replace("\\s+","");
            List<String> strList = StringProcess.regex2List(str,"\\[\\d+\\]");
            for(String s :strList){
                s=StringProcess.regex2List(s,"\\d+").get(0);
                int num = Integer.parseInt(s);
                intList.add(num);
            }
            return intList;
        }
        return null;
    }
}
