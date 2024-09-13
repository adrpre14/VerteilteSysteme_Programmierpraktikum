package de.umr.ds.task2;


import java.io.*;
import java.net.*;
import java.util.Scanner;

public class TextClient {
	Socket clientSocket;

	BufferedReader reader;
	PrintWriter writer;

	public TextClient() {
		try {
			connectToServer();
			reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			writer = new PrintWriter(clientSocket.getOutputStream(), true);
		} catch (Exception e) {
			throw new RuntimeException("Could not connect to server", e);
		}
	}

	public TextClient(String host, int port) {
		try {
			connectToServer(host, port);
			reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			writer = new PrintWriter(clientSocket.getOutputStream(), true);
		} catch (Exception e) {
			throw new RuntimeException("Could not connect to server", e);
		}
	}

	public void connectToServer(String host, int port) {
		try {
			clientSocket = new Socket(host, port);
		}
		catch (Exception e) {
			throw new RuntimeException("Error connecting to host: " + host + " on port: " + port, e);
		}
	}

	public void connectToServer() {
		this.connectToServer("dsgw.mathematik.uni-marburg.de", 32823);
	}

	void stopAll() {
		try {
			if (reader != null) {
				reader.close();
			}
			if (writer != null) {
				writer.close();
			}
			if (clientSocket != null) {
				clientSocket.close();
			}
		} catch (Exception e) {
			throw new RuntimeException("Error closing resources", e);
		}
	}

	public static void main(String[] args) {
		TextClient tc = null;
		try {
			tc = new TextClient("localhost", 13697);
			Scanner sc = new Scanner(System.in);

			System.out.println("Give input (exit to stop): ");
			String input = sc.nextLine();

			while (!input.equals("exit") && tc.clientSocket.isConnected()) {
				tc.writer.println(input);
				System.out.println("Server response: " + tc.reader.readLine());
				System.out.println("Give input (exit to stop): ");
				input = sc.nextLine();
			}
		}
		catch (IOException e) {
            tc.stopAll();
			throw new RuntimeException("Error somewhere", e);
		}

		tc.stopAll();
	}
}