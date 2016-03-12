import java.io.IOException;
import java.util.Calendar;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class MapAge extends Mapper<LongWritable, Text, Text, Text> {
	protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
		String userdata[] = value.toString().split(",");
		int age = Calendar.getInstance().get(Calendar.YEAR) - Integer.parseInt(userdata[9].split("/")[2]);
		context.write(new Text(userdata[0]), new Text(String.valueOf(age)));
	}
}