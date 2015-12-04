package test.avalon.core;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.avalon.core.AvalonActorSystem;
import com.avalon.core.message.AvalonMessageEvent;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.testkit.JavaTestKit;
import akka.testkit.TestActorRef;

public class Main {

	static ActorSystem system;

	@BeforeClass
	public static void setup() {
		system = ActorSystem.create();
	}

	@AfterClass
	public static void teardown() {
		JavaTestKit.shutdownActorSystem(system);
		system = null;
	}

	public static void main(String[] args) {
		Integer a = 10_00;
		Integer b = 10_00;

		System.out.println(a.intValue() == b.intValue());
		System.out.println(1000 == 1000);
	}

	@Test
	public void demonstrateTestActorRef() {
		final Props props = Props.create(AvalonActorSystem.class, system);
		final TestActorRef<AvalonActorSystem> ref = TestActorRef.create(system, props, "testA");
		AvalonMessageEvent init = new AvalonMessageEvent.InitAvalon();
		ref.tell(init, ActorRef.noSender());
	}
}
