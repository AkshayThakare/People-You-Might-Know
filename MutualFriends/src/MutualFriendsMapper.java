import java.io.Console;
import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;


public class MutualFriendsMapper extends Mapper<Object, Text, Text, IntWritable> {
	private Text userKey = new Text();
	private IntWritable one = new IntWritable(1);
	private static String userA;
	private static String userB;
	public static final Log log = LogFactory.getLog(MutualFriendsMapper.class);

	public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
		Configuration conf = context.getConfiguration();
		userA = conf.get("UserA");
		userB = conf.get("UserB");
		log.info("*************************");
		log.info("User A: "+userA);
		log.info("User B: "+userB);
		String line = value.toString();
		String[] users = line.split("\t");
		String user = users[0];
		if(users.length==2){
			if(user.compareTo(userA)==0||user.compareTo(userB)==0){
				String[] friends = users[1].split(",");
				for(String friend : friends) {
//					String allFriends = users[1];
//					String userKeyId = user.compareTo(friend) < 0 ? user+","+friend : friend+","+user;
					userKey.set(friend);
					context.write(userKey, one);
					}
				}
		}
		
	}
}
