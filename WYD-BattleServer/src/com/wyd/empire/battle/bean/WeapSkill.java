package com.wyd.empire.battle.bean;
/**
 * The persistent class for the tab_admin database table.
 * 
 * @author BEA Workshop
 */
public class WeapSkill {
    private String name;
    private int    type;
    private int    chance; // 触发概率
    private int    param1;   // 参数1
    private int    param2;   // 参数2

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getChance() {
        return chance;
    }

    public void setChance(int chance) {
        this.chance = chance;
    }

    public int getParam1() {
        return param1;
    }

    public void setParam1(int param1) {
        this.param1 = param1;
    }

    public int getParam2() {
        return param2;
    }

    public void setParam2(int param2) {
        this.param2 = param2;
    }
}