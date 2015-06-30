/**
 * 
 */
package com.ca.eai.esb.transformer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mule.DefaultMuleMessage;
import org.mule.api.MuleMessage;
import org.mule.api.transport.PropertyScope;
import org.mule.transformer.AbstractMessageTransformer;


/**
 * @author imaar01
 *
 */
public class IncomingRequestPropertyTransformer extends AbstractMessageTransformer {

	private static final String MULE_REMOTE_CLIENT_ADDRESS = "MULE_REMOTE_CLIENT_ADDRESS";
	private static final String ORIGINAL_MULE_REMOTE_CLIENT_ADDRESS = "ORIGINAL_MULE_REMOTE_CLIENT_ADDRESS";
	private static final String HTTP_DISABLE_STATUS_CODE_EXCEPTION_CHECK = "http.disable.status.code.exception.check";
	private final Log mLog = LogFactory.getLog(this.getClass());

	/* (non-Javadoc)
	 * @see org.mule.api.lifecycle.Callable#onCall(org.mule.api.MuleEventContext)
	 */
	@Override
    public Object transformMessage(MuleMessage pMessage, String pOutputEncoding) {
		
		// Add the original request's remote client address from the service router
		// as the inbound property to the actual service only if the value is null
		// otherwise carry forward the set value
		String lMuleRemoteClientAddress = pMessage.getInboundProperty(ORIGINAL_MULE_REMOTE_CLIENT_ADDRESS);
		
		try {
			//mLog.info("Remote Client Address: " + lMuleRemoteClientAddress);
			
			if (lMuleRemoteClientAddress == null || lMuleRemoteClientAddress.trim().length() < 1) {
				lMuleRemoteClientAddress = pMessage.getInboundProperty(MULE_REMOTE_CLIENT_ADDRESS);
			}
			
			pMessage.setProperty(ORIGINAL_MULE_REMOTE_CLIENT_ADDRESS, lMuleRemoteClientAddress, PropertyScope.OUTBOUND);
			pMessage.setInvocationProperty(HTTP_DISABLE_STATUS_CODE_EXCEPTION_CHECK, "true");
		} catch (Exception e) {
			mLog.error(e.getMessage());
		}
		
		DefaultMuleMessage lDMMessage = new DefaultMuleMessage(pMessage);
		return lDMMessage;
	}
}
