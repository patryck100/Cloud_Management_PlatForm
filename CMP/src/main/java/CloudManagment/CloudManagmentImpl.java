// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: cloud.proto

package CloudManagment;

public final class CloudManagmentImpl {
  private CloudManagmentImpl() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_CloudManagment_AddRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_CloudManagment_AddRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_CloudManagment_ResponseMessage_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_CloudManagment_ResponseMessage_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_CloudManagment_RemoveRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_CloudManagment_RemoveRequest_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\013cloud.proto\022\016CloudManagment\"w\n\nAddRequ" +
      "est\022\r\n\005empNo\030\001 \001(\005\022\023\n\013dateOfBirth\030\002 \001(\t\022" +
      "\021\n\tfirstName\030\003 \001(\t\022\020\n\010lastName\030\004 \001(\t\022\016\n\006" +
      "gender\030\005 \001(\t\022\020\n\010hireDate\030\006 \001(\t\"#\n\017Respon" +
      "seMessage\022\020\n\010response\030\001 \001(\t\"1\n\rRemoveReq" +
      "uest\022\r\n\005empNo\030\001 \001(\005\022\021\n\tfirstName\030\002 \001(\t2\246" +
      "\001\n\014CloudService\022F\n\003add\022\032.CloudManagment." +
      "AddRequest\032\037.CloudManagment.ResponseMess" +
      "age\"\000(\001\022N\n\006remove\022\035.CloudManagment.Remov" +
      "eRequest\032\037.CloudManagment.ResponseMessag" +
      "e\"\000(\0010\001B&\n\016CloudManagmentB\022CloudManagmen" +
      "tImplP\001b\006proto3"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
        new com.google.protobuf.Descriptors.FileDescriptor.    InternalDescriptorAssigner() {
          public com.google.protobuf.ExtensionRegistry assignDescriptors(
              com.google.protobuf.Descriptors.FileDescriptor root) {
            descriptor = root;
            return null;
          }
        };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        }, assigner);
    internal_static_CloudManagment_AddRequest_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_CloudManagment_AddRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_CloudManagment_AddRequest_descriptor,
        new java.lang.String[] { "EmpNo", "DateOfBirth", "FirstName", "LastName", "Gender", "HireDate", });
    internal_static_CloudManagment_ResponseMessage_descriptor =
      getDescriptor().getMessageTypes().get(1);
    internal_static_CloudManagment_ResponseMessage_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_CloudManagment_ResponseMessage_descriptor,
        new java.lang.String[] { "Response", });
    internal_static_CloudManagment_RemoveRequest_descriptor =
      getDescriptor().getMessageTypes().get(2);
    internal_static_CloudManagment_RemoveRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_CloudManagment_RemoveRequest_descriptor,
        new java.lang.String[] { "EmpNo", "FirstName", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
