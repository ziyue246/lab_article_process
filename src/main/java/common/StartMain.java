package common;

import common.system.AppContext;
import org.apache.log4j.Logger;

public class StartMain {

    private static Logger logger = Logger.getLogger(StartMain.class);


    public static void main(String []args) throws Exception{

        AppContext.initial();




//        InquireInfoData inquireInfoData = new InquireInfoData();
//
//
//
//        boolean eiProcess=false;
//        List<PaperData> eiPaperDataList = Systemconfig.paperService.getAllDatas(inquireInfoData);
//
//            inquireInfoData.setTableName("ei_data");
//
//            EiInstitutionProcess eiInstitution = new EiInstitutionProcess();
//            eiInstitution.process(eiPaperDataList, "####");
//            eiInstitution.extractAndSave(eiPaperDataList, "institution_ei");
//
//            EiAuthorProcess eiAuthorProcess = new EiAuthorProcess();
//            eiAuthorProcess.process(eiPaperDataList,";");
//            eiAuthorProcess.extractAndSave(eiPaperDataList,"author_ei");
//
//
//
//        boolean sciProcess=false;
//        List<PaperData> sciPaperDataList = Systemconfig.paperService.getAllDatas(inquireInfoData);
//
//        inquireInfoData.setTableName("sci_data");
//
//
//        InstitutionProcess institution = new InstitutionProcess();
//        institution.process(sciPaperDataList, ";");
//        institution.extractAndSave(sciPaperDataList, "institution_sci");
//
//        SciAuthorProcess sciAuthorProcess = new SciAuthorProcess();
//        sciAuthorProcess.process(sciPaperDataList,";");
//        sciAuthorProcess.extractAndSave(sciPaperDataList,"author_sci");
//
//
//
//        List<PaperData> cnkiPaperDataList = Systemconfig.paperService.getAllDatas(inquireInfoData);
//
//        inquireInfoData.setTableName("cnki_data");
//
//
//        CnkiInstitutionProcess cnkiInstitution = new CnkiInstitutionProcess();
//        cnkiInstitution.process(cnkiPaperDataList, ";");
//        cnkiInstitution.extractAndSave(cnkiPaperDataList, "institution_cnki");
//
//        CnkiAuthorProcess cnkiAuthorProcess = new CnkiAuthorProcess();
//        cnkiAuthorProcess.process(cnkiPaperDataList,";");
//        cnkiAuthorProcess.extractAndSave(cnkiPaperDataList,"author_cnki");


    }

}
