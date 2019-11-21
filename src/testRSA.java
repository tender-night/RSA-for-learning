import java.util.*;
import java.io.*;
import java.math.*;

public class testRSA {

	static StringBuffer buff = new StringBuffer();// ������
	static String filename;// �ļ���

	public static void Editor(String text) {//���ļ����ݽ��б༭
		buff.delete(0, text.length());
		buff.append(text);
	}

	public static void savedisk() {//д�ļ�������
		try {
			File f = new File(filename);
			if (!f.exists()) {
				f.createNewFile();
			}
			OutputStreamWriter oWriter = new OutputStreamWriter(new FileOutputStream(f), "UTF-8");
			BufferedWriter bWriter = new BufferedWriter(oWriter);
			bWriter.write("\n");
			bWriter.write(buff.toString());// д������

			bWriter.close();
			oWriter.close();
		} catch (IOException e2) {
			e2.printStackTrace();
		}
	}

	public static String readdisk() {//��ȡ�ļ�
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

		filename = "C:\\Users\\�ҿ�\\Desktop\\�Ұ�ѧϰ\\��������ȫ�����\\RSA�㷨��ҵ\\plain.txt";
		plain_read = new BigInteger(readdisk());

		cipher_write = (rsa.Encrypt(plain_read)).toString();
		if(cipher_write.equals("0")) {//������ֵΪ0ʱ���������Ĵ���n����ʱҪ���û�������������
			System.out.println("�������");
		}else {
			Editor(cipher_write);
			savedisk();

			System.out.println("����鿴���ܹ��̣�������1");
			a = Integer.parseInt(input.nextLine());
			if (a == 1) {
				Editor(rsa.Decrypt().toString());
				savedisk();
			} else {
				System.out.println("-----�������-----");
			}
		}		
	}
}
