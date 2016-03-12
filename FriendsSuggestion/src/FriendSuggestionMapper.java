import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class FriendSuggestionMapper extends Mapper<LongWritable, Text, IntWritable, Text> {
    public void map(LongWritable key, Text value, Context output) throws IOException, InterruptedException {
        //iterating over each of the input lines
    	String line = value.toString();
    	//splitting the line by tab
        String[] userids = line.split("\t");
        //if line had user id along with friend list then proceed
        if (userids.length == 2) {
        	//getting the user id
            String user = userids[0];
            IntWritable userKey = new IntWritable(Integer.parseInt(user));
            //getting all the friends in an array
            String[] friends = userids[1].split(",");
            String friend1;
			String friend2;
            IntWritable friend1Key = new IntWritable();
			IntWritable friend2Key = new IntWritable();
            Text friend1Value = new Text();           
            Text friend2Value = new Text();
          //iterating over the friends list to emit the pairs
            for (int i = 0; i < friends.length; i++) { 
                friend1 = friends[i];
                friend1Value.set("1," + friend1);
                //emit the pair for user and his direct friend
                output.write(userKey, friend1Value);
                friend1Key.set(Integer.parseInt(friend1));
                friend1Value.set("2," + friend1);
                //iterating over friends list to emit the friends pair with user as mutual friend
                for (int j = i+1; j < friends.length; j++) {
                    friend2 = friends[j];
                    friend2Key.set(Integer.parseInt(friend2));
                    friend2Value.set("2," + friend2);
                    //emit the friends pair with user as mutual friend
                    output.write(friend1Key, friend2Value);
                    output.write(friend2Key, friend1Value);
                }
            }
        }
    }
}
