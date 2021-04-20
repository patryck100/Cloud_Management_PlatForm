package print;

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
    comments = "Source: print.proto")
public final class printServiceGrpc {

  private printServiceGrpc() {}

  public static final String SERVICE_NAME = "capacity.printService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<print.printRequest,
      print.printResponse> getPrintMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "print",
      requestType = print.printRequest.class,
      responseType = print.printResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<print.printRequest,
      print.printResponse> getPrintMethod() {
    io.grpc.MethodDescriptor<print.printRequest, print.printResponse> getPrintMethod;
    if ((getPrintMethod = printServiceGrpc.getPrintMethod) == null) {
      synchronized (printServiceGrpc.class) {
        if ((getPrintMethod = printServiceGrpc.getPrintMethod) == null) {
          printServiceGrpc.getPrintMethod = getPrintMethod = 
              io.grpc.MethodDescriptor.<print.printRequest, print.printResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(
                  "capacity.printService", "print"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  print.printRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  print.printResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new printServiceMethodDescriptorSupplier("print"))
                  .build();
          }
        }
     }
     return getPrintMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static printServiceStub newStub(io.grpc.Channel channel) {
    return new printServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static printServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new printServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static printServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new printServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class printServiceImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     *Server streaming API
     * </pre>
     */
    public void print(print.printRequest request,
        io.grpc.stub.StreamObserver<print.printResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getPrintMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getPrintMethod(),
            asyncServerStreamingCall(
              new MethodHandlers<
                print.printRequest,
                print.printResponse>(
                  this, METHODID_PRINT)))
          .build();
    }
  }

  /**
   */
  public static final class printServiceStub extends io.grpc.stub.AbstractStub<printServiceStub> {
    private printServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private printServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected printServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new printServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     *Server streaming API
     * </pre>
     */
    public void print(print.printRequest request,
        io.grpc.stub.StreamObserver<print.printResponse> responseObserver) {
      asyncServerStreamingCall(
          getChannel().newCall(getPrintMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class printServiceBlockingStub extends io.grpc.stub.AbstractStub<printServiceBlockingStub> {
    private printServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private printServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected printServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new printServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     *Server streaming API
     * </pre>
     */
    public java.util.Iterator<print.printResponse> print(
        print.printRequest request) {
      return blockingServerStreamingCall(
          getChannel(), getPrintMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class printServiceFutureStub extends io.grpc.stub.AbstractStub<printServiceFutureStub> {
    private printServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private printServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected printServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new printServiceFutureStub(channel, callOptions);
    }
  }

  private static final int METHODID_PRINT = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final printServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(printServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_PRINT:
          serviceImpl.print((print.printRequest) request,
              (io.grpc.stub.StreamObserver<print.printResponse>) responseObserver);
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

  private static abstract class printServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    printServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return print.printImpl.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("printService");
    }
  }

  private static final class printServiceFileDescriptorSupplier
      extends printServiceBaseDescriptorSupplier {
    printServiceFileDescriptorSupplier() {}
  }

  private static final class printServiceMethodDescriptorSupplier
      extends printServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    printServiceMethodDescriptorSupplier(String methodName) {
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
      synchronized (printServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new printServiceFileDescriptorSupplier())
              .addMethod(getPrintMethod())
              .build();
        }
      }
    }
    return result;
  }
}
