package common.service;

import common.pojo.InquireInfoData;
import common.pojo.PaperData;
import common.pojo.PeriodData;
import common.system.AppContext;
import common.system.Systemconfig;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.List;

public class Service {



    @Test
    public void test() throws Exception{

        AppContext.initial();

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        PeriodData date = new PeriodData();

        date.setStart(format.parse("2017-01-01"));
        date.setEnd(format.parse("2018-01-01"));


        InquireInfoData inquireInfoData = new InquireInfoData();

        inquireInfoData.setTableName("ei_data");
        List<PaperData> paperDataList = Systemconfig.paperService.getAllDatas(inquireInfoData);


        for(PaperData paperData:paperDataList){
            System.out.println(paperData);
        }

    }

}
