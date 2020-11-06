// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: S2C_SyncAdventure.proto

package kitty_protos;

public final class S2CSyncAdventure {
  private S2CSyncAdventure() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }
  public interface S2C_SyncAdventureOrBuilder extends
      // @@protoc_insertion_point(interface_extends:kitty_protos.S2C_SyncAdventure)
      com.google.protobuf.MessageLiteOrBuilder {

    /**
     * <code>int32 code = 1;</code>
     */
    int getCode();

    /**
     * <code>map&lt;int32, int32&gt; kitties = 2;</code>
     */
    int getKittiesCount();
    /**
     * <code>map&lt;int32, int32&gt; kitties = 2;</code>
     */
    boolean containsKitties(
        int key);
    /**
     * Use {@link #getKittiesMap()} instead.
     */
    @java.lang.Deprecated
    java.util.Map<java.lang.Integer, java.lang.Integer>
    getKitties();
    /**
     * <code>map&lt;int32, int32&gt; kitties = 2;</code>
     */
    java.util.Map<java.lang.Integer, java.lang.Integer>
    getKittiesMap();
    /**
     * <code>map&lt;int32, int32&gt; kitties = 2;</code>
     */

    int getKittiesOrDefault(
        int key,
        int defaultValue);
    /**
     * <code>map&lt;int32, int32&gt; kitties = 2;</code>
     */

    int getKittiesOrThrow(
        int key);

    /**
     * <code>int32 status = 3;</code>
     */
    int getStatus();

    /**
     * <code>int64 beginTime = 4;</code>
     */
    long getBeginTime();

    /**
     * <code>int64 endTime = 5;</code>
     */
    long getEndTime();

    /**
     * <code>int32 power = 6;</code>
     */
    int getPower();

    /**
     * <code>repeated .kitty_protos.AdventureReward reward = 7;</code>
     */
    java.util.List<kitty_protos.Adventure.AdventureReward> 
        getRewardList();
    /**
     * <code>repeated .kitty_protos.AdventureReward reward = 7;</code>
     */
    kitty_protos.Adventure.AdventureReward getReward(int index);
    /**
     * <code>repeated .kitty_protos.AdventureReward reward = 7;</code>
     */
    int getRewardCount();

    /**
     * <code>repeated .kitty_protos.AdventureEvent events = 8;</code>
     */
    java.util.List<kitty_protos.Adventure.AdventureEvent> 
        getEventsList();
    /**
     * <code>repeated .kitty_protos.AdventureEvent events = 8;</code>
     */
    kitty_protos.Adventure.AdventureEvent getEvents(int index);
    /**
     * <code>repeated .kitty_protos.AdventureEvent events = 8;</code>
     */
    int getEventsCount();

    /**
     * <code>int32 visitAdTimes = 9;</code>
     */
    int getVisitAdTimes();
  }
  /**
   * Protobuf type {@code kitty_protos.S2C_SyncAdventure}
   */
  public  static final class S2C_SyncAdventure extends
      com.google.protobuf.GeneratedMessageLite<
          S2C_SyncAdventure, S2C_SyncAdventure.Builder> implements
      // @@protoc_insertion_point(message_implements:kitty_protos.S2C_SyncAdventure)
      S2C_SyncAdventureOrBuilder {
    private S2C_SyncAdventure() {
      reward_ = emptyProtobufList();
      events_ = emptyProtobufList();
    }
    /**
     * Protobuf enum {@code kitty_protos.S2C_SyncAdventure.StatusCode}
     */
    public enum StatusCode
        implements com.google.protobuf.Internal.EnumLite {
      /**
       * <code>SUCCESS = 0;</code>
       */
      SUCCESS(0),
      /**
       * <code>FAILED = 1;</code>
       */
      FAILED(1),
      UNRECOGNIZED(-1),
      ;

      /**
       * <code>SUCCESS = 0;</code>
       */
      public static final int SUCCESS_VALUE = 0;
      /**
       * <code>FAILED = 1;</code>
       */
      public static final int FAILED_VALUE = 1;


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
          case 1: return FAILED;
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

      // @@protoc_insertion_point(enum_scope:kitty_protos.S2C_SyncAdventure.StatusCode)
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

    public static final int KITTIES_FIELD_NUMBER = 2;
    private static final class KittiesDefaultEntryHolder {
      static final com.google.protobuf.MapEntryLite<
          java.lang.Integer, java.lang.Integer> defaultEntry =
              com.google.protobuf.MapEntryLite
              .<java.lang.Integer, java.lang.Integer>newDefaultInstance(
                  com.google.protobuf.WireFormat.FieldType.INT32,
                  0,
                  com.google.protobuf.WireFormat.FieldType.INT32,
                  0);
    }
    private com.google.protobuf.MapFieldLite<
        java.lang.Integer, java.lang.Integer> kitties_ =
            com.google.protobuf.MapFieldLite.emptyMapField();
    private com.google.protobuf.MapFieldLite<java.lang.Integer, java.lang.Integer>
    internalGetKitties() {
      return kitties_;
    }
    private com.google.protobuf.MapFieldLite<java.lang.Integer, java.lang.Integer>
    internalGetMutableKitties() {
      if (!kitties_.isMutable()) {
        kitties_ = kitties_.mutableCopy();
      }
      return kitties_;
    }
    @java.lang.Override

    public int getKittiesCount() {
      return internalGetKitties().size();
    }
    /**
     * <code>map&lt;int32, int32&gt; kitties = 2;</code>
     */
    @java.lang.Override

    public boolean containsKitties(
        int key) {
      
      return internalGetKitties().containsKey(key);
    }
    /**
     * Use {@link #getKittiesMap()} instead.
     */
    @java.lang.Override
    @java.lang.Deprecated
    public java.util.Map<java.lang.Integer, java.lang.Integer> getKitties() {
      return getKittiesMap();
    }
    /**
     * <code>map&lt;int32, int32&gt; kitties = 2;</code>
     */
    @java.lang.Override

    public java.util.Map<java.lang.Integer, java.lang.Integer> getKittiesMap() {
      return java.util.Collections.unmodifiableMap(
          internalGetKitties());
    }
    /**
     * <code>map&lt;int32, int32&gt; kitties = 2;</code>
     */
    @java.lang.Override

    public int getKittiesOrDefault(
        int key,
        int defaultValue) {
      
      java.util.Map<java.lang.Integer, java.lang.Integer> map =
          internalGetKitties();
      return map.containsKey(key) ? map.get(key) : defaultValue;
    }
    /**
     * <code>map&lt;int32, int32&gt; kitties = 2;</code>
     */
    @java.lang.Override

    public int getKittiesOrThrow(
        int key) {
      
      java.util.Map<java.lang.Integer, java.lang.Integer> map =
          internalGetKitties();
      if (!map.containsKey(key)) {
        throw new java.lang.IllegalArgumentException();
      }
      return map.get(key);
    }
    /**
     * <code>map&lt;int32, int32&gt; kitties = 2;</code>
     */
    private java.util.Map<java.lang.Integer, java.lang.Integer>
    getMutableKittiesMap() {
      return internalGetMutableKitties();
    }

    public static final int STATUS_FIELD_NUMBER = 3;
    private int status_;
    /**
     * <code>int32 status = 3;</code>
     */
    @java.lang.Override
    public int getStatus() {
      return status_;
    }
    /**
     * <code>int32 status = 3;</code>
     */
    private void setStatus(int value) {
      
      status_ = value;
    }
    /**
     * <code>int32 status = 3;</code>
     */
    private void clearStatus() {
      
      status_ = 0;
    }

    public static final int BEGINTIME_FIELD_NUMBER = 4;
    private long beginTime_;
    /**
     * <code>int64 beginTime = 4;</code>
     */
    @java.lang.Override
    public long getBeginTime() {
      return beginTime_;
    }
    /**
     * <code>int64 beginTime = 4;</code>
     */
    private void setBeginTime(long value) {
      
      beginTime_ = value;
    }
    /**
     * <code>int64 beginTime = 4;</code>
     */
    private void clearBeginTime() {
      
      beginTime_ = 0L;
    }

    public static final int ENDTIME_FIELD_NUMBER = 5;
    private long endTime_;
    /**
     * <code>int64 endTime = 5;</code>
     */
    @java.lang.Override
    public long getEndTime() {
      return endTime_;
    }
    /**
     * <code>int64 endTime = 5;</code>
     */
    private void setEndTime(long value) {
      
      endTime_ = value;
    }
    /**
     * <code>int64 endTime = 5;</code>
     */
    private void clearEndTime() {
      
      endTime_ = 0L;
    }

    public static final int POWER_FIELD_NUMBER = 6;
    private int power_;
    /**
     * <code>int32 power = 6;</code>
     */
    @java.lang.Override
    public int getPower() {
      return power_;
    }
    /**
     * <code>int32 power = 6;</code>
     */
    private void setPower(int value) {
      
      power_ = value;
    }
    /**
     * <code>int32 power = 6;</code>
     */
    private void clearPower() {
      
      power_ = 0;
    }

    public static final int REWARD_FIELD_NUMBER = 7;
    private com.google.protobuf.Internal.ProtobufList<kitty_protos.Adventure.AdventureReward> reward_;
    /**
     * <code>repeated .kitty_protos.AdventureReward reward = 7;</code>
     */
    @java.lang.Override
    public java.util.List<kitty_protos.Adventure.AdventureReward> getRewardList() {
      return reward_;
    }
    /**
     * <code>repeated .kitty_protos.AdventureReward reward = 7;</code>
     */
    public java.util.List<? extends kitty_protos.Adventure.AdventureRewardOrBuilder> 
        getRewardOrBuilderList() {
      return reward_;
    }
    /**
     * <code>repeated .kitty_protos.AdventureReward reward = 7;</code>
     */
    @java.lang.Override
    public int getRewardCount() {
      return reward_.size();
    }
    /**
     * <code>repeated .kitty_protos.AdventureReward reward = 7;</code>
     */
    @java.lang.Override
    public kitty_protos.Adventure.AdventureReward getReward(int index) {
      return reward_.get(index);
    }
    /**
     * <code>repeated .kitty_protos.AdventureReward reward = 7;</code>
     */
    public kitty_protos.Adventure.AdventureRewardOrBuilder getRewardOrBuilder(
        int index) {
      return reward_.get(index);
    }
    private void ensureRewardIsMutable() {
      if (!reward_.isModifiable()) {
        reward_ =
            com.google.protobuf.GeneratedMessageLite.mutableCopy(reward_);
       }
    }

    /**
     * <code>repeated .kitty_protos.AdventureReward reward = 7;</code>
     */
    private void setReward(
        int index, kitty_protos.Adventure.AdventureReward value) {
      if (value == null) {
        throw new NullPointerException();
      }
      ensureRewardIsMutable();
      reward_.set(index, value);
    }
    /**
     * <code>repeated .kitty_protos.AdventureReward reward = 7;</code>
     */
    private void setReward(
        int index, kitty_protos.Adventure.AdventureReward.Builder builderForValue) {
      ensureRewardIsMutable();
      reward_.set(index, builderForValue.build());
    }
    /**
     * <code>repeated .kitty_protos.AdventureReward reward = 7;</code>
     */
    private void addReward(kitty_protos.Adventure.AdventureReward value) {
      if (value == null) {
        throw new NullPointerException();
      }
      ensureRewardIsMutable();
      reward_.add(value);
    }
    /**
     * <code>repeated .kitty_protos.AdventureReward reward = 7;</code>
     */
    private void addReward(
        int index, kitty_protos.Adventure.AdventureReward value) {
      if (value == null) {
        throw new NullPointerException();
      }
      ensureRewardIsMutable();
      reward_.add(index, value);
    }
    /**
     * <code>repeated .kitty_protos.AdventureReward reward = 7;</code>
     */
    private void addReward(
        kitty_protos.Adventure.AdventureReward.Builder builderForValue) {
      ensureRewardIsMutable();
      reward_.add(builderForValue.build());
    }
    /**
     * <code>repeated .kitty_protos.AdventureReward reward = 7;</code>
     */
    private void addReward(
        int index, kitty_protos.Adventure.AdventureReward.Builder builderForValue) {
      ensureRewardIsMutable();
      reward_.add(index, builderForValue.build());
    }
    /**
     * <code>repeated .kitty_protos.AdventureReward reward = 7;</code>
     */
    private void addAllReward(
        java.lang.Iterable<? extends kitty_protos.Adventure.AdventureReward> values) {
      ensureRewardIsMutable();
      com.google.protobuf.AbstractMessageLite.addAll(
          values, reward_);
    }
    /**
     * <code>repeated .kitty_protos.AdventureReward reward = 7;</code>
     */
    private void clearReward() {
      reward_ = emptyProtobufList();
    }
    /**
     * <code>repeated .kitty_protos.AdventureReward reward = 7;</code>
     */
    private void removeReward(int index) {
      ensureRewardIsMutable();
      reward_.remove(index);
    }

    public static final int EVENTS_FIELD_NUMBER = 8;
    private com.google.protobuf.Internal.ProtobufList<kitty_protos.Adventure.AdventureEvent> events_;
    /**
     * <code>repeated .kitty_protos.AdventureEvent events = 8;</code>
     */
    @java.lang.Override
    public java.util.List<kitty_protos.Adventure.AdventureEvent> getEventsList() {
      return events_;
    }
    /**
     * <code>repeated .kitty_protos.AdventureEvent events = 8;</code>
     */
    public java.util.List<? extends kitty_protos.Adventure.AdventureEventOrBuilder> 
        getEventsOrBuilderList() {
      return events_;
    }
    /**
     * <code>repeated .kitty_protos.AdventureEvent events = 8;</code>
     */
    @java.lang.Override
    public int getEventsCount() {
      return events_.size();
    }
    /**
     * <code>repeated .kitty_protos.AdventureEvent events = 8;</code>
     */
    @java.lang.Override
    public kitty_protos.Adventure.AdventureEvent getEvents(int index) {
      return events_.get(index);
    }
    /**
     * <code>repeated .kitty_protos.AdventureEvent events = 8;</code>
     */
    public kitty_protos.Adventure.AdventureEventOrBuilder getEventsOrBuilder(
        int index) {
      return events_.get(index);
    }
    private void ensureEventsIsMutable() {
      if (!events_.isModifiable()) {
        events_ =
            com.google.protobuf.GeneratedMessageLite.mutableCopy(events_);
       }
    }

    /**
     * <code>repeated .kitty_protos.AdventureEvent events = 8;</code>
     */
    private void setEvents(
        int index, kitty_protos.Adventure.AdventureEvent value) {
      if (value == null) {
        throw new NullPointerException();
      }
      ensureEventsIsMutable();
      events_.set(index, value);
    }
    /**
     * <code>repeated .kitty_protos.AdventureEvent events = 8;</code>
     */
    private void setEvents(
        int index, kitty_protos.Adventure.AdventureEvent.Builder builderForValue) {
      ensureEventsIsMutable();
      events_.set(index, builderForValue.build());
    }
    /**
     * <code>repeated .kitty_protos.AdventureEvent events = 8;</code>
     */
    private void addEvents(kitty_protos.Adventure.AdventureEvent value) {
      if (value == null) {
        throw new NullPointerException();
      }
      ensureEventsIsMutable();
      events_.add(value);
    }
    /**
     * <code>repeated .kitty_protos.AdventureEvent events = 8;</code>
     */
    private void addEvents(
        int index, kitty_protos.Adventure.AdventureEvent value) {
      if (value == null) {
        throw new NullPointerException();
      }
      ensureEventsIsMutable();
      events_.add(index, value);
    }
    /**
     * <code>repeated .kitty_protos.AdventureEvent events = 8;</code>
     */
    private void addEvents(
        kitty_protos.Adventure.AdventureEvent.Builder builderForValue) {
      ensureEventsIsMutable();
      events_.add(builderForValue.build());
    }
    /**
     * <code>repeated .kitty_protos.AdventureEvent events = 8;</code>
     */
    private void addEvents(
        int index, kitty_protos.Adventure.AdventureEvent.Builder builderForValue) {
      ensureEventsIsMutable();
      events_.add(index, builderForValue.build());
    }
    /**
     * <code>repeated .kitty_protos.AdventureEvent events = 8;</code>
     */
    private void addAllEvents(
        java.lang.Iterable<? extends kitty_protos.Adventure.AdventureEvent> values) {
      ensureEventsIsMutable();
      com.google.protobuf.AbstractMessageLite.addAll(
          values, events_);
    }
    /**
     * <code>repeated .kitty_protos.AdventureEvent events = 8;</code>
     */
    private void clearEvents() {
      events_ = emptyProtobufList();
    }
    /**
     * <code>repeated .kitty_protos.AdventureEvent events = 8;</code>
     */
    private void removeEvents(int index) {
      ensureEventsIsMutable();
      events_.remove(index);
    }

    public static final int VISITADTIMES_FIELD_NUMBER = 9;
    private int visitAdTimes_;
    /**
     * <code>int32 visitAdTimes = 9;</code>
     */
    @java.lang.Override
    public int getVisitAdTimes() {
      return visitAdTimes_;
    }
    /**
     * <code>int32 visitAdTimes = 9;</code>
     */
    private void setVisitAdTimes(int value) {
      
      visitAdTimes_ = value;
    }
    /**
     * <code>int32 visitAdTimes = 9;</code>
     */
    private void clearVisitAdTimes() {
      
      visitAdTimes_ = 0;
    }

    public static kitty_protos.S2CSyncAdventure.S2C_SyncAdventure parseFrom(
        java.nio.ByteBuffer data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, data);
    }
    public static kitty_protos.S2CSyncAdventure.S2C_SyncAdventure parseFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, data, extensionRegistry);
    }
    public static kitty_protos.S2CSyncAdventure.S2C_SyncAdventure parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, data);
    }
    public static kitty_protos.S2CSyncAdventure.S2C_SyncAdventure parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, data, extensionRegistry);
    }
    public static kitty_protos.S2CSyncAdventure.S2C_SyncAdventure parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, data);
    }
    public static kitty_protos.S2CSyncAdventure.S2C_SyncAdventure parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, data, extensionRegistry);
    }
    public static kitty_protos.S2CSyncAdventure.S2C_SyncAdventure parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, input);
    }
    public static kitty_protos.S2CSyncAdventure.S2C_SyncAdventure parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, input, extensionRegistry);
    }
    public static kitty_protos.S2CSyncAdventure.S2C_SyncAdventure parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return parseDelimitedFrom(DEFAULT_INSTANCE, input);
    }
    public static kitty_protos.S2CSyncAdventure.S2C_SyncAdventure parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }
    public static kitty_protos.S2CSyncAdventure.S2C_SyncAdventure parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, input);
    }
    public static kitty_protos.S2CSyncAdventure.S2C_SyncAdventure parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static Builder newBuilder() {
      return (Builder) DEFAULT_INSTANCE.createBuilder();
    }
    public static Builder newBuilder(kitty_protos.S2CSyncAdventure.S2C_SyncAdventure prototype) {
      return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
    }

    /**
     * Protobuf type {@code kitty_protos.S2C_SyncAdventure}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageLite.Builder<
          kitty_protos.S2CSyncAdventure.S2C_SyncAdventure, Builder> implements
        // @@protoc_insertion_point(builder_implements:kitty_protos.S2C_SyncAdventure)
        kitty_protos.S2CSyncAdventure.S2C_SyncAdventureOrBuilder {
      // Construct using kitty_protos.S2CSyncAdventure.S2C_SyncAdventure.newBuilder()
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

      @java.lang.Override

      public int getKittiesCount() {
        return instance.getKittiesMap().size();
      }
      /**
       * <code>map&lt;int32, int32&gt; kitties = 2;</code>
       */
      @java.lang.Override

      public boolean containsKitties(
          int key) {
        
        return instance.getKittiesMap().containsKey(key);
      }

      public Builder clearKitties() {
        copyOnWrite();
        instance.getMutableKittiesMap().clear();
        return this;
      }
      /**
       * <code>map&lt;int32, int32&gt; kitties = 2;</code>
       */

      public Builder removeKitties(
          int key) {
        
        copyOnWrite();
        instance.getMutableKittiesMap().remove(key);
        return this;
      }
      /**
       * Use {@link #getKittiesMap()} instead.
       */
      @java.lang.Override
      @java.lang.Deprecated
      public java.util.Map<java.lang.Integer, java.lang.Integer> getKitties() {
        return getKittiesMap();
      }
      /**
       * <code>map&lt;int32, int32&gt; kitties = 2;</code>
       */
      @java.lang.Override
      public java.util.Map<java.lang.Integer, java.lang.Integer> getKittiesMap() {
        return java.util.Collections.unmodifiableMap(
            instance.getKittiesMap());
      }
      /**
       * <code>map&lt;int32, int32&gt; kitties = 2;</code>
       */
      @java.lang.Override

      public int getKittiesOrDefault(
          int key,
          int defaultValue) {
        
        java.util.Map<java.lang.Integer, java.lang.Integer> map =
            instance.getKittiesMap();
        return map.containsKey(key) ? map.get(key) : defaultValue;
      }
      /**
       * <code>map&lt;int32, int32&gt; kitties = 2;</code>
       */
      @java.lang.Override

      public int getKittiesOrThrow(
          int key) {
        
        java.util.Map<java.lang.Integer, java.lang.Integer> map =
            instance.getKittiesMap();
        if (!map.containsKey(key)) {
          throw new java.lang.IllegalArgumentException();
        }
        return map.get(key);
      }
      /**
       * <code>map&lt;int32, int32&gt; kitties = 2;</code>
       */
      public Builder putKitties(
          int key,
          int value) {
        
        
        copyOnWrite();
        instance.getMutableKittiesMap().put(key, value);
        return this;
      }
      /**
       * <code>map&lt;int32, int32&gt; kitties = 2;</code>
       */
      public Builder putAllKitties(
          java.util.Map<java.lang.Integer, java.lang.Integer> values) {
        copyOnWrite();
        instance.getMutableKittiesMap().putAll(values);
        return this;
      }

      /**
       * <code>int32 status = 3;</code>
       */
      @java.lang.Override
      public int getStatus() {
        return instance.getStatus();
      }
      /**
       * <code>int32 status = 3;</code>
       */
      public Builder setStatus(int value) {
        copyOnWrite();
        instance.setStatus(value);
        return this;
      }
      /**
       * <code>int32 status = 3;</code>
       */
      public Builder clearStatus() {
        copyOnWrite();
        instance.clearStatus();
        return this;
      }

      /**
       * <code>int64 beginTime = 4;</code>
       */
      @java.lang.Override
      public long getBeginTime() {
        return instance.getBeginTime();
      }
      /**
       * <code>int64 beginTime = 4;</code>
       */
      public Builder setBeginTime(long value) {
        copyOnWrite();
        instance.setBeginTime(value);
        return this;
      }
      /**
       * <code>int64 beginTime = 4;</code>
       */
      public Builder clearBeginTime() {
        copyOnWrite();
        instance.clearBeginTime();
        return this;
      }

      /**
       * <code>int64 endTime = 5;</code>
       */
      @java.lang.Override
      public long getEndTime() {
        return instance.getEndTime();
      }
      /**
       * <code>int64 endTime = 5;</code>
       */
      public Builder setEndTime(long value) {
        copyOnWrite();
        instance.setEndTime(value);
        return this;
      }
      /**
       * <code>int64 endTime = 5;</code>
       */
      public Builder clearEndTime() {
        copyOnWrite();
        instance.clearEndTime();
        return this;
      }

      /**
       * <code>int32 power = 6;</code>
       */
      @java.lang.Override
      public int getPower() {
        return instance.getPower();
      }
      /**
       * <code>int32 power = 6;</code>
       */
      public Builder setPower(int value) {
        copyOnWrite();
        instance.setPower(value);
        return this;
      }
      /**
       * <code>int32 power = 6;</code>
       */
      public Builder clearPower() {
        copyOnWrite();
        instance.clearPower();
        return this;
      }

      /**
       * <code>repeated .kitty_protos.AdventureReward reward = 7;</code>
       */
      @java.lang.Override
      public java.util.List<kitty_protos.Adventure.AdventureReward> getRewardList() {
        return java.util.Collections.unmodifiableList(
            instance.getRewardList());
      }
      /**
       * <code>repeated .kitty_protos.AdventureReward reward = 7;</code>
       */
      @java.lang.Override
      public int getRewardCount() {
        return instance.getRewardCount();
      }/**
       * <code>repeated .kitty_protos.AdventureReward reward = 7;</code>
       */
      @java.lang.Override
      public kitty_protos.Adventure.AdventureReward getReward(int index) {
        return instance.getReward(index);
      }
      /**
       * <code>repeated .kitty_protos.AdventureReward reward = 7;</code>
       */
      public Builder setReward(
          int index, kitty_protos.Adventure.AdventureReward value) {
        copyOnWrite();
        instance.setReward(index, value);
        return this;
      }
      /**
       * <code>repeated .kitty_protos.AdventureReward reward = 7;</code>
       */
      public Builder setReward(
          int index, kitty_protos.Adventure.AdventureReward.Builder builderForValue) {
        copyOnWrite();
        instance.setReward(index, builderForValue);
        return this;
      }
      /**
       * <code>repeated .kitty_protos.AdventureReward reward = 7;</code>
       */
      public Builder addReward(kitty_protos.Adventure.AdventureReward value) {
        copyOnWrite();
        instance.addReward(value);
        return this;
      }
      /**
       * <code>repeated .kitty_protos.AdventureReward reward = 7;</code>
       */
      public Builder addReward(
          int index, kitty_protos.Adventure.AdventureReward value) {
        copyOnWrite();
        instance.addReward(index, value);
        return this;
      }
      /**
       * <code>repeated .kitty_protos.AdventureReward reward = 7;</code>
       */
      public Builder addReward(
          kitty_protos.Adventure.AdventureReward.Builder builderForValue) {
        copyOnWrite();
        instance.addReward(builderForValue);
        return this;
      }
      /**
       * <code>repeated .kitty_protos.AdventureReward reward = 7;</code>
       */
      public Builder addReward(
          int index, kitty_protos.Adventure.AdventureReward.Builder builderForValue) {
        copyOnWrite();
        instance.addReward(index, builderForValue);
        return this;
      }
      /**
       * <code>repeated .kitty_protos.AdventureReward reward = 7;</code>
       */
      public Builder addAllReward(
          java.lang.Iterable<? extends kitty_protos.Adventure.AdventureReward> values) {
        copyOnWrite();
        instance.addAllReward(values);
        return this;
      }
      /**
       * <code>repeated .kitty_protos.AdventureReward reward = 7;</code>
       */
      public Builder clearReward() {
        copyOnWrite();
        instance.clearReward();
        return this;
      }
      /**
       * <code>repeated .kitty_protos.AdventureReward reward = 7;</code>
       */
      public Builder removeReward(int index) {
        copyOnWrite();
        instance.removeReward(index);
        return this;
      }

      /**
       * <code>repeated .kitty_protos.AdventureEvent events = 8;</code>
       */
      @java.lang.Override
      public java.util.List<kitty_protos.Adventure.AdventureEvent> getEventsList() {
        return java.util.Collections.unmodifiableList(
            instance.getEventsList());
      }
      /**
       * <code>repeated .kitty_protos.AdventureEvent events = 8;</code>
       */
      @java.lang.Override
      public int getEventsCount() {
        return instance.getEventsCount();
      }/**
       * <code>repeated .kitty_protos.AdventureEvent events = 8;</code>
       */
      @java.lang.Override
      public kitty_protos.Adventure.AdventureEvent getEvents(int index) {
        return instance.getEvents(index);
      }
      /**
       * <code>repeated .kitty_protos.AdventureEvent events = 8;</code>
       */
      public Builder setEvents(
          int index, kitty_protos.Adventure.AdventureEvent value) {
        copyOnWrite();
        instance.setEvents(index, value);
        return this;
      }
      /**
       * <code>repeated .kitty_protos.AdventureEvent events = 8;</code>
       */
      public Builder setEvents(
          int index, kitty_protos.Adventure.AdventureEvent.Builder builderForValue) {
        copyOnWrite();
        instance.setEvents(index, builderForValue);
        return this;
      }
      /**
       * <code>repeated .kitty_protos.AdventureEvent events = 8;</code>
       */
      public Builder addEvents(kitty_protos.Adventure.AdventureEvent value) {
        copyOnWrite();
        instance.addEvents(value);
        return this;
      }
      /**
       * <code>repeated .kitty_protos.AdventureEvent events = 8;</code>
       */
      public Builder addEvents(
          int index, kitty_protos.Adventure.AdventureEvent value) {
        copyOnWrite();
        instance.addEvents(index, value);
        return this;
      }
      /**
       * <code>repeated .kitty_protos.AdventureEvent events = 8;</code>
       */
      public Builder addEvents(
          kitty_protos.Adventure.AdventureEvent.Builder builderForValue) {
        copyOnWrite();
        instance.addEvents(builderForValue);
        return this;
      }
      /**
       * <code>repeated .kitty_protos.AdventureEvent events = 8;</code>
       */
      public Builder addEvents(
          int index, kitty_protos.Adventure.AdventureEvent.Builder builderForValue) {
        copyOnWrite();
        instance.addEvents(index, builderForValue);
        return this;
      }
      /**
       * <code>repeated .kitty_protos.AdventureEvent events = 8;</code>
       */
      public Builder addAllEvents(
          java.lang.Iterable<? extends kitty_protos.Adventure.AdventureEvent> values) {
        copyOnWrite();
        instance.addAllEvents(values);
        return this;
      }
      /**
       * <code>repeated .kitty_protos.AdventureEvent events = 8;</code>
       */
      public Builder clearEvents() {
        copyOnWrite();
        instance.clearEvents();
        return this;
      }
      /**
       * <code>repeated .kitty_protos.AdventureEvent events = 8;</code>
       */
      public Builder removeEvents(int index) {
        copyOnWrite();
        instance.removeEvents(index);
        return this;
      }

      /**
       * <code>int32 visitAdTimes = 9;</code>
       */
      @java.lang.Override
      public int getVisitAdTimes() {
        return instance.getVisitAdTimes();
      }
      /**
       * <code>int32 visitAdTimes = 9;</code>
       */
      public Builder setVisitAdTimes(int value) {
        copyOnWrite();
        instance.setVisitAdTimes(value);
        return this;
      }
      /**
       * <code>int32 visitAdTimes = 9;</code>
       */
      public Builder clearVisitAdTimes() {
        copyOnWrite();
        instance.clearVisitAdTimes();
        return this;
      }

      // @@protoc_insertion_point(builder_scope:kitty_protos.S2C_SyncAdventure)
    }
    @java.lang.Override
    @java.lang.SuppressWarnings({"unchecked", "fallthrough"})
    protected final java.lang.Object dynamicMethod(
        com.google.protobuf.GeneratedMessageLite.MethodToInvoke method,
        java.lang.Object arg0, java.lang.Object arg1) {
      switch (method) {
        case NEW_MUTABLE_INSTANCE: {
          return new kitty_protos.S2CSyncAdventure.S2C_SyncAdventure();
        }
        case NEW_BUILDER: {
          return new Builder();
        }
        case BUILD_MESSAGE_INFO: {
            java.lang.Object[] objects = new java.lang.Object[] {
              "code_",
              "kitties_",
              KittiesDefaultEntryHolder.defaultEntry,
              "status_",
              "beginTime_",
              "endTime_",
              "power_",
              "reward_",
              kitty_protos.Adventure.AdventureReward.class,
              "events_",
              kitty_protos.Adventure.AdventureEvent.class,
              "visitAdTimes_",
            };
            java.lang.String info =
                "\u0000\t\u0000\u0000\u0001\t\t\u0001\u0002\u0000\u0001\u0004\u00022\u0003\u0004\u0004" +
                "\u0002\u0005\u0002\u0006\u0004\u0007\u001b\b\u001b\t\u0004";
            return newMessageInfo(DEFAULT_INSTANCE, info, objects);
        }
        // fall through
        case GET_DEFAULT_INSTANCE: {
          return DEFAULT_INSTANCE;
        }
        case GET_PARSER: {
          com.google.protobuf.Parser<kitty_protos.S2CSyncAdventure.S2C_SyncAdventure> parser = PARSER;
          if (parser == null) {
            synchronized (kitty_protos.S2CSyncAdventure.S2C_SyncAdventure.class) {
              parser = PARSER;
              if (parser == null) {
                parser =
                    new DefaultInstanceBasedParser<kitty_protos.S2CSyncAdventure.S2C_SyncAdventure>(
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


    // @@protoc_insertion_point(class_scope:kitty_protos.S2C_SyncAdventure)
    private static final kitty_protos.S2CSyncAdventure.S2C_SyncAdventure DEFAULT_INSTANCE;
    static {
      S2C_SyncAdventure defaultInstance = new S2C_SyncAdventure();
      // New instances are implicitly immutable so no need to make
      // immutable.
      DEFAULT_INSTANCE = defaultInstance;
      com.google.protobuf.GeneratedMessageLite.registerDefaultInstance(
        S2C_SyncAdventure.class, defaultInstance);
    }

    public static kitty_protos.S2CSyncAdventure.S2C_SyncAdventure getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static volatile com.google.protobuf.Parser<S2C_SyncAdventure> PARSER;

    public static com.google.protobuf.Parser<S2C_SyncAdventure> parser() {
      return DEFAULT_INSTANCE.getParserForType();
    }
  }


  static {
  }

  // @@protoc_insertion_point(outer_class_scope)
}