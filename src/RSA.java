import java.util.*;
import java.math.*;

public class RSA {
	public static BigInteger p, q, e, d, n, phi_n, plain, cipher;
	public static int e_temp, d_temp, p_temp, q_temp;
	// 由于幂次运算及随机数选取需要用到int形式，故先定义一些对应的int变量
	public static long starttime, endtime;

	public static BigInteger gcd(BigInteger a, BigInteger b) {// 辗转相除法的实现
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

	public static boolean isPrime(int a) {// 判断是否是素数
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

	public static void CreateKey() {// 生成公钥私钥流程
		System.out.println("-----以下为秘钥生成过程-----");
		Scanner input = new Scanner(System.in);
		BigInteger k;
		BigInteger one;
		one = BigInteger.ONE;
		BigInteger zero;
		zero = BigInteger.ZERO;

		System.out.println("请输入素数p");// 获得p和q
		p = new BigInteger(input.nextLine());
		p_temp = Integer.valueOf(p.toString());// 将p,q临时转换为int形式（但若p*q较大，则下面的步骤会出问题）
		for (;;) {// 判断p是否为素数
			if (isPrime(p_temp) == false) {
				System.out.println("p不是素数，请重新输入");
				p = new BigInteger(input.nextLine());
				p_temp = Integer.valueOf(p.toString());
			} else {
				break;
			}
		}

		System.out.println("请输入素数q，且q与p相异");
		q = new BigInteger(input.nextLine());
		for (;;) {// 判断q是否与p相同，且是否为素数
			if (p.equals(q)) {
				System.out.println("p与q相同，请重新输入q");
				q = new BigInteger(input.nextLine());
				continue;
			}
			q_temp = Integer.valueOf(q.toString());
			if (isPrime(q_temp) == false) {
				System.out.println("q不是素数，请重新输入");
				q = new BigInteger(input.nextLine());
			} else {
				break;
			}
		}

		n = p.multiply(q);// 计算出n和phi_n
		phi_n = p.subtract(one).multiply(q.subtract(one));

		for (;;) {
			e_temp = (int) (1 + Math.random() * p_temp * q_temp);
			e = BigInteger.valueOf(e_temp);// 随机生成e

			if (gcd(e, phi_n).equals(one)) {// 判断e是否与phi_n互质
				System.out.println("公钥：e=" + e + "；n=" + n);
				break;
			}
		}

		starttime = System.currentTimeMillis();
		for (k = BigInteger.ONE;; k = k.add(one)) {// 求解私钥d
			if ((((k.multiply(phi_n)).add(one)).mod(e)).equals(zero)) {
				d = ((k.multiply(phi_n)).add(one)).divide(e);
				if (d.equals(e)) {
					continue;
				} else {
					System.out.println("私钥：d=" + d + "；n=" + n);
					break;
				}
			}
		}
		endtime = System.currentTimeMillis();
		System.out.println("计算私钥共耗时：" + (endtime - starttime) + " ms");
		System.out.println("-----生成秘钥已完成-----");
	}

	public static BigInteger Encrypt(BigInteger plain_read) {// 加密过程
		CreateKey();
		System.out.println("-----以下为加密过程-----");
		Scanner input = new Scanner(System.in);

		plain = plain_read;// 获取明文
		
		for (;;) {// 判断明文与n的大小
			if ((plain.compareTo(n)) == 1) {
				System.out.println("明文大于等于n！请结束程序并修改文件中的明文。");
				return BigInteger.ZERO;//返回0用于主函数做判定
			} else {
				break;
			}
		}
		System.out.println("从文件中读取明文，明文：" + plain);
		System.out.println("公钥：e=" + e + "；n=" + n);
		e_temp = Integer.valueOf(e.toString());

		starttime = System.currentTimeMillis();
		cipher = (plain.pow(e_temp)).mod(n);// 计算密文
		endtime = System.currentTimeMillis();

		System.out.println("密文：" + cipher + " ，文件已修改");
		System.out.println("加密耗时：" + (endtime - starttime) + " ms");
		System.out.println("-----加密过程已完成-----");
		return cipher;
	}

	public static BigInteger Decrypt() {// 解密过程
		System.out.println("-----以下为解密过程-----");
		System.out.println("私钥：d=" + d + "；n=" + n);
		System.out.println("密文：" + cipher);
		BigInteger tempplain;
		d_temp = Integer.valueOf(d.toString());

		starttime = System.currentTimeMillis();
		tempplain = (cipher.pow(d_temp)).mod(n);// 计算明文
		endtime = System.currentTimeMillis();

		System.out.println("明文：" + tempplain + " ，文件已修改");
		System.out.println("解密耗时：" + (endtime - starttime) + " ms");
		System.out.println("-----解密过程已完成-----");
		return tempplain;
	}
}
