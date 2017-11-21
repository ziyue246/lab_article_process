package common.mapper;



import common.pojo.AuthorData;
import common.pojo.InquireInfoData;
import common.pojo.PaperData;

import java.util.List;


public interface AuthorMapper {


    public List<AuthorData> findAllDatas(InquireInfoData inquireInfoData);
    public void insert(InquireInfoData inquireInfoData);

}
