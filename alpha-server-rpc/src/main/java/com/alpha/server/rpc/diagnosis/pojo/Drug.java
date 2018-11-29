package com.alpha.server.rpc.diagnosis.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ebm_b_drug")
public class Drug {
	@Id
	@Column(name = "id_")
	private Long id;
	
	@Column(name = "name_")
	private String name;			//名称
	
	@Column(name = "dosage_")
	private Double dosage;			//用量
	
	@Column(name = "category_")
	private Long category;			//分类
	
	@Column(name = "order_no_")
	private Integer orderNo;			//序号
	
	@Column(name = "code_")
	private String code;			//编码
	
	@Column(name = "structure_")
 	private String structure;		//结构式
	
	@Column(name = "create_rule_")
	private String createRule;		//制剂规格
	
	@Column(name = "use_dos_age_")
	private String useDosAge;		//用法用量
	
	@Column(name = "note_item_")
	private String noteItem;		//注意事项
	
	@Column(name = "bad_reaction_")
	private String badReaction;		//不良反应
	
	@Column(name = "indication_")
	private String indication;		//适应证
	
	@Column(name = "forbidden_")
	private String forbidden;		//禁忌证
	
	@Column(name = "from_brand_")
	private String frombrand;		//厂家
	
	@Column(name = "composition_")
	private String composition;		//成分
	
	@Column(name = "formula_")
	private String formula;			//分子式
	
	@Column(name = "formula_age_")
	private String formulaAge;		//分子量
	
	@Column(name = "standard_")
	private String standard;		//药物规格
	
	@Column(name = "shape_")
	private String shape;			//形状
	
	@Column(name = "for_pregnant_")
	private String forPregnant;		//孕妇哺乳用药
	
	@Column(name = "for_child")
	private String forChild;		//儿童用药
	
	@Column(name = "interaction_")
	private String interaction;		//药物相互作用
	
	@Column(name = "excess_")
	private String excess;			//药物过量
	
	@Column(name = "poison_theory_")
	private String poisonTheory;	//药物毒理
	
	@Column(name = "dynamics_")
	private String dynamics;		//动力学
	
	@Column(name = "store_up_")
	private String storeUp;			//贮藏
	
	@Column(name = "package_")
	private String package_;		//包装
	
	@Column(name = "exe_rule_")
	private String exeRule;			//执行标准
	
	@Column(name = "apply_licensee_")
	private String applyLicensee;	//批准文号
	
	@Column(name = "expr_")
	private String expr;			//『未用』
	
	@Column(name = "validate_")
	private String validate;		//药物有效期
	
	@Column(name = "symbol_")
	private String symbol;			//助记符
	
	@Column(name = "drug_code_")
	private String drugCode;			//药物编码
	
	@Column(name = "data_sources_")
	private String data_sources;
	
	public String getData_sources() {
		return data_sources;
	}
	public void setData_sources(String data_sources) {
		this.data_sources = data_sources;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name= name;
	}
	public Double getDosage() {
		return dosage;
	}
	public void setDosage(Double dosage) {
		this.dosage = dosage;
	}
	public Long getCategory() {
		return category;
	}
	public void setCategory(Long category) {
		this.category = category;
	}
	public Integer getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}

	public String getStructure() {
		return structure;
	}
	public void setStructure(String structure) {
		this.structure = structure;
	}
	public String getCreateRule() {
		return createRule;
	}
	public void setCreateRule(String createRule) {
		this.createRule = createRule;
	}
	public String getApplyLicensee() {
		return applyLicensee;
	}
	public void setApplyLicensee(String applyLicensee) {
		this.applyLicensee = applyLicensee;
	}
	public String getUseDosAge() {
		return useDosAge;
	}
	public void setUseDosAge(String useDosAge) {
		this.useDosAge = useDosAge;
	}
	public String getNoteItem() {
		return noteItem;
	}
	public void setNoteItem(String noteItem) {
		this.noteItem = noteItem;
	}
	public String getBadReaction() {
		return badReaction;
	}
	public void setBadReaction(String badReaction) {
		this.badReaction = badReaction;
	}
	public String getIndication() {
		return indication;
	}
	public void setIndication(String indication) {
		this.indication = indication;
	}
	public String getForbidden() {
		return forbidden;
	}
	public void setForbidden(String forbidden) {
		this.forbidden = forbidden;
	}
	public String getFrombrand() {
		return frombrand;
	}
	public void setFrombrand(String frombrand) {
		this.frombrand = frombrand;
	}
	public String getComposition() {
		return composition;
	}
	public void setComposition(String composition) {
		this.composition = composition;
	}
	public String getFormula() {
		return formula;
	}
	public void setFormula(String formula) {
		this.formula = formula;
	}
	public String getFormulaAge() {
		return formulaAge;
	}
	public void setFormulaAge(String formulaAge) {
		this.formulaAge = formulaAge;
	}
	public String getStandard() {
		return standard;
	}
	public void setStandard(String standard) {
		this.standard = standard;
	}
	public String getShape() {
		return shape;
	}
	public void setShape(String shape) {
		this.shape = shape;
	}
	public String getForPregnant() {
		return forPregnant;
	}
	public void setForPregnant(String forPregnant) {
		this.forPregnant = forPregnant;
	}
	public String getForChild() {
		return forChild;
	}
	public void setForChild(String forChild) {
		this.forChild = forChild;
	}
	public String getInteraction() {
		return interaction;
	}
	public void setInteraction(String interaction) {
		this.interaction = interaction;
	}
	public String getExcess() {
		return excess;
	}
	public void setExcess(String excess) {
		this.excess = excess;
	}
	public String getPoisonTheory() {
		return poisonTheory;
	}
	public void setPoisonTheory(String poisonTheory) {
		this.poisonTheory = poisonTheory;
	}
	public String getDynamics() {
		return dynamics;
	}
	public void setDynamics(String dynamics) {
		this.dynamics = dynamics;
	}
	public String getStoreUp() {
		return storeUp;
	}
	public void setStoreUp(String storeUp) {
		this.storeUp = storeUp;
	}
	public String getPackage_() {
		return package_;
	}
	public void setPackage_(String package_) {
		this.package_ = package_;
	}
	public String getExeRule() {
		return exeRule;
	}
	public void setExeRule(String exeRule) {
		this.exeRule = exeRule;
	}
	public String getExpr() {
		return expr;
	}
	public void setExpr(String expr) {
		this.expr = expr;
	}
	public String getValidate() {
		return validate;
	}
	public void setValidate(String validate) {
		this.validate = validate;
	}
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public String getDrugCode() {
		return drugCode;
	}
	public void setDrugCode(String drugCode) {
		this.drugCode = drugCode;
	}
}
