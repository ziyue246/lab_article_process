package common.service;

import common.mapper.InstitutionMapper;
import common.mapper.PaperMapper;
import common.pojo.InquireInfoData;
import common.pojo.InstitutionData;
import common.pojo.PaperData;
import common.process.institution.merge.InstitutionMergeProcess;
import common.system.Systemconfig;
import org.apache.log4j.Logger;

import java.util.List;

public class InstitutionService {


    private static Logger logger = Logger.getLogger(InstitutionService.class);


    private InstitutionMapper institutionMapper;

    public InstitutionMapper getInstitutionMapper() {
        return institutionMapper;
    }

    public void setInstitutionMapper(InstitutionMapper institutionMapper) {
        this.institutionMapper = institutionMapper;
    }

    //    public PaperData findDataById(InquireInfo inquireInfo);
//
//    public List<PaperData> findAllDatas(InquireInfo inquireInfo);
//
//    public List<PaperData> findDatasByPeriod(InquireInfo inquireInfo);
//
//    public List<PaperData> findDatasByStartId(InquireInfo inquireInfo);
//    public int findMaxId(InquireInfo inquireInfo);



    public void save(InquireInfoData inquireInfoData){
        institutionMapper.insert(inquireInfoData);
    }
    public void saveMerge(InquireInfoData inquireInfoData){
        institutionMapper.insertMerge(inquireInfoData);
    }
    public List<InstitutionData> getAllDatas(InquireInfoData inquireInfoData){
        return institutionMapper.findAllDatas(inquireInfoData);
    }

    public List<InstitutionData> getAllMergeDatas(InquireInfoData inquireInfoData){
        return institutionMapper.findAllMergeDatas(inquireInfoData);
    }
    /**
     *
     * @param list   List<InstitutionData>
     * @param tableName db tableName
     */
    private void saveList(List<InstitutionData> list,String tableName){

        logger.info("tatol merge institution datas size:"+list.size());
        for(InstitutionData institutionData:list){
            InquireInfoData inquireInfoData = new InquireInfoData();
            inquireInfoData.setTableName(tableName);
            inquireInfoData.setInstitutionData(institutionData);
            //Systemconfig.institutionService.
            saveMerge(inquireInfoData);
        }
        logger.info("all merge institution datas save sucessful");
    }

    /**
     *
     * @param institutionData   InstitutionData
     * @param tableName db tableName
     */
    private void save(InstitutionData institutionData,String tableName){


        InquireInfoData inquireInfoData = new InquireInfoData();
        inquireInfoData.setTableName(tableName);
        inquireInfoData.setInstitutionData(institutionData);
        //Systemconfig.institutionService.
        saveMerge(inquireInfoData);
        logger.info("institution data save sucessful");
    }

}
