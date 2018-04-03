package common.analysis;

import common.system.FileOperation;
import common.system.Systemconfig;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class EnAnalysis {

    private static Logger logger = Logger.getLogger(EnAnalysis.class);

    public static double getSimilarity(String aStr,String bStrs) {


        double maxSimilarity = getOneStrSimilarity(aStr,bStrs);
        if(bStrs.contains(";")){
            String []strs = bStrs.split(";");
            for(String str:strs){
                double similarity_tmp = getOneStrSimilarity(aStr,str.trim());
                if(similarity_tmp>maxSimilarity){
                    maxSimilarity=similarity_tmp;
                }
            }
        }
        return maxSimilarity;
    }

    public static double getOneStrSimilarity(String aStr,String bStr){
        aStr=aStr.replace("é","e");
        bStr=bStr.replace("é","e");
        double maxSimilarity = getOneSimilarity(aStr,bStr);
        Set<String> keySet = Systemconfig.escapeCharacterMap.keySet();
        for(String key:keySet){
            if(aStr.contains(key)||bStr.contains(key)){

                logger.info("before escapeCharacter: aStr"+aStr+"\tbStr:"+bStr);

                List<String> values = Systemconfig.escapeCharacterMap.get(key);
                for(String value:values){

                    String aStr_tmp = aStr.replace(key,value);
                    String bStr_tmp = bStr.replace(key,value);
                    logger.info("after escapeCharacter: aStr"+aStr_tmp+"\tbStr:"+bStr_tmp);

                    double maxSimilarity_tmp =  getOneSimilarity(aStr_tmp,bStr_tmp);
                    if(maxSimilarity_tmp>maxSimilarity){
                        maxSimilarity=maxSimilarity_tmp;
                    }
                }

            }
        }
        return maxSimilarity;
    }

    public static double getOneSimilarity(String aStr,String bStr){

        int[]aStati = statisticsLetters(aStr);
        int[]bStati = statisticsLetters(bStr);
        if(aStati==null||bStati==null)return 0.0;

        int diff=0;
        for(int i=0;i<aStati.length;i++){
            diff+=Math.abs(aStati[i]-bStati[i]);
        }
        int aSum=0;
        int bSum=0;
        for(int i=0;i<aStati.length;i++){
            aSum+=aStati[i];
            bSum+=bStati[i];
        }
        return  (1.0*(aSum+bSum)/2.0-diff)/(1.0*(aSum+bSum)/2.0);
    }

    private static int[] statisticsLetters(String aStr){
        if(aStr==null||aStr.length()==0)return null;
        char[] aChs = aStr.toLowerCase().toCharArray();


        int [] stati = new int[26];
        for(int i=0;i<stati.length;i++){
            stati[i]=0;
        }
        for(char ch:aChs){
            if(ch>='a'&&ch<='z') {
                int index = ch - 'a';
                ++stati[index];
            }
        }
        return stati;
    }


}
