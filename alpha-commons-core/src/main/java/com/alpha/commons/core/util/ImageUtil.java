package com.alpha.commons.core.util;

import com.alpha.commons.config.pojo.FTPInfo;
import com.alpha.commons.util.FtpTool;
import com.google.zxing.*;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.apache.commons.lang3.StringUtils;
import sun.misc.BASE64Decoder;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.util.Base64;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

/**
 * Created by Administrator on 2018/3/14.
 */
public class ImageUtil {

    private static String generateQRCode(String text, int width, int height, String format) throws Exception {
        Hashtable<EncodeHintType, Object> hints = new Hashtable<>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        BitMatrix bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, width, height, hints);
        String pathName = "F:/new.png";
        File outputFile = new File(pathName);
        MatrixToImageWriter.writeToFile(bitMatrix, format, outputFile);
        return pathName;
    }

    private static String parseQRCode(String filePath) {
        String content = "";
        try {
            File file = new File(filePath);
            BufferedImage image = ImageIO.read(file);
            LuminanceSource source = new BufferedImageLuminanceSource(image);
            Binarizer binarizer = new HybridBinarizer(source);
            BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);
            Map<DecodeHintType, Object> hints = new HashMap<>();
            hints.put(DecodeHintType.CHARACTER_SET, "UTF-8");
            MultiFormatReader formatReader = new MultiFormatReader();
            Result result = formatReader.decode(binaryBitmap, hints);

            content = result.getText();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content;
    }

    /**
     * @param imgStr base64编码字符串
     * @param path   图片路径-具体到文件
     * @Description: 将base64编码字符串转换为图片
     * @Author: yangbin
     */
    public static String generateImage(String imgStr, String path, FTPInfo ftpInfo) {
        if (StringUtils.isNotBlank(imgStr)) {
            //data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAA
            String[] imgStrArray = imgStr.split(",");
            String s = imgStrArray[0];
            String suffix = s.substring(s.indexOf("/") + 1, s.indexOf(";"));
            String fileName = StringUtil.genUUID() + "." + suffix;
            BASE64Decoder decoder = new BASE64Decoder();
            try {
                // 解密
                byte[] b = decoder.decodeBuffer(imgStrArray[1]);
                // 处理数据
                for (int i = 0; i < b.length; ++i) {
                    if (b[i] < 0) {
                        b[i] += 256;
                    }
                }
                InputStream inputStream = new ByteArrayInputStream(b);
                FtpTool.uploadFtpFile(ftpInfo.getIp(), ftpInfo.getPort(), ftpInfo.getUserName(), ftpInfo.getPassword(), path, fileName, inputStream);
                return System.getProperty("file.separator")+path + System.getProperty("file.separator") + fileName;
            } catch (Exception e) {
                return "";
            }
        }
        return "";
    }

    public static String getBase64QRCode(String content) throws Exception {
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        @SuppressWarnings("rawtypes")
        Map hints = new HashMap();
        hints.put(EncodeHintType.MARGIN, 1);
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        BitMatrix bitMatrix = multiFormatWriter.encode(content, BarcodeFormat.QR_CODE, 300, 300, hints);
        BufferedImage image = MatrixToImageWriter.toBufferedImage(bitMatrix);
        ByteArrayOutputStream os = new ByteArrayOutputStream();//新建流。
        ImageIO.write(image, "png", os);//利用ImageIO类提供的write方法，将bi以png图片的数据模式写入流。
        byte b[] = os.toByteArray();//从流中获取数据数组。
        String base64String = Base64.getEncoder().encodeToString(b);
        return base64String;
    }
}
