package com.company;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.*;


/**
 * Created by lavanyadixit on 12/04/17.
 */
public class DistanceCalculator {


    public DistanceCalculator() throws Exception {
    }

    public float calculateDistance(String origin,String destination) throws Exception{
        float distance=0.0f;
        URL url = new URL("https://maps.googleapis.com/maps/api/distancematrix/json?origins="+origin+"&destinations="+destination);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        String line, outputString = "";
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(conn.getInputStream()));
        while ((line = reader.readLine()) != null) {
            outputString = outputString + line;
        }
        System.out.println(outputString);
        JSONObject obj = new JSONObject(outputString);
        JSONArray pageName = obj.getJSONArray("rows");
        for(Object object:pageName){
            JSONObject jsonObject;
            if(object instanceof JSONObject)
            {
                jsonObject=(JSONObject)object;
                JSONArray elementsArray = jsonObject.getJSONArray("elements");
               distance= Float.valueOf(((JSONObject)((JSONObject)elementsArray.get(0)).get("distance")).get("value").toString());
            }
        }
        System.out.println(distance);
        return distance;
               // .getJSONObject("elements").getString("pageName");

    }

    public float distancePointInput(String[] ar) throws Exception{
        System.out.println("points are:" + ar[0]);
        for(int i=0;i<ar.length;i++){
            ar[i]=ar[i].replaceAll(" ","");
           ar[i]= ar[i].replaceAll(",","+");

        }
       return  new DistanceCalculator().calculateDistance(ar[0],ar[1]);
       // new DistanceCalculator().calculateDistance("Vancouver+BC","San+Francisco");
    }
}
