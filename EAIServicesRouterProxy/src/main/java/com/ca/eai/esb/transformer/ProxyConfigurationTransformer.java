package com.ca.eai.esb.transformer;

import java.util.Collections;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.mule.api.MuleMessage;
import org.mule.transformer.AbstractMessageTransformer;


public class ProxyConfigurationTransformer extends AbstractMessageTransformer
{
    private List<ServiceProxy> proxies;
    private String instance;
    private final Log log = LogFactory.getLog(this.getClass());        
    
    public String getInstance() {
		return instance;
	}

	public void setInstance(String instance) {
		this.instance = instance;
	}

	public List<ServiceProxy> getProxies() {
        return proxies;
    }

    public void setProxies(List<ServiceProxy> proxies) {
        this.proxies = proxies;
    }

    @Override
    public Object transformMessage(MuleMessage message, String outputEncoding) {
    	Collections.sort(proxies);
        log.debug(proxies.toString());
        int lCounter = 0;
        String lBackgroundColor = "";
        
        StringBuffer lBuffer = 
        	new StringBuffer(2048)
			.append("<body>")
			.append("<h1>Services Configuration - ")
			.append(getInstance())
			.append("</h1>")
			.append("<table border=\"1\">")
			.append("<th align=\"left\" bgcolor=\"#FFFF00\">In URL</th>")
			.append("<th align=\"left\" bgcolor=\"#FFFF00\">Out URL</th>");
        
        for (ServiceProxy lProxyService : proxies) {
        	if (lProxyService.getOutUrl().indexOf(":00000") < 0) {
	        	if ((++lCounter % 2) == 1) {
	        		lBackgroundColor = "#FFFFFF";
	        	} else {
	        		lBackgroundColor = "#D8D8D8";
	        	}
	
	        	lBuffer
					.append("\n<tr bgcolor=\"").append(lBackgroundColor).append("\"><td valign=\"top\">")
					.append(lProxyService.getInUrl())
					.append("</td><td valign=\"top\">")
					.append(lProxyService.getOutUrl())
					.append("</td></tr>");
        	}
        }
    
        lBuffer.append("</table></body>");
        
        return lBuffer.toString();
    }
}
