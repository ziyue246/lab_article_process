package common.system;


import common.pojo.PaperData;
import common.util.Pos;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.util.CellRangeAddress;

public class OperationExcel {

    final private static int MONTH_CELL = 0;
    final private static int ARTICLE = 1;
    final private static int AUTHOR_RANK = 2;
    final private static int MARK_ORNOT = 3;
    final private static int FACTOR_2_YEAR = 4;
    final private static int FACTOR_5_YEAR = 5;
    final private static int INSTITUTIONS = 6;
    final private static int JCR = 7;


    private static String dealSciAuthor(String authors){
        authors =  authors.replace("By:","").
                replaceAll("\\(.*?\\)","").
                replaceAll("\\[.*?\\]","");

        String []ss = authors.split(";");

        StringBuffer resultSb= new StringBuffer();
        for(int i=0;i<ss.length;i++){
            String s = ss[i];
            System.out.println(s);
            String first = s.split(",")[0];

            if(i==ss.length-1){
                resultSb.append("and ");
            }
            if(s.contains(",")) {
                char[] last = s.split(",")[1].toCharArray();
                for (char ch : last) {
                    resultSb.append(ch + ". ");
                }
            }
            resultSb.append(first + ", ");
        }
        return resultSb.subSequence(0,resultSb.length()-1).toString();
    }
    private static String dealEiAuthor(String authors){
        try{
            authors =  authors.replaceAll("\\(.*?\\)","").
                    replaceAll("\\[.*?\\]","");
        }catch(Exception e){
            authors = "";
        }

        System.out.println(authors);
        char []initialsChart= {'B','P','M','F','D','T','N','L','G','K','H','J','Q','X','R','Z','C','S','Y','W'};
        Set<Character> initialsChartSet = new HashSet<Character>();
        for(char ch:initialsChart){
            initialsChartSet.add(ch);
        }
        StringBuffer resoultSb = new StringBuffer();
        String []ss = authors.split(";");
        for(int k=0;k<ss.length;k++){
            String s=ss[k];
            String first = s.split(",")[0];
            String last = "";
            try{
                last = s.split(",")[1];
            }catch(Exception e){
                last = "";
            }
            last = last.toUpperCase();
            char[] lastChs = last.toCharArray();
            for(int i=0;i<lastChs.length;++i){
                char ch = lastChs[i];
                if(initialsChartSet.contains(ch)){
                    if(i<lastChs.length-1){
                        if((ch=='Z'||ch=='C'||ch=='S')&&lastChs[i+1]=='H'){
                            resoultSb.append(ch+". ");
                            ++i;
                        }
                        else if(!initialsChartSet.contains(lastChs[i+1])) {
                            resoultSb.append(ch + ". ");
                        }
                    }
                }
            }
            resoultSb.append(first);
            if(k!=ss.length-1){
                resoultSb.append(", ");
            }
        }
        String result = resoultSb.toString();
        System.out.println(result);
        result = result.replace("   "," ").replace("  "," ");
        StringBuilder lastResultSb = new StringBuilder();
        String[] strs = result.split(",");
        for(int i = 0;i < strs.length;i++){
            if(strs.length == 1){
                lastResultSb.append(strs[i]);
                break;
            }
            if(i < strs.length -2){
                lastResultSb.append(strs[i].trim()+", ");
            }else if(i == strs.length -2){
                lastResultSb.append(strs[i].trim());
            } else{
                lastResultSb.append(" and " + strs[i].trim());
            }
        }
        System.out.println(lastResultSb.toString());
        return lastResultSb.toString();
    }

    public static String dealSciArticle(PaperData d , HSSFCell cell, HSSFWorkbook wb,
                                        HSSFFont fo, HSSFCellStyle style){
        String s = dealSciAuthor(d.getAuthor()) + ", \"" + d.getTitle() + ",\" ";
        List<Pos> poslist = new ArrayList<Pos>();
        int startxt=0;
        int endxt=0;
        if (d.getJournal() != null && !d.getJournal().equals("")) {
            startxt=s.length()-1;

            String journals="";
            String []ss_tmp= d.getJournal().split(" ");
            for(String s_tmp:ss_tmp){
                s_tmp = s_tmp.trim().toLowerCase();
                if(s_tmp.equals("of")||s_tmp.equals("and")||s_tmp.equals("on")){
                    journals += s_tmp+" ";
                }else if(s_tmp.equals("ieee")||s_tmp.equals("iet")||
                        s_tmp.equals("ieee-asme")||s_tmp.equals("bmc")||s_tmp.equals("mis")){
                    journals += s_tmp.toUpperCase()+" ";
                }else {
                    journals += s_tmp.substring(0, 1).toUpperCase() + s_tmp.substring(1) + " ";
                }
            }
            s += journals.trim();
            endxt=s.length();
            s +=", ";
        }

        Pos pos = new Pos();
        pos.setStart(s.length());
        s += "vol";
        if (d.getVolume() != null && !d.getVolume().equals("")) {
            s += ". " + d.getVolume() + ", ";
        } else {
            pos.setEnd(s.length());
            poslist.add(pos);
            s += ". , ";
        }

        pos = new Pos();
        pos.setStart(s.length());
        s += "no";
        if (d.getIssue() != null && !d.getIssue().equals("")) {
            s += ". " + d.getIssue() + ", ";
        } else {
            pos.setEnd(s.length());
            poslist.add(pos);
            s += ". , ";
        }

        pos = new Pos();
        pos.setStart(s.length());
        s += "pp";
        if (d.getPageCode() != null && !d.getPageCode().equals("")) {
            s += ". " + d.getPageCode() + ", ";
        } else {
            pos.setEnd(s.length());
            poslist.add(pos);
            s += ". , ";
        }
        pos = new Pos();
        pos.setStart(s.length());
        if (d.getPubdate() != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("MMM. yyyy",
                    Locale.ENGLISH);
            s += sdf.format(d.getPubdate())+".";
        }

        pos = new Pos();
        pos.setStart(s.length());
        s += " DOI";
        if (d.getDoi() != null && !d.getDoi().equals("")) {
            s += ": " + d.getDoi();
        } else {
            pos.setEnd(s.length());
            poslist.add(pos);
            s += ": ";
        }
        s=s.replace(",,",",");


        if(cell==null||wb==null||fo==null||style==null){
            return s;
        }


        HSSFRichTextString rich = new HSSFRichTextString(s);// 文章字段
        HSSFFont f = wb.createFont();
        f.setFontHeightInPoints((short) 12); // 字体高度
        f.setFontName("Times new roman"); // 字体
        f.setColor(Font.COLOR_RED);
        for (Pos p : poslist) {
            rich.applyFont(p.getStart(), p.getEnd(), f);
            rich.applyFont(p.getEnd(), s.length(), fo);
        }

        f.setItalic(true);
        f.setColor(Font.COLOR_NORMAL);
        rich.applyFont(startxt, endxt, f);
        rich.applyFont(endxt, s.length(), fo);
        cell.setCellValue(rich);
        cell.setCellStyle(style);
        return s;
    }


    public static String dealCnkiArticle(PaperData d ,HSSFCell cell,HSSFWorkbook wb,HSSFFont fo,HSSFCellStyle style){

        //"李力;林懿伦;曹东璞;郑南宁;王飞跃"
        String author = d.getAuthor().replace(";",", ");

        String s =  author+ ". " + d.getTitle() + ". ";
        List<Pos> poslist = new ArrayList<Pos>();

        int startxt=0;
        int endxt=0;
        if (d.getSourceTitle() != null && !d.getSourceTitle().equals("")) {
            startxt = s.length();
            s += d.getSourceTitle();
            endxt = s.length();
            s += ", ";
        }
        if (d.getPubtime() != null && !d.getPubtime().equals("")) {
            String pub =d.getPubtime().replace("年",",").replace("期","");
            s += pub+", ";
        }
        if (d.getPageCode() != null && !d.getPageCode().equals("")) {
            s += d.getPageCode()+".";
        }
        if(cell==null||wb==null||fo==null||style==null){
            return s;
        }
        HSSFRichTextString rich = new HSSFRichTextString(s);// 文章字段
        HSSFFont f = wb.createFont();
        f.setFontHeightInPoints((short) 12); // 字体高度
        f.setFontName("Times new roman"); // 字体
        f.setColor(Font.COLOR_RED);
        for (Pos p : poslist) {
            rich.applyFont(p.getStart(), p.getEnd(), f);
            rich.applyFont(p.getEnd(), s.length(), fo);
        }
        f.setItalic(true);
        f.setColor(Font.COLOR_NORMAL);
        rich.applyFont(startxt, endxt, f);
        rich.applyFont(endxt, s.length(), fo);

        cell.setCellValue(rich);
        cell.setCellStyle(style);
        return s;
    }

    public static String dealEiArticle(PaperData d ,HSSFCell cell,HSSFWorkbook wb,HSSFFont fo,HSSFCellStyle style){
        String s = dealEiAuthor(d.getAuthor()) + ", \"" + d.getTitle() + ",\" ";
        List<Pos> poslist = new ArrayList<Pos>();

        int startxt=0;
        int endxt=0;
        if (d.getSourceTitle() != null && !d.getSourceTitle().equals("")) {
            s += "in ";
            startxt = s.length();
            s += "proceedings of "+d.getSourceTitle();
            endxt = s.length();
            s += ", ";
        }

        if (d.getConferenceLocation() != null && !d.getConferenceLocation().equals("")) {
            s += d.getConferenceLocation() + ", ";
        }
        if (d.getConferenceDate() != null && !d.getConferenceDate().equals("")) {

            String dates=d.getConferenceDate().trim();
            if(dates.contains("-")){
                String date1=dates.split("-")[0].trim().replaceAll(" \\d*?, "," ");
                String date2=dates.split("-")[1].trim().replaceAll(" \\d*?, "," ");
                if(!date1.equals(date2)){
                    if(date2.contains(" ")) {
                        String month = date2.split(" ")[0].trim();

                        date1 = date1.replace(" ", "-" + month + " ");
                    }
                }
                s += date1 + ", ";
            }
        }
        if (d.getPageCode() != null && !d.getPageCode().equals("")) {
            s += "pp. "+d.getPageCode() + ".";
        }

        if(cell==null||wb==null||fo==null||style==null){
            return s;
        }
        HSSFRichTextString rich = new HSSFRichTextString(s);// 文章字段
        HSSFFont f = wb.createFont();
        f.setFontHeightInPoints((short) 12); // 字体高度
        f.setFontName("Times new roman"); // 字体
        f.setColor(Font.COLOR_RED);
        for (Pos p : poslist) {
            rich.applyFont(p.getStart(), p.getEnd(), f);
            rich.applyFont(p.getEnd(), s.length(), fo);
        }
        f.setItalic(true);
        f.setColor(Font.COLOR_NORMAL);
        rich.applyFont(startxt, endxt, f);
        rich.applyFont(endxt, s.length(), fo);

        cell.setCellValue(rich);
        cell.setCellStyle(style);
        return s;
    }

    public static void exportExcel(String fileName, List<PaperData> list) throws Exception {
        //List<DataExe> list = process(path, file);
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("论文报告");

        title(wb, sheet);// 处理头

        HSSFFont fo = wb.createFont();
        fo.setFontHeightInPoints((short) 12); // 字体高度
        fo.setFontName("Times new roman"); // 字体
        fo.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL); // 宽度
        HSSFCellStyle style = wb.createCellStyle();
        style.setFont(fo);
        style.setAlignment(HSSFCellStyle.ALIGN_LEFT); // 水平布局：
        style.setWrapText(true);

        for (int i = 0; i < list.size(); i++) {
            PaperData d = list.get(i);
            HSSFRow row = sheet.createRow(i + 4);
            HSSFCell cell = row.createCell(1);


            if(fileName.toLowerCase().contains("sci")) {
                dealSciArticle(d, cell, wb, fo, style);
            }else if(fileName.toLowerCase().contains("ei")){
                dealEiArticle(d, cell, wb, fo, style);
            }else if(fileName.toLowerCase().contains("cnki")){
                dealCnkiArticle(d, cell, wb, fo, style);
            }


            cell = row.createCell(MONTH_CELL);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
            style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            cell.setCellValue(format.format(d.getPubdate()));
            cell.setCellStyle(style);
            // 添加两列
            cell = row.createCell(FACTOR_2_YEAR);
            cell.setCellValue(d.getImpactFactor2year());
            cell.setCellStyle(style);
            cell = row.createCell(FACTOR_5_YEAR);
            cell.setCellValue(d.getImpactFactor5year());
            cell.setCellStyle(style);
            // 分区
            cell = row.createCell(JCR);
            cell.setCellValue(d.getJcr());
            cell.setCellStyle(style);
            row.setHeight((short) 1000);

        }


        sheet.setColumnWidth(MONTH_CELL, 4000);
        sheet.setColumnWidth(ARTICLE, 34900);
        sheet.setColumnWidth(AUTHOR_RANK, 4000);
        sheet.setColumnWidth(MARK_ORNOT, 4000);
        FileOutputStream fileOut = new FileOutputStream(fileName);
        wb.write(fileOut);
        fileOut.close();
    }

    private static void title(HSSFWorkbook wb, HSSFSheet sheet) {
        HSSFCellStyle normal = getStyle(wb, false);
        HSSFCellStyle red = getStyle(wb, true);
        HSSFRow rowtitle = sheet.createRow(0);// 表头
        HSSFCell celltitle = rowtitle.createCell(0);
        celltitle.setCellValue("复杂系统管理与控制国家重点实验室成果(2017年1-12月)\r\n" + "(State Key Laboratory of Management and Control for Complex Systems)");// 设置表头内容
        celltitle.setCellStyle(red);// 设置单元格样式
        rowtitle.setHeight((short) 500);
        sheet.createRow(1).setHeight((short) 500);
        //sheet.addMergedRegion(new Regiom(0, (short) 0, 1, (short) 3));

        sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, 3));
        rowtitle = sheet.createRow(3);
        celltitle = rowtitle.createCell(MONTH_CELL);
        celltitle.setCellValue("月份");
        celltitle.setCellStyle(normal);
        celltitle = rowtitle.createCell(ARTICLE);
        celltitle.setCellValue("文章");
        celltitle.setCellStyle(normal);
        celltitle = rowtitle.createCell(AUTHOR_RANK);
        celltitle.setCellValue("作者排名");
        celltitle.setCellStyle(normal);
        celltitle = rowtitle.createCell(MARK_ORNOT);
        celltitle.setCellValue("是否标注");
        celltitle.setCellStyle(normal);
        celltitle = rowtitle.createCell(FACTOR_2_YEAR);
        celltitle.setCellValue("两年因子");
        celltitle.setCellStyle(normal);
        celltitle = rowtitle.createCell(FACTOR_5_YEAR);
        celltitle.setCellValue("五年因子");
        celltitle.setCellStyle(normal);
        celltitle = rowtitle.createCell(INSTITUTIONS);
        celltitle.setCellValue("机构");
        celltitle.setCellStyle(normal);
        celltitle = rowtitle.createCell(JCR);
        celltitle.setCellValue("JCR分区");
        celltitle.setCellStyle(normal);
        rowtitle.setHeight((short) 400);

    }

    private static HSSFCellStyle getStyle(HSSFWorkbook wb, boolean red) {
        // 设置字体
        HSSFFont font = wb.createFont();
        font.setFontHeightInPoints((short) 16); // 字体高度
        if (red)
            font.setColor(HSSFFont.COLOR_RED); // 字体颜色
        font.setFontName("宋体"); // 字体
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // 宽度

        // 设置单元格类型
        HSSFCellStyle cellStyle = wb.createCellStyle();
        cellStyle.setFont(font);
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 水平布局：居中
        cellStyle.setWrapText(true);
        return cellStyle;
    }

}
