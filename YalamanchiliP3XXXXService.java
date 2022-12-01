import java.io.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.Buffer;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
public class YalamanchiliP3XXXXService extends UnicastRemoteObject implements YalamanchiliP3XXXXInterface
{
	protected YalamanchiliP3XXXXService() throws RemoteException
	{
		super();
	}
	Scanner sc = new Scanner(System.in);
	StringBuffer sb = new StringBuffer();
	@Override
	public String publishTopic(String topic) throws IOException//Publisher publishing topic method
	{
		FileWriter fw = new FileWriter("topicsInfo", true); //creating variable for file writer  which is taking topics information file as input
		BufferedWriter bw = new BufferedWriter(fw); // creating buffer writer for file writer
    		String[] data = topic.split(":"); //spliting the data in the input to get required information
		System.out.println("Topic is:"+data[0]); //displaying topic
		System.out.println("Content is:"+data[1]); //displaying content
		bw.write(data[0].toLowerCase()+":"+data[1]);
		bw.newLine();
		//closeing all files and displaying information
		fw.flush();
		bw.close();
		fw.close();	
		return "Successfully added "+data[0]+" Topic";
	}
	@Override
	public String addingContentToExistingPublisher(String topic) throws IOException //Publisher adds new content to the existing topics
	{
		String[] data = topic.split(":");//spliting the data in the input to get required information
		String info = null;
		boolean flag =false;
		File file = new File("topicsInfo");
		BufferedReader br = new BufferedReader(new FileReader(file));
		File tmp = new File("temp"); //creating tempporary file
		PrintWriter pw = new PrintWriter(new FileWriter(tmp)); //creating variable for print writer
		sb = new StringBuffer();
		while((info = br.readLine()) != null)//reads every line in the file
		{
			if(info.contains(data[0]))//matching info to the file to add content
			{
				info = sb.append(info+","+data[1]).toString(); //appending the info
				flag = true;
			}
			pw.println(info);
			pw.flush();
		}
		pw.close();//closing connections
		br.close();
		if(!flag)
			return "Please provide the existing topic name";
		if (!file.delete()) 
		{
            		System.out.println("Adding content failed");
            		return "";
        	}
        	if (!tmp.renameTo(file)) 
            		System.out.println("Adding content failed");
		br.close();
		return "Added content to the " + data[0] +" topic"; //displays information
	}
	@Override
	public String viewTopics() throws IOException 
	{
		sb= new StringBuffer();
		File topics = new File("topicsInfo");
        	BufferedReader br = new BufferedReader(new FileReader(topics));
		String data;
		sb.append("Publishers topics are:\n");
        	while ((data = br.readLine()) != null) 
		{
			String[] list = data.split(":"); //splits the data in the input to get required information
			sb.append(list[0].toUpperCase()+"\n");//displays topics
        	}
		br.close();
		return sb.toString();
	}
	@Override
	public String subscribeTopic(String username, String sub) throws IOException //subscriber subscribing topics
	{
		File subFile = new File("subscribersInfo");
		BufferedReader br = new BufferedReader(new FileReader(subFile));
		String data = null;
		File tempFile = new File("tmp");
		PrintWriter pw = new PrintWriter(new FileWriter(tempFile));
		sb = new StringBuffer();
		while((data=br.readLine())!=null)
		{
			if(data.contains(username))
			{
				data = sb.append(data+","+sub).toString(); //appends to the string and subscribing to the topic
			}
			pw.println(data);
			pw.flush();
		}
		pw.close();
		br.close();
		br=new BufferedReader(new FileReader(subFile));	
		br.close();
		if (!subFile.delete())
		{
            		return "Subscribing failed";
        	}
        	if (!tempFile.renameTo(subFile))
            		System.out.println("Subscribing failed");	
		return "Successfully subscribed to "+sub+" Topic";
	}
	public String unsubscribeTopic(String username, String unSub) throws IOException//subscriber unsubscribing topics
	{
		File unSubFile = new File("subscribersInfo");
		BufferedReader br = new BufferedReader(new FileReader(unSubFile));
		String data = null;
		File tempFile = new File("tmp");
		PrintWriter pw = new PrintWriter(new FileWriter(tempFile));
		sb = new StringBuffer();
		while((data = br.readLine()) != null)
		{
			if(data.contains(username))
			{
				data = data.replace(","+unSub,"").toString();	//replace the string and unsubscribing to the topic	
			}
			pw.println(data);
			pw.flush();
		}
		pw.close();
		br.close();
		br = new BufferedReader(new FileReader(unSubFile));
		br.close();
		if (!unSubFile.delete())
		{
            		System.out.println("Unsubscribing failed");
            		return "Unsubscribing failed";
		}
		if (!tempFile.renameTo(unSubFile)) 
            		System.out.println("Unsubscribing failed");
		return "Successfully unsubscribed to "+unSub+" Topic";
	}
	@Override
	public String viewSubscribedTopics(String username, String auth) throws IOException //subscriber viewing subscribed topics
	{
		FileReader subFile = new FileReader("subscribersInfo");
		BufferedReader br = new BufferedReader(subFile);
		String data = null;
		while ((data = br.readLine()) != null) 
		{
			if(data.contains(username))
			{
				auth = data.split("\\s+")[2];
			}
		}
		subFile.close();
		br.close();
		sb= new StringBuffer();
		String[] subTopicList = auth.split(",");//spliting the data in the input to get required information
		List<String> topicsList = Arrays.asList(subTopicList); 				
		File info = new File("topicsInfo");
		String data1;
        	BufferedReader br1 = new BufferedReader(new FileReader(info));
		sb.append("Your Subscribed Topics are :\n");
        	while ((data1 = br1.readLine())!= null)
		{
			String[] topicInfo= data1.split(":"); //spliting the data in the input to get required information
			if(topicsList.contains(topicInfo[0]))
			{ 			
				sb.append("\n"+topicInfo[0].toUpperCase()+":"); 
				for(String list:topicInfo[1].split("\n")) //spliting the data in the input to get required information
				sb.append(" "+list);
				sb.append("\n");
			}
        	}
		br.close();
		return sb.toString();
	}
	@Override	
	public String authentication(String username,String password)throws RemoteException, Exception//authentication for publisher
	{
		int flag=0;
		String resultString = null;
		try 
		{
			FileInputStream fis=new FileInputStream("publishersInfo.txt");   
			sc=new Scanner(fis);
			while(sc.hasNextLine())  
			{  			
				String str=sc.nextLine();
				String[] strArray = str.split("\\s+");//spliting the data in the input to get required information
				if(strArray[0].equals(username)&&strArray[1].equals(password))//authenticating username and password
				{
					flag=1;
					resultString=str;
				}
			}
			fis.close();
		}
		catch (FileNotFoundException e) 
		{		
			e.printStackTrace(); 		
		} 
		catch (IOException e) 	
		{			
			e.printStackTrace();
		}
		if(flag==1)
			System.out.println("Publisher Successfully logged in...");
		else
			resultString=null;
		return resultString;
	}
	@Override
	public String authentication1(String username,String password) throws RemoteException, Exception//authentication for subscriber 
	{
		FileReader file= new FileReader("subscribersInfo");
		BufferedReader br = new BufferedReader(file);
		String data= null;
		while ((data = br.readLine()) != null) 
		{
			String[] info = data.split("\\s+");//spliting the data in the input to get required information
			if (info[0].equals(username) && info[1].equals(password)) //authenticating username and password
			{ 
				System.out.println("Subscriber Successfully logged in...");
				//closing files and returning data
				file.close();
				br.close();
				return info[2];
			}
		}
		file.close();
		br.close();
		return null;
	}
}