package common.process.institution;

import common.analysis.EnAnalysis;
import common.pojo.InquireInfoData;
import common.pojo.InstitutionData;
import common.pojo.PaperData;
import common.system.FileOperation;
import common.system.Systemconfig;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;


/**
 *
 */
public class CnkiInstitutionProcess {

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


        //��ȡ�������֣�


//        �廪��Ϣ��ѧ�뼼������ʵ����(��),�廪��ѧ�Զ���ϵ
//        �й���ѧԺ�Զ����о�������ϵͳ��������ƹ����ص�ʵ����
//                �й���ѧԺ��ѧ
//        Ӣ�������ƶ��´�ѧ��ʻԱ��֪���Զ���ʻʵ����;
//       �ൺ���ܲ�ҵ�����о�Ժ
//                ������ͨ��ѧ�˹�������������о���
//        �����Ƽ���ѧ���¼���ʵ����ƽ��ϵͳ��������
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


    public void extractAndSave(List<PaperData> paperDataList,String tableName){

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
        //�������ݿ�
        Set<String> instiNameKeySet=institutionDataHashMap.keySet();
        logger.info("tatol institution datas size:"+instiNameKeySet.size());
        for(String instiName:instiNameKeySet){
            if(institutionDataHashMap.get(instiName).getStatus()==-1)continue;
            InquireInfoData inquireInfoData = new InquireInfoData();
            inquireInfoData.setTableName(tableName);
            inquireInfoData.setInstitutionData(institutionDataHashMap.get(instiName));
            //Systemconfig.institutionService.save(inquireInfoData);
        }
        logger.info("all institution datas save sucessful");
    }

}
