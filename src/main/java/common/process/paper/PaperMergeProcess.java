package common.process.paper;

import common.analysis.EnAnalysis;
import common.pojo.InquireInfoData;
import common.pojo.PaperData;
import common.pojo.PaperMergeData;
import common.system.OperationExcel;
import common.system.Systemconfig;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PaperMergeProcess {

    private static Logger logger = Logger.getLogger(PaperMergeProcess.class);

    public void process() {

        InquireInfoData inquireInfoData = new InquireInfoData();
        inquireInfoData.setTableName("ei_data");
        List<PaperData> eiPaperDataList = Systemconfig.paperService.getAllDatas(inquireInfoData);

        inquireInfoData.setTableName("sci_data");
        List<PaperData> sciPaperDataList = Systemconfig.paperService.getAllDatas(inquireInfoData);

        inquireInfoData.setTableName("cnki_data");
        List<PaperData> cnkiPaperDataList = Systemconfig.paperService.getAllDatas(inquireInfoData);


        List<PaperMergeData> paperMergeDataList = Systemconfig.paperService.getAllMergeDatas();
        //List<PaperMergeData> paperMergeDataList = new ArrayList<PaperMergeData>();
        for(PaperData paperData:eiPaperDataList){
            paperMergeDataList.add(paperData2paperMergeData(paperData,"ei"));
        }
        for(PaperData paperData:sciPaperDataList){
            enPaperMerge(paperMergeDataList,paperData,"sci");
        }
        for(PaperData paperData:cnkiPaperDataList){
            paperMergeDataList.add(paperData2paperMergeData(paperData,"cnki"));
        }
        for(PaperMergeData paperMergeData:paperMergeDataList){
            Systemconfig.paperService.saveMergeData(paperMergeData);
        }
        logger.info("save paper merge data size:"+ paperMergeDataList.size());
    }
    private void  enPaperMerge(List<PaperMergeData> paperMergeDataList,PaperData paperData,String type){
        for(PaperMergeData paperMergeData:paperMergeDataList){
            if(paperMergeData.getInCnki()==1){
                continue;
            }
            double titleSimilarity          = EnAnalysis.getSimilarity(paperMergeData.getTitle(),paperData.getTitle());
            //double authorSimilarity         = EnAnalysis.getSimilarity(paperMergeData.getAuthors(),paperData.getAuthor());
            //double institutionSimilarity    = EnAnalysis.getSimilarity(paperMergeData.getInstitutions(),paperData.getAddress());


            if(titleSimilarity>0.95){
                if(paperMergeData.getAuthors()==null&&paperData.getAuthor()!=null){
                    paperMergeData.setAuthors(paperData.getAuthor());
                }
                if(paperMergeData.getInstitutions()==null&&paperData.getAddress()!=null){
                    paperMergeData.setInstitutions(paperData.getAddress());
                }
                if(type.toLowerCase().contains("sci")) {
                    paperMergeData.setInSci(1);
                    paperMergeData.setSciDown(paperData.getDownNum());
                    paperMergeData.setSciRefer(paperData.getCiteNum());
                    paperMergeData.setSciDataId(paperData.getId());


                }
                if(type.toLowerCase().contains("ei")) {
                    paperMergeData.setInEi(1);
                    paperMergeData.setEiDown(paperData.getDownNum());
                    paperMergeData.setEiRefer(paperData.getCiteNum());
                    paperMergeData.setEiDataId(paperData.getId());
                    if(paperMergeData.getInSci()!=1){
                        String formatStr = OperationExcel.dealEiArticle(paperData,null,null,null,null);
                        paperMergeData.setFormatStr(formatStr);
                    }
                }
                return;
            }
        }
        paperMergeDataList.add(paperData2paperMergeData(paperData,type));

    }

    private PaperMergeData  paperData2paperMergeData(PaperData paperData,String type){
        if(paperData==null)return null;
        PaperMergeData paperMergeData = new PaperMergeData();
        paperMergeData.setTitle(paperData.getTitle());
        paperMergeData.setAuthors(paperData.getAuthor());
        paperMergeData.setInstitutions(paperData.getAddress());
        paperMergeData.setPubdate(paperData.getPubdate());
        paperMergeData.setPublisher(paperData.getPublisher());
        paperMergeData.setInsertTime(paperData.getInsertTime());
        paperMergeData.setBrief(paperData.getBrief());
        paperMergeData.setJournal(paperData.getJournal());
        paperMergeData.setKeywords(paperData.getKeywords());
        paperMergeData.setFund(paperData.getFund());
        paperMergeData.setVolume(paperData.getVolume());
        paperMergeData.setIssue(paperData.getIssue());
        paperMergeData.setPageCode(paperData.getPageCode());
        paperMergeData.setDoi(paperData.getDoi());
        paperMergeData.setImpactFactor2year(paperData.getImpactFactor2year());
        paperMergeData.setImpactFactor5year(paperData.getImpactFactor5year());
        paperMergeData.setJcr(paperData.getJcr());
        paperMergeData.setSourceTitle(paperData.getSourceTitle());


        if(type.toLowerCase().contains("sci")) {
            paperMergeData.setInSci(1);

            paperMergeData.setSciDown(paperData.getDownNum()<0?0:paperData.getDownNum());
            paperMergeData.setSciRefer(paperData.getCiteNum()<0?0:paperData.getCiteNum());
            paperMergeData.setSciDataId(paperData.getId());
            String formatStr = OperationExcel.dealSciArticle(paperData,null,null,null,null);
            paperMergeData.setFormatStr(formatStr);
        }
        if(type.toLowerCase().contains("ei")) {
            paperMergeData.setInEi(1);
            paperMergeData.setEiDown(paperData.getDownNum()<0?0:paperData.getDownNum());
            paperMergeData.setEiRefer(paperData.getCiteNum()<0?0:paperData.getCiteNum());
            paperMergeData.setEiDataId(paperData.getId());
            if(paperMergeData.getInSci()!=1){
                String formatStr = OperationExcel.dealEiArticle(paperData,null,null,null,null);
                paperMergeData.setFormatStr(formatStr);
            }
        }
        if(type.toLowerCase().contains("cnki")) {
            paperMergeData.setInCnki(1);
            paperMergeData.setCnkiDown(paperData.getDownNum()<0?0:paperData.getDownNum());
            paperMergeData.setCnkiRefer(paperData.getCiteNum()<0?0:paperData.getCiteNum());
            paperMergeData.setCnkiDataId(paperData.getId());
            String formatStr = OperationExcel.dealCnkiArticle(paperData,null,null,null,null);
            paperMergeData.setFormatStr(formatStr);
        }
        return paperMergeData;
    }
}
