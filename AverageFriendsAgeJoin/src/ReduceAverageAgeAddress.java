import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class ReduceAverageAgeAddress extends Reducer<Text, Text, Text, Text> {
		Map<Long, String> map = new TreeMap<>(Collections.reverseOrder());
		protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
			long sum = 0;
			long count = 0;
			String address = "";
			for (Text val : values) {
				String str = val.toString();
				if (!str.contains(",")) {
					sum += Long.parseLong(str);
					count++;
				} else {
					address += str;
				}
			}
			if (count != 0)
				map.put(sum / count, key.toString() + ", " + address);
		}

		protected void cleanup(Context context) throws IOException, InterruptedException {
			int count = 0;
			for (Map.Entry<Long, String> m : map.entrySet()) {
				context.write(new Text(m.getValue()), new Text(m.getKey().toString()));
				count++;
				if (count == 20) {
					break;
				}
			}
		}

	}