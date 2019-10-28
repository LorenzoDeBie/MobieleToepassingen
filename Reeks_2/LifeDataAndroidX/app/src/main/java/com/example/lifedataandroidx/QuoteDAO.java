package com.example.lifedataandroidx;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class QuoteDAO extends ArrayList<Quote> {
    public QuoteDAO(Context c) {
        Log.i("context", c.toString());
        JSONObject result = null;

        try {
            // Read file into string builder
            InputStream inputStream = c.getApplicationContext().getResources().openRawResource(R.raw.quotes);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder builder = new StringBuilder();

            for (String line = null; (line = reader.readLine()) != null ; ) {
                builder.append(line).append("\n");
            }

            // Parse into JSONObject
            String resultStr = builder.toString();
            JSONTokener tokener = new JSONTokener(resultStr);
            result = new JSONObject(tokener);
            JSONArray a = result.getJSONArray("quotes");
            for (int i=0; i < a.length(); i++){
                String quote = a.getJSONObject(i).get("quote").toString();
                String author = a.getJSONObject(i).get("author").toString();
                add(new Quote(quote, author));
            }

        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
