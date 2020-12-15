package com.apkrename.plugin

import com.android.build.gradle.api.ApkVariantOutput

//import com.android.build.api.variant.ApplicationVariant
import com.android.build.gradle.api.ApplicationVariant
import com.android.build.gradle.AppExtension
import com.android.build.gradle.api.BaseVariantOutput
import org.gradle.api.Plugin
import org.gradle.api.Project

public class ApkRenamePlugin implements Plugin<Project> {

    public void apply(Project project) {
        println("ApkRenamePlugin!-------------------------------------------------------")
        project.android.applicationVariants.each { variant ->
            variant.outputs.all {
                println("${variant.name}-${variant.versionName}.apk")
                outputFileName = "${variant.name}-${variant.versionName}.apk"
            }
        }
    }
}