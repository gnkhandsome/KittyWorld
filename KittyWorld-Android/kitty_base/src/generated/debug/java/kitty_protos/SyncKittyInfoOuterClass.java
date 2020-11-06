// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: SyncKittyInfo.proto

package kitty_protos;

public final class SyncKittyInfoOuterClass {
  private SyncKittyInfoOuterClass() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }
  public interface SyncKittyInfoOrBuilder extends
      // @@protoc_insertion_point(interface_extends:kitty_protos.SyncKittyInfo)
      com.google.protobuf.MessageLiteOrBuilder {

    /**
     * <code>string totalCoin = 1;</code>
     */
    java.lang.String getTotalCoin();
    /**
     * <code>string totalCoin = 1;</code>
     */
    com.google.protobuf.ByteString
        getTotalCoinBytes();

    /**
     * <code>string currBuyPrice = 2;</code>
     */
    java.lang.String getCurrBuyPrice();
    /**
     * <code>string currBuyPrice = 2;</code>
     */
    com.google.protobuf.ByteString
        getCurrBuyPriceBytes();

    /**
     * <code>int32 maxLevel = 3;</code>
     */
    int getMaxLevel();

    /**
     * <code>repeated .kitty_protos.kittyLevelList kittyLevelList = 4;</code>
     */
    java.util.List<kitty_protos.SyncKittyInfoOuterClass.kittyLevelList> 
        getKittyLevelListList();
    /**
     * <code>repeated .kitty_protos.kittyLevelList kittyLevelList = 4;</code>
     */
    kitty_protos.SyncKittyInfoOuterClass.kittyLevelList getKittyLevelList(int index);
    /**
     * <code>repeated .kitty_protos.kittyLevelList kittyLevelList = 4;</code>
     */
    int getKittyLevelListCount();

    /**
     * <code>int32 buyLevel = 5;</code>
     */
    int getBuyLevel();
  }
  /**
   * Protobuf type {@code kitty_protos.SyncKittyInfo}
   */
  public  static final class SyncKittyInfo extends
      com.google.protobuf.GeneratedMessageLite<
          SyncKittyInfo, SyncKittyInfo.Builder> implements
      // @@protoc_insertion_point(message_implements:kitty_protos.SyncKittyInfo)
      SyncKittyInfoOrBuilder {
    private SyncKittyInfo() {
      totalCoin_ = "";
      currBuyPrice_ = "";
      kittyLevelList_ = emptyProtobufList();
    }
    public static final int TOTALCOIN_FIELD_NUMBER = 1;
    private java.lang.String totalCoin_;
    /**
     * <code>string totalCoin = 1;</code>
     */
    @java.lang.Override
    public java.lang.String getTotalCoin() {
      return totalCoin_;
    }
    /**
     * <code>string totalCoin = 1;</code>
     */
    @java.lang.Override
    public com.google.protobuf.ByteString
        getTotalCoinBytes() {
      return com.google.protobuf.ByteString.copyFromUtf8(totalCoin_);
    }
    /**
     * <code>string totalCoin = 1;</code>
     */
    private void setTotalCoin(
        java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      totalCoin_ = value;
    }
    /**
     * <code>string totalCoin = 1;</code>
     */
    private void clearTotalCoin() {
      
      totalCoin_ = getDefaultInstance().getTotalCoin();
    }
    /**
     * <code>string totalCoin = 1;</code>
     */
    private void setTotalCoinBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
      
      totalCoin_ = value.toStringUtf8();
    }

    public static final int CURRBUYPRICE_FIELD_NUMBER = 2;
    private java.lang.String currBuyPrice_;
    /**
     * <code>string currBuyPrice = 2;</code>
     */
    @java.lang.Override
    public java.lang.String getCurrBuyPrice() {
      return currBuyPrice_;
    }
    /**
     * <code>string currBuyPrice = 2;</code>
     */
    @java.lang.Override
    public com.google.protobuf.ByteString
        getCurrBuyPriceBytes() {
      return com.google.protobuf.ByteString.copyFromUtf8(currBuyPrice_);
    }
    /**
     * <code>string currBuyPrice = 2;</code>
     */
    private void setCurrBuyPrice(
        java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      currBuyPrice_ = value;
    }
    /**
     * <code>string currBuyPrice = 2;</code>
     */
    private void clearCurrBuyPrice() {
      
      currBuyPrice_ = getDefaultInstance().getCurrBuyPrice();
    }
    /**
     * <code>string currBuyPrice = 2;</code>
     */
    private void setCurrBuyPriceBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
      
      currBuyPrice_ = value.toStringUtf8();
    }

    public static final int MAXLEVEL_FIELD_NUMBER = 3;
    private int maxLevel_;
    /**
     * <code>int32 maxLevel = 3;</code>
     */
    @java.lang.Override
    public int getMaxLevel() {
      return maxLevel_;
    }
    /**
     * <code>int32 maxLevel = 3;</code>
     */
    private void setMaxLevel(int value) {
      
      maxLevel_ = value;
    }
    /**
     * <code>int32 maxLevel = 3;</code>
     */
    private void clearMaxLevel() {
      
      maxLevel_ = 0;
    }

    public static final int KITTYLEVELLIST_FIELD_NUMBER = 4;
    private com.google.protobuf.Internal.ProtobufList<kitty_protos.SyncKittyInfoOuterClass.kittyLevelList> kittyLevelList_;
    /**
     * <code>repeated .kitty_protos.kittyLevelList kittyLevelList = 4;</code>
     */
    @java.lang.Override
    public java.util.List<kitty_protos.SyncKittyInfoOuterClass.kittyLevelList> getKittyLevelListList() {
      return kittyLevelList_;
    }
    /**
     * <code>repeated .kitty_protos.kittyLevelList kittyLevelList = 4;</code>
     */
    public java.util.List<? extends kitty_protos.SyncKittyInfoOuterClass.kittyLevelListOrBuilder> 
        getKittyLevelListOrBuilderList() {
      return kittyLevelList_;
    }
    /**
     * <code>repeated .kitty_protos.kittyLevelList kittyLevelList = 4;</code>
     */
    @java.lang.Override
    public int getKittyLevelListCount() {
      return kittyLevelList_.size();
    }
    /**
     * <code>repeated .kitty_protos.kittyLevelList kittyLevelList = 4;</code>
     */
    @java.lang.Override
    public kitty_protos.SyncKittyInfoOuterClass.kittyLevelList getKittyLevelList(int index) {
      return kittyLevelList_.get(index);
    }
    /**
     * <code>repeated .kitty_protos.kittyLevelList kittyLevelList = 4;</code>
     */
    public kitty_protos.SyncKittyInfoOuterClass.kittyLevelListOrBuilder getKittyLevelListOrBuilder(
        int index) {
      return kittyLevelList_.get(index);
    }
    private void ensureKittyLevelListIsMutable() {
      if (!kittyLevelList_.isModifiable()) {
        kittyLevelList_ =
            com.google.protobuf.GeneratedMessageLite.mutableCopy(kittyLevelList_);
       }
    }

    /**
     * <code>repeated .kitty_protos.kittyLevelList kittyLevelList = 4;</code>
     */
    private void setKittyLevelList(
        int index, kitty_protos.SyncKittyInfoOuterClass.kittyLevelList value) {
      if (value == null) {
        throw new NullPointerException();
      }
      ensureKittyLevelListIsMutable();
      kittyLevelList_.set(index, value);
    }
    /**
     * <code>repeated .kitty_protos.kittyLevelList kittyLevelList = 4;</code>
     */
    private void setKittyLevelList(
        int index, kitty_protos.SyncKittyInfoOuterClass.kittyLevelList.Builder builderForValue) {
      ensureKittyLevelListIsMutable();
      kittyLevelList_.set(index, builderForValue.build());
    }
    /**
     * <code>repeated .kitty_protos.kittyLevelList kittyLevelList = 4;</code>
     */
    private void addKittyLevelList(kitty_protos.SyncKittyInfoOuterClass.kittyLevelList value) {
      if (value == null) {
        throw new NullPointerException();
      }
      ensureKittyLevelListIsMutable();
      kittyLevelList_.add(value);
    }
    /**
     * <code>repeated .kitty_protos.kittyLevelList kittyLevelList = 4;</code>
     */
    private void addKittyLevelList(
        int index, kitty_protos.SyncKittyInfoOuterClass.kittyLevelList value) {
      if (value == null) {
        throw new NullPointerException();
      }
      ensureKittyLevelListIsMutable();
      kittyLevelList_.add(index, value);
    }
    /**
     * <code>repeated .kitty_protos.kittyLevelList kittyLevelList = 4;</code>
     */
    private void addKittyLevelList(
        kitty_protos.SyncKittyInfoOuterClass.kittyLevelList.Builder builderForValue) {
      ensureKittyLevelListIsMutable();
      kittyLevelList_.add(builderForValue.build());
    }
    /**
     * <code>repeated .kitty_protos.kittyLevelList kittyLevelList = 4;</code>
     */
    private void addKittyLevelList(
        int index, kitty_protos.SyncKittyInfoOuterClass.kittyLevelList.Builder builderForValue) {
      ensureKittyLevelListIsMutable();
      kittyLevelList_.add(index, builderForValue.build());
    }
    /**
     * <code>repeated .kitty_protos.kittyLevelList kittyLevelList = 4;</code>
     */
    private void addAllKittyLevelList(
        java.lang.Iterable<? extends kitty_protos.SyncKittyInfoOuterClass.kittyLevelList> values) {
      ensureKittyLevelListIsMutable();
      com.google.protobuf.AbstractMessageLite.addAll(
          values, kittyLevelList_);
    }
    /**
     * <code>repeated .kitty_protos.kittyLevelList kittyLevelList = 4;</code>
     */
    private void clearKittyLevelList() {
      kittyLevelList_ = emptyProtobufList();
    }
    /**
     * <code>repeated .kitty_protos.kittyLevelList kittyLevelList = 4;</code>
     */
    private void removeKittyLevelList(int index) {
      ensureKittyLevelListIsMutable();
      kittyLevelList_.remove(index);
    }

    public static final int BUYLEVEL_FIELD_NUMBER = 5;
    private int buyLevel_;
    /**
     * <code>int32 buyLevel = 5;</code>
     */
    @java.lang.Override
    public int getBuyLevel() {
      return buyLevel_;
    }
    /**
     * <code>int32 buyLevel = 5;</code>
     */
    private void setBuyLevel(int value) {
      
      buyLevel_ = value;
    }
    /**
     * <code>int32 buyLevel = 5;</code>
     */
    private void clearBuyLevel() {
      
      buyLevel_ = 0;
    }

    public static kitty_protos.SyncKittyInfoOuterClass.SyncKittyInfo parseFrom(
        java.nio.ByteBuffer data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, data);
    }
    public static kitty_protos.SyncKittyInfoOuterClass.SyncKittyInfo parseFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, data, extensionRegistry);
    }
    public static kitty_protos.SyncKittyInfoOuterClass.SyncKittyInfo parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, data);
    }
    public static kitty_protos.SyncKittyInfoOuterClass.SyncKittyInfo parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, data, extensionRegistry);
    }
    public static kitty_protos.SyncKittyInfoOuterClass.SyncKittyInfo parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, data);
    }
    public static kitty_protos.SyncKittyInfoOuterClass.SyncKittyInfo parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, data, extensionRegistry);
    }
    public static kitty_protos.SyncKittyInfoOuterClass.SyncKittyInfo parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, input);
    }
    public static kitty_protos.SyncKittyInfoOuterClass.SyncKittyInfo parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, input, extensionRegistry);
    }
    public static kitty_protos.SyncKittyInfoOuterClass.SyncKittyInfo parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return parseDelimitedFrom(DEFAULT_INSTANCE, input);
    }
    public static kitty_protos.SyncKittyInfoOuterClass.SyncKittyInfo parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }
    public static kitty_protos.SyncKittyInfoOuterClass.SyncKittyInfo parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, input);
    }
    public static kitty_protos.SyncKittyInfoOuterClass.SyncKittyInfo parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static Builder newBuilder() {
      return (Builder) DEFAULT_INSTANCE.createBuilder();
    }
    public static Builder newBuilder(kitty_protos.SyncKittyInfoOuterClass.SyncKittyInfo prototype) {
      return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
    }

    /**
     * Protobuf type {@code kitty_protos.SyncKittyInfo}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageLite.Builder<
          kitty_protos.SyncKittyInfoOuterClass.SyncKittyInfo, Builder> implements
        // @@protoc_insertion_point(builder_implements:kitty_protos.SyncKittyInfo)
        kitty_protos.SyncKittyInfoOuterClass.SyncKittyInfoOrBuilder {
      // Construct using kitty_protos.SyncKittyInfoOuterClass.SyncKittyInfo.newBuilder()
      private Builder() {
        super(DEFAULT_INSTANCE);
      }


      /**
       * <code>string totalCoin = 1;</code>
       */
      @java.lang.Override
      public java.lang.String getTotalCoin() {
        return instance.getTotalCoin();
      }
      /**
       * <code>string totalCoin = 1;</code>
       */
      @java.lang.Override
      public com.google.protobuf.ByteString
          getTotalCoinBytes() {
        return instance.getTotalCoinBytes();
      }
      /**
       * <code>string totalCoin = 1;</code>
       */
      public Builder setTotalCoin(
          java.lang.String value) {
        copyOnWrite();
        instance.setTotalCoin(value);
        return this;
      }
      /**
       * <code>string totalCoin = 1;</code>
       */
      public Builder clearTotalCoin() {
        copyOnWrite();
        instance.clearTotalCoin();
        return this;
      }
      /**
       * <code>string totalCoin = 1;</code>
       */
      public Builder setTotalCoinBytes(
          com.google.protobuf.ByteString value) {
        copyOnWrite();
        instance.setTotalCoinBytes(value);
        return this;
      }

      /**
       * <code>string currBuyPrice = 2;</code>
       */
      @java.lang.Override
      public java.lang.String getCurrBuyPrice() {
        return instance.getCurrBuyPrice();
      }
      /**
       * <code>string currBuyPrice = 2;</code>
       */
      @java.lang.Override
      public com.google.protobuf.ByteString
          getCurrBuyPriceBytes() {
        return instance.getCurrBuyPriceBytes();
      }
      /**
       * <code>string currBuyPrice = 2;</code>
       */
      public Builder setCurrBuyPrice(
          java.lang.String value) {
        copyOnWrite();
        instance.setCurrBuyPrice(value);
        return this;
      }
      /**
       * <code>string currBuyPrice = 2;</code>
       */
      public Builder clearCurrBuyPrice() {
        copyOnWrite();
        instance.clearCurrBuyPrice();
        return this;
      }
      /**
       * <code>string currBuyPrice = 2;</code>
       */
      public Builder setCurrBuyPriceBytes(
          com.google.protobuf.ByteString value) {
        copyOnWrite();
        instance.setCurrBuyPriceBytes(value);
        return this;
      }

      /**
       * <code>int32 maxLevel = 3;</code>
       */
      @java.lang.Override
      public int getMaxLevel() {
        return instance.getMaxLevel();
      }
      /**
       * <code>int32 maxLevel = 3;</code>
       */
      public Builder setMaxLevel(int value) {
        copyOnWrite();
        instance.setMaxLevel(value);
        return this;
      }
      /**
       * <code>int32 maxLevel = 3;</code>
       */
      public Builder clearMaxLevel() {
        copyOnWrite();
        instance.clearMaxLevel();
        return this;
      }

      /**
       * <code>repeated .kitty_protos.kittyLevelList kittyLevelList = 4;</code>
       */
      @java.lang.Override
      public java.util.List<kitty_protos.SyncKittyInfoOuterClass.kittyLevelList> getKittyLevelListList() {
        return java.util.Collections.unmodifiableList(
            instance.getKittyLevelListList());
      }
      /**
       * <code>repeated .kitty_protos.kittyLevelList kittyLevelList = 4;</code>
       */
      @java.lang.Override
      public int getKittyLevelListCount() {
        return instance.getKittyLevelListCount();
      }/**
       * <code>repeated .kitty_protos.kittyLevelList kittyLevelList = 4;</code>
       */
      @java.lang.Override
      public kitty_protos.SyncKittyInfoOuterClass.kittyLevelList getKittyLevelList(int index) {
        return instance.getKittyLevelList(index);
      }
      /**
       * <code>repeated .kitty_protos.kittyLevelList kittyLevelList = 4;</code>
       */
      public Builder setKittyLevelList(
          int index, kitty_protos.SyncKittyInfoOuterClass.kittyLevelList value) {
        copyOnWrite();
        instance.setKittyLevelList(index, value);
        return this;
      }
      /**
       * <code>repeated .kitty_protos.kittyLevelList kittyLevelList = 4;</code>
       */
      public Builder setKittyLevelList(
          int index, kitty_protos.SyncKittyInfoOuterClass.kittyLevelList.Builder builderForValue) {
        copyOnWrite();
        instance.setKittyLevelList(index, builderForValue);
        return this;
      }
      /**
       * <code>repeated .kitty_protos.kittyLevelList kittyLevelList = 4;</code>
       */
      public Builder addKittyLevelList(kitty_protos.SyncKittyInfoOuterClass.kittyLevelList value) {
        copyOnWrite();
        instance.addKittyLevelList(value);
        return this;
      }
      /**
       * <code>repeated .kitty_protos.kittyLevelList kittyLevelList = 4;</code>
       */
      public Builder addKittyLevelList(
          int index, kitty_protos.SyncKittyInfoOuterClass.kittyLevelList value) {
        copyOnWrite();
        instance.addKittyLevelList(index, value);
        return this;
      }
      /**
       * <code>repeated .kitty_protos.kittyLevelList kittyLevelList = 4;</code>
       */
      public Builder addKittyLevelList(
          kitty_protos.SyncKittyInfoOuterClass.kittyLevelList.Builder builderForValue) {
        copyOnWrite();
        instance.addKittyLevelList(builderForValue);
        return this;
      }
      /**
       * <code>repeated .kitty_protos.kittyLevelList kittyLevelList = 4;</code>
       */
      public Builder addKittyLevelList(
          int index, kitty_protos.SyncKittyInfoOuterClass.kittyLevelList.Builder builderForValue) {
        copyOnWrite();
        instance.addKittyLevelList(index, builderForValue);
        return this;
      }
      /**
       * <code>repeated .kitty_protos.kittyLevelList kittyLevelList = 4;</code>
       */
      public Builder addAllKittyLevelList(
          java.lang.Iterable<? extends kitty_protos.SyncKittyInfoOuterClass.kittyLevelList> values) {
        copyOnWrite();
        instance.addAllKittyLevelList(values);
        return this;
      }
      /**
       * <code>repeated .kitty_protos.kittyLevelList kittyLevelList = 4;</code>
       */
      public Builder clearKittyLevelList() {
        copyOnWrite();
        instance.clearKittyLevelList();
        return this;
      }
      /**
       * <code>repeated .kitty_protos.kittyLevelList kittyLevelList = 4;</code>
       */
      public Builder removeKittyLevelList(int index) {
        copyOnWrite();
        instance.removeKittyLevelList(index);
        return this;
      }

      /**
       * <code>int32 buyLevel = 5;</code>
       */
      @java.lang.Override
      public int getBuyLevel() {
        return instance.getBuyLevel();
      }
      /**
       * <code>int32 buyLevel = 5;</code>
       */
      public Builder setBuyLevel(int value) {
        copyOnWrite();
        instance.setBuyLevel(value);
        return this;
      }
      /**
       * <code>int32 buyLevel = 5;</code>
       */
      public Builder clearBuyLevel() {
        copyOnWrite();
        instance.clearBuyLevel();
        return this;
      }

      // @@protoc_insertion_point(builder_scope:kitty_protos.SyncKittyInfo)
    }
    @java.lang.Override
    @java.lang.SuppressWarnings({"unchecked", "fallthrough"})
    protected final java.lang.Object dynamicMethod(
        com.google.protobuf.GeneratedMessageLite.MethodToInvoke method,
        java.lang.Object arg0, java.lang.Object arg1) {
      switch (method) {
        case NEW_MUTABLE_INSTANCE: {
          return new kitty_protos.SyncKittyInfoOuterClass.SyncKittyInfo();
        }
        case NEW_BUILDER: {
          return new Builder();
        }
        case BUILD_MESSAGE_INFO: {
            java.lang.Object[] objects = new java.lang.Object[] {
              "totalCoin_",
              "currBuyPrice_",
              "maxLevel_",
              "kittyLevelList_",
              kitty_protos.SyncKittyInfoOuterClass.kittyLevelList.class,
              "buyLevel_",
            };
            java.lang.String info =
                "\u0000\u0005\u0000\u0000\u0001\u0005\u0005\u0000\u0001\u0000\u0001\u0208\u0002\u0208" +
                "\u0003\u0004\u0004\u001b\u0005\u0004";
            return newMessageInfo(DEFAULT_INSTANCE, info, objects);
        }
        // fall through
        case GET_DEFAULT_INSTANCE: {
          return DEFAULT_INSTANCE;
        }
        case GET_PARSER: {
          com.google.protobuf.Parser<kitty_protos.SyncKittyInfoOuterClass.SyncKittyInfo> parser = PARSER;
          if (parser == null) {
            synchronized (kitty_protos.SyncKittyInfoOuterClass.SyncKittyInfo.class) {
              parser = PARSER;
              if (parser == null) {
                parser =
                    new DefaultInstanceBasedParser<kitty_protos.SyncKittyInfoOuterClass.SyncKittyInfo>(
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


    // @@protoc_insertion_point(class_scope:kitty_protos.SyncKittyInfo)
    private static final kitty_protos.SyncKittyInfoOuterClass.SyncKittyInfo DEFAULT_INSTANCE;
    static {
      SyncKittyInfo defaultInstance = new SyncKittyInfo();
      // New instances are implicitly immutable so no need to make
      // immutable.
      DEFAULT_INSTANCE = defaultInstance;
      com.google.protobuf.GeneratedMessageLite.registerDefaultInstance(
        SyncKittyInfo.class, defaultInstance);
    }

    public static kitty_protos.SyncKittyInfoOuterClass.SyncKittyInfo getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static volatile com.google.protobuf.Parser<SyncKittyInfo> PARSER;

    public static com.google.protobuf.Parser<SyncKittyInfo> parser() {
      return DEFAULT_INSTANCE.getParserForType();
    }
  }

  public interface kittyLevelListOrBuilder extends
      // @@protoc_insertion_point(interface_extends:kitty_protos.kittyLevelList)
      com.google.protobuf.MessageLiteOrBuilder {

    /**
     * <code>int32 level = 1;</code>
     */
    int getLevel();

    /**
     * <code>int64 expireTime = 2;</code>
     */
    long getExpireTime();

    /**
     * <code>int64 expireIn = 3;</code>
     */
    long getExpireIn();
  }
  /**
   * Protobuf type {@code kitty_protos.kittyLevelList}
   */
  public  static final class kittyLevelList extends
      com.google.protobuf.GeneratedMessageLite<
          kittyLevelList, kittyLevelList.Builder> implements
      // @@protoc_insertion_point(message_implements:kitty_protos.kittyLevelList)
      kittyLevelListOrBuilder {
    private kittyLevelList() {
    }
    public static final int LEVEL_FIELD_NUMBER = 1;
    private int level_;
    /**
     * <code>int32 level = 1;</code>
     */
    @java.lang.Override
    public int getLevel() {
      return level_;
    }
    /**
     * <code>int32 level = 1;</code>
     */
    private void setLevel(int value) {
      
      level_ = value;
    }
    /**
     * <code>int32 level = 1;</code>
     */
    private void clearLevel() {
      
      level_ = 0;
    }

    public static final int EXPIRETIME_FIELD_NUMBER = 2;
    private long expireTime_;
    /**
     * <code>int64 expireTime = 2;</code>
     */
    @java.lang.Override
    public long getExpireTime() {
      return expireTime_;
    }
    /**
     * <code>int64 expireTime = 2;</code>
     */
    private void setExpireTime(long value) {
      
      expireTime_ = value;
    }
    /**
     * <code>int64 expireTime = 2;</code>
     */
    private void clearExpireTime() {
      
      expireTime_ = 0L;
    }

    public static final int EXPIREIN_FIELD_NUMBER = 3;
    private long expireIn_;
    /**
     * <code>int64 expireIn = 3;</code>
     */
    @java.lang.Override
    public long getExpireIn() {
      return expireIn_;
    }
    /**
     * <code>int64 expireIn = 3;</code>
     */
    private void setExpireIn(long value) {
      
      expireIn_ = value;
    }
    /**
     * <code>int64 expireIn = 3;</code>
     */
    private void clearExpireIn() {
      
      expireIn_ = 0L;
    }

    public static kitty_protos.SyncKittyInfoOuterClass.kittyLevelList parseFrom(
        java.nio.ByteBuffer data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, data);
    }
    public static kitty_protos.SyncKittyInfoOuterClass.kittyLevelList parseFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, data, extensionRegistry);
    }
    public static kitty_protos.SyncKittyInfoOuterClass.kittyLevelList parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, data);
    }
    public static kitty_protos.SyncKittyInfoOuterClass.kittyLevelList parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, data, extensionRegistry);
    }
    public static kitty_protos.SyncKittyInfoOuterClass.kittyLevelList parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, data);
    }
    public static kitty_protos.SyncKittyInfoOuterClass.kittyLevelList parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, data, extensionRegistry);
    }
    public static kitty_protos.SyncKittyInfoOuterClass.kittyLevelList parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, input);
    }
    public static kitty_protos.SyncKittyInfoOuterClass.kittyLevelList parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, input, extensionRegistry);
    }
    public static kitty_protos.SyncKittyInfoOuterClass.kittyLevelList parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return parseDelimitedFrom(DEFAULT_INSTANCE, input);
    }
    public static kitty_protos.SyncKittyInfoOuterClass.kittyLevelList parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }
    public static kitty_protos.SyncKittyInfoOuterClass.kittyLevelList parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, input);
    }
    public static kitty_protos.SyncKittyInfoOuterClass.kittyLevelList parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static Builder newBuilder() {
      return (Builder) DEFAULT_INSTANCE.createBuilder();
    }
    public static Builder newBuilder(kitty_protos.SyncKittyInfoOuterClass.kittyLevelList prototype) {
      return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
    }

    /**
     * Protobuf type {@code kitty_protos.kittyLevelList}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageLite.Builder<
          kitty_protos.SyncKittyInfoOuterClass.kittyLevelList, Builder> implements
        // @@protoc_insertion_point(builder_implements:kitty_protos.kittyLevelList)
        kitty_protos.SyncKittyInfoOuterClass.kittyLevelListOrBuilder {
      // Construct using kitty_protos.SyncKittyInfoOuterClass.kittyLevelList.newBuilder()
      private Builder() {
        super(DEFAULT_INSTANCE);
      }


      /**
       * <code>int32 level = 1;</code>
       */
      @java.lang.Override
      public int getLevel() {
        return instance.getLevel();
      }
      /**
       * <code>int32 level = 1;</code>
       */
      public Builder setLevel(int value) {
        copyOnWrite();
        instance.setLevel(value);
        return this;
      }
      /**
       * <code>int32 level = 1;</code>
       */
      public Builder clearLevel() {
        copyOnWrite();
        instance.clearLevel();
        return this;
      }

      /**
       * <code>int64 expireTime = 2;</code>
       */
      @java.lang.Override
      public long getExpireTime() {
        return instance.getExpireTime();
      }
      /**
       * <code>int64 expireTime = 2;</code>
       */
      public Builder setExpireTime(long value) {
        copyOnWrite();
        instance.setExpireTime(value);
        return this;
      }
      /**
       * <code>int64 expireTime = 2;</code>
       */
      public Builder clearExpireTime() {
        copyOnWrite();
        instance.clearExpireTime();
        return this;
      }

      /**
       * <code>int64 expireIn = 3;</code>
       */
      @java.lang.Override
      public long getExpireIn() {
        return instance.getExpireIn();
      }
      /**
       * <code>int64 expireIn = 3;</code>
       */
      public Builder setExpireIn(long value) {
        copyOnWrite();
        instance.setExpireIn(value);
        return this;
      }
      /**
       * <code>int64 expireIn = 3;</code>
       */
      public Builder clearExpireIn() {
        copyOnWrite();
        instance.clearExpireIn();
        return this;
      }

      // @@protoc_insertion_point(builder_scope:kitty_protos.kittyLevelList)
    }
    @java.lang.Override
    @java.lang.SuppressWarnings({"unchecked", "fallthrough"})
    protected final java.lang.Object dynamicMethod(
        com.google.protobuf.GeneratedMessageLite.MethodToInvoke method,
        java.lang.Object arg0, java.lang.Object arg1) {
      switch (method) {
        case NEW_MUTABLE_INSTANCE: {
          return new kitty_protos.SyncKittyInfoOuterClass.kittyLevelList();
        }
        case NEW_BUILDER: {
          return new Builder();
        }
        case BUILD_MESSAGE_INFO: {
            java.lang.Object[] objects = new java.lang.Object[] {
              "level_",
              "expireTime_",
              "expireIn_",
            };
            java.lang.String info =
                "\u0000\u0003\u0000\u0000\u0001\u0003\u0003\u0000\u0000\u0000\u0001\u0004\u0002\u0002" +
                "\u0003\u0002";
            return newMessageInfo(DEFAULT_INSTANCE, info, objects);
        }
        // fall through
        case GET_DEFAULT_INSTANCE: {
          return DEFAULT_INSTANCE;
        }
        case GET_PARSER: {
          com.google.protobuf.Parser<kitty_protos.SyncKittyInfoOuterClass.kittyLevelList> parser = PARSER;
          if (parser == null) {
            synchronized (kitty_protos.SyncKittyInfoOuterClass.kittyLevelList.class) {
              parser = PARSER;
              if (parser == null) {
                parser =
                    new DefaultInstanceBasedParser<kitty_protos.SyncKittyInfoOuterClass.kittyLevelList>(
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


    // @@protoc_insertion_point(class_scope:kitty_protos.kittyLevelList)
    private static final kitty_protos.SyncKittyInfoOuterClass.kittyLevelList DEFAULT_INSTANCE;
    static {
      kittyLevelList defaultInstance = new kittyLevelList();
      // New instances are implicitly immutable so no need to make
      // immutable.
      DEFAULT_INSTANCE = defaultInstance;
      com.google.protobuf.GeneratedMessageLite.registerDefaultInstance(
        kittyLevelList.class, defaultInstance);
    }

    public static kitty_protos.SyncKittyInfoOuterClass.kittyLevelList getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static volatile com.google.protobuf.Parser<kittyLevelList> PARSER;

    public static com.google.protobuf.Parser<kittyLevelList> parser() {
      return DEFAULT_INSTANCE.getParserForType();
    }
  }


  static {
  }

  // @@protoc_insertion_point(outer_class_scope)
}