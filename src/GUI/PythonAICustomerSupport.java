package GUI;

import java.io.*;
import java.net.Socket;

public class PythonAICustomerSupport 
{
    public PythonAICustomerSupport(int ID) 
    {
        String pythonScriptPath = "C:\\Users\\aa591\\Documents\\AdvancedProgrammingProject\\Python\\CustomerBot.Py"; 
        String pythonExe = "C:\\Program Files\\Python39\\python.exe"; 
        String server = "localhost";
        int port = 65432;
        String numberToSend = String.valueOf(ID);  // Convert int to String

        try {
            // Start Python server
            ProcessBuilder pb = new ProcessBuilder(pythonExe, pythonScriptPath);
            pb.redirectErrorStream(true);
            Process pythonProcess = pb.start();

            // Read Python server output in a separate thread
            new Thread(() -> {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(pythonProcess.getInputStream()))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        System.out.println("[Python] " + line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

            Thread.sleep(2000);

            // Connect to Python server and send number
            try (Socket socket = new Socket(server, port)) 
            {
                OutputStream out = socket.getOutputStream();
                InputStream in = socket.getInputStream();
                out.write(numberToSend.getBytes());
                out.flush();
                byte[] buffer = new byte[1024];
                int read = in.read(buffer);
                String response = new String(buffer, 0, read);
                System.out.println("Response from Python server: " + response);
            }
            // terminate the Python server afterwards
            pythonProcess.destroy();
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
    }
}

