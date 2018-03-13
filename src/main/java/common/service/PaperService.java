package common.service;

import common.mapper.PaperMapper;
import common.pojo.InquireInfoData;
import common.pojo.PaperAuthorInstiData;
import common.pojo.PaperData;
import common.pojo.PaperMergeData;

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


    public List<PaperMergeData> getAllMergeDatas(){
        return paperMapper.findAllMergeDatas();
    }

    public void saveMergeData(PaperMergeData paperMergeData){
        paperMapper.insertMerge(paperMergeData);
    }
    public void updateMergeData(PaperMergeData paperMergeData){
        paperMapper.updateMerge(paperMergeData);
    }
    public void savePaperAuthor(PaperAuthorInstiData paperAuthorInstiData){
        paperMapper.insertPaperAuthor(paperAuthorInstiData);
    }

    public void savePaperAuthorInsti(PaperAuthorInstiData paperAuthorInstiData){
        paperMapper.insertPaperAuthorInsti(paperAuthorInstiData);
    }
    public void savePaperInsti(PaperAuthorInstiData paperAuthorInstiData){
        paperMapper.insertPaperInsti(paperAuthorInstiData);
    }
    public void saveAuthorInstiGroup(PaperAuthorInstiData paperAuthorInstiData){
        paperMapper.insertAuthorInstiGroup(paperAuthorInstiData);
    }

//    public List<PaperAuthorInstiData> findPaperAuthorDatas();
//    public List<PaperAuthorInstiData> findPaperAuthorInstiDatas();
//    public List<PaperAuthorInstiData> findPaperInstiDatas();
//    public List<PaperAuthorInstiData> findAuthorInstiGroupDatas();

    public List<PaperAuthorInstiData> getPaperAuthorDatas(){
        return paperMapper.findPaperAuthorDatas();
    }
    public List<PaperAuthorInstiData> getPaperAuthorInstiDatas(){
        return paperMapper.findPaperAuthorInstiDatas();

    }
    public List<PaperAuthorInstiData> getPaperInstiDatas(){
        return paperMapper.findPaperInstiDatas();
    }
    public List<PaperAuthorInstiData> getAuthorInstiGroupDatas(){
        return paperMapper.findAuthorInstiGroupDatas();
    }
}
