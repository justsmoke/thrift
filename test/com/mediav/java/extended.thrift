namespace java com.mediav.thrift.extended

enum Alphabet {
  A,
  B,
  C,
  D,
}

struct Extension {
  1: optional i16 ext1,
  2: optional i32 ext2,
  3: optional i64 ext3,
  4: optional bool ext4,
  5: optional byte ext5,
  6: optional double ext6,
  7: optional string ext7,
  8: optional binary ext8,
  9: optional Alphabet ext9,
}

struct Extended
{
  1: required Alphabet alphabet,
  2: optional i16 ext1,
  3: optional i32 ext2,
  4: optional i64 ext3,
  5: optional bool ext4,
  6: optional byte ext5,
  7: optional double ext6,
  8: optional string ext7,
  9: optional binary ext8,
  10: optional Alphabet ex9,
  11: optional Extension ext10,
  12: optional list<Extension> ext11,
  13: optional map<Extension, Extension> ext12,
  14: optional set<Extension> ext13,
  15: optional list<Alphabet> ext14,
  16: optional map<Alphabet, Alphabet> ext15,
  17: optional set<Alphabet> ext16,
}
