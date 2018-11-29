
package com.alpha.his;


import com.alpha.commons.core.pojo.OutPatientInfo;
import com.alpha.commons.core.util.SoapUtil;
import com.alpha.commons.core.util.StaticHttpclientCall;
import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.InputStreamRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;

import javax.xml.namespace.QName;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;


public class WSTest {





	public static void main(String[] args) {

//		挂号信息
//		long time=System.currentTimeMillis();
//		String wsdl="http://172.16.240.124:7811/BS10015?wsdl";   //挂号信息
//		String resultXml = StaticHttpclientCall.registrationInfo(wsdl,"BS10015","8100447494","2018-06-03");
//		SoapUtil.parseSoapXml(resultXml);
//		System.out.println("请求话费时间："+(System.currentTimeMillis()-time));

//		住院病人信息
//				String wsdl="http://172.16.240.124:7807/BS10007?wsdl";    //住院病人信息1308155064
//		        String resultXml=	StaticHttpclientCall.hospitalizedInfo(wsdl,"BS10007","16110110033");
//		        SoapUtil.parseSoapXml(resultXml);


//   门诊病人信息
//			String wsdl="http://172.16.240.124:7807/BS10008?wsdl";	// 门诊病人信息
//		    String resultXml=    StaticHttpclientCall.outPatientParam(wsdl,"BS10008","1803294157");
//		    SoapUtil.parseSoapXml(resultXml);


//   一卡通信息
//			String wsdl="http://172.16.240.124:7807/BS10008?wsdl";	// 一卡通信息
//		    String resultXml= StaticHttpclientCall.outPatientNo(wsdl,"BS10008","18101816024");
//		    SoapUtil.parseSoapXml(resultXml);
//		List<String> list = SoapUtil.parseETYYxml(resultXml);
//		OutPatientInfo outPatientInfo=new OutPatientInfo();
//		String str=outPatientInfo.getClass().toString();
//		str=str.replace("class ","");
//		str=str.replace(" ","");
//		List<Object> objectList=SoapUtil.string2obj(list,str,SoapUtil.outpatientmap);
//		outPatientInfo=(OutPatientInfo)objectList.get(0);
//		System.out.println(outPatientInfo.getOutpatientNo());


		//住院通知单 调用
//		long a=System.currentTimeMillis();
//		String wsdl="http://172.16.240.124:7811/BS10023?wsdl";
//		String resultXml=  StaticHttpclientCall.hospitalizedNotice(wsdl,"BS10023","17091859007");
//				if(StringUtils.isBlank(resultXml)){
//			return ;
//		}
//		List<String> list = SoapUtil.parseETYYxml(resultXml);
//		HospitalizedNoticeNew notice=new HospitalizedNoticeNew();
//		String str=notice.getClass().toString();
//		str=str.replace("class ","");
//		str=str.replace(" ","");
//		List<Object> objectList=SoapUtil.string2obj(list,str,SoapUtil.mapNotice);
//		System.out.println(new Gson().toJson(objectList.get(0)));
//		System.out.println(System.currentTimeMillis()-a);




		//医院全天挂号信息
//		String wsdl="http://172.16.240.124:7811/BS10015?wsdl";   //挂号信息
//		String resultXml = StaticHttpclientCall.allNo(wsdl,"BS10015","15101624001","2018-05-14");
//		SoapUtil.parseSoapXml(resultXml);


//		String wsdl="http://172.16.240.124:7811/BS10015?wsdl";   //挂号信息
//		String resultXml = StaticHttpclientCall.getreport(wsdl,"BS10015","15101624001","2018-05-14");
//		SoapUtil.parseSoapXml(resultXml);

//		预约挂号

//		String wsdl="http://172.16.240.124:7811/BS10020?wsdl";   //挂号信息
//		String resultXml = StaticHttpclientCall.all(wsdl,"BS10020","2018-07-25 12:00:00","2018-07-25 12:00:59");
//		System.out.println(resultXml);
//		if(StringUtils.isBlank(resultXml)){
//			return ;
//		}
//		List<String> list = SoapUtil.parseETYYxml(resultXml);
//		HisRegisterYygh HisRegisterYyg=new HisRegisterYygh();
//		String str=HisRegisterYyg.getClass().toString();
//		str=str.replace("class ","");
//		str=str.replace(" ","");
//		List<Object> objectList=SoapUtil.string2obj(list,str,SoapUtil.ghmap);
//		List<HisRegisterYygh> type0=new ArrayList<>();
//		List<HisRegisterYygh> type1=new ArrayList<>();
//		List<HisRegisterYygh> type2=new ArrayList<>();
//		List<HisRegisterYygh> type3=new ArrayList<>();
//		objectList.stream().forEach( e -> {
//			HisRegisterYygh tem = (HisRegisterYygh) e;
//			if (tem.getDeptName().contains("内科综合") || tem.getDeptName().contains("内科专家综合")){
//				type0.add(tem);
//			};
//		});
//		type0.stream().forEach(it -> {
//			if(it.getType().equals(1)){
//				type1.add(it);
//			}
//			if(it.getType().equals(2)){
//				type2.add(it);
//			}
//			if(it.getType().equals(3)){
//				type2.add(it);
//			}
//		});
//
//		System.out.println("type1: "+type1.size());
//		System.out.println("type2: "+type2.size());
//		System.out.println("type3: "+type3.size());

//		String wsdl="http://172.16.241.176:17001/hdepc/services/hisWebService?WSDL";
//		String wsdl="http://183.62.200.106:9180/hdepc/services/hisWebService?wsdl";
		String wsdl="http://121.15.136.85:17001/hdepc/services/hisWebService?wsdl";
		push160(wsdl,"","15999621670" +
						"" +

						"","检验报告",
				"心标+血脂+肾功+肝代+肝酶+电八+骨代+葡萄糖",
				"点击查看报告","https://gmywz.zndaozhen.com/alpha/ywz_.html#/pre/guangming");




//		try {
//			Service service = new Service();
//			Call call = (Call) service.createCall();
//			call.setTargetEndpointAddress("http://192.168.29.61:19092/services/UserCardService?wsdl");
//			call.setOperationName(new QName("http://webService.his.alpha.com", "getEmrInfoUrlNew"));
//			call.setUseSOAPAction(true);
//			call.addParameter("request", XMLType.XSD_STRING,
//					javax.xml.rpc.ParameterMode.IN);//接口的参数
//			call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);//设置返回类型
//
//			String s = "";
//
//			String result = (String) call.invoke(new Object[]{s});
//			//给方法传递参数，并且调用方法
//			System.out.println(result);
//		} catch (Exception e) {
//		}

	}

	public static String getPushMsg() {
		StringBuffer stringBuffer = new StringBuffer();
//		try {
//			String curTime = String.valueOf(System.currentTimeMillis());
//			stringBuffer.append("<request><head><key>hisRemind_common_message</key><hospcode>111</hospcode><token></token><time>").append(curTime).append("</time></head><body>")
//					.append("<cardNo>").append(pushInfo.getCardNo()).append("</cardNo>")
//					.append("<phone>").append(pushInfo.getPhone()).append("</phone>")
//					.append("<title>").append(pushInfo.getTitle()).append("</title>").
//					append("<content>预问诊须知</content>").
//					append("<note>");
//			if ("内科专家综合副高".equals(pushInfo.getDep())) {
//				stringBuffer.append("就诊科室：小儿内科\n就诊医生：").append(pushInfo.getDep());
//			} else {
//				stringBuffer.append("就诊科室：").append(pushInfo.getDep()).append("\n就诊医生：").append(pushInfo.getDepDocter());
//			}
//
//			stringBuffer.append("\n就诊时间：").append(DateUtils.date2String(DateUtils.string2Date(pushInfo.getWatchingTime()), "yyyy-MM-dd")).
//					append("\n就诊人：").append(pushInfo.getUserName()).
//					append("\n\n\n点击此处开始预问诊 ﹥﹥﹥").
//					append("</note>").append("<url><![CDATA[").append(pushInfo.getUrl()).append("]]></url></body></request>");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}

		return stringBuffer.toString();
	}



	public static String servicetest(String wsdl, String input ){

		try {
			String soapRequestData = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:esb=\"http://esb.ewell.cc\">"
                    + "<soapenv:Header/>"
                    + " <soapenv:Body>"
                    + "<esb:HIPMessageServer>"
                    + "<esb:input>"
                    + input
                    + "</esb:input>"
                    + " </esb:HIPMessageServer>"
                    + " </soapenv:Body>"
                    + "</soapenv:Envelope>";

			PostMethod postMethod = new PostMethod(wsdl);

			byte[] b = soapRequestData.getBytes("utf-8");
			InputStream is = new ByteArrayInputStream(b, 0, b.length);
			RequestEntity re = new InputStreamRequestEntity(is, b.length,
                    "application/soap+xml; charset=utf-8");
			postMethod.setRequestEntity(re);

			HttpClient httpClient = new HttpClient();
			int statusCode = httpClient.executeMethod(postMethod);
			if(statusCode == 200) {
                String soapResponseData = postMethod.getResponseBodyAsString();
                return soapRequestData;
            }
            else {
                return "调用失败错误码：" + statusCode;
            }
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}


	public static String push160(String wsdl, String cardNo,String phone,String content,String title,String note, String url){
		try {
			StringBuffer stringBuffer=new StringBuffer();
			stringBuffer.append("<soapenv:Envelope\n" +
					"xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\"\n" +
					"xmlns:ser=\"http://service.bd.com/\">\n" +
					"<soapenv:Header/>\n" +
					"<soapenv:Body>\n" +
					"<ser:requestWS>\n" +
					"<request>\n" +
					"<![CDATA[\n" +
					"<request><head><key>hisRemind_common_message</key><hospcode>111</hospcode><token></token><time>20180425201845</time></head><body>");
			stringBuffer.append("<cardNo>"+cardNo+"</cardNo>");
			stringBuffer.append("<title>"+title+"</title>");
			stringBuffer.append("<content>"+content+"</content>");
			stringBuffer.append("<note>"+note+"</note>");
			stringBuffer.append("<phone>"+phone+"</phone>");
			stringBuffer.append("<url>"+url+"</url>");
			stringBuffer.append("</body></request>" +
					"]]>\n" +
					"</request>\n" +
					"</ser:requestWS>\n" +
					"</soapenv:Body>\n" +
					"</soapenv:Envelope>");
			String soapRequestData =stringBuffer.toString();
			PostMethod postMethod = new PostMethod(wsdl);
			byte[] b = soapRequestData.getBytes("utf-8");
			InputStream is = new ByteArrayInputStream(b, 0, b.length);
			RequestEntity re = new InputStreamRequestEntity(is, b.length,
					"application/soap+xml; charset=utf-8");
			postMethod.setRequestEntity(re);
			HttpClient httpClient = new HttpClient();
			int statusCode = httpClient.executeMethod(postMethod);
			if(statusCode == 200) {
				String soapResponseData = postMethod.getResponseBodyAsString();
				return soapResponseData;
			}
			else {

				return "调用失败错误码：" + statusCode;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}

