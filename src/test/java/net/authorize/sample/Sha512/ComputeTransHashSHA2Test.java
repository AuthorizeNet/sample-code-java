package net.authorize.sample.Sha512;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ComputeTransHashSHA2Test {

  @Rule public final ExpectedException thrown = ExpectedException.none();

  @Test
  public void getHMACSHA512InputNotNullNotNullOutputIllegalArgumentException() throws Exception {
  	thrown.expect(IllegalArgumentException.class);
    ComputeTransHashSHA2.getHMACSHA512("/", "/");
  }

  @Test
  public void getHMACSHA512InputNotNullNotNullOutputIllegalArgumentException2() throws Exception {
  	thrown.expect(IllegalArgumentException.class);
    ComputeTransHashSHA2.getHMACSHA512(
        "\u0003\u0001\u0001 \u0010\u0000      0                                                                       ",
        "\u0000\u0000\u0000?????????????????????????????????????");
  }

  @Test
  public void getHMACSHA512InputNotNullNullOutputIllegalArgumentException() throws Exception {
  	thrown.expect(IllegalArgumentException.class);
    ComputeTransHashSHA2.getHMACSHA512(
        "\u8003\u0001\u0000\u0000>\u0000\ubffe\ubffe\ubffe\u0003",
        null);
  }

  @Test
  public void getHMACSHA512InputNullNotNullOutputIllegalArgumentException() throws Exception {
  	thrown.expect(IllegalArgumentException.class);
    ComputeTransHashSHA2.getHMACSHA512(
        null, "\u0000\u0000\u0000??????????????????????");
  }

  @Test
  public void hexStringToByteArrayInputNotNullOutput0() {
	  Assert.assertArrayEquals(new byte[] {}, ComputeTransHashSHA2.hexStringToByteArray(""));
  }

  @Test
  public void hexStringToByteArrayInputNotNullOutputStringIndexOutOfBoundsException() {
	  thrown.expect(StringIndexOutOfBoundsException.class);
    ComputeTransHashSHA2.hexStringToByteArray("/");
  }

  @Test
  public void hexStringToByteArrayInputNotNullOutputStringIndexOutOfBoundsException2() {
	  thrown.expect(StringIndexOutOfBoundsException.class);
    ComputeTransHashSHA2.hexStringToByteArray("#\uff77\u0002");
  }
}
