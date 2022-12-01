import java.util.Scanner;
import java.rmi.Naming;
public class YalamanchiliP3Subscriber  implements YalamanchiliP3XXXXSubscriber
{
	YalamanchiliP3Subscriber()
	{
	}
	public static void main(String[] args)
	{
		try 
		{
			YalamanchiliP3XXXXInterface rmiObj = (YalamanchiliP3XXXXInterface) Naming.lookup("rmi://10.40.172.1:6952/pubsub");//creating object  for interface and connecting to bulletin board
			boolean flag = true;
			String username = null;
			Scanner sc = new Scanner(System.in);
			YalamanchiliP3XXXXPublisher publisher;
			YalamanchiliP3XXXXSubscriber sub;
			String auth = null;
			for (;;) 
			{
				if (flag) 
				{
					System.out.println("Enter subscriber name: ");
					username = sc.nextLine(); //takes input from user
					System.out.println("Enter subscriber password: ");
					String password = sc.nextLine(); //takes input from user
					auth = rmiObj.authentication1(username.toLowerCase(), password);//calling authentication method in the service class and allowing user to login
					flag = auth != null ? false : true;
					if (flag)
						System.out.println("Please enter valid credentials...");
					else 
					{
						System.out.println(username+" logged in successfully as a subscriber....!");
						break;
					}
				}
			}
			while (!flag) 
			{
				System.out.println("Please enter your option from below:");
				System.out.println("1.View Publisher Topics\n2.View Subscribed Topics\n3.Subscribe Topic\n4.Unsubscribe Topic\n5.Exit");
				Integer option= sc.nextInt(); //takes input from user
				sub = new YalamanchiliP3Subscriber();
				switch(option)
				{
					case 1: 
						System.out.println(rmiObj.viewTopics()); //calling view topics method which is in service class
						break;
					case 2: 
						System.out.println(rmiObj.viewSubscribedTopics(username,auth));//calling view subscribed topics method which is in service class
						break;
					case 3: 
						System.out.println(rmiObj.subscribeTopic(username,sub.subscribe()));//calling subscribe method which is in service class
						break;
					case 4: 
						System.out.println(rmiObj.unsubscribeTopic(username,sub.unSubscribe()));//calling unsubscribe topics method which is in service class
						break;
					case 5: 
						System.out.println("Connection ended successfully...");
						System.exit(0); //ending connection with bulletin board
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
	Scanner sc = new Scanner(System.in);
    	String topic = null;
    	StringBuffer buffer = new StringBuffer();
	@Override
	public String subscribe()
	{
		System.out.println("Enter topic name to subscribe:");
        	topic = sc.nextLine();
        	buffer.append(topic); //appending topic to the list
        	return buffer.toString();
	}
	@Override
	public String unSubscribe()
	{
		System.out.println("Enter topic name to unsubscribe:");
        	topic = sc.nextLine();
        	buffer.append(topic); //searching topic and then deleting it from list
        	return buffer.toString();
	}
}