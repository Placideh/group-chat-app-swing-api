/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package groupchatapplication;
import java.net.*;
import java.io.*;
import java.util.Vector;
/**
 *
 * @author placideh
 */
public class Server implements Runnable {
    Socket s;
    public static Vector client =new Vector();
    
    public Server(Socket s){
        try {
            this.s=s;   
        } catch (Exception e) {
        }
        
    }
    public void run(){
        try {
            BufferedReader reader= new BufferedReader(new InputStreamReader (s.getInputStream()));
            BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
            client.add(writer);
            while(true){
                String data=reader.readLine().trim();
                System.out.println("Received "+data);
                for(int i=0;i<client.size();i++){
                    try {
                        BufferedWriter write=(BufferedWriter)client.get(i);
                        write.write(data);
                        write.write("\r\n ");
                        write.flush();
                    } catch (Exception e) {
                    }
                }
            }
        } catch (Exception e) {
        }
    }
    public static void main(String[] args ) throws Exception{
        
         ServerSocket socket=new ServerSocket(21172);
        while(true){
            Socket s=socket.accept();
            Server server=new Server( s);
            Thread thread=new Thread(server);
            thread.start();
            
        }
    }
    
}
