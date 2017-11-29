package common.process.author;

import common.analysis.EnAnalysis;
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

public class AuthorProcess {

    private static Logger logger = Logger.getLogger(AuthorProcess.class);

    public void process(List<PaperData> paperDataList, String splitMain){
        for(PaperData paperData:paperDataList){
            if(paperData.getAuthor()==null)continue;
            String []authors = paperData.getAuthor().split(splitMain);
            List<InstitutionData> institutionDataList =paperData.getInstitutionDataList();
            List<AuthorData> authorDataList = new ArrayList<AuthorData>();
            paperData.setAuthorDataList(authorDataList);
            for(String author:authors){
                author=author.replaceAll("\\s+","").trim();
                List<Integer> authorInstiList = getBracketNums(author);
                AuthorData authorData = new AuthorData();
                author = author.replaceAll("\\[\\d+\\]","");

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
        //细合并，计算相似度合并
        List<String> authorNameKeyList = new ArrayList<String>(authorDataHashMap.keySet());
        for(int i=0;i<authorNameKeyList.size();i++){
            String authorNamei = authorNameKeyList.get(i);
            AuthorData authorDatai = authorDataHashMap.get(authorNamei);
            if(authorDatai.getStatus()==-1){
                continue;
            }
            for(int j=i+1;j<authorNameKeyList.size();j++){

                String authorNamej = authorNameKeyList.get(j);
                AuthorData authorDataj = authorDataHashMap.get(authorNamej);
                if(authorDataj.getStatus()==-1){
                    continue;
                }
                double similarity = EnAnalysis.getSimilarity(authorNamei,authorNamej);
                if(similarity>0.95){
                    if(authorDatai.getWeight()>authorDataj.getWeight()){
                        authorDatai.setWeight(authorDatai.getWeight()+authorDataj.getWeight());
                        authorDatai.setTitles(authorDatai.getTitles()+authorDataj.getTitles());
                        authorDatai.setOriginIds(authorDatai.getOriginIds()+authorDataj.getOriginIds());
                        authorDataHashMap.get(authorNamej).setTitles(authorNamei);
                        authorDataHashMap.get(authorNamej).setStatus(-1);
                    }else{
                        authorDataj.setWeight(authorDatai.getWeight()+authorDataj.getWeight());
                        authorDataj.setTitles(authorDatai.getTitles()+authorDataj.getTitles());
                        authorDataj.setOriginIds(authorDatai.getOriginIds()+authorDataj.getOriginIds());
                        authorDataHashMap.get(authorNamei).setTitles(authorNamej);
                        authorDataHashMap.get(authorNamei).setStatus(-1);
                    }
                }
            }
        }

        return authorDataHashMap;
//        //保存数据库
//        Set<String> authorNameKeySet=authorDataHashMap.keySet();
//        logger.info("tatol author datas size:"+authorNameKeySet.size());
//        for(String authorName:authorNameKeySet){
//            if(authorDataHashMap.get(authorName).getStatus()==-1)continue;
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
