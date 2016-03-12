import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class MutualFriendsApplication extends Configured implements Tool{

	public static void main(String[] args) throws Exception {
		int res = ToolRunner.run(new Configuration(),new MutualFriendsApplication(), args);
		System.exit(res);
	}
	@Override
	public int run(String[] args) throws Exception {
		int exitCode = 0;
		Configuration conf = new Configuration();
		//JobConf jobconf = (JobConf)getConf();
		conf.set("UserA", args[2]);
		conf.set("UserB", args[3]);
        Job job = new Job(conf, "MutualFriendsApplication");
        job.setJarByClass(MutualFriendsApplication.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);
 
        job.setMapperClass(MutualFriendsMapper.class);
        job.setReducerClass(MutualFriendsReducer.class);
 
        job.setInputFormatClass(TextInputFormat.class);
        job.setOutputFormatClass(TextOutputFormat.class);
 
        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
		exitCode = job.waitForCompletion (true) ? 0 : 1;
		return exitCode;
	}

}
