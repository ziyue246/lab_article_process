package common.process.paper;

import common.analysis.EnAnalysis;
import common.analysis.ZhAnalysis;
import common.pojo.*;
import common.system.OperationExcel;
import common.system.Systemconfig;
import org.apache.log4j.Logger;

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

        // sci is main category
        // when one paper both in sci and ei, save sci massage
        for(PaperData paperData:sciPaperDataList){
            //paperMergeDataList.add(paperData2paperMergeData(paperData,"sci"));
            enPaperMerge(paperMergeDataList,paperData,"sci");
        }
        for(PaperData paperData:eiPaperDataList){
            enPaperMerge(paperMergeDataList,paperData,"ei");
        }
        for(PaperData paperData:cnkiPaperDataList){
            //paperMergeDataList.add(paperData2paperMergeData(paperData,"cnki"));
            zhPaperMerge(paperMergeDataList,paperData,"cnki");
        }

        int updateCount=0,insertCount=0;
        for(PaperMergeData paperMergeData:paperMergeDataList){
            if(paperMergeData.getDbStatus().equals(DbDataOpreration.INSERT)) {
                Systemconfig.paperService.saveMergeData(paperMergeData);
                ++insertCount;
            }else if(paperMergeData.getDbStatus().equals(DbDataOpreration.UPDATE)) {
                Systemconfig.paperService.updateMergeData(paperMergeData);
                ++updateCount;
            }else{

            }
        }
        logger.info("all paper merge data size:"+ paperMergeDataList.size());
        logger.info("insert paper merge data size:"+ insertCount);
        logger.info("update paper merge data size:"+ updateCount);
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
                if(!paperMergeData.getDbStatus().equals(DbDataOpreration.INSERT)){
                    paperMergeData.setDbStatus(DbDataOpreration.UPDATE);
                }

                if(type.toLowerCase().contains("sci")) {
                    paperMergeData.setInSci(1);
                    paperMergeData.setSciDown(paperData.getDownNum());
                    paperMergeData.setSciRefer(paperData.getCiteNum());
                    paperMergeData.setSciDataId(paperData.getId());
                    paperMergeDataUpdate(paperMergeData,paperData, EnumType.DBData.SCI);
                    String formatStr = OperationExcel.dealSciArticle(paperData,null,null,null,null);
                    paperMergeData.setFormatStr(formatStr);
                }
                if(type.toLowerCase().contains("ei")) {
                    paperMergeData.setInEi(1);
                    paperMergeData.setEiDown(paperData.getDownNum());
                    paperMergeData.setEiRefer(paperData.getCiteNum());
                    paperMergeData.setEiDataId(paperData.getId());
                    paperMergeDataUpdate(paperMergeData,paperData, EnumType.DBData.EI);
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


    private void  zhPaperMerge(List<PaperMergeData> paperMergeDataList,PaperData paperData,String type){
        for(PaperMergeData paperMergeData:paperMergeDataList){
            if(paperMergeData.getInCnki()!=1){
                continue;
            }
            double titleSimilarity    = ZhAnalysis.getSimilarity(paperMergeData.getTitle(),paperData.getTitle());
            //double authorSimilarity         = EnAnalysis.getSimilarity(paperMergeData.getAuthors(),paperData.getAuthor());
            //double institutionSimilarity    = EnAnalysis.getSimilarity(paperMergeData.getInstitutions(),paperData.getAddress());

            if(titleSimilarity>0.95){
                if(!paperMergeData.getDbStatus().equals(DbDataOpreration.INSERT)){
                    paperMergeData.setDbStatus(DbDataOpreration.UPDATE);
                }

                if(type.toLowerCase().contains("cnki")) {
                    paperMergeData.setInCnki(1);
                    paperMergeData.setCnkiDown(paperData.getDownNum()<0?0:paperData.getDownNum());
                    paperMergeData.setCnkiRefer(paperData.getCiteNum()<0?0:paperData.getCiteNum());
                    paperMergeData.setCnkiDataId(paperData.getId());
                    paperMergeDataUpdate(paperMergeData,paperData, EnumType.DBData.CNKI);
                    String formatStr = OperationExcel.dealCnkiArticle(paperData,null,null,null,null);
                    paperMergeData.setFormatStr(formatStr);
                }
                return;
            }
        }
        paperMergeDataList.add(paperData2paperMergeData(paperData,type));
    }
    /**
     * paperMergeData field filling
     * @param paperMergeData
     * @param paperData
     */
    private void  paperMergeDataUpdate(PaperMergeData paperMergeData,PaperData paperData,EnumType.DBData dbData){

        if(paperMergeData.getTitle()==null||dbData.equals(EnumType.DBData.SCI))paperMergeData.setTitle(paperData.getTitle());
        if(paperMergeData.getAuthors()==null||dbData.equals(EnumType.DBData.SCI))paperMergeData.setAuthors(paperData.getAuthor());
        if(paperMergeData.getInstitutions()==null||dbData.equals(EnumType.DBData.SCI))paperMergeData.setInstitutions(paperData.getAddress());
        if(paperMergeData.getReprintAuthor()==null||dbData.equals(EnumType.DBData.SCI))paperMergeData.setReprintAuthor(paperData.getReprintAuthor());
        if(paperMergeData.getReprintInstitution()==null||dbData.equals(EnumType.DBData.SCI))paperMergeData.setReprintInstitution(paperData.getReprintInstitution());
        if(paperMergeData.getPubdate()==null||dbData.equals(EnumType.DBData.SCI))paperMergeData.setPubdate(paperData.getPubdate());
        if(paperMergeData.getPublisher()==null||dbData.equals(EnumType.DBData.SCI))paperMergeData.setPublisher(paperData.getPublisher());
        if(paperMergeData.getInsertTime()==null||dbData.equals(EnumType.DBData.SCI))paperMergeData.setInsertTime(paperData.getInsertTime());
        if(paperMergeData.getBrief()==null||dbData.equals(EnumType.DBData.SCI))paperMergeData.setBrief(paperData.getBrief());
        if(paperMergeData.getJournal()==null||dbData.equals(EnumType.DBData.SCI))paperMergeData.setJournal(paperData.getJournal());
        if(paperMergeData.getKeywords()==null||dbData.equals(EnumType.DBData.SCI))paperMergeData.setKeywords(paperData.getKeywords());
        if(paperMergeData.getFund()==null||dbData.equals(EnumType.DBData.SCI))paperMergeData.setFund(paperData.getFund());
        if(paperMergeData.getVolume()==null||dbData.equals(EnumType.DBData.SCI))paperMergeData.setVolume(paperData.getVolume());
        if(paperMergeData.getIssue()==null||dbData.equals(EnumType.DBData.SCI))paperMergeData.setIssue(paperData.getIssue());
        if(paperMergeData.getPageCode()==null||dbData.equals(EnumType.DBData.SCI))paperMergeData.setPageCode(paperData.getPageCode());
        if(paperMergeData.getDoi()==null||dbData.equals(EnumType.DBData.SCI))paperMergeData.setDoi(paperData.getDoi());
        if(paperMergeData.getImpactFactor2year()<=0||dbData.equals(EnumType.DBData.SCI))paperMergeData.setImpactFactor2year(paperData.getImpactFactor2year());
        if(paperMergeData.getImpactFactor5year()<=0||dbData.equals(EnumType.DBData.SCI))paperMergeData.setImpactFactor5year(paperData.getImpactFactor5year());
        if(paperMergeData.getJcr()==null||dbData.equals(EnumType.DBData.SCI))paperMergeData.setJcr(paperData.getJcr());
        if(paperMergeData.getSourceTitle()==null||dbData.equals(EnumType.DBData.SCI))paperMergeData.setSourceTitle(paperData.getSourceTitle());
        if(paperMergeData.getConferenceDate()==null||dbData.equals(EnumType.DBData.SCI))paperMergeData.setConferenceDate(paperData.getConferenceDate());
        if(paperMergeData.getConferenceLocation()==null||dbData.equals(EnumType.DBData.SCI))paperMergeData.setConferenceLocation(paperData.getConferenceLocation());
        if(paperMergeData.getCategory()==null||dbData.equals(EnumType.DBData.SCI))paperMergeData.setCategory(paperData.getCategory());
        if(paperMergeData.getCategoryCode()==null||dbData.equals(EnumType.DBData.SCI))paperMergeData.setCategoryCode(paperData.getCategoryCode());

        //        -1:null()
        //        0:other()
        //        1:Conference article (CA)
        //        2:Journal article (JA)
        //        3:Article in Press
        //        4:Book chapter (CH)
        //        5:Editorial (ED)
        //        6:Book (BK)
        if(dbData.equals(EnumType.DBData.EI)) {
            if (paperData.getDocumentType() == null) {
                paperMergeData.setDocumentType(-1);
            } else if (paperData.getDocumentType().contains("CA")) {
                paperMergeData.setDocumentType(1);
            } else if (paperData.getDocumentType().contains("JA")) {
                paperMergeData.setDocumentType(2);
            } else if (paperData.getDocumentType().contains("Article in Press")) {
                paperMergeData.setDocumentType(3);
            } else if (paperData.getDocumentType().contains("CH")) {
                paperMergeData.setDocumentType(4);
            } else if (paperData.getDocumentType().contains("ED")) {
                paperMergeData.setDocumentType(5);
            } else if (paperData.getDocumentType().contains("BK")) {
                paperMergeData.setDocumentType(6);
            } else {
                paperMergeData.setDocumentType(0);
            }
        }
    }
    /**
     *
     * @param paperData
     * @param type
     * @return
     */
    private PaperMergeData  paperData2paperMergeData(PaperData paperData,String type){
        if(paperData==null)return null;
        PaperMergeData paperMergeData = new PaperMergeData();


        paperMergeData.setDbStatus(DbDataOpreration.INSERT);


        if(type.toLowerCase().contains("sci")) {
            paperMergeData.setInSci(1);
            paperMergeData.setSciDown(paperData.getDownNum()<0?0:paperData.getDownNum());
            paperMergeData.setSciRefer(paperData.getCiteNum()<0?0:paperData.getCiteNum());
            paperMergeData.setSciDataId(paperData.getId());

            paperMergeDataUpdate(paperMergeData,paperData, EnumType.DBData.SCI);
            String formatStr = OperationExcel.dealSciArticle(paperData,null,null,null,null);
            paperMergeData.setFormatStr(formatStr);
        }
        if(type.toLowerCase().contains("ei")) {
            paperMergeData.setInEi(1);
            paperMergeData.setEiDown(paperData.getDownNum()<0?0:paperData.getDownNum());
            paperMergeData.setEiRefer(paperData.getCiteNum()<0?0:paperData.getCiteNum());
            paperMergeData.setEiDataId(paperData.getId());
            paperMergeDataUpdate(paperMergeData,paperData, EnumType.DBData.EI);
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
            paperMergeDataUpdate(paperMergeData,paperData, EnumType.DBData.CNKI);
            String formatStr = OperationExcel.dealCnkiArticle(paperData,null,null,null,null);
            paperMergeData.setFormatStr(formatStr);
        }
        return paperMergeData;
    }
}
