package com.apkrename.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

public class ApkRenamePlugin implements Plugin<Project> {

    @Override
   public void apply(Project target) {
        // Register a task
        target.tasks.register("apkrename") {
            doLast {
                println("Hello from plugin 'com.apkrename.plugin:ApkRenamePlugin'")
            }
        }
    }

//    public void apply(Project project) {
//        // Register a task
//        project.tasks.register("greeting") {
//            doLast {
//                println("Hello from plugin 'com.lenebf.plugin.hello.greeting'")
//            }
//        }
//    }

}