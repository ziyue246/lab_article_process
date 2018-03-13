package common.system;

import common.pojo.PaperAuthorInstiData;
import common.service.AuthorService;
import common.service.CommonService;
import common.service.InstitutionService;
import common.service.PaperService;
import common.util.UrlReduplicationRemove;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Systemconfig {


    public static Map<String,List<String>> escapeCharacterMap;

    public static PaperService paperService;
    public static InstitutionService institutionService;
    public static AuthorService authorService;
    public static CommonService commonService;
    public static UrlReduplicationRemove urm;



    public void initial(){
        getEscapeCharacterMap();
        md5AddBloomFilter();
    }


    private void md5AddBloomFilter(){

        List<PaperAuthorInstiData> paperAuthorInstiDataList = paperService.getPaperAuthorDatas();
        for(PaperAuthorInstiData data:paperAuthorInstiDataList){
            String md5 = MD5Util.MD5(data.getPaperId()+"#"+data.getAuthorNameId()+"#"+data.getAuthorType());
            urm.checkNoRepeat(md5);
        }
        paperAuthorInstiDataList = paperService.getPaperAuthorInstiDatas();
        for(PaperAuthorInstiData data:paperAuthorInstiDataList){
            String md5 = MD5Util.MD5(data.getPaperId()
                    +"#"+data.getAuthorNameId()+"#"+data.getInstitutionId()+"#"+data.getAuthorType());
            urm.checkNoRepeat(md5);
        }
        paperAuthorInstiDataList = paperService.getPaperInstiDatas();
        for(PaperAuthorInstiData data:paperAuthorInstiDataList){
            String md5 = MD5Util.MD5(data.getPaperId()+"#"+data.getInstitutionId()+"#"+data.getAuthorType());
            urm.checkNoRepeat(md5);
        }
    }

    private void getEscapeCharacterMap(){
        escapeCharacterMap = new HashMap<String, List<String>>();

        String content = FileOperation.read("src/main/resources/config/EnEscapeCharacter");
        if(content==null||content.length()==0)return ;

        String []lines = content.split("\n");
        for (String line:lines){
            line=line.trim();
            String strKey   = line.split("#")[0];
            String strValue = line.split("#")[1];
            if(escapeCharacterMap.get(strKey)==null){
                escapeCharacterMap.put(strKey,new ArrayList<String>());
            }
            escapeCharacterMap.get(strKey).add(strValue);
        }
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


    public CommonService getCommonService() {
        return commonService;
    }

    public void setCommonService(CommonService commonService) {
        Systemconfig.commonService = commonService;
    }
}
