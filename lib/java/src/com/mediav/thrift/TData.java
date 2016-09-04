package com.mediav.thrift;

import org.apache.thrift.protocol.TField;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author zhangyf
 * @version 1.0 2016-09-01
 */

public class TData {

  private static final byte ANONYMOUS_TYPE = -1;

  private TField tField;
  private Object value = null;
  private byte listElementType;
  private List<TData> listFields = null;
  private byte setElementType;
  private Set<TData> setFields = null;
  private byte mapKeyType;
  private byte mapValueType;
  private Map<TData, TData> mapFields = null;
  private ArrayList<TData> structFields = null;

  public TData(TField tField, Object value,
               byte listElementType, List<TData> listFields,
               byte setElementType, Set<TData> setFields,
               byte mapKeyType, byte mapValueType, Map<TData, TData> mapFields,
               ArrayList<TData> structFields) {
    this.tField = tField;
    this.value = value;
    this.listElementType = listElementType;
    this.listFields = listFields;
    this.setElementType = setElementType;
    this.setFields = setFields;
    this.mapKeyType = mapKeyType;
    this.mapValueType = mapValueType;
    this.mapFields = mapFields;
    this.structFields = structFields;
  }

  public TField gettField() {
    return tField;
  }

  public Object getValue() {
    return value;
  }

  public byte getListElementType() {
    return listElementType;
  }

  public List<TData> getListFields() {
    return listFields;
  }

  public byte getSetElementType() {
    return setElementType;
  }

  public Set<TData> getSetFields() {
    return setFields;
  }

  public byte getMapKeyType() {
    return mapKeyType;
  }

  public byte getMapValueType() {
    return mapValueType;
  }

  public Map<TData, TData> getMapFields() {
    return mapFields;
  }

  public ArrayList<TData> getStructFields() {
    return structFields;
  }

  public static TData ofBasic(TField tField, Object value) {
    return new TData(tField, value, ANONYMOUS_TYPE, null, ANONYMOUS_TYPE, null, ANONYMOUS_TYPE, ANONYMOUS_TYPE, null, null);
  }

  public static TData ofList(TField tField, byte listElementType, List<TData> value) {
    return new TData(tField, null, listElementType, value, ANONYMOUS_TYPE, null, ANONYMOUS_TYPE, ANONYMOUS_TYPE, null, null);
  }

  public static TData ofSet(TField tField, byte setElementType, Set<TData> value) {
    return new TData(tField, null, ANONYMOUS_TYPE, null, setElementType, value, ANONYMOUS_TYPE, ANONYMOUS_TYPE, null, null);
  }

  public static TData ofMap(TField tField, byte mapKeyType, byte mapValueType, Map<TData, TData> value) {
    return new TData(tField, null, ANONYMOUS_TYPE, null, ANONYMOUS_TYPE, null, mapKeyType, mapValueType, value, null);
  }

  public static TData ofStruct(TField tField, ArrayList<TData> value) {
    return new TData(tField, null, ANONYMOUS_TYPE, null, ANONYMOUS_TYPE, null, ANONYMOUS_TYPE, ANONYMOUS_TYPE, null, value);
  }

}
