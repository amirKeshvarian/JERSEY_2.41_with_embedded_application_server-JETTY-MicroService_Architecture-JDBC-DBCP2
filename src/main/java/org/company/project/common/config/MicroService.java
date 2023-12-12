package org.company.project.common.config;

import org.company.project.common.annotation.MicroServiceRunner;
import org.company.project.common.annotation.RestConfiguration;
import org.company.project.common.exception.MicroServiceRunnerAnnotationNotFound;
import org.company.project.common.exception.RestConfigurationAnnotationNotFound;
import org.company.project.common.exception.ServerCouldNotStart;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.glassfish.jersey.jetty.JettyHttpContainerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;


import java.lang.annotation.Annotation;
import java.net.URI;
import java.util.Objects;

public class MicroService {

    private MicroService () {}

    public static void runWithoutThreadPoolAndIdleTimeout(Class<?> runnerClass) throws ServerCouldNotStart {
        MicroServiceRunner microServiceRunnerAnnotation = (MicroServiceRunner) getMicroServiceRunnerAnnotation(runnerClass);
        try {
            JettyHttpContainerFactory
                    .createServer(URI.create("http://" + microServiceRunnerAnnotation.ip() + ":" + microServiceRunnerAnnotation.port() + "/"
                    ), new ResourceConfig().packages(microServiceRunnerAnnotation.basePackage()));
        }catch (Exception e){
            throw new ServerCouldNotStart();
        }
    }
    public static void run (Class<?> runnerClass) throws ServerCouldNotStart{
        MicroServiceRunner microServiceRunnerAnnotation = (MicroServiceRunner) getMicroServiceRunnerAnnotation(runnerClass);
        RestConfiguration restConfigurationAnnotation = (RestConfiguration) getRestConfigurationAnnotation(runnerClass);
        try {
            QueuedThreadPool queuedThreadPool = new QueuedThreadPool(restConfigurationAnnotation.maxThread(), restConfigurationAnnotation.minThread(), restConfigurationAnnotation.idleTimeout());
            Server server = createServer(microServiceRunnerAnnotation.ip(),
                    microServiceRunnerAnnotation.port(),
                    restConfigurationAnnotation.prefix().replace("/", "").trim(),
                    microServiceRunnerAnnotation.basePackage(),
                    queuedThreadPool);
            server.start();
            server.join();
        }catch (Exception e){
            throw new ServerCouldNotStart();
        }
    }
    private static Annotation getMicroServiceRunnerAnnotation (Class<?> aClass)  {
        MicroServiceRunner microServiceRunnerAnnotation = aClass.getDeclaredAnnotation(MicroServiceRunner.class);
        if (Objects.isNull(microServiceRunnerAnnotation)){
            throw new MicroServiceRunnerAnnotationNotFound();
        }
        return microServiceRunnerAnnotation;
    }
    private static Annotation getRestConfigurationAnnotation (Class<?> aClass) {
        RestConfiguration restConfigurationAnnotation = aClass.getDeclaredAnnotation(RestConfiguration.class);
        if (Objects.isNull(restConfigurationAnnotation)){
            throw new RestConfigurationAnnotationNotFound();
        }
        return restConfigurationAnnotation;
    }
    private static Server createServer (String ip ,int port, String prefix, String basePackage, QueuedThreadPool queuedThreadPool){
        Server server = new Server(queuedThreadPool);
        ServerConnector serverConnector = new ServerConnector(server);
        serverConnector.setPort(port);
        serverConnector.setHost(ip);
        server.addConnector(serverConnector);
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        ServletHolder servletHolder = context.addServlet(ServletContainer.class, "/" + prefix + "/*");
        servletHolder.setInitOrder(1);//load on startup
        servletHolder.setInitParameter("jersey.config.server.provider.packages", basePackage);
        server.setHandler(context);
        return server;
    }
}
