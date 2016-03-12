import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class MapFriends extends Mapper<LongWritable, Text, Text, Text> {
		protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
			String[] userfriend = value.toString().split("\t");
			if (userfriend.length == 2) {
				for (String friend : userfriend[1].split(",")) {
					context.write(new Text(userfriend[0]), new Text(friend + "X"));
				}
			}
		}
	}
