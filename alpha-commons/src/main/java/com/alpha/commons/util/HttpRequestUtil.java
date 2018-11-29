package com.alpha.commons.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;

import com.alpha.commons.api.tencent.offical.MyX509TrustManager;
import com.alpha.commons.api.tencent.offical.dto.QRCodeDTO;
import com.alpha.commons.config.pojo.FTPInfo;

public class HttpRequestUtil {
	
	public static final String GET_METHOD = "GET";  
	  
    public static final String POST_METHOD = "POST";  
  
    public static final String DEFAULT_CHARSET = "utf-8";  
  
    private static int DEFAULT_CONNTIME = 5000;  
      
    private static int DEFAULT_READTIME = 5000;  

	public static String defaultConnection(String method, String path, int timeout, int readTimeout, String data)
			throws Exception {
		String result = "";
		URL url = new URL(path);
		if (url != null) {
			HttpURLConnection conn = getConnection(method, url);
			conn.setConnectTimeout(timeout == 0 ? DEFAULT_CONNTIME : timeout);
			conn.setReadTimeout(readTimeout == 0 ? DEFAULT_READTIME : readTimeout);
			if (data != null && !"".equals(data)) {
				OutputStream output = conn.getOutputStream();
				output.write(data.getBytes(DEFAULT_CHARSET));
				output.flush();
				output.close();
			}
			if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
				InputStream input = conn.getInputStream();
				result = inputToStr(input);
				input.close();
				conn.disconnect();
			}
		}
		return result;
	}
	
	/** 
     * 根据url的协议选择对应的请求方式 例如 http://www.baidu.com 则使用http请求,https://www.baidu.com 
     * 则使用https请求 
     *  
     * @param method 
     *            请求的方法 
     * @return 
     * @throws IOException 
     */  
    private static HttpURLConnection getConnection(String method, URL url) throws IOException {  
        HttpURLConnection conn = null;  
        if ("https".equals(url.getProtocol())) {  
            SSLContext context = null;  
            try {  
                context = SSLContext.getInstance("SSL", "SunJSSE");  
                context.init(new KeyManager[0], new TrustManager[] { new MyX509TrustManager() },  
                        new java.security.SecureRandom());  
            } catch (Exception e) {  
                throw new IOException(e);  
            }  
            HttpsURLConnection connHttps = (HttpsURLConnection) url.openConnection();  
            connHttps.setSSLSocketFactory(context.getSocketFactory());  
            connHttps.setHostnameVerifier(new HostnameVerifier() {  
  
                @Override  
                public boolean verify(String arg0, SSLSession arg1) {  
                    return true;  
                }  
            });  
            conn = connHttps;  
        } else {  
            conn = (HttpURLConnection) url.openConnection();  
        }  
        conn.setRequestMethod(method);  
        conn.setUseCaches(false);  
        conn.setDoInput(true);  
        conn.setDoOutput(true);  
        return conn;  
    } 

    /** 
     * 默认的下载素材方法 
     *  
     * @param method  http方法 POST/GET 
     * @param apiPath api路径 
     * @param savePath 素材需要保存的路径 
     * @return 是否下载成功 Reuslt.success==true 表示下载成功 
     */  
    public static QRCodeDTO downMeaterMetod(TreeMap<String, String> params, String method, String apiPath, String savePath) {  
        QRCodeDTO result = new QRCodeDTO();  
        try {  
            apiPath = setParmas(params, apiPath, "");  
            URL url = new URL(apiPath);  
            HttpURLConnection conn = getConnection(method, url);  
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {  
                String contentType = conn.getContentType();  
                result = ContentType(contentType, conn, savePath);  
            } else {  
                result.setObj(conn.getResponseCode() + " " + conn.getResponseMessage());  
            }  
        } catch (MalformedURLException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return result;  
    } 
    
    /** 
     * 默认的下载素材方法 
     *  
     * @param method  http方法 POST/GET 
     * @param apiPath api路径 
     * @param savePath 素材需要保存的路径 
     * @return 是否下载成功 Reuslt.success==true 表示下载成功 
     */  
    public static QRCodeDTO download(TreeMap<String, String> params, String method, String apiPath, FTPInfo ftpInfo) {  
        QRCodeDTO result = new QRCodeDTO();  
        try {  
            apiPath = setParmas(params, apiPath, "");  
            URL url = new URL(apiPath);  
            HttpURLConnection conn = getConnection(method, url);  
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {  
                String contentType = conn.getContentType();  
                result = ContentType(contentType, conn, ftpInfo);  
            } else {  
                result.setObj(conn.getResponseCode() + " " + conn.getResponseMessage());  
            }  
        } catch (MalformedURLException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return result;  
    }
    
    /**
	 * 根据返回的头信息返回具体信息
	 * 
	 * @param contentType
	 *            ContentType请求头信息
	 * @return Result.type==1 表示文本消息,
	 */
	private static QRCodeDTO ContentType(String contentType, HttpURLConnection conn, FTPInfo ftpInfo) {
		QRCodeDTO result = null;
		try {
			if (conn != null) {
				InputStream input = conn.getInputStream();
				String fileName = System.currentTimeMillis() + "";
				if (contentType.equals("image/gif")) { // gif图片
					fileName = fileName.concat(".gif");
					result = uploadToFtpServer(input, fileName, ftpInfo);
				} else if (contentType.equals("image/jpeg")) { // jpg图片
					fileName = fileName.concat(".jpg");
					result = uploadToFtpServer(input, fileName, ftpInfo);
				} else if (contentType.equals("image/jpg")) { // jpg图片
					fileName = fileName.concat(".jpg");
					result = uploadToFtpServer(input, fileName, ftpInfo);
				} else if (contentType.equals("image/png")) { // png图片
					fileName = 	fileName.concat(".png");
					result = uploadToFtpServer(input, fileName, ftpInfo);
				} else if (contentType.equals("image/bmp")) { // bmp图片
					fileName = fileName.concat(".bmp");
					result = uploadToFtpServer(input, fileName, ftpInfo);
				} else if (contentType.equals("audio/x-wav")) { // wav语音
					fileName = fileName.concat(".wav");
					result = uploadToFtpServer(input, fileName, ftpInfo);
				} else if (contentType.equals("audio/x-ms-wma")) { // wma语言
					fileName = fileName.concat(".wma");
					result = uploadToFtpServer(input, fileName, ftpInfo);
				} else if (contentType.equals("audio/mpeg")) { // mp3语言
					fileName = fileName.concat(".mp3");
					result = uploadToFtpServer(input, fileName, ftpInfo);
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
	 * 上传文件到FTP服务器
	 * @param input
	 *            字符流
	 * @param savePath
	 *            图片需要保存的路径
	 * @param 类型
	 *            jpg/png等
	 * @return
	 */
	private static QRCodeDTO uploadToFtpServer_bak(InputStream inputStream, String fileName, FTPInfo ftpInfo) {
		String hostname = ftpInfo.getIp();
		int port = ftpInfo.getPort();
		String username = ftpInfo.getUserName();
		String password = ftpInfo.getPassword();
		String uploadPath = ftpInfo.getUploadPath4code();
		FTPUtils.uploadFile(hostname, port, username, password, uploadPath, fileName, inputStream);
		QRCodeDTO dto = new QRCodeDTO();
		dto.setSuccess(true);
		dto.setMsg(uploadPath + File.separator + fileName);
		return dto;
	}
	
	private static QRCodeDTO uploadToFtpServer(InputStream inputStream, String fileName, FTPInfo ftpInfo) {
		String hostname = ftpInfo.getIp();
		int port = ftpInfo.getPort();
		String username = ftpInfo.getUserName();
		String password = ftpInfo.getPassword();
		String uploadPath = ftpInfo.getUploadPath4code();
		FtpTool.uploadFtpFile(hostname, port, username, password, uploadPath, fileName, inputStream);//上传图片方法
		QRCodeDTO dto = new QRCodeDTO();
		dto.setSuccess(true);
		dto.setMsg(uploadPath + File.separator + fileName);
		return dto;
	}

	/**
	 * 将字符流转换为图片文件
	 * 
	 * @param input
	 *            字符流
	 * @param savePath
	 *            图片需要保存的路径
	 * @param 类型
	 *            jpg/png等
	 * @return
	 */
	private static QRCodeDTO InputStreamToMedia(InputStream input, String savePath, String type) {
		QRCodeDTO result = new QRCodeDTO();
		try {
//			File file = ResourceUtils.getFile(savePath);
			File file = new File(savePath);
			if(!file.exists()) {
				file.createNewFile();
			}
			String absolutePath = file.getAbsolutePath();
			System.out.println("absolutePath="+absolutePath);
			
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
			input.close();
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
	
	/** 
     * 设置参数 
     *  
     * @param map 
     *            参数map 
     * @param path 
     *            需要赋值的path 
     * @param charset 
     *            编码格式 默认编码为utf-8(取消默认) 
     * @return 已经赋值好的url 只需要访问即可 
     */  
    public static String setParmas(Map<String, String> map, String path, String charset) throws Exception {  
        String result = "";  
        boolean hasParams = false;  
        if (path != null && !"".equals(path)) {  
            if (map != null && map.size() > 0) {  
                StringBuilder builder = new StringBuilder();  
                Set<Entry<String, String>> params = map.entrySet();  
                for (Entry<String, String> entry : params) {  
                    String key = entry.getKey().trim();  
                    String value = entry.getValue().trim();  
                    if (hasParams) {  
                        builder.append("&");  
                    } else {  
                        hasParams = true;  
                    }  
                    if(charset != null && !"".equals(charset)){  
                        //builder.append(key).append("=").append(URLDecoder.(value, charset));    
                        builder.append(key).append("=").append(urlEncode(value, charset));  
                    }else{  
                        builder.append(key).append("=").append(value);  
                    }  
                }  
                result = builder.toString();  
            }  
        }  
        return doUrlPath(path, result).toString();  
    }  
    
    public static String urlEncode(String source, String encode) {  
        String result = source;  
        try {  
            result = URLEncoder.encode(source, encode);  
        } catch (UnsupportedEncodingException e) {  
            e.printStackTrace();  
        }  
        return result;  
    } 
    
    /** 
     * 设置连接参数 
     *  
     * @param path 
     *            路径 
     * @return 
     */  
    private static URL doUrlPath(String path, String query) throws Exception {  
        URL url = new URL(path);  
        if (StringUtils.isEmpty(path)) {  
            return url;  
        }  
        if (StringUtils.isEmpty(url.getQuery())) {  
            if (path.endsWith("?")) {  
                path += query;  
            } else {  
                path = path + "?" + query;  
            }  
        } else {  
            if (path.endsWith("&")) {  
                path += query;  
            } else {  
                path = path + "&" + query;  
            }  
        }  
        return new URL(path);  
    }
}

