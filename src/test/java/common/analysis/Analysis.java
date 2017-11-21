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
        String a = "����ϵͳ���������ܿ��ƹ����ص�ʵ����";
        String b = "����ϵͳ��������ƹ����ص�ʵ����";

        System.out.println(ZhAnalysis.getSimilarity(a, b));

        a = "������ѧ";
        b = "�����Ƽ���ѧ";

        System.out.println(ZhAnalysis.getSimilarity(a, b));

        a = "������ѧ";
        b = "������ѧҽѧԺ";

        System.out.println(ZhAnalysis.getSimilarity(a, b));

        a = "������ѧ";
        b = "������ѧԺ";

        System.out.println(ZhAnalysis.getSimilarity(a, b));

        a = "������ѧ";
        b = "������ѧ������ѧ������ѧ������ѧ";

        System.out.println(ZhAnalysis.getSimilarity(a, b));
    }
}
