package org.dei.perla.socket;

import org.usb4java.*;

import org.usb4java.LibUsb;

public class Usb {
	
	public static void initializeContext(){
	Context context = new Context();
	int result = LibUsb.init(context);
	if (result != LibUsb.SUCCESS) throw new LibUsbException("Unable to initialize libusb.", result);
	}
	
	public Device findDevice(short vendorId, short productId)
	{
	    // Read the USB device list
	    DeviceList list = new DeviceList();
	    int result = LibUsb.getDeviceList(null, list);
	    if (result < 0) throw new LibUsbException("Unable to get device list", result);

	    try
	    {
	        // Iterate over all devices and scan for the right one
	        for (Device device: list)
	        {
	            DeviceDescriptor descriptor = new DeviceDescriptor();
	            result = LibUsb.getDeviceDescriptor(device, descriptor);
	            if (result != LibUsb.SUCCESS) throw new LibUsbException("Unable to read device descriptor", result);
	            if (descriptor.idVendor() == vendorId && descriptor.idProduct() == productId) return device;
	        }
	    }
	    finally
	    {
	        // Ensure the allocated device list is freed
	        LibUsb.freeDeviceList(list, true);
	    }

	    // Device not found
	    return null;
	}
	
	public static void main(String args[]){
		 DeviceList list = new DeviceList();
		 int result = LibUsb.getDeviceList(null, list);
		 System.out.println(result);
	}
	
}
