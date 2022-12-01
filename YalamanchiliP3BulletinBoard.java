import java.util.Scanner;
import java.rmi.Remote;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
public class YalamanchiliP3BulletinBoard
{
	public static void main(String[] args) throws Exception
	{
		try
		{
			YalamanchiliP3XXXXService service = new YalamanchiliP3XXXXService(); //creating service object
			Registry registry = LocateRegistry.createRegistry(6952); //creating registry
			registry.bind("pubsub", (Remote) service);
			System.out.println("******* Bulletin board application started *******");
			Scanner sc = new Scanner(System.in);
			while (true)
			{
				String input = sc.nextLine(); //takes the input from user
				if(input.toLowerCase().equals("exit")) // to exit from the bulletin board
				{
					System.exit(0);
				}
			}
		}
		catch (Exception e)
		{
			System.out.printf("Exception:"+e);
		}
	}
}