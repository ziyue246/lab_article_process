package common.process.institution.single;

import common.pojo.InquireInfoData;
import common.pojo.InstitutionData;
import common.pojo.PaperData;
import common.process.institution.InstitutionProcess;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;


/**
 *
 */
public class CnkiInstitutionProcess extends InstitutionProcess {

    private static Logger logger = Logger.getLogger(CnkiInstitutionProcess.class);
    private final static List<String> cityPinyinList = new ArrayList<String>();



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
            paperData.setInstitutionDataList(institutionDataList);//当前文章机构列表
            for(String address:addresss){
                //address = address.replaceAll("\\s*","").trim();

                String abbadd = getAbbInstitution(address,",");
                InstitutionData institutionData = new InstitutionData();
                institutionData.setId(-1);
                institutionData.setName(abbadd);
                institutionData.setTitles(";"+paperData.getTitle()+";");
                institutionData.setOriginIds(";"+paperData.getId()+";");
                institutionData.setWeight(1);
                institutionDataList.add(institutionData);
                logger.info(abbadd);

            }
        }
    }



    protected String getAbbInstitution(String institution,String splitMark){

        institution = institution.replace("&","and");
        String []inss= institution.split(splitMark);


        //截取机构部分；


//        清华信息科学与技术国家实验室(筹),清华大学自动化系
//        中国科学院自动化研究所复杂系统管理与控制国家重点实验室
//                中国科学院大学
//        英国克兰菲尔德大学驾驶员认知与自动驾驶实验室;
//       青岛智能产业技术研究院
//                西安交通大学人工智能与机器人研究所
//        国防科技大学军事计算实验与平行系统技术中心
        String result=inss[0];
        for(String ins:inss){
            if(ins.contains("大学")){
                result= ins.substring(0,ins.indexOf("大学")+2);
            }else if(ins.contains("中国科学院")){
                result="中国科学院";
            }else if(ins.contains("研究院")){
                result= ins.substring(0,ins.indexOf("研究院")+3);
            }
            if((institution.contains("自动化研究所"))&&
                    institution.contains("复杂")&&
                    institution.contains("控制")&&institution.contains("国家重点")){
                result="复杂系统管理与控制国家重点实验室";
                break;
            }
        }
        return result;
    }


    public HashMap<String,InstitutionData> extractMerge(List<PaperData> paperDataList){

        //粗合并，名称相同即合并
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
        return institutionDataHashMap;
    }



}
