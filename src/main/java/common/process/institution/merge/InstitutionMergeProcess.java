package common.process.institution.merge;

import common.analysis.EnAnalysis;
import common.analysis.ZhAnalysis;
import common.pojo.InquireInfoData;
import common.pojo.InstitutionData;
import common.pojo.PaperData;
import common.process.institution.single.CnkiInstitutionProcess;
import common.process.institution.single.EiInstitutionProcess;
import common.process.institution.InstitutionProcess;
import common.system.Systemconfig;
import org.apache.log4j.Logger;

import java.util.*;


/**
 *
 */
public class InstitutionMergeProcess extends InstitutionProcess {

    private static Logger logger = Logger.getLogger(InstitutionMergeProcess.class);
    private final static List<String> cityPinyinList = new ArrayList<String>();


    /**
     *
     */

    public void process() {

        InquireInfoData inquireInfoData = new InquireInfoData();

        Set<String>  eiInstitutionSet   = getIeInstitutionNameSet();
        Set<String>  sciInstitutionSet  = getSciInstitutionNameSet();
        Set<String>  cnkiInstitutionSet = getCnkiInstitutionNameSet();

        //从数据库获取mergetable数据
        inquireInfoData.setTableName("institution");
        List<InstitutionData> originMergePaperDataList = Systemconfig.institutionService.getAllMergeDatas(inquireInfoData);

        for(InstitutionData institutionData:originMergePaperDataList){
            String nameZh = institutionData.getNameZh();
            String nameEn = institutionData.getNameEn();
            removeFromSet(nameEn,eiInstitutionSet  ,"EN");
            removeFromSet(nameEn,sciInstitutionSet ,"EN");
            removeFromSet(nameZh,cnkiInstitutionSet,"ZH");
        }
        List<InstitutionData> mergePaperDataList = new ArrayList<InstitutionData>();

        for(String eiInstiName:eiInstitutionSet){
            for(Iterator<String> it = sciInstitutionSet.iterator(); it.hasNext(); ){
                String name_tmp = it.next();
                if (isEnInstiAbbFullMerge(name_tmp, eiInstiName)) {
                    it.remove();
                }
            }
        }
        addMergePaperDataList(mergePaperDataList,eiInstitutionSet  ,"EN");
        addMergePaperDataList(mergePaperDataList,sciInstitutionSet ,"EN");
        addMergePaperDataList(mergePaperDataList,cnkiInstitutionSet,"ZH");

        save(mergePaperDataList,"institution");

    }

    private void save(List<InstitutionData> list,String tableName){

        logger.info("tatol merge institution datas size:"+list.size());
        for(InstitutionData institutionData:list){
            InquireInfoData inquireInfoData = new InquireInfoData();
            inquireInfoData.setTableName(tableName);
            inquireInfoData.setInstitutionData(institutionData);
            Systemconfig.institutionService.saveMerge(inquireInfoData);
        }
        logger.info("all merge institution datas save sucessful");
    }

    /**
     *
     * @param list
     * @param set
     * @param mark
     */
    private void addMergePaperDataList(List<InstitutionData> list,Set<String> set,String mark){

        for(String instiName:set){
            InstitutionData institutionData = new InstitutionData();
            if(mark.equals("EN")) {
                institutionData.setNameEn(instiName);
                list.add(institutionData);
            }else if(mark.equals("ZH")){
                institutionData.setNameZh(instiName);
                list.add(institutionData);
            }

        }

    }

    /**
     *
     * @param name
     * @param set
     * @param mark
     */
    private void removeFromSet(String name,Set<String> set,String mark){
        if(name==null||name.length()==0)return ;
        for(Iterator<String> it = set.iterator(); it.hasNext(); ){
            String name_tmp = it.next();
            if(mark.equals("EN")) {
                if (EnAnalysis.getSimilarity(name, name_tmp) > 0.95) {
                    it.remove();
                }
            }else if(mark.equals("ZH")){
                if (ZhAnalysis.getSimilarity(name, name_tmp) > 0.95) {
                    it.remove();
                }
            }

        }
    }
    private Set<String> getCnkiInstitutionNameSet(){
        InquireInfoData inquireInfoData = new InquireInfoData();
        //从数据库获取cnki数据
        inquireInfoData.setTableName("cnki_data");
        List<PaperData> cnkiPaperDataList = Systemconfig.paperService.getAllDatas(inquireInfoData);
        CnkiInstitutionProcess cnkiInstitution = new CnkiInstitutionProcess();
        cnkiInstitution.process(cnkiPaperDataList, ";");
        return cnkiInstitution.extractMerge(cnkiPaperDataList).keySet();

    }

    private Set<String> getSciInstitutionNameSet(){
        InquireInfoData inquireInfoData = new InquireInfoData();

        //从数据库获取sci数据
        inquireInfoData.setTableName("sci_data");
        List<PaperData> sciPaperDataList = Systemconfig.paperService.getAllDatas(inquireInfoData);

        InstitutionProcess institution = new InstitutionProcess();
        institution.process(sciPaperDataList, ";");

        return institution.extractMerge(sciPaperDataList).keySet();
    }


    private Set<String> getIeInstitutionNameSet(){
        InquireInfoData inquireInfoData = new InquireInfoData();

        //从数据库获取ei数据

        inquireInfoData.setTableName("ei_data");
        List<PaperData> eiPaperDataList = Systemconfig.paperService.getAllDatas(inquireInfoData);

        EiInstitutionProcess eiInstitution = new EiInstitutionProcess();
        eiInstitution.process(eiPaperDataList, "####");
        return eiInstitution.extractMerge(eiPaperDataList).keySet();
    }

    public boolean isEnInstiAbbFullMerge(String abb,String full) {
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
