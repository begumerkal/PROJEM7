package com.wyd.empire.world.bean;

public class AbstractBean {
	public enum BeanState {
		normal, modify, insert, delete, lose
	};

	public BeanState cuBeanState = BeanState.normal;

	public boolean isNormalState() {
		return cuBeanState == BeanState.normal ? true : false;
	}

	public boolean isModifyState() {
		return cuBeanState == BeanState.modify ? true : false;
	}

	public boolean isInsertState() {
		return cuBeanState == BeanState.insert ? true : false;
	}

	public boolean isDeleteState() {
		return cuBeanState == BeanState.delete ? true : false;
	}

	public boolean isLoseState() {
		return cuBeanState == BeanState.lose ? true : false;
	}

	public void setModifyState() {
		if (this.cuBeanState != AbstractBean.BeanState.insert)
			this.cuBeanState = AbstractBean.BeanState.modify;
	}

	public void setDeleteState() {
		if (this.cuBeanState == AbstractBean.BeanState.insert)
			this.cuBeanState = AbstractBean.BeanState.lose;
		else
			this.cuBeanState = AbstractBean.BeanState.delete;
	}

	public void setNormalState() {
		this.cuBeanState = AbstractBean.BeanState.normal;
	}

	public void setLoseState() {
		this.cuBeanState = AbstractBean.BeanState.lose;
	}
}
