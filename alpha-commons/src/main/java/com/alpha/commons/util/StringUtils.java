package com.alpha.commons.util;

import com.alpha.commons.constants.GlobalConstants;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.regex.Pattern;


/**
 * 
 * @author 字符串帮助类
 * 
 */
@SuppressWarnings({ "unused", "unchecked", "rawtypes" })
public final class StringUtils {
	private StringUtils() {
	}

	private static final String[] UPPER_DIGIT = { "一", "二", "三", "四", "五", "六", "七", "八", "九" };

	private static final String FOLDER_SEPARATOR = "/";

	private static final String WINDOWS_FOLDER_SEPARATOR = "\\";

	private static final String TOP_PATH = "..";

	private static final String CURRENT_PATH = ".";

	private static final char EXTENSION_SEPARATOR = 46;
	
	public static final String[] UNITS = {"min", "分钟", "小时", "天", "日", "周", "月", "年", "度", "摄氏度", "℃"}; 
	
	
	/**
	 * 将一个数组转换成指定格式字符串
	 * 
	 * @param obj
	 * @return 例如： for: ['40288007318493de0131849474690001','40288007318493de0131849474690001 ' ] to
	 *         :'40288007318493de0131849474690001','40288007318493de013184947469000 1 '
	 */
	public static String formatArrayToStr(Object[] obj) {
		StringBuffer strbf = new StringBuffer();
		for (int i = 0; i < obj.length; i++) {
			strbf.append("'");
			strbf.append(obj[i]);
			strbf.append("',");
		}
		return strbf.substring(0, strbf.lastIndexOf(",")).toString();
	}

	public static byte[] getStringBytes(String str) throws UnsupportedEncodingException {
		if (str != null) {
			return str.getBytes("UTF-8");
		}
		return null;
	}

	/**
	 * @param idName
	 *            传入你要转入id的名字
	 * @param ids
	 *            获取的所有的ID
	 * @return 例如：传入idName = "id" 返回样式如下： ( id= '40288007318493de0131849474690001' or id= '40288007318493de01318496e3270002' )
	 */
	public static String getOrStr(String idName, String ids) {
		if ("".equals(idName) || idName == null)
			idName = "id";
		if (null != ids && ids.length() != 0) {
			String arr[] = ids.split(",");
			ids = "";
			ids = "( ";
			for (int i = 0; i < arr.length; i++) {
				if (i == arr.length - 1)
					ids += " " + idName + "= '" + arr[i] + "' ";
				else
					ids += " " + idName + "= '" + arr[i] + "' or";
			}
			ids += " )";

			return ids;
		} else {
			return null;
		}
	}

	/**
	 * 生成32位的UUID字符串
	 * 
	 * @return
	 */
	public static String getUUID32() {
		return UUID.randomUUID().toString().replace("-", "").toUpperCase();
	}

	public static boolean isEmpty(String str) {
		return str == null || "".equals(str.trim());
	}

	public static boolean isNotEmpty(String str) {
		return str != null && !str.trim().isEmpty();
	}

	public static String string(Object obj) {
		if (obj == null) {
			return "";
		}
		return obj.toString();
	}

	/**
	 * 当obj为null或为""，初始化"0";
	 * 
	 * @param obj
	 * @return
	 */
	public static String initStr(String obj) {
		if (obj == null || "".equals(obj.trim())) {
			obj = "0";
		}
		return obj;
	}

	/**
	 * 获取文件扩展名
	 * 
	 * @param fileName
	 * @return
	 */
	public static String getExtention(String fileName) {
		int pos = fileName.lastIndexOf(".");
		if (pos > -1) {
			return fileName.substring(pos + 1);
		}
		return "";
	}

	/**
	 * 首字母大写
	 * 
	 * @param str
	 */
	public static String UpperHeadLetter(String str) {
		return str.substring(0, 1).toUpperCase() + str.substring(1);
	}

	/**
	 * 首字母小写...驼峰命名法
	 * 
	 * @param str
	 */
	public static String lowerHeadLetter(String str) {
		return str.substring(0, 1).toLowerCase() + str.substring(1);
	}

	/**
	 * 编码转换
	 * 
	 * @param fileName
	 * @return
	 */
	public static String toUtf8String(String fileName) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < fileName.length(); i++) {
			char c = fileName.charAt(i);
			if (c >= 0 && c <= 255) {
				sb.append(c);
			} else {
				byte[] b;
				try {
					b = Character.toString(c).getBytes("UTF-8");
				} catch (Exception ex) {
					b = new byte[0];
				}
				for (int j = 0; j < b.length; j++) {
					int k = b[j];
					if (k < 0)
						k += 256;
					sb.append("%" + Integer.toHexString(k).toUpperCase());
				}
			}
		}
		return sb.toString();
	}

	/**
	 * 将小写数字转换为大些 (支持二位数正常)
	 */
	public static String toUpperDigit(String original) {
		String[] arrUnit = { "", "十", "百", "千", "万" }; // 计量单位
		String[] arrNum = { "", "一", "二", "三", "四", "五", "六", "七", "八", "九" }; // 大写数字
		String[] strNum = original.split("|");
		int nNumCount = strNum.length - 2;
		String present = "";
		for (int i = 0; i < strNum.length; i++) {
			if (strNum[i] != null && !"".equals(strNum[i])) {
				int n = Integer.parseInt(strNum[i]);
				switch (n) {
				case 0:
					present += arrNum[n] + "" + arrUnit[nNumCount--];
					break;
				case 1:
					present += arrNum[n] + "" + arrUnit[nNumCount--];
					break;
				case 2:
					present += arrNum[n] + "" + arrUnit[nNumCount--];
					break;
				case 3:
					present += arrNum[n] + "" + arrUnit[nNumCount--];
					break;
				case 4:
					present += arrNum[n] + "" + arrUnit[nNumCount--];
					break;
				case 5:
					present += arrNum[n] + "" + arrUnit[nNumCount--];
					break;
				case 6:
					present += arrNum[n] + "" + arrUnit[nNumCount--];
					break;
				case 7:
					present += arrNum[n] + "" + arrUnit[nNumCount--];
					break;
				case 8:
					present += arrNum[n] + "" + arrUnit[nNumCount--];
					break;
				case 9:
					present += arrNum[n] + "" + arrUnit[nNumCount--];
					break;
				}
			}
		}
		return present;
	}

	/**
	 * 转化HTML标记 Add comments here.
	 * 
	 * @param content
	 * @return
	 */
	public static String formatHTMLTag(String content) {
		content = content.replaceAll("<", "&lt;");
		content = content.replaceAll(">", "&gt;");
		return content;
	}

	public static boolean hasLength(CharSequence str) {
		return ((str != null) && (str.length() > 0));
	}

	public static boolean hasLength(String str) {
		return ((str != null) && !str.isEmpty() && (str.length() > 0));
	}

	public static boolean hasText(CharSequence str) {
		if (!(hasLength(str))) {
			return false;
		}
		int strLen = str.length();
		for (int i = 0; i < strLen; ++i) {
			if (!(Character.isWhitespace(str.charAt(i)))) {
				return true;
			}
		}
		return false;
	}

	public static boolean hasText(String str) {
		return isNotEmpty(str);
	}

	public static boolean containsWhitespace(CharSequence str) {
		if (!(hasLength(str))) {
			return false;
		}
		int strLen = str.length();
		for (int i = 0; i < strLen; ++i) {
			if (Character.isWhitespace(str.charAt(i))) {
				return true;
			}
		}
		return false;
	}

	public static boolean containsWhitespace(String str) {
		return containsWhitespace((CharSequence) str);
	}

	public static String trimWhitespace(String str) {
		if (!(hasLength(str))) {
			return str;
		}
		StringBuilder sb = new StringBuilder(str);
		do {
			sb.deleteCharAt(0);

			if (sb.length() <= 0)
				break;
		} while (Character.isWhitespace(sb.charAt(0)));

		while ((sb.length() > 0) && (Character.isWhitespace(sb.charAt(sb.length() - 1)))) {
			sb.deleteCharAt(sb.length() - 1);
		}
		return sb.toString();
	}

	public static String trimAllWhitespace(String str) {
		if (!(hasLength(str))) {
			return str;
		}
		StringBuilder sb = new StringBuilder(str);
		int index = 0;
		while (sb.length() > index) {
			if (Character.isWhitespace(sb.charAt(index))) {
				sb.deleteCharAt(index);
			} else {
				++index;
			}
		}
		return sb.toString();
	}

	public static String trimLeadingWhitespace(String str) {
		if (!(hasLength(str))) {
			return str;
		}
		StringBuilder sb = new StringBuilder(str);
		while ((sb.length() > 0) && (Character.isWhitespace(sb.charAt(0)))) {
			sb.deleteCharAt(0);
		}
		return sb.toString();
	}

	public static String trimTrailingWhitespace(String str) {
		if (!(hasLength(str))) {
			return str;
		}
		StringBuilder sb = new StringBuilder(str);
		while ((sb.length() > 0) && (Character.isWhitespace(sb.charAt(sb.length() - 1)))) {
			sb.deleteCharAt(sb.length() - 1);
		}
		return sb.toString();
	}

	public static String trimLeadingCharacter(String str, char leadingCharacter) {
		if (!(hasLength(str))) {
			return str;
		}
		StringBuilder sb = new StringBuilder(str);
		while ((sb.length() > 0) && (sb.charAt(0) == leadingCharacter)) {
			sb.deleteCharAt(0);
		}
		return sb.toString();
	}

	public static String trimTrailingCharacter(String str, char trailingCharacter) {
		if (!(hasLength(str))) {
			return str;
		}
		StringBuilder sb = new StringBuilder(str);
		while ((sb.length() > 0) && (sb.charAt(sb.length() - 1) == trailingCharacter)) {
			sb.deleteCharAt(sb.length() - 1);
		}
		return sb.toString();
	}

	public static boolean startsWithIgnoreCase(String str, String prefix) {
		if ((str == null) || (prefix == null)) {
			return false;
		}
		if (str.startsWith(prefix)) {
			return true;
		}
		if (str.length() < prefix.length()) {
			return false;
		}
		String lcStr = str.substring(0, prefix.length()).toLowerCase();
		String lcPrefix = prefix.toLowerCase();
		return lcStr.equals(lcPrefix);
	}

	public static boolean endsWithIgnoreCase(String str, String suffix) {
		if ((str == null) || (suffix == null)) {
			return false;
		}
		if (str.endsWith(suffix)) {
			return true;
		}
		if (str.length() < suffix.length()) {
			return false;
		}

		String lcStr = str.substring(str.length() - suffix.length()).toLowerCase();
		String lcSuffix = suffix.toLowerCase();
		return lcStr.equals(lcSuffix);
	}

	public static boolean substringMatch(CharSequence str, int index, CharSequence substring) {
		for (int j = 0; j < substring.length(); ++j) {
			int i = index + j;
			if ((i >= str.length()) || (str.charAt(i) != substring.charAt(j))) {
				return false;
			}
		}
		return true;
	}

	public static int countOccurrencesOf(String str, String sub) {
		if ((str == null) || (sub == null) || (str.length() == 0) || (sub.length() == 0)) {
			return 0;
		}
		int count = 0;
		int pos = 0;
		int idx;
		while ((idx = str.indexOf(sub, pos)) != -1) {
			++count;
			pos = idx + sub.length();
		}
		return count;
	}

	public static String replace(String inString, String oldPattern, String newPattern) {
		if ((!(hasLength(inString))) || (!(hasLength(oldPattern))) || (newPattern == null)) {
			return inString;
		}
		StringBuilder sb = new StringBuilder();
		int pos = 0;
		int index = inString.indexOf(oldPattern);

		int patLen = oldPattern.length();
		while (index >= 0) {
			sb.append(inString.substring(pos, index));
			sb.append(newPattern);
			pos = index + patLen;
			index = inString.indexOf(oldPattern, pos);
		}
		sb.append(inString.substring(pos));

		return sb.toString();
	}

	public static String delete(String inString, String pattern) {
		return replace(inString, pattern, "");
	}

	public static String deleteAny(String inString, String charsToDelete) {
		if ((!(hasLength(inString))) || (!(hasLength(charsToDelete)))) {
			return inString;
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < inString.length(); ++i) {
			char c = inString.charAt(i);
			if (charsToDelete.indexOf(c) == -1) {
				sb.append(c);
			}
		}
		return sb.toString();
	}

	public static String quote(String str) {
		return ((str != null) ? "'" + str + "'" : null);
	}

	public static Object quoteIfString(Object obj) {
		return ((obj instanceof String) ? quote((String) obj) : obj);
	}

	public static String unqualify(String qualifiedName) {
		return unqualify(qualifiedName, '.');
	}

	public static String unqualify(String qualifiedName, char separator) {
		return qualifiedName.substring(qualifiedName.lastIndexOf(separator) + 1);
	}

	public static String capitalize(String str) {
		return changeFirstCharacterCase(str, true);
	}

	public static String uncapitalize(String str) {
		return changeFirstCharacterCase(str, false);
	}

	private static String changeFirstCharacterCase(String str, boolean capitalize) {
		if ((str == null) || (str.length() == 0)) {
			return str;
		}
		StringBuilder sb = new StringBuilder(str.length());
		if (capitalize) {
			sb.append(Character.toUpperCase(str.charAt(0)));
		} else {
			sb.append(Character.toLowerCase(str.charAt(0)));
		}
		sb.append(str.substring(1));
		return sb.toString();
	}

	public static String getFilename(String path) {
		if (path == null) {
			return null;
		}
		int separatorIndex = path.lastIndexOf("/");
		return ((separatorIndex != -1) ? path.substring(separatorIndex + 1) : path);
	}

	public static String getFilenameExtension(String path) {
		if (path == null) {
			return null;
		}
		int sepIndex = path.lastIndexOf(46);
		return ((sepIndex != -1) ? path.substring(sepIndex + 1) : null);
	}

	public static String stripFilenameExtension(String path) {
		if (path == null) {
			return null;
		}
		int sepIndex = path.lastIndexOf(46);
		return ((sepIndex != -1) ? path.substring(0, sepIndex) : path);
	}

	public static String applyRelativePath(String path, String relativePath) {
		int separatorIndex = path.lastIndexOf("/");
		if (separatorIndex != -1) {
			String newPath = path.substring(0, separatorIndex);
			if (!(relativePath.startsWith("/"))) {
				newPath = newPath + "/";
			}
			return newPath + relativePath;
		}

		return relativePath;
	}

	
	public static Locale parseLocaleString(String localeString) {
		String[] parts = tokenizeToStringArray(localeString, "_ ", false, false);
		String language = (parts.length > 0) ? parts[0] : "";
		String country = (parts.length > 1) ? parts[1] : "";
		String variant = "";
		if (parts.length >= 2) {
			int endIndexOfCountryCode = localeString.indexOf(country) + country.length();

			variant = trimLeadingWhitespace(localeString.substring(endIndexOfCountryCode));
			if (variant.startsWith("_")) {
				variant = trimLeadingCharacter(variant, '_');
			}
		}
		return ((language.length() > 0) ? new Locale(language, country, variant) : null);
	}

	public static String toLanguageTag(Locale locale) {
		return locale.getLanguage() + ((hasText(locale.getCountry())) ? "-" + locale.getCountry() : "");
	}


	public static String[] toStringArray(Collection<String> collection) {
		if (collection == null) {
			return null;
		}
		return ((String[]) collection.toArray(new String[collection.size()]));
	}

	public static String[] toStringArray(Enumeration<String> enumeration) {
		if (enumeration == null) {
			return null;
		}
		List list = (List) Collections.list(enumeration);
		return ((String[]) list.toArray(new String[list.size()]));
	}

	public static String[] split(String toSplit, String delimiter) {
		if ((!(hasLength(toSplit))) || (!(hasLength(delimiter)))) {
			return null;
		}
		int offset = toSplit.indexOf(delimiter);
		if (offset < 0) {
			return null;
		}
		String beforeDelimiter = toSplit.substring(0, offset);
		String afterDelimiter = toSplit.substring(offset + delimiter.length());
		return new String[] { beforeDelimiter, afterDelimiter };
	}

	public static String[] tokenizeToStringArray(String str, String delimiters) {
		return tokenizeToStringArray(str, delimiters, true, true);
	}

	public static String[] tokenizeToStringArray(String str, String delimiters, boolean trimTokens, boolean ignoreEmptyTokens) {
		if (str == null) {
			return null;
		}
		StringTokenizer st = new StringTokenizer(str, delimiters);
		List tokens = new ArrayList();
		while (st.hasMoreTokens()) {
			String token = st.nextToken();
			if (trimTokens) {
				token = token.trim();
			}
			if ((!(ignoreEmptyTokens)) || (token.length() > 0)) {
				tokens.add(token);
			}
		}
		return toStringArray(tokens);
	}

	public static String[] delimitedListToStringArray(String str, String delimiter) {
		return delimitedListToStringArray(str, delimiter, null);
	}

	public static String[] delimitedListToStringArray(String str, String delimiter, String charsToDelete) {
		if (str == null) {
			return new String[0];
		}
		if (delimiter == null) {
			return new String[] { str };
		}
		List result = new ArrayList();
		if ("".equals(delimiter)) {
			for (int i = 0; i < str.length(); ++i)
				result.add(deleteAny(str.substring(i, i + 1), charsToDelete));
		} else {
			int pos = 0;
			int delPos;
			while ((delPos = str.indexOf(delimiter, pos)) != -1) {
				result.add(deleteAny(str.substring(pos, delPos), charsToDelete));
				pos = delPos + delimiter.length();
			}
			if ((str.length() > 0) && (pos <= str.length())) {
				result.add(deleteAny(str.substring(pos), charsToDelete));
			}
		}
		return toStringArray(result);
	}

	public static String[] commaDelimitedListToStringArray(String str) {
		return delimitedListToStringArray(str, ",");
	}

	public static Set<String> commaDelimitedListToSet(String str) {
		Set set = new TreeSet();
		String[] tokens = commaDelimitedListToStringArray(str);
		for (String token : tokens) {
			set.add(token);
		}
		return set;
	}

	public static String URLSLASH = "/";

	public static String addSlash(String str) {
		if (isNotEmpty(str)) {
			int lastIdx = str.lastIndexOf(URLSLASH);
			if (lastIdx < 1) {
				str += URLSLASH;
			}
			str = str.contains("\\") ? str.replaceAll("\\\\+", URLSLASH) : str;
			str = str.contains("//") ? str.replaceAll("//+", URLSLASH) : str;
		}
		return str;
	}
	
	/**
     * 不够位数的在前面补0，保留num的长度位数字
     * @param code
     * @return
     */
    public static String autoGenericCode(String code, int num) {
        String result = "";      
        result = String.format("%0" + num + "d", Integer.parseInt(code));
        return result;
    }
    
    public static String toString(Object obj) {
    	if(obj != null)
    		return obj.toString();
    	return "";
    }
    
    public static Object toObject(String str) {
    	if(isEmpty(str)) 
    		return null;
    	return str;
    }
    
    public static boolean isEmpty(List<String> params) {
		return params.stream().anyMatch(StringUtils::isEmpty);
	}
	
	public static boolean isEmpty(String... params) {
		List<String> paramList = Arrays.asList(params);
		return isEmpty(paramList);
	}

	// 根据Unicode编码完美的判断中文汉字和符号
	public static boolean isChinese(char c) {
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
		if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {
			return true;
		}
		return false;
	}

	// 完整的判断中文汉字和符号
	public static boolean isChinese(String strName) {
		char[] ch = strName.toCharArray();
		for (int i = 0; i < ch.length; i++) {
			char c = ch[i];
			if (isChinese(c)) {
				return true;
			}
		}
		return false;
	}

	// 只能判断部分CJK字符（CJK统一汉字）
	public static boolean isChineseByREG(String str) {
		if (str == null) {
			return false;
		}
		Pattern pattern = Pattern.compile("[\\u4E00-\\u9FBF]+");
		return pattern.matcher(str.trim()).find();
	}

	// 只能判断部分CJK字符（CJK统一汉字）
	public static boolean isChineseByName(String str) {
		if (str == null) {
			return false;
		}
		// 大小写不同：\\p 表示包含，\\P 表示不包含
		// \\p{Cn} 的意思为 Unicode 中未被定义字符的编码，\\P{Cn} 就表示 Unicode中已经被定义字符的编码
		String reg = "\\p{InCJK Unified Ideographs}&&\\P{Cn}";
		Pattern pattern = Pattern.compile(reg);
		return pattern.matcher(str.trim()).find();
	}

	public static String buildUrlWithParameter(String url, Map<String, String> params) {
    	StringBuffer sb = new StringBuffer(url);
    	sb.append("?");
    	String code = "&";
    	params.forEach((k, v) -> {
    		sb.append(k).append("=").append(v).append(code);
		});
    	String result = sb.toString();
    	if(result.endsWith(code)) {
    		result = result.substring(0, result.length() - 1);
		}
		return result;
	}

	public static String replaceBrace(String source, String... args) {
        StringBuilder sb = new StringBuilder(source);
        for (int i = 0; i < args.length; i++) {
            sb.replace(sb.indexOf("{}"), sb.indexOf("{}") + 2, args[i]);
        }
        return sb.toString();
    }
	
	public static Map<String, String> paramToMap(String content) {
		Map<String, String> param = new HashMap<>();
		String[] arr = content.split("&");
		for(String item : arr) {
			String[] arr2 = item.split("=");
			if(arr2.length == 2) {
				String key = arr2[0].trim();
				String val = arr2[1].trim();
				param.put(key, val);
			}
		}
		return param;
	}

	public static String replaceWithPatientNameOrDoctorName(String msg, String patientName, String doctorName) {
		patientName = isEmpty(patientName) ? "" : patientName;
		doctorName = isEmpty(doctorName) ? "" : doctorName;
		if(msg.contains(GlobalConstants.PATIENT_NAME)) {
			msg = msg.replace(GlobalConstants.PATIENT_NAME, patientName);
		}
		if(msg.contains(GlobalConstants.DOCTOR_NAME1)) {
			msg = msg.replace(GlobalConstants.DOCTOR_NAME1, doctorName);
		}
		return msg;
	}

	public static int getRandomNumber(int max) {
		Random r=new Random();
		int i=r.nextInt(8)+1;
		return i;
	}

	/**
	 * 截取括号
	 * @param answerContent
	 * @return
	 */
	public static String removeBracketsChar(String answerContent) {
		int index1 = answerContent.indexOf("(");
		int index2 = answerContent.indexOf("（");
		int index = index1 == -1 ? index2 : index1;
		if(index != -1) {
			answerContent = answerContent.substring(0, index);
		}
		return answerContent;
	}
}
