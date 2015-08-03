package com.wyd.empire.protocol.data.trate;
import com.wyd.protocol.data.AbstractData;
import com.wyd.empire.protocol.Protocol;
/**
 * 类 <code>促销</code>继承抽象类<code>AbstractData</code>，实现接口主命令Protocol.MAIN_TRATE下子命令TRATE_GetShopListOk(获得物品列表成功)对应数据封装。
 * 
 * @see AbstractData
 * @author zengxc
 */
public class GetPromotShopListOk extends AbstractData {
    private int[]     id;                 // 物品序号
    private byte[]    maintype;          // " 0:投掷武器 1:射击武器 2:身躯装扮 3:脸谱 4:头发5:一般道具（只能在战场上使用的道具）
    // 6:合成类（合成时使用的） 7：镶嵌（镶嵌时使用的）8:其它"
    private byte[]    subtype;            // 主类型是其它类型可以使用这个再分类
    private boolean[] isOnSale;           // 是否上架中
    private int[]     saledNum;           // 商场中物品出售的次数
    private String[]  onSaleTime;         // 商场中物品上架的时间
    private String[]  offSaleTime;        // 物品到达时间是不再在商城中显示以及不可续费购买
    private int[]     floorPrice;         // 底价
    private int[]     payType;            // 支付类型 0：点卷 1：金币-1:免费3:勋章兑换
    private boolean[] newMark;            // 是否是新品
    private int[]     discount;           // 折扣
    private int[]     limitLeave;         // 剩余可以购买数量
    private int       remaTime;           // 刷新剩余时间(秒)   
    private int[]	  saleType;			  // 是否是全服折扣，0是全服，1是单日
   
    public GetPromotShopListOk(int sessionId, int serial) {
        super(Protocol.MAIN_TRATE, Protocol.TRATE_GetPromotShopListOk, sessionId, serial);
    }

    public GetPromotShopListOk() {
        super(Protocol.MAIN_TRATE, Protocol.TRATE_GetPromotShopListOk);
    }

	public int[] getId() {
		return id;
	}

	public void setId(int[] id) {
		this.id = id;
	}

	public byte[] getMaintype() {
		return maintype;
	}

	public void setMaintype(byte[] maintype) {
		this.maintype = maintype;
	}

	public byte[] getSubtype() {
		return subtype;
	}

	public void setSubtype(byte[] subtype) {
		this.subtype = subtype;
	}

	public boolean[] getIsOnSale() {
		return isOnSale;
	}

	public void setIsOnSale(boolean[] isOnSale) {
		this.isOnSale = isOnSale;
	}

	public int[] getSaledNum() {
		return saledNum;
	}

	public void setSaledNum(int[] saledNum) {
		this.saledNum = saledNum;
	}

	public String[] getOnSaleTime() {
		return onSaleTime;
	}

	public void setOnSaleTime(String[] onSaleTime) {
		this.onSaleTime = onSaleTime;
	}

	public String[] getOffSaleTime() {
		return offSaleTime;
	}

	public void setOffSaleTime(String[] offSaleTime) {
		this.offSaleTime = offSaleTime;
	}

	public int[] getFloorPrice() {
		return floorPrice;
	}

	public void setFloorPrice(int[] floorPrice) {
		this.floorPrice = floorPrice;
	}

	public int[] getPayType() {
		return payType;
	}

	public void setPayType(int[] payType) {
		this.payType = payType;
	}

	public boolean[] getNewMark() {
		return newMark;
	}

	public void setNewMark(boolean[] newMark) {
		this.newMark = newMark;
	}

	public int[] getDiscount() {
		return discount;
	}

	public void setDiscount(int[] discount) {
		this.discount = discount;
	}

	public int[] getLimitLeave() {
		return limitLeave;
	}

	public void setLimitLeave(int[] limitLeave) {
		this.limitLeave = limitLeave;
	}

	public int getRemaTime() {
		return remaTime;
	}

	public void setRemaTime(int remaTime) {
		this.remaTime = remaTime;
	}

	public int[] getSaleType() {
		return saleType;
	}

	public void setSaleType(int[] saleType) {
		this.saleType = saleType;
	}

  
    
}
