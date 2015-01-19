package com.wyd.empire.world.server.handler.strengthen;

import java.util.List;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.strengthen.GetRateListOk;
import com.wyd.empire.world.bean.StrenthPercent;
import com.wyd.empire.world.bean.Successrate;
import com.wyd.empire.world.buff.Buff;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

public class GetRateListHandler implements IDataHandler {

	Logger log = Logger.getLogger(GetRateListHandler.class);

	/**
	 * 获取强化成功比率列表
	 */
	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer player = session.getPlayer(data.getSessionId());
		try {
			GetRateListOk getRateListOk = new GetRateListOk(data.getSessionId(), data.getSerial());
			result(player, getRateListOk);
			session.write(getRateListOk);
		} catch (Exception ex) {
			if (null == ex.getMessage() || !ex.getMessage().startsWith(Common.ERRORKEY))
				this.log.error(ex, ex);
			throw new ProtocolException(ErrorMessages.STRENGTHEN_GETRATEFAIL_MESSAGE, data.getSerial(), data.getSessionId(),
					data.getType(), data.getSubType());
		}
	}

	@SuppressWarnings("unchecked")
	public void result(WorldPlayer player, GetRateListOk getRateListOk) {
		List<Successrate> list = ServiceManager.getManager().getStrengthenService().getAll(Successrate.class);
		int[] rateId = new int[list.size()];
		int[] rateGold = new int[list.size()];
		int[] rateAddpower = new int[list.size()];
		int[] rateStone1 = new int[list.size()];
		int[] rateStone2 = new int[list.size()];
		int[] rateStone3 = new int[list.size()];
		int[] rateStone4 = new int[list.size()];
		int[] rateStone5 = new int[list.size()];
		int[] addhead = new int[list.size()]; // 头饰血量增幅
		int[] addface = new int[list.size()]; // 脸谱血量增幅
		int[] addbody = new int[list.size()]; // 着装血量增幅
		int[] addwing = new int[list.size()]; // 翅膀血量增幅
		int[] rateRealStone1 = new int[list.size()];
		int[] rateRealStone2 = new int[list.size()];
		int[] rateRealStone3 = new int[list.size()];
		int[] rateRealStone4 = new int[list.size()];
		int[] rateRealStone5 = new int[list.size()];
		int[] addRing = new int[list.size()]; // 戒指血量增幅
		int[] addNecklace = new int[list.size()]; // 项链血量增幅

		int index = 0;
		for (Successrate sr : list) {
			rateId[index] = sr.getId();
			rateGold[index] = (int) ServiceManager.getManager().getBuffService().getAddition(player, sr.getGold(), Buff.CGOLDLOW);
			rateAddpower[index] = sr.getAddpower();
			rateStone1[index] = sr.getStone1();
			rateStone2[index] = sr.getStone2();
			rateStone3[index] = sr.getStone3();
			rateStone4[index] = sr.getStone4();
			rateStone5[index] = sr.getStone5();
			rateRealStone1[index] = sr.getStone1real();
			rateRealStone2[index] = sr.getStone2real();
			rateRealStone3[index] = sr.getStone3real();
			rateRealStone4[index] = sr.getStone4real();
			rateRealStone5[index] = sr.getStone5real();
			addhead[index] = sr.getAddhead();
			addface[index] = sr.getAddface();
			addbody[index] = sr.getAddbody();
			addwing[index] = sr.getAddwing();
			addRing[index] = sr.getAddring();
			addNecklace[index] = sr.getAddnecklace();
			index++;
		}
		List<StrenthPercent> list1 = ServiceManager.getManager().getStrengthenService().getAll(StrenthPercent.class);
		int[] percentLow = new int[list1.size()];
		int[] percentHigh = new int[list1.size()];
		String[] wordDesc = new String[list1.size()];

		index = 0;
		for (StrenthPercent str1 : list1) {
			percentLow[index] = str1.getPercentLow();
			percentHigh[index] = str1.getPercentHigh();
			wordDesc[index] = str1.getWordDesc();
			index++;
		}
		getRateListOk.setRateId(rateId);
		getRateListOk.setRateGold(rateGold);
		getRateListOk.setRateAddpower(rateAddpower);
		getRateListOk.setRateStone1(rateStone1);
		getRateListOk.setRateStone2(rateStone2);
		getRateListOk.setRateStone3(rateStone3);
		getRateListOk.setRateStone4(rateStone4);
		getRateListOk.setRateStone5(rateStone5);
		getRateListOk.setAddbody(addbody);
		getRateListOk.setAddface(addface);
		getRateListOk.setAddhead(addhead);
		getRateListOk.setAddwing(addwing);
		getRateListOk.setRateRealStone1(rateRealStone1);
		getRateListOk.setRateRealStone2(rateRealStone2);
		getRateListOk.setRateRealStone3(rateRealStone3);
		getRateListOk.setRateRealStone4(rateRealStone4);
		getRateListOk.setRateRealStone5(rateRealStone5);

		getRateListOk.setAddRing(addRing);
		getRateListOk.setAddNecklace(addNecklace);

		getRateListOk.setPercentLow(percentLow);
		getRateListOk.setPercentHigh(percentHigh);
		getRateListOk.setWordDesc(wordDesc);

		getRateListOk.setStrenthFlag(ServiceManager.getManager().getVersionService().getStrenthFlag());
		getRateListOk.setStrongeTopLevel(ServiceManager.getManager().getVersionService().getStrongeTopLevel());
	}

}
