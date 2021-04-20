package CloudManagment;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 * <pre>
 *name of the service
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.15.0)",
    comments = "Source: cloud.proto")
public final class CloudServiceGrpc {

  private CloudServiceGrpc() {}

  public static final String SERVICE_NAME = "CloudManagment.CloudService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<CloudManagment.AddRequest,
      CloudManagment.ResponseMessage> getAddMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "add",
      requestType = CloudManagment.AddRequest.class,
      responseType = CloudManagment.ResponseMessage.class,
      methodType = io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
  public static io.grpc.MethodDescriptor<CloudManagment.AddRequest,
      CloudManagment.ResponseMessage> getAddMethod() {
    io.grpc.MethodDescriptor<CloudManagment.AddRequest, CloudManagment.ResponseMessage> getAddMethod;
    if ((getAddMethod = CloudServiceGrpc.getAddMethod) == null) {
      synchronized (CloudServiceGrpc.class) {
        if ((getAddMethod = CloudServiceGrpc.getAddMethod) == null) {
          CloudServiceGrpc.getAddMethod = getAddMethod = 
              io.grpc.MethodDescriptor.<CloudManagment.AddRequest, CloudManagment.ResponseMessage>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
              .setFullMethodName(generateFullMethodName(
                  "CloudManagment.CloudService", "add"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  CloudManagment.AddRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  CloudManagment.ResponseMessage.getDefaultInstance()))
                  .setSchemaDescriptor(new CloudServiceMethodDescriptorSupplier("add"))
                  .build();
          }
        }
     }
     return getAddMethod;
  }

  private static volatile io.grpc.MethodDescriptor<CloudManagment.RemoveRequest,
      CloudManagment.ResponseMessage> getRemoveMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "remove",
      requestType = CloudManagment.RemoveRequest.class,
      responseType = CloudManagment.ResponseMessage.class,
      methodType = io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
  public static io.grpc.MethodDescriptor<CloudManagment.RemoveRequest,
      CloudManagment.ResponseMessage> getRemoveMethod() {
    io.grpc.MethodDescriptor<CloudManagment.RemoveRequest, CloudManagment.ResponseMessage> getRemoveMethod;
    if ((getRemoveMethod = CloudServiceGrpc.getRemoveMethod) == null) {
      synchronized (CloudServiceGrpc.class) {
        if ((getRemoveMethod = CloudServiceGrpc.getRemoveMethod) == null) {
          CloudServiceGrpc.getRemoveMethod = getRemoveMethod = 
              io.grpc.MethodDescriptor.<CloudManagment.RemoveRequest, CloudManagment.ResponseMessage>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
              .setFullMethodName(generateFullMethodName(
                  "CloudManagment.CloudService", "remove"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  CloudManagment.RemoveRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  CloudManagment.ResponseMessage.getDefaultInstance()))
                  .setSchemaDescriptor(new CloudServiceMethodDescriptorSupplier("remove"))
                  .build();
          }
        }
     }
     return getRemoveMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static CloudServiceStub newStub(io.grpc.Channel channel) {
    return new CloudServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static CloudServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new CloudServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static CloudServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new CloudServiceFutureStub(channel);
  }

  /**
   * <pre>
   *name of the service
   * </pre>
   */
  public static abstract class CloudServiceImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     *Client Streaming API (Client willl be able to send many add request and receive a final result)
     * </pre>
     */
    public io.grpc.stub.StreamObserver<CloudManagment.AddRequest> add(
        io.grpc.stub.StreamObserver<CloudManagment.ResponseMessage> responseObserver) {
      return asyncUnimplementedStreamingCall(getAddMethod(), responseObserver);
    }

    /**
     * <pre>
     *Bidirectional Streaming API (Client gets respective response for each request, and final result)
     * </pre>
     */
    public io.grpc.stub.StreamObserver<CloudManagment.RemoveRequest> remove(
        io.grpc.stub.StreamObserver<CloudManagment.ResponseMessage> responseObserver) {
      return asyncUnimplementedStreamingCall(getRemoveMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getAddMethod(),
            asyncClientStreamingCall(
              new MethodHandlers<
                CloudManagment.AddRequest,
                CloudManagment.ResponseMessage>(
                  this, METHODID_ADD)))
          .addMethod(
            getRemoveMethod(),
            asyncBidiStreamingCall(
              new MethodHandlers<
                CloudManagment.RemoveRequest,
                CloudManagment.ResponseMessage>(
                  this, METHODID_REMOVE)))
          .build();
    }
  }

  /**
   * <pre>
   *name of the service
   * </pre>
   */
  public static final class CloudServiceStub extends io.grpc.stub.AbstractStub<CloudServiceStub> {
    private CloudServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private CloudServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected CloudServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new CloudServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     *Client Streaming API (Client willl be able to send many add request and receive a final result)
     * </pre>
     */
    public io.grpc.stub.StreamObserver<CloudManagment.AddRequest> add(
        io.grpc.stub.StreamObserver<CloudManagment.ResponseMessage> responseObserver) {
      return asyncClientStreamingCall(
          getChannel().newCall(getAddMethod(), getCallOptions()), responseObserver);
    }

    /**
     * <pre>
     *Bidirectional Streaming API (Client gets respective response for each request, and final result)
     * </pre>
     */
    public io.grpc.stub.StreamObserver<CloudManagment.RemoveRequest> remove(
        io.grpc.stub.StreamObserver<CloudManagment.ResponseMessage> responseObserver) {
      return asyncBidiStreamingCall(
          getChannel().newCall(getRemoveMethod(), getCallOptions()), responseObserver);
    }
  }

  /**
   * <pre>
   *name of the service
   * </pre>
   */
  public static final class CloudServiceBlockingStub extends io.grpc.stub.AbstractStub<CloudServiceBlockingStub> {
    private CloudServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private CloudServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected CloudServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new CloudServiceBlockingStub(channel, callOptions);
    }
  }

  /**
   * <pre>
   *name of the service
   * </pre>
   */
  public static final class CloudServiceFutureStub extends io.grpc.stub.AbstractStub<CloudServiceFutureStub> {
    private CloudServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private CloudServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected CloudServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new CloudServiceFutureStub(channel, callOptions);
    }
  }

  private static final int METHODID_ADD = 0;
  private static final int METHODID_REMOVE = 1;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final CloudServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(CloudServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_ADD:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.add(
              (io.grpc.stub.StreamObserver<CloudManagment.ResponseMessage>) responseObserver);
        case METHODID_REMOVE:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.remove(
              (io.grpc.stub.StreamObserver<CloudManagment.ResponseMessage>) responseObserver);
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class CloudServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    CloudServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return CloudManagment.CloudManagmentImpl.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("CloudService");
    }
  }

  private static final class CloudServiceFileDescriptorSupplier
      extends CloudServiceBaseDescriptorSupplier {
    CloudServiceFileDescriptorSupplier() {}
  }

  private static final class CloudServiceMethodDescriptorSupplier
      extends CloudServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    CloudServiceMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (CloudServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new CloudServiceFileDescriptorSupplier())
              .addMethod(getAddMethod())
              .addMethod(getRemoveMethod())
              .build();
        }
      }
    }
    return result;
  }
}
