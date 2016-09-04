namespace java com.mediav.thrift.basic

enum Alphabet {
  A,
  B,
}

struct Basic
{
  1: required Alphabet alphabet;
}

