package com.wyd.service.bean;
import java.util.ArrayList;
import java.util.List;

import com.wyd.empire.world.title.PlayerTaskVo;
/**
 * 正在执行中的任务列表
 * @author sunzx
 *
 */
public class TaskIng extends PlayerTaskVo {
    private static final long serialVersionUID = 1L;
    private String            taskName;
    private String            target;
    private int               sort;
    private List<String[]>    titleList        = new ArrayList<String[]>();
    private List<Integer>     targetValueList  = new ArrayList<Integer>();
    private List<Integer>     finishValueList  = new ArrayList<Integer>();

    /**
     * 无参构造函数
     */
    public TaskIng() {
    }

    /**
     * 有参构造函数，初始化相关值
     * @param taskId        任务ID
     * @param taskName      任务名称
     * @param taskType      任务主类型
     * @param taskSubType   任务整类型
     * @param target        任务完成条件
     * @param sort          任务排序
     */
    public TaskIng(int taskId, String taskName, byte taskType, String target, int sort) {
        this.setTaskId(taskId);
        this.setTaskName(taskName);
        this.setTaskType(taskType);
        this.setTarget(target);
        this.sort = sort;
    }

    /**
     * 重置正在进行中的任务相关完成条件
     */
    public void reset() {
        if (titleList != null && !titleList.isEmpty()) {
            titleList.clear();
        }
        if (targetValueList != null && !targetValueList.isEmpty()) {
            targetValueList.clear();
        }
        if (finishValueList != null && !finishValueList.isEmpty()) {
            finishValueList.clear();
        }
    }

    /**
     * 获取任务名称
     * @return  任务名称
     */
    public String getTaskName() {
        return taskName;
    }

    /**
     * 设置任务名称
     * @param taskName  任务名称
     */
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    /**
     * 获取任务完成条件
     * @return  任务完成条件
     */
    public String getTarget() {
        return target;
    }

    /**
     * 设置任务完成条件
     * @param target    任务完成条件
     */
    public void setTarget(String target) {
        this.target = target;
    }

    /**
     * 获取任务已完成次数
     * @return  任务已完成次数
     */
    public List<Integer> getFinishValueList() {
        return finishValueList;
    }

    /**
     * 获取任务完成各部分条件
     * @return  任务完成各部分条件
     */
    public List<String[]> getTitleList() {
        return titleList;
    }

    /**
     * 设置任务完成各部分条件
     * @param titleList 任务完成各部分条件
     */
    public void setTitleList(List<String[]> titleList) {
        this.titleList = titleList;
    }

    /**
     * 获取任务各部分
     * @return  任务各部分完成各次数
     */
    public List<Integer> getTargetValueList() {
        return targetValueList;
    }

    /**
     * 设置任务各部分
     * @param targetValueList   任务各部分
     */
    public void setTargetValueList(List<Integer> targetValueList) {
        this.targetValueList = targetValueList;
    }
    
    /**
     * 获取任务排序
     * @return  任务排序
     */
    public int getSort() {
        return sort;
    }

    /**
     * 设置任务排序
     * @param sort  任务排序
     */
    public void setSort(int sort) {
        this.sort = sort;
    }
}
