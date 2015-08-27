package com.avalon.core.cluster;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import scala.collection.immutable.IndexedSeq;
import akka.actor.ActorRef;
import akka.contrib.pattern.ShardCoordinator;
import akka.contrib.pattern.ShardCoordinator.AbstractShardAllocationStrategy;
/**
 * 集群在平衡策略
 * @author ZERO
 *
 */
public class AvalonShardAllocationStrategy extends AbstractShardAllocationStrategy {

	@Override
	public ActorRef allocateShard(ActorRef requester, String shardId, Map<ActorRef, IndexedSeq<String>> currentShardAllocations) {
		ActorRef allocateShard = new ShardCoordinator.LeastShardAllocationStrategy(1, 10).allocateShard(requester, shardId, (scala.collection.immutable.Map<ActorRef, IndexedSeq<String>>) currentShardAllocations);
		
		return 
				allocateShard;
	}

	@Override
	public Set<String> rebalance(Map<ActorRef, IndexedSeq<String>> currentShardAllocations, Set<String> rebalanceInProgress) {
		return new HashSet<String>();
	}

}
