package common.analysis;

import common.pojo.AuthorData;
import common.pojo.InquireInfoData;
import common.system.AppContext;
import common.system.FileOperation;
import common.system.StringProcess;
import common.system.Systemconfig;
import org.junit.Test;

import java.util.List;

public class Author {


    @Test
    public void test(){


        AppContext.initial();
        String[]names =FileOperation.read("src/main/resources/baseData/author").split("\n");

        for(String name:names){
            if(name.startsWith("#"))continue;

            AuthorData authorData = zhNameStr2Author(name);

            InquireInfoData inquireInfoData = new InquireInfoData();
            inquireInfoData.setTableName("author");
            inquireInfoData.setAuthorData(authorData);

            System.out.println(authorData.getName());
            Systemconfig.authorService.saveMerge(inquireInfoData);

        }

    }

    public AuthorData zhNameStr2Author(String name){
        AuthorData author = new AuthorData();

        String firstName = name.substring(1);
        String lastName = name.substring(0,1);


        int firstNamelen= firstName.length();
        String abbFirstName="";
        for(int i=0;i<firstNamelen;++i){
            abbFirstName+=StringProcess.toHanyuPinyin(
                    firstName.substring(i,i+1)).substring(0,1).toUpperCase();
        }


        author.setZhName(name);
        author.setZhFirstName(firstName);
        author.setZhLastName(lastName);


        firstName = StringProcess.toHanyuPinyin(firstName);
        lastName = StringProcess.toHanyuPinyin(lastName);

        firstName = StringProcess.upCaseFirstLetter(firstName);
        lastName = StringProcess.upCaseFirstLetter(lastName);

        author.setEnFirstName(firstName);
        author.setEnLastName(lastName);
        author.setEnName(firstName+","+lastName);
        author.setName(firstName+","+lastName);
        author.setEnFirstNameShort(abbFirstName);

        return author;
    }


}
