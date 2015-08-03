package com.wyd.empire.protocol.data.cache;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class StrongRateList extends AbstractData {
	 /** 强化等级 */
    private int[] rateId       ;                                           
    /** 强化消耗金币 */
    private int[] rateGold     ;
    /** 属性加强 */
    private int[] rateAddpower ;
    /** 一级石概率 */
    private int[] rateStone1  ;
    /** 二级石概率 */
    private int[] rateStone2   ;
    /** 三级石概率 */
    private int[] rateStone3  ;
    /** 四级石概率 */
    private int[] rateStone4  ;
    /** 五级石概率 */
    private int[] rateStone5  ;
    /** 头饰血量增幅 */
    private int[] 	addhead	;
    /** 脸谱血量增幅 */
    private int[] 	addface	;
    /** 着装血量增幅 */
    private int[] 	addbody	;
    /** 翅膀血量增幅 */
    private int[] 	addwing	;
    
    /** 真一级石概率 */
    private int[] rateRealStone1   ;
    /** 真二级石概率 */
    private int[] rateRealStone2   ;
    /** 真三级石概率 */
    private int[] rateRealStone3  ;
    /** 真四级石概率 */
    private int[] rateRealStone4  ;
    /** 真五级石概率 */
    private int[] rateRealStone5  ;
    
    /** 戒指血量增幅 */
    private int[] 	addRing	;
    /** 项链血量增幅 */
    private int[] 	addNecklace	;
    /** 强化优化标识*/
    private int strenthFlag = 1;
    /**强化下限值*/
    private int[] percentLow;
    /**强化上限值*/
    private int[] percentHigh;
    /**强化文字描述*/
    private String[] wordDesc;


	public StrongRateList(int sessionId, int serial) {
        super(Protocol.MAIN_CACHE, Protocol.CACHE_StrongRateList, sessionId, serial);
    }

    public StrongRateList() {
        super(Protocol.MAIN_CACHE, Protocol.CACHE_StrongRateList);
    }
    
    public int[] getRateAddpower() {
        return rateAddpower;
    }

    public void setRateAddpower(int[] rateAddpower) {
        this.rateAddpower = rateAddpower;
    }

    public int[] getRateGold() {
        return rateGold;
    }

    public void setRateGold(int[] rateGold) {
        this.rateGold = rateGold;
    }

    public int[] getRateId() {
        return rateId;
    }

    public void setRateId(int[] rateId) {
        this.rateId = rateId;
    }

    public int[] getRateStone1() {
        return rateStone1;
    }

    public void setRateStone1(int[] rateStone1) {
        this.rateStone1 = rateStone1;
    }

    public int[] getRateStone2() {
        return rateStone2;
    }

    public void setRateStone2(int[] rateStone2) {
        this.rateStone2 = rateStone2;
    }

    public int[] getRateStone3() {
        return rateStone3;
    }

    public void setRateStone3(int[] rateStone3) {
        this.rateStone3 = rateStone3;
    }

    public int[] getRateStone4() {
        return rateStone4;
    }

    public void setRateStone4(int[] rateStone4) {
        this.rateStone4 = rateStone4;
    }

    public int[] getRateStone5() {
        return rateStone5;
    }

    public void setRateStone5(int[] rateStone5) {
        this.rateStone5 = rateStone5;
    }

	public int[] getAddhead() {
		return addhead;
	}

	public void setAddhead(int[] addhead) {
		this.addhead = addhead;
	}

	public int[] getAddface() {
		return addface;
	}

	public void setAddface(int[] addface) {
		this.addface = addface;
	}

	public int[] getAddbody() {
		return addbody;
	}

	public void setAddbody(int[] addbody) {
		this.addbody = addbody;
	}

	public int[] getAddwing() {
		return addwing;
	}

	public void setAddwing(int[] addwing) {
		this.addwing = addwing;
	}

	public int[] getRateRealStone2() {
		return rateRealStone2;
	}

	public void setRateRealStone2(int[] rateRealStone2) {
		this.rateRealStone2 = rateRealStone2;
	}

	public int[] getRateRealStone3() {
		return rateRealStone3;
	}

	public void setRateRealStone3(int[] rateRealStone3) {
		this.rateRealStone3 = rateRealStone3;
	}

	public int[] getRateRealStone4() {
		return rateRealStone4;
	}

	public void setRateRealStone4(int[] rateRealStone4) {
		this.rateRealStone4 = rateRealStone4;
	}

	public int[] getRateRealStone5() {
		return rateRealStone5;
	}

	public void setRateRealStone5(int[] rateRealStone5) {
		this.rateRealStone5 = rateRealStone5;
	}

	public int[] getRateRealStone1() {
		return rateRealStone1;
	}

	public void setRateRealStone1(int[] rateRealStone1) {
		this.rateRealStone1 = rateRealStone1;
	}

	public int[] getAddRing() {
		return addRing;
	}

	public void setAddRing(int[] addRing) {
		this.addRing = addRing;
	}

	public int[] getAddNecklace() {
		return addNecklace;
	}

	public void setAddNecklace(int[] addNecklace) {
		this.addNecklace = addNecklace;
	}


	public int getStrenthFlag() {
		return strenthFlag;
	}

	public void setStrenthFlag(int strenthFlag) {
		this.strenthFlag = strenthFlag;
	}

	public int[] getPercentLow() {
		return percentLow;
	}

	public void setPercentLow(int[] percentLow) {
		this.percentLow = percentLow;
	}

	public int[] getPercentHigh() {
		return percentHigh;
	}

	public void setPercentHigh(int[] percentHigh) {
		this.percentHigh = percentHigh;
	}

	public String[] getWordDesc() {
		return wordDesc;
	}

	public void setWordDesc(String[] wordDesc) {
		this.wordDesc = wordDesc;
	}
    
}

