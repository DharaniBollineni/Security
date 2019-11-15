
// imports for RSA	key	pairs and scanner

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Scanner;

public class MakingKeys1 {

	public static void main(String[] args) throws NoSuchAlgorithmException, FileNotFoundException {
		int[] arraykeyPoints = { 10, 20, 50, 100, 200, 500, 1000 };
		//String testname = null;

		for (int i = 0; i < arraykeyPoints.length; i++) {

			// change 2048 or 1024 for file names identity 
			String fileName = "2048_" + arraykeyPoints[i] + "_test.csv";
			File file = new File(fileName);
			BufferedWriter bw;
			FileWriter fw;
			long starttimeTotel = System.nanoTime(); 
			for (int j = 0; j < arraykeyPoints[i]; j++) {
				
				KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");

				// Initializing the key pair generator
				// 1024 used for normal securities or change to 2048
				long starttime = System.nanoTime();
				keyPairGen.initialize(2048);

				// Generate the pair of keys
				KeyPair pair = keyPairGen.generateKeyPair();

				// Getting the public key from the key pair
				PublicKey publicKey = pair.getPublic();

				// Getting the private key from the key pair
				PrivateKey privateKey = pair.getPrivate();

				long endtime = System.nanoTime(); // end time - time mesure
				long timeTaken = (endtime - starttime);
			//	System.out.println(timeTaken);
				
				try {
					fw = new FileWriter(file, true);
					bw = new BufferedWriter(fw);
					bw.newLine();
					bw.write(Long.toString(timeTaken));
					bw.close();
					fw.close();
				} catch (IOException ex) {
					System.out.printf("File failure:%s%n", ex);
				}

			}
			long endtimeTotel = System.nanoTime(); // end time - time mesure
			long timeTakenTotel=(endtimeTotel - starttimeTotel);
			System.out.println("Time taken to generate "+arraykeyPoints[i]+" keys is  "+timeTakenTotel+"	and its average	time per key "+(timeTakenTotel/arraykeyPoints[i]));
		}
	}

}

// public static void generateKeys(PrintWriter pw) throws
// NoSuchAlgorithmException {
//// Creating KeyPair generator object
// KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
//
// // Initializing the key pair generator
// // 1024 used for normal securities
// keyPairGen.initialize(1024);
//
// // Generate the pair of keys
// KeyPair pair = keyPairGen.generateKeyPair();
//
// Long starttime = System.nanoTime();
// // Getting the public key from the key pair
// PublicKey publicKey = pair.getPublic();
//
// // Getting the private key from the key pair
// PrivateKey privateKey = pair.getPrivate();
//
// Long endtime = System.nanoTime(); // end time - time mesure
// Long timeTaken=(endtime - starttime);
// String timestr=""+timeTaken;
// pw.print("timestr");
// pw.print("\n\r");
//// System.out.println("Public Key - " + publicKey);
//// System.out.println("Private Key - " + privateKey);
////
// }
//

// public static void writeResult(String test,Long time, int arraykeyPoints)
// throws FileNotFoundException
// {
// String fileName="1024_"+arraykeyPoints+"_test.csv";
// //File file=new File(fileName);
// PrintWriter pw=new PrintWriter(fileName);
// String timestr=""+time;
// pw.println("timestr");
//
//
//
// }
// public static void writeResult(String test,Long time,int arraykeyPoints) {
// String fileName="keyTime"+arraykeyPoints+".csv";
// File file=new File(fileName);
// BufferedWriter br;
// FileWriter fr;
//
// try {
// fr=new FileWriter(file,true);
// br=new BufferedWriter(fr);
// br.newLine();
// br.write(String.format("%s,%.10f", test,time));
// br.close();
// fr.close();
// }catch(IOException ex) {System.out.printf("File failure:%s%n", ex);}
//
//
// }
// refrence url
// https://www.tutorialspoint.com/java_cryptography/java_cryptography_creating_signature.htm

//ICT School is Winning place