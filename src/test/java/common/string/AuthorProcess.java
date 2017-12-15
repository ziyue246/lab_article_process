package common.string;

import common.pojo.AuthorData;
import common.system.AppContext;
import common.system.FileOperation;
import common.system.StringProcess;
import common.system.Systemconfig;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AuthorProcess {

    @Test
    public void getAuthor_institutionTableInsertSql(){



        //AppContext.initial();
        String[]names = FileOperation.read("D:\\Users\\Administrator\\ideaworkspace\\dataProcess\\lab_article_process\\src\\test\\resources\\file\\authorId").split("\n");

        Map<String,Integer> nameMap = new HashMap<String, Integer>();


        final String sqlModel = "INSERT INTO author_institution(author_id,institution_id,group_id) " +
                "VALUES (<authorId>,<instiId>,<groupId>);";
        for(String name:names){
            String idStr = name.split("#")[0].trim();
            name = name.split("#")[1].trim();
            int id = Integer.parseInt(idStr);
            nameMap.put(name,id);
        }

        names = FileOperation.read("D:\\Users\\Administrator" +
                "\\ideaworkspace\\dataProcess\\lab_article_process\\src" +
                "\\test\\resources\\file\\author_fz").split("\n");//id=1



        int groupId = 0;
        for(String name:names){
            name = name.replace("\\s+","").trim();
            if(name.startsWith("#"))continue;
            if(name.startsWith("@")){
                groupId = StringProcess.str2Int(name.replace("@",""));
                continue;
            }
            //System.out.println(name);
            int authorId = nameMap.get(name);

            int instiId=1;

            String sql  = sqlModel.replace("<authorId>",""+authorId)
                    .replace("<instiId>","1")
                    .replace("<groupId>",""+groupId);
            System.out.println(sql);

        }


        names = FileOperation.read("D:\\Users\\Administrator" +
                "\\ideaworkspace\\dataProcess\\lab_article_process\\src" +
                "\\test\\resources\\file\\author_qd").split("\n");//id=2

        for(String name:names){
            name = name.trim();
            if(name.startsWith("#"))continue;
            int authorId = nameMap.get(name);
            //System.out.println(name+":"+authorId);
            int instiId=2;

            String sql  = sqlModel.replace("<authorId>",""+authorId)
                    .replace("<instiId>","2").replace("<groupId>",""+groupId);
            //System.out.println(sql);

        }


    }

}
