package com.avalon.core.message;

public enum MessageType
{
	InitAvalon, HasSenderPathMessage, DirectSessionMessage, LostConnect, 
	CluserSessionMessage, AddGameServerMember, BlockGameServerMember, LostGameServerMember,
	DistributionCluserSessionMessage, LocalSessionMessage, IOSessionReciveMessage, IOSessionReciveDirectMessage,
	ActorSendMessageToSession, IoSessionBinding, ConnectionSessionsBinding, CloseConnectionSessions, ServerClose, 
	CreateIOSessionActor, ReciveIOSessionMessage, CheckNoSessionTransport, TransportBindingServer, NetServerLost, TransportLostNetSession,
	TaskMessage,ServerIsTheSame,ServerOnline,ServerLost,SendRedirectMessage,ReciveRedirectMessage,DistributionConnectionSessionsProtocol,
	Ping,CancelTask,RunTask,RunTaskInfo
}
