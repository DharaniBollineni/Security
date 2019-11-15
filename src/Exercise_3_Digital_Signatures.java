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
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Arrays;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/*
 *	Implementation:
 *------------------------
 * Generated the RSA key pairs - private and public keys using KeyPair 
 *class and KeyPairGenerator class. Written the private key into private Key file and public key 
 *into public key file using the stream classes and read them when needed. Created the
 * MessageDigest (using SHA-1 algorithm) object and passed the text to it. Later computed 
 * the message digest. Encrypt digest using public key and decrypted the encrypted one using 
 * private key to readable form using Cipher class. Compared the text before and after encryption 
 * and decryption. Please find the below screen short for output. 
 */

public class Exercise_3_Digital_Signatures {

	static PublicKey publicKeyRead;
	static PrivateKey privateKeyRead;

	public static void main(String[] args)
			throws NoSuchAlgorithmException, FileNotFoundException, IOException, ClassNotFoundException,
			InvalidKeyException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {		
		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");// Creating KeyPair generator object		
		keyPairGen.initialize(1024);// Initializing the key pair generator - 1024 used for normal securities
		KeyPair pair = keyPairGen.generateKeyPair(); // Generate the pair of keys		
		PublicKey publicKey = pair.getPublic();// Getting the public key from the key pair		
		PrivateKey privateKey = pair.getPrivate();// Getting the private key from the key pair

		System.out.println("File Logs");
		System.out.println("-------------------------------------------------------------------");
		try (FileOutputStream filePriKey = new FileOutputStream("privateKey")) {// write the Private key object in to privateKey file
			ObjectOutputStream objectPriKey = new ObjectOutputStream(filePriKey);
			objectPriKey.writeObject(privateKey);
			System.out.println("Private key written to file successfully");
			objectPriKey.close();
			filePriKey.close();
		} catch (Exception e) {
			System.out.println("Something went wrong while writing private key");
		}
		try (FileOutputStream filePubKey = new FileOutputStream("publicKey")) {// write the public key object in to publicKey file
			ObjectOutputStream objectPubKey = new ObjectOutputStream(filePubKey);
			objectPubKey.writeObject(publicKey);
			System.out.println("Public key written to publicKey file successfully");
			objectPubKey.close();
			filePubKey.close();
		} catch (Exception e) {
			System.out.println("Something went wrong while writing public key");
		}
		try (FileInputStream filePriKeyRead = new FileInputStream("privateKey")) {// read the Private key object
			ObjectInputStream objectPriKeyRead = new ObjectInputStream(filePriKeyRead);
			privateKeyRead = (PrivateKey) objectPriKeyRead.readObject();
			System.out.println("Private key is read from privateKey file successfully");
			objectPriKeyRead.close();
			filePriKeyRead.close();
		} catch (Exception e) {
			System.out.println("Something went wrong while reading private key");
		}
		try (FileInputStream filePubKeyRead = new FileInputStream("publicKey")) {// read the public key object
			ObjectInputStream objectPubKeyRead = new ObjectInputStream(filePubKeyRead);
			publicKeyRead = (PublicKey) objectPubKeyRead.readObject();
			System.out.println("Public key is read from publicKey file successfully");
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
		String strToEncrypt = "ICT School is Winning place";
		System.out.println("-------------------------------------------------------------------");
		System.out.println("Text before encrption:     " + strToEncrypt);		
		
		MessageDigest md = MessageDigest.getInstance("SHA-1");// Creating the MessageDigest object
		md.update(strToEncrypt.getBytes());// Passing data to the created MessageDigest Object
		byte[] digest = md.digest(); // Compute the message digest		
		byte[] encryptSig = encrypt(digest, publicKeyRead);// encrypt digest using public  key		
		byte[] decryptText = decrypt(encryptSig, privateKeyRead);// decrypt the encryptSig using private key
		String decryptedStr = new String(decryptText);		
		
		if (Arrays.equals(digest, decryptText)) {// compare digest array and decrypt array is same or not
			System.out.println("digest and decrypt array is same");
			// System.out.println("Text after decription: " + decryptedStr);
			System.out.println("digest is " + new String(digest));
			System.out.println("decrypt is " + new String(decryptText));
		}
		else {
			System.out.println("They are not same");
		}
	}
	/*****
	for encryption and decryption
 	Creating a Cipher object using getInstance()
	Initialize the Cypher object using the init() - pass Cipher.ENCRYPT_MODE (or) Cipher.DECRYPT_MODE
	initialized Cipher object by passing the data to the update() // this is not used because i'm passing the byte[] of text
	doFinal() method of the Cipher class completes the encryption operation	 
	*/
	public static byte[] encrypt(byte[] digestArray, PublicKey publicKeyRead)//encrypt digest using private key
			throws UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
			IllegalBlockSizeException, BadPaddingException {
		// byte[] plaintTextByteArray = strToEncrypt.getBytes();
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, publicKeyRead);
		byte[] cipherText = cipher.doFinal(digestArray); // writen the array of bytes.
		System.out.println("-------------------------------------------------------------------");
		String s = new String(cipherText);// Convert byte[] to String
		// String s = Base64.getEncoder().encodeToString(cipherText); //java8
		System.out.println("Encrypted digest is:: ");
		System.out.println(s);
		System.out.println("-------------------------------------------------------------------");
		return cipherText;

	}
	public static byte[] decrypt(byte[] todencrypt, PrivateKey privateKeyRead) // decrypt the encryptSig using private key
			throws NoSuchAlgorithmException,
			NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, privateKeyRead);
		return cipher.doFinal(todencrypt);
	}

}

// references:
//------------------------------------------------------------------------------------------------------------------
// https://www.tutorialspoint.com/java_cryptography/java_cryptography_message_digest.htm
// https://crypto.stackexchange.com/questions/33864/how-is-the-message-digest-related-to-signatures-and-encryption
