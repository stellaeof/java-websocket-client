package net.rcode.wsclient;


public class Message {
	private static final byte[] EMPTY_BYTES=new byte[0];
	
	// -- message opcodes
	public static final int OPCODE_CONTINUATION=0;
	public static final int OPCODE_CLOSE=1;
	public static final int OPCODE_PING=2;
	public static final int OPCODE_PONG=3;
	public static final int OPCODE_TEXT=4;
	public static final int OPCODE_BINARY=5;
	
	private int opcode;
	private byte[] messageData;
	private boolean userMessage;
	
	public Message(int opcode, byte[] messageData, boolean userMessage) {
		if (messageData==null) messageData=EMPTY_BYTES;
		this.opcode=opcode;
		this.messageData=messageData;
		this.userMessage=userMessage;
	}
	
	public Message(CharSequence textMessage) {
		this(OPCODE_TEXT, Util.getUTF8Bytes(textMessage), true);
	}
	
	public Message(byte[] binaryMessage) {
		this(OPCODE_BINARY, binaryMessage, true);
	}
	
	/**
	 * @return the size in bytes of the user data portion of the message
	 */
	public int getBytes() {
		return messageData.length;
	}
	
	/**
	 * @return true if the message is generated by or targeted at users.  false if it is
	 * system framing.
	 */
	public boolean isUserMessage() {
		return userMessage;
	}
	
	public boolean isText() {
		return opcode==OPCODE_TEXT;
	}
	
	public CharSequence getMessageText() {
		return Util.fromUTF8Bytes(messageData);
	}
	
	public int getOpcode() {
		return opcode;
	}
	
	public byte[] getMessageData() {
		return messageData;
	}
	
	@Override
	public String toString() {
		if (opcode==OPCODE_TEXT) return getMessageText().toString();
		else if (messageData!=null ){
			StringBuilder ret=new StringBuilder();
			for (byte b: messageData) {
				ret.append(Integer.toHexString(b)).append(" ");
			}
			return ret.toString();
		} else return "";
	}
}
