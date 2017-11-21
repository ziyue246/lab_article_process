package common.analysis;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class Analysis {


    @Test
    public void test() {
        String a = "CAS Center for Excellence in Brain Science and Intelligence Technology (CEBSIT)";
        String b = "CAS Center for Excellence in Brain Science and Intelligence Technology";

        System.out.println(EnAnalysis.getSimilarity(a, b));
        a = "Dipartimento di Elettronica e Informazione";
        b = "Dipartimento di Ingegneria dell'Informazione";

        System.out.println(EnAnalysis.getSimilarity(a, b));
        a = "Indiana Purdue University";
        b = "Indiana State University";
        System.out.println(EnAnalysis.getSimilarity(a, b));


        a = "CAS Center for Excellence in Brain Science and Intelligence Technology";
        b = "CAS Center for Excellence in Brain Science and tttelligence Technology";

        System.out.println(EnAnalysis.getSimilarity(a, b));
        a = "Xi'an Jiaotong University";
        b = "Xia'an Jiaotong University";

        System.out.println(EnAnalysis.getSimilarity(a, b));

        a = "Airel Ltd.";
        b = "Airel Ltd";

        System.out.println(EnAnalysis.getSimilarity(a, b));
        a = "Liu,Shennmg";
        b = "Liu, Shennm";

        System.out.println(EnAnalysis.getSimilarity(a, b));
    }
    @Test
    public void test1() {
        String a = "复杂系统管理与智能控制国家重点实验室";
        String b = "复杂系统管理与控制国家重点实验室";

        System.out.println(ZhAnalysis.getSimilarity(a, b));

        a = "北京大学";
        b = "北京科技大学";

        System.out.println(ZhAnalysis.getSimilarity(a, b));

        a = "北京大学";
        b = "北京大学医学院";

        System.out.println(ZhAnalysis.getSimilarity(a, b));

        a = "北京大学";
        b = "北京大学院";

        System.out.println(ZhAnalysis.getSimilarity(a, b));

        a = "北京大学";
        b = "北京大学北京大学北京大学北京大学";

        System.out.println(ZhAnalysis.getSimilarity(a, b));
    }
}
