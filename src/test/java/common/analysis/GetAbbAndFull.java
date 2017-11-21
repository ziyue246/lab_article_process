package common.analysis;

import common.pojo.InquireInfoData;
import common.pojo.InstitutionData;
import common.system.AppContext;
import common.system.Systemconfig;
import org.junit.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GetAbbAndFull {

    @Test
    public void test(){
        AppContext.initial();
        InquireInfoData inquireInfoData = new InquireInfoData();
        inquireInfoData.setTableName("institution_ei");
        List<InstitutionData> eiInstitutionDataList = Systemconfig.institutionService.getAllDatas(inquireInfoData);


        inquireInfoData.setTableName("institution_sci");
        List<InstitutionData> sciInstitutionDataList = Systemconfig.institutionService.getAllDatas(inquireInfoData);

        Set<String> eiNameSet  = new HashSet<String>();
        Set<String> sciNameSet = new HashSet<String>();

        for(InstitutionData data:eiInstitutionDataList){
            String name = data.getName();
            String []words = name.split(" ");
            for(String word:words){
                word=word.trim();
                eiNameSet.add(word);
            }
        }
        for(InstitutionData data:sciInstitutionDataList){
            String name = data.getName();
            String []words = name.split(" ");
            for(String word:words){
                word=word.trim();
                //System.out.println(word+"\t:\t"+name);
                sciNameSet.add(word);
            }
        }
        for(String eiName:eiNameSet){
            for(String sciName:sciNameSet){

                if(sciName.length()>=3&&
                        sciName.length()< eiName.length()&&
                        eiName.startsWith(sciName)){
                    System.out.println(sciName+"\t:\t"+eiName);
                }

            }
        }

    }

    @Test
    public void test01(){
        AppContext.initial();
        InquireInfoData inquireInfoData = new InquireInfoData();
        inquireInfoData.setTableName("institution_ei");
        List<InstitutionData> eiInstitutionDataList = Systemconfig.institutionService.getAllDatas(inquireInfoData);


        inquireInfoData.setTableName("institution_sci");
        List<InstitutionData> sciInstitutionDataList = Systemconfig.institutionService.getAllDatas(inquireInfoData);


        Set<String> eiNameSet  = new HashSet<String>();
        Set<String> sciNameSet = new HashSet<String>();


        for(InstitutionData data:eiInstitutionDataList){
            String name = data.getName();

            eiNameSet.add(name);

        }
        for(InstitutionData data:sciInstitutionDataList){
            String name = data.getName();
            sciNameSet.add(name);

        }


        int allcount=0;
        for(String eiName:eiNameSet){
            for(String sciName:sciNameSet) {
                if(isInstitutionMerge(sciName,eiName)){
                    System.out.println(sciName+"\t:\t"+eiName);
                }
            }
        }




        System.out.println(allcount);
    }

    public boolean isInstitutionMerge(String abb,String full) {
        String nameabb = abb;
        String namefull = full.replace("of", "")
                .replace("  ", " ");
        String[] abbwords = nameabb.split(" ");
        String[] fullwords = namefull.split(" ");
        if (abbwords.length == fullwords.length) {
            int count = 0;
            for (String abbword : abbwords) {
                for (String fullword : fullwords) {
                    if (fullword.startsWith(abbword)) {
                        ++count;
                    }
                }
            }
            if (count == abbwords.length) {
                return true;
            }
        }
        return false;
    }
}
