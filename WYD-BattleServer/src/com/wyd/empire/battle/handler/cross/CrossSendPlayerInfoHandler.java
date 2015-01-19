package com.wyd.empire.battle.handler.cross;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import com.wyd.empire.battle.bean.BattleTeam;
import com.wyd.empire.battle.bean.WeapSkill;
import com.wyd.empire.battle.bean.WorldPlayer;
import com.wyd.empire.battle.service.factory.ServiceManager;
import com.wyd.empire.protocol.data.cross.CrossSendPlayerInfo;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;
import com.wyd.session.AcceptSession;
public class CrossSendPlayerInfoHandler implements IDataHandler {
    private static final Logger log = Logger.getLogger(CrossSendPlayerInfoHandler.class);

    public void handle(AbstractData message) throws Exception {
        CrossSendPlayerInfo sendPlayerInfo = (CrossSendPlayerInfo) message;
        AcceptSession session = (AcceptSession) message.getSource();
        try {
            int battleId = sendPlayerInfo.getBattleId();
            BattleTeam battleTeam = ServiceManager.getManager().getBattleTeamService().getBattleTeam(battleId);
            if (null != battleTeam) {
                int camp = battleTeam.getLoadRoom();
                int buffIndex = 0;
                int weaponSkillIndex = 0;
                WorldPlayer player;
                for (int i = 0; i < sendPlayerInfo.getPlayerId().length; i++) {
                    player = new WorldPlayer(session);
                    player.setRoomId(sendPlayerInfo.getRoomIds()[i]);
                    player.setBattleId(sendPlayerInfo.getBattleId());
                    player.setPlayerId(sendPlayerInfo.getPlayerId()[i]);
                    player.setPlayerName(sendPlayerInfo.getPlayerName()[i]);
                    player.setPlayerLevel(sendPlayerInfo.getPlayerLevel()[i]);
                    player.setSex(sendPlayerInfo.getBoyOrGirl()[i]);
                    player.setSuit_head(sendPlayerInfo.getSuit_head()[i]);
                    player.setSuit_face(sendPlayerInfo.getSuit_face()[i]);
                    player.setSuit_body(sendPlayerInfo.getSuit_body()[i]);
                    player.setSuit_weapon(sendPlayerInfo.getSuit_weapon()[i]);
                    player.setWeapon_type(sendPlayerInfo.getWeapon_type()[i]);
                    player.setSuit_wing(sendPlayerInfo.getSuit_wing()[i]);
                    player.setPlayer_title(sendPlayerInfo.getPlayer_title()[i]);
                    player.setPlayer_community(sendPlayerInfo.getPlayer_community()[i]);
                    player.setMaxHP(sendPlayerInfo.getMaxHP()[i]);
                    player.setMaxPF(sendPlayerInfo.getMaxPF()[i]);
                    player.setMaxSP(sendPlayerInfo.getMaxSP()[i]);
                    player.setAttack(sendPlayerInfo.getAttack()[i]);
                    player.setBigSkillAttack(sendPlayerInfo.getBigSkillAttack()[i]);
                    player.setCritRate(sendPlayerInfo.getCritRate()[i]);
                    player.setDefend(sendPlayerInfo.getDefence()[i]);
                    player.setBigSkillType(sendPlayerInfo.getBigSkillType()[i]);
                    player.setExplodeRadius(sendPlayerInfo.getExplodeRadius()[i]);
                    player.setInjuryFree(sendPlayerInfo.getInjuryFree()[i]);
                    player.setWreckDefense(sendPlayerInfo.getWreckDefense()[i]);
                    player.setReduceCrit(sendPlayerInfo.getReduceCrit()[i]);
                    player.setReduceBury(sendPlayerInfo.getReduceBury()[i]);
                    player.setZsleve(sendPlayerInfo.getZsleve()[i]);
                    player.setSkillful(sendPlayerInfo.getSkillful()[i]);
                    player.setPetId(sendPlayerInfo.getPetId()[i]);
                    player.setPetIcon(sendPlayerInfo.getPetIcon()[i]);
                    player.setPetType(sendPlayerInfo.getPetType()[i]);
                    player.setPetSkillId(sendPlayerInfo.getPetSkillId()[i]);
                    player.setPetProbability(sendPlayerInfo.getPetProbability()[i]);
                    player.setPetParam1(sendPlayerInfo.getPetParam1()[i]);
                    player.setPetParam2(sendPlayerInfo.getPetParam2()[i]);
                    player.setPetEffect(sendPlayerInfo.getPetEffect()[i]);
                    player.setPetVersion(sendPlayerInfo.getPetVersion()[i]);
                    player.setForce(sendPlayerInfo.getForce()[i]);
                    player.setArmor(sendPlayerInfo.getArmor()[i]);
                    player.setFighting(sendPlayerInfo.getFighting()[i]);
                    player.setWinRate(sendPlayerInfo.getWinRate()[i]);
                    player.setConstitution(sendPlayerInfo.getConstitution()[i]);
                    player.setAgility(sendPlayerInfo.getAgility()[i]);
                    player.setLucky(sendPlayerInfo.getLucky()[i]);
                    int itemIndex = i * 8;
                    int[] item_id = new int[8];
                    int[] item_used = new int[8];
                    String[] item_img = new String[8];
                    String[] item_name = new String[8];
                    String[] item_desc = new String[8];
                    int[] item_type = new int[8];
                    int[] item_subType = new int[8];
                    int[] item_param1 = new int[8];
                    int[] item_param2 = new int[8];
                    int[] item_ConsumePower = new int[8];
                    int[] specialAttackType = new int[8];
                    int[] specialAttackParam = new int[8];
                    for (int y = 0; y < 8; y++) {
                        item_id[y] = sendPlayerInfo.getItem_id()[itemIndex + y];
                        item_used[y] = sendPlayerInfo.getItem_used()[itemIndex + y];
                        item_img[y] = sendPlayerInfo.getItem_img()[itemIndex + y];
                        item_name[y] = sendPlayerInfo.getItem_name()[itemIndex + y];
                        item_desc[y] = sendPlayerInfo.getItem_desc()[itemIndex + y];
                        item_type[y] = sendPlayerInfo.getItem_type()[itemIndex + y];
                        item_subType[y] = sendPlayerInfo.getItem_subType()[itemIndex + y];
                        item_param1[y] = sendPlayerInfo.getItem_param1()[itemIndex + y];
                        item_param2[y] = sendPlayerInfo.getItem_param2()[itemIndex + y];
                        item_ConsumePower[y] = sendPlayerInfo.getItem_ConsumePower()[itemIndex + y];
                        specialAttackType[y] = sendPlayerInfo.getSpecialAttackType()[itemIndex + y];
                        specialAttackParam[y] = sendPlayerInfo.getSpecialAttackParam()[itemIndex + y];
                    }
                    player.setItem_id(item_id);
                    player.setItem_used(item_used);
                    player.setItem_img(item_img);
                    player.setItem_name(item_name);
                    player.setItem_desc(item_desc);
                    player.setItem_type(item_type);
                    player.setItem_subType(item_subType);
                    player.setItem_param1(item_param1);
                    player.setItem_param2(item_param2);
                    player.setItem_ConsumePower(item_ConsumePower);
                    player.setSpecialAttackType(specialAttackType);
                    player.setSpecialAttackParam(specialAttackParam);
                    int playerBuffCount = sendPlayerInfo.getPlayerBuffCount()[i];
                    List<Integer> buffType = new ArrayList<Integer>();
                    List<Integer> buffParam1 = new ArrayList<Integer>();
                    List<Integer> buffParam2 = new ArrayList<Integer>();
                    List<Integer> buffParam3 = new ArrayList<Integer>();
                    for (int x = 0; x < playerBuffCount; x++, buffIndex++) {
                        buffType.add(sendPlayerInfo.getBuffType()[buffIndex]);
                        buffParam1.add(sendPlayerInfo.getBuffParam1()[buffIndex]);
                        buffParam2.add(sendPlayerInfo.getBuffParam2()[buffIndex]);
                        buffParam3.add(sendPlayerInfo.getBuffParam3()[buffIndex]);
                    }
                    player.setBuffType(buffType);
                    player.setBuffParam1(buffParam1);
                    player.setBuffParam2(buffParam2);
                    player.setBuffParam3(buffParam3);
                    int weaponSkillCount = sendPlayerInfo.getPlayerWeaponSkillCount()[i];
                    player.setWeapSkillCount(weaponSkillCount);
                    for (int x = 0; x < weaponSkillCount; x++, weaponSkillIndex++) {
                        WeapSkill weapSkill = new WeapSkill();
                        weapSkill.setName(sendPlayerInfo.getWeaponSkillName()[weaponSkillIndex]);
                        weapSkill.setType(sendPlayerInfo.getWeaponSkillType()[weaponSkillIndex]);
                        weapSkill.setChance(sendPlayerInfo.getWeaponSkillChance()[weaponSkillIndex]);
                        weapSkill.setParam1(sendPlayerInfo.getWeaponSkillParam1()[weaponSkillIndex]);
                        weapSkill.setParam2(sendPlayerInfo.getWeaponSkillParam2()[weaponSkillIndex]);
                        if(0==x){
                            player.setWeapSkill1(weapSkill);
                        }else{
                            player.setWeapSkill2(weapSkill);
                        }
                    }
                    player.setActionTime(System.currentTimeMillis());
                    player.setServerName(sendPlayerInfo.getServerName());
                    ServiceManager.getManager().getBattleTeamService().enBattleTeam(battleId, player, camp, sendPlayerInfo.getRobot()[i]);
                }
                battleTeam.addSession(session, sendPlayerInfo.getRoomId());
                if (camp > 0) {
                    ServiceManager.getManager().getBattleTeamService().startBattle(battleId, sendPlayerInfo.getAnimationIndexCode());
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error(ex, ex);
        }
    }
}
