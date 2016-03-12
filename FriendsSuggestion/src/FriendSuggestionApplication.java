import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class FriendSuggestionApplication extends Configured implements Tool{

	public static void main(String[] args) throws Exception {
		int res = ToolRunner.run(new Configuration(),new FriendSuggestionApplication(), args);
		System.exit(res);
	}
	@Override
	public int run(String[] args) throws Exception {
		int exitCode = 0;
		Configuration conf = new Configuration();
		 
        Job job = new Job(conf, "FriendSuggestionApplication");
        job.setJarByClass(FriendSuggestionApplication.class);
        job.setOutputKeyClass(IntWritable.class);
        job.setOutputValueClass(Text.class);
 
        job.setMapperClass(FriendSuggestionMapper.class);
        job.setReducerClass(FriendSuggestionReducer.class);
 
        job.setInputFormatClass(TextInputFormat.class);
        job.setOutputFormatClass(TextOutputFormat.class);
 
        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
		exitCode = job.waitForCompletion (true) ? 0 : 1;
		return exitCode;
	}

}
