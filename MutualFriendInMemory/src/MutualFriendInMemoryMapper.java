import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Mapper.Context;

public class MutualFriendInMemoryMapper extends Mapper<Object, Text, Text, Text>{
	private Text newkey = new Text();   
	private Text friendslist = new Text();

	HashMap<String,String> myMap;

	public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
		Configuration conf = context.getConfiguration();
		String arr1 = new String(myMap.get(conf.get("UserA")));
		String arr2 = new String(myMap.get(conf.get("UserB")));
//		String[] sarr1 = arr1.split(",");
//		String[] sarr2 = arr2.split(",");
		ArrayList<String> compareList = new ArrayList<String>(Arrays.asList(arr1.split(",")));
		ArrayList<String> baseList = new ArrayList<String>(Arrays.asList(arr2.split(",")));
		baseList.retainAll(compareList);
		String line = value.toString();
		String[] userdata = line.split(",");
		String user = userdata[0];
		if(baseList.contains(user)){
			String usersKey = conf.get("UserA")+" "+conf.get("UserB");
			newkey.set(usersKey);
			String friendsInfo = userdata[1]+":"+userdata[6];
			friendslist.set(friendsInfo);
			context.write(newkey, friendslist);
		}
	}
	@Override
	protected void setup(Context context) throws IOException, InterruptedException {
		super.setup(context);
		myMap = new HashMap<String,String>();
		Configuration conf = context.getConfiguration();
		String myuserdataPath = conf.get("userfriends");
		Path part=new Path(myuserdataPath);
		FileSystem fs = FileSystem.get(conf);
		FileStatus[] fss = fs.listStatus(part);
		for (FileStatus status : fss) {
			Path pt = status.getPath();		        
			BufferedReader br=new BufferedReader(new InputStreamReader(fs.open(pt)));
			String line;
			line=br.readLine();
			while (line != null){
				String[] arr=line.split("\t");
				if(arr.length == 2){
					myMap.put(arr[0].trim(), arr[1].trim());
				}else{
					myMap.put(arr[0].trim(), null);
				}
				line=br.readLine();
			}  
		}  
	}
}