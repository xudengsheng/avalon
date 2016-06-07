package com.avalon.http;

import java.util.concurrent.CompletionStage;

import akka.NotUsed;
import akka.actor.ActorSystem;
import akka.http.javadsl.ConnectHttp;
import akka.http.javadsl.Http;
import akka.http.javadsl.IncomingConnection;
import akka.http.javadsl.ServerBinding;
import akka.http.javadsl.model.ContentTypes;
import akka.http.javadsl.model.HttpEntities;
import akka.http.javadsl.model.HttpEntity;
import akka.http.javadsl.model.HttpMethods;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.HttpResponse;
import akka.http.javadsl.model.Uri;
import akka.japi.function.Function;
import akka.stream.ActorMaterializer;
import akka.stream.Materializer;
import akka.stream.javadsl.Flow;
import akka.stream.javadsl.Sink;
import akka.stream.javadsl.Source;
import akka.util.ByteString;

public class HttpTest {
	public static void main(String[] args) {
		ActorSystem system = ActorSystem.create();
		final Materializer materializer = ActorMaterializer.create(system);

		Source<IncomingConnection, CompletionStage<ServerBinding>> serverSource = Http.get(system)
				.bind(ConnectHttp.toHost("localhost", 8080), materializer);

		Flow<HttpRequest, HttpRequest, NotUsed> failureDetection = Flow.of(HttpRequest.class)
				.watchTermination((notUsed, termination) -> {
					termination.whenComplete((done, cause) -> {
						if (cause != null) {
							// signal the failure to external monitoring
							// service!
						}
					});
					return NotUsed.getInstance();
				});

		Flow<HttpRequest, HttpResponse, NotUsed> httpEcho = Flow.of(HttpRequest.class).via(failureDetection)
				.map(request -> {
					Source<ByteString, Object> bytes = request.entity().getDataBytes();
					HttpEntity.Chunked entity = HttpEntities.create(ContentTypes.TEXT_PLAIN_UTF8, bytes);

					return HttpResponse.create().withEntity(entity);
				});

		final Function<HttpRequest, HttpResponse> requestHandler = new Function<HttpRequest, HttpResponse>() {
			private static final long serialVersionUID = -5167908768264228645L;
			private final HttpResponse NOT_FOUND = HttpResponse.create().withStatus(404)
					.withEntity("Unknown resource!");
			private final HttpResponse SERVER_ERROR = HttpResponse.create().withStatus(500)
					.withEntity("SERVER ERROR!");
			@Override
			public HttpResponse apply(HttpRequest request) throws Exception {
				Uri uri = request.getUri();
				if (request.method() == HttpMethods.GET) {
					if (uri.path().equals("/"))
						return HttpResponse.create().withEntity(ContentTypes.TEXT_HTML_UTF8,
								"<html><body>Hello world!</body></html>");
					else if (uri.path().equals("/hello")) {
						String name = uri.query().get("name").orElse("Mister X");

						return HttpResponse.create().withEntity("Hello " + name + "!");
					} else if (uri.path().equals("/ping"))
						return HttpResponse.create().withEntity("PONG!");
					else
						return NOT_FOUND;
				} else
					return NOT_FOUND;
			}
		};

		CompletionStage<ServerBinding> serverBindingFuture = serverSource.to(Sink.foreach(connection -> {
			System.out.println("Accepted new connection from " + connection.remoteAddress());

			connection.handleWithSyncHandler(requestHandler, materializer);
			// this is equivalent to
			// connection.handleWith(Flow.of(HttpRequest.class).map(requestHandler),
			// materializer);
		})).run(materializer);

		serverBindingFuture.whenCompleteAsync((binding, failure) -> {
			// possibly report the failure somewhere...
			System.out.println("sss");
		} , system.dispatcher());
	}
}
