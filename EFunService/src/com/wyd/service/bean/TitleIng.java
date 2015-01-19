package com.wyd.service.bean;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wyd.empire.world.title.PlayerTitleVo;
/**
 * 玩家正在进行的称号列表
 * @author sunzx
 *
 */
public class TitleIng extends PlayerTitleVo {
    private static final long serialVersionUID = 1L;
    private String            target;                                      // 成就完成条件
    private List<String[]>    titleList        = new ArrayList<String[]>(); // 成就完成各部分条件
    private List<Integer>     targetValueList  = new ArrayList<Integer>(); // 成就各部分需要完成次数
    private List<Integer>     finishValueList  = new ArrayList<Integer>(); // 成就各部分已经完成次数

    /**
     * 构造函数，初始化成就ID值
     * @param titleId   成就ID
     */
    public TitleIng(int titleId) {
        super(titleId);
    }

    /**
     * 获取成就完成条件
     * @return  成就完成条件
     */
    public String getTarget() {
        return target;
    }

    /**
     * 设置成就完成条件
     * @param target    成就完成条件
     */
    public void setTarget(String target) {
        this.target = target;
    }

    /**
     * 获取成就完成各部分条件
     * @return  成就完成各部分条件
     */
    public List<String[]> getTitleList() {
        return titleList;
    }

    /**
     * 设置成就完成各部分条件
     * @param target    成就完成各部分条件
     */
    public void setTitleList(List<String[]> titleList) {
        this.titleList = titleList;
    }

    /**
     * 获取成就各部分需要完成次数
     * @return  成就各部分需要完成次数
     */
    public List<Integer> getTargetValueList() {
        return targetValueList;
    }

    /**
     * 设置成就各部分需要完成次数
     * @param targetValueList   成就各部分需要完成次数
     */
    public void setTargetValueList(List<Integer> targetValueList) {
        this.targetValueList = targetValueList;
    }

    /**
     * 获取成就各部分已经完成次数
     * @return  成就各部分已经完成次数
     */
    public List<Integer> getFinishValuetList() {
        return finishValueList;
    }

    /**
     * 设置成就各部分已经完成次数
     * @param finishValueList   成就各部分已经完成次数
     */
    public void setFinishValuetList(List<Integer> finishValueList) {
        this.finishValueList = finishValueList;
    }

    /**
     * 初始化任务完成情况
     */
    public void init() {
        this.titleList.clear();
        this.targetValueList.clear();
        this.finishValueList.clear();
        Map<String, Integer> statusMap = new HashMap<String, Integer>();
        if(this.getTitleId()==43){
            return;
        }
        String[] targers = this.target.split("&");
        for (String targer : targers) {
            String[] tempTargers = targer.split("=");
            if (tempTargers.length != 2) {
                System.out.println("Loading TitleIng Fail: " + this.getTitleId());
                continue;
            }
            this.titleList.add(tempTargers[0].split(","));
            this.targetValueList.add(Integer.parseInt(tempTargers[1]));
            this.finishValueList.add(0);
        }
        if (null == this.getTargetStatus() || this.getTargetStatus().equals("")) {
            StringBuffer targetStatus = new StringBuffer();
            for (int i = 0; i < this.titleList.size(); i++) {
                String[] trs = this.titleList.get(i);
                for (int j = 0; j < trs.length; j++) {
                    if (j != trs.length - 1) {
                        targetStatus.append(trs[j] + ",");
                    } else {
                        targetStatus.append(trs[j]);
                    }
                }
                if (i != this.titleList.size() - 1) {
                    targetStatus.append("=0&");
                } else {
                    targetStatus.append("=0");
                }
            }
            this.setTargetStatus(targetStatus.toString());
        } else {
            String[] targetStatus = this.getTargetStatus().split("&");
            statusMap.clear();
            for (String ts : targetStatus) {
                String[] temptss = ts.split("=");
                if (temptss.length != 2) {
                    System.out.println("Loading TargetStatus Fail: " + this.getTitleId());
                    continue;
                }
                statusMap.put(temptss[0], Integer.parseInt(temptss[1]));
            }
            for (int i = 0; i < this.getTitleList().size(); i++) {
                StringBuffer key = new StringBuffer();
                String[] tempTitles = this.getTitleList().get(i);
                for (int k = 0; k < tempTitles.length; k++) {
                    String strTitle = tempTitles[k];
                    if (k != tempTitles.length - 1) {
                        key.append(strTitle + ",");
                    } else {
                        key.append(strTitle);
                    }
                }
                this.getFinishValuetList().set(i, statusMap.get(key.toString()));
            }
        }
    }
}
