package common.mapper;



import common.pojo.InquireInfoData;
import common.pojo.PaperAuthorInstiData;
import common.pojo.PaperData;
import common.pojo.PaperMergeData;

import java.util.List;


public interface PaperMapper {

    public PaperData findDataById(InquireInfoData inquireInfoData);

    public List<PaperData> findAllDatas(InquireInfoData inquireInfoData);

    public List<PaperData> findDatasByPeriod(InquireInfoData inquireInfoData);

    public List<PaperData> findDatasByStartId(InquireInfoData inquireInfoData);


    public int findMaxId(InquireInfoData inquireInfoData);

    public List<PaperMergeData> findAllMergeDatas();
    public void insertMerge(PaperMergeData paperMergeData);
    public void updateMerge(PaperMergeData paperMergeData);


    public void insertPaperAuthor(PaperAuthorInstiData paperAuthorInstiData);
    public void insertPaperAuthorInsti(PaperAuthorInstiData paperAuthorInstiData);
    public void insertPaperInsti(PaperAuthorInstiData paperAuthorInstiData);
    public void insertAuthorInstiGroup(PaperAuthorInstiData paperAuthorInstiData);

}
