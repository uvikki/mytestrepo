package com.ca.eai.esb.transformer;

public class ServiceProxy implements Comparable<ServiceProxy> {
	private String mInUrl = null;
	private String mOutUrl = null;
	
	public ServiceProxy
		(String pInUrl,
		 String pOutUrl) {
		
		mInUrl = pInUrl;
		mOutUrl = pOutUrl;
	}
	
	@Override
	public int compareTo(ServiceProxy pProxyService) {
		return (mOutUrl.compareTo(pProxyService.getOutUrl()));
	}
	
	public String getInUrl() {
		return mInUrl;
	}

	public String getOutUrl() {
		return mOutUrl;
	}
}
