package services_Implementation;

import capacity_print.capacityServiceGrpc.capacityServiceImplBase;
import io.grpc.stub.StreamObserver;
import capacity_print.*;

public class CapacityService extends capacityServiceImplBase {

/* ----------------------- Still needs to create the record with add, remove or alter data before being able to print it -------------*/	
	//Server Streaming API. Prints all record into cloud + storage. 
	@Override
	public void print(printRequest request, StreamObserver<printResponse> responseObserver) {
		boolean print = request.getPrint();
		String printing = "";
		int cloud = 1;
		
		//when a print is requested, "print" is set to true and a loop which goes through the entire list in the cloud
		//returns each record individually with a pause of a second
		if(print) {
			try {
				// goes through the entire list in the cloud
				for(int i= 0; i< cloud; i++) {
					printResponse response = printResponse.newBuilder().setPrinting(printing).build(); //this will generate a response for each data in the cloud
					responseObserver.onNext(response);
			 
					Thread.sleep(1000L);

				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} //put the thread to sleep for 1 second. It needs try and catch
			
			responseObserver.onCompleted();
			
		}
		
	}
	
	//First idea being Unary but considering to make it Client streaming. Client could send many request and receive only one response
	@Override
	public void storage(storageRequest request, StreamObserver<storageResponse> responseObserver) {
		
	}
	
	

}
