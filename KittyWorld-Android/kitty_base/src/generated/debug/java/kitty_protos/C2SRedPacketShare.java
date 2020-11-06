// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: C2S_RedPacketShare.proto

package kitty_protos;

public final class C2SRedPacketShare {
  private C2SRedPacketShare() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }
  public interface C2S_RedPacketShareOrBuilder extends
      // @@protoc_insertion_point(interface_extends:kitty_protos.C2S_RedPacketShare)
      com.google.protobuf.MessageLiteOrBuilder {

    /**
     * <code>int32 callTimestamp = 1;</code>
     */
    int getCallTimestamp();
  }
  /**
   * Protobuf type {@code kitty_protos.C2S_RedPacketShare}
   */
  public  static final class C2S_RedPacketShare extends
      com.google.protobuf.GeneratedMessageLite<
          C2S_RedPacketShare, C2S_RedPacketShare.Builder> implements
      // @@protoc_insertion_point(message_implements:kitty_protos.C2S_RedPacketShare)
      C2S_RedPacketShareOrBuilder {
    private C2S_RedPacketShare() {
    }
    public static final int CALLTIMESTAMP_FIELD_NUMBER = 1;
    private int callTimestamp_;
    /**
     * <code>int32 callTimestamp = 1;</code>
     */
    @java.lang.Override
    public int getCallTimestamp() {
      return callTimestamp_;
    }
    /**
     * <code>int32 callTimestamp = 1;</code>
     */
    private void setCallTimestamp(int value) {
      
      callTimestamp_ = value;
    }
    /**
     * <code>int32 callTimestamp = 1;</code>
     */
    private void clearCallTimestamp() {
      
      callTimestamp_ = 0;
    }

    public static kitty_protos.C2SRedPacketShare.C2S_RedPacketShare parseFrom(
        java.nio.ByteBuffer data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, data);
    }
    public static kitty_protos.C2SRedPacketShare.C2S_RedPacketShare parseFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, data, extensionRegistry);
    }
    public static kitty_protos.C2SRedPacketShare.C2S_RedPacketShare parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, data);
    }
    public static kitty_protos.C2SRedPacketShare.C2S_RedPacketShare parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, data, extensionRegistry);
    }
    public static kitty_protos.C2SRedPacketShare.C2S_RedPacketShare parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, data);
    }
    public static kitty_protos.C2SRedPacketShare.C2S_RedPacketShare parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, data, extensionRegistry);
    }
    public static kitty_protos.C2SRedPacketShare.C2S_RedPacketShare parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, input);
    }
    public static kitty_protos.C2SRedPacketShare.C2S_RedPacketShare parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, input, extensionRegistry);
    }
    public static kitty_protos.C2SRedPacketShare.C2S_RedPacketShare parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return parseDelimitedFrom(DEFAULT_INSTANCE, input);
    }
    public static kitty_protos.C2SRedPacketShare.C2S_RedPacketShare parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }
    public static kitty_protos.C2SRedPacketShare.C2S_RedPacketShare parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, input);
    }
    public static kitty_protos.C2SRedPacketShare.C2S_RedPacketShare parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static Builder newBuilder() {
      return (Builder) DEFAULT_INSTANCE.createBuilder();
    }
    public static Builder newBuilder(kitty_protos.C2SRedPacketShare.C2S_RedPacketShare prototype) {
      return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
    }

    /**
     * Protobuf type {@code kitty_protos.C2S_RedPacketShare}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageLite.Builder<
          kitty_protos.C2SRedPacketShare.C2S_RedPacketShare, Builder> implements
        // @@protoc_insertion_point(builder_implements:kitty_protos.C2S_RedPacketShare)
        kitty_protos.C2SRedPacketShare.C2S_RedPacketShareOrBuilder {
      // Construct using kitty_protos.C2SRedPacketShare.C2S_RedPacketShare.newBuilder()
      private Builder() {
        super(DEFAULT_INSTANCE);
      }


      /**
       * <code>int32 callTimestamp = 1;</code>
       */
      @java.lang.Override
      public int getCallTimestamp() {
        return instance.getCallTimestamp();
      }
      /**
       * <code>int32 callTimestamp = 1;</code>
       */
      public Builder setCallTimestamp(int value) {
        copyOnWrite();
        instance.setCallTimestamp(value);
        return this;
      }
      /**
       * <code>int32 callTimestamp = 1;</code>
       */
      public Builder clearCallTimestamp() {
        copyOnWrite();
        instance.clearCallTimestamp();
        return this;
      }

      // @@protoc_insertion_point(builder_scope:kitty_protos.C2S_RedPacketShare)
    }
    @java.lang.Override
    @java.lang.SuppressWarnings({"unchecked", "fallthrough"})
    protected final java.lang.Object dynamicMethod(
        com.google.protobuf.GeneratedMessageLite.MethodToInvoke method,
        java.lang.Object arg0, java.lang.Object arg1) {
      switch (method) {
        case NEW_MUTABLE_INSTANCE: {
          return new kitty_protos.C2SRedPacketShare.C2S_RedPacketShare();
        }
        case NEW_BUILDER: {
          return new Builder();
        }
        case BUILD_MESSAGE_INFO: {
            java.lang.Object[] objects = new java.lang.Object[] {
              "callTimestamp_",
            };
            java.lang.String info =
                "\u0000\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0000\u0000\u0001\u0004";
            return newMessageInfo(DEFAULT_INSTANCE, info, objects);
        }
        // fall through
        case GET_DEFAULT_INSTANCE: {
          return DEFAULT_INSTANCE;
        }
        case GET_PARSER: {
          com.google.protobuf.Parser<kitty_protos.C2SRedPacketShare.C2S_RedPacketShare> parser = PARSER;
          if (parser == null) {
            synchronized (kitty_protos.C2SRedPacketShare.C2S_RedPacketShare.class) {
              parser = PARSER;
              if (parser == null) {
                parser =
                    new DefaultInstanceBasedParser<kitty_protos.C2SRedPacketShare.C2S_RedPacketShare>(
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


    // @@protoc_insertion_point(class_scope:kitty_protos.C2S_RedPacketShare)
    private static final kitty_protos.C2SRedPacketShare.C2S_RedPacketShare DEFAULT_INSTANCE;
    static {
      C2S_RedPacketShare defaultInstance = new C2S_RedPacketShare();
      // New instances are implicitly immutable so no need to make
      // immutable.
      DEFAULT_INSTANCE = defaultInstance;
      com.google.protobuf.GeneratedMessageLite.registerDefaultInstance(
        C2S_RedPacketShare.class, defaultInstance);
    }

    public static kitty_protos.C2SRedPacketShare.C2S_RedPacketShare getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static volatile com.google.protobuf.Parser<C2S_RedPacketShare> PARSER;

    public static com.google.protobuf.Parser<C2S_RedPacketShare> parser() {
      return DEFAULT_INSTANCE.getParserForType();
    }
  }


  static {
  }

  // @@protoc_insertion_point(outer_class_scope)
}
