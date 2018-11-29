package com.alpha.commons.util;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.Map;

import com.alpha.commons.api.tencent.offical.dto.QRCodeDTO;


public class HttpUtils {

	public static String doGet(String url) {
        String result = "";
        BufferedReader in = null;
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            
            // 建立实际的连接
            connection.connect();
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }
	
	public static String doGet2(String url) {
        String result = "";
        BufferedReader in = null;
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            
            // 建立实际的连接
            connection.connect();
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }
	
	public static String doPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        System.out.println("post uri is:" + url);
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
//            conn.addRequestProperty("Content-Type","application/x-www-form-urlencoded; charset=utf-8");
            conn.addRequestProperty("Content-Type","application/json; charset=utf-8");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(new OutputStreamWriter(conn.getOutputStream(),"utf-8"));
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        System.out.println("result is : " + result);
        return result;
    }
	
	public static String doPost(String url, String param, String contentType) {
        PrintWriter out = null;
        BufferedReader in = null;
        System.out.println("post uri is:" + url);
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.addRequestProperty("Content-Type",contentType);
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        System.out.println("result is : " + result);
        return result;
    }
	
	public static String doPostWithSessionId(String url, String param, String sessionId) {
        PrintWriter out = null;
        BufferedReader in = null;
        System.out.println("post uri is:" + url);
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestProperty("Cookie", "JSESSIONID="+sessionId);
            conn.addRequestProperty("Content-Type","application/x-www-form-urlencoded; charset=utf-8");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        System.out.println("result is : " + result);
        return result;
    }
	
	public static String formUpload(String urlStr, Map<String, String> textMap, Map<String, String> fileMap,String sessionId) {  
        String res = "";  
        HttpURLConnection conn = null;  
        String BOUNDARY = "---------------------------123821742118716"; //boundary就是request头和上传文件内容的分隔符    
        try {  
            URL url = new URL(urlStr);  
            conn = (HttpURLConnection) url.openConnection();  
            conn.setConnectTimeout(5000);  
            conn.setReadTimeout(30000);  
            conn.setDoOutput(true);  
            conn.setDoInput(true);  
            conn.setUseCaches(false);  
            conn.setRequestMethod("POST");  
            conn.setRequestProperty("Connection", "Keep-Alive");  
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.1; zh-CN; rv:1.9.2.6)");  
            conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);  
            //conn.setRequestProperty("Cookie", "JSESSIONID="+sessionId);
  
            OutputStream out = new DataOutputStream(conn.getOutputStream());  
            // text    
            if (textMap != null) {  
                StringBuffer strBuf = new StringBuffer();  
                Iterator<Map.Entry<String, String>> iter = textMap.entrySet().iterator();  
                while (iter.hasNext()) {  
                    Map.Entry<String, String> entry = iter.next();  
                    String inputName = (String) entry.getKey();  
                    String inputValue = (String) entry.getValue();  
                    if (inputValue == null) {  
                        continue;  
                    }  
                    strBuf.append("\r\n").append("--").append(BOUNDARY).append("\r\n");  
                    strBuf.append("Content-Disposition: form-data; name=\"" + inputName + "\"\r\n\r\n");  
                    strBuf.append(inputValue);  
                }  
                out.write(strBuf.toString().getBytes());  
            }  
  
            // file    
            if (fileMap != null) {  
                Iterator<Map.Entry<String, String>> iter = fileMap.entrySet().iterator();  
                while (iter.hasNext()) {  
                    Map.Entry<String, String> entry = iter.next();  
                    String inputName = (String) entry.getKey();  
                    String inputValue = (String) entry.getValue();  
                    if (inputValue == null) {  
                        continue;  
                    }  
                    File file = new File(inputValue);  
                    String filename = file.getName();  
                    String contentType = "multipart /form-data";  
  
                    StringBuffer strBuf = new StringBuffer();  
                    strBuf.append("\r\n").append("--").append(BOUNDARY).append("\r\n");  
                    strBuf.append("Content-Disposition: form-data; name=\"" + inputName + "\"; filename=\"" + filename + "\"\r\n");  
                    strBuf.append("Content-Type:" + contentType + "\r\n\r\n");  
  
                    out.write(strBuf.toString().getBytes());  
  
                    DataInputStream in = new DataInputStream(new FileInputStream(file));  
                    int bytes = 0;  
                    byte[] bufferOut = new byte[1024];  
                    while ((bytes = in.read(bufferOut)) != -1) {  
                        out.write(bufferOut, 0, bytes);  
                    }  
                    in.close();  
                }  
            }  
  
            byte[] endData = ("\r\n--" + BOUNDARY + "--\r\n").getBytes();  
            out.write(endData);  
            out.flush();  
            out.close();  
  
            // 读取返回数据    
            StringBuffer strBuf = new StringBuffer();  
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));  
            String line = null;  
            while ((line = reader.readLine()) != null) {  
                strBuf.append(line).append("\n");  
            }  
            res = strBuf.toString();  
            reader.close();  
            reader = null;  
        } catch (Exception e) {  
            System.out.println("发送POST请求出错。" + urlStr);  
            e.printStackTrace();  
        } finally {  
            if (conn != null) {  
                conn.disconnect();  
                conn = null;  
            }  
        }  
        return res;  
    }  
	
	public static String formUpload(String urlStr, String param, Map<String, String> fileMap) {  
        String res = "";  
        HttpURLConnection conn = null;  
        String BOUNDARY = "---------------------------123821742118716"; //boundary就是request头和上传文件内容的分隔符    
        try {  
            URL url = new URL(urlStr);  
            conn = (HttpURLConnection) url.openConnection();  
            conn.setConnectTimeout(5000);  
            conn.setReadTimeout(30000);  
            conn.setDoOutput(true);  
            conn.setDoInput(true);  
            conn.setUseCaches(false);  
            conn.setRequestMethod("POST");  
            conn.setRequestProperty("Connection", "Keep-Alive");  
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.1; zh-CN; rv:1.9.2.6)");  
            conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);  
            //conn.setRequestProperty("Cookie", "JSESSIONID="+sessionId);
  
            OutputStream out = new DataOutputStream(conn.getOutputStream());  
            // text    
           /* if (textMap != null) {  
                StringBuffer strBuf = new StringBuffer();  
                Iterator<Map.Entry<String, String>> iter = textMap.entrySet().iterator();  
                while (iter.hasNext()) {  
                    Map.Entry<String, String> entry = iter.next();  
                    String inputName = (String) entry.getKey();  
                    String inputValue = (String) entry.getValue();  
                    if (inputValue == null) {  
                        continue;  
                    }  
                    strBuf.append("\r\n").append("--").append(BOUNDARY).append("\r\n");  
                    strBuf.append("Content-Disposition: form-data; name=\"" + inputName + "\"\r\n\r\n");  
                    strBuf.append(inputValue);  
                }  
                out.write(strBuf.toString().getBytes());  
            }*/
            if(true) {
            	StringBuffer strBuf = new StringBuffer();  
            	strBuf.append("\r\n").append("--").append(BOUNDARY).append("\r\n");  
            	strBuf.append("Content-Disposition: form-data;");  
            	strBuf.append(param); 
            	out.write(param.getBytes()); 
            }
  
            // file    
            if (fileMap != null) {  
                Iterator<Map.Entry<String, String>> iter = fileMap.entrySet().iterator();  
                while (iter.hasNext()) {  
                    Map.Entry<String, String> entry = iter.next();  
                    String inputName = (String) entry.getKey();  
                    String inputValue = (String) entry.getValue();  
                    if (inputValue == null) {  
                        continue;  
                    }  
                    File file = new File(inputValue);  
                    String filename = file.getName();  
                    String contentType = "multipart /form-data";  
  
                    StringBuffer strBuf = new StringBuffer();  
                    strBuf.append("\r\n").append("--").append(BOUNDARY).append("\r\n");  
                    strBuf.append("Content-Disposition: form-data; name=\"" + inputName + "\"; filename=\"" + filename + "\"\r\n");  
                    strBuf.append("Content-Type:" + contentType + "\r\n\r\n");  
  
                    out.write(strBuf.toString().getBytes());  
  
                    DataInputStream in = new DataInputStream(new FileInputStream(file));  
                    int bytes = 0;  
                    byte[] bufferOut = new byte[1024];  
                    while ((bytes = in.read(bufferOut)) != -1) {  
                        out.write(bufferOut, 0, bytes);  
                    }  
                    in.close();  
                }  
            }  
  
            byte[] endData = ("\r\n--" + BOUNDARY + "--\r\n").getBytes();  
            out.write(endData);  
            out.flush();  
            out.close();  
  
            // 读取返回数据    
            StringBuffer strBuf = new StringBuffer();  
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));  
            String line = null;  
            while ((line = reader.readLine()) != null) {  
                strBuf.append(line).append("\n");  
            }  
            res = strBuf.toString();  
            reader.close();  
            reader = null;  
        } catch (Exception e) {  
            System.out.println("发送POST请求出错。" + urlStr);  
            e.printStackTrace();  
        } finally {  
            if (conn != null) {  
                conn.disconnect();  
                conn = null;  
            }  
        }  
        return res;  
    }
	
	/** 
     * 根据返回的头信息返回具体信息 
     *  
     * @param contentType 
     *            ContentType请求头信息 
     * @return Result.type==1 表示文本消息, 
     */  
    private static QRCodeDTO ContentType(String contentType, HttpURLConnection conn, String savePath) {  
        QRCodeDTO result = new QRCodeDTO();  
        try {  
            if (conn != null) {  
                InputStream input = conn.getInputStream();  
                if (contentType.equals("image/gif")) { // gif图片  
                    result = InputStreamToMedia(input, savePath, "gif");  
                } else if (contentType.equals("image/jpeg")) { // jpg图片  
                    result = InputStreamToMedia(input, savePath, "jpg");  
                } else if (contentType.equals("image/jpg")) { // jpg图片  
                    result = InputStreamToMedia(input, savePath, "jpg");  
                } else if (contentType.equals("image/png")) { // png图片  
                    result = InputStreamToMedia(input, savePath, "png");  
                } else if (contentType.equals("image/bmp")) { // bmp图片  
                    result = InputStreamToMedia(input, savePath, "bmp");  
                } else if (contentType.equals("audio/x-wav")) { // wav语音  
                    result = InputStreamToMedia(input, savePath, "wav");  
                } else if (contentType.equals("audio/x-ms-wma")) { // wma语言  
                    result = InputStreamToMedia(input, savePath, "wma");  
                } else if (contentType.equals("audio/mpeg")) { // mp3语言  
                    result = InputStreamToMedia(input, savePath, "mp3");  
                } else if (contentType.equals("text/plain")) { // 文本信息  
                    String str = inputToStr(input);  
                    result.setObj(str);  
                } else if (contentType.equals("application/json")) { // 返回json格式的数据  
                    String str = inputToStr(input);  
                    result.setObj(str);  
                } else { // 此处有问题  
                    String str = inputToStr(input);  
                    result.setObj(str);  
                }  
            } else {  
                result.setObj("conn is null!");  
            }  
        } catch (Exception ex) {  
            ex.printStackTrace();  
        }  
        return result;  
    }
    
    private static String inputToStr(InputStream input) {  
        String result = "";  
        if (input != null) {  
            byte[] array = new byte[1024];  
            StringBuffer buffer = new StringBuffer();  
            try {  
                for (int index; (index = (input.read(array))) > -1;) {  
                    buffer.append(new String(array, 0, index, "UTF-8"));  
                }  
                result = buffer.toString();  
            } catch (IOException e) {  
                e.printStackTrace();  
                result = "";  
            }  
        }  
        return result;  
    }
    
    /** 
     * 将字符流转换为图片文件 
     *  
     * @param input 字符流 
     * @param savePath 图片需要保存的路径 
     * @param 类型 jpg/png等 
     * @return 
     */  
    private static QRCodeDTO InputStreamToMedia(InputStream input, String savePath, String type) {  
        QRCodeDTO result = new QRCodeDTO();  
        try {  
            File file = null;  
            file = new File(savePath);  
            String paramPath = file.getParent(); // 路径  
            String fileName = file.getName(); //  
            String newName = fileName.substring(0, fileName.lastIndexOf(".")) + "." + type;// 根据实际返回的文件类型后缀  
            savePath = paramPath + "\\" + newName;  
            if (!file.exists()) {  
                File dirFile = new File(paramPath);  
                dirFile.mkdirs();  
            }  
            file = new File(savePath);  
            FileOutputStream output = new FileOutputStream(file);  
            int len = 0;  
            byte[] array = new byte[1024];  
            while ((len = input.read(array)) != -1) {  
                output.write(array, 0, len);  
            }  
            output.flush();  
            output.close();  
            result.setSuccess(true);  
            result.setObj("save success!");  
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
            result.setSuccess(false);  
            result.setObj(e.getMessage());  
        } catch (IOException e) {  
            e.printStackTrace();  
            result.setSuccess(false);  
            result.setObj(e.getMessage());  
        }  
        return result;  
    } 
}
