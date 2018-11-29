package com.alpha.commons.util;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by xc.xiong on 2017/2/16.
 * ftp服务器相关操作
 */
public class FtpTool {


    public static final String FTP_FILE_PATCH_DRUGSTORE = "zhys/drugstore/avatar";//药店版图片存放地址
    public static final String FTP_FILE_PATCH_PEOPLE = "zhys/people/avatar";//大众版图片存放地址

    public static final String FTP_FILE_PATCH_GROUP_ICO = "zhys/group";//群组图片存放地址

    public static final String FTP_FILE_PATCH_REPORT_ICO = "zhys/report";//投诉图片存放地址

    public static final String FTP_FILE_PATCH_H5_IMG_MSG_ICO = "zhys/h5/img/msg";//h5 图片消息地址

    private static final Logger LOGGER = LoggerFactory.getLogger(FtpTool.class);

    private static FTPClient ftpClient = new FTPClient();

    /**
     * Description: 向FTP服务器上传文件
     *
     * @param url      FTP服务器hostname
     * @param port     FTP服务器端口
     * @param username FTP登录账号
     * @param password FTP登录密码
     * @param path     FTP服务器保存目录
     * @param filename 上传到FTP服务器上的文件名
     * @param input    输入流
     * @return 成功返回true，否则返回false *
     * @Version 1.0
     */
    public static boolean uploadFtpFile(String url, int port, String username, String password, String path, String filename, InputStream input) {
        ftpClient.setControlEncoding("GBK");
        try {
            int reply;
            ftpClient.connect(url, port);// 连接FTP服务器
            // 如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
            ftpClient.login(username, password);// 登录
            reply = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftpClient.disconnect();
                LOGGER.error("{}:{}  Connection FTP Server Fail", url, port);
                return false;
            }
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftpClient.enterLocalPassiveMode();
            makeDirectory(path);
            ftpClient.storeFile(filename, input);
            input.close();
            ftpClient.logout();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
        }
        return false;
    }

    // 删除文件至FTP通用方法
    public static boolean deleteFtpFile(String url, int port, String username, String password, String pathname) {
        FTPClient ftp = new FTPClient();
        ftp.setControlEncoding("GBK");
        try {
            int reply;
            ftp.connect(url, port);// 连接FTP服务器
            // 如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
            ftp.login(username, password);// 登录
            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                LOGGER.error("{}:{}  Connection FTP Server Fail", url, port);
                return false;
            }
            ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftp.deleteFile(pathname);
            ftp.logout();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
        }
        return false;
    }

    /**
     * 在服务器上创建一个文件夹
     *
     * @param dir 文件夹名称，不能含有特殊字符，如 \ 、/ 、: 、* 、?、 "、 <、>...
     */
    private static boolean makeDirectory(String dir) {
        boolean flag = true;
        try {
            String dirs[] = dir.split("/");
            for (int i = 0; i < dirs.length; i++) {
                boolean reply = ftpClient.changeWorkingDirectory(dirs[i]);
                if (!reply) {
                    flag = ftpClient.makeDirectory(dirs[i]);
                    if (!flag) {
                        LOGGER.error("创建文件夹" + dir + " 失败！");
                        throw new Exception("创建文件夹失败");
                    }
                    ftpClient.changeWorkingDirectory(dirs[i]);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

}
