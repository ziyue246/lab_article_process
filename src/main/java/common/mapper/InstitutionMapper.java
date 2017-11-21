package common.mapper;



import common.pojo.InquireInfoData;
import common.pojo.InstitutionData;
import common.pojo.PaperData;

import java.util.List;


public interface InstitutionMapper {


    public List<InstitutionData> findAllDatas(InquireInfoData inquireInfoData);
    public void insert(InquireInfoData inquireInfoData);

}
