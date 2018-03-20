package common.service;

import common.mapper.AuthorMapper;
import common.pojo.AuthorData;
import common.pojo.InquireInfoData;
import org.apache.log4j.Logger;

import java.util.List;

public class AuthorService {

    private static Logger logger = Logger.getLogger(AuthorService.class);
    private AuthorMapper authorMapper;

    public AuthorMapper getAuthorMapper() {
        return authorMapper;
    }

    public void setAuthorMapper(AuthorMapper authorMapper) {
        this.authorMapper = authorMapper;
    }


    public void save(InquireInfoData inquireInfoData){
        authorMapper.insert(inquireInfoData);
    }

    public List<AuthorData> getAllDatas(InquireInfoData inquireInfoData){
        return authorMapper.findAllMergeDatas(inquireInfoData);
    }

    public void saveMerge(InquireInfoData inquireInfoData){
        try {
            authorMapper.insertMerge(inquireInfoData);
        }catch (Exception e){
            logger.error(e.getMessage()+"data:"+inquireInfoData.toString());
            e.printStackTrace();
            System.exit(-1);

        }
    }
    public List<AuthorData> getAllMergeDatas(InquireInfoData inquireInfoData){
        return authorMapper.findAllMergeDatas(inquireInfoData);
    }


}
