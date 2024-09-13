package de.umr.ds.task2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EchoServer {
	private final ExecutorService pool;
    public static final int PORT = new Random().nextInt(65535 - 1024) + 1024;
	private final ServerSocket serverSocket = new ServerSocket(PORT);
	private boolean running = true;

    public EchoServer(int nThreads) throws IOException {
		System.out.println("Server started on port " + PORT);
		pool = Executors.newFixedThreadPool(nThreads);
		while (running) {
			Socket clientSocket = serverSocket.accept();
			pool.execute(new ServerResponseTask(clientSocket));
		}
    }

	public void stopServer() throws IOException {
		running = false;
		pool.shutdownNow();
		serverSocket.close();
	}

	public int getPort() {
		return PORT;
	}

	private class ServerResponseTask implements Runnable {
		Socket clientSocket;

		public ServerResponseTask(Socket clientSocket) {
			this.clientSocket = clientSocket;
		}

		public void run() {
			System.out.println("Client connected (" + clientSocket.getInetAddress() + ":" + clientSocket.getPort() + ")");
			try {
				BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

				String message = in.readLine();
				while (clientSocket.isConnected() && message != null) {
					System.out.println("Received message (" + clientSocket.getInetAddress() + ":" + clientSocket.getPort() + ") - " + message);
					out.println(message);
					message = in.readLine();
				}
			} catch (IOException ioException) {
				throw new RuntimeException("Error reading client socket/message", ioException);
			}
		}
	}

	public static void main(String[] args) {
		try {
			EchoServer server = new EchoServer(2);
		}
		catch (IOException ioException) {
			throw new RuntimeException("Error creating server", ioException);
		}
	}
}
