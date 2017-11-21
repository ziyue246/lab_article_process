package common;

import common.pojo.InquireInfoData;
import common.pojo.PaperData;
import common.process.author.AuthorProcess;
import common.process.author.CnkiAuthorProcess;
import common.process.author.EiAuthorProcess;
import common.process.author.SciAuthorProcess;
import common.process.institution.CnkiInstitutionProcess;
import common.process.institution.EiInstitutionProcess;
import common.process.institution.InstitutionProcess;
import common.system.AppContext;
import common.system.Systemconfig;
import org.apache.log4j.Logger;

import java.util.List;

public class StartMain {

    private static Logger logger = Logger.getLogger(StartMain.class);


    public static void main(String []args) throws Exception{

        AppContext.initial();
        InquireInfoData inquireInfoData = new InquireInfoData();



        boolean eiProcess=false;
        if(eiProcess) {

            inquireInfoData.setTableName("ei_data");
            List<PaperData> paperDataList = Systemconfig.paperService.getAllDatas(inquireInfoData);
            EiInstitutionProcess eiInstitution = new EiInstitutionProcess();
            eiInstitution.process(paperDataList, "####");
            eiInstitution.extractAndSave(paperDataList, "institution_ei");

            EiAuthorProcess eiAuthorProcess = new EiAuthorProcess();
            eiAuthorProcess.process(paperDataList,";");
            eiAuthorProcess.extractAndSave(paperDataList,"author_ei");
        }



        boolean sciProcess=false;
        if(sciProcess) {
            inquireInfoData.setTableName("sci_data");
            List<PaperData> paperDataList = Systemconfig.paperService.getAllDatas(inquireInfoData);

            InstitutionProcess institution = new InstitutionProcess();
            institution.process(paperDataList, ";");
            institution.extractAndSave(paperDataList, "institution_sci");

            SciAuthorProcess sciAuthorProcess = new SciAuthorProcess();
            sciAuthorProcess.process(paperDataList,";");
            sciAuthorProcess.extractAndSave(paperDataList,"author_sci");
        }

        boolean cnkiProcess=true;
        if(cnkiProcess) {
            inquireInfoData.setTableName("cnki_data");
            List<PaperData> paperDataList = Systemconfig.paperService.getAllDatas(inquireInfoData);

            CnkiInstitutionProcess cnkiInstitution = new CnkiInstitutionProcess();
            cnkiInstitution.process(paperDataList, ";");
            cnkiInstitution.extractAndSave(paperDataList, "institution_cnki");

            CnkiAuthorProcess cnkiAuthorProcess = new CnkiAuthorProcess();
            cnkiAuthorProcess.process(paperDataList,";");
            cnkiAuthorProcess.extractAndSave(paperDataList,"author_cnki");

        }
    }

}
