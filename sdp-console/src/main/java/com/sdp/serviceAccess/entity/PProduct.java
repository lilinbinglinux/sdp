/**
 * 
 */
package com.sdp.serviceAccess.entity;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.sdp.serviceAccess.protyTrans.ProptyTransDom4j;
import com.sdp.serviceAccess.protyTrans.ProtyTransContext;
import com.sdp.serviceAccess.util.ItemsConvertUtils;

/**   
* Copyright: Copyright (c) 2018 BONC
* 
* @ClassName: PProduct.java
* @Description: 服务注册实体
*
* @version: v1.0.0
* @author: renpengyuan
* @date: 2018年8月2日 下午2:39:35 
*
* Modification History:
* Date         Author          Version            Description
*---------------------------------------------------------*
* 2018年8月2日     renpengyuan      v1.0.0               修改原因
* 2018年8月7日     renpengyuan      v1.0.1            增加订购方式字段/增加xml描述三个映射字段
* 2018年8月17日  renpengyuan      v1.0.2            增加 url
* 2018年8月21日  renpengyuan      v1.0.2            去掉 url
* 2018年9月4日      renpengyuan      v1.0.3            增加productTypeName
* 2018年9月7日      renpengyuan      v1.0.4            增加排序字段
* 2018年10月10日    renpengyuan      v1.0.5          增加依赖字段及依赖映射
* 2018年11月20日    renpengyuan      v1.0.6          增加是否展示实例的字段
*/
public class PProduct extends BaseInfo{
    
	private String productId;

	private String productName;
	
	private String productTypeId;
	
	private String productTypeName;
	
	private String productVersion;
	
	private String productStatus;
	
	private String productIntrod;
	
	private String detailedIntrod;
	
	private String productAttr;
	
	private String exampleAttr;
	
	private String nodeAttr;
	
	private byte[] productLogo;
	
	private String apiAddr;
	
	private Integer sortId;
	
	private String showInstance;   //10 是  20否
	
	private String pOrderWaysId;//订购流程Id

	public String getpOrderWaysId() {
		return pOrderWaysId;
	}

	public void setpOrderWaysId(String pOrderWaysId) {
		this.pOrderWaysId = pOrderWaysId;
	}

	public String getShowInstance() {
		return showInstance;
	}

	public void setShowInstance(String showInstance) {
		this.showInstance = showInstance;
	}

	/**10 审批  20 付费  30 自动开通*/
	private String orderType;
	
	private List<String> productIds;
	
	/**扩展 是否需要check KV*/
	//10 是  20否
	private String checkKv;
	
	public String getCheckKv() {
		return checkKv;
	}

	public void setCheckKv(String checkKv) {
		this.checkKv = checkKv;
	}

	public List<String> getProductIds() {
		return productIds;
	}

	public void setProductIds(List<String> productIds) {
		this.productIds = productIds;
	}

	private List<ProductFieldItem> productAttrOrm;
	
	private List<ProductFieldItem> exampleAttrOrm;
	
	private List<ProductFieldItem> nodeAttrOrm;
	
	private String relyOnAttr; //服务依赖属性基本配置
	
	private List<ProductRelyOnItem> relyOnAttrOrm; //服务依赖属性基本配置映射
	
	public String getRelyOnAttr() {
		return relyOnAttr;
	}

	public void setRelyOnAttr(String relyOnAttr) {
		this.relyOnAttr = relyOnAttr;
	}

	public List<ProductRelyOnItem> getRelyOnAttrOrm() {
		return relyOnAttrOrm;
	}

	public void setRelyOnAttrOrm(List<ProductRelyOnItem> relyOnAttrOrm) {
		this.relyOnAttrOrm = relyOnAttrOrm;
	}

	private String imgFlag;
	
	private String sortType;
	
	public String getSortType() {
		return sortType;
	}

	public void setSortType(String sortType) {
		this.sortType = sortType;
	}

	public String getProductTypeName() {
		return productTypeName;
	}

	public void setProductTypeName(String productTypeName) {
		this.productTypeName = productTypeName;
	}

	public String getImgFlag() {
		return imgFlag;
	}

	public void setImgFlag(String imgFlag) {
		this.imgFlag = imgFlag;
	}

	public List<ProductFieldItem> getProductAttrOrm() {
		return productAttrOrm;
	}

	public void setProductAttrOrm(List<ProductFieldItem> productAttrOrm) {
		this.productAttrOrm = productAttrOrm;
	}

	public List<ProductFieldItem> getExampleAttrOrm() {
		return exampleAttrOrm;
	}

	public void setExampleAttrOrm(List<ProductFieldItem> exampleAttrOrm) {
		this.exampleAttrOrm = exampleAttrOrm;
	}

	public List<ProductFieldItem> getNodeAttrOrm() {
		return nodeAttrOrm;
	}

	public void setNodeAttrOrm(List<ProductFieldItem> nodeAttrOrm) {
		this.nodeAttrOrm = nodeAttrOrm;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductTypeId() {
		return productTypeId;
	}
	public void setProductTypeId(String productTypeId) {
		this.productTypeId = productTypeId;
	}

	public String getProductVersion() {
		return productVersion;
	}

	public void setProductVersion(String productVersion) {
		this.productVersion = productVersion;
	}

	public String getProductStatus() {
		return productStatus;
	}

	public void setProductStatus(String productStatus) {
		this.productStatus = productStatus;
	}

	public String getProductIntrod() {
		return productIntrod;
	}

	public void setProductIntrod(String productIntrod) {
		this.productIntrod = productIntrod;
	}

	public String getDetailedIntrod() {
		return detailedIntrod;
	}

	public void setDetailedIntrod(String detailedIntrod) {
		this.detailedIntrod = detailedIntrod;
	}

	public String getProductAttr() {
		return productAttr;
	}

	public void setProductAttr(String productAttr) {
		this.productAttr = productAttr;
	}

	public String getExampleAttr() {
		return exampleAttr;
	}

	public void setExampleAttr(String exampleAttr) {
		this.exampleAttr = exampleAttr;
	}

	public String getNodeAttr() {
		return nodeAttr;
	}

	public void setNodeAttr(String nodeAttr) {
		this.nodeAttr = nodeAttr;
	}
    
	public byte[] getProductLogo() {
		return productLogo;
	}

	public void setProductLogo(byte[] productLogo) {
		this.productLogo = productLogo;
	}

	public String getApiAddr() {
		return apiAddr;
	}

	public void setApiAddr(String apiAddr) {
		this.apiAddr = apiAddr;
	}

	public Integer getSortId() {
		return sortId;
	}

	public void setSortId(Integer sortId) {
		this.sortId = sortId;
	}

	public PProduct() {
		super();
	}
	
	public PProduct(String productId) {
		super();
		this.productId = productId;
	}
	
//	public static void main(String[] args) {
//		PProduct product = new PProduct();
//		product.setProductName("测试");
//		product.setProductId("test");
//		product.setProductStatus("10");
//		product.setProductTypeId("0");
//		product.setProductIntrod("这是个测试服务");
//		product.setProductVersion("V1");
//		List<ProductFieldItem> items = new ArrayList<>();
//		String [] pros = {"CPU","Memeory","Storage"};
//		String [] unis = {"个","G","G"};
//		for(int i=0;i<3;i++){
//			ProductFieldItem item = new ProductFieldItem();
//			item.setProCode(pros[i]);
//			item.setProEnName(pros[i]);
//			item.setProLabel("10,20");
//			item.setProShowType("10");
//			item.setProUnit(unis[i]);
//			item.setProDesc("desc:"+pros[i]);
//			item.setProChargePrice("10");
//			items.add(item);
//		}
//		product.setProductAttrOrm(items);
//		System.out.println(JSONObject.toJSONString(product));
//	}
//	public static void main(String[] args) {
//		String [] codes = {"username","email","phone","applyType","target","explanation","type","instanceName","adminAccountName","adminPassword","confirmPassword","memory","cpu","storage"};
//		String [] names = {"申请人","邮箱","手机","申请类型","需求目标","使用说明","类型","实例名称","管理员账号","管理员密码","确认密码","内存","cpu","存储"};
//		String [] units = {null,null,null,null,null,null,null,null,null,null,null,"G","个","G"};
//		String [] values = {"admin","renpengyuan@bonc.com.cn","13021999129","mysql","mysql","测试使用","mysql","docs-mysql-1","root","root","root","4","4","126"};
//		List<ProductFieldItem> items = new ArrayList<>();
//		List<ProductFieldItem> controlItems  = new ArrayList<>();
//		for(int i=0;i<14;i++){
//			ProductFieldItem item = new ProductFieldItem();
//			item.setProCode(codes[i]);
//			item.setProEnName(codes[i]);
//			if(i<=10){
//				item.setProLabel("10");
//			}else{
//				item.setProLabel("30");
//			}
//			item.setProShowType("10");
//			item.setProUnit(units[i]);
//			item.setProDesc("desc:"+names[i]);
//			item.setProValue(values[i]);
//			items.add(item);
//		}
//		String [] controlCodes = {"memory","cpu","storage"};
//		for(int i=0;i<3;i++){
//			ProductFieldItem item = new ProductFieldItem();
//			item.setProCode(controlCodes[i]);
//			item.setProEnName(controlCodes[i]);
//				item.setProLabel("30");
//			item.setProShowType("10");
//			item.setProUnit(units[11+i]);
//			controlItems.add(item);
//		}
//		
//		ProtyTransContext context = new ProtyTransContext(new ProptyTransDom4j());
//		System.out.println(context.transToXml(ItemsConvertUtils.convertToPro(items), "fieldItems", "fieldItem"));
//		System.out.println(context.transToXml(ItemsConvertUtils.convertToPro(controlItems), "fieldItems", "fieldItem"));
//	}
	public static void main(String[] args) {
		List<ProductRelyOnItem> items = new ArrayList<>();
		String [] products = {"mysql","zk","yarn"};
		for(int i=0;i<3;i++) {
			ProductRelyOnItem item = new ProductRelyOnItem();
			items.add(item);
			item.setRelyOnProductCode(products[i]);
			item.setIsShowRelyOnPros("10");
			item.setIsConfRelyOnPros("10");
			item.setIsAddiCasePros("10");
			item.setRelyOnOrder(i+"");
		}
		System.out.println(JSON.toJSONString(items));
	}
}

