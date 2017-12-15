package common.service;

import common.mapper.CommonMapper;
import common.mapper.PaperMapper;
import common.pojo.InquireInfoData;
import common.pojo.PaperAuthorInstiData;
import common.pojo.PaperData;
import common.pojo.PaperMergeData;

import java.util.List;

public class CommonService {

    private CommonMapper commonMapper;

    public CommonMapper getCommonMapper() {
        return commonMapper;
    }

    public void setCommonMapper(CommonMapper commonMapper) {
        this.commonMapper = commonMapper;
    }

    //    public PaperData findDataById(InquireInfo inquireInfo);
//
//    public List<PaperData> findAllDatas(InquireInfo inquireInfo);
//
//    public List<PaperData> findDatasByPeriod(InquireInfo inquireInfo);
//
//    public List<PaperData> findDatasByStartId(InquireInfo inquireInfo);
//    public int findMaxId(InquireInfo inquireInfo);






    public void save(String oneSql){
        commonMapper.insert(oneSql);
    }
}
