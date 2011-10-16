package blueMesh.display;

//import android.bluetooth.BluetoothAdapter;
import java.util.List;

import android.content.Context;
import android.os.Handler;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import blueMesh.display.Constants;


@SuppressWarnings("unused")
public class BlueMeshService {
	
	//Number of devices connected
	private static int numRadiosConnected = 0;
	private static String myID = null;
	//List of devices connected
	private static List <String> connectedDeviceIDs;
	
	private final Handler mHandler;

	private final BluetoothAdapter mBluetoothAdapter;
    //private ConnectThread mConnectThread;
    //private ConnectedThread mConnectedThread;
	private SearchThread searchThread;

	//Constructor
	public BlueMeshService (Context context, Handler handler){
		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();		
		mHandler = handler;
	}
	
	//Used to start the service
	public synchronized void start(){
		searchThread = new SearchThread(mHandler, mBluetoothAdapter);
		searchThread.start();
	}
	
	
	public synchronized int print_debug( String outString ){
		
		//Create buffer for string to be converted to bytes to be 
		//displayed by the UI thread
		byte[] buffer = new byte[1024];
		int bytes;
		
		buffer = outString.getBytes();
		
		//Check size of input string
		if(buffer.length > 1024) return Constants.ERR_STRING_TO_LARGE;
		
		bytes = buffer.length;
		mHandler.obtainMessage(BlueMeshDisplayActivity.MSG_DEBUG,
				bytes, -1, buffer).sendToTarget();
		
		return Constants.SUCCESS;
	}
	
}
