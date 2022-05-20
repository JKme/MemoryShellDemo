<%@ page import="java.lang.reflect.Field" %>
<%@ page import="org.apache.catalina.core.ApplicationContext" %>
<%@ page import="org.apache.catalina.core.StandardContext" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    {
//获取ApplicationContextFade对象，ApplicationContextFade是ServletContext的实现，所以其实是ServletContext对象
        ServletContext servletContext = request.getSession().getServletContext();
        Field appctx = null;
        try {
            appctx = servletContext.getClass().getDeclaredField("context"); //从ApplicationContextFade获取context，这里context是指ApplicationContext
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        appctx.setAccessible(true);
        ApplicationContext applicationContext = (ApplicationContext) appctx.get(servletContext); //获取ApplicationContext

        Field standCtx = applicationContext.getClass().getDeclaredField("context");
        standCtx.setAccessible(true);
        StandardContext standardContext = (StandardContext) standCtx.get(applicationContext);
        out.print("Hello");
    }
%>