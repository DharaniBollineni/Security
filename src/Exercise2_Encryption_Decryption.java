
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/*
 *	Generate – private and public keys 
	Write private key into private Key file and public key into publickey file and read them when needed.
	Encrypt the text using the public key and decrypt the encrypted text using private key to readable form.
	Compare the text before and after encryption and decryption. 
 */

public class Exercise2_Encryption_Decryption {
	static PublicKey publicKeyRead;
	static PrivateKey privateKeyRead;

	public static void main(String[] args)
			throws NoSuchAlgorithmException, FileNotFoundException, IOException, ClassNotFoundException,
			InvalidKeyException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {

		
		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");// Creating KeyPair generator object		
		keyPairGen.initialize(1024);// Initializing the key pair generator - 1024 used for normal securities		
		KeyPair pair = keyPairGen.generateKeyPair();// Generate the pair of keys		
		PublicKey publicKey = pair.getPublic();// Getting the public key from the key pair		
		PrivateKey privateKey = pair.getPrivate();// Getting the private key from the key pair

		System.out.println("File Logs");
		System.out.println("-------------------------------------------------------------------");

		try (FileOutputStream filePriKey = new FileOutputStream("privateKey")) {// write the Private key object in the file
			ObjectOutputStream objectPriKey = new ObjectOutputStream(filePriKey);
			objectPriKey.writeObject(privateKey);
			System.out.println("Private key written to file successfully");
			objectPriKey.close();
			filePriKey.close();
		} catch (Exception e) {
			System.out.println("Something went wrong while writing private key");
		}

		try (FileOutputStream filePubKey = new FileOutputStream("publicKey")) {// write the public key object in the file
			ObjectOutputStream objectPubKey = new ObjectOutputStream(filePubKey);
			objectPubKey.writeObject(publicKey);
			System.out.println("Public key written to file successfully");
			objectPubKey.close();
			filePubKey.close();
		} catch (Exception e) {
			System.out.println("Something went wrong while writing public key");
		}

		try (FileInputStream filePriKeyRead = new FileInputStream("privateKey")) {// read the Private key object
			ObjectInputStream objectPriKeyRead = new ObjectInputStream(filePriKeyRead);
			privateKeyRead = (PrivateKey) objectPriKeyRead.readObject();
			System.out.println("Private key is read from the file successfully");
			objectPriKeyRead.close();
			filePriKeyRead.close();
		} catch (Exception e) {
			System.out.println("Something went wrong while reading private key");
		}

		try (FileInputStream filePubKeyRead = new FileInputStream("publicKey")) {// read the public key object
			ObjectInputStream objectPubKeyRead = new ObjectInputStream(filePubKeyRead);
			publicKeyRead = (PublicKey) objectPubKeyRead.readObject();
			System.out.println("Public key is read from the file successfully");
			objectPubKeyRead.close();
			filePubKeyRead.close();
		} catch (Exception e) {
			System.out.println("Something went wrong while reading public key");
		}
		System.out.println("-------------------------------------------------------------------");
		System.out.println("Print keys from files");
		System.out.println("-------------------------------------------------------------------");
		System.out.println("privateKeyRead::::     " + privateKeyRead);
		System.out.println("publicKeyRead::::      " + publicKeyRead);
		String strToEncrypt = "ICT School is Winning place";  // text to test
		System.out.println("-------------------------------------------------------------------");
		System.out.println("Text before encrption:     " + strToEncrypt);		
		byte[] encryptText = encrypt(strToEncrypt, publicKeyRead);// encrpt text using public key		
		String decryptedStr = decrypt(encryptText, privateKeyRead);// decrypt the encryptText using private key
		System.out.println("Text after decription:      " + decryptedStr);
	}

	/*****
	 * for encryption and decryption Creating a Cipher object using getInstance()
	 * Initialize the Cypher object using the init() - pass Cipher.ENCRYPT_MODE (or)
	 * Cipher.DECRYPT_MODE initialized Cipher object by passing the data to the
	 * update() // this is not used because i'm passing the byte[] of text doFinal()
	 * method of the Cipher class completes the encryption operation
	 */
	public static byte[] encrypt(String strToEncrypt, PublicKey publicKey) // encrypt using publicKey
			throws UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
			IllegalBlockSizeException, BadPaddingException {
		byte[] plaintTextByteArray = strToEncrypt.getBytes();
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		byte[] cipherText = cipher.doFinal(plaintTextByteArray); // writen the array of bytes.
		System.out.println("-------------------------------------------------------------------");		
		String s = new String(cipherText);// Convert byte[] to String
		// String s = Base64.getEncoder().encodeToString(cipherText); //java8
		System.out.println("Encrypted text is:: ");
		System.out.println(s);
		System.out.println("-------------------------------------------------------------------");
		return cipherText;
	}
	
	public static String decrypt(byte[] todecryptText, PrivateKey privateKey) // decrypt the encrypted text using private key
			throws NoSuchAlgorithmException,
			NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		byte[] retByte = cipher.doFinal(todecryptText);
		return new String(retByte);
	}

}
//Reference
//https://www.programcreek.com/java-api-examples/javax.crypto.Cipher
//https://www.tutorialspoint.com/java_cryptography/java_cryptography_creating_signature.htm



// other tries code
// try(FileOutputStream out= new FileOutputStream("privatekeyFile.txt")){
// out.write(privateKey.getEncoded());
// out.close();
// }
//
// try(FileOutputStream out= new FileOutputStream("publicFile.txt")){
// out.write(publicKey.getEncoded());
// out.close();
// }

