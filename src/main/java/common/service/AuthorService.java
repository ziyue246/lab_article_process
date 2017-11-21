package common.service;

import common.mapper.AuthorMapper;
import common.pojo.AuthorData;
import common.pojo.InquireInfoData;

import java.util.List;

public class AuthorService {

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
        return authorMapper.findAllDatas(inquireInfoData);
    }

}
