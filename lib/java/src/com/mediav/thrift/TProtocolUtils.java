package com.mediav.thrift;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.*;

import java.nio.ByteBuffer;
import java.util.*;

/**
 * @author zhangyf
 * @version 1.0 2016-09-01
 */

public class TProtocolUtils {

  private static final TStruct ANONYMOUS_STRUCT = new TStruct();

  public static void write(TProtocol prot, TData TData) throws TException {
    TField tField = TData.gettField();
    switch (tField.type) {
      case TType.BOOL:
        prot.writeBool((Boolean) TData.getValue());
        break;

      case TType.BYTE:
        prot.writeByte((Byte) TData.getValue());
        break;

      case TType.I16:
        prot.writeI16((Short) TData.getValue());
        break;

      case TType.I32:
      case TType.ENUM:
        prot.writeI32((Integer) TData.getValue());
        break;

      case TType.I64:
        prot.writeI64((Long) TData.getValue());
        break;

      case TType.DOUBLE:
        prot.writeDouble((Double) TData.getValue());
        break;

      case TType.STRING:
        prot.writeBinary((ByteBuffer) TData.getValue());
        break;

      case TType.STRUCT:
        prot.writeStructBegin(ANONYMOUS_STRUCT);
        ArrayList<TData> TDatas = TData.getStructFields();
        for (int i = 0; i < TDatas.size(); i++) {
          TData oneField = TDatas.get(i);
          prot.writeFieldBegin(oneField.gettField());
          write(prot, oneField);
          prot.writeFieldEnd();
        }
        prot.writeFieldStop();
        prot.writeStructEnd();
        break;

      case TType.MAP:
        Map<TData, TData> mapFields = TData.getMapFields();
        prot.writeMapBegin(new TMap(TData.getMapKeyType(), TData.getMapValueType(), mapFields.size()));
        for (Map.Entry<TData, TData> entry : mapFields.entrySet()) {
          write(prot, entry.getKey());
          write(prot, entry.getValue());
        }
        prot.writeMapEnd();
        break;

      case TType.SET:
        Set<TData> setFields = TData.getSetFields();
        prot.writeSetBegin(new TSet(TData.getSetElementType(), setFields.size()));
        for (TData element : setFields) {
          write(prot, element);
        }
        prot.writeSetEnd();
        break;

      case TType.LIST:
        List<TData> listFields = TData.getListFields();
        prot.writeListBegin(new TList(TData.getListElementType(), listFields.size()));
        for (TData element : listFields) {
          write(prot, element);
        }
        prot.writeListEnd();
        break;

      default:
        break;
    }
  }

  public static TData read(TProtocol prot, TField tField) throws TException {
    return read(prot, tField, Integer.MAX_VALUE);
  }

  private static TData read(TProtocol prot, TField tField, int maxDepth) throws TException {
    if (maxDepth <= 0) {
      throw new TException("Maximum skip depth exceeded");
    }
    switch (tField.type) {
      case TType.BOOL:
        return TData.ofBasic(tField, prot.readBool());

      case TType.BYTE:
        return TData.ofBasic(tField, prot.readByte());

      case TType.I16:
        return TData.ofBasic(tField, prot.readI16());

      case TType.I32:
      case TType.ENUM:
        return TData.ofBasic(tField, prot.readI32());

      case TType.I64:
        return TData.ofBasic(tField, prot.readI64());

      case TType.DOUBLE:
        return TData.ofBasic(tField, prot.readDouble());

      case TType.STRING:
        return TData.ofBasic(tField, prot.readBinary());

      case TType.STRUCT:
        ArrayList<TData> structFields = new ArrayList<TData>();
        prot.readStructBegin();
        while (true) {
          TField field = prot.readFieldBegin();
          if (field.type == TType.STOP) {
            break;
          }
          structFields.add(read(prot, field, maxDepth - 1));
          prot.readFieldEnd();
        }
        prot.readStructEnd();
        return TData.ofStruct(tField, structFields);

      case TType.MAP:
        Map<TData, TData> mapFields = new LinkedHashMap<TData, TData>();
        TData key;
        TData value;
        TMap map = prot.readMapBegin();
        for (int i = 0; i < map.size; i++) {
          key = read(prot, tFieldOfType(map.keyType), maxDepth - 1);
          value = read(prot, tFieldOfType(map.valueType), maxDepth - 1);
          mapFields.put(key, value);
        }
        prot.readMapEnd();
        return TData.ofMap(tField, map.keyType, map.valueType, mapFields);

      case TType.SET:
        Set<TData> setFields = new LinkedHashSet<TData>();
        TSet set = prot.readSetBegin();
        for (int i = 0; i < set.size; i++) {
          setFields.add(read(prot, tFieldOfType(set.elemType), maxDepth - 1));
        }
        prot.readSetEnd();
        return TData.ofSet(tField, set.elemType, setFields);

      case TType.LIST:
        List<TData> listFields = new ArrayList<TData>();
        TList list = prot.readListBegin();
        for (int i = 0; i < list.size; i++) {
          listFields.add(read(prot, tFieldOfType(list.elemType), maxDepth - 1));
        }
        prot.readListEnd();
        return TData.ofList(tField, list.elemType, listFields);

      default:
        return null;
    }
  }

  public static TField tFieldOfType(byte type) {
    return new TField("", type, (short) 0);
  }
}
