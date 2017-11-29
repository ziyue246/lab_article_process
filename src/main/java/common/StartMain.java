package common;

import common.process.author.merge.AuthorMergeProcess;
import common.process.institution.merge.InstitutionMergeProcess;
import common.system.AppContext;
import org.apache.log4j.Logger;

public class StartMain {

    private static Logger logger = Logger.getLogger(StartMain.class);


    public static void main(String []args) throws Exception{

        AppContext.initial();


        //InstitutionMergeProcess instiMergeProcess = new InstitutionMergeProcess();
        //instiMergeProcess.process();



        AuthorMergeProcess authorMergeProcess = new AuthorMergeProcess();
        authorMergeProcess.process();


    }

}
