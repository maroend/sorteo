package com.core;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
 
public class SessionListener implements HttpSessionListener {
    
	private int sessionCount = 1;
    private static  String urlLogin="/sorteo/Login.do";
	private static  String path="/sorteo/";
    
    
    public enum Parameter { ID, USERNAME,NOMBRE };
 
    public void sessionCreated(HttpSessionEvent event) {
        synchronized (this) {
            sessionCount++;
            
        }
 
        System.out.println("Session Created: " + event.getSession().getId());
        System.out.println("Total Sessions: " + sessionCount);
    }
 
    public void sessionDestroyed(HttpSessionEvent event) {
        synchronized (this) {
            sessionCount--;
        }
        
        System.out.println("Session Destroyed: " + event.getSession().getId());
        System.out.println("Total Sessions: " + sessionCount);
    }
    
    
    /**/
    public static void SessionStart(HttpServletRequest request, HttpServletResponse response,boolean login){
    	
		//Validar si existe la session
    	HttpSession session = request.getSession(false);
    	
    	if(session != null){
    		
    		String targetURL = request.getRequestURI();
    		if(urlLogin.equals(targetURL) || path.equals(targetURL)){
    		
    			String url = session.getAttribute("origin").toString();
    			String[] partsurl= url.split("/");
    			
    			try {
					response.sendRedirect(partsurl[2]);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    			
    		}else{
    		
    			request.getSession().setAttribute("origin", targetURL);
    			
    		}
    		
            
    	}else{
    		try {
    			
    			if(!login){
    				response.sendRedirect("Login.do");	
    			}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	
	
    	
    }
    
    
    public static String getSession(HttpServletRequest request, HttpServletResponse response,Parameter param){
   	 
    	HttpSession session = request.getSession(false);
    	
    	if(session != null){
    	
    	switch(param){
    	 
    	 case ID:{
    		 return session.getAttribute("id").toString();
    	 }
    	 
         case USERNAME:{
    		 return session.getAttribute("usuario").toString();
    	 }
         
         case NOMBRE:{
    		 return session.getAttribute("nombre").toString();
    	 }
    		
    		 
    	 default: { //added TOP_RIGHT but forgot about it?
    	      throw new IllegalArgumentException("No existe: " + param);

    	   }
 	   
 	    
    	 }
    	
    	  }else{
    		  
    		  return null;
    		  
    	  }
		
   	 
    }
    
}
