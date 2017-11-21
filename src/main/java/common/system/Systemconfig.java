package common.system;

import common.service.AuthorService;
import common.service.InstitutionService;
import common.service.PaperService;

public class Systemconfig {


    public static PaperService paperService;

    public static InstitutionService institutionService;
    public static AuthorService authorService;


    public void initial(){

    }

    public PaperService getPaperService() {
        return paperService;
    }

    public void setPaperService(PaperService paperService) {
        Systemconfig.paperService = paperService;
    }


    public InstitutionService getInstitutionService() {
        return institutionService;
    }

    public void setInstitutionService(InstitutionService institutionService) {
        Systemconfig.institutionService = institutionService;
    }


    public AuthorService getAuthorService() {
        return authorService;
    }

    public void setAuthorService(AuthorService authorService) {
        Systemconfig.authorService = authorService;
    }
}
