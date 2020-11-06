// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: S2S_SendKitty.proto

package kitty_protos;

public final class S2SSendKitty {
  private S2SSendKitty() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }
  public interface S2S_SendKittyOrBuilder extends
      // @@protoc_insertion_point(interface_extends:kitty_protos.S2S_SendKitty)
      com.google.protobuf.MessageLiteOrBuilder {

    /**
     * <code>string uuid = 1;</code>
     */
    java.lang.String getUuid();
    /**
     * <code>string uuid = 1;</code>
     */
    com.google.protobuf.ByteString
        getUuidBytes();

    /**
     * <code>int32 kittyLevel = 2;</code>
     */
    int getKittyLevel();
  }
  /**
   * Protobuf type {@code kitty_protos.S2S_SendKitty}
   */
  public  static final class S2S_SendKitty extends
      com.google.protobuf.GeneratedMessageLite<
          S2S_SendKitty, S2S_SendKitty.Builder> implements
      // @@protoc_insertion_point(message_implements:kitty_protos.S2S_SendKitty)
      S2S_SendKittyOrBuilder {
    private S2S_SendKitty() {
      uuid_ = "";
    }
    public static final int UUID_FIELD_NUMBER = 1;
    private java.lang.String uuid_;
    /**
     * <code>string uuid = 1;</code>
     */
    @java.lang.Override
    public java.lang.String getUuid() {
      return uuid_;
    }
    /**
     * <code>string uuid = 1;</code>
     */
    @java.lang.Override
    public com.google.protobuf.ByteString
        getUuidBytes() {
      return com.google.protobuf.ByteString.copyFromUtf8(uuid_);
    }
    /**
     * <code>string uuid = 1;</code>
     */
    private void setUuid(
        java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      uuid_ = value;
    }
    /**
     * <code>string uuid = 1;</code>
     */
    private void clearUuid() {
      
      uuid_ = getDefaultInstance().getUuid();
    }
    /**
     * <code>string uuid = 1;</code>
     */
    private void setUuidBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
      
      uuid_ = value.toStringUtf8();
    }

    public static final int KITTYLEVEL_FIELD_NUMBER = 2;
    private int kittyLevel_;
    /**
     * <code>int32 kittyLevel = 2;</code>
     */
    @java.lang.Override
    public int getKittyLevel() {
      return kittyLevel_;
    }
    /**
     * <code>int32 kittyLevel = 2;</code>
     */
    private void setKittyLevel(int value) {
      
      kittyLevel_ = value;
    }
    /**
     * <code>int32 kittyLevel = 2;</code>
     */
    private void clearKittyLevel() {
      
      kittyLevel_ = 0;
    }

    public static kitty_protos.S2SSendKitty.S2S_SendKitty parseFrom(
        java.nio.ByteBuffer data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, data);
    }
    public static kitty_protos.S2SSendKitty.S2S_SendKitty parseFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, data, extensionRegistry);
    }
    public static kitty_protos.S2SSendKitty.S2S_SendKitty parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, data);
    }
    public static kitty_protos.S2SSendKitty.S2S_SendKitty parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, data, extensionRegistry);
    }
    public static kitty_protos.S2SSendKitty.S2S_SendKitty parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, data);
    }
    public static kitty_protos.S2SSendKitty.S2S_SendKitty parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, data, extensionRegistry);
    }
    public static kitty_protos.S2SSendKitty.S2S_SendKitty parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, input);
    }
    public static kitty_protos.S2SSendKitty.S2S_SendKitty parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, input, extensionRegistry);
    }
    public static kitty_protos.S2SSendKitty.S2S_SendKitty parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return parseDelimitedFrom(DEFAULT_INSTANCE, input);
    }
    public static kitty_protos.S2SSendKitty.S2S_SendKitty parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }
    public static kitty_protos.S2SSendKitty.S2S_SendKitty parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, input);
    }
    public static kitty_protos.S2SSendKitty.S2S_SendKitty parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static Builder newBuilder() {
      return (Builder) DEFAULT_INSTANCE.createBuilder();
    }
    public static Builder newBuilder(kitty_protos.S2SSendKitty.S2S_SendKitty prototype) {
      return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
    }

    /**
     * Protobuf type {@code kitty_protos.S2S_SendKitty}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageLite.Builder<
          kitty_protos.S2SSendKitty.S2S_SendKitty, Builder> implements
        // @@protoc_insertion_point(builder_implements:kitty_protos.S2S_SendKitty)
        kitty_protos.S2SSendKitty.S2S_SendKittyOrBuilder {
      // Construct using kitty_protos.S2SSendKitty.S2S_SendKitty.newBuilder()
      private Builder() {
        super(DEFAULT_INSTANCE);
      }


      /**
       * <code>string uuid = 1;</code>
       */
      @java.lang.Override
      public java.lang.String getUuid() {
        return instance.getUuid();
      }
      /**
       * <code>string uuid = 1;</code>
       */
      @java.lang.Override
      public com.google.protobuf.ByteString
          getUuidBytes() {
        return instance.getUuidBytes();
      }
      /**
       * <code>string uuid = 1;</code>
       */
      public Builder setUuid(
          java.lang.String value) {
        copyOnWrite();
        instance.setUuid(value);
        return this;
      }
      /**
       * <code>string uuid = 1;</code>
       */
      public Builder clearUuid() {
        copyOnWrite();
        instance.clearUuid();
        return this;
      }
      /**
       * <code>string uuid = 1;</code>
       */
      public Builder setUuidBytes(
          com.google.protobuf.ByteString value) {
        copyOnWrite();
        instance.setUuidBytes(value);
        return this;
      }

      /**
       * <code>int32 kittyLevel = 2;</code>
       */
      @java.lang.Override
      public int getKittyLevel() {
        return instance.getKittyLevel();
      }
      /**
       * <code>int32 kittyLevel = 2;</code>
       */
      public Builder setKittyLevel(int value) {
        copyOnWrite();
        instance.setKittyLevel(value);
        return this;
      }
      /**
       * <code>int32 kittyLevel = 2;</code>
       */
      public Builder clearKittyLevel() {
        copyOnWrite();
        instance.clearKittyLevel();
        return this;
      }

      // @@protoc_insertion_point(builder_scope:kitty_protos.S2S_SendKitty)
    }
    @java.lang.Override
    @java.lang.SuppressWarnings({"unchecked", "fallthrough"})
    protected final java.lang.Object dynamicMethod(
        com.google.protobuf.GeneratedMessageLite.MethodToInvoke method,
        java.lang.Object arg0, java.lang.Object arg1) {
      switch (method) {
        case NEW_MUTABLE_INSTANCE: {
          return new kitty_protos.S2SSendKitty.S2S_SendKitty();
        }
        case NEW_BUILDER: {
          return new Builder();
        }
        case BUILD_MESSAGE_INFO: {
            java.lang.Object[] objects = new java.lang.Object[] {
              "uuid_",
              "kittyLevel_",
            };
            java.lang.String info =
                "\u0000\u0002\u0000\u0000\u0001\u0002\u0002\u0000\u0000\u0000\u0001\u0208\u0002\u0004" +
                "";
            return newMessageInfo(DEFAULT_INSTANCE, info, objects);
        }
        // fall through
        case GET_DEFAULT_INSTANCE: {
          return DEFAULT_INSTANCE;
        }
        case GET_PARSER: {
          com.google.protobuf.Parser<kitty_protos.S2SSendKitty.S2S_SendKitty> parser = PARSER;
          if (parser == null) {
            synchronized (kitty_protos.S2SSendKitty.S2S_SendKitty.class) {
              parser = PARSER;
              if (parser == null) {
                parser =
                    new DefaultInstanceBasedParser<kitty_protos.S2SSendKitty.S2S_SendKitty>(
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


    // @@protoc_insertion_point(class_scope:kitty_protos.S2S_SendKitty)
    private static final kitty_protos.S2SSendKitty.S2S_SendKitty DEFAULT_INSTANCE;
    static {
      S2S_SendKitty defaultInstance = new S2S_SendKitty();
      // New instances are implicitly immutable so no need to make
      // immutable.
      DEFAULT_INSTANCE = defaultInstance;
      com.google.protobuf.GeneratedMessageLite.registerDefaultInstance(
        S2S_SendKitty.class, defaultInstance);
    }

    public static kitty_protos.S2SSendKitty.S2S_SendKitty getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static volatile com.google.protobuf.Parser<S2S_SendKitty> PARSER;

    public static com.google.protobuf.Parser<S2S_SendKitty> parser() {
      return DEFAULT_INSTANCE.getParserForType();
    }
  }


  static {
  }

  // @@protoc_insertion_point(outer_class_scope)
}