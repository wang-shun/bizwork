package com.sogou.bizwork.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CSVReportUtils {
    public static String CSV_QUOTE = "\"";
    public static String CSV_QUOTE_ESCAPE = "\"\"";
    public static String CSV_DELIMITER = ",";
    public static char CSV_DELIMITER_CHAR = ',';
    public static String CSV_NEWLINE = "\r\n";

    /**
     * 生成CSV格式的报表字符串
     * 
     * @param titles
     *            List<String> 报表标题
     * @param data
     *            List<String[]> 报表数据
     * @return CSV格式的报表字符串
     */
    public static String genCSVReport(List<String> titles, List<List<String>> data) {
        StringBuffer sb = new StringBuffer();

        if (titles != null) {
            for (String title : titles) {
                if (title == null) {
                    title = "";
                } else {
                    title = title.replaceAll(CSV_QUOTE, CSV_QUOTE_ESCAPE);
                }
                sb.append(CSV_QUOTE).append(title).append(CSV_QUOTE).append(CSV_DELIMITER);
            }
            if (sb.charAt(sb.length() - 1) == CSV_DELIMITER_CHAR) {
                sb.deleteCharAt(sb.length() - 1);
            }
            sb.append(CSV_NEWLINE);
        }

        if (data != null) {
            for (List<String> items : data) {
                for (String item : items) {
                    if (item == null) {
                        item = "";
                    } else {
                        item = item.replaceAll(CSV_QUOTE, CSV_QUOTE_ESCAPE);
                    }
                    sb.append(CSV_QUOTE).append(item).append(CSV_QUOTE).append(CSV_DELIMITER);
                }
                if (sb.charAt(sb.length() - 1) == CSV_DELIMITER_CHAR) {
                    sb.deleteCharAt(sb.length() - 1);
                }
                sb.append(CSV_NEWLINE);
            }
        }

        return sb.toString();
    }

    public static void writeCsvData(String content, File file) throws IOException {
        OutputStreamWriter fw = new OutputStreamWriter(new FileOutputStream(file.getAbsolutePath()), "gbk");
        fw.write(content);
        fw.flush();
        fw.close();
    }

    /**
     * 解析csv。网上找的，待优化。
     * 
     * @param file
     * @return
     * @author Xuxin
     * @throws IOException
     */
    public static List<List<String>> readCSVFile(InputStream is) throws IOException {
        InputStreamReader fr = new InputStreamReader(is, "GBK");
        BufferedReader br = new BufferedReader(fr);
        String rec = null;// 一行
        String str;// 一个单元格
        List<List<String>> listFile = new ArrayList<List<String>>();
        try {
            Pattern pCells = Pattern.compile("(\"[^\"]*(\"{2})*[^\"]*\")*[^,]*,");
            Pattern pReplace1 = Pattern.compile("(?sm)\"?([^\"]*(\"{2})*[^\"]*)\"?.*,");
            Pattern pReplace2 = Pattern.compile("(?sm)(\"(\"))");
            // 读取一行
            while ((rec = br.readLine()) != null) {
                if (!rec.endsWith(",")) {
                    rec = rec + ",";
                }
                Matcher mCells = pCells.matcher(rec);
                List<String> cells = new ArrayList<String>();// 每行记录一个list
                // 读取每个单元格
                while (mCells.find()) {
                    str = mCells.group();
                    str = pReplace1.matcher(str).replaceAll("$1");
                    str = pReplace2.matcher(str).replaceAll("$2");
                    cells.add(str);
                }
                listFile.add(cells);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fr != null) {
                fr.close();
            }
            if (br != null) {
                br.close();
            }
        }
        return listFile;
    }

}
