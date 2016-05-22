package com.fernandocejas.frodo.plugin

import com.android.build.gradle.AppPlugin
import com.android.build.gradle.LibraryPlugin
import org.aspectj.bridge.IMessage
import org.aspectj.bridge.MessageHandler
import org.aspectj.tools.ajc.Main
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.TaskInstantiationException
import org.gradle.api.tasks.compile.JavaCompile

class FrodoPlugin implements Plugin<Project> {
  @Override
  void apply(Project project) {
    verifyRequiredPlugins project

    project.buildscript.repositories.maven {
      url "http://dl.bintray.com/android10/maven"
    }

    project.repositories.maven {
      url "http://dl.bintray.com/android10/maven"
    }

    project.dependencies {
      compile "com.fernandocejas.frodo:frodo-api:0.8.4"
      debugCompile "org.aspectj:aspectjrt:1.8.6"
      debugCompile "com.fernandocejas.frodo:frodo-runtime:0.8.4"
    }

    project.extensions.create('frodo', FrodoEnablerExtension)
    final def log = project.logger

    def variants = getProjectVariants project
    variants.all { variant ->
      if (!variant.buildType.isDebuggable()) {
        log.debug("Skipping non-debuggable build type '${variant.buildType.name}'.")
        return; //Only weaving on Debug version of the app/library.
      } else if (!project.frodo.enabled) {
        log.debug("Frodo is not enabled.")
        return;
      }

      JavaCompile javaCompile = variant.javaCompile
      javaCompile.doLast {
        String[] args = ["-showWeaveInfo",
                         "-1.5",
                         "-inpath", javaCompile.destinationDir.toString(),
                         "-aspectpath", javaCompile.classpath.asPath,
                         "-d", javaCompile.destinationDir.toString(),
                         "-classpath", javaCompile.classpath.asPath,
                         "-bootclasspath", project.android.bootClasspath.join(File.pathSeparator)]

        final MessageHandler handler = new MessageHandler(true);
        new Main().run(args, handler);
        for (IMessage message : handler.getMessages(null, true)) {
          switch (message.getKind()) {
            case IMessage.ABORT:
            case IMessage.ERROR:
            case IMessage.FAIL:
              log.error message.message, message.thrown
              break;
            case IMessage.WARNING:
              log.warn message.message, message.thrown
              break;
            case IMessage.INFO:
              log.info message.message, message.thrown
              break;
            case IMessage.DEBUG:
              log.debug message.message, message.thrown
              break;
          }
        }
      }
    }
  }

  def verifyRequiredPlugins(Project project) {
    def hasAppPlugin = project.plugins.hasPlugin(AppPlugin)
    def hasLibraryPlugin = project.plugins.hasPlugin(LibraryPlugin)
    if (!hasAppPlugin && !hasLibraryPlugin) {
      throw new TaskInstantiationException("Plugins required: 'android' or 'android-library'.")
    }
  }

  def getProjectVariants(Project project) {
    def variants
    if (project.plugins.hasPlugin(AppPlugin)) {
      variants = project.android.applicationVariants
    } else {
      variants = project.android.libraryVariants
    }
    return variants
  }
}
