package com.wyd.service.server.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;

import com.wyd.service.bean.Empireaccount;
import com.wyd.service.bean.Player;
import com.wyd.service.server.factory.IPlayerService;
import com.wyd.service.server.factory.ServiceManager;
import com.wyd.service.utils.CryptionUtil;
import com.wyd.service.utils.DateUtil;
import com.wyd.service.utils.HttpClientUtil;

public class CheckRechargeService {
	public String postUrl;

	private Logger log = Logger.getLogger(CheckRechargeService.class);

	public void addIntegralInfo(List<Object> list) throws InterruptedException {
		ServiceManager.getManager().getHttpThreadPool().execute(createTask(list));
	}

	private Runnable createTask(List<Object> list) {
		return new CheckRechargeThread(list);
	}

	public class CheckRechargeThread implements Runnable {
		private List<PlayerBillVo> pbList = new ArrayList<PlayerBillVo>();

		public CheckRechargeThread(List<Object> list) {
			log.info("查到帐单"+list.size()+"条");
			IPlayerService playerService = ServiceManager.getManager().getPlayerService();
			String area = ServiceManager.getManager().getConfiguration().getString("area");
			Object[] playBill;
			for (int i = 0; i < list.size(); i++) {
				try {
					playBill = (Object[]) list.get(i);
					int playerId = Integer.parseInt(playBill[4].toString());
					Player player = playerService.getById(playerId);
					PlayerBillVo pbv = new PlayerBillVo();
					pbv.setAmount(Integer.parseInt(playBill[0].toString()));
					pbv.setChargePrice(Float.parseFloat(playBill[1].toString()));
					pbv.setCreateTime(DateUtil.parse(playBill[2].toString()));
					pbv.setOrderNum(playBill[3].toString());
					pbv.setPlayerId(playerId);
					pbv.setRemark(playBill[5].toString());
					pbv.setChannelId(playBill[6].toString());
					pbv.setArea(area);
					pbv.setAreaId(player.getAreaId() + "");
					pbv.setUserId(getAccountId(player));
					pbList.add(pbv);
				} catch (Exception e) {
					log.error(e, e);
				}
			}
		}

		private String getAccountId(Player player) {
			Empireaccount empireaccount = ServiceManager.getManager().getAccountService().getById(player.getAccountId());
			String accountName = empireaccount.getName();
			int index_ = accountName.indexOf("_");
			if (accountName != null && index_ != -1) {
				return accountName.substring(index_ + 1);
			}
			return accountName;
		}

		public void run() {
			try {
				if (null == postUrl) {
					postUrl = ServiceManager.getManager().getConfiguration().getString("exchangeurl");
				}
				String url;
				byte[] data;
				url = postUrl + "/checkRecharge";
				System.out.println(JSONArray.fromObject(pbList).toString());
				data = CryptionUtil.Encrypt(JSONArray.fromObject(pbList).toString(), ServiceManager.getManager().getConfiguration().getString("deckey"));
				HttpClientUtil.PostData(url, data);
				
			} catch (Exception e) {
				e.printStackTrace();
				log.error(e, e);
			}
		}
	}

	public class PlayerBillVo {
		private String userId;
		private Integer playerId;
		private Date createTime;
		private Integer amount;
		private String remark;
		private Float chargePrice;
		private String orderNum;
		private String channelId;
		private String area;
		private String areaId;

		public String getUserId() {
			return userId;
		}

		public void setUserId(String userId) {
			this.userId = userId;
		}

		public Integer getPlayerId() {
			return playerId;
		}

		public void setPlayerId(Integer playerId) {
			this.playerId = playerId;
		}

		public Date getCreateTime() {
			return createTime;
		}

		public void setCreateTime(Date createTime) {
			this.createTime = createTime;
		}

		public Integer getAmount() {
			return amount;
		}

		public void setAmount(Integer amount) {
			this.amount = amount;
		}

		public String getRemark() {
			return remark;
		}

		public void setRemark(String remark) {
			this.remark = remark;
		}

		public Float getChargePrice() {
			return chargePrice;
		}

		public void setChargePrice(Float chargePrice) {
			this.chargePrice = chargePrice;
		}

		public String getOrderNum() {
			return orderNum;
		}

		public void setOrderNum(String orderNum) {
			this.orderNum = orderNum;
		}

		public String getChannelId() {
			return channelId;
		}

		public void setChannelId(String channelId) {
			this.channelId = channelId;
		}

		public String getArea() {
			return area;
		}

		public void setArea(String area) {
			this.area = area;
		}

		public String getAreaId() {
			return areaId;
		}

		public void setAreaId(String areaId) {
			this.areaId = areaId;
		}

	}
}
