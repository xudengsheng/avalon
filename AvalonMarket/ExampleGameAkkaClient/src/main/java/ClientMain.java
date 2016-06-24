import java.io.File;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.ActorSystem;

public class ClientMain {
	public static void main(String[] args) {
		File file=new File("/Users/zero/Documents/github/Avalon/AvalonMarket/ExampleGameAkkaClient/conf/application.conf");
		Config cg = ConfigFactory.parseFile(file);
		cg.withFallback(ConfigFactory.defaultReference(Thread.currentThread().getContextClassLoader()));
		Config config = ConfigFactory.load(cg).getConfig("AVALON_CLIENT");
		ActorSystem system = ActorSystem.create("Client", config);
		ActorSelection selection =system.actorSelection("akka.tcp://AVALON@10.1.37.203:2551/user/AVALON_WORLD");
		selection.tell("Hello World", ActorRef.noSender());
	}
}
