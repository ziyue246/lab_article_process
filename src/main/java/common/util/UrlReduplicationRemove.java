package common.util;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import common.system.Systemconfig;

/**
 * URL容器初始化和判重过滤，系统启动后完成
 *
 * @author rzy
 * @since 2018.3
 */
public class UrlReduplicationRemove {
    private Logger logger = Logger.getLogger(UrlReduplicationRemove.class);
    private static BloomFilter<String> bloomFilters;

    /**
     * 过滤url容器初始化操作
     */
    public UrlReduplicationRemove() {
        try {
            logger.info("init bloom filter…");
            iniUrlsBloomFilter();
            logger.info("init bloom filter success！");
        } catch (UnsupportedEncodingException e) {
            logger.error("init bloom filter failed, system will exit,", e);
            System.exit(-1);
        }
    }

    private void iniUrlsBloomFilter() throws UnsupportedEncodingException {
        Map<String, List<String>> map = new HashMap<String, List<String>>();
        int size = 0;

        size = getQueryList(map);// 获得相应的list列表
        int t = 800000;
        int k = 10;
        int n = size * k < t ? t : size * k;// 保证在8K万以上
        double error = 0.0001d;
        int m = new Double(Math.log(error) * n / (Math.log(1d - Math.pow(Math.E, -0.6d)) * 0.6)).intValue();
        logger.info("init bloomFilter" + m + "  " + n + " table data size:" + size);
        bloomFilters = new BloomFilter<String>(m, n);

        List<String> repeatList = new ArrayList<String>();
        for (Map.Entry<String, List<String>> ma : map.entrySet()) {
            repeatList.clear();
            Iterator<String> iter = ma.getValue().iterator();
            while (iter.hasNext()) {
                String s = (String) iter.next();
                if (bloomFilters.contains(s)) {
                    repeatList.add(s);
                    logger.error("error :  " + s + " is repeat data！");// 删除重复url？！
                } else {
                    bloomFilters.add(s);
                }
            }
            deleteUrl(repeatList, ma.getKey());// 删除重复URL
            ma.getValue().clear();
        }
        map.clear();
    }

    // 获得数据表中的url数据
    private int getQueryList(Map<String, List<String>> map) {
        //return Systemconfig.dbService.getAllMd5(Systemconfig.table, map);
        return 0 ;
    }

    // 删除重复的url
    private void deleteUrl(List<String> url, String table) {
        if (url.size() > 0) logger.error(table + "表中的重复数据将被删除！");
        if (table.contains("ebusiness")) table = table.replace("ebusiness", "eb");
        //Systemconfig.dbService.deleteReduplicationUrls(url, table);
    }

    /**
     * 判断新加入数据表中的一个url列表中的url重复与否，返回不包含重复url的url列表 2013.6修改
     *
     * @param list 用于判断重复与否的url列表
     * @return 非重复url列表
     */
    public synchronized List<String> filterUrls(List<String> list) {
        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()) {
            if (!checkNoRepeat(iterator.next())) {
                iterator.remove();
            }
        }
        return list;
    }

    /**
     * 用于判断新加入数据表中的唯一标识（MD5）重复与否
     *
     * @param MD5 用于判断重复与否的参数
     * @return true：非重复MD5；false：重复MD5
     */
    public synchronized boolean checkNoRepeat(String MD5) {
        if (MD5 == null) return false;
        try {
            if (!bloomFilters.contains(MD5)) {
                bloomFilters.add(MD5);
                return true;
            }
        } catch (UnsupportedEncodingException e) {
            logger.error("", e);
        }
        return false;
    }

    public BloomFilter<String> getBloomFilters() {
        return bloomFilters;
    }

    /**
     * 删除过期MD5
     *
     * @param md5
     * @return
     */
    public synchronized void deleteOutUrl(String md5) {
        if (md5 == null) return;
        try {
            if (bloomFilters.contains(md5)) {
                bloomFilters.delete(md5);
            }
        } catch (UnsupportedEncodingException e) {
            logger.error("", e);
        }
    }

    public void deleteOutUrls(List<String> urls) {
        Iterator<String> iterator = urls.iterator();
        while (iterator.hasNext()) {
            deleteOutUrl(iterator.next());
        }
    }

}