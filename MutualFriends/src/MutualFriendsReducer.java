import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;


public class MutualFriendsReducer extends Reducer<Text, IntWritable, Text, Text> {
	private static String userA;
	private static String userB;
	private Text users = new Text();
	private Text friends = new Text();
	IntWritable result = new IntWritable();
	StringBuilder s1 = new StringBuilder();
	public void reduce(Text key, Iterable<IntWritable> values, Context context)throws IOException, InterruptedException {
		int sum = 0;
		for(IntWritable value : values){
			sum +=value.get();
		}
		if(sum>1){
			s1.append(key+",");
		}
	}
	@Override
	protected void cleanup(Reducer<Text, IntWritable, Text, Text>.Context context)throws IOException, InterruptedException {
		Configuration conf = context.getConfiguration();
		userA = conf.get("UserA");
		userB = conf.get("UserB");
		String res = s1.toString();
		friends.set(res);
		users.set(userA+","+userB);
		context.write(users, friends);
	}
	
}