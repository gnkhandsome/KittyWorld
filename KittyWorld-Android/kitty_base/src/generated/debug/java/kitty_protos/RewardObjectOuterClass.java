// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: RewardObject.proto

package kitty_protos;

public final class RewardObjectOuterClass {
  private RewardObjectOuterClass() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }
  public interface RewardObjectOrBuilder extends
      // @@protoc_insertion_point(interface_extends:kitty_protos.RewardObject)
      com.google.protobuf.MessageLiteOrBuilder {

    /**
     * <code>string id = 1;</code>
     */
    java.lang.String getId();
    /**
     * <code>string id = 1;</code>
     */
    com.google.protobuf.ByteString
        getIdBytes();

    /**
     * <code>int64 amount = 2;</code>
     */
    long getAmount();

    /**
     * <code>int32 type = 3;</code>
     */
    int getType();

    /**
     * <code>string extra = 4;</code>
     */
    java.lang.String getExtra();
    /**
     * <code>string extra = 4;</code>
     */
    com.google.protobuf.ByteString
        getExtraBytes();
  }
  /**
   * Protobuf type {@code kitty_protos.RewardObject}
   */
  public  static final class RewardObject extends
      com.google.protobuf.GeneratedMessageLite<
          RewardObject, RewardObject.Builder> implements
      // @@protoc_insertion_point(message_implements:kitty_protos.RewardObject)
      RewardObjectOrBuilder {
    private RewardObject() {
      id_ = "";
      extra_ = "";
    }
    public static final int ID_FIELD_NUMBER = 1;
    private java.lang.String id_;
    /**
     * <code>string id = 1;</code>
     */
    @java.lang.Override
    public java.lang.String getId() {
      return id_;
    }
    /**
     * <code>string id = 1;</code>
     */
    @java.lang.Override
    public com.google.protobuf.ByteString
        getIdBytes() {
      return com.google.protobuf.ByteString.copyFromUtf8(id_);
    }
    /**
     * <code>string id = 1;</code>
     */
    private void setId(
        java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      id_ = value;
    }
    /**
     * <code>string id = 1;</code>
     */
    private void clearId() {
      
      id_ = getDefaultInstance().getId();
    }
    /**
     * <code>string id = 1;</code>
     */
    private void setIdBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
      
      id_ = value.toStringUtf8();
    }

    public static final int AMOUNT_FIELD_NUMBER = 2;
    private long amount_;
    /**
     * <code>int64 amount = 2;</code>
     */
    @java.lang.Override
    public long getAmount() {
      return amount_;
    }
    /**
     * <code>int64 amount = 2;</code>
     */
    private void setAmount(long value) {
      
      amount_ = value;
    }
    /**
     * <code>int64 amount = 2;</code>
     */
    private void clearAmount() {
      
      amount_ = 0L;
    }

    public static final int TYPE_FIELD_NUMBER = 3;
    private int type_;
    /**
     * <code>int32 type = 3;</code>
     */
    @java.lang.Override
    public int getType() {
      return type_;
    }
    /**
     * <code>int32 type = 3;</code>
     */
    private void setType(int value) {
      
      type_ = value;
    }
    /**
     * <code>int32 type = 3;</code>
     */
    private void clearType() {
      
      type_ = 0;
    }

    public static final int EXTRA_FIELD_NUMBER = 4;
    private java.lang.String extra_;
    /**
     * <code>string extra = 4;</code>
     */
    @java.lang.Override
    public java.lang.String getExtra() {
      return extra_;
    }
    /**
     * <code>string extra = 4;</code>
     */
    @java.lang.Override
    public com.google.protobuf.ByteString
        getExtraBytes() {
      return com.google.protobuf.ByteString.copyFromUtf8(extra_);
    }
    /**
     * <code>string extra = 4;</code>
     */
    private void setExtra(
        java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      extra_ = value;
    }
    /**
     * <code>string extra = 4;</code>
     */
    private void clearExtra() {
      
      extra_ = getDefaultInstance().getExtra();
    }
    /**
     * <code>string extra = 4;</code>
     */
    private void setExtraBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
      
      extra_ = value.toStringUtf8();
    }

    public static kitty_protos.RewardObjectOuterClass.RewardObject parseFrom(
        java.nio.ByteBuffer data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, data);
    }
    public static kitty_protos.RewardObjectOuterClass.RewardObject parseFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, data, extensionRegistry);
    }
    public static kitty_protos.RewardObjectOuterClass.RewardObject parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, data);
    }
    public static kitty_protos.RewardObjectOuterClass.RewardObject parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, data, extensionRegistry);
    }
    public static kitty_protos.RewardObjectOuterClass.RewardObject parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, data);
    }
    public static kitty_protos.RewardObjectOuterClass.RewardObject parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, data, extensionRegistry);
    }
    public static kitty_protos.RewardObjectOuterClass.RewardObject parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, input);
    }
    public static kitty_protos.RewardObjectOuterClass.RewardObject parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, input, extensionRegistry);
    }
    public static kitty_protos.RewardObjectOuterClass.RewardObject parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return parseDelimitedFrom(DEFAULT_INSTANCE, input);
    }
    public static kitty_protos.RewardObjectOuterClass.RewardObject parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }
    public static kitty_protos.RewardObjectOuterClass.RewardObject parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, input);
    }
    public static kitty_protos.RewardObjectOuterClass.RewardObject parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static Builder newBuilder() {
      return (Builder) DEFAULT_INSTANCE.createBuilder();
    }
    public static Builder newBuilder(kitty_protos.RewardObjectOuterClass.RewardObject prototype) {
      return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
    }

    /**
     * Protobuf type {@code kitty_protos.RewardObject}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageLite.Builder<
          kitty_protos.RewardObjectOuterClass.RewardObject, Builder> implements
        // @@protoc_insertion_point(builder_implements:kitty_protos.RewardObject)
        kitty_protos.RewardObjectOuterClass.RewardObjectOrBuilder {
      // Construct using kitty_protos.RewardObjectOuterClass.RewardObject.newBuilder()
      private Builder() {
        super(DEFAULT_INSTANCE);
      }


      /**
       * <code>string id = 1;</code>
       */
      @java.lang.Override
      public java.lang.String getId() {
        return instance.getId();
      }
      /**
       * <code>string id = 1;</code>
       */
      @java.lang.Override
      public com.google.protobuf.ByteString
          getIdBytes() {
        return instance.getIdBytes();
      }
      /**
       * <code>string id = 1;</code>
       */
      public Builder setId(
          java.lang.String value) {
        copyOnWrite();
        instance.setId(value);
        return this;
      }
      /**
       * <code>string id = 1;</code>
       */
      public Builder clearId() {
        copyOnWrite();
        instance.clearId();
        return this;
      }
      /**
       * <code>string id = 1;</code>
       */
      public Builder setIdBytes(
          com.google.protobuf.ByteString value) {
        copyOnWrite();
        instance.setIdBytes(value);
        return this;
      }

      /**
       * <code>int64 amount = 2;</code>
       */
      @java.lang.Override
      public long getAmount() {
        return instance.getAmount();
      }
      /**
       * <code>int64 amount = 2;</code>
       */
      public Builder setAmount(long value) {
        copyOnWrite();
        instance.setAmount(value);
        return this;
      }
      /**
       * <code>int64 amount = 2;</code>
       */
      public Builder clearAmount() {
        copyOnWrite();
        instance.clearAmount();
        return this;
      }

      /**
       * <code>int32 type = 3;</code>
       */
      @java.lang.Override
      public int getType() {
        return instance.getType();
      }
      /**
       * <code>int32 type = 3;</code>
       */
      public Builder setType(int value) {
        copyOnWrite();
        instance.setType(value);
        return this;
      }
      /**
       * <code>int32 type = 3;</code>
       */
      public Builder clearType() {
        copyOnWrite();
        instance.clearType();
        return this;
      }

      /**
       * <code>string extra = 4;</code>
       */
      @java.lang.Override
      public java.lang.String getExtra() {
        return instance.getExtra();
      }
      /**
       * <code>string extra = 4;</code>
       */
      @java.lang.Override
      public com.google.protobuf.ByteString
          getExtraBytes() {
        return instance.getExtraBytes();
      }
      /**
       * <code>string extra = 4;</code>
       */
      public Builder setExtra(
          java.lang.String value) {
        copyOnWrite();
        instance.setExtra(value);
        return this;
      }
      /**
       * <code>string extra = 4;</code>
       */
      public Builder clearExtra() {
        copyOnWrite();
        instance.clearExtra();
        return this;
      }
      /**
       * <code>string extra = 4;</code>
       */
      public Builder setExtraBytes(
          com.google.protobuf.ByteString value) {
        copyOnWrite();
        instance.setExtraBytes(value);
        return this;
      }

      // @@protoc_insertion_point(builder_scope:kitty_protos.RewardObject)
    }
    @java.lang.Override
    @java.lang.SuppressWarnings({"unchecked", "fallthrough"})
    protected final java.lang.Object dynamicMethod(
        com.google.protobuf.GeneratedMessageLite.MethodToInvoke method,
        java.lang.Object arg0, java.lang.Object arg1) {
      switch (method) {
        case NEW_MUTABLE_INSTANCE: {
          return new kitty_protos.RewardObjectOuterClass.RewardObject();
        }
        case NEW_BUILDER: {
          return new Builder();
        }
        case BUILD_MESSAGE_INFO: {
            java.lang.Object[] objects = new java.lang.Object[] {
              "id_",
              "amount_",
              "type_",
              "extra_",
            };
            java.lang.String info =
                "\u0000\u0004\u0000\u0000\u0001\u0004\u0004\u0000\u0000\u0000\u0001\u0208\u0002\u0002" +
                "\u0003\u0004\u0004\u0208";
            return newMessageInfo(DEFAULT_INSTANCE, info, objects);
        }
        // fall through
        case GET_DEFAULT_INSTANCE: {
          return DEFAULT_INSTANCE;
        }
        case GET_PARSER: {
          com.google.protobuf.Parser<kitty_protos.RewardObjectOuterClass.RewardObject> parser = PARSER;
          if (parser == null) {
            synchronized (kitty_protos.RewardObjectOuterClass.RewardObject.class) {
              parser = PARSER;
              if (parser == null) {
                parser =
                    new DefaultInstanceBasedParser<kitty_protos.RewardObjectOuterClass.RewardObject>(
                        DEFAULT_INSTANCE);
                PARSER = parser;
              }
            }
          }
          return parser;
      }
      case GET_MEMOIZED_IS_INITIALIZED: {
        return (byte) 1;
      }
      case SET_MEMOIZED_IS_INITIALIZED: {
        return null;
      }
      }
      throw new UnsupportedOperationException();
    }


    // @@protoc_insertion_point(class_scope:kitty_protos.RewardObject)
    private static final kitty_protos.RewardObjectOuterClass.RewardObject DEFAULT_INSTANCE;
    static {
      RewardObject defaultInstance = new RewardObject();
      // New instances are implicitly immutable so no need to make
      // immutable.
      DEFAULT_INSTANCE = defaultInstance;
      com.google.protobuf.GeneratedMessageLite.registerDefaultInstance(
        RewardObject.class, defaultInstance);
    }

    public static kitty_protos.RewardObjectOuterClass.RewardObject getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static volatile com.google.protobuf.Parser<RewardObject> PARSER;

    public static com.google.protobuf.Parser<RewardObject> parser() {
      return DEFAULT_INSTANCE.getParserForType();
    }
  }


  static {
  }

  // @@protoc_insertion_point(outer_class_scope)
}
