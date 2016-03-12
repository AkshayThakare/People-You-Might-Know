
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;


public class MutualFriendInMemoryApplication extends Configured implements Tool{

	public static void main(String[] args) throws Exception {

		int res = ToolRunner.run(new Configuration(),new MutualFriendInMemoryApplication(), args);
		System.exit(res);

	}

	@Override
	public int run(String[] args) throws Exception {
		Configuration conf = new Configuration();
		String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();		
		conf.set("userfriends", otherArgs[0]);
		conf.set("UserA", args[2]);
		conf.set("UserB", args[3]);
		Job job = new Job(conf, "MutualFriendInMemoryApplication");
		job.setJarByClass(MutualFriendInMemoryApplication.class);
		job.setMapperClass(MutualFriendInMemoryMapper.class);
		job.setReducerClass(MutualFriendInMemoryReducer.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		FileInputFormat.addInputPath(job, new Path(otherArgs[1]));
		FileOutputFormat.setOutputPath(job, new Path(otherArgs[4]));
		return job.waitForCompletion(true) ? 0 : 1;
	}

}
