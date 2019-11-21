import java.util.*;
import java.io.*;
import java.math.*;

public class testRSA {

	static StringBuffer buff = new StringBuffer();// 缓冲区
	static String filename;// 文件名

	public static void Editor(String text) {//对文件内容进行编辑
		buff.delete(0, text.length());
		buff.append(text);
	}

	public static void savedisk() {//写文件并存盘
		try {
			File f = new File(filename);
			if (!f.exists()) {
				f.createNewFile();
			}
			OutputStreamWriter oWriter = new OutputStreamWriter(new FileOutputStream(f), "UTF-8");
			BufferedWriter bWriter = new BufferedWriter(oWriter);
			bWriter.write("\n");
			bWriter.write(buff.toString());// 写入内容

			bWriter.close();
			oWriter.close();
		} catch (IOException e2) {
			e2.printStackTrace();
		}
	}

	public static String readdisk() {//读取文件
		FileInputStream in;
		BufferedReader br;
		String aline = "1";
		String temp = null;

		try {
			in = new FileInputStream(filename);
			br = new BufferedReader(new InputStreamReader(in, "utf-8"));
			do {
				aline = br.readLine();
				if (aline != null) {
					temp = aline;
				}
			} while (aline != null);
			br.close();
			in.close();
		}

		catch (IOException e2) {
			e2.printStackTrace();
		}
		return temp;
	}

	public static void main(String[] args) {
		RSA rsa = new RSA();
		Scanner input = new Scanner(System.in);
		BigInteger plain_read;
		String cipher_write;
		int a;

		filename = "C:\\Users\\我科\\Desktop\\我爱学习\\电子商务安全与管理\\RSA算法作业\\plain.txt";
		plain_read = new BigInteger(readdisk());

		cipher_write = (rsa.Encrypt(plain_read)).toString();
		if(cipher_write.equals("0")) {//当返回值为0时，代表明文大于n，此时要求用户重新输入明文
			System.out.println("程序结束");
		}else {
			Editor(cipher_write);
			savedisk();

			System.out.println("若想查看解密过程，请输入1");
			a = Integer.parseInt(input.nextLine());
			if (a == 1) {
				Editor(rsa.Decrypt().toString());
				savedisk();
			} else {
				System.out.println("-----程序结束-----");
			}
		}		
	}
}
