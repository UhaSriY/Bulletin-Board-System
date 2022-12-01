import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
public interface YalamanchiliP3XXXXInterface extends Remote
{
	public String publishTopic(String topic)throws FileNotFoundException, IOException; //Publisher publishing topic method
	public String addingContentToExistingPublisher(String topic) throws IOException; //Publisher adds new content to the existing topics
	public String viewTopics() throws IOException;
	public String subscribeTopic(String username,String password)throws FileNotFoundException, IOException;//subscriber subscribing topics
	public String viewSubscribedTopics(String username,String password) throws IOException;//subscriber viewing subscribed topics
	public String unsubscribeTopic(String username,String password)throws FileNotFoundException, IOException;//subscriber unsubscribing topics
	public String authentication(String username,String password)throws RemoteException, Exception;//authentication for publisher
	public String authentication1(String username,String password) throws RemoteException, Exception;//authentication for subscriber
}