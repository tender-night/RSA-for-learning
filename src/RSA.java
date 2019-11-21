import java.util.*;
import java.math.*;

public class RSA {
	public static BigInteger p, q, e, d, n, phi_n, plain, cipher;
	public static int e_temp, d_temp, p_temp, q_temp;
	// �����ݴ����㼰�����ѡȡ��Ҫ�õ�int��ʽ�����ȶ���һЩ��Ӧ��int����
	public static long starttime, endtime;

	public static BigInteger gcd(BigInteger a, BigInteger b) {// շת�������ʵ��
		BigInteger r, temp;
		BigInteger zero;
		zero = BigInteger.ZERO;

		if (a.compareTo(b) == -1) {
			temp = a;
			a = b;
			b = temp;
		}

		r = a.mod(b);
		while (!r.equals(zero)) {
			a = b;
			b = r;
			r = a.mod(b);
		}
		return b;
	}

	public static boolean isPrime(int a) {// �ж��Ƿ�������
		boolean flag = true;

		if (a < 2) {
			return false;
		} else {
			for (int i = 2; i <= Math.sqrt(a); i++) {
				if (a % i == 0) {
					flag = false;
					break;
				}
			}
		}
		return flag;
	}

	public static void CreateKey() {// ���ɹ�Կ˽Կ����
		System.out.println("-----����Ϊ��Կ���ɹ���-----");
		Scanner input = new Scanner(System.in);
		BigInteger k;
		BigInteger one;
		one = BigInteger.ONE;
		BigInteger zero;
		zero = BigInteger.ZERO;

		System.out.println("����������p");// ���p��q
		p = new BigInteger(input.nextLine());
		p_temp = Integer.valueOf(p.toString());// ��p,q��ʱת��Ϊint��ʽ������p*q�ϴ�������Ĳ��������⣩
		for (;;) {// �ж�p�Ƿ�Ϊ����
			if (isPrime(p_temp) == false) {
				System.out.println("p��������������������");
				p = new BigInteger(input.nextLine());
				p_temp = Integer.valueOf(p.toString());
			} else {
				break;
			}
		}

		System.out.println("����������q����q��p����");
		q = new BigInteger(input.nextLine());
		for (;;) {// �ж�q�Ƿ���p��ͬ�����Ƿ�Ϊ����
			if (p.equals(q)) {
				System.out.println("p��q��ͬ������������q");
				q = new BigInteger(input.nextLine());
				continue;
			}
			q_temp = Integer.valueOf(q.toString());
			if (isPrime(q_temp) == false) {
				System.out.println("q��������������������");
				q = new BigInteger(input.nextLine());
			} else {
				break;
			}
		}

		n = p.multiply(q);// �����n��phi_n
		phi_n = p.subtract(one).multiply(q.subtract(one));

		for (;;) {
			e_temp = (int) (1 + Math.random() * p_temp * q_temp);
			e = BigInteger.valueOf(e_temp);// �������e

			if (gcd(e, phi_n).equals(one)) {// �ж�e�Ƿ���phi_n����
				System.out.println("��Կ��e=" + e + "��n=" + n);
				break;
			}
		}

		starttime = System.currentTimeMillis();
		for (k = BigInteger.ONE;; k = k.add(one)) {// ���˽Կd
			if ((((k.multiply(phi_n)).add(one)).mod(e)).equals(zero)) {
				d = ((k.multiply(phi_n)).add(one)).divide(e);
				if (d.equals(e)) {
					continue;
				} else {
					System.out.println("˽Կ��d=" + d + "��n=" + n);
					break;
				}
			}
		}
		endtime = System.currentTimeMillis();
		System.out.println("����˽Կ����ʱ��" + (endtime - starttime) + " ms");
		System.out.println("-----������Կ�����-----");
	}

	public static BigInteger Encrypt(BigInteger plain_read) {// ���ܹ���
		CreateKey();
		System.out.println("-----����Ϊ���ܹ���-----");
		Scanner input = new Scanner(System.in);

		plain = plain_read;// ��ȡ����
		
		for (;;) {// �ж�������n�Ĵ�С
			if ((plain.compareTo(n)) == 1) {
				System.out.println("���Ĵ��ڵ���n������������޸��ļ��е����ġ�");
				return BigInteger.ZERO;//����0�������������ж�
			} else {
				break;
			}
		}
		System.out.println("���ļ��ж�ȡ���ģ����ģ�" + plain);
		System.out.println("��Կ��e=" + e + "��n=" + n);
		e_temp = Integer.valueOf(e.toString());

		starttime = System.currentTimeMillis();
		cipher = (plain.pow(e_temp)).mod(n);// ��������
		endtime = System.currentTimeMillis();

		System.out.println("���ģ�" + cipher + " ���ļ����޸�");
		System.out.println("���ܺ�ʱ��" + (endtime - starttime) + " ms");
		System.out.println("-----���ܹ��������-----");
		return cipher;
	}

	public static BigInteger Decrypt() {// ���ܹ���
		System.out.println("-----����Ϊ���ܹ���-----");
		System.out.println("˽Կ��d=" + d + "��n=" + n);
		System.out.println("���ģ�" + cipher);
		BigInteger tempplain;
		d_temp = Integer.valueOf(d.toString());

		starttime = System.currentTimeMillis();
		tempplain = (cipher.pow(d_temp)).mod(n);// ��������
		endtime = System.currentTimeMillis();

		System.out.println("���ģ�" + tempplain + " ���ļ����޸�");
		System.out.println("���ܺ�ʱ��" + (endtime - starttime) + " ms");
		System.out.println("-----���ܹ��������-----");
		return tempplain;
	}
}
