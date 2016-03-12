import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Reducer.Context;

public class FriendSuggestionReducer extends Reducer<IntWritable, Text, IntWritable, Text> {
    public void reduce(IntWritable key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        String[] value;
        HashMap<String, Integer> hash = new HashMap<String, Integer>();
        for (Text t1 : values) {
            value = (t1.toString()).split(",");
            if (value[0].equals("1")) {
                hash.put(value[1], -1);
            } else if (value[0].equals("2")) {
                if (hash.containsKey(value[1])) {
                    if (hash.get(value[1]) != -1) {
                        hash.put(value[1], hash.get(value[1]) + 1);
                    }
                } else {
                    hash.put(value[1], 1);
                }
            }
        }
        
        ArrayList<Entry<String, Integer>> list = new ArrayList<Entry<String, Integer>>();
        for (Entry<String, Integer> entry : hash.entrySet()) {
            if (entry.getValue() != -1) {   
                list.add(entry);
            }
        }
        
        Collections.sort(list, new Comparator<Entry<String, Integer>>() {
            public int compare(Entry<String, Integer> e1, Entry<String, Integer> e2) {
            	int returnvalue = e2.getValue().compareTo(e1.getValue());
            	if(returnvalue==0){
            		Long key1 = Long.parseLong(e1.getKey());
            		Long key2 = Long.parseLong(e2.getKey());
            		return key1.compareTo(key2);
            	}
            	return returnvalue;
            }
        });
        int MAX_RECOMMENDATION_COUNT = 10;
        if (MAX_RECOMMENDATION_COUNT < 1) {
            context.write(key, new Text(StringUtils.join(list, ",")));
        } else {
            ArrayList<String> top = new ArrayList<String>();
            for (int i = 0; i < Math.min(MAX_RECOMMENDATION_COUNT, list.size()); i++) {
                top.add(list.get(i).getKey());
            }
            context.write(key, new Text(StringUtils.join(top, ",")));
        }
    }
}