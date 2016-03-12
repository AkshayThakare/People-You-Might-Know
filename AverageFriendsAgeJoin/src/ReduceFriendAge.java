import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class ReduceFriendAge extends Reducer<Text, Text, Text, Text> {
		protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
			List<String> friends = new ArrayList<>();
			String age = "";
			for (Text val : values) {
				String str = val.toString();
				if (str.endsWith("X")) {
					friends.add(str.substring(0, str.length() - 1));
				} else {
					age += str;
				}
			}
			for (String friend : friends) {
				context.write(new Text(friend), new Text(age));
			}
		}
	}