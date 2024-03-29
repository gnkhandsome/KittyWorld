// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: S2C_CapacityBackpack.proto

package kitty_protos;

public final class S2CCapacityBackpack {
  private S2CCapacityBackpack() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }
  public interface S2C_CapacityBackpackOrBuilder extends
      // @@protoc_insertion_point(interface_extends:kitty_protos.S2C_CapacityBackpack)
      com.google.protobuf.MessageLiteOrBuilder {

    /**
     * <code>int32 code = 1;</code>
     */
    int getCode();

    /**
     * <code>int32 capacity = 2;</code>
     */
    int getCapacity();
  }
  /**
   * Protobuf type {@code kitty_protos.S2C_CapacityBackpack}
   */
  public  static final class S2C_CapacityBackpack extends
      com.google.protobuf.GeneratedMessageLite<
          S2C_CapacityBackpack, S2C_CapacityBackpack.Builder> implements
      // @@protoc_insertion_point(message_implements:kitty_protos.S2C_CapacityBackpack)
      S2C_CapacityBackpackOrBuilder {
    private S2C_CapacityBackpack() {
    }
    /**
     * Protobuf enum {@code kitty_protos.S2C_CapacityBackpack.StatusCode}
     */
    public enum StatusCode
        implements com.google.protobuf.Internal.EnumLite {
      /**
       * <code>SUCCESS = 0;</code>
       */
      SUCCESS(0),
      /**
       * <code>MAX_CAPACITY = 1;</code>
       */
      MAX_CAPACITY(1),
      /**
       * <code>LACK_COIN = 2;</code>
       */
      LACK_COIN(2),
      /**
       * <code>UNKNOWN_FAILED = 3;</code>
       */
      UNKNOWN_FAILED(3),
      UNRECOGNIZED(-1),
      ;

      /**
       * <code>SUCCESS = 0;</code>
       */
      public static final int SUCCESS_VALUE = 0;
      /**
       * <code>MAX_CAPACITY = 1;</code>
       */
      public static final int MAX_CAPACITY_VALUE = 1;
      /**
       * <code>LACK_COIN = 2;</code>
       */
      public static final int LACK_COIN_VALUE = 2;
      /**
       * <code>UNKNOWN_FAILED = 3;</code>
       */
      public static final int UNKNOWN_FAILED_VALUE = 3;


      @java.lang.Override
      public final int getNumber() {
        if (this == UNRECOGNIZED) {
          throw new java.lang.IllegalArgumentException(
              "Can't get the number of an unknown enum value.");
        }
        return value;
      }

      /**
       * @deprecated Use {@link #forNumber(int)} instead.
       */
      @java.lang.Deprecated
      public static StatusCode valueOf(int value) {
        return forNumber(value);
      }

      public static StatusCode forNumber(int value) {
        switch (value) {
          case 0: return SUCCESS;
          case 1: return MAX_CAPACITY;
          case 2: return LACK_COIN;
          case 3: return UNKNOWN_FAILED;
          default: return null;
        }
      }

      public static com.google.protobuf.Internal.EnumLiteMap<StatusCode>
          internalGetValueMap() {
        return internalValueMap;
      }
      private static final com.google.protobuf.Internal.EnumLiteMap<
          StatusCode> internalValueMap =
            new com.google.protobuf.Internal.EnumLiteMap<StatusCode>() {
              @java.lang.Override
              public StatusCode findValueByNumber(int number) {
                return StatusCode.forNumber(number);
              }
            };

      public static com.google.protobuf.Internal.EnumVerifier 
          internalGetVerifier() {
        return StatusCodeVerifier.INSTANCE;
      }

      private static final class StatusCodeVerifier implements 
           com.google.protobuf.Internal.EnumVerifier { 
              static final com.google.protobuf.Internal.EnumVerifier           INSTANCE = new StatusCodeVerifier();
              @java.lang.Override
              public boolean isInRange(int number) {
                return StatusCode.forNumber(number) != null;
              }
            };

      private final int value;

      private StatusCode(int value) {
        this.value = value;
      }

      // @@protoc_insertion_point(enum_scope:kitty_protos.S2C_CapacityBackpack.StatusCode)
    }

    public static final int CODE_FIELD_NUMBER = 1;
    private int code_;
    /**
     * <code>int32 code = 1;</code>
     */
    @java.lang.Override
    public int getCode() {
      return code_;
    }
    /**
     * <code>int32 code = 1;</code>
     */
    private void setCode(int value) {
      
      code_ = value;
    }
    /**
     * <code>int32 code = 1;</code>
     */
    private void clearCode() {
      
      code_ = 0;
    }

    public static final int CAPACITY_FIELD_NUMBER = 2;
    private int capacity_;
    /**
     * <code>int32 capacity = 2;</code>
     */
    @java.lang.Override
    public int getCapacity() {
      return capacity_;
    }
    /**
     * <code>int32 capacity = 2;</code>
     */
    private void setCapacity(int value) {
      
      capacity_ = value;
    }
    /**
     * <code>int32 capacity = 2;</code>
     */
    private void clearCapacity() {
      
      capacity_ = 0;
    }

    public static kitty_protos.S2CCapacityBackpack.S2C_CapacityBackpack parseFrom(
        java.nio.ByteBuffer data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, data);
    }
    public static kitty_protos.S2CCapacityBackpack.S2C_CapacityBackpack parseFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, data, extensionRegistry);
    }
    public static kitty_protos.S2CCapacityBackpack.S2C_CapacityBackpack parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, data);
    }
    public static kitty_protos.S2CCapacityBackpack.S2C_CapacityBackpack parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, data, extensionRegistry);
    }
    public static kitty_protos.S2CCapacityBackpack.S2C_CapacityBackpack parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, data);
    }
    public static kitty_protos.S2CCapacityBackpack.S2C_CapacityBackpack parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, data, extensionRegistry);
    }
    public static kitty_protos.S2CCapacityBackpack.S2C_CapacityBackpack parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, input);
    }
    public static kitty_protos.S2CCapacityBackpack.S2C_CapacityBackpack parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, input, extensionRegistry);
    }
    public static kitty_protos.S2CCapacityBackpack.S2C_CapacityBackpack parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return parseDelimitedFrom(DEFAULT_INSTANCE, input);
    }
    public static kitty_protos.S2CCapacityBackpack.S2C_CapacityBackpack parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }
    public static kitty_protos.S2CCapacityBackpack.S2C_CapacityBackpack parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, input);
    }
    public static kitty_protos.S2CCapacityBackpack.S2C_CapacityBackpack parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static Builder newBuilder() {
      return (Builder) DEFAULT_INSTANCE.createBuilder();
    }
    public static Builder newBuilder(kitty_protos.S2CCapacityBackpack.S2C_CapacityBackpack prototype) {
      return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
    }

    /**
     * Protobuf type {@code kitty_protos.S2C_CapacityBackpack}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageLite.Builder<
          kitty_protos.S2CCapacityBackpack.S2C_CapacityBackpack, Builder> implements
        // @@protoc_insertion_point(builder_implements:kitty_protos.S2C_CapacityBackpack)
        kitty_protos.S2CCapacityBackpack.S2C_CapacityBackpackOrBuilder {
      // Construct using kitty_protos.S2CCapacityBackpack.S2C_CapacityBackpack.newBuilder()
      private Builder() {
        super(DEFAULT_INSTANCE);
      }


      /**
       * <code>int32 code = 1;</code>
       */
      @java.lang.Override
      public int getCode() {
        return instance.getCode();
      }
      /**
       * <code>int32 code = 1;</code>
       */
      public Builder setCode(int value) {
        copyOnWrite();
        instance.setCode(value);
        return this;
      }
      /**
       * <code>int32 code = 1;</code>
       */
      public Builder clearCode() {
        copyOnWrite();
        instance.clearCode();
        return this;
      }

      /**
       * <code>int32 capacity = 2;</code>
       */
      @java.lang.Override
      public int getCapacity() {
        return instance.getCapacity();
      }
      /**
       * <code>int32 capacity = 2;</code>
       */
      public Builder setCapacity(int value) {
        copyOnWrite();
        instance.setCapacity(value);
        return this;
      }
      /**
       * <code>int32 capacity = 2;</code>
       */
      public Builder clearCapacity() {
        copyOnWrite();
        instance.clearCapacity();
        return this;
      }

      // @@protoc_insertion_point(builder_scope:kitty_protos.S2C_CapacityBackpack)
    }
    @java.lang.Override
    @java.lang.SuppressWarnings({"unchecked", "fallthrough"})
    protected final java.lang.Object dynamicMethod(
        com.google.protobuf.GeneratedMessageLite.MethodToInvoke method,
        java.lang.Object arg0, java.lang.Object arg1) {
      switch (method) {
        case NEW_MUTABLE_INSTANCE: {
          return new kitty_protos.S2CCapacityBackpack.S2C_CapacityBackpack();
        }
        case NEW_BUILDER: {
          return new Builder();
        }
        case BUILD_MESSAGE_INFO: {
            java.lang.Object[] objects = new java.lang.Object[] {
              "code_",
              "capacity_",
            };
            java.lang.String info =
                "\u0000\u0002\u0000\u0000\u0001\u0002\u0002\u0000\u0000\u0000\u0001\u0004\u0002\u0004" +
                "";
            return newMessageInfo(DEFAULT_INSTANCE, info, objects);
        }
        // fall through
        case GET_DEFAULT_INSTANCE: {
          return DEFAULT_INSTANCE;
        }
        case GET_PARSER: {
          com.google.protobuf.Parser<kitty_protos.S2CCapacityBackpack.S2C_CapacityBackpack> parser = PARSER;
          if (parser == null) {
            synchronized (kitty_protos.S2CCapacityBackpack.S2C_CapacityBackpack.class) {
              parser = PARSER;
              if (parser == null) {
                parser =
                    new DefaultInstanceBasedParser<kitty_protos.S2CCapacityBackpack.S2C_CapacityBackpack>(
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


    // @@protoc_insertion_point(class_scope:kitty_protos.S2C_CapacityBackpack)
    private static final kitty_protos.S2CCapacityBackpack.S2C_CapacityBackpack DEFAULT_INSTANCE;
    static {
      S2C_CapacityBackpack defaultInstance = new S2C_CapacityBackpack();
      // New instances are implicitly immutable so no need to make
      // immutable.
      DEFAULT_INSTANCE = defaultInstance;
      com.google.protobuf.GeneratedMessageLite.registerDefaultInstance(
        S2C_CapacityBackpack.class, defaultInstance);
    }

    public static kitty_protos.S2CCapacityBackpack.S2C_CapacityBackpack getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static volatile com.google.protobuf.Parser<S2C_CapacityBackpack> PARSER;

    public static com.google.protobuf.Parser<S2C_CapacityBackpack> parser() {
      return DEFAULT_INSTANCE.getParserForType();
    }
  }


  static {
  }

  // @@protoc_insertion_point(outer_class_scope)
}
