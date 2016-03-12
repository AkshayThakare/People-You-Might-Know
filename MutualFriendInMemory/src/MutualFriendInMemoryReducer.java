import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Reducer.Context;

public class MutualFriendInMemoryReducer extends Reducer<Text,Text,Text,Text> {
	private Text mutualFriends = new Text();
	public void reduce(Text key, Iterable<Text> values,Context context ) throws IOException, InterruptedException {
		StringBuilder finalfriendslist = new StringBuilder();
		for(Text t : values){
			finalfriendslist.append(t+",");
		}
		String s1 = finalfriendslist.toString();
		String finalstring = "["+s1+"]";
		mutualFriends.set(finalstring);
		context.write(key,mutualFriends);
	}
}