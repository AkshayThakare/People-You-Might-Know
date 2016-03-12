import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.MultipleInputs;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class AverageFriendAgeApplication extends Configured implements Tool{

	public static void main(String[] args) throws Exception {
		int res = ToolRunner.run(new Configuration(),new AverageFriendAgeApplication(), args);
		System.exit(res);
	}
	public int run(String[] args) throws Exception {
		Configuration conf = new Configuration();
		String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
		Job job = new Job(conf, "AverageAgeMapper");
		job.setJarByClass(AverageFriendAgeApplication.class);
		MultipleInputs.addInputPath(job, new Path("/socNetData/networkdata"), TextInputFormat.class, MapFriends.class);
		MultipleInputs.addInputPath(job, new Path("/socNetData/userdata"), TextInputFormat.class, MapAge.class);
		job.setReducerClass(ReduceFriendAge.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		String str = "/ast14230/intermediate" + System.currentTimeMillis();
		FileOutputFormat.setOutputPath(job, new Path(str));
		int returnVal = 0;
		if (job.waitForCompletion(true) == true) {
			Job job2 = new Job(conf, "TwentyAverageAges");
			job2.setJarByClass(AverageFriendAgeApplication.class);
			MultipleInputs.addInputPath(job2, new Path(str), TextInputFormat.class, MapPassthrough.class);
			MultipleInputs.addInputPath(job2, new Path("/socNetData/userdata"), TextInputFormat.class, MapAddress.class);
			job2.setReducerClass(ReduceAverageAgeAddress.class);
			job2.setOutputKeyClass(Text.class);
			job2.setOutputValueClass(Text.class);
			FileOutputFormat.setOutputPath(job2, new Path(otherArgs[0]));
			returnVal = job2.waitForCompletion(true) ? 0 : 1;
		}
		return returnVal;
	}
}
