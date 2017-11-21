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
            paperData.setInstitutionDataList(institutionDataList);//��ǰ���»����б�
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
            }
        }
        return result;
    }


    public HashMap<String,InstitutionData> extractMerge(List<PaperData> paperDataList){

        //�ֺϲ���������ͬ���ϲ�
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
