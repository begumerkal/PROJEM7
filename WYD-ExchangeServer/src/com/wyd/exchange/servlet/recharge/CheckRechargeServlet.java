package com.wyd.exchange.servlet.recharge;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import com.wyd.exchange.bean.PlayerBillVo;
import com.wyd.exchange.server.factory.ServiceManager;
import com.wyd.exchange.utils.CryptionUtil;

/**
 * 充值对账
 * @author zpl
 */
public class CheckRechargeServlet extends HttpServlet {
    private static final long serialVersionUID = 1911747458628093909L;

    // /private static final String CONTENT_TYPE = "text/html";
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String ret = "success";
        try {
            byte[] data = CryptionUtil.inputStream2byte(req.getInputStream());
            String dataString = new String(CryptionUtil.Decrypt(data, ServiceManager.getManager().getConfiguration().getString("deckey")), "utf-8");
            JSONArray jsonArray = JSONArray.fromObject(dataString);
            @SuppressWarnings({ "unchecked", "deprecation" })
			List<PlayerBillVo> pbList = (List<PlayerBillVo>)JSONArray.toList(jsonArray, PlayerBillVo.class);
            
            if(pbList.size()>0){
            	String dirName = pbList.get(0).getArea();
            	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            	Calendar calendar = Calendar.getInstance();
            	calendar.add(Calendar.DAY_OF_MONTH, -1);
            	String timeStr = sdf.format(calendar.getTime());
            	String fileName = "DDD-"+dirName+"-"+timeStr;
                File f = new File("/home/efunftp/"+dirName);
         	    if(!f.exists()){
    	     	    f.mkdir();
    	     	    f = new File("/home/efunftp/"+dirName+"/"+fileName+".txt");
    	     	    if(!f.exists()){
    	     	    	f.createNewFile();//不存在则创建
    	     	    	importTxt("/home/efunftp/"+dirName+"/"+fileName+".txt", pbList);
    	     	    }
         	    }else{
         	    	f = new File("/home/efunftp/"+dirName+"/"+fileName+".txt");
     	     	    if(!f.exists()){
     	     	    	f.createNewFile();//不存在则创建
     	     	    	importTxt("/home/efunftp/"+dirName+"/"+fileName+".txt", pbList);
     	     	    }
         	    }
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            ret = "fail";
        }
        resp.setContentType("text/html;charset=utf-8");
        resp.setStatus(200);
        ServletOutputStream out = resp.getOutputStream();
        OutputStreamWriter os = new OutputStreamWriter(out, "utf-8");
        os.write(ret);
        os.flush();
        os.close();
    }
    
    
    public void importTxt(String fileName,List<PlayerBillVo> list){
    	try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
			
			String stringLine = "";
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        	for(PlayerBillVo pbv:list){
            	//（时间(yyyy-MM-dd HH:mm:ss)|游戏服|用户Id|efun订单号|原厂订单号|币种类型|充值金额|游戏币|对账月|系统Android/IOS）|efun用户ID
            	stringLine = sdf.format(pbv.getCreateTime())+"|"+pbv.getAreaId() +"|"+pbv.getPlayerId()+"|"+pbv.getOrderNum()+"|"+pbv.getOrderNum();
            	String[] tmp = pbv.getRemark().split("_");//获得币种类型
            	if(tmp.length==3){
            		stringLine += "|"+tmp[0]+"|"+pbv.getChargePrice()+"|"+pbv.getAmount()+"|"+tmp[2];
            	}else{
            		stringLine += "|"+tmp[0]+"|"+pbv.getChargePrice()+"|"+pbv.getAmount()+"|null";
            	}
            	
            	if(pbv.getOrderNum().indexOf("IOS")!=-1){
            		stringLine +="|IOS";
            	}else{
            		stringLine +="|Android";
            	}
            	stringLine +="|"+pbv.getUserId();
            	bw.write(stringLine);
				bw.newLine();
				bw.flush();
				stringLine="";
            }
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}
