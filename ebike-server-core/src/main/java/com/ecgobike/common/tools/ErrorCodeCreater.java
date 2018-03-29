package com.ecgobike.common.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.core.io.DefaultResourceLoader;

/**
 * Created by ChenJun on 2018/3/12.
 */

public class ErrorCodeCreater {
    private static String sep = File.separator;
    private static final String ERR_CODE_TEMPLATE = getBaseDir() + sep + "err_code.txt";
    private static final String ERR_CODE_URL = getBaseDir() + sep + "err_code.xml";
    private static Document document = null;
    public static Element root = null;

    private static final String packageDir = "net.zriot.ebike.common.constant";

    private static String springEnv = getBaseDir() + sep + "src" + sep + "main" + sep + "java" + sep + "net" + sep + "zriot" + sep + "ebike" + sep + "common" + sep + "constant";

    private static StringBuffer data = new StringBuffer();


    public static String getBaseDir() {
        String dir = "";
        try {
            dir = new DefaultResourceLoader().getResource("").getFile().toString().replace(File.separator + "target" + File.separator + "classes", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dir;

    }

    public static void main(String[] args) throws IOException {
        String url = springEnv;
        File file = new File(url + sep + "ErrorConstants.java");
        System.out.println(url + sep + "ErrorConstants.java");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        insertData();
        OutputStream os = null;
        try {
            os = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        PrintStream ps = new PrintStream(os);
        String template = readTemplate();
        template = template.replace("${data}", data.toString());
        template = template.replace("${package}", packageDir);
        ps.print(template);
        ps.flush();
        ps.close();

    }

    public static String readTemplate() throws IOException {
        StringBuffer sb = new StringBuffer();
        FileReader fr = new FileReader(new File(ERR_CODE_TEMPLATE));
        @SuppressWarnings("resource")
        BufferedReader br = new BufferedReader(fr);
        String temp = "";

        while ((temp = br.readLine()) != null) {
            sb.append(temp + "\r\n");
        }

        return sb.toString();
    }

    public static void insertData() {
        SAXReader reader = new SAXReader();
        try {
            document = reader.read(new File(ERR_CODE_URL));
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        root = document.getRootElement();
        @SuppressWarnings("unchecked")
        List<Element> errs = root.elements("err");
        for (Element e : errs) {
            data.append("	public static final int " + e.attributeValue("name") + " = " + e.attributeValue("id") + ";" + "//" + e.attributeValue("description") + "\r\n");
        }
    }
}

