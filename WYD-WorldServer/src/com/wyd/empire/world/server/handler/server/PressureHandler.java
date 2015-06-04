package com.wyd.empire.world.server.handler.server;

import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 压力测试
 * 
 * @since JDK 1.6
 */
public class PressureHandler implements IDataHandler {

	public AbstractData handle(AbstractData data) throws Exception {
		// ConnectSession session = (ConnectSession) data.getHandlerSource();
		// Pressure pressure = (Pressure) data;
		// try {
		// if(0==pressure.getKey()){
		// List<Room> roomlist =
		// ServiceManager.getManager().getRoomService().getListRoom();
		// int roomCount = roomlist.size();
		// int returnSize = 10;
		// int[] roomId = new int[returnSize];
		// String[] roomName = new String[returnSize];
		// int[] battleStatus = new int[returnSize];
		// int[] battleMode = new int[returnSize];
		// int[] playerNumMode = new int[returnSize];
		// String[] passWord = new String[returnSize];
		// int[] playerNum = new int[returnSize];
		// int[] startMode = new int[returnSize];
		// boolean[] roomStaus = new boolean[returnSize];
		// Room room;
		// int roomIndex = 0;
		// int roomChannel = 1;
		// for (int i = 0; i < roomCount && i < returnSize; i++) {
		// room = roomlist.get(i);
		// if(room.getRoomChannel()==roomChannel){
		// roomId[i] = room.getRoomId();
		// roomName[i] = room.getRoomName();
		// battleStatus[i] = room.getBattleStatus();
		// battleMode[i] = room.getBattleMode();
		// playerNumMode[i] = room.getPlayerNumMode();
		// passWord[i] = room.getPassWord();
		// playerNum[i] = room.getPlayerNum();
		// startMode[i] = room.getStartMode();
		// roomStaus[i] = room.isFull();
		// roomIndex++;
		// }
		// }
		//
		// List<Room> leaveRoomlist =
		// ServiceManager.getManager().getRoomService().getLeaveRoom();
		// for (int i = roomIndex; i < returnSize; i++) {
		// room = leaveRoomlist.get(i);
		// roomId[i] = room.getRoomId();
		// roomName[i] = room.getRoomName();
		// battleStatus[i] = room.getBattleStatus();
		// battleMode[i] = room.getBattleMode();
		// playerNumMode[i] = room.getPlayerNumMode();
		// passWord[i] = room.getPassWord();
		// playerNum[i] = room.getPlayerNum();
		// startMode[i] = room.getStartMode();
		// roomStaus[i] = true;
		// }
		//
		// GetRoomListOk sendRoomList = new GetRoomListOk(data.getSessionId(),
		// data.getSerial());
		// sendRoomList.setRoomCount(returnSize);
		// sendRoomList.setRoomId(roomId);
		// sendRoomList.setRoomName(roomName);
		// sendRoomList.setBattleStatus(battleStatus);
		// sendRoomList.setBattleMode(battleMode);
		// sendRoomList.setPlayerNumMode(playerNumMode);
		// sendRoomList.setPassWord(passWord);
		// sendRoomList.setPlayerNum(playerNum);
		// sendRoomList.setStartMode(startMode);
		// sendRoomList.setRoomStaus(roomStaus);
		// session.write(sendRoomList);
		// }else{
		// List<TaskVo> taskList =
		// ServiceManager.getManager().getTaskService().getService().getTaskVoList(ServiceManager.getManager().getPlayerService().getLostPlayer(new
		// HashMap<Integer, Integer>(), 1));
		// int taskCount = taskList.size();
		// int[] taskId = new int[taskCount];
		// int[] taskType = new int[taskCount];
		// String[] taskName = new String[taskCount];
		// String[] taskDesc = new String[taskCount];
		// int[] exp = new int[taskCount];
		// int[] gold = new int[taskCount];
		// int[] taskStatus = new int[taskCount];
		// int[] targetCount = new int[taskCount];
		// List<String> targetText = new ArrayList<String>();
		// List<String> targetValue = new ArrayList<String>();
		// int[] itemCount = new int[taskCount];
		// List<Integer> itemId = new ArrayList<Integer>();
		// List<String> itemName = new ArrayList<String>();
		// List<String> itemIcon = new ArrayList<String>();
		// List<Integer> days = new ArrayList<Integer>();
		// List<Integer> count = new ArrayList<Integer>();
		// List<Boolean> optional = new ArrayList<Boolean>();
		//
		// int i = 0;
		// for(TaskVo task:taskList){
		// taskId[i] = task.getTaskId();
		// taskType[i] = task.getTaskType();
		// taskName[i] = task.getTaskName();
		// taskDesc[i] = task.getTaskDesc();
		// exp[i] = task.getExp();
		// gold[i] = task.getGold();
		// taskStatus[i] = task.getTaskStatus();
		// targetCount[i] = task.getTargetCount();
		// targetText.addAll(task.getTgtext());
		// targetValue.addAll(task.getTgvalue());
		// itemCount[i] = task.getItemCount();
		// itemId.addAll(task.getItemId());
		// itemName.addAll(task.getItemName());
		// itemIcon.addAll(task.getItemIcon());
		// days.addAll(task.getDays());
		// count.addAll(task.getCount());
		// optional.addAll(task.getOptional());
		// i++;
		// }
		// SendTaskList sendTaskList = new SendTaskList(data.getSessionId(),
		// data.getSerial());
		// sendTaskList.setTaskCount(taskCount);
		// sendTaskList.setTaskId(taskId);
		// sendTaskList.setTaskType(taskType);
		// sendTaskList.setTaskName(taskName);
		// sendTaskList.setTaskDesc(taskDesc);
		// sendTaskList.setExp(exp);
		// sendTaskList.setGold(gold);
		// sendTaskList.setTaskStatus(taskStatus);
		// sendTaskList.setTargetCount(targetCount);
		// sendTaskList.setTargetText(targetText.toArray(new String[0]));
		// sendTaskList.setTargetValue(targetValue.toArray(new String[0]));
		// sendTaskList.setItemCount(itemCount);
		// sendTaskList.setItemId(ServiceUtils.getInts(itemId.toArray()));
		// sendTaskList.setItemName(itemName.toArray(new String[0]));
		// sendTaskList.setItemIcon(itemIcon.toArray(new String[0]));
		// sendTaskList.setDays(ServiceUtils.getInts(days.toArray()));
		// sendTaskList.setCount(ServiceUtils.getInts(count.toArray()));
		// sendTaskList.setOptional(ServiceUtils.getBooleans(optional.toArray()));
		//
		// session.write(sendTaskList);
		// }
		// } catch (Exception e) {
		// }
		return null;
	}
}