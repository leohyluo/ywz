package com.alpha.server.rpc.diagnosis.pojo;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 药品说明书
 *
 * @author fxf
 * @since 2014-4-22
 */
@Entity
@Table(name = "zhys_drug")
public class DrugOnSellDetail {

    private static final long serialVersionUID = -8287624935798507470L;

    @Id
    @Column(name = "id")
    private Long id;

    /**
     * 商品名称
     */
    @Column(name = "c007")
    private String tradeName;

    /**
     * 适应症
     */
    @Column(name = "c008")
    private String indication;
    /**
     * 禁忌症
     */
    @Column(name = "c005")
    private String contraindication;
    /**
     * 英语名
     */
    @Column(name = "c004")
    private String englishName;
    /**
     * 注意事项
     */
    @Column(name = "c009")
    private String announcements;
    /**
     * 不良反应
     */
    @Column(name = "c010")
    private String untowardEffect;
    /**
     * 药理作用
     */
    @Column(name = "c011")
    private String pharmacology;
    /**
     * 剂型
     */
    @Column(name = "c012")
    private String dosageForm;
    /**
     * 剂型编码
     */
    @Column(name = "c019")
    private String formCode;
    /**
     * 生产规格
     */
    @Column(name = "c013")
    private String specification;
    /**
     * 批准文号
     */
    @Column(name = "c014")
    private String approvalNum;
    /**
     * 生产企业
     */
    @Column(name = "c016")
    private String enterprise;
    /**
     * 生产企业编码
     */
    @Column(name = "c018")
    private String enterpriseCode;
    /**
     * 药品图片
     */
    @Column(name = "c020")
    private String tradeImage;
    /**
     * 价格
     */

    @Column(name = "c021")
    private BigDecimal price;
    /**
     * 包装
     */
    @Column(name = "c022")
    private String packages;

    /**
     * 成分
     */
    @Column(name = "c023")
    private String ingredient;
    /**
     * 性状
     */
    @Column(name = "c024")
    private String trait;
    /**
     * 用法用量
     */
    @Column(name = "c025")
    private String usage;
    /**
     * 相互作用
     */
    @Column(name = "c026")
    private String interaction;
    /**
     * 服药与进食
     */
    @Column(name = "c027")
    private String medicineFood;
    /**
     * 警告
     */
    @Column(name = "c028")
    private String warining;
    /**
     * 用药须知
     */
    @Column(name = "c029")
    private String medicationTips;
    /**
     * FDA妊娠药物分级
     */
    @Column(name = "c030")
    private String fdaClass;
    /**
     * 药代动力学
     */
    @Column(name = "c031")
    private String pharmacokinetics;
    /**
     * 贮藏
     */
    @Column(name = "c033")
    private String store;

    /**
     * 化学成分
     */
    @Column(name = "c032")
    private String chemical;
    /**
     * 药物分类
     */
    @Column(name = "c034")
    private String drugClass;

    /**
     * 儿童用药
     */
    @Column(name = "c035")
    private String kidDrug;
    /**
     * 老人用药
     */
    @Column(name = "c036")
    private String oldDrug;

    /**
     * 药物过量
     */
    @Column(name = "c037")
    private String overdose;
    /**
     * 执行标准
     */
    @Column(name = "c038")
    private String operStandard;
    /**
     * 药理毒理
     */
    @Column(name = "c039")
    private String toxicology;
    /**
     * 核准日期
     */
    @Column(name = "c040")
    private String approvedDate;
    /**
     * 修改日期
     */
    @Column(name = "c041")
    private String updateDate;
    /**
     * 药品监管分级
     */
    @Column(name = "c042")
    private String drugGrade;
    /**
     * 主要成分名称
     */
    @Column(name = "c043")
    private String drugBaseName;

    /**
     * 有效期
     */
    @Column(name = "c015")
    private String validityDate;

    /**
     * 孕妇及哺乳期妇女用药
     */
    @Column(name = "c044")
    private String womenDrug;

    //药品编码
    @Column(name = "c003")
    private String drugCode;

    /**
     * 药品评价数
     */
    @Column(name = "c045")
    private Integer pinjCount;

    /**
     * 药品销售量
     */
    @Column(name = "c046")
    private Integer sellCount;

    /**
     * 是否是主推药品
     */
    @Column(name = "c047")
    private Integer isTop;

    /**
     * 说明书编码
     */
    @Column(name = "c048")
    private String onSellCode;


    /**
     * 当前说明书是属于哪个OnSell表中的ID
     */
    @Transient
    private Long onsellId;

    /**
     * 在替换药品时，所需要的CODE值
     */
    @Transient
    private String repCode;

    /**
     * 二维度分值
     */
    @Transient
    private double erSocre;

    /**
     * J形
     */
    @Transient
    private String packing;

    /**
     * 药品备注（社保、非社保）
     */
    @Transient
    private Integer healthCareDrug;
    
  //默认排序
    @Column(name = "default_order")
    private String defaultOrder;
    
    //智能排序阀值
    @Column(name = "threshold")
    private Integer threshold;
    
    //用户选择次数
    @Column(name = "user_select_count")
    private Integer userSelectCount;


    public String getPacking() {
        return packing;
    }

    public void setPacking(String packing) {
        this.packing = packing;
    }

    public Integer getHealthCareDrug() {
        return healthCareDrug;
    }

    public void setHealthCareDrug(Integer healthCareDrug) {
        this.healthCareDrug = healthCareDrug;
    }

    @Transient
    public double getErSocre() {
        return erSocre;
    }

    public void setErSocre(double erSocre) {
        this.erSocre = erSocre;
    }

    @Transient
    public String getRepCode() {
        return repCode;
    }

    public void setRepCode(String repCode) {
        this.repCode = repCode;
    }

    @Transient
    public Long getOnsellId() {
        return onsellId;
    }

    public void setOnsellId(Long onsellId) {
        this.onsellId = onsellId;
    }

    public Integer getIsTop() {
        return isTop;
    }

    public void setIsTop(Integer isTop) {
        this.isTop = isTop;
    }

    public Integer getPinjCount() {
        return pinjCount;
    }

    public void setPinjCount(Integer pinjCount) {
        this.pinjCount = pinjCount;
    }

    public Integer getSellCount() {
        return sellCount;
    }

    public void setSellCount(Integer sellCount) {
        this.sellCount = sellCount;
    }

    public String getIndication() {
        return indication;
    }

    public void setIndication(String indication) {
        this.indication = indication;
    }

    public String getContraindication() {
        return contraindication;
    }

    public void setContraindication(String contraindication) {
        this.contraindication = contraindication;
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public String getAnnouncements() {
        return announcements;
    }

    public void setAnnouncements(String announcements) {
        this.announcements = announcements;
    }

    public String getUntowardEffect() {
        return untowardEffect;
    }

    public void setUntowardEffect(String untowardEffect) {
        this.untowardEffect = untowardEffect;
    }

    public String getPharmacology() {
        return pharmacology;
    }

    public void setPharmacology(String pharmacology) {
        this.pharmacology = pharmacology;
    }

    public String getDosageForm() {
        return dosageForm;
    }

    public void setDosageForm(String dosageForm) {
        this.dosageForm = dosageForm;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public String getApprovalNum() {
        return approvalNum;
    }

    public void setApprovalNum(String approvalNum) {
        this.approvalNum = approvalNum;
    }

    public String getEnterprise() {
        return enterprise;
    }

    public void setEnterprise(String enterprise) {
        this.enterprise = enterprise;
    }

    public String getEnterpriseCode() {
        return enterpriseCode;
    }

    public void setEnterpriseCode(String enterpriseCode) {
        this.enterpriseCode = enterpriseCode;
    }

    public String getFormCode() {
        return formCode;
    }

    public void setFormCode(String formCode) {
        this.formCode = formCode;
    }

    public String getTradeImage() {
        return tradeImage;
    }

    public void setTradeImage(String tradeImage) {
        this.tradeImage = tradeImage;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getPackages() {
        return packages;
    }

    public void setPackages(String packages) {
        this.packages = packages;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public String getTrait() {
        return trait;
    }

    public void setTrait(String trait) {
        this.trait = trait;
    }

    public String getUsage() {
        return usage;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }

    public String getInteraction() {
        return interaction;
    }

    public void setInteraction(String interaction) {
        this.interaction = interaction;
    }

    public String getMedicineFood() {
        return medicineFood;
    }

    public void setMedicineFood(String medicineFood) {
        this.medicineFood = medicineFood;
    }

    public String getWarining() {
        return warining;
    }

    public void setWarining(String warining) {
        this.warining = warining;
    }

    public String getMedicationTips() {
        return medicationTips;
    }

    public void setMedicationTips(String medicationTips) {
        this.medicationTips = medicationTips;
    }

    public String getFdaClass() {
        return fdaClass;
    }

    public void setFdaClass(String fdaClass) {
        this.fdaClass = fdaClass;
    }

    public String getPharmacokinetics() {
        return pharmacokinetics;
    }

    public void setPharmacokinetics(String pharmacokinetics) {
        this.pharmacokinetics = pharmacokinetics;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public String getChemical() {
        return chemical;
    }

    public void setChemical(String chemical) {
        this.chemical = chemical;
    }

    public String getDrugClass() {
        return drugClass;
    }

    public void setDrugClass(String drugClass) {
        this.drugClass = drugClass;
    }

    public String getKidDrug() {
        return kidDrug;
    }

    public void setKidDrug(String kidDrug) {
        this.kidDrug = kidDrug;
    }

    public String getOldDrug() {
        return oldDrug;
    }

    public void setOldDrug(String oldDrug) {
        this.oldDrug = oldDrug;
    }

    public String getOverdose() {
        return overdose;
    }

    public void setOverdose(String overdose) {
        this.overdose = overdose;
    }

    public String getOperStandard() {
        return operStandard;
    }

    public void setOperStandard(String operStandard) {
        this.operStandard = operStandard;
    }

    public String getToxicology() {
        return toxicology;
    }

    public void setToxicology(String toxicology) {
        this.toxicology = toxicology;
    }

    public String getApprovedDate() {
        return approvedDate;
    }

    public void setApprovedDate(String approvedDate) {
        this.approvedDate = approvedDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getDrugGrade() {
        return drugGrade;
    }

    public void setDrugGrade(String drugGrade) {
        this.drugGrade = drugGrade;
    }

    public String getDrugBaseName() {
        return drugBaseName;
    }

    public void setDrugBaseName(String drugBaseName) {
        this.drugBaseName = drugBaseName;
    }

    public String getValidityDate() {
        return validityDate;
    }

    public void setValidityDate(String validityDate) {
        this.validityDate = validityDate;
    }

    public String getWomenDrug() {
        return womenDrug;
    }

    public void setWomenDrug(String womenDrug) {
        this.womenDrug = womenDrug;
    }

    public String getDrugCode() {
        return drugCode;
    }

    public void setDrugCode(String drugCode) {
        this.drugCode = drugCode;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public String getTradeName() {
        return tradeName;
    }

    public void setTradeName(String tradeName) {
        this.tradeName = tradeName;
    }

    public String getOnSellCode() {
        return onSellCode;
    }

    public void setOnSellCode(String onSellCode) {
        this.onSellCode = onSellCode;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getThreshold() {
		return threshold;
	}

	public void setThreshold(Integer threshold) {
		this.threshold = threshold;
	}

	public Integer getUserSelectCount() {
		return userSelectCount;
	}

	public void setUserSelectCount(Integer userSelectCount) {
		this.userSelectCount = userSelectCount;
	}

	public String getDefaultOrder() {
		return defaultOrder;
	}

	public void setDefaultOrder(String defaultOrder) {
		this.defaultOrder = defaultOrder;
	}

	
}
