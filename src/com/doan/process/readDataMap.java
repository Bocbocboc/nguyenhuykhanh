package com.doan.process;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import tronglv.gs.model.Share;
import android.content.Context;
import android.util.Log;

public class readDataMap {
	public static ArrayList<mapObj> listMap= new ArrayList<mapObj>();
	public static int[][] maTranMap;
	
	public readDataMap(Context context) {
		Log.e("bat dau read data", "bat dau read data");
		maTranMap = new int[Share.column][Share.row];
		
		final String storedString = ReadFromfile("map/jsondulieumap.txt", context);
		
		try{
	        JSONObject mainJson = new JSONObject(storedString);
	        
			JSONArray jsonArray = mainJson.getJSONArray("map");
			for (int index =0; index < jsonArray.length(); index++) {
				JSONObject objJson = jsonArray.getJSONObject(index);
				
				mapObj objItem = new mapObj();
				
				objItem.setTenMap(objJson.getString("TenMap"));
				objItem.setChieuDoc(objJson.getInt("ChieuDoc"));
				objItem.setChieuNgang(objJson.getInt("ChieuNgang"));
				objItem.setSoKimCuong(objJson.getInt("SoKimCuong"));
				objItem.setThoiGianChoi(objJson.getInt("ThoiGianChoi"));
				objItem.setMocDiem1(objJson.getInt("MocDiem1"));
				objItem.setMocDiem2(objJson.getInt("MocDiem2"));
				objItem.setMocDiem3(objJson.getInt("MocDiem3"));
				objItem.setXuVang(objJson.getInt("XuVang"));
				objItem.setXuBac(objJson.getInt("XuBac"));
				
				listMap.add(objItem);
			}
		}catch (JSONException e) {
			e.printStackTrace();
		}
		
		logMap(3);
	}
	
	public static void logMap(int stt){
		Log.e("log mapppp", ""+listMap.get(stt).getTenMap());
		Log.e("log mapppp", ""+listMap.get(stt).getChieuDoc());
		Log.e("log mapppp", ""+listMap.get(stt).getChieuNgang());
		Log.e("log mapppp", ""+listMap.get(stt).getSoKimCuong());
		Log.e("log mapppp", ""+listMap.get(stt).getThoiGianChoi());
		Log.e("log mapppp", ""+listMap.get(stt).getMocDiem1());
		Log.e("log mapppp", ""+listMap.get(stt).getMocDiem2());
		Log.e("log mapppp", ""+listMap.get(stt).getMocDiem3());
		Log.e("log mapppp", ""+listMap.get(stt).getXuVang());
		Log.e("log mapppp", ""+listMap.get(stt).getXuBac());
		
	}
	
	public String ReadFromfile(String fileName, Context context) {
	    StringBuilder ReturnString = new StringBuilder();
	    InputStream fIn = null;
	    InputStreamReader isr = null;
	    BufferedReader input = null;
	    try {
	        fIn = context.getResources().getAssets()
	                .open(fileName, context.MODE_WORLD_READABLE);
	        isr = new InputStreamReader(fIn);
	        input = new BufferedReader(isr);
	        String line = "";
	        while ((line = input.readLine()) != null) {
	        	if(line != " " || line != "\t")
	        		ReturnString.append(line);
	        }
	    } catch (Exception e) {
	        e.getMessage();
	    } finally {
	        try {
	            if (isr != null)
	                isr.close();
	            if (fIn != null)
	                fIn.close();
	            if (input != null)
	                input.close();
	        } catch (Exception e2) {
	            e2.getMessage();
	        }
	    }
	    return ReturnString.toString();
	}

}
