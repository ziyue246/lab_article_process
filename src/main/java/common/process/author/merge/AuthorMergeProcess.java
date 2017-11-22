package common.process.author.merge;

import common.analysis.EnAnalysis;
import common.pojo.AuthorData;
import common.pojo.InquireInfoData;
import common.pojo.InstitutionData;
import common.pojo.PaperData;
import common.process.author.single.CnkiAuthorProcess;
import common.process.author.single.EiAuthorProcess;
import common.process.author.single.SciAuthorProcess;
import common.system.StringProcess;
import common.system.Systemconfig;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class AuthorMergeProcess {

    private static Logger logger = Logger.getLogger(AuthorMergeProcess.class);

    public void process(List<PaperData> paperDataList, String splitMain){
        InquireInfoData inquireInfoData = new InquireInfoData();

        List<PaperData> eiPaperDataList = Systemconfig.paperService.getAllDatas(inquireInfoData);

        inquireInfoData.setTableName("ei_data");
        EiAuthorProcess eiAuthorProcess = new EiAuthorProcess();
        eiAuthorProcess.process(eiPaperDataList,";");
        eiAuthorProcess.extractAndSave(eiPaperDataList,"author_ei");


        List<PaperData> sciPaperDataList = Systemconfig.paperService.getAllDatas(inquireInfoData);

        inquireInfoData.setTableName("sci_data");

        SciAuthorProcess sciAuthorProcess = new SciAuthorProcess();
        sciAuthorProcess.process(sciPaperDataList,";");
        sciAuthorProcess.extractAndSave(sciPaperDataList,"author_sci");

        List<PaperData> cnkiPaperDataList = Systemconfig.paperService.getAllDatas(inquireInfoData);

        inquireInfoData.setTableName("cnki_data");

        CnkiAuthorProcess cnkiAuthorProcess = new CnkiAuthorProcess();
        cnkiAuthorProcess.process(cnkiPaperDataList,";");
        cnkiAuthorProcess.extractAndSave(cnkiPaperDataList,"author_cnki");
    }
}
