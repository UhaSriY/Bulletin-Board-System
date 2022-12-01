import java.util.Scanner;
import java.rmi.Naming;
public class YalamanchiliP3Publisher implements YalamanchiliP3XXXXPublisher 
{
	YalamanchiliP3Publisher()
	{
	} // constructor
	public static void main(String[] args)
	{
		try 
		{
			YalamanchiliP3XXXXInterface inter = (YalamanchiliP3XXXXInterface) Naming.lookup("rmi://10.40.172.1:6952/pubsub"); //creating object  for interface and connecting to bulletin board
			boolean flag = true;
			String username = null;
			Scanner sc = new Scanner(System.in);
			YalamanchiliP3XXXXPublisher pub;
			YalamanchiliP3XXXXSubscriber sub;
			String auth = null;
			for (;;) 
			{
				if (flag) 
				{
					System.out.println("Enter publisher name: ");
					username = sc.nextLine(); //takes input from user
					System.out.println("Enter publisher password: ");
					String password = sc.nextLine(); //takes input from user
					auth = inter.authentication(username.toLowerCase(), password);//calling authentication method in the service class and allowing user to login
					flag = auth != null ? false : true;
					if (flag)
						System.out.println("Please enter valid credentials...");
					else 
					{
						System.out.println(username+" logged in successfully as a publisher....!");
						break;
					}
				}
			}
			while (!flag) 
			{
				System.out.println("Please enter your option from below:");
				System.out.println("1.Add Topic\n2.Add Content To Existing Publisher\n3.Exit");
				Integer option= sc.nextInt();//takes input from user
				pub = new YalamanchiliP3Publisher();
				switch(option)
				{
					case 1: 
						System.out.println(inter.publishTopic(pub.publish()));//calling publish topic method which is in service
						break;
					case 2: 
						System.out.println(inter.addingContentToExistingPublisher(pub.addContentToExistingPublisher()));//calling add content methods from service and publisher classes
						break;
					case 3: 
						System.out.println("Connection ended successfully...");
						System.exit(0);//ending connection
						break;
					default : 	
						break;
				}	
			}

		}
		catch (Exception e) 
		{
			System.out.printf("Exception",e);
			e.printStackTrace();
		}
	}
	//Creating variables for future use
	String topic = null; 
	String topicContent = null;
   	StringBuffer buffer = new StringBuffer();
	Scanner sc = new Scanner(System.in);
	@Override
	public String publish()
	{
		System.out.println("Enter valid new publisher topic:");
        	topic = sc.nextLine();//taking input from user
        	buffer.append(topic+":");//appending topic
        	System.out.println("Enter Content related to this Topic:");
        	topicContent = sc.nextLine();//taking input from user
        	buffer.append(topicContent);//appending content
        	return buffer.toString();
	}
	@Override
    	public String addContentToExistingPublisher()
	{
        	topic = null;
        	System.out.println("Enter valid publisher topic name to add content to it:");
        	topic = sc.nextLine();//taking input from user
		buffer.append(topic+":");//appending topic
        	System.out.println("Enter information related to the topic that need to be added to the existing publisher:");
        	topic = sc.nextLine();//taking input from user
        	buffer.append(topic); //appending content  
        	return buffer.toString();   
    	}
}