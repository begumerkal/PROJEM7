package com.wyd.empire.battle.bean;

import java.util.Comparator;

/**
 * @author zguoqiu
 * @version 创建时间：2013-6-17 下午8:07:23
 * 类说明
 */
public class CombatComparator implements Comparator<Combat> {
    public int compare(Combat combat1, Combat combat2) {
        return (combat1.getTiredValue() - (int) combat1.getPf() + combat1.getFrozenTimes() * 10000) - (combat2.getTiredValue() - (int) combat2.getPf() + combat2.getFrozenTimes() * 10000);
    }
}
