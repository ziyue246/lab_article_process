package common;

import common.process.author.merge.AuthorMergeProcess;
import common.process.institution.merge.InstitutionMergeProcess;
import common.process.paper.PaperMergeProcess;
import common.process.relation.PaperAuthorProcess;
import common.system.AppContext;
import org.apache.log4j.Logger;


/**
 * Created by ziyue on 2017/12/15
 */
public class StartMain {

    private static Logger logger = Logger.getLogger(StartMain.class);


    public static void main(String []args) throws Exception{

        AppContext.initial();


//        InstitutionMergeProcess instiMergeProcess = new InstitutionMergeProcess();
//        instiMergeProcess.process();
//        AuthorMergeProcess authorMergeProcess = new AuthorMergeProcess();
//        authorMergeProcess.process();
        PaperMergeProcess paperMergeProcess = new PaperMergeProcess();
        paperMergeProcess.process();
//        PaperAuthorProcess paperAuthorProcess = new PaperAuthorProcess();
//        paperAuthorProcess.process();

        logger.info("Over...");
    }

}
