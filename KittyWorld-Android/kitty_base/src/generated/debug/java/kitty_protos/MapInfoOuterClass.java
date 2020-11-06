// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: MapInfo.proto

package kitty_protos;

public final class MapInfoOuterClass {
  private MapInfoOuterClass() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }
  public interface MapInfoOrBuilder extends
      // @@protoc_insertion_point(interface_extends:kitty_protos.MapInfo)
      com.google.protobuf.MessageLiteOrBuilder {

    /**
     * <code>repeated int64 info = 1;</code>
     */
    java.util.List<java.lang.Long> getInfoList();
    /**
     * <code>repeated int64 info = 1;</code>
     */
    int getInfoCount();
    /**
     * <code>repeated int64 info = 1;</code>
     */
    long getInfo(int index);
  }
  /**
   * Protobuf type {@code kitty_protos.MapInfo}
   */
  public  static final class MapInfo extends
      com.google.protobuf.GeneratedMessageLite<
          MapInfo, MapInfo.Builder> implements
      // @@protoc_insertion_point(message_implements:kitty_protos.MapInfo)
      MapInfoOrBuilder {
    private MapInfo() {
      info_ = emptyLongList();
    }
    public static final int INFO_FIELD_NUMBER = 1;
    private com.google.protobuf.Internal.LongList info_;
    /**
     * <code>repeated int64 info = 1;</code>
     */
    @java.lang.Override
    public java.util.List<java.lang.Long>
        getInfoList() {
      return info_;
    }
    /**
     * <code>repeated int64 info = 1;</code>
     */
    @java.lang.Override
    public int getInfoCount() {
      return info_.size();
    }
    /**
     * <code>repeated int64 info = 1;</code>
     */
    @java.lang.Override
    public long getInfo(int index) {
      return info_.getLong(index);
    }
    private int infoMemoizedSerializedSize = -1;
    private void ensureInfoIsMutable() {
      if (!info_.isModifiable()) {
        info_ =
            com.google.protobuf.GeneratedMessageLite.mutableCopy(info_);
       }
    }
    /**
     * <code>repeated int64 info = 1;</code>
     */
    private void setInfo(
        int index, long value) {
      ensureInfoIsMutable();
      info_.setLong(index, value);
    }
    /**
     * <code>repeated int64 info = 1;</code>
     */
    private void addInfo(long value) {
      ensureInfoIsMutable();
      info_.addLong(value);
    }
    /**
     * <code>repeated int64 info = 1;</code>
     */
    private void addAllInfo(
        java.lang.Iterable<? extends java.lang.Long> values) {
      ensureInfoIsMutable();
      com.google.protobuf.AbstractMessageLite.addAll(
          values, info_);
    }
    /**
     * <code>repeated int64 info = 1;</code>
     */
    private void clearInfo() {
      info_ = emptyLongList();
    }

    public static kitty_protos.MapInfoOuterClass.MapInfo parseFrom(
        java.nio.ByteBuffer data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, data);
    }
    public static kitty_protos.MapInfoOuterClass.MapInfo parseFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, data, extensionRegistry);
    }
    public static kitty_protos.MapInfoOuterClass.MapInfo parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, data);
    }
    public static kitty_protos.MapInfoOuterClass.MapInfo parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, data, extensionRegistry);
    }
    public static kitty_protos.MapInfoOuterClass.MapInfo parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, data);
    }
    public static kitty_protos.MapInfoOuterClass.MapInfo parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, data, extensionRegistry);
    }
    public static kitty_protos.MapInfoOuterClass.MapInfo parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, input);
    }
    public static kitty_protos.MapInfoOuterClass.MapInfo parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, input, extensionRegistry);
    }
    public static kitty_protos.MapInfoOuterClass.MapInfo parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return parseDelimitedFrom(DEFAULT_INSTANCE, input);
    }
    public static kitty_protos.MapInfoOuterClass.MapInfo parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }
    public static kitty_protos.MapInfoOuterClass.MapInfo parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, input);
    }
    public static kitty_protos.MapInfoOuterClass.MapInfo parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static Builder newBuilder() {
      return (Builder) DEFAULT_INSTANCE.createBuilder();
    }
    public static Builder newBuilder(kitty_protos.MapInfoOuterClass.MapInfo prototype) {
      return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
    }

    /**
     * Protobuf type {@code kitty_protos.MapInfo}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageLite.Builder<
          kitty_protos.MapInfoOuterClass.MapInfo, Builder> implements
        // @@protoc_insertion_point(builder_implements:kitty_protos.MapInfo)
        kitty_protos.MapInfoOuterClass.MapInfoOrBuilder {
      // Construct using kitty_protos.MapInfoOuterClass.MapInfo.newBuilder()
      private Builder() {
        super(DEFAULT_INSTANCE);
      }


      /**
       * <code>repeated int64 info = 1;</code>
       */
      @java.lang.Override
      public java.util.List<java.lang.Long>
          getInfoList() {
        return java.util.Collections.unmodifiableList(
            instance.getInfoList());
      }
      /**
       * <code>repeated int64 info = 1;</code>
       */
      @java.lang.Override
      public int getInfoCount() {
        return instance.getInfoCount();
      }
      /**
       * <code>repeated int64 info = 1;</code>
       */
      @java.lang.Override
      public long getInfo(int index) {
        return instance.getInfo(index);
      }
      /**
       * <code>repeated int64 info = 1;</code>
       */
      public Builder setInfo(
          int index, long value) {
        copyOnWrite();
        instance.setInfo(index, value);
        return this;
      }
      /**
       * <code>repeated int64 info = 1;</code>
       */
      public Builder addInfo(long value) {
        copyOnWrite();
        instance.addInfo(value);
        return this;
      }
      /**
       * <code>repeated int64 info = 1;</code>
       */
      public Builder addAllInfo(
          java.lang.Iterable<? extends java.lang.Long> values) {
        copyOnWrite();
        instance.addAllInfo(values);
        return this;
      }
      /**
       * <code>repeated int64 info = 1;</code>
       */
      public Builder clearInfo() {
        copyOnWrite();
        instance.clearInfo();
        return this;
      }

      // @@protoc_insertion_point(builder_scope:kitty_protos.MapInfo)
    }
    @java.lang.Override
    @java.lang.SuppressWarnings({"unchecked", "fallthrough"})
    protected final java.lang.Object dynamicMethod(
        com.google.protobuf.GeneratedMessageLite.MethodToInvoke method,
        java.lang.Object arg0, java.lang.Object arg1) {
      switch (method) {
        case NEW_MUTABLE_INSTANCE: {
          return new kitty_protos.MapInfoOuterClass.MapInfo();
        }
        case NEW_BUILDER: {
          return new Builder();
        }
        case BUILD_MESSAGE_INFO: {
            java.lang.Object[] objects = new java.lang.Object[] {
              "info_",
            };
            java.lang.String info =
                "\u0000\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0001\u0000\u0001%";
            return newMessageInfo(DEFAULT_INSTANCE, info, objects);
        }
        // fall through
        case GET_DEFAULT_INSTANCE: {
          return DEFAULT_INSTANCE;
        }
        case GET_PARSER: {
          com.google.protobuf.Parser<kitty_protos.MapInfoOuterClass.MapInfo> parser = PARSER;
          if (parser == null) {
            synchronized (kitty_protos.MapInfoOuterClass.MapInfo.class) {
              parser = PARSER;
              if (parser == null) {
                parser =
                    new DefaultInstanceBasedParser<kitty_protos.MapInfoOuterClass.MapInfo>(
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


    // @@protoc_insertion_point(class_scope:kitty_protos.MapInfo)
    private static final kitty_protos.MapInfoOuterClass.MapInfo DEFAULT_INSTANCE;
    static {
      MapInfo defaultInstance = new MapInfo();
      // New instances are implicitly immutable so no need to make
      // immutable.
      DEFAULT_INSTANCE = defaultInstance;
      com.google.protobuf.GeneratedMessageLite.registerDefaultInstance(
        MapInfo.class, defaultInstance);
    }

    public static kitty_protos.MapInfoOuterClass.MapInfo getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static volatile com.google.protobuf.Parser<MapInfo> PARSER;

    public static com.google.protobuf.Parser<MapInfo> parser() {
      return DEFAULT_INSTANCE.getParserForType();
    }
  }


  static {
  }

  // @@protoc_insertion_point(outer_class_scope)
}
