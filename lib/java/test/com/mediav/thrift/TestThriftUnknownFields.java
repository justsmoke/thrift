package com.mediav.thrift;

import com.mediav.thrift.magic.Basic;
import com.mediav.thrift.extended.Alphabet;
import com.mediav.thrift.extended.Extended;
import com.mediav.thrift.extended.Extension;
import junit.framework.TestCase;
import org.apache.thrift.TDeserializer;
import org.apache.thrift.TException;
import org.apache.thrift.TSerializer;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocolFactory;

import java.util.*;

import static org.junit.Assert.assertArrayEquals;

/**
 * @author zhangyf
 * @version 1.0 2016-09-02
 */

public class TestThriftUnknownFields extends TestCase {

  public void test_thrift_deserialize_and_serialize() {
    byte[] bytes = new byte[1];
    bytes[0] = 8;

    Extension extension = new Extension();
    extension.setExt1((short) 1);
    extension.setExt2(2);
    extension.setExt3(3L);
    extension.setExt4(false);
    extension.setExt5((byte) 5);
    extension.setExt6((double) 6);
    extension.setExt7("7");
    extension.setExt8(bytes);
    extension.setExt9(Alphabet.D);

    Extended extended = new Extended();
    extended.setAlphabet(Alphabet.C);
    extended.setExt1((short) 1);
    extended.setExt2(2);
    extended.setExt3(3L);
    extended.setExt4(false);
    extended.setExt5((byte) 5);
    extended.setExt6((double) 6);
    extended.setExt7("7");
    extended.setExt8(bytes);
    extended.setEx9(Alphabet.A);
    extended.setExt10(extension);
    extended.setExt11(Arrays.asList(extension));
    Map<Extension, Extension> ext12 = new HashMap<Extension, Extension>();
    ext12.put(extension, extension);
    extended.setExt12(ext12);
    Set<Extension> ext13 = new HashSet<Extension>();
    ext13.add(extension);
    extended.setExt13(ext13);
    extended.setExt14(Arrays.asList(Alphabet.A, Alphabet.B, Alphabet.C, Alphabet.D));
    Map<Alphabet, Alphabet> ext15 = new HashMap<Alphabet, Alphabet>();
    ext15.put(Alphabet.A, Alphabet.B);
    ext15.put(Alphabet.C, Alphabet.D);
    extended.setExt15(ext15);
    extended.setExt16(EnumSet.allOf(Alphabet.class));

    List<TProtocolFactory> protocolFactories
        = Arrays.asList(new TBinaryProtocol.Factory(), new TCompactProtocol.Factory());

    for (TProtocolFactory factory : protocolFactories) {
      TDeserializer deserializer = new TDeserializer(factory);
      TSerializer serializer = new TSerializer(factory);
      try {
        byte[] data = serializer.serialize(extended);
        Basic basic = new Basic();
        deserializer.deserialize(basic, data);
        byte[] transformed = serializer.serialize(basic);
        assertArrayEquals("the byte array is unchanged after transformation with " + factory.toString(), data, transformed);
      } catch (TException e) {
        System.out.println(e);
        fail();
      }
    }


  }
}
