package common.service;

import common.mapper.InstitutionMapper;
import common.mapper.PaperMapper;
import common.pojo.InquireInfoData;
import common.pojo.InstitutionData;
import common.pojo.PaperData;

import java.util.List;

public class InstitutionService {

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


}
