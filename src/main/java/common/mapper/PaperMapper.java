package common.mapper;



import common.pojo.InquireInfoData;
import common.pojo.PaperData;

import java.util.List;


public interface PaperMapper {

    public PaperData findDataById(InquireInfoData inquireInfoData);

    public List<PaperData> findAllDatas(InquireInfoData inquireInfoData);

    public List<PaperData> findDatasByPeriod(InquireInfoData inquireInfoData);

    public List<PaperData> findDatasByStartId(InquireInfoData inquireInfoData);


    public int findMaxId(InquireInfoData inquireInfoData);











}
