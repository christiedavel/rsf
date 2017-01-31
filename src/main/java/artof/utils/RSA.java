package artof.utils;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class RSA {
  private BigInteger n, d, e;

  public RSA(int bitlen) {
    SecureRandom r = new SecureRandom();
    BigInteger p = new BigInteger(bitlen / 2, 100, r);
    BigInteger q = new BigInteger(bitlen / 2, 100, r);

    n = p.multiply(q);

    BigInteger m = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));

    e = new BigInteger("3");
    while (m.gcd(e).intValue() > 1)
      e = e.add(new BigInteger("2"));

    d = e.modInverse(m);
  }

  public BigInteger getEValue() {
    return e;
  }

  public BigInteger getNValue() {
    return n;
  }

  public BigInteger encrypt(BigInteger message) {
    return message.modPow(e, n);
  }

  public BigInteger encrypt(BigInteger message, BigInteger ev, BigInteger nv) {
    return message.modPow(ev, nv);
  }

  public BigInteger decrypt(BigInteger message) {
    return message.modPow(d, n);
  }
}

