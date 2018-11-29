package com.alpha.server.rpc.his.pojo;

import com.alpha.commons.core.pojo.BasePojo;
import com.alpha.commons.core.util.JSONDateSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * 住院-新生儿信息
 */
@Entity
@Table(name = "hospitalized_new_ill_child")
public class HospitalizedNewIllChild extends BasePojo<HospitalizedNewIllChild> implements Serializable {
    public static interface SaveGroup {
    }

    public static interface ModifyGroup {
    }

    /**
     * id
     */
    @Id
    @Column(name = "id")
    private Long id;

    /**
     * 医院编码
     */
    @Column(name = "hospital_code")
    private String hospitalCode;

    /**
     * 门诊号
     */
    @Column(name = "out_patient_no")
    private String outPatientNo;

    /**
     * 住院号
     */
    @NotBlank(message="住院号不能为空!",groups = {HospitalizedNewIllChild.SaveGroup.class})
    @Column(name = "hosNo")
    private String hosno;

    @Column(name = "noticeId")
    private String noticeId;

    /**
     * 主诉
     */
    @NotBlank(message="主诉不能为空!",groups = {HospitalizedNewIllChild.SaveGroup.class})
    @Column(name = "mainSymp")
    private String mainsymp;

    /**
     * 排胎便情况
     */
    @Column(name = "meconium")
    private String meconium;

    /**
     * 脐带第几天脱落
     */
    @Column(name = "ucdown")
    private String ucdown;

    /**
     * 残端
     */
    @Column(name = "stump")
    private String stump;

    /**
     * 脐轮红肿
     */
    @Column(name = "navel")
    private String navel;

    /**
     * 脐部分泌物
     */
    @Column(name = "secretion")
    private String secretion;

    /**
     * 吃奶情况
     */
    @Column(name = "nurse")
    private String nurse;

    /**
     * 哭声大小
     */
    @Column(name = "cayN")
    private String cayn;

    /**
     * 体温
     */
    @Column(name = "temperature")
    private String temperature;

    /**
     * 黄疸
     */
    @Column(name = "isJaundice")
    private String isjaundice;

    /**
     * 青紫
     */
    @Column(name = "isCyanose")
    private String iscyanose;

    /**
     * 抽搐
     */
    @Column(name = "isTwitch")
    private String istwitch;

    /**
     * 呼吸困难
     */
    @Column(name = "isDyspnea")
    private String isdyspnea;

    /**
     * 大小便情况
     */
    @Column(name = "stool")
    private String stool;

    /**
     * 治疗经过
     */
    @Column(name = "tp")
    private String tp;

    /**
     * 出生体重
     */
    @Column(name = "bmilkMonth")
    private String bmilkmonth;

    /**
     * 呼吸开始时间
     */
    @Column(name = "foodTime")
    private String foodtime;

    /**
     * 哭声
     */
    @Column(name = "cayO")
    private String cayo;

    /**
     * 皮肤颜色
     */
    @Column(name = "skinColor")
    private String skincolor;

    /**
     * 四肢张力
     */
    @Column(name = "toe")
    private String toe;

    /**
     * 羊水吸入史
     */
    @Column(name = "afi")
    private String afi;

    /**
     * 1分钟
     */
    @Column(name = "oneTime")
    private String onetime;

    /**
     * 5分钟
     */
    @Column(name = "fiveTime")
    private String fivetime;

    /**
     * 窒息
     */
    @Column(name = "isAsphyxia")
    private String isasphyxia;

    /**
     * 分钟窒息原因
     */
    @Column(name = "timeAsphyxia")
    private String timeasphyxia;

    /**
     * 抢救措施
     */
    @Column(name = "resultRm")
    private String resultrm;

    /**
     * 生理性黄疸几天
     */
    @Column(name = "jaundiceTime")
    private String jaundicetime;

    /**
     * 几天高潮
     */
    @Column(name = "jaundiceUpTime")
    private String jaundiceuptime;

    /**
     * 几天消失
     */
    @Column(name = "jaundiceDTime")
    private String jaundicedtime;

    /**
     * 程度
     */
    @Column(name = "djaundiceDgree")
    private String djaundicedgree;

    /**
     * 开奶时间
     */
    @Column(name = "omTime")
    private String omtime;

    /**
     * 喂养方式
     */
    @Column(name = "omType")
    private String omtype;

    /**
     * 新生儿筛查
     */
    @Column(name = "nc")
    private String nc;

    /**
     * 卡介苗接种
     */
    @Column(name = "isBCG")
    private String isbcg;

    /**
     * 乙肝接种
     */
    @Column(name = "isHB")
    private String ishb;

    /**
     * 乙肝高效价免疫球蛋白
     */
    @Column(name = "isHBHEI")
    private String ishbhei;

    /**
     * 父亲健康状况
     */
    @Column(name = "fatherHeal")
    private String fatherheal;

    /**
     * 父亲姓名
     */
    @Column(name = "fatherName")
    private String fathername;

    /**
     * 父亲身份证号
     */
    @Column(name = "fatherIdNo")
    private String fatheridno;

    /**
     * 父亲血型
     */
    @Column(name = "fatherBType")
    private String fatherbtype;

    /**
     * 患儿母亲姓名
     */
    @Column(name = "momName")
    private String momname;

    /**
     * 母亲年龄
     */
    @Column(name = "momAge")
    private String momage;

    /**
     * 母亲健康状况
     */
    @Column(name = "momHeal")
    private String momheal;

    /**
     * 母亲身份证号
     */
    @Column(name = "momIdNo")
    private String momidno;

    /**
     * 母亲血型
     */
    @Column(name = "momBType")
    private String mombtype;

    /**
     * 是否健康
     */
    @Column(name = "isHealth")
    private String ishealth;

    /**
     * 接触史
     */
    @Column(name = "ch")
    private String ch;

    /**
     * 接触情况
     */
    @Column(name = "cha")
    private String cha;

    /**
     * 第几胎
     */
    @Column(name = "wjt")
    private String wjt;

    /**
     * 第几产
     */
    @Column(name = "wjc")
    private String wjc;

    /**
     * 妊娠周数
     */
    @Column(name = "rrweek")
    private String rrweek;

    /**
     * 双胎大小
     */
    @Column(name = "dcSize")
    private String dcsize;

    /**
     * 分娩地点
     */
    @Column(name = "rrAdd")
    private String rradd;

    /**
     * 接生用具
     */
    @Column(name = "babyType")
    private String babytype;

    /**
     * 分娩方式
     */
    @Column(name = "rrType")
    private String rrtype;

    /**
     * 总产程时长
     */
    @Column(name = "allTime")
    private String alltime;

    /**
     * 第二产程时长
     */
    @Column(name = "secondTime")
    private String secondtime;

    /**
     * 羊水性质
     */
    @Column(name = "afType")
    private String aftype;

    /**
     * 羊水早破
     */
    @Column(name = "isAfb")
    private String isafb;

    /**
     * 胎盘异常
     */
    @Column(name = "isPla")
    private String ispla;

    /**
     * 脐带异常
     */
    @Column(name = "isuca")
    private String isuca;

    /**
     * 妊娠次数
     */
    @Column(name = "rrTimes")
    private String rrtimes;

    /**
     * 自然流产次数
     */
    @Column(name = "zrlTimes")
    private String zrltimes;

    /**
     * 人工流产次数
     */
    @Column(name = "rglTimes")
    private String rgltimes;

    /**
     * 死胎几个
     */
    @Column(name = "wst")
    private String wst;

    /**
     * 死产几个
     */
    @Column(name = "wsc")
    private String wsc;

    /**
     * 家中共有几个娃
     */
    @Column(name = "hchild")
    private String hchild;

    /**
     * 分别年龄
     */
    @Column(name = "childAges")
    private String childages;

    /**
     * 娃娃身体状况
     */
    @Column(name = "childHeal")
    private String childheal;

    /**
     * 家族遗传病史或传染病
     */
    @Column(name = "infectiousDis")
    private String infectiousdis;

    /**
     * 填表日期
     */
    @Column(name = "dTime")
    private String dtime;

    /**
     * 填表者
     */
    @Column(name = "dAuthor")
    private String dauthor;

    /**
     * 填表与患儿关系
     */
    @Column(name = "drShip")
    private String drship;

    /**
     * apgar评价
     */
    private String apgar;
    /**
     * 父亲年龄
     */
    private String fatherAge;
    /**
     * 分娩前用药
     */
    private String predelivery;
    /**
     * 剖宫产原因
     */
    private String cesareanSection;
    /**
     * 早产原因
     */
    private String prematureBirth;
    /**
     * 床号
     */
    @NotBlank(message="床号不能为空!",groups = {HospitalizedNewIllChild.SaveGroup.class})
    private String bedNumber;
    /**
     * 出生时间
     */
    @NotNull(message="出生时间不能为空!",groups = {HospitalizedNewIllChild.SaveGroup.class})
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date birthTime;

    /**
     * 填表人联系电话
     */
    @Column(name = "contactPhone")
    private String contactPhone;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHospitalCode() {
        return hospitalCode;
    }

    public void setHospitalCode(String hospitalCode) {
        this.hospitalCode = hospitalCode == null ? null : hospitalCode.trim();
    }

    public String getOutPatientNo() {
        return outPatientNo;
    }

    public void setOutPatientNo(String outPatientNo) {
        this.outPatientNo = outPatientNo == null ? null : outPatientNo.trim();
    }

    public String getMainsymp() {
        return mainsymp;
    }

    public void setMainsymp(String mainsymp) {
        this.mainsymp = mainsymp == null ? null : mainsymp.trim();
    }

    public String getMeconium() {
        return meconium;
    }

    public void setMeconium(String meconium) {
        this.meconium = meconium == null ? null : meconium.trim();
    }

    public String getUcdown() {
        return ucdown;
    }

    public void setUcdown(String ucdown) {
        this.ucdown = ucdown == null ? null : ucdown.trim();
    }

    public String getStump() {
        return stump;
    }

    public void setStump(String stump) {
        this.stump = stump == null ? null : stump.trim();
    }

    public String getNavel() {
        return navel;
    }

    public void setNavel(String navel) {
        this.navel = navel == null ? null : navel.trim();
    }

    public String getSecretion() {
        return secretion;
    }

    public void setSecretion(String secretion) {
        this.secretion = secretion == null ? null : secretion.trim();
    }

    public String getNurse() {
        return nurse;
    }

    public void setNurse(String nurse) {
        this.nurse = nurse == null ? null : nurse.trim();
    }

    public String getCayn() {
        return cayn;
    }

    public void setCayn(String cayn) {
        this.cayn = cayn == null ? null : cayn.trim();
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature == null ? null : temperature.trim();
    }

    public String getIsjaundice() {
        return isjaundice;
    }

    public void setIsjaundice(String isjaundice) {
        this.isjaundice = isjaundice == null ? null : isjaundice.trim();
    }

    public String getIscyanose() {
        return iscyanose;
    }

    public void setIscyanose(String iscyanose) {
        this.iscyanose = iscyanose == null ? null : iscyanose.trim();
    }

    public String getIstwitch() {
        return istwitch;
    }

    public void setIstwitch(String istwitch) {
        this.istwitch = istwitch == null ? null : istwitch.trim();
    }

    public String getIsdyspnea() {
        return isdyspnea;
    }

    public void setIsdyspnea(String isdyspnea) {
        this.isdyspnea = isdyspnea == null ? null : isdyspnea.trim();
    }

    public String getStool() {
        return stool;
    }

    public void setStool(String stool) {
        this.stool = stool == null ? null : stool.trim();
    }

    public String getTp() {
        return tp;
    }

    public void setTp(String tp) {
        this.tp = tp == null ? null : tp.trim();
    }

    public String getBmilkmonth() {
        return bmilkmonth;
    }

    public void setBmilkmonth(String bmilkmonth) {
        this.bmilkmonth = bmilkmonth == null ? null : bmilkmonth.trim();
    }

    public String getFoodtime() {
        return foodtime;
    }

    public void setFoodtime(String foodtime) {
        this.foodtime = foodtime == null ? null : foodtime.trim();
    }

    public String getCayo() {
        return cayo;
    }

    public void setCayo(String cayo) {
        this.cayo = cayo == null ? null : cayo.trim();
    }

    public String getSkincolor() {
        return skincolor;
    }

    public void setSkincolor(String skincolor) {
        this.skincolor = skincolor == null ? null : skincolor.trim();
    }

    public String getToe() {
        return toe;
    }

    public void setToe(String toe) {
        this.toe = toe == null ? null : toe.trim();
    }

    public String getAfi() {
        return afi;
    }

    public void setAfi(String afi) {
        this.afi = afi == null ? null : afi.trim();
    }

    public String getOnetime() {
        return onetime;
    }

    public void setOnetime(String onetime) {
        this.onetime = onetime == null ? null : onetime.trim();
    }

    public String getFivetime() {
        return fivetime;
    }

    public void setFivetime(String fivetime) {
        this.fivetime = fivetime == null ? null : fivetime.trim();
    }

    public String getIsasphyxia() {
        return isasphyxia;
    }

    public void setIsasphyxia(String isasphyxia) {
        this.isasphyxia = isasphyxia == null ? null : isasphyxia.trim();
    }

    public String getTimeasphyxia() {
        return timeasphyxia;
    }

    public void setTimeasphyxia(String timeasphyxia) {
        this.timeasphyxia = timeasphyxia == null ? null : timeasphyxia.trim();
    }

    public String getResultrm() {
        return resultrm;
    }

    public void setResultrm(String resultrm) {
        this.resultrm = resultrm == null ? null : resultrm.trim();
    }

    public String getJaundicetime() {
        return jaundicetime;
    }

    public void setJaundicetime(String jaundicetime) {
        this.jaundicetime = jaundicetime == null ? null : jaundicetime.trim();
    }

    public String getJaundiceuptime() {
        return jaundiceuptime;
    }

    public void setJaundiceuptime(String jaundiceuptime) {
        this.jaundiceuptime = jaundiceuptime == null ? null : jaundiceuptime.trim();
    }

    public String getJaundicedtime() {
        return jaundicedtime;
    }

    public void setJaundicedtime(String jaundicedtime) {
        this.jaundicedtime = jaundicedtime == null ? null : jaundicedtime.trim();
    }

    public String getDjaundicedgree() {
        return djaundicedgree;
    }

    public void setDjaundicedgree(String djaundicedgree) {
        this.djaundicedgree = djaundicedgree == null ? null : djaundicedgree.trim();
    }

    public String getOmtime() {
        return omtime;
    }

    public void setOmtime(String omtime) {
        this.omtime = omtime == null ? null : omtime.trim();
    }

    public String getOmtype() {
        return omtype;
    }

    public void setOmtype(String omtype) {
        this.omtype = omtype == null ? null : omtype.trim();
    }

    public String getNc() {
        return nc;
    }

    public void setNc(String nc) {
        this.nc = nc == null ? null : nc.trim();
    }

    public String getIsbcg() {
        return isbcg;
    }

    public void setIsbcg(String isbcg) {
        this.isbcg = isbcg == null ? null : isbcg.trim();
    }

    public String getIshb() {
        return ishb;
    }

    public void setIshb(String ishb) {
        this.ishb = ishb == null ? null : ishb.trim();
    }

    public String getIshbhei() {
        return ishbhei;
    }

    public void setIshbhei(String ishbhei) {
        this.ishbhei = ishbhei == null ? null : ishbhei.trim();
    }

    public String getFatherheal() {
        return fatherheal;
    }

    public void setFatherheal(String fatherheal) {
        this.fatherheal = fatherheal == null ? null : fatherheal.trim();
    }

    public String getFathername() {
        return fathername;
    }

    public void setFathername(String fathername) {
        this.fathername = fathername == null ? null : fathername.trim();
    }

    public String getFatheridno() {
        return fatheridno;
    }

    public void setFatheridno(String fatheridno) {
        this.fatheridno = fatheridno == null ? null : fatheridno.trim();
    }

    public String getFatherbtype() {
        return fatherbtype;
    }

    public void setFatherbtype(String fatherbtype) {
        this.fatherbtype = fatherbtype == null ? null : fatherbtype.trim();
    }

    public String getMomname() {
        return momname;
    }

    public void setMomname(String momname) {
        this.momname = momname == null ? null : momname.trim();
    }

    public String getMomage() {
        return momage;
    }

    public void setMomage(String momage) {
        this.momage = momage == null ? null : momage.trim();
    }

    public String getMomheal() {
        return momheal;
    }

    public void setMomheal(String momheal) {
        this.momheal = momheal == null ? null : momheal.trim();
    }

    public String getMomidno() {
        return momidno;
    }

    public void setMomidno(String momidno) {
        this.momidno = momidno == null ? null : momidno.trim();
    }

    public String getMombtype() {
        return mombtype;
    }

    public void setMombtype(String mombtype) {
        this.mombtype = mombtype == null ? null : mombtype.trim();
    }

    public String getIshealth() {
        return ishealth;
    }

    public void setIshealth(String ishealth) {
        this.ishealth = ishealth == null ? null : ishealth.trim();
    }

    public String getCh() {
        return ch;
    }

    public void setCh(String ch) {
        this.ch = ch == null ? null : ch.trim();
    }

    public String getCha() {
        return cha;
    }

    public void setCha(String cha) {
        this.cha = cha == null ? null : cha.trim();
    }

    public String getWjt() {
        return wjt;
    }

    public void setWjt(String wjt) {
        this.wjt = wjt == null ? null : wjt.trim();
    }

    public String getWjc() {
        return wjc;
    }

    public void setWjc(String wjc) {
        this.wjc = wjc == null ? null : wjc.trim();
    }

    public String getRrweek() {
        return rrweek;
    }

    public void setRrweek(String rrweek) {
        this.rrweek = rrweek == null ? null : rrweek.trim();
    }

    public String getDcsize() {
        return dcsize;
    }

    public void setDcsize(String dcsize) {
        this.dcsize = dcsize == null ? null : dcsize.trim();
    }

    public String getRradd() {
        return rradd;
    }

    public void setRradd(String rradd) {
        this.rradd = rradd == null ? null : rradd.trim();
    }

    public String getBabytype() {
        return babytype;
    }

    public void setBabytype(String babytype) {
        this.babytype = babytype == null ? null : babytype.trim();
    }

    public String getRrtype() {
        return rrtype;
    }

    public void setRrtype(String rrtype) {
        this.rrtype = rrtype == null ? null : rrtype.trim();
    }

    public String getAlltime() {
        return alltime;
    }

    public void setAlltime(String alltime) {
        this.alltime = alltime == null ? null : alltime.trim();
    }

    public String getSecondtime() {
        return secondtime;
    }

    public void setSecondtime(String secondtime) {
        this.secondtime = secondtime == null ? null : secondtime.trim();
    }

    public String getAftype() {
        return aftype;
    }

    public void setAftype(String aftype) {
        this.aftype = aftype == null ? null : aftype.trim();
    }

    public String getIsafb() {
        return isafb;
    }

    public void setIsafb(String isafb) {
        this.isafb = isafb == null ? null : isafb.trim();
    }

    public String getIspla() {
        return ispla;
    }

    public void setIspla(String ispla) {
        this.ispla = ispla == null ? null : ispla.trim();
    }

    public String getIsuca() {
        return isuca;
    }

    public void setIsuca(String isuca) {
        this.isuca = isuca == null ? null : isuca.trim();
    }

    public String getRrtimes() {
        return rrtimes;
    }

    public void setRrtimes(String rrtimes) {
        this.rrtimes = rrtimes == null ? null : rrtimes.trim();
    }

    public String getZrltimes() {
        return zrltimes;
    }

    public void setZrltimes(String zrltimes) {
        this.zrltimes = zrltimes == null ? null : zrltimes.trim();
    }

    public String getRgltimes() {
        return rgltimes;
    }

    public void setRgltimes(String rgltimes) {
        this.rgltimes = rgltimes == null ? null : rgltimes.trim();
    }

    public String getWst() {
        return wst;
    }

    public void setWst(String wst) {
        this.wst = wst == null ? null : wst.trim();
    }

    public String getWsc() {
        return wsc;
    }

    public void setWsc(String wsc) {
        this.wsc = wsc == null ? null : wsc.trim();
    }

    public String getHchild() {
        return hchild;
    }

    public void setHchild(String hchild) {
        this.hchild = hchild == null ? null : hchild.trim();
    }

    public String getChildages() {
        return childages;
    }

    public void setChildages(String childages) {
        this.childages = childages == null ? null : childages.trim();
    }

    public String getChildheal() {
        return childheal;
    }

    public void setChildheal(String childheal) {
        this.childheal = childheal == null ? null : childheal.trim();
    }

    public String getInfectiousdis() {
        return infectiousdis;
    }

    public void setInfectiousdis(String infectiousdis) {
        this.infectiousdis = infectiousdis == null ? null : infectiousdis.trim();
    }

    public String getDtime() {
        return dtime;
    }

    public void setDtime(String dtime) {
        this.dtime = dtime == null ? null : dtime.trim();
    }

    public String getDauthor() {
        return dauthor;
    }

    public void setDauthor(String dauthor) {
        this.dauthor = dauthor == null ? null : dauthor.trim();
    }

    public String getDrship() {
        return drship;
    }

    public void setDrship(String drship) {
        this.drship = drship == null ? null : drship.trim();
    }

    public String getHosno() {
        return hosno;
    }

    public void setHosno(String hosno) {
        this.hosno = hosno == null ? null : hosno.trim();
    }

    public String getApgar() {
        return apgar;
    }

    public void setApgar(String apgar) {
        this.apgar = apgar == null ? null : apgar.trim();
    }

    public String getFatherAge() {
        return fatherAge;
    }

    public void setFatherAge(String fatherAge) {
        this.fatherAge = fatherAge == null ? null : fatherAge.trim();
    }

    public String getPredelivery() {
        return predelivery;
    }

    public void setPredelivery(String predelivery) {
        this.predelivery = predelivery == null ? null : predelivery.trim();
    }

    public String getCesareanSection() {
        return cesareanSection;
    }

    public void setCesareanSection(String cesareanSection) {
        this.cesareanSection = cesareanSection == null ? null : cesareanSection.trim();
    }

    public String getPrematureBirth() {
        return prematureBirth;
    }

    public void setPrematureBirth(String prematureBirth) {
        this.prematureBirth = prematureBirth == null ? null : prematureBirth.trim();
    }

    public String getBedNumber() {
        return bedNumber;
    }

    public void setBedNumber(String bedNumber) {
        this.bedNumber = bedNumber  == null ? null : bedNumber.trim();
    }

    public Date getBirthTime() {
        return birthTime;
    }

    public void setBirthTime(Date birthTime) {
        this.birthTime = birthTime;
    }

    public String getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(String noticeId) {
        this.noticeId = noticeId;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }
}