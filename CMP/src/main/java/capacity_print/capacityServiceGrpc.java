package capacity_print;

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
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.15.0)",
    comments = "Source: capacity.proto")
public final class capacityServiceGrpc {

  private capacityServiceGrpc() {}

  public static final String SERVICE_NAME = "capacity.capacityService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<capacity_print.printRequest,
      capacity_print.printResponse> getPrintMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "print",
      requestType = capacity_print.printRequest.class,
      responseType = capacity_print.printResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<capacity_print.printRequest,
      capacity_print.printResponse> getPrintMethod() {
    io.grpc.MethodDescriptor<capacity_print.printRequest, capacity_print.printResponse> getPrintMethod;
    if ((getPrintMethod = capacityServiceGrpc.getPrintMethod) == null) {
      synchronized (capacityServiceGrpc.class) {
        if ((getPrintMethod = capacityServiceGrpc.getPrintMethod) == null) {
          capacityServiceGrpc.getPrintMethod = getPrintMethod = 
              io.grpc.MethodDescriptor.<capacity_print.printRequest, capacity_print.printResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(
                  "capacity.capacityService", "print"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  capacity_print.printRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  capacity_print.printResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new capacityServiceMethodDescriptorSupplier("print"))
                  .build();
          }
        }
     }
     return getPrintMethod;
  }

  private static volatile io.grpc.MethodDescriptor<capacity_print.storageRequest,
      capacity_print.storageResponse> getStorageMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "storage",
      requestType = capacity_print.storageRequest.class,
      responseType = capacity_print.storageResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<capacity_print.storageRequest,
      capacity_print.storageResponse> getStorageMethod() {
    io.grpc.MethodDescriptor<capacity_print.storageRequest, capacity_print.storageResponse> getStorageMethod;
    if ((getStorageMethod = capacityServiceGrpc.getStorageMethod) == null) {
      synchronized (capacityServiceGrpc.class) {
        if ((getStorageMethod = capacityServiceGrpc.getStorageMethod) == null) {
          capacityServiceGrpc.getStorageMethod = getStorageMethod = 
              io.grpc.MethodDescriptor.<capacity_print.storageRequest, capacity_print.storageResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "capacity.capacityService", "storage"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  capacity_print.storageRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  capacity_print.storageResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new capacityServiceMethodDescriptorSupplier("storage"))
                  .build();
          }
        }
     }
     return getStorageMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static capacityServiceStub newStub(io.grpc.Channel channel) {
    return new capacityServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static capacityServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new capacityServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static capacityServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new capacityServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class capacityServiceImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     *Server streaming API
     * </pre>
     */
    public void print(capacity_print.printRequest request,
        io.grpc.stub.StreamObserver<capacity_print.printResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getPrintMethod(), responseObserver);
    }

    /**
     * <pre>
     *Unary API
     * </pre>
     */
    public void storage(capacity_print.storageRequest request,
        io.grpc.stub.StreamObserver<capacity_print.storageResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getStorageMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getPrintMethod(),
            asyncServerStreamingCall(
              new MethodHandlers<
                capacity_print.printRequest,
                capacity_print.printResponse>(
                  this, METHODID_PRINT)))
          .addMethod(
            getStorageMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                capacity_print.storageRequest,
                capacity_print.storageResponse>(
                  this, METHODID_STORAGE)))
          .build();
    }
  }

  /**
   */
  public static final class capacityServiceStub extends io.grpc.stub.AbstractStub<capacityServiceStub> {
    private capacityServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private capacityServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected capacityServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new capacityServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     *Server streaming API
     * </pre>
     */
    public void print(capacity_print.printRequest request,
        io.grpc.stub.StreamObserver<capacity_print.printResponse> responseObserver) {
      asyncServerStreamingCall(
          getChannel().newCall(getPrintMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *Unary API
     * </pre>
     */
    public void storage(capacity_print.storageRequest request,
        io.grpc.stub.StreamObserver<capacity_print.storageResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getStorageMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class capacityServiceBlockingStub extends io.grpc.stub.AbstractStub<capacityServiceBlockingStub> {
    private capacityServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private capacityServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected capacityServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new capacityServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     *Server streaming API
     * </pre>
     */
    public java.util.Iterator<capacity_print.printResponse> print(
        capacity_print.printRequest request) {
      return blockingServerStreamingCall(
          getChannel(), getPrintMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     *Unary API
     * </pre>
     */
    public capacity_print.storageResponse storage(capacity_print.storageRequest request) {
      return blockingUnaryCall(
          getChannel(), getStorageMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class capacityServiceFutureStub extends io.grpc.stub.AbstractStub<capacityServiceFutureStub> {
    private capacityServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private capacityServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected capacityServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new capacityServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     *Unary API
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<capacity_print.storageResponse> storage(
        capacity_print.storageRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getStorageMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_PRINT = 0;
  private static final int METHODID_STORAGE = 1;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final capacityServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(capacityServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_PRINT:
          serviceImpl.print((capacity_print.printRequest) request,
              (io.grpc.stub.StreamObserver<capacity_print.printResponse>) responseObserver);
          break;
        case METHODID_STORAGE:
          serviceImpl.storage((capacity_print.storageRequest) request,
              (io.grpc.stub.StreamObserver<capacity_print.storageResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class capacityServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    capacityServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return capacity_print.capacityImpl.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("capacityService");
    }
  }

  private static final class capacityServiceFileDescriptorSupplier
      extends capacityServiceBaseDescriptorSupplier {
    capacityServiceFileDescriptorSupplier() {}
  }

  private static final class capacityServiceMethodDescriptorSupplier
      extends capacityServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    capacityServiceMethodDescriptorSupplier(String methodName) {
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
      synchronized (capacityServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new capacityServiceFileDescriptorSupplier())
              .addMethod(getPrintMethod())
              .addMethod(getStorageMethod())
              .build();
        }
      }
    }
    return result;
  }
}
