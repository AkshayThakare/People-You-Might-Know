import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class MapAddress extends Mapper<LongWritable, Text, Text, Text> {
		protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
			String data[] = value.toString().split(",");
			context.write(new Text(data[0]),new Text(data[3] + "," + data[4] + "," + data[5] + "," + data[6] + "," + data[7]));
		}
	}