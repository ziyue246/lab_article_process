package common.process.author;

import common.pojo.AuthorData;
import common.pojo.InstitutionData;
import common.pojo.PaperData;
import common.system.StringProcess;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class SciAuthorProcess extends AuthorProcess{

    private static Logger logger = Logger.getLogger(SciAuthorProcess.class);


    //Zheng,EH(Zheng,Enhao)[1,2];
    // Manca,S(Manca,Silvia)[3];Yan,TF(Yan,Tingfang)[3,4];Parri,A(Parri,Andrea)[3];
    // Vitiello,N(Vitiello,Nicola)[3,5];Wang,QN(Wang,Qining)[6,7]
    public void process(List<PaperData> paperDataList, String splitMain){
        for(PaperData paperData:paperDataList){
            if(paperData.getAuthor()==null)continue;
            String []authors = paperData.getAuthor().split(splitMain);
            List<InstitutionData> institutionDataList =paperData.getInstitutionDataList();
            List<AuthorData> authorDataList = new ArrayList<AuthorData>();
            paperData.setAuthorDataList(authorDataList);
            for(String author:authors){
                author=author.replaceAll("\\s+","").trim();
                String abbName=author.split(("\\("))[0];
                String fullName = StringProcess.regex2StrSplitByMark(author,("\\(.*\\)"),(""));
                fullName=fullName.replace((")"),("")).replace(("("),(""));

                String fullInsti = StringProcess.regex2StrSplitByMark(author,("\\[.*\\]"),(""));
                fullInsti = fullInsti.replaceAll((","),("]["));
                List<Integer> authorInstiList = getBracketNums(fullInsti);
                AuthorData authorData = new AuthorData();
                if(authorInstiList!=null&&authorInstiList.size()>0) {
                    //int maxNum = authorInstiList.get(authorInstiList.size() - 1);
                    //author = author.split("\\[" + maxNum + "\\]")[1].trim();
                    for(int num:authorInstiList){
                        authorData.setInstitution(";"+institutionDataList.get(num-1).getName()+";");

                    }
                }
                authorData.setName(fullName);
                authorData.setAbbName(abbName);
                authorData.setTitles(";"+paperData.getTitle()+";");
                authorData.setOriginIds(";"+paperData.getId()+";");
                authorData.setWeight(1);
                authorDataList.add(authorData);
            }
        }
    }
}
