package com.alpha.server.rpc.his.pojo;

import com.alpha.commons.api.tencent.qcloud.Module.Base;
import com.alpha.commons.core.pojo.BasePojo;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * 住院-普通患儿信息
 */
@Entity
@Table(name = "hospitalized_common_ill_child")
public class HospitalizedCommonIllChild extends BasePojo<HospitalizedCommonIllChild> implements Serializable {
    public static interface SaveGroup{}
    public static interface ModifyGroup{}

    @Id
    @NotNull(message="主键不能为空!",groups = {ModifyGroup.class})
    @Column(name="id")
    private Long id;

    /**
     * 医院编码
     */
    @Column(name="hospital_code")
    private String hospitalCode;

    /**
     * 门诊号
     */
    @Column(name="out_patient_no")
    @NotBlank(message="门诊号不能为空!",groups = {SaveGroup.class})
    private String outPatientNo;

    /**
     * 住院号
     */
    @NotBlank(message="住院号不能为空!",groups = {SaveGroup.class})
    @Column(name="hosNo")
    private String hosno;

    /**
     * 通知单编码
     */
    @Column(name = "noticeId")
    private String noticeId;

    /**
     * 床号
     */
    @NotBlank(message="床号不能为空!",groups = {SaveGroup.class})
    @Column(name="bedNo")
    private String bedno;

    /**
     * 发病多久
     */
    @Column(name="morbidity_time")
    private String morbidityTime;

    /**
     * 是第几胎
     */
    @Column(name="aft")
    private String aft;

    /**
     * 是第几产
     */
    @Column(name="afp")
    private String afp;

    /**
     * 是否是足月
     */
    @Column(name="isTerm")
    private String isterm;

    /**
     * 预产期前还是后出生
     */
    @Column(name="abp")
    private String abp;

    /**
     * 顺产还是剖腹产
     */
    @Column(name="dcb")
    private String dcb;

    /**
     * 母孕期是否患病
     */
    @Column(name="isDisIm")
    private String isdisim;

    /**
     * 出生生的状况
     */
    @Column(name="bornStatus")
    private String bornstatus;

    /**
     * 出生时体重
     */
    @Column(name="bornWeight")
    private String bornweight;

    /**
     * 母乳几个月
     */
    @Column(name="bmilkMonth")
    private String bmilkmonth;

    /**
     * 几个月开始加辅食
     */
    @Column(name="foodTime")
    private String foodtime;

    /**
     * 几个月开始抬头
     */
    @Column(name="upTime")
    private String uptime;

    /**
     * 几个月会坐
     */
    @Column(name="seatTime")
    private String seattime;

    /**
     * 几个月会站
     */
    @Column(name="standTime")
    private String standtime;

    /**
     * 几个月会走
     */
    @Column(name="moveTime")
    private String movetime;

    /**
     * 几个月出牙
     */
    @Column(name="toothTime")
    private String toothtime;

    /**
     * 几个月会笑
     */
    @Column(name="laughTIme")
    private String laughtime;

    /**
     * 几个月会认人
     */
    @Column(name="lookTime")
    private String looktime;

    /**
     * 几个月会说话
     */
    @Column(name="speekTime")
    private String speektime;

    /**
     * 接种过哪种疫苗
     */
    @Column(name="vaccineInfo")
    private String vaccineinfo;

    /**
     * 患儿以前是否患病
     */
    @Column(name="sinceDis")
    private String sincedis;

    /**
     * 患病时间
     */
    @Column(name="sinceDisTime")
    private String sincedistime;

    /**
     * 患病住院医院名称
     */
    @Column(name="sinceHosName")
    private String sincehosname;

    /**
     * 患病住院几天
     */
    @Column(name="sinceHosTime")
    private String sincehostime;

    /**
     * 有无过敏史
     */
    @Column(name="isAh")
    private String isah;

    /**
     * 对什么过敏
     */
    @Column(name="whatAh")
    private String whatah;

    /**
     * 有无外伤或手术史
     */
    @Column(name="isOp")
    private String isop;

    /**
     * 外伤或手术史名称
     */
    @Column(name="opName")
    private String opname;

    /**
     * 患儿父亲姓名
     */
    @Column(name="fatherName")
    private String fathername;

    /**
     * 父亲年龄
     */
    @Column(name="fatherAge")
    private String fatherage;

    /**
     * 父亲健康状况
     */
    @Column(name="fatherHeal")
    private String fatherheal;

    /**
     * 父亲身份证号
     */
    @Column(name="fatherIdNo")
    private String fatheridno;

    /**
     * 患儿母亲姓名
     */
    @Column(name="momName")
    private String momname;

    /**
     * 母亲年龄
     */
    @Column(name="momAge")
    private String momage;

    /**
     * 母亲健康状况
     */
    @Column(name="momHeal")
    private String momheal;

    /**
     * 母亲身份证号
     */
    @Column(name="momidno")
    private String momidno;

    /**
     * 目前怀孕过几次
     */
    @Column(name="pregnanttimes")
    private String pregnanttimes;

    /**
     * 自然流产几次
     */
    @Column(name="abortiontimes")
    private String abortiontimes;

    /**
     * 人流几次
     */
    @Column(name="soptimes")
    private String soptimes;

    /**
     * 目前有几个孩子
     */
    @Column(name="wchild")
    private String wchild;

    /**
     * 几个孩子分别多大
     */
    @Column(name="childage")
    private String childage;

    /**
     * 身体状态
     */
    @Column(name="childheal")
    private String childheal;

    /**
     * 家中是有传染病或遗传病
     */
    @Column(name="infectiousdis")
    private String infectiousdis;

    /**
     * 填表日期
     */
    @Column(name="dtime")
    private String dtime;

    /**
     * 填表者
     */
    @Column(name="dauthor")
    private String dauthor;

    /**
     * 填表与患儿关系
     */
    @Column(name="drship")
    private String drship;

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

    public String getBedno() {
        return bedno;
    }

    public void setBedno(String bedno) {
        this.bedno = bedno == null ? null : bedno.trim();
    }

    public String getMorbidityTime() {
        return morbidityTime;
    }

    public void setMorbidityTime(String morbidityTime) {
        this.morbidityTime = morbidityTime == null ? null : morbidityTime.trim();
    }

    public String getAft() {
        return aft;
    }

    public void setAft(String aft) {
        this.aft = aft == null ? null : aft.trim();
    }

    public String getAfp() {
        return afp;
    }

    public void setAfp(String afp) {
        this.afp = afp == null ? null : afp.trim();
    }

    public String getIsterm() {
        return isterm;
    }

    public void setIsterm(String isterm) {
        this.isterm = isterm == null ? null : isterm.trim();
    }

    public String getAbp() {
        return abp;
    }

    public void setAbp(String abp) {
        this.abp = abp == null ? null : abp.trim();
    }

    public String getDcb() {
        return dcb;
    }

    public void setDcb(String dcb) {
        this.dcb = dcb == null ? null : dcb.trim();
    }

    public String getIsdisim() {
        return isdisim;
    }

    public void setIsdisim(String isdisim) {
        this.isdisim = isdisim == null ? null : isdisim.trim();
    }

    public String getBornstatus() {
        return bornstatus;
    }

    public void setBornstatus(String bornstatus) {
        this.bornstatus = bornstatus == null ? null : bornstatus.trim();
    }

    public String getBornweight() {
        return bornweight;
    }

    public void setBornweight(String bornweight) {
        this.bornweight = bornweight == null ? null : bornweight.trim();
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

    public String getUptime() {
        return uptime;
    }

    public void setUptime(String uptime) {
        this.uptime = uptime == null ? null : uptime.trim();
    }

    public String getSeattime() {
        return seattime;
    }

    public void setSeattime(String seattime) {
        this.seattime = seattime == null ? null : seattime.trim();
    }

    public String getStandtime() {
        return standtime;
    }

    public void setStandtime(String standtime) {
        this.standtime = standtime == null ? null : standtime.trim();
    }

    public String getMovetime() {
        return movetime;
    }

    public void setMovetime(String movetime) {
        this.movetime = movetime == null ? null : movetime.trim();
    }

    public String getToothtime() {
        return toothtime;
    }

    public void setToothtime(String toothtime) {
        this.toothtime = toothtime == null ? null : toothtime.trim();
    }

    public String getLaughtime() {
        return laughtime;
    }

    public void setLaughtime(String laughtime) {
        this.laughtime = laughtime == null ? null : laughtime.trim();
    }

    public String getLooktime() {
        return looktime;
    }

    public void setLooktime(String looktime) {
        this.looktime = looktime == null ? null : looktime.trim();
    }

    public String getSpeektime() {
        return speektime;
    }

    public void setSpeektime(String speektime) {
        this.speektime = speektime == null ? null : speektime.trim();
    }

    public String getVaccineinfo() {
        return vaccineinfo;
    }

    public void setVaccineinfo(String vaccineinfo) {
        this.vaccineinfo = vaccineinfo == null ? null : vaccineinfo.trim();
    }

    public String getSincedis() {
        return sincedis;
    }

    public void setSincedis(String sincedis) {
        this.sincedis = sincedis == null ? null : sincedis.trim();
    }

    public String getSincedistime() {
        return sincedistime;
    }

    public void setSincedistime(String sincedistime) {
        this.sincedistime = sincedistime == null ? null : sincedistime.trim();
    }

    public String getSincehosname() {
        return sincehosname;
    }

    public void setSincehosname(String sincehosname) {
        this.sincehosname = sincehosname == null ? null : sincehosname.trim();
    }

    public String getSincehostime() {
        return sincehostime;
    }

    public void setSincehostime(String sincehostime) {
        this.sincehostime = sincehostime == null ? null : sincehostime.trim();
    }

    public String getIsah() {
        return isah;
    }

    public void setIsah(String isah) {
        this.isah = isah == null ? null : isah.trim();
    }

    public String getWhatah() {
        return whatah;
    }

    public void setWhatah(String whatah) {
        this.whatah = whatah == null ? null : whatah.trim();
    }

    public String getIsop() {
        return isop;
    }

    public void setIsop(String isop) {
        this.isop = isop == null ? null : isop.trim();
    }

    public String getOpname() {
        return opname;
    }

    public void setOpname(String opname) {
        this.opname = opname == null ? null : opname.trim();
    }

    public String getFathername() {
        return fathername;
    }

    public void setFathername(String fathername) {
        this.fathername = fathername == null ? null : fathername.trim();
    }

    public String getFatherage() {
        return fatherage;
    }

    public void setFatherage(String fatherage) {
        this.fatherage = fatherage == null ? null : fatherage.trim();
    }

    public String getFatherheal() {
        return fatherheal;
    }

    public void setFatherheal(String fatherheal) {
        this.fatherheal = fatherheal == null ? null : fatherheal.trim();
    }

    public String getFatheridno() {
        return fatheridno;
    }

    public void setFatheridno(String fatheridno) {
        this.fatheridno = fatheridno == null ? null : fatheridno.trim();
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

    public String getPregnanttimes() {
        return pregnanttimes;
    }

    public void setPregnanttimes(String pregnanttimes) {
        this.pregnanttimes = pregnanttimes == null ? null : pregnanttimes.trim();
    }

    public String getAbortiontimes() {
        return abortiontimes;
    }

    public void setAbortiontimes(String abortiontimes) {
        this.abortiontimes = abortiontimes == null ? null : abortiontimes.trim();
    }

    public String getSoptimes() {
        return soptimes;
    }

    public void setSoptimes(String soptimes) {
        this.soptimes = soptimes == null ? null : soptimes.trim();
    }

    public String getWchild() {
        return wchild;
    }

    public void setWchild(String wchild) {
        this.wchild = wchild == null ? null : wchild.trim();
    }

    public String getChildage() {
        return childage;
    }

    public void setChildage(String childage) {
        this.childage = childage == null ? null : childage.trim();
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
        this.hosno = hosno;
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