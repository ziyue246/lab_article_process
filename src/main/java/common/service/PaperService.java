package common.service;

import common.mapper.PaperMapper;
import common.pojo.InquireInfoData;
import common.pojo.PaperData;

import java.util.List;

public class PaperService {

    private PaperMapper paperMapper;

    public PaperMapper getPaperMapper() {
        return paperMapper;
    }

    public void setPaperMapper(PaperMapper paperMapper) {
        this.paperMapper = paperMapper;
    }

    //    public PaperData findDataById(InquireInfo inquireInfo);
//
//    public List<PaperData> findAllDatas(InquireInfo inquireInfo);
//
//    public List<PaperData> findDatasByPeriod(InquireInfo inquireInfo);
//
//    public List<PaperData> findDatasByStartId(InquireInfo inquireInfo);
//    public int findMaxId(InquireInfo inquireInfo);



    public PaperData getDataById(InquireInfoData inquireInfoData){
        return paperMapper.findDataById(inquireInfoData);
    }


    public List<PaperData> getAllDatas(InquireInfoData inquireInfoData){
        return paperMapper.findAllDatas(inquireInfoData);
    }

    public List<PaperData> getDatasByPeriod(InquireInfoData inquireInfoData){
        return paperMapper.findDatasByPeriod(inquireInfoData);
    }
    public List<PaperData> findDatasByStartId(InquireInfoData inquireInfoData){
        return paperMapper.findDatasByStartId(inquireInfoData);
    }
    public int getMaxId(InquireInfoData inquireInfoData){
        return paperMapper.findMaxId(inquireInfoData);
    }

}
