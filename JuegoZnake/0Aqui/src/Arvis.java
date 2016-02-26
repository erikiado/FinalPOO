import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.OpenOption;


public class Arvis {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		File f = new File("holas.txt");
		String s = "Erick,200,Jorge,150,Erik,60";
		byte[] b = null;
		
		BufferedReader writer;
		try{
			writer = new BufferedReader(new FileReader(f));
			String l = writer.readLine();
			System.out.print(l);
		}catch(Exception e){
			System.out.print("VV");
		}
	}

}
