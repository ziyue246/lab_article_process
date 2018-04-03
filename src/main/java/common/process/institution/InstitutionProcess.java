package common.process.institution;

import common.analysis.EnAnalysis;
import common.pojo.InstitutionData;
import common.pojo.PaperData;
import common.system.FileOperation;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 *
 */
public class InstitutionProcess {

    private static Logger logger = Logger.getLogger(InstitutionProcess.class);
    private final static List<String> cityPinyinList = new ArrayList<String>();

    static{
        String []citys = FileOperation.read("src/main/resources/baseData/cityName&Pinyin").split("\n");
        for (String city:citys) {
            cityPinyinList.add(city.split("##")[1].trim());
        }
    }


    /**
     *
     * @param paperDataList
     * @param splitMain
     */
    public void process(List<PaperData> paperDataList,String splitMain){
        for(PaperData paperData:paperDataList){
            if(paperData.getAddress()==null)continue;
            String []addresss = paperData.getAddress().split(splitMain);
            List<InstitutionData> institutionDataList =new ArrayList<InstitutionData>();
            paperData.setInstitutionDataList(institutionDataList);//��ǰ���»����б�
            for(String address:addresss){
                //address = address.replaceAll("\\s*","").trim();
                if(address.contains("]")){
                    String numStr = address.split("\\]")[0].split("\\[")[1].trim();
                    int num = Integer.parseInt(numStr);
                    address=address.split("\\]")[1].trim();
                    String abbadd = getAbbInstitution(address,",");
                    InstitutionData institutionData = new InstitutionData();
                    institutionData.setId(num);
                    institutionData.setName(abbadd);
                    institutionData.setTitles(";"+paperData.getTitle()+";");
                    institutionData.setOriginIds(";"+paperData.getId()+";");
                    institutionData.setWeight(1);
                    institutionDataList.add(institutionData);
                    logger.info(abbadd);
                }
            }
        }
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
            if((institution.toLowerCase().contains("institute of automation")||
                    institution.toLowerCase().contains("casia")||
                    institution.toLowerCase().contains("inst automat"))
                    &&
                    institution.contains("State Key")&&
                    institution.contains("Complex")&&institution.contains("Management")) {
                result = "State Key Laboratory of Management and Control for Complex Systems";

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

        result = result.replaceAll("\\(.+\\)","");
        if(result.contains(";")){
            result = result.split(";")[0].trim();
        }
        return result;
    }


    private String citySplit(String institution){

        String markCity=null;
        for(String cityPinyin:cityPinyinList){
            String lowerCase1=institution.toLowerCase();
            String lowerCase2=cityPinyin.toLowerCase();

            if(lowerCase1.contains(lowerCase2)){
                int startSite=lowerCase2.indexOf(lowerCase2);
                markCity = institution.substring(startSite,startSite+lowerCase2.length());
            }
        }

        if(markCity==null)return institution;
        StringBuffer sb = new StringBuffer();
        String []instis = institution.split(markCity);
        for(int i=0;i<instis.length;i++){
            if(institution.startsWith(markCity)||i>0){
                sb.append(markCity+instis[i]+",");
            }else{
                sb.append(instis[i]+",");
            }
        }
        return sb.toString();
    }

    public HashMap<String,InstitutionData>  extractMerge(List<PaperData> paperDataList){



        HashMap<String,InstitutionData>  institutionDataHashMap = new HashMap<String, InstitutionData>();
        for(PaperData paperData:paperDataList){
            List<InstitutionData> institutionDataList = paperData.getInstitutionDataList();

            for(InstitutionData insti:institutionDataList){
                if(institutionDataHashMap.get(insti.getName())==null){
                    institutionDataHashMap.put(insti.getName(),insti);
                }else{
                    InstitutionData insti_tmp =  institutionDataHashMap.get(insti.getName());
                    insti_tmp.setTitles(insti_tmp.getTitles()+insti.getTitles());
                    insti_tmp.setOriginIds(insti_tmp.getOriginIds()+insti.getOriginIds());
                    insti_tmp.setWeight(insti_tmp.getWeight()+insti.getWeight());
                }
            }

        }


        List<String> instiNameKeyList = new ArrayList<String>(institutionDataHashMap.keySet());
        for(int i=0;i<instiNameKeyList.size();i++){
            String instiNamei = instiNameKeyList.get(i);
            InstitutionData institutionDatai = institutionDataHashMap.get(instiNamei);
            if(institutionDatai.getStatus()==-1){
                continue;
            }
            for(int j=i+1;j<instiNameKeyList.size();j++){

                String instiNamej = instiNameKeyList.get(j);
                InstitutionData institutionDataj = institutionDataHashMap.get(instiNamej);
                if(institutionDataj.getStatus()==-1){
                    continue;
                }
                double similarity = EnAnalysis.getSimilarity(instiNamei,instiNamej);
                if(similarity>0.95){
                    if(institutionDatai.getWeight()>institutionDataj.getWeight()){
                        institutionDatai.setWeight(institutionDatai.getWeight()+institutionDataj.getWeight());
                        institutionDatai.setTitles(institutionDatai.getTitles()+institutionDataj.getTitles());
                        institutionDatai.setOriginIds(institutionDatai.getOriginIds()+institutionDataj.getOriginIds());
                        institutionDataHashMap.get(instiNamej).setTitles(instiNamei);
                        institutionDataHashMap.get(instiNamej).setStatus(-1);
                    }else{
                        institutionDataj.setWeight(institutionDatai.getWeight()+institutionDataj.getWeight());
                        institutionDataj.setTitles(institutionDatai.getTitles()+institutionDataj.getTitles());
                        institutionDataj.setOriginIds(institutionDatai.getOriginIds()+institutionDataj.getOriginIds());
                        institutionDataHashMap.get(instiNamei).setTitles(instiNamej);
                        institutionDataHashMap.get(instiNamei).setStatus(-1);
                    }
                }
            }
        }
        processTwo(paperDataList,institutionDataHashMap);
        return institutionDataHashMap;
    }


    /**
     * similarity
     * @param paperDataList
     * @param institutionDataHashMap
     */
    private void processTwo(List<PaperData> paperDataList,HashMap<String,InstitutionData>  institutionDataHashMap){

        for(PaperData paperData:paperDataList) {

            List<InstitutionData> institutionDataList =paperData.getInstitutionDataList();

            if(institutionDataList==null||institutionDataList.size()==0){
                continue;
            }
            for (InstitutionData insti:institutionDataList){
                InstitutionData insti_tmp = institutionDataHashMap.get(insti);
                if(insti_tmp==null)continue;
                String correctName=insti_tmp.getCorrectName();
                if(correctName!=null){
                    insti.setName(correctName);
                }
            }
        }
    }

}
