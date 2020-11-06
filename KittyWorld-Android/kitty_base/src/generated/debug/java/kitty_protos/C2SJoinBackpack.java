// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: C2S_JoinBackpack.proto

package kitty_protos;

public final class C2SJoinBackpack {
  private C2SJoinBackpack() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }
  public interface C2S_JoinBackpackOrBuilder extends
      // @@protoc_insertion_point(interface_extends:kitty_protos.C2S_JoinBackpack)
      com.google.protobuf.MessageLiteOrBuilder {

    /**
     * <code>int32 position = 1;</code>
     */
    int getPosition();
  }
  /**
   * Protobuf type {@code kitty_protos.C2S_JoinBackpack}
   */
  public  static final class C2S_JoinBackpack extends
      com.google.protobuf.GeneratedMessageLite<
          C2S_JoinBackpack, C2S_JoinBackpack.Builder> implements
      // @@protoc_insertion_point(message_implements:kitty_protos.C2S_JoinBackpack)
      C2S_JoinBackpackOrBuilder {
    private C2S_JoinBackpack() {
    }
    public static final int POSITION_FIELD_NUMBER = 1;
    private int position_;
    /**
     * <code>int32 position = 1;</code>
     */
    @java.lang.Override
    public int getPosition() {
      return position_;
    }
    /**
     * <code>int32 position = 1;</code>
     */
    private void setPosition(int value) {
      
      position_ = value;
    }
    /**
     * <code>int32 position = 1;</code>
     */
    private void clearPosition() {
      
      position_ = 0;
    }

    public static kitty_protos.C2SJoinBackpack.C2S_JoinBackpack parseFrom(
        java.nio.ByteBuffer data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, data);
    }
    public static kitty_protos.C2SJoinBackpack.C2S_JoinBackpack parseFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, data, extensionRegistry);
    }
    public static kitty_protos.C2SJoinBackpack.C2S_JoinBackpack parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, data);
    }
    public static kitty_protos.C2SJoinBackpack.C2S_JoinBackpack parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, data, extensionRegistry);
    }
    public static kitty_protos.C2SJoinBackpack.C2S_JoinBackpack parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, data);
    }
    public static kitty_protos.C2SJoinBackpack.C2S_JoinBackpack parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, data, extensionRegistry);
    }
    public static kitty_protos.C2SJoinBackpack.C2S_JoinBackpack parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, input);
    }
    public static kitty_protos.C2SJoinBackpack.C2S_JoinBackpack parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, input, extensionRegistry);
    }
    public static kitty_protos.C2SJoinBackpack.C2S_JoinBackpack parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return parseDelimitedFrom(DEFAULT_INSTANCE, input);
    }
    public static kitty_protos.C2SJoinBackpack.C2S_JoinBackpack parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }
    public static kitty_protos.C2SJoinBackpack.C2S_JoinBackpack parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, input);
    }
    public static kitty_protos.C2SJoinBackpack.C2S_JoinBackpack parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static Builder newBuilder() {
      return (Builder) DEFAULT_INSTANCE.createBuilder();
    }
    public static Builder newBuilder(kitty_protos.C2SJoinBackpack.C2S_JoinBackpack prototype) {
      return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
    }

    /**
     * Protobuf type {@code kitty_protos.C2S_JoinBackpack}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageLite.Builder<
          kitty_protos.C2SJoinBackpack.C2S_JoinBackpack, Builder> implements
        // @@protoc_insertion_point(builder_implements:kitty_protos.C2S_JoinBackpack)
        kitty_protos.C2SJoinBackpack.C2S_JoinBackpackOrBuilder {
      // Construct using kitty_protos.C2SJoinBackpack.C2S_JoinBackpack.newBuilder()
      private Builder() {
        super(DEFAULT_INSTANCE);
      }


      /**
       * <code>int32 position = 1;</code>
       */
      @java.lang.Override
      public int getPosition() {
        return instance.getPosition();
      }
      /**
       * <code>int32 position = 1;</code>
       */
      public Builder setPosition(int value) {
        copyOnWrite();
        instance.setPosition(value);
        return this;
      }
      /**
       * <code>int32 position = 1;</code>
       */
      public Builder clearPosition() {
        copyOnWrite();
        instance.clearPosition();
        return this;
      }

      // @@protoc_insertion_point(builder_scope:kitty_protos.C2S_JoinBackpack)
    }
    @java.lang.Override
    @java.lang.SuppressWarnings({"unchecked", "fallthrough"})
    protected final java.lang.Object dynamicMethod(
        com.google.protobuf.GeneratedMessageLite.MethodToInvoke method,
        java.lang.Object arg0, java.lang.Object arg1) {
      switch (method) {
        case NEW_MUTABLE_INSTANCE: {
          return new kitty_protos.C2SJoinBackpack.C2S_JoinBackpack();
        }
        case NEW_BUILDER: {
          return new Builder();
        }
        case BUILD_MESSAGE_INFO: {
            java.lang.Object[] objects = new java.lang.Object[] {
              "position_",
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
          com.google.protobuf.Parser<kitty_protos.C2SJoinBackpack.C2S_JoinBackpack> parser = PARSER;
          if (parser == null) {
            synchronized (kitty_protos.C2SJoinBackpack.C2S_JoinBackpack.class) {
              parser = PARSER;
              if (parser == null) {
                parser =
                    new DefaultInstanceBasedParser<kitty_protos.C2SJoinBackpack.C2S_JoinBackpack>(
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


    // @@protoc_insertion_point(class_scope:kitty_protos.C2S_JoinBackpack)
    private static final kitty_protos.C2SJoinBackpack.C2S_JoinBackpack DEFAULT_INSTANCE;
    static {
      C2S_JoinBackpack defaultInstance = new C2S_JoinBackpack();
      // New instances are implicitly immutable so no need to make
      // immutable.
      DEFAULT_INSTANCE = defaultInstance;
      com.google.protobuf.GeneratedMessageLite.registerDefaultInstance(
        C2S_JoinBackpack.class, defaultInstance);
    }

    public static kitty_protos.C2SJoinBackpack.C2S_JoinBackpack getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static volatile com.google.protobuf.Parser<C2S_JoinBackpack> PARSER;

    public static com.google.protobuf.Parser<C2S_JoinBackpack> parser() {
      return DEFAULT_INSTANCE.getParserForType();
    }
  }


  static {
  }

  // @@protoc_insertion_point(outer_class_scope)
}